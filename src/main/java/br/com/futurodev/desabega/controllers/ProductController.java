package br.com.futurodev.desabega.controllers;

import br.com.futurodev.desabega.exception.PersonNotFoundException;
import br.com.futurodev.desabega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desabega.models.transport.CreatePersonForm;
import br.com.futurodev.desabega.models.transport.CreateProductForm;
import br.com.futurodev.desabega.models.transport.PersonDto;
import br.com.futurodev.desabega.models.transport.ProductDto;
import br.com.futurodev.desabega.services.PersonService;
import br.com.futurodev.desabega.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService){
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> registerProduct(@RequestBody @Valid CreateProductForm createProductForm,
                                                     UriComponentsBuilder uriComponentsBuilder,
                                                     @AuthenticationPrincipal UserDetails userInSession) throws UserAlreadyRegisteredException, PersonNotFoundException {
        ProductDto response = this.productService.registerProduct(createProductForm,userInSession);

        URI uri = uriComponentsBuilder
                .path("/products/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }
}

