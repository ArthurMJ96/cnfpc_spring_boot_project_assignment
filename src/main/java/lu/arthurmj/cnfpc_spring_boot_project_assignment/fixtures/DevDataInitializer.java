package lu.arthurmj.cnfpc_spring_boot_project_assignment.fixtures;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.AddressFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.CategoryFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.CustomerFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.factory.ProductFactory;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Category;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.model.Customer;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.CategoryRepository;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.repository.CustomerRepository;
import lu.arthurmj.cnfpc_spring_boot_project_assignment.service.AddressService;
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

	@Autowired
	private AddressFactory addressFactory;

	@Value("${app.load-sample-data}")
	private Boolean loadSampleData;

	@Bean
	public CommandLineRunner initData(
			CustomerRepository customerRepository,
			CustomerService customerService,
			CategoryRepository categoryRepository,
			CategoryService categoryService,
			ProductService productService,
			AddressService addressService) {
		return args -> {
			if (!loadSampleData) {
				return;
			}

			List<Customer> customers = customerService.getTop10();
			if (customers.size() == 0) {
				System.out.println("Inserting sample data...");
				// #region Users
				// Create default admin user
				Customer admin = customerFactory.createAdminUser(
						"admin@admin.com",
						"admin@admin.com");
				customerService.save(admin);
				addressService.save(addressFactory.createAddress(
						"Mr. Admin", "User",
						"127001 Admin Street", "Admin City",
						"Admin State", "L-1337",
						"Adminland", admin));
				addressService.save(addressFactory.createAddress(
						"Mr. Admin", "User",
						"::1 Badmin Street", "Badmin City",
						"Badmin Federation", "L-7331",
						"Badminland", admin));

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
				// #endregion

				// #region Create categories
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
				// #endregion

				// #region Create products
				productService.save(productFactory.createProduct(
						"Ipad Pro",
						"Revolutionary iPad with M1 chip.",
						59999, new HashSet<>(Arrays.asList(electronics, apple)),
						Arrays.asList("/images/product/ipadpro.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Harry Potter and the Philosopher's Stone",
						"Harry Potter's first adventure in the wizarding world.",
						2999, new HashSet<>(Arrays.asList(books)),
						Arrays.asList("/images/product/potter1.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"MacBook Pro",
						"Powerful laptop designed for professionals.",
						199999, new HashSet<>(Arrays.asList(electronics, apple)),
						Arrays.asList("/images/product/mbpro.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"The Lord of the Rings: The Fellowship of the Ring",
						"An epic fantasy novel by J.R.R. Tolkien.",
						3999, new HashSet<>(Arrays.asList(books)),
						Arrays.asList("/images/product/lotr1.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Running Shoes",
						"High-quality running shoes for all terrains.",
						7999, new HashSet<>(Arrays.asList(sports, fashion)),
						Arrays.asList("/images/product/shoes.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Blender",
						"Powerful kitchen blender with multiple speed settings.",
						4999, new HashSet<>(Arrays.asList(home, electronics)),
						Arrays.asList("/images/product/blender.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Smartwatch",
						"Feature-rich smartwatch with health tracking capabilities.",
						14999, new HashSet<>(Arrays.asList(electronics, fashion)),
						Arrays.asList("/images/product/watch.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Cookbook",
						"Delicious recipes from around the world.",
						2599, new HashSet<>(Arrays.asList(books, home)),
						Arrays.asList("/images/product/cookbooklmao.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Digital Camera",
						"High-resolution digital camera for stunning photos.",
						89999, new HashSet<>(Arrays.asList(electronics)),
						Arrays.asList("/images/product/camera.jpg"), rndNumber()));
				productService.save(productFactory.createProduct(
						"Yoga Mat",
						"Non-slip yoga mat for all types of exercises.",
						2999, new HashSet<>(Arrays.asList(sports, home)),
						Arrays.asList("/images/product/yogamat.jpg"), rndNumber()));
				// #endregion
			}
		};
	}

	private int rndNumber() {
		return (int) (5 + Math.random() * 45);
	}
}
