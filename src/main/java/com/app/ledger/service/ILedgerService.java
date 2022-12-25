package com.app.ledger.service;

import com.app.ledger.request.LedgerRequest;
import org.springframework.stereotype.Service;

@Service
public interface ILedgerService {
    void routeCommand(LedgerRequest request);
}
