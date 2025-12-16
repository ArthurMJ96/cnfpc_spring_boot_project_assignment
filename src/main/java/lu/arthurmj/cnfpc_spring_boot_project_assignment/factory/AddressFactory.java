package lu.arthurmj.cnfpc_spring_boot_project_assignment.factory;

import org.springframework.stereotype.Component;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Address;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;

@Component
public class AddressFactory {

    /**
     * Creates an Address instance with the provided details.
     */
    public Address createAddress(
            String firstName,
            String lastName,
            String street,
            String city,
            String state,
            String zipCode,
            String country,
            Customer customer) {
        Address address = new Address();
        address.setFirstName(firstName);
        address.setLastName(lastName);
        address.setAddress(street);
        address.setCity(city);
        address.setRegion(state);
        address.setPostalCode(zipCode);
        address.setCountry(country);
        address.setCustomer(customer);
        return address;
    }
}
