package com.app.ledger.service;

import com.app.ledger.command.ICommand;
import com.app.ledger.repository.CustomerRepository;
import com.app.ledger.request.LedgerRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class LedgerService implements ILedgerService {

    @Autowired
    ApplicationContext context;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private Map<String, ICommand> commandHandlersMap;

    @Override
    public void routeCommand(LedgerRequest request) {
        ICommand command = commandHandlersMap.get(request.getCommandType().getCommandName());
        command.setRequestDetails(request);
        command.execute();
    }
}
