package com.example.springbootecommerce.controller;

import com.example.springbootecommerce.exceptionHandler.IdException;
import com.example.springbootecommerce.exceptionHandler.ProductException;
import com.example.springbootecommerce.model.Product;
import com.example.springbootecommerce.model.PayloadValidation;
import com.example.springbootecommerce.service.ProductService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/products")
public class ProductController {
    private ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    // add a product
    @PostMapping("/add-product")
    public Product saveProduct(@RequestBody Product product) throws IdException {
        if(!PayloadValidation.payloadValProd(product)) {
            throw new IdException("ObjectId not defined");
        }
        return productService.createProduct(product);
    }

    @GetMapping("/getById/{id}")
    public Product getProductById(@PathVariable ObjectId id) throws ProductException {return productService.getProductById(id);}

    // get product by any field
    @PostMapping("/get")
    public List<Product> getProductByField(@RequestBody Map<String,Object> map) throws ProductException {
        return productService.getProductByField(map.get("field").toString(),map.get("value").toString());
    }

    // get all products
    @GetMapping("get-products")
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    //update category
    @PostMapping("/update/{id}")
    public String updateProduct(@RequestBody Product product, @PathVariable String id) {return productService.updateProduct(id,product);}

    //link product with Category id
    @PostMapping("/product-with-category")
    public String linkProdWithCategory(@RequestBody Map<String,Object> map) {
        return productService.linkProductWithCategory(map.get("ProductId").toString(),map.get("CategoryId").toString());
    }
    // remove product by id
    @PostMapping("/delete/{id}")
    public String deleteById(@PathVariable ObjectId id) {return productService.deleteProductById(id);}

}
