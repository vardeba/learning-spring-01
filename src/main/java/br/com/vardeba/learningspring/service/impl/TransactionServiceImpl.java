package br.com.vardeba.learningspring.service.impl;

import br.com.vardeba.learningspring.dto.CreateTransactionDto;
import br.com.vardeba.learningspring.exception.AppException;
import br.com.vardeba.learningspring.model.Transaction;
import br.com.vardeba.learningspring.model.User;
import br.com.vardeba.learningspring.repository.TransactionRepository;
import br.com.vardeba.learningspring.repository.UserRepository;
import br.com.vardeba.learningspring.service.TransactionService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class TransactionServiceImpl implements TransactionService {

    private final TransactionRepository transactionRepository;
    private final UserRepository userRepository;

    public TransactionServiceImpl(TransactionRepository transactionRepository, UserRepository userRepository) {
        this.transactionRepository = transactionRepository;
        this.userRepository = userRepository;
    }

    public Transaction createTransaction(final CreateTransactionDto transactionData) {
        final User foundPayer = userRepository.findById(transactionData.getPayer_id()).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        if (Objects.equals(foundPayer.getType(), "SELLER")) {
            throw new AppException("SELLER type users cannot send money", HttpStatus.FORBIDDEN);
        }

        final User foundPayee = userRepository.findById(transactionData.getPayee_id()).orElseThrow(() -> new AppException("User not found!", HttpStatus.NOT_FOUND));

        final float transactionValue = transactionData.getValue();

        final float payerCurrentBalance = foundPayer.getBalance();

        if (payerCurrentBalance < transactionValue) {
            throw new AppException("Payer balance not sufficient", HttpStatus.FORBIDDEN);
        }

        final float payeeCurrentBalance = foundPayee.getBalance();

        foundPayer.setBalance(payerCurrentBalance - transactionValue);
        foundPayee.setBalance(payeeCurrentBalance + transactionValue);

        final Transaction newTransaction = new Transaction(foundPayer, foundPayee, transactionValue);

        return transactionRepository.save(newTransaction);

    }

    public Transaction retrieveTransaction(final long id) {
        return transactionRepository.findById(id).orElseThrow(() -> new AppException("Transaction not found!", HttpStatus.NOT_FOUND));
    }


}
