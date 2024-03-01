package pl.urnotrme.sales.reservation;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;


import java.math.BigDecimal;

@Entity
public class Reservation {

    @Id
    String id;
    BigDecimal total;
    String paymentId;


    public Reservation() {

    }

    public Reservation(String id, BigDecimal total, String paymentId) {
        this.id = id;
        this.total = total;
        this.paymentId = paymentId;
    }
}
