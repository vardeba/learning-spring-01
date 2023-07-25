package br.com.vardeba.learningspring.repository;

import br.com.vardeba.learningspring.model.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TransactionRepository extends JpaRepository<Transaction, Long> {
}
