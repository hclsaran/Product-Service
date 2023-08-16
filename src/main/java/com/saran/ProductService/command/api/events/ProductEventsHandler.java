package com.saran.ProductService.command.api.events;

import org.axonframework.config.ProcessingGroup;
import org.axonframework.eventhandling.EventHandler;
import org.axonframework.messaging.interceptors.ExceptionHandler;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.saran.ProductService.command.api.data.Product;
import com.saran.ProductService.command.api.data.ProductRepository;

@Component
@ProcessingGroup("product")
public class ProductEventsHandler {
   
    private ProductRepository productRepository;

    public ProductEventsHandler(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @EventHandler
    public void on(ProductCreatedEvent event) throws Exception {
        Product product =new Product();
        BeanUtils.copyProperties(event,product);
        productRepository.save(product);
        System.out.println("calling event handler");
     /*
      * Whenever the ProductOrder is trying to create. if there is any issue then it must rollback
      * both in eventstore and database as well 
      */
     //  throw new Exception("Exception Occurred");
        
    }

    @ExceptionHandler
    public void handle(Exception exception) throws Exception {
        throw exception;
    }
}
