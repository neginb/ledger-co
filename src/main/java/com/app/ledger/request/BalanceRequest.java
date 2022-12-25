package com.app.ledger.request;

import com.app.ledger.command.CommandType;

public class BalanceRequest extends LedgerRequest {
    private int emiMonthCount;

    public BalanceRequest(Builder builder) {
        this.commandType = builder.commandType;
        this.bankName = builder.bankName;
        this.customerName = builder.customerName;
        this.emiMonthCount = builder.emiMonthCount;
    }

    public int getEmiMonthCount() {
        return emiMonthCount;
    }

    public static class Builder {
        private CommandType commandType;
        private String bankName;
        private String customerName;
        private int emiMonthCount;

        public static BalanceRequest.Builder newInstance()
        {
            return new BalanceRequest.Builder();
        }

        private Builder() {}

        public Builder setCommandType(String commandType) {
            this.commandType = CommandType.valueOf(commandType);
            return this;
        }

        public Builder setBankName(String bankName) {
            this.bankName = bankName;
            return this;
        }

        public Builder setCustomerName(String customerName) {
            this.customerName = customerName;
            return this;
        }

        public Builder setEmiMonthCount(int emiMonthCount) {
            this.emiMonthCount = emiMonthCount;
            return this;
        }

        public BalanceRequest build()
        {
            return new BalanceRequest(this);
        }
    }
}
