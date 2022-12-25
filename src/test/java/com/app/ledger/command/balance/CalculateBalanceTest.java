package com.app.ledger.command.balance;

import com.app.ledger.LedgerApplicationTest;
import com.app.ledger.command.CommandType;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.BalanceRequest;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

class CalculateBalanceTest extends LedgerApplicationTest {

    @Autowired
    CalculateBalance calculateBalance;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void shouldExecuteCalculateBalanceCommandSuccessfully() {
        try {
            BalanceRequest request = BalanceRequest.Builder.newInstance()
                    .setCommandType(CommandType.BALANCE_COMMAND)
                    .setBankName("IDIDI")
                    .setCustomerName("Dale")
                    .setEmiMonthCount(0)
                    .build();

            calculateBalance.setRequestDetails(request);
            calculateBalance.execute();
        } catch (Exception ex) {
            ex.printStackTrace();
            Assert.fail();
        }
    }
}