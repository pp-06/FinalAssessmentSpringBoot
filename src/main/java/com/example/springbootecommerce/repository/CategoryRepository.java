package com.example.springbootecommerce.repository;

import com.example.springbootecommerce.model.Category;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CategoryRepository extends MongoRepository<Category, ObjectId> {

    @Query("{id: \"?0\"}")
    List<Category> findByID(ObjectId id);
    @Query("{name: \"?0\"}")
    List<Category> findByName(String name);

    @Query("{description: \"?0\"}")
    List<Category> findByDesc(String description);
}
