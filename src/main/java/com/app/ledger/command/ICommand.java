package com.app.ledger.command;

import com.app.ledger.request.LedgerRequest;

public interface ICommand {
    void execute();
    void setRequestDetails(LedgerRequest request);
}
