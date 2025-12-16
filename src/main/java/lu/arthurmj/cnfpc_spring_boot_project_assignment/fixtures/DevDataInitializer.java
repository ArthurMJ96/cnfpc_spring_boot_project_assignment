package lu.arthurmj.cnfpc_spring_boot_project_assignment.fixtures;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.CategoryFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.CustomerFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.ProductFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Product;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.CategoryRepository;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.CustomerRepository;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CategoryService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.CustomerService;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.ProductService;

/**
 * Initializes default data such as an admin user if no users exist in the
 * database. Only for presentation purposes.
 */
@Configuration
public class DevDataInitializer {
	@Autowired
	private CustomerFactory customerFactory;

	@Autowired
	private ProductFactory productFactory;

	@Autowired
	private CategoryFactory categoryFactory;

	@Value("${app.load-sample-data}")
	private Boolean loadSampleData;

	@Bean
	public CommandLineRunner initData(
			CustomerRepository customerRepository,
			CustomerService customerService,
			CategoryRepository categoryRepository,
			CategoryService categoryService,
			ProductService productService) {
		return args -> {
			if (!loadSampleData) {
				return;
			}

			List<Customer> customers = customerService.getTop10();
			if (customers.size() == 0) {
				System.out.println("Inserting sample data...");
				// Create default admin user
				Customer admin = customerFactory.createAdminUser(
						"admin@admin.com",
						"admin@admin.com");
				customerService.save(admin);

				// Create default employee user
				Customer employee = customerFactory.createEmployeeUser(
						"emp@emp.com",
						"emp@emp.com");
				customerService.save(employee);

				// Create default customer user
				Customer customer = customerFactory.createCustomer(
						"cust@cust.com",
						"cust@cust.com");
				customerService.save(customer);

				// Create categories
				Category electronics = categoryFactory.createCategory("Electronics");
				Category books = categoryFactory.createCategory("Books");
				Category apple = categoryFactory.createCategory("Apple");
				Category fashion = categoryFactory.createCategory("Fashion");
				Category sports = categoryFactory.createCategory("Sports");
				Category home = categoryFactory.createCategory("Home");
				categoryService.save(electronics);
				categoryService.save(books);
				categoryService.save(apple);
				categoryService.save(fashion);
				categoryService.save(sports);
				categoryService.save(home);

				// Create products
				Product product1 = productFactory.createProduct(
						"Ipad Pro",
						"Revolutionary iPad with M1 chip.",
						59999, new HashSet<>(Arrays.asList(electronics, apple)),
						Arrays.asList("https://placehold.net/600x800.png"));
				Product product2 = productFactory.createProduct(
						"Harry Potter and the Philosopher's Stone",
						"Harry Potter's first adventure in the wizarding world.",
						2999, new HashSet<>(Arrays.asList(books)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product3 = productFactory.createProduct(
						"MacBook Pro",
						"Powerful laptop designed for professionals.",
						199999, new HashSet<>(Arrays.asList(electronics, apple)),
						Arrays.asList("https://placehold.net/800x600.png"));
				Product product4 = productFactory.createProduct(
						"The Lord of the Rings: The Fellowship of the Ring",
						"An epic fantasy novel by J.R.R. Tolkien.",
						3999, new HashSet<>(Arrays.asList(books)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product5 = productFactory.createProduct(
						"Running Shoes",
						"High-quality running shoes for all terrains.",
						7999, new HashSet<>(Arrays.asList(sports, fashion)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product6 = productFactory.createProduct(
						"Blender",
						"Powerful kitchen blender with multiple speed settings.",
						4999, new HashSet<>(Arrays.asList(home, electronics)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product7 = productFactory.createProduct(
						"Smartwatch",
						"Feature-rich smartwatch with health tracking capabilities.",
						14999, new HashSet<>(Arrays.asList(electronics, fashion)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product8 = productFactory.createProduct(
						"Cookbook",
						"Delicious recipes from around the world.",
						2599, new HashSet<>(Arrays.asList(books, home)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product9 = productFactory.createProduct(
						"Digital Camera",
						"High-resolution digital camera for stunning photos.",
						89999, new HashSet<>(Arrays.asList(electronics)),
						Arrays.asList("https://placehold.net/600x600.png"));
				Product product10 = productFactory.createProduct(
						"Yoga Mat",
						"Non-slip yoga mat for all types of exercises.",
						2999, new HashSet<>(Arrays.asList(sports, home)),
						Arrays.asList("https://placehold.net/600x600.png"));
				productService.save(product1);
				productService.save(product2);
				productService.save(product3);
				productService.save(product4);
				productService.save(product5);
				productService.save(product6);
				productService.save(product7);
				productService.save(product8);
				productService.save(product9);
				productService.save(product10);
			}
		};
	}

}
