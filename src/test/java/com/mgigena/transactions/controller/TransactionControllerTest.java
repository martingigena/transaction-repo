package com.mgigena.transactions.controller;

import com.mgigena.transactions.controllers.TransactionController;
import com.mgigena.transactions.entities.Transaction;
import com.mgigena.transactions.services.TransactionServiceImp;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@RunWith(SpringRunner.class)
public class TransactionControllerTest {

    @InjectMocks
    TransactionController transactionController;

    @Mock
    TransactionServiceImp transactionServiceImp;

    @Test
    public void shouldGetAll() throws Exception {
        // given
        List<Transaction> transactionsListTest = new ArrayList<>(Arrays.asList(
                new Transaction(10L,"cars",1000,null),
                new Transaction(11L,"shopping",5000,10L)
        ));

        when(transactionServiceImp.getAll()).thenReturn(transactionsListTest);

        // when
        List<Transaction> transactionsListResult = transactionController.getAll();

        // then
        assertThat(transactionsListResult.size()).isEqualTo(2);

        assertThat(transactionsListResult.get(0).getType())
                .isEqualTo(transactionsListTest.get(0).getType());

        assertThat(transactionsListResult.get(1).getAmount())
                .isEqualTo(transactionsListTest.get(1).getAmount());
    }

    @Test
    public void shouldGetTransactionById() throws Exception {
        // given
        Optional<Transaction> transaction = Optional.ofNullable(new Transaction(11L,"shopping",5000,10L));
        when(transactionServiceImp.getTransactionById(anyLong())).thenReturn(transaction);

        // when
        Transaction transactionsResult = transactionController.getTransactionById(anyLong());

        // then
        assertThat(transactionsResult.getTransaction_id()).isEqualTo(11L);
    }

    @Test
    public void shouldGetAllTransactionsByType() throws Exception {
        // given
        Transaction transaction1 = new Transaction(10L,"cars",1000,null);
        Transaction transaction2 = new Transaction(11L,"cars",5000,10L);

        when(transactionServiceImp.getAllTransactionsByType(anyString())).thenReturn(Arrays.asList(transaction1.getTransaction_id(),transaction2.getTransaction_id()));

        // when
        List<Long> transactionIdsResult = transactionController.getAllTransactionsByType(anyString());

        // then
        assertThat(transactionIdsResult.size()).isEqualTo(2);
    }

    @Test
    public void shouldUpdateTransaction() throws Exception {
        // given
        Transaction transaction = new Transaction(10L,"cars",1000,null);
        Transaction transactioUpdated = new Transaction(10L,"cars",5000,10L);
        when(transactionServiceImp.getTransactionById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionServiceImp.save(any(Transaction.class))).thenReturn(transactioUpdated);

        // when
        ResponseEntity responseEntity = transactionController.updateTransaction(anyLong(),transaction);

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
    }


    @Test
    public void shouldGet6000AmountSumById() throws Exception {
        // given
        Transaction transaction = new Transaction(10L,"cars",1000,null);
        List<Transaction> transactionsListTest = new ArrayList<>(Arrays.asList(
                new Transaction(11L,"shopping",5000,10L)
        ));
        when(transactionServiceImp.getTransactionById(anyLong())).thenReturn(Optional.of(transaction));
        when(transactionServiceImp.getByParent_Id(anyLong())).thenReturn(transactionsListTest);

        // when
        ResponseEntity responseEntity = transactionController.getAmountSumById(anyLong());

        // then
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(responseEntity.getBody().toString()).isEqualTo("sum = 6000.0");
    }
}
