package com.mgigena.transactions.controllers;

import com.mgigena.transactions.entities.Transaction;
import com.mgigena.transactions.services.TransactionServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
     *  Recordatorio: Eliminar al finalizar
     * @return
     */
    @GetMapping()
    public String getAll(){
        transactionsList.stream().forEach(t-> System.out.println(t.toString()));
        return transactionsList.toString();
    }
    @GetMapping("/sum/{id}")
    public List<Transaction> getAmountSumById(@PathVariable Long id){

        return null;
    }

    @GetMapping("/types/{type}")
    public List<Long> findAllTransactionsByType(@PathVariable String type){
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

    @PutMapping("/{id}")
    public String updateTransaction(@PathVariable Long id,@RequestBody Transaction transaction) {

        return null;
    }
}
