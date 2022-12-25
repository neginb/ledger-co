package com.app.ledger.command.loan;

import com.app.ledger.command.CommandType;
import com.app.ledger.command.ICommand;
import com.app.ledger.request.LedgerRequest;
import com.app.ledger.request.LoanRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(CommandType.LOAN_COMMAND)
public class ProcessLoan implements ICommand {

    @Autowired
    private LoanProcessor loanProcessor;

    private LoanRequest loanRequest;

    @Override
    public void execute() {
        loanProcessor.processLoan(loanRequest);
    }

    @Override
    public void setRequestDetails(LedgerRequest request) {
        this.loanRequest = (LoanRequest) request;
    }


}
