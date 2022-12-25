package com.app.ledger;

import com.app.ledger.entity.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.entity.Payment;
import com.app.ledger.request.LedgerRequest;
import com.app.ledger.service.LedgerService;
import com.app.ledger.util.LedgerUtility;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Stream;

class LedgerApplicationITTest extends LedgerApplicationTest {

    @Autowired
    private LedgerService ledgerService;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    public void setUp(){
        customerRepository.deleteAll();
    }

    @Test
    void validateBalance_Scenario_1() {
            try (Stream<String> stream = Files.lines(Paths.get("src/test/resources/input_testdata_1.txt"))) {
                stream.forEach(fileLine -> {
                    LedgerRequest request = LedgerUtility.buildRequest(fileLine);
                    ledgerService.routeCommand(request);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }



        Customer customer1 = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer1);

        List<Payment> paymentListDale = customer1.getLoan().getPaymentList();
        Assert.assertTrue(paymentListDale.get(paymentListDale.size()-1).getAmountPaid()==3652.0);
        Assert.assertTrue(paymentListDale.get(paymentListDale.size()-1).getEmisLeft()==4);

        Customer customer2 = customerRepository.getUserLoanDetails("Shelly", "UON");
        Assert.assertNotNull(customer2);

        List<Payment> paymentListShelly = customer2.getLoan().getPaymentList();
        Assert.assertTrue(paymentListShelly.get(paymentListShelly.size()-1).getAmountPaid()==15856.0);
        Assert.assertTrue(paymentListShelly.get(paymentListShelly.size()-1).getEmisLeft()==3);

        Customer customer3 = customerRepository.getUserLoanDetails("Harry", "MBI");
        Assert.assertNotNull(customer3);

        List<Payment> paymentListHarry = customer3.getLoan().getPaymentList();
        Assert.assertTrue(paymentListHarry.get(paymentListHarry.size()-1).getAmountPaid()==9044.0);
        Assert.assertTrue(paymentListHarry.get(paymentListHarry.size()-1).getEmisLeft()==10);
    }

    @Test
    void validateBalance_Scenario_2() {
        try (Stream<String> stream = Files.lines(Paths.get("src/test/resources/input_testdata_2.txt"))) {
            stream.forEach(fileLine -> {
                LedgerRequest request = LedgerUtility.buildRequest(fileLine);
                ledgerService.routeCommand(request);
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        Customer customer1 = customerRepository.getUserLoanDetails("Dale", "IDIDI");
        Assert.assertNotNull(customer1);

        List<Payment> paymentListDale = customer1.getLoan().getPaymentList();
        Assert.assertTrue(paymentListDale.get(paymentListDale.size()-1).getAmountPaid()==8000.0);
        Assert.assertTrue(paymentListDale.get(paymentListDale.size()-1).getEmisLeft()==20);


        Customer customer2 = customerRepository.getUserLoanDetails("Harry", "MBI");
        Assert.assertNotNull(customer2);

        List<Payment> paymentListHarry = customer2.getLoan().getPaymentList();
        Assert.assertTrue(paymentListHarry.get(paymentListHarry.size()-1).getAmountPaid()==1044.0);
        Assert.assertTrue(paymentListHarry.get(paymentListHarry.size()-1).getEmisLeft()==12);
    }
}