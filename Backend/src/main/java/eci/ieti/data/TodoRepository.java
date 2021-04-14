package eci.ieti.data;

import eci.ieti.data.model.Todo;
import eci.ieti.data.model.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TodoRepository extends MongoRepository<Todo, String> {

    List<Todo> findByResponsible(User responsible);

}


