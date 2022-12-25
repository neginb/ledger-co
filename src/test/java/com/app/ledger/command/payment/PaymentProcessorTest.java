package com.app.ledger.command.payment;

import com.app.ledger.LedgerApplicationTest;
import com.app.ledger.command.CommandType;
import com.app.ledger.command.loan.LoanProcessor;
import com.app.ledger.entity.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.LoanRequest;
import com.app.ledger.request.PaymentRequest;
import com.app.ledger.entity.Loan;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

class PaymentProcessorTest extends LedgerApplicationTest {

    @Autowired
    PaymentProcessor paymentProcessor;

    @Autowired
    LoanProcessor loanProcessor;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void shouldUpdatePaymentDoneByTheGivenCustomerAsZeroWhenNotPaidAnything() {
        LoanRequest request = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(5000)
                .setYears(1)
                .setInterestRate(6)
                .build();

        loanProcessor.processLoan(request);

        Customer customer1 = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer1);
        Assert.assertTrue(customer1.getLoan().getAmount() == 5300);
        Assert.assertTrue(customer1.getLoan().getPaymentList().get(0).getAmountPaid() == 0.0);
        Assert.assertTrue(customer1.getLoan().getPaymentList().get(0).getEmisLeft() == 12);
    }

    @Test
    void shouldUpdatePaymentDoneByTheGivenCustomerWhenALumpSumIsPaid() {
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

        Customer customer1 = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer1);
        Assert.assertTrue(customer1.getLoan().getAmount() == 5300);
        Assert.assertTrue(customer1.getLoan().getPaymentList().get(0).getAmountPaid() == 1000.0);
        Assert.assertTrue(customer1.getLoan().getPaymentList().get(0).getEmisLeft() == 10);
    }
}