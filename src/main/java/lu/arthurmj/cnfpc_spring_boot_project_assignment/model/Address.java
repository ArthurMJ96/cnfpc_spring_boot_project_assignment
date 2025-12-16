package lu.arthurmj.cnfpc_spring_boot_project_assignment.model;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "addresses")
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @NotBlank(message = "Firstname is required")
    @Size(min = 1, max = 128, message = "Firstname must be between 1 and 128 characters")
    private String firstName;

    @NotBlank(message = "Lastname is required")
    @Size(min = 1, max = 256, message = "Lastname must be between 1 and 256 characters")
    private String lastName;

    @NotBlank(message = "Address is required")
    @Size(min = 1, max = 512, message = "Address must be between 1 and 512 characters")
    private String address;

    @NotBlank(message = "City is required")
    @Size(min = 1, max = 256, message = "City must be between 1 and 256 characters")
    private String city;

    @NotBlank(message = "Region is required")
    @Size(min = 1, max = 256, message = "Region must be between 1 and 256 characters")
    private String region;

    @NotBlank(message = "Postal Code is required")
    @Size(min = 1, max = 64, message = "Postal Code must be between 1 and 64 characters")
    private String postalCode;

    @NotBlank(message = "Country is required")
    @Size(min = 1, max = 128, message = "Country must be between 1 and 128 characters")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    public String toString() {
        return firstName + " " + lastName + ", " + address + "\n" + city + ", " + region + ", " + postalCode + ", "
                + country;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }
}
