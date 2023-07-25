package br.com.vardeba.learningspring.controller;

import br.com.vardeba.learningspring.dto.CreateTransactionDto;
import br.com.vardeba.learningspring.model.Transaction;
import br.com.vardeba.learningspring.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/transactions")
public class TransactionController {
    final TransactionService transactionService;

    public TransactionController(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @PostMapping
    public ResponseEntity<Transaction> createTransaction(@RequestBody final CreateTransactionDto transactionData) {
        final Transaction createdTransaction = transactionService.createTransaction(transactionData);

        return new ResponseEntity<>(createdTransaction, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Transaction> retrieveTransaction(@PathVariable final String id) {
        final Transaction transaction = transactionService.retrieveTransaction(Long.parseLong(id));

        return new ResponseEntity<>(transaction, HttpStatus.OK);

    }
}
