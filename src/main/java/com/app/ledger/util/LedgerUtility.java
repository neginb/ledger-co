package com.app.ledger.util;


import com.app.ledger.command.CommandType;
import com.app.ledger.request.BalanceRequest;
import com.app.ledger.request.LedgerRequest;
import com.app.ledger.request.LoanRequest;
import com.app.ledger.request.PaymentRequest;


public class LedgerUtility {

    private LedgerUtility() { throw new IllegalStateException("Utility class"); }

    public static int getMonthsFromYears(int year) {
        return year*12;
    }

    public static double calculateInterestAmount(double principalAmount, int years, double interestRate) {
        return (principalAmount * years * interestRate)/100;
    }

    public static int calculateEMIAmount(double principalAmount, int months) {
        return (int) Math.ceil(principalAmount/months);
    }

    public static LedgerRequest buildRequest(String fileLine){
        String[] inputSplit = fileLine.split(" ");
        String command = inputSplit[0];
        String bankName = inputSplit[1];
        String customerName = inputSplit[2];

        CommandType commandType = CommandType.valueOf(command);
        LedgerRequest request;
        switch (commandType) {
            case LOAN:
                double amount = Double.parseDouble(inputSplit[3]);
                int years = Integer.parseInt(inputSplit[4]);
                double interestRate = Double.parseDouble(inputSplit[5]);
                request = LoanRequest.Builder.newInstance()
                        .setCommandType(command)
                        .setBankName(bankName)
                        .setCustomerName(customerName)
                        .setAmount(amount)
                        .setYears(years)
                        .setInterestRate(interestRate)
                        .build();
                break;
            case PAYMENT:
                double lumSumAmount = Double.parseDouble(inputSplit[3]);
                int afterEmiCount = Integer.parseInt(inputSplit[4]);
                request = PaymentRequest.Builder.newInstance()
                        .setCommandType(command)
                        .setBankName(bankName)
                        .setCustomerName(customerName)
                        .setLumSumAmount(lumSumAmount)
                        .setAfterEmiCount(afterEmiCount)
                        .build();
                break;
            case BALANCE:
                int emiMonthCount = Integer.parseInt(inputSplit[3]);
                request = BalanceRequest.Builder.newInstance()
                        .setCommandType(command)
                        .setBankName(bankName)
                        .setCustomerName(customerName)
                        .setEmiMonthCount(emiMonthCount)
                        .build();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + command);
        }
        return request;
    }
}
