package eci.ieti.controller;

import eci.ieti.data.TodoRepository;
import eci.ieti.data.model.Todo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsResource;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mongodb.client.gridfs.model.GridFSFile;

import java.io.IOException;
import java.util.List;

@RequestMapping("api")
@RestController
public class RESTController {

	@Autowired
	GridFsTemplate gridFsTemplate;

	@Autowired
	TodoRepository todoRepository;

	@RequestMapping("/files/{filename}")
	public ResponseEntity<InputStreamResource> getFileByName(@PathVariable String filename) throws IOException {
		GridFSFile file = gridFsTemplate.findOne(new Query().addCriteria(Criteria.where("filename").is(filename)));
		if (file != null) {
			GridFsResource resource = gridFsTemplate.getResource(file.getFilename());
			return ResponseEntity.ok().contentType(MediaType.valueOf(resource.getContentType()))
					.body(new InputStreamResource(resource.getInputStream()));
		}
		return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	}

	@CrossOrigin("*")
	@PostMapping("/files")
	public String handleFileUpload(@RequestParam("file") MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			gridFsTemplate.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType());
		} catch (IOException e) {
			return "files/Not Found";
		}
		return "files/" + file.getOriginalFilename();	
	}

	@CrossOrigin("*")
	@PostMapping("/todo")
	public Todo createTodo(@RequestBody Todo todo) {
		 return todoRepository.save(todo);
	}

	@CrossOrigin("*")
	@GetMapping("/todo")
	public List<Todo> getTodoList() {
		return todoRepository.findAll();
	}

}
