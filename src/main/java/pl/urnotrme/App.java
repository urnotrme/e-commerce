package pl.urnotrme;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import pl.urnotrme.productcatalog.HashMapProductStorage;
import pl.urnotrme.productcatalog.ProductCatalog;
import pl.urnotrme.sales.Sales;
import pl.urnotrme.sales.cart.CartStorage;
import pl.urnotrme.sales.offering.OfferCalculator;
import pl.urnotrme.sales.product.InMemoryProductDetailsProvider;


import java.math.BigDecimal;

@SpringBootApplication
public class App {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    ProductCatalog createNewProductCatalog() {
        ProductCatalog productCatalog = new ProductCatalog(new HashMapProductStorage());

        String productId1 = productCatalog.addProduct("My ebok", "NIce one");
        productCatalog.assignImage(productId1, "images/nice.jpeg");
        productCatalog.changePrice(productId1, BigDecimal.TEN);
        productCatalog.publishProduct(productId1);

        String productId2 = productCatalog.addProduct("New Ebook", "NIce one");
        productCatalog.assignImage(productId2, "images/nice.jpeg");
        productCatalog.changePrice(productId2, BigDecimal.valueOf(20.20));
        productCatalog.publishProduct(productId2);

        return productCatalog;
    }

    @Bean
    Sales createSales() {
        InMemoryProductDetailsProvider productDetails = new InMemoryProductDetailsProvider();
        return new Sales(new CartStorage(), productDetails, new OfferCalculator(productDetails));
    }
}
