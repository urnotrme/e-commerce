package pl.jkanclerz.sales;

import pl.jkanclerz.sales.cart.Cart;
import pl.jkanclerz.sales.cart.CartStorage;
import pl.jkanclerz.sales.offering.EveryNItemLineDiscountPolicy;
import pl.jkanclerz.sales.offering.Offer;
import pl.jkanclerz.sales.offering.OfferCalculator;
import pl.jkanclerz.sales.offering.TotalDiscountPolicy;
import pl.jkanclerz.sales.payment.PaymentData;
import pl.jkanclerz.sales.payment.PaymentGateway;
import pl.jkanclerz.sales.payment.RegisterPaymentRequest;
import pl.jkanclerz.sales.productdetails.NoSuchProductException;
import pl.jkanclerz.sales.productdetails.ProductDetails;
import pl.jkanclerz.sales.productdetails.ProductDetailsProvider;
import pl.jkanclerz.sales.reservation.OfferAcceptanceRequest;
import pl.jkanclerz.sales.reservation.Reservation;
import pl.jkanclerz.sales.reservation.ReservationDetails;
import pl.jkanclerz.sales.reservation.InMemoryReservationStorage;

import java.math.BigDecimal;
import java.util.Optional;

public class Sales {
    private CartStorage cartStorage;
    private ProductDetailsProvider productDetailsProvider;
    private final OfferCalculator offerCalculator;
    private PaymentGateway paymentGateway;
    private InMemoryReservationStorage reservationStorage;

    public Sales(
            CartStorage cartStorage,
            ProductDetailsProvider productDetails,
            OfferCalculator offerCalculator,
            PaymentGateway paymentGateway,
            InMemoryReservationStorage reservationStorage
        ) {
        this.cartStorage = cartStorage;
        this.productDetailsProvider = productDetails;
        this.offerCalculator = offerCalculator;
        this.paymentGateway = paymentGateway;
        this.reservationStorage = reservationStorage;
    }

    public void addToCart(String customerId, String productId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        ProductDetails product = loadProductDetails(productId)
                .orElseThrow(() -> new NoSuchProductException());

        customerCart.add(product.getId());

        cartStorage.addForCustomer(customerId, customerCart);
    }

    private Optional<ProductDetails> loadProductDetails(String productId) {
        return productDetailsProvider.load(productId);
    }

    private Optional<Cart> loadCartForCustomer(String customerId) {
        return cartStorage.load(customerId);
    }

    public Offer getCurrentOffer(String customerId) {
        Cart customerCart = loadCartForCustomer(customerId)
                .orElse(Cart.empty());

        Offer offer = this.offerCalculator.calculateOffer(
                customerCart.getCartItems(),
                new TotalDiscountPolicy(BigDecimal.valueOf(500), BigDecimal.valueOf(50)),
                new EveryNItemLineDiscountPolicy(5)
        );

        return offer;
    }

    public ReservationDetails acceptOffer(String customerId, OfferAcceptanceRequest request) {
        Offer offer = this.getCurrentOffer(customerId);

        PaymentData payment = paymentGateway.register(RegisterPaymentRequest.of(request, offer));

        Reservation reservation = Reservation.of(request, offer, payment);

        reservationStorage.save(reservation);

        return new ReservationDetails(reservation.getId(), reservation.getPaymentUrl());
    }
}
