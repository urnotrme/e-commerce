package pl.urnotrme.sales;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.urnotrme.productcatalog.Product;
import pl.urnotrme.productcatalog.ProductCatalog;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class OfferAcceptanceHTTPTest {

    @Autowired
    ProductCatalog productCatalog;

    @Autowired
    TestRestTemplate http;

    @Test
    void itAllowsToAcceptOffer() {
        //Arrange
        //thereAreProducts

        String productId = thereIsExampleProduct();
        http.postForEntity(String.format("/api/add-to-cart/%s", productId), null, Object.class);
        http.postForEntity(String.format("/api/add-to-cart/%s", productId), null, Object.class);

        AcceptOffer acceptOffer = new  AcceptOffer("Kamila", "kamila@email.com");
        ResponseEntity<PaymentData> response = http.postForEntity(String.format("/api/accept-offer"), acceptOffer, PaymentData.class);


        //Assert
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        assertNotNull(response.getBody().getPaymentId());
        assertNotNull(response.getBody().getPaymentUrl());
    }

    private String thereIsExampleProduct() {
        return productCatalog.allPublishedProducts().stream()
                .findFirst()
                .map(Product::getId)
                .get();
    }
}
