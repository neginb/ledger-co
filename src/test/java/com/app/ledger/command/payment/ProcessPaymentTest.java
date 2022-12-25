package com.app.ledger.command.payment;

import com.app.ledger.LedgerApplicationTest;
import com.app.ledger.command.CommandType;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.PaymentRequest;
import com.app.ledger.util.LedgerUtility;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class ProcessPaymentTest extends LedgerApplicationTest {

    @Autowired
    ProcessPayment processPayment;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void shouldExecuteProcessPaymentCommandSuccessfully() {
        try {
            PaymentRequest paymentRequest = PaymentRequest.Builder.newInstance()
                    .setCommandType(CommandType.PAYMENT_COMMAND)
                    .setBankName("IDIDI")
                    .setCustomerName("Dale")
                    .setLumSumAmount(1000)
                    .setAfterEmiCount(0)
                    .build();
            processPayment.setRequestDetails(paymentRequest);
            processPayment.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}