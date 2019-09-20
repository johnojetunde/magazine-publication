package net.javaguides.springboot.tutorial.repository;

import net.javaguides.springboot.tutorial.entity.Customer;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findByName(String name);
}
