package com.store.demo.service;

import com.store.demo.domain.Transaction;
import com.store.demo.repository.TransactionRepository;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {
    private final TransactionRepository transactionRepository;

    public TransactionService(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
    }

    public Transaction saveTransaction(Transaction transaction) {
        return this.transactionRepository.save(transaction);
    }
}
