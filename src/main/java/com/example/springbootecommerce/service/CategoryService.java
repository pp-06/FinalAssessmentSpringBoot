package com.example.springbootecommerce.service;

import com.example.springbootecommerce.exceptionHandler.CategoryException;
import com.example.springbootecommerce.model.Category;
import com.example.springbootecommerce.repository.CategoryRepository;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    private CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) { this.categoryRepository = categoryRepository;}

    // add category
    public Category createCategory(Category category) {
        return categoryRepository.save(category);
    }

    //get category by id
    public Category getCategoryById(ObjectId id) throws CategoryException{
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isEmpty() || optCategory == null) {
            throw new CategoryException("Category doesn't exist");
        }
        return optCategory.orElseGet(optCategory::get);
    }

    //get category by any field
    public List<Category> getCategoryByField(String field, String value) throws CategoryException {
        List<Category> listCategory = switch (field) {
            case "id" -> categoryRepository.findByID(new ObjectId(value));
            case "name" -> categoryRepository.findByName(value);
            case "description" -> categoryRepository.findByDesc(value);
            default -> throw new CategoryException("incorrect field");
        };

        if(listCategory.isEmpty()) {
            throw new CategoryException("Category doesn't exist");
        }
        return listCategory;
    }


    //get all categories
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    // update Category
    public String updateCategory(String id, Category category) {
        ObjectId objectId = new ObjectId(id);
        Optional<Category> optCategory = categoryRepository.findById(objectId);
        if(optCategory.isEmpty()) {
            throw new RuntimeException("given category doesn't exist");
        }
        Category categoryRec = optCategory.get();
        if (category.getName() != null)
            categoryRec.setName(category.getName());
        if (category.getDescription() != null)
            categoryRec.setDescription(category.getDescription());

        categoryRepository.save(categoryRec);
        return "{" +
                "\"message\":"+"Successfully updated category\",\n"+
                "\"data\":"+categoryRec+",\n"+
                "}";
    }

    // delete category by id
    public String deleteCategoryById(ObjectId id) {
        Optional<Category> optCategory = categoryRepository.findById(id);
        if(optCategory.isEmpty()) {
            throw new RuntimeException("Category id " + id + " doesn't exist");
        }
        categoryRepository.deleteById(id);
        return "{" +
                "\"message\":"+"Successfully deleted category\",\n"+
                "\"id\":"+id+",\n"+
                "}";
    }

    
}
