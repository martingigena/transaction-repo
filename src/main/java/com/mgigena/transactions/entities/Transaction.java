package com.mgigena.transactions.entities;

import lombok.*;

//@Entity
//@Table(name = "transactions")
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class Transaction {


    /*@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)*/
    private Long transaction_id;
    //@Column(nullable = false)
    private String type;
    //@Column(nullable = false)
    private double amount;
    private Long parent_id;
}
