package com.mgigena.transactions.controllers;

import com.mgigena.transactions.entities.Transaction;
import com.mgigena.transactions.services.TransactionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
public class TransactionController {

    //FAKE DATA
    private List<Transaction> transactionsList = new ArrayList<>(Arrays.asList(
            new Transaction(10L, "cars", 1000,0L),
            new Transaction(11L, "shopping", 5000,0L),
            new Transaction(12L, "market", 10000,0L),
            new Transaction(13L, "cars", 6000,0L)
    ));

    /**
     *  List all transactions
     * @return List<Tramsaction>
     */
    @GetMapping()
    public List<Transaction> getAll(){
        transactionsList.stream().forEach(t-> System.out.println(t.toString())); //Eliminar al finalizar
        return transactionsList;
    }

    /**
     * Get transaction by given id
     * @param id Long
     * @return Transaction object
     */
    @GetMapping("/{id}")
    public Transaction getTransactionById(@PathVariable Long id){
        Optional<Transaction> oTransaction = transactionsList.stream().filter(t->t.getTransaction_id().equals(id)).findFirst();
        boolean transactionFound = oTransaction.isPresent();
        Transaction rTransaction = transactionFound ? oTransaction.get():null;
        System.out.println(rTransaction);//Eliminar al finalizar
        return transactionsList.stream().filter(t->t.getTransaction_id().equals(id)).findFirst().orElse(null);
    }
    @GetMapping("/sum/{id}")
    public List<Transaction> getAmountSumById(@PathVariable Long id){

        return null;
    }

    /**
     * Return all transactions id by the given type
     * @param type String
     * @return List<Long>
     */
    @GetMapping("/types/{type}")
    public List<Long> findAllTransactionsByType(@PathVariable String type){


        //Si lo tuviera que ir a buscar a la DB tendria que fijarme si esta para evitar NPE
        /*
        Optional<Transaction> oTransaction = transactionService.findByType(type);
        if (!oTransaction.isPresent()) {
            return Collections.emptyList(); //Pongo un emptyList pero podria ser un ResponseEntity.notFound() u otra cosa
        }*/
        /**
         *  Recordatorio: Eliminar al finalizar
         */
        transactionsList.stream()
                .filter(t -> t.getType().equals(type))
                .map(t->t.getTransaction_id())
                .collect(Collectors.toList())
                .forEach(t-> System.out.println(t.toString()));
        return transactionsList.stream().filter(t -> t.getType().equals(type)).map(t->t.getTransaction_id()).collect(Collectors.toList());
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
        Optional<Transaction> oTransactionToFind = Optional.ofNullable(getTransactionById(id));
        if (!oTransactionToFind.isPresent()){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Transaction not found.");
        }

        oTransactionToFind.get().setType(transaction.getType());
        oTransactionToFind.get().setAmount(transaction.getAmount());
        oTransactionToFind.get().setParent_id(transaction.getParent_id());
      return ResponseEntity.status(HttpStatus.OK).body("Updated"); 
    }
}
