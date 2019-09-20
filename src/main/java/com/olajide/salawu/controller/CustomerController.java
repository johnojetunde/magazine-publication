package com.olajide.salawu.controller;

import com.olajide.salawu.entity.Customer;
import com.olajide.salawu.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/customers/")
public class CustomerController {

    private static final String staticFileDirectory = "customer/";
    private final CustomerRepository customerRepository;

    @Autowired
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @GetMapping("signup")
    public String showSignUpForm(Customer customer) {
        return staticFileDirectory + "add-customer";
    }

    @GetMapping("list")
    public String showUpdateForm(Model model) {
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }

    @PostMapping("add")
    public String addCustomer(@Valid Customer customer, BindingResult result, Model model) {
        if (result.hasErrors()) {
            return staticFileDirectory + "add-customer";
        }

        customerRepository.save(customer);
        return "redirect:list";
    }

    @GetMapping("edit/{id}")
    public String showUpdateForm(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        model.addAttribute("customer", customer);
        return staticFileDirectory + "update-customer";
    }

    @PostMapping("update/{id}")
    public String updateCustomer(@PathVariable("id") long id, @Valid Customer customer, BindingResult result,
                                 Model model) {
        if (result.hasErrors()) {
            customer.setId(id);
            return staticFileDirectory + "update-customer";
        }

        customerRepository.save(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }

    @GetMapping("delete/{id}")
    public String deleteCustomer(@PathVariable("id") long id, Model model) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid customer Id:" + id));
        customerRepository.delete(customer);
        model.addAttribute("customers", customerRepository.findAll());
        return "index";
    }
}
