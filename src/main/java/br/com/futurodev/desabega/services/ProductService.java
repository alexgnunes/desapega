package br.com.futurodev.desabega.services;

import br.com.futurodev.desabega.exception.PersonNotFoundException;
import br.com.futurodev.desabega.exception.ProductNotFoundException;
import br.com.futurodev.desabega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desabega.models.Person;
import br.com.futurodev.desabega.models.Product;
import br.com.futurodev.desabega.models.transport.CreateProductForm;
import br.com.futurodev.desabega.models.transport.ProductDto;
import br.com.futurodev.desabega.models.transport.UpdateProductForm;
import br.com.futurodev.desabega.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final PersonService personService;
    private final ProductRepository productRepository;

    ProductService(PersonService personService, ProductRepository productRepository) {
        this.personService = personService;
        this.productRepository = productRepository;
    }

    @Transactional
    public ProductDto registerProduct(CreateProductForm createProductForm, UserDetails userInSession) throws UserAlreadyRegisteredException, PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        Product product = new Product(createProductForm);
        product.setOwner(person);

        Product persistenceProduct = this.productRepository.save(product);
        return new ProductDto(persistenceProduct);
    }

    public Page<ProductDto> listAllByOwner(Pageable pageable, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        return this.productRepository.findAllByOwnerPersonIdAndDeletedFalse(person.getPersonId(), pageable).map(ProductDto::new);
    }

    public Page<ProductDto> findAvailableProducts(Pageable pageable, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        return this.productRepository.findByOwnerPersonIdNotAndDeletedFalseAndSoldFalse(person.getPersonId(), pageable).map(ProductDto::new);
    }

    public ProductDto findProductsById(Long productId, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        Product product = this.productRepository.findByProductIdAndDeletedFalseAndOwnerPersonId(productId, person.getPersonId())
                .orElseThrow(() -> new ProductNotFoundException(productId));

        return new ProductDto(product);
    }

    public ProductDto updateProduct(Long id, @Valid UpdateProductForm form, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        Product productForUpdate = this.productRepository.findByProductIdAndDeletedFalseAndOwnerPersonId(id,person.getPersonId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        productForUpdate.updateAvailableAttributes(form);
        return new ProductDto(productForUpdate);
    }
}
