package com.app.ledger.command.balance;

import com.app.ledger.command.CommandType;
import com.app.ledger.command.ICommand;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.BalanceRequest;
import com.app.ledger.request.LedgerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(CommandType.BALANCE_COMMAND)
public class CalculateBalance implements ICommand {

    @Autowired
    private BalanceCalculator balanceCalculator;

    @Autowired
    private CustomerRepository customerRepository;

    private BalanceRequest balanceRequest;

    @Override
    public void execute() {
        String bankName = this.balanceRequest.getBankName();
        String customerName = this.balanceRequest.getCustomerName();
        int emiMonthCount = this.balanceRequest.getEmiMonthCount();
        List paymentList = balanceCalculator.processAndUpdatePayments(this.balanceRequest);
        if(paymentList != null){
            balanceCalculator.fetchAndDisplayBalance(paymentList, this.balanceRequest);
        }
    }

    @Override
    public void setRequestDetails(LedgerRequest request) {
        this.balanceRequest = (BalanceRequest) request;
    }
}
