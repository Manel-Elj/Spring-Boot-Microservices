package or.sid.billingservice.restcontroller;

import or.sid.billingservice.entity.Bill;
import or.sid.billingservice.repository.BillRepo;
import or.sid.billingservice.repository.ProductItemRepo;
import or.sid.billingservice.service.CustomerServiceClient;
import or.sid.billingservice.service.InventoryServiceClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
class BillRestController{
    @Autowired
    private BillRepo billRepository;
    @Autowired private ProductItemRepo productItemRepository;
    @Autowired private CustomerServiceClient customerServiceClient;
    @Autowired private InventoryServiceClient inventoryServiceClient;

    @GetMapping("/bills/full/{id}")
    Bill getBill(@PathVariable(name="id") Long id) {
        Bill bill=billRepository.findById(id).get();
        bill.setCustomer(customerServiceClient.findCustomerById(bill.getCustomerID()));
        bill.setProductItems(productItemRepository.findByBillId(id));
        bill.getProductItems().forEach(pi -> {
            pi.setProduct(inventoryServiceClient.findProductById(pi.getProductID()));
        });
        return bill;
    }
}
