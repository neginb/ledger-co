package com.app.ledger.command.balance;


import com.app.ledger.entity.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.BalanceRequest;
import com.app.ledger.entity.Loan;
import com.app.ledger.entity.Payment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BalanceCalculator {

    @Autowired
    private CustomerRepository customerRepository;

    public Payment fetchAndDisplayBalance(List<Payment> paymentList,BalanceRequest balanceRequest) {
        int afterEMICount = balanceRequest.getEmiMonthCount();
        String bankName = balanceRequest.getBankName();
        String customerName = balanceRequest.getCustomerName();
        Payment payment = paymentList.get(afterEMICount);
        System.out.println(bankName + " " + customerName + " " + (int)(payment.getAmountPaid()) + " " + payment.getEmisLeft());
        return payment;
    }

    public List<Payment> processAndUpdatePayments(BalanceRequest balanceRequest) {
        Customer customer = customerRepository.getUserLoanDetails(balanceRequest.getCustomerName(), balanceRequest.getBankName());
        if(customer != null){
            Loan customerLoan = customer.getLoan();
            int afterEmi = balanceRequest.getEmiMonthCount();
            int emiAmt = customerLoan.getEmiAmount();
            double totalAmt = customerLoan.getAmount();
            List<Payment> paymentList = customerLoan.getPaymentList();
            for(int i = paymentList.size(); i <= afterEmi;i++) {
                Payment prevPayment = paymentList.get(i-1);
                double totalAmountPaid = prevPayment.getAmountPaid() + emiAmt;
                if(totalAmountPaid > totalAmt) {
                    totalAmountPaid = totalAmt;
                }
                Payment payment = new Payment(totalAmountPaid,prevPayment.getEmisLeft()-1);
                paymentList.add(payment);
            }
            customerRepository.updateCustomerPayment(balanceRequest.getCustomerName(), balanceRequest.getBankName(), paymentList);
            return paymentList;
        }
        return null;
    }
}
