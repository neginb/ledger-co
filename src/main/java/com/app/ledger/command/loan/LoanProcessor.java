package com.app.ledger.command.loan;

import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.entity.Customer;
import com.app.ledger.entity.Loan;
import com.app.ledger.entity.Payment;
import com.app.ledger.request.LoanRequest;
import com.app.ledger.util.LedgerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class LoanProcessor {

    @Autowired
    private CustomerRepository customerRepository;

    public void processLoan(LoanRequest loanRequest) {
        double principalAmount = loanRequest.getAmount();
        int years = loanRequest.getYears();
        double interestRate = loanRequest.getInterestRate();

        List<Payment> paymentList = initializePayment(years);
        int totalMonths = LedgerUtility.getMonthsFromYears(years);
        double interestAmount = LedgerUtility.calculateInterestAmount(principalAmount, years, interestRate);
        double totalAmount = principalAmount + interestAmount;
        int emiAmount = LedgerUtility.calculateEMIAmount(totalAmount, totalMonths);
        Loan loan = new Loan(loanRequest.getBankName(), principalAmount, interestAmount, totalAmount, totalMonths, emiAmount, paymentList);
        updateLoanDetails(loan, loanRequest.getBankName(), loanRequest.getCustomerName());
    }

    private List<Payment> initializePayment(int years) {
        List<Payment> paymentList = new ArrayList<>();
        Payment payment = new Payment(0, LedgerUtility.getMonthsFromYears(years));
        paymentList.add(payment);
        return paymentList;
    }

    private void updateLoanDetails(Loan loan, String bankName, String customerName) {
        List<Customer> customersList = customerRepository.findByName(customerName);
        Customer customer = !CollectionUtils.isEmpty(customersList) ? customersList.stream().filter(c -> c.getLoan().getBank().equalsIgnoreCase(bankName)).findFirst().orElse(new Customer(customerName)) : new Customer(customerName);
        customer.setLoan(loan);
        customerRepository.save(customer);
    }

}
