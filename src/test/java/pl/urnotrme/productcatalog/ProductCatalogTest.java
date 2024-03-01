package pl.urnotrme.productcatalog;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.List;

public class ProductCatalogTest {

    private static void assertListIsEmpty(List<Product> products) {
        assert  0 == products.size();
    }

    private ProductCatalog thereIsProductCatalog() {
        return new ProductCatalog(new HashMapProductStorage());
    }
    @Test
    void itExposeEmptyProductsList() {
        //Arrange
        ProductCatalog catalog = thereIsProductCatalog();
        //Act
        List<Product> products = catalog.allProducts();
        //Assert
        assertListIsEmpty(products);
    }

    @Test
    void itAllowsToAddProduct() {
        //Arrange
        ProductCatalog catalog = thereIsProductCatalog();
        //Act
        String productId = catalog.addProduct("LEGO set 1234", "flowers");
        //Assert
        List<Product> products = catalog.allProducts();
        assert 1 == products.size();
    }

    @Test
    void itAllowsToLoadProductDetails() {
        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("LEGO set 1234", "flowers");

        Product loadedProduct = catalog.loadById(productId);
        assert loadedProduct.getId().equals(productId);
        assert loadedProduct.getName().equals("LEGO set 1234");
    }
    @Test
    void itAllowsToChangePrice() {
        ProductCatalog catalog = thereIsProductCatalog();

        String productId = catalog.addProduct("LEGO set 1234", "flowers");

        catalog.changePrice(productId, BigDecimal.valueOf(399.99));
        Product loadedProduct = catalog.loadById(productId);
        assertEquals(BigDecimal.valueOf(399.99), loadedProduct.getPrice());
    }

    @Test
    void itAllowsToAssignImage(){
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("LEGO set 1234", "flowers");

        catalog.assignImage(productId, "foo/boo/flowers.jpeg");

        Product loadedProduct = catalog.loadById(productId);
        assertEquals("foo/boo/flowers.jpeg", loadedProduct.getImageKey());

    }

    @Test
    void itAllowsToPublishProduct() {
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("LEGO set 1234", "flowers");
        catalog.changePrice(productId, BigDecimal.valueOf(399.99));
        catalog.assignImage(productId, "flowers.jpeg");

        catalog.publishProduct(productId);

        List<Product> publishedProducts = catalog.allPublishedProducts();
        assertDoesNotThrow(() -> catalog.publishProduct(productId));
        assertEquals(1, publishedProducts.size());
    }

    @Test
    void productCantBePublishedWithoutImageAndPrice() {
        ProductCatalog catalog = thereIsProductCatalog();
        String productId = catalog.addProduct("LEGO set 1234", "flowers");

        assertThrows(
                ProductCantBePublishedException.class,
                () -> catalog.publishProduct(productId)
        );
    }






}
