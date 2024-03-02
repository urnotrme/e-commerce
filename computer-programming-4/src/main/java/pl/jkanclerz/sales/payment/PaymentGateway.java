package pl.jkanclerz.sales.payment;

public interface PaymentGateway {
    PaymentData register(RegisterPaymentRequest request);
}
