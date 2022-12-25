package com.app.ledger.entity;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.List;

@Entity(name = "LOAN")
public class Loan {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name = "native", strategy = "native")
    @Column(name = "ID")
    private Long id;

    @Column(name = "BANK")
    private String bank;

    @Column(name = "PRINCIPAL")
    private double principal;

    @Column(name = "INTEREST_AMOUNT")
    private double interestAmount;

    @Column(name = "AMOUNT")
    private double amount;

    @Column(name = "EMI_MONTHS")
    private int emiMonths;

    @Column(name = "EMI_AMOUNT")
    private int emiAmount;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name="LOAN_ID")
    private List<Payment> paymentList;

    public Loan() {
    }

    public Loan(double principal, double interestAmount, double amount, int emiMonths, int emiAmount, List<Payment> paymentList) {
        this.principal = principal;
        this.interestAmount = interestAmount;
        this.amount = amount;
        this.emiMonths = emiMonths;
        this.emiAmount = emiAmount;
        this.paymentList = paymentList;
    }

    public Loan(String bank, double principal, double interestAmount, double amount, int emiMonths, int emiAmount, List<Payment> paymentList) {
        this(principal, interestAmount, amount, emiMonths, emiAmount, paymentList);
        this.bank = bank;
    }

    public double getAmount() {
        return amount;
    }

    public int getEmiAmount() {
        return emiAmount;
    }

    public List<Payment> getPaymentList() {
        return paymentList;
    }

    public void setPaymentList(List<Payment> paymentList) {
        this.paymentList = paymentList;
    }

    public void setBank(String bank) {
        this.bank = bank;
    }

    public String getBank() {
        return bank;
    }

    @Override
    public String toString() {
        return "Loan{" +
                ", bank=" + bank +
                ", principal=" + principal +
                ", interestAmount=" + interestAmount +
                ", amount=" + amount +
                ", emiMonths=" + emiMonths +
                ", emiAmount=" + emiAmount +
                ", paymentList=" + paymentList +
                '}';
    }
}
