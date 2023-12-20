package com.example.springbootecommerce.repository;

import com.example.springbootecommerce.model.Product;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends MongoRepository<Product, ObjectId> {

    @Query("{id: \"?0\"}")
    List<Product> findByID(ObjectId id);
    @Query("{name: \"?0\"}")
    List<Product> findByName(String name);

    @Query("{description: \"?0\"}")
    List<Product> findByDesc(String description);

    @Query("{price: ?0}")
    List<Product> findByPrice(int price);
}
