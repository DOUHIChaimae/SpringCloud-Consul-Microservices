package ma.enset.orderservice.web;

import ma.enset.orderservice.entities.Order;
import ma.enset.orderservice.models.Customer;
import ma.enset.orderservice.models.Product;
import ma.enset.orderservice.repositories.OrderRepository;
import ma.enset.orderservice.repositories.ProductItemRepository;
import ma.enset.orderservice.services.CustomerRestClientService;
import ma.enset.orderservice.services.InventoryRestClientService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderRestController {
    private OrderRepository orderRepository;
    private ProductItemRepository productItemRepository;
    private CustomerRestClientService customerRestClientService;
    private InventoryRestClientService inventoryRestClientService;

    public OrderRestController(OrderRepository orderRepository, ProductItemRepository productItemRepository, CustomerRestClientService customerRestClientService, InventoryRestClientService inventoryRestClientService) {
        this.orderRepository = orderRepository;
        this.productItemRepository = productItemRepository;
        this.customerRestClientService = customerRestClientService;
        this.inventoryRestClientService = inventoryRestClientService;
    }

    @GetMapping("/fullOrder/{id}")
    public Order getOrder(@PathVariable Long id) {
        Order order = orderRepository.findById(id).get();
        Customer customer = customerRestClientService.getCustomerById(order.getCustomerId());
        order.setCustomer(customer);
        order.getProductItems().forEach(
                productItem -> {
                    Product product = inventoryRestClientService.getProductById(productItem.getProductID());
                    productItem.setProduct(product);
                }
        );
        return order;

    }

}
