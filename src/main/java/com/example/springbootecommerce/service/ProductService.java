package com.example.springbootecommerce.service;

import com.example.springbootecommerce.exceptionHandler.ProductException;
import com.example.springbootecommerce.model.Category;
import com.example.springbootecommerce.model.Product;
import com.example.springbootecommerce.repository.CategoryRepository;
import com.example.springbootecommerce.repository.ProductRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    private CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository) { this.productRepository = productRepository;}

    // add product
    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    //get product by id
    public Product getProductById(ObjectId id) throws ProductException{
        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isEmpty() || optProduct == null) {
            throw new ProductException("Product doesn't exist");
        }
        return optProduct.orElseGet(optProduct::get);
    }

    //get product by any field
    public List<Product> getProductByField(String field, String value) throws ProductException {
        List<Product> listProduct = switch (field) {
            case "id" -> productRepository.findByID(new ObjectId(value));
            case "name" -> productRepository.findByName(value);
            case "description" -> productRepository.findByDesc(value);
            case "price" -> productRepository.findByPrice(Integer.parseInt(value));
            default -> throw new ProductException("incorrect field");
        };

        if(listProduct.isEmpty()) {
            throw new ProductException("Product doesn't exist");
        }
        return listProduct;
    }

    //get all products
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // update Product
    public String updateProduct(String id, Product product) {
        ObjectId objectId = new ObjectId(id);
        Optional<Product> optProduct = productRepository.findById(objectId);
        if(optProduct.isEmpty()) {
            throw new RuntimeException("given product doesn't exist");
        }
        Product productRec = optProduct.get();
        if (product.getName() != null)
            productRec.setName(product.getName());
        if (product.getDescription() != null)
            productRec.setDescription(product.getDescription());
        if (product.getPrice() != 0)
            productRec.setPrice(product.getPrice());

        productRepository.save(productRec);
        return "{" +
                "\"message\":"+"Successfully updated product\",\n"+
                "\"data\":"+productRec+",\n"+
                "}";
    }

    // link product with category id
    public String linkProductWithCategory(String prodId, String categoryId) {
        Optional<Product> optProduct = productRepository.findById(new ObjectId(prodId));
        if(optProduct.isEmpty()) {
            throw new RuntimeException("Product id: " + prodId + "doesn't exist");
        }
        Optional<Category> optCategory = categoryRepository.findById(new ObjectId(categoryId));
        if(optCategory.isEmpty()) {
            throw new RuntimeException("Category id: " + categoryId + "doesn't exist");
        }
        Product productRec = optProduct.get();
        productRec.setCategory(optCategory.get());
        productRepository.save(productRec);
        return "{" +
                "\"message\":"+"Successfully linked product with category id"+
                "\"data\": "+productRec+
                "}";
    }

    // delete product by id
    public String deleteProductById(ObjectId id) {
        Optional<Product> optProduct = productRepository.findById(id);
        if(optProduct.isEmpty()) {
            throw new RuntimeException("Product id " + id + " doesn't exist");
        }
        productRepository.deleteById(id);
        return "{" +
                "\"message\":"+"Successfully deleted product\",\n"+
                "\"id\":"+id+",\n"+
                "}";
    }


}

