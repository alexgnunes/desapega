package br.com.futurodev.desapega.services;

import br.com.futurodev.desapega.exception.InsufficientBalanceException;
import br.com.futurodev.desapega.exception.PersonNotFoundException;
import br.com.futurodev.desapega.exception.ProductNotFoundException;
import br.com.futurodev.desapega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desapega.models.Person;
import br.com.futurodev.desapega.models.Product;
import br.com.futurodev.desapega.models.transport.CreateProductForm;
import br.com.futurodev.desapega.models.transport.ProductDto;
import br.com.futurodev.desapega.models.transport.UpdateProductForm;
import br.com.futurodev.desapega.repositories.PersonRepository;
import br.com.futurodev.desapega.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ProductService {

    private final PersonService personService;
    private final ProductRepository productRepository;
    private final PersonRepository personRepository;

    ProductService(PersonService personService, ProductRepository productRepository, PersonRepository personRepository) {
        this.personService = personService;
        this.productRepository = productRepository;
        this.personRepository = personRepository;
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

    @Transactional
    public ProductDto updateProduct(Long id, @Valid UpdateProductForm form, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        Product productForUpdate = this.productRepository.findByProductIdAndDeletedFalseAndOwnerPersonId(id,person.getPersonId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        productForUpdate.updateAvailableAttributes(form);
        return new ProductDto(productForUpdate);
    }

    @Transactional
    public void deleteProduct(Long id, UserDetails userInSession) throws PersonNotFoundException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        Product response = this.productRepository.findByProductIdAndDeletedFalseAndOwnerPersonId(id,person.getPersonId())
                .orElseThrow(() -> new ProductNotFoundException(id));

        response.markAsDeleted();
    }

    @Transactional
    public void buyProduct(Long id, UserDetails userInSession) throws PersonNotFoundException, InsufficientBalanceException {
        Person person = this.personService.getSinglePerson((userInSession.getUsername()));
        Product product = this.productRepository.findByProductIdAndDeletedFalseAndSoldFalseAndOwnerPersonIdNot(id,person.getPersonId())
                .orElseThrow(() -> new ProductNotFoundException(id));
        person.getWallet().getBalance();

        validateBalance (person, product);

        product.markAsSold();
        person.getWallet().setBalance(person.getWallet().getBalance().subtract(product.getPrice()));
        product.getOwner().getWallet().setBalance(product.getOwner().getWallet().getBalance().add(product.getPrice()));
        product.setOwner(person);

        productRepository.save(product);
        personRepository.save(person);
    }

    private void validateBalance(Person person, Product product) throws InsufficientBalanceException {
        BigDecimal balancePerson = person.getWallet().getBalance();

        if (balancePerson.compareTo(product.getPrice()) < 0) {
            throw new InsufficientBalanceException(balancePerson);
        }
    }
}
