package com.app.ledger.request;

import com.app.ledger.command.CommandType;

public class PaymentRequest extends LedgerRequest {

    private double lumSumAmount;
    private int afterEmiCount;

    public PaymentRequest(Builder builder) {
        this.commandType = builder.commandType;
        this.bankName = builder.bankName;
        this.customerName = builder.customerName;
        this.lumSumAmount = builder.lumSumAmount;
        this.afterEmiCount = builder.afterEmiCount;
    }

    public double getLumSumAmount() {
        return lumSumAmount;
    }

    public int getAfterEmiCount() {
        return afterEmiCount;
    }

    public static class Builder {
        private CommandType commandType;
        private String bankName;
        private String customerName;
        private double lumSumAmount;
        private int afterEmiCount;

        public static PaymentRequest.Builder newInstance()
        {
            return new PaymentRequest.Builder();
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

        public Builder setLumSumAmount(double lumSumAmount) {
            this.lumSumAmount = lumSumAmount;
            return this;
        }

        public Builder setAfterEmiCount(int afterEmiCount) {
            this.afterEmiCount = afterEmiCount;
            return this;
        }

        public PaymentRequest build()
        {
            return new PaymentRequest(this);
        }
    }
}
