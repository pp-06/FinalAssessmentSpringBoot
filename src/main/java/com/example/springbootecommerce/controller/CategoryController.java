package com.example.springbootecommerce.controller;


import com.example.springbootecommerce.exceptionHandler.CategoryException;
import com.example.springbootecommerce.exceptionHandler.IdException;
import com.example.springbootecommerce.model.Category;
import com.example.springbootecommerce.model.PayloadValidation;
import com.example.springbootecommerce.service.CategoryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private CategoryService categoryService;
    @Autowired
    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    // add a category
    @PostMapping("/add-category")
    public Category saveCategory(@RequestBody Category category) throws IdException {
        if(!PayloadValidation.payloadValCat(category)) {
            throw new IdException("ObjectId not defined");
        }
        return categoryService.createCategory(category);
    }

    //get category by id
    @GetMapping("/getById/{id}")
    public Category getCategoryById(@PathVariable ObjectId id) throws CategoryException {return categoryService.getCategoryById(id);}

    // get category by any field
    @PostMapping("/get")
    public List<Category> getCategoryByField(@RequestBody Map<String,Object> map) throws CategoryException {
        return categoryService.getCategoryByField(map.get("field").toString(),map.get("value").toString());
    }

    // get all categories
    @GetMapping("get-categories")
    public List<Category> getAllCategories() {
        return categoryService.getAllCategories();
    }

    //update category
    @PostMapping("/update/{id}")
    public String updateCategory(@RequestBody Category category, @PathVariable String id) {return categoryService.updateCategory(id,category);}

    // remove category by id
    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable ObjectId id) {return categoryService.deleteCategoryById(id);}

}
