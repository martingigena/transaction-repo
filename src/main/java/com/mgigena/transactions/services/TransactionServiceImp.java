package com.mgigena.transactions.services;

import com.mgigena.transactions.entities.Transaction;
import com.mgigena.transactions.repository.ITransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.*;
import java.util.stream.Collectors;


@Service
public class TransactionServiceImp implements ITransactionService {
/*
    @Autowired
    private ITransactionRepository transactionRepository;

    @Override
    public List<Transaction> getAll(){
        return transactionRepository.getAll()
    }

    @Override
    public Optional<Transaction> getTransactionById(Long id) {
        return transactionRepository.findById(id);
    }

    @Override
    public Transaction save(Transaction transaction) {
       return transactionRepository.save(transaction);
    }

    @Override
    public List<Transaction> getByParentId(Long parentId){
        return transactionRepository.findByParent_Id(Long parent_id);
    }

    @Override
    public List<Long> getAllTransactionsByType(@PathVariable String type){
        return transactionRepository.getAllTransactionsByType(type)
    }

    @Override
    public List<Transaction> getByParent_Id(Long parent_id){
        return transactionRepository.getByParent_Id(type)
    }
    */

    //FAKE DATA
    private List<Transaction> transactionsList = new ArrayList<>(Arrays.asList(
            new Transaction(10L,"cars",1000,null),
            new Transaction(11L,"shopping",5000,10L),
            new Transaction(12L,"market",8000,null),
            new Transaction(13L,"cars",7000,null)
    ));

    public List<Transaction> getAll(){
        if (listOfTransactionsNull()) return null;
        return transactionsList.stream().filter(t -> Objects.nonNull(t)).collect(Collectors.toList());
    }

    public Optional<Transaction> getTransactionById(Long id) {

        return transactionsList.stream()
                .filter(t->t.getTransaction_id().equals(id))
                .findFirst();
    }

    public List<Long> getAllTransactionsByType(@PathVariable String type){
        if (listOfTransactionsNull()) return null;
        return transactionsList.stream()
                .filter(t->Objects.nonNull(t.getType())) //avoid NPE
                .filter(t -> t.getType().equals(type))
                .map(t->t.getTransaction_id())
                .collect(Collectors.toList());
    }

    public List<Transaction> getByParent_Id(Long parent_id){
        return transactionsList.stream()
                .filter(t->Objects.nonNull(t.getParent_id())) //avoid NPE
                .filter(t->t.getParent_id().equals(parent_id))
                .collect(Collectors.toList());
    }

    public void save(Transaction transaction){
        transactionsList.stream()
                .filter(t->t.getTransaction_id().equals(transaction.getTransaction_id()))
                .forEach(t-> {
                    t.setType(transaction.getType());
                    t.setAmount(transaction.getAmount());
                    t.setParent_id(transaction.getParent_id());
                });
    };

    private boolean listOfTransactionsNull() {
        Optional<List<Transaction>> optionalList = Optional.ofNullable(transactionsList);
        if (!optionalList.isPresent()) {
            return true;
        }
        return false;
    }

}
