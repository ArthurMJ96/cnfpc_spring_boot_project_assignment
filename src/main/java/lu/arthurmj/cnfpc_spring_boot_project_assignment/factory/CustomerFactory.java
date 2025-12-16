package lu.arthurmj.cnfpc_spring_boot_project_assignment.factory;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.enums.Role;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;

@Component
public class CustomerFactory {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /** Creates a custom user with specified roles. */
    public Customer createCustomUser(String email, String rawPassword, Set<Role> roles) {
        Customer user = new Customer();
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(rawPassword));
        user.setRoles(roles);
        return user;
    }

    /** Creates a standard Customer user. */
    public Customer createCustomer(String email, String rawPassword) {
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.CUSTOMER));
        return createCustomUser(email, rawPassword, roles);
    }

    /** Creates an administrative user with all roles. */
    public Customer createAdminUser(String email, String rawPassword) {
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.ADMIN, Role.EMPLOYEE, Role.CUSTOMER));
        return createCustomUser(email, rawPassword, roles);
    }

    /** Creates an administrative user with all roles. */
    public Customer createEmployeeUser(String email, String rawPassword) {
        Set<Role> roles = new HashSet<>(Arrays.asList(Role.EMPLOYEE, Role.CUSTOMER));
        return createCustomUser(email, rawPassword, roles);
    }

}