package br.com.vardeba.learningspring.service;

import br.com.vardeba.learningspring.dto.CreateTransactionDto;
import br.com.vardeba.learningspring.model.Transaction;

public interface TransactionService {

    Transaction createTransaction(final CreateTransactionDto transactionData);

    Transaction retrieveTransaction(final long id);


}
