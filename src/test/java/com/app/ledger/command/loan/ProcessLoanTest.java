package com.app.ledger.command.loan;

import com.app.ledger.LedgerApplicationTest;
import com.app.ledger.command.CommandType;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.LoanRequest;
import com.app.ledger.util.LedgerUtility;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProcessLoanTest extends LedgerApplicationTest {

    @Autowired
    ProcessLoan processLoan;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void shouldExecuteProcessLoanCommandSuccessfully() {
        try {
            LoanRequest requestOfDale = LoanRequest.Builder.newInstance()
                    .setCommandType(CommandType.LOAN_COMMAND)
                    .setBankName("IDIDI")
                    .setCustomerName("Dale")
                    .setAmount(10000)
                    .setYears(5)
                    .setInterestRate(4)
                    .build();
            processLoan.setRequestDetails(requestOfDale);
            processLoan.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}