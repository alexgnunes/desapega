package br.com.futurodev.desapega.controllers;

import br.com.futurodev.desapega.exception.InsufficientBalanceException;
import br.com.futurodev.desapega.exception.PersonNotFoundException;
import br.com.futurodev.desapega.exception.ProductNotFoundException;
import br.com.futurodev.desapega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desapega.models.transport.CreateProductForm;
import br.com.futurodev.desapega.models.transport.ProductDto;
import br.com.futurodev.desapega.models.transport.UpdateProductForm;
import br.com.futurodev.desapega.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<ProductDto> registerProduct(@RequestBody @Valid CreateProductForm createProductForm,
                                                      UriComponentsBuilder uriComponentsBuilder,
                                                      @AuthenticationPrincipal UserDetails userInSession) throws UserAlreadyRegisteredException, PersonNotFoundException {
        ProductDto response = this.productService.registerProduct(createProductForm, userInSession);

        URI uri = uriComponentsBuilder
                .path("/products/{id}")
                .buildAndExpand(response.id())
                .toUri();

        return ResponseEntity.created(uri).body(response);
    }

    @GetMapping
    public ResponseEntity<Page<ProductDto>> listAllByOwner(@PageableDefault(size = 10) Pageable pageable,
                                                           @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        Page<ProductDto> list = this.productService.listAllByOwner(pageable, userInSession);
        return ResponseEntity.ok(list);
    }

    @GetMapping("/available")
    public ResponseEntity<Page<ProductDto>> findAvailableProducts(@PageableDefault(size = 10) Pageable pageable,
                                                                  @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        Page<ProductDto> list = this.productService.findAvailableProducts(pageable, userInSession);
        return list.isEmpty() ? ResponseEntity.noContent().build() : ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDto> findProductsById(@PathVariable("id") Long productId,
                                                       @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        ProductDto response = this.productService.findProductsById(productId, userInSession);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDto> updateProduct(@PathVariable("id") Long id,
                                                 @RequestBody @Valid UpdateProductForm form,
                                                 @AuthenticationPrincipal UserDetails userInSession) throws ProductNotFoundException, PersonNotFoundException {
        ProductDto response = this.productService.updateProduct(id, form, userInSession);
        return ResponseEntity.ok(response);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<ProductDto> deleteProduct(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException {
        this.productService.deleteProduct(id,  userInSession);
        return  ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/buy")
    public ResponseEntity<ProductDto> buyProduct(@PathVariable("id") Long id, @AuthenticationPrincipal UserDetails userInSession) throws PersonNotFoundException, InsufficientBalanceException {
        this.productService.buyProduct(id,  userInSession);
        return  ResponseEntity.ok().build();
    }
}

