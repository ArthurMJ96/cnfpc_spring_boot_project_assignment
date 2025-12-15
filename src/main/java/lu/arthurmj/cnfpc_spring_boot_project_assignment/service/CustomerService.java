package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.enums.Role;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.exception.CustomerEmailExistsException;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.CustomerRepository;

@Service
public class CustomerService implements UserDetailsService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Customer customer = customerRepository.findByEmail(email);
        if (customer == null) {
            throw new UsernameNotFoundException("Customer not found");
        }
        return customer;
    }

    public void create(Customer customer) throws CustomerEmailExistsException {
        if (customerRepository.findByEmail(customer.getEmail()) != null) {
            throw new CustomerEmailExistsException("Account with this email already exists");
        }
        customer.setPassword(passwordEncoder.encode(customer.getPassword()));
        customer.addRole(Role.CUSTOMER);
        customerRepository.save(customer);
    }

    public void save(Customer customer) {
        customerRepository.save(customer);
    }

    public Customer findById(String id) {
        return customerRepository.findById(id).orElse(null);
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public List<Customer> getTop10() {
        return customerRepository.findTop10By();
    }
}