package lu.arthurmj.cnfpc_spring_boot_project_assignment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.enums.Role;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CustomerService;

@Configuration
public class SecurityConfig {

  @Bean
  public BCryptPasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public UserDetailsService userDetailsService(CustomerService customerService) {
    return customerService;
  }

  @Bean
  public DaoAuthenticationProvider authenticationProvider(UserDetailsService userDetailsService) {
    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
    provider.setPasswordEncoder(passwordEncoder());
    return provider;
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .authorizeHttpRequests(auth -> auth
            .requestMatchers("/css/**", "/js/**", "/images/**").permitAll()
            .requestMatchers("/", "/products/**", "/product/**", "/category/**", "/register", "/error").permitAll()
            .requestMatchers("/cart/**", "/orders/**", "/profile").hasAuthority(Role.CUSTOMER.name())
            .requestMatchers("/admin/**").hasAuthority(Role.EMPLOYEE.name())
            .anyRequest().authenticated())
        .formLogin(form -> form
            .loginPage("/login")
            .permitAll()
            .usernameParameter("email")
            .defaultSuccessUrl("/", true)
            .failureUrl("/login?error=true"))
        .logout(logout -> logout
            .logoutSuccessUrl("/?logout=true")
            .logoutUrl("/logout")
            .invalidateHttpSession(true)
            .clearAuthentication(true).permitAll())
        .exceptionHandling(eh -> eh
            .accessDeniedPage("/403"));
    return http.build();
  }
}