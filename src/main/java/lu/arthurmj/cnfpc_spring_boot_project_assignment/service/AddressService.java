package lu.arthurmj.cnfpc_spring_boot_project_assignment.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Address;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.AddressRepository;

@Service
public class AddressService {

    @Autowired
    AddressRepository addressRepository;

    public void save(Address address) {
        addressRepository.save(address);
    }

    public Address findById(Long id) {
        return id == null ? null : addressRepository.findById(id).orElse(null);
    }

    public List<Address> findForCustomerId(String customerId) {
        return customerId == null ? List.of() : addressRepository.findByCustomer_IdOrderByIdAsc(customerId);
    }

}
