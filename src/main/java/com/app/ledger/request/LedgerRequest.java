package com.app.ledger.request;

import com.app.ledger.command.CommandType;

public class LedgerRequest {
    protected CommandType commandType;
    protected String bankName;
    protected String customerName;

    public CommandType getCommandType() {
        return commandType;
    }

    public String getBankName() {
        return bankName;
    }

    public String getCustomerName() {
        return customerName;
    }
}
