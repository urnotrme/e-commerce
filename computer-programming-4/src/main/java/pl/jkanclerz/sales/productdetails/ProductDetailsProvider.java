package pl.jkanclerz.sales.productdetails;

import pl.jkanclerz.sales.productdetails.ProductDetails;

import java.util.Optional;

public interface ProductDetailsProvider {
    public Optional<ProductDetails> load(String productId);
}
