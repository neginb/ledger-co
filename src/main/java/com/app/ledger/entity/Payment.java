package com.app.ledger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

@Entity(name = "PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;

    @Column(name = "AMOUNT_PAID")
    private double amountPaid;

    @Column(name = "EMIS_LEFT")
    private int emisLeft;

    public Payment() {
    }

    public Payment(double amountPaid, int emisLeft) {
        this.amountPaid = amountPaid;
        this.emisLeft = emisLeft;
    }

    public double getAmountPaid() {
        return amountPaid;
    }

    public int getEmisLeft() {
        return emisLeft;
    }

    @Override
    public String toString() {
        return "Payment{" +
                "amountPaid=" + amountPaid +
                ", emisLeft=" + emisLeft +
                '}';
    }
}
