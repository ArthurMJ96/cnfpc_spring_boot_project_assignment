package lu.arthurmj.cnfpc_spring_boot_project_assignment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;

public interface CustomerRepository extends JpaRepository<Customer, String> {

    Customer findByEmail(String email);

    List<Customer> findTop10By();
}
