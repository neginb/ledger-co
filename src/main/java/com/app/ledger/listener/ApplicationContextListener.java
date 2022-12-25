package com.app.ledger.listener;

import com.app.ledger.request.LedgerRequest;
import com.app.ledger.service.LedgerService;
import com.app.ledger.util.LedgerUtility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

@Component
@Order(0)
public class ApplicationContextListener implements CommandLineRunner {

    @Autowired
    private LedgerService ledgerService;

    @Override
    public void run(String... args) {
        String filePath = args.length == 1 ? args[0] : null;
        if(filePath != null){
            try (Stream<String> stream = Files.lines(Paths.get(filePath))) {
                stream.forEach(fileLine -> {
                    LedgerRequest request = LedgerUtility.buildRequest(fileLine);
                    ledgerService.routeCommand(request);
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
