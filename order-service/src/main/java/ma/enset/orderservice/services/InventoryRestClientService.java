package ma.enset.orderservice.services;

import ma.enset.orderservice.models.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.hateoas.PagedModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "inventory-service")
public interface InventoryRestClientService {
    @GetMapping(path = "/products")
    PagedModel<Product> allProducts(@RequestParam(name = "page") int page, @RequestParam(name = "size") int size);

    @GetMapping(path = "/products")
    PagedModel<Product> allProducts();

    @GetMapping(path = "/products/{id}")
    Product getProductById(@PathVariable Long id);
}
