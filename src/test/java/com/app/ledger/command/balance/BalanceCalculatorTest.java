package com.app.ledger.command.balance;

import com.app.ledger.LedgerApplicationTest;
import com.app.ledger.command.CommandType;
import com.app.ledger.command.loan.LoanProcessor;
import com.app.ledger.command.payment.PaymentProcessor;
import com.app.ledger.entity.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.entity.Payment;
import com.app.ledger.request.BalanceRequest;
import com.app.ledger.request.LoanRequest;
import com.app.ledger.request.PaymentRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class BalanceCalculatorTest extends LedgerApplicationTest {

    @Autowired
    PaymentProcessor paymentProcessor;

    @Autowired
    LoanProcessor loanProcessor;

    @Autowired
    BalanceCalculator balanceCalculator;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void shouldDisplayBalanceWhenNotPaidAnything() {
        LoanRequest request = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(5000)
                .setYears(1)
                .setInterestRate(6)
                .build();

        loanProcessor.processLoan(request);

        BalanceRequest balanceRequest = BalanceRequest.Builder.newInstance()
                .setCommandType(CommandType.BALANCE_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setEmiMonthCount(0)
                .build();

        Payment payment = balanceCalculator.fetchAndDisplayBalance(customerRepository.getUserLoanDetails("Dale", "IDIDI").getLoan().getPaymentList(), balanceRequest);

        Assert.assertTrue(payment.getAmountPaid()==0.0);
        Assert.assertTrue(payment.getEmisLeft()==12.0);
    }

    @Test
    void shouldDisplayBalanceWhenALumpSumIsPaid() {
        LoanRequest loanRequest = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(5000)
                .setYears(1)
                .setInterestRate(6)
                .build();
        loanProcessor.processLoan(loanRequest);

        PaymentRequest paymentRequest = PaymentRequest.Builder.newInstance()
                .setCommandType(CommandType.PAYMENT_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setLumSumAmount(1000)
                .setAfterEmiCount(0)
                .build();
        paymentProcessor.updatePaymentDetails(paymentRequest);

        BalanceRequest balanceRequest = BalanceRequest.Builder.newInstance()
                .setCommandType(CommandType.BALANCE_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setEmiMonthCount(0)
                .build();
        Payment payment = balanceCalculator.fetchAndDisplayBalance(customerRepository.getUserLoanDetails("Dale", "IDIDI").getLoan().getPaymentList(), balanceRequest);

        Assert.assertTrue(payment.getAmountPaid()==1000.0);
        Assert.assertTrue(payment.getEmisLeft()==10.0);
    }

    @Test
    void shouldDisplayPendingBalanceWhenRemainingAmountIsLessThanEMI() {
        LoanRequest loanRequest = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(5000)
                .setYears(1)
                .setInterestRate(6)
                .build();
        loanProcessor.processLoan(loanRequest);

        PaymentRequest paymentRequest = PaymentRequest.Builder.newInstance()
                .setCommandType(CommandType.PAYMENT_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setLumSumAmount(4900)
                .setAfterEmiCount(0)
                .build();
        paymentProcessor.updatePaymentDetails(paymentRequest);

        BalanceRequest balanceRequest = BalanceRequest.Builder.newInstance()
                .setCommandType(CommandType.BALANCE_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setEmiMonthCount(0)
                .build();
        Payment payment = balanceCalculator.fetchAndDisplayBalance(customerRepository.getUserLoanDetails("Dale", "IDIDI").getLoan().getPaymentList(), balanceRequest);

        Assert.assertTrue(payment.getAmountPaid()==4900.0);
        Assert.assertTrue(payment.getEmisLeft()==1.0);
    }
}