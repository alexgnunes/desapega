package br.com.futurodev.desabega.services;

import br.com.futurodev.desabega.exception.PersonNotFoundException;
import br.com.futurodev.desabega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desabega.models.Person;
import br.com.futurodev.desabega.models.Product;
import br.com.futurodev.desabega.models.transport.CreateProductForm;
import br.com.futurodev.desabega.models.transport.PersonDto;
import br.com.futurodev.desabega.models.transport.ProductDto;
import br.com.futurodev.desabega.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

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

        Product persistenceProduct= this.productRepository.save(product);
        return new ProductDto(persistenceProduct);
    }

}
