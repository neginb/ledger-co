package com.app.ledger.repository;

import com.app.ledger.entity.Customer;
import com.app.ledger.entity.Loan;
import com.app.ledger.entity.Payment;
import org.junit.Assert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    List<Customer> findByName(String name);

    default Customer getUserLoanDetails(String customerName, String bankName) {
        List<Customer> customersList = findAll();
        if(!CollectionUtils.isEmpty(customersList)) {
            return customersList.stream()
                    .filter(c -> c.getName().equalsIgnoreCase(customerName) && c.getLoan().getBank().equalsIgnoreCase(bankName))
                    .findFirst().orElse(null);
        }
        return null;
    }

    default void updateCustomerPayment(String customerName, String bankName, List<Payment> paymentList) {
        Customer customer = getUserLoanDetails(customerName, bankName);
        if(customer != null){
          customer.getLoan().setPaymentList(paymentList);
          save(customer);
        }
    }
}
