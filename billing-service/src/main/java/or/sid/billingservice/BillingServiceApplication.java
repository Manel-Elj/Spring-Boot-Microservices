package or.sid.billingservice;

import or.sid.billingservice.entity.Bill;
import or.sid.billingservice.entity.ProductItem;
import or.sid.billingservice.mockups.Customer;
import or.sid.billingservice.repository.BillRepo;
import or.sid.billingservice.repository.ProductItemRepo;
import or.sid.billingservice.service.CustomerServiceClient;
import or.sid.billingservice.service.InventoryServiceClient;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;

import java.util.Date;

@SpringBootApplication
@EnableFeignClients
public class BillingServiceApplication{
	public static void main(String[] args) {
		SpringApplication.run(BillingServiceApplication.class, args);
	}

	@Bean
	CommandLineRunner start(
			BillRepo billRepository,
			ProductItemRepo productItemRepository,
			InventoryServiceClient inventoryServiceClient,
			CustomerServiceClient customerServiceClient) {
		return args -> {
			Customer customer= customerServiceClient.findCustomerById(1L);
			System.out.print(customer);
			Bill bill= Bill.builder()
					.customerID(customer.getId())
					.billingDate(new Date())
					.customer(customer)
					.build();
			billRepository.save(bill);
			inventoryServiceClient.findAll().getContent().forEach( p-> {
			productItemRepository.save(
				ProductItem.builder()
					.price(p.getPrice())
					.product(inventoryServiceClient.findProductById(p.getId()))
					.productID(p.getId())
					.quantity((int)(1+Math.random()*1000))
					.bill(bill)
					.build()
			);
			});
		};
	}}