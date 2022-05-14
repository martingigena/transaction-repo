package com.mgigena.transactions.controllers;

import com.mgigena.transactions.entities.Transaction;
import com.mgigena.transactions.services.TransactionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

     @Autowired
     TransactionServiceImp transactionServiceImp;
    /**
     *  List all transactions
     * @return List<Tramsaction>
     */
    @GetMapping()
    public List<Transaction> getAll(){
        return  transactionServiceImp.getAll();
    }

    /**
     * Get transaction by given id
     * @param id Long
     * @return Transaction object
     */
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id){
       return transactionServiceImp.getTransactionById(id).orElse(null);
    }

    /**
     * Return the sum between the amount of the given transaction and all those that have that transaction as their parent.
     * @param id Long
     * @return ResponseEntity
     */
    @GetMapping("/sum/{id}")
    public ResponseEntity<?> getAmountSumById(@PathVariable Long id){
        Optional<Transaction> oTransactionToFind = Optional.ofNullable(transactionServiceImp.getTransactionById(id).orElse(null));
        if (!oTransactionToFind.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }

        double sum = oTransactionToFind.get().getAmount();

        List <Transaction> parentListTransaction = transactionServiceImp.getByParent_Id(id);

        if (parentListTransaction.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body("sum = " + sum);
        }

        sum += parentListTransaction.stream().mapToDouble(Transaction::getAmount).sum();

        return ResponseEntity.status(HttpStatus.OK).body("sum = " + sum);
    }

    /**
     * Return all transactions id by the given type
     * @param type String
     * @return List<Long>
     */
    @GetMapping("/types/{type}")
    public List<Long> getAllTransactionsByType(@PathVariable String type){
        return transactionServiceImp.getAllTransactionsByType(type);
    }

    /**
     * Given an id and a transaction object this update the transaction with the new data
     * Header = "key":"Content-Type","value":"application/json"
     * @param id Long
     * @param transaction Json object
     * @return Status
     */
    @PutMapping("/{id}")
    public ResponseEntity<?> updateTransaction(@PathVariable Long id, @RequestBody(required = false) Transaction transaction) {
        Optional<Transaction> oTransactionBody = Optional.ofNullable(transaction);
        if (!oTransactionBody.isPresent()){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Request body is empty.");
        }
        Optional<Transaction> oTransactionToFind = transactionServiceImp.getTransactionById(id);
        if (!oTransactionToFind.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }

        oTransactionToFind.get().setType(transaction.getType());
        oTransactionToFind.get().setAmount(transaction.getAmount());
        oTransactionToFind.get().setParent_id(transaction.getParent_id());
        transactionServiceImp.save(oTransactionToFind.get());

      return ResponseEntity.status(HttpStatus.OK).body("Updated");
    }


}
