package com.app.ledger.request;

import com.app.ledger.command.CommandType;

public class LoanRequest extends LedgerRequest {
    private double amount;
    private int years;
    private double interestRate;

    public LoanRequest(Builder builder) {
        this.commandType = builder.commandType;
        this.bankName = builder.bankName;
        this.customerName = builder.customerName;
        this.amount = builder.amount;
        this.years = builder.years;
        this.interestRate = builder.interestRate;
    }

    public double getAmount() {
        return amount;
    }

    public int getYears() {
        return years;
    }

    public double getInterestRate() {
        return interestRate;
    }

    public static class Builder {
        private CommandType commandType;
        private String bankName;
        private String customerName;
        private double amount;
        private int years;
        private double interestRate;

        public static Builder newInstance()
        {
            return new Builder();
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

        public Builder setAmount(double amount) {
            this.amount = amount;
            return this;
        }

        public Builder setYears(int years) {
            this.years = years;
            return this;
        }

        public Builder setInterestRate(double interestRate) {
            this.interestRate = interestRate;
            return this;
        }

        public LoanRequest build()
        {
            return new LoanRequest(this);
        }
    }
}
