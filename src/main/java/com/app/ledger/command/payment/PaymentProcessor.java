package com.app.ledger.command.payment;


import com.app.ledger.entity.Customer;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.entity.Loan;
import com.app.ledger.entity.Payment;
import com.app.ledger.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PaymentProcessor {

    @Autowired
    private CustomerRepository customerRepository;

    public List<Payment> updatePaymentDetails(PaymentRequest paymentRequest) {
        Customer customer = customerRepository.getUserLoanDetails(paymentRequest.getCustomerName(), paymentRequest.getBankName());
        if(customer != null){
            Loan customerLoan = customer.getLoan();
            double lumSumAmount = paymentRequest.getLumSumAmount();
            int afterEmi = paymentRequest.getAfterEmiCount();
            List<Payment> paymentList = customerLoan.getPaymentList();
            int emiAmt = customerLoan.getEmiAmount();
            double totalAmt = customerLoan.getAmount();

            if(afterEmi == 0) {
                paymentList = processFirstEMIPayment(paymentList,totalAmt,lumSumAmount, emiAmt);
            } else {
                for (int i = paymentList.size(); i < afterEmi; i++) {
                    Payment prevPayment = paymentList.get(i - 1);
                    paymentList.add(processPayment(prevPayment, emiAmt, totalAmt));
                }
                if (paymentList.size() == afterEmi) {
                    Payment prevPayment = paymentList.get(paymentList.size() - 1);
                    paymentList.add(processLastEMIPayment(prevPayment, emiAmt, lumSumAmount, totalAmt));
                }
            }
            customerRepository.updateCustomerPayment(paymentRequest.getCustomerName(), paymentRequest.getBankName(), paymentList);
            return paymentList;
        }
        return null;

    }

    public List<Payment> processFirstEMIPayment(List<Payment> paymentList, double totalAmount, double lumSumAmount, int emiAmount ) {
        double amountLeft = totalAmount - lumSumAmount;
        int emiCountLeft = (int) Math.ceil(amountLeft/emiAmount);
        Payment payment = new Payment(lumSumAmount,emiCountLeft);
        paymentList.remove(paymentList.get(0));
        paymentList.add(payment);
        return paymentList;
    }

    public Payment processLastEMIPayment(Payment prevPayment, int emiAmount, double lumSumAmount, double totalAmount) {
        double emiAmountPaid = prevPayment.getAmountPaid() + emiAmount + lumSumAmount;
        if(emiAmountPaid > totalAmount) {
            emiAmountPaid = totalAmount;
        }
        double amountLeft = totalAmount - emiAmountPaid;
        int emiCountLeft = (int) Math.ceil(amountLeft / emiAmount);
        return new Payment(emiAmountPaid, emiCountLeft);
    }

    public Payment processPayment(Payment prevPayment, int emiAmount, double totalAmount) {
        double amount = prevPayment.getAmountPaid() + emiAmount;
        if(amount > totalAmount) {
            amount = totalAmount;
        }
        return new Payment(amount, prevPayment.getEmisLeft() - 1);
    }
}
