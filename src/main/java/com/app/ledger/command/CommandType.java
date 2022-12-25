package com.app.ledger.command;

public enum CommandType {
    LOAN(CommandType.LOAN_COMMAND),
    PAYMENT(CommandType.PAYMENT_COMMAND),
    BALANCE(CommandType.BALANCE_COMMAND);

    public static final String LOAN_COMMAND = "LOAN";
    public static final String PAYMENT_COMMAND = "PAYMENT";
    public static final String BALANCE_COMMAND = "BALANCE";

    private String commandName;

    CommandType(String commandName) {
        this.commandName = commandName;
    }

    public String getCommandName(){
        return this.commandName;
    }
}
