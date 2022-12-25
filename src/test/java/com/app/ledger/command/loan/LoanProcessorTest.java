package com.app.ledger.command.loan;

import com.app.ledger.LedgerApplicationTest;
import com.app.ledger.command.CommandType;
import com.app.ledger.entity.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.LoanRequest;
import com.app.ledger.entity.Loan;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

class LoanProcessorTest extends LedgerApplicationTest {

    @Autowired
    LoanProcessor loanProcessor;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void shouldCalculateLoanAmountAndUpdateForTheGivenCustomer() {
        LoanRequest request = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(10000)
                .setYears(5)
                .setInterestRate(4)
                .build();

        loanProcessor.processLoan(request);

        Customer customer = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer);
        Assert.assertTrue(customer.getLoan().getAmount() == 12000);
    }

    @Test
    void shouldCalculateLoanAmountAndUpdateForMultipleCustomerInDifferentBanks() {
        LoanRequest requestOfDale = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(10000)
                .setYears(5)
                .setInterestRate(4)
                .build();
        loanProcessor.processLoan(requestOfDale);

        LoanRequest requestOfHarry = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("MBI")
                .setCustomerName("Harry")
                .setAmount(2000)
                .setYears(2)
                .setInterestRate(2)
                .build();
        loanProcessor.processLoan(requestOfHarry);

        Customer customer1 = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer1);
        Assert.assertTrue(customer1.getLoan().getAmount() == 12000);

        Customer customer2 = customerRepository.getUserLoanDetails("Harry", "MBI");
        Assert.assertNotNull(customer2);
        Assert.assertTrue(customer2.getLoan().getAmount() == 2080);
    }

    @Test
    void shouldCalculateLoanAmountAndUpdateForMultipleCustomerInSameBanks() {
        LoanRequest requestOfDale = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Dale")
                .setAmount(10000)
                .setYears(5)
                .setInterestRate(4)
                .build();
        loanProcessor.processLoan(requestOfDale);

        Customer customer1 = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer1);
        Assert.assertTrue(customer1.getLoan().getAmount() == 12000);

        LoanRequest requestOfHarry = LoanRequest.Builder.newInstance()
                .setCommandType(CommandType.LOAN_COMMAND)
                .setBankName("IDIDI")
                .setCustomerName("Harry")
                .setAmount(2000)
                .setYears(2)
                .setInterestRate(4)
                .build();
        loanProcessor.processLoan(requestOfHarry);

        Customer customer2 = customerRepository.getUserLoanDetails("Harry", "IDIDI");
        Assert.assertNotNull(customer2);
        Assert.assertTrue(customer2.getLoan().getAmount() == 2160);
    }
}