package com.app.ledger.command.payment;

import com.app.ledger.command.CommandType;
import com.app.ledger.command.ICommand;
import com.app.ledger.request.LedgerRequest;
import com.app.ledger.request.PaymentRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(CommandType.PAYMENT_COMMAND)
public class ProcessPayment implements ICommand {

    @Autowired
    private PaymentProcessor paymentProcessor;

    private PaymentRequest paymentRequest;

    @Override
    public void execute() {
        paymentProcessor.updatePaymentDetails(paymentRequest);
    }

    @Override
    public void setRequestDetails(LedgerRequest request) {
        this.paymentRequest = (PaymentRequest) request;
    }


}
