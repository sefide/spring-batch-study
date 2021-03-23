package com.heedi.batch.model;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "sales")
public class SalesSum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;

    private int orderNo;
    private int amount;
    private LocalDate orderDate;

    private long amountSum;
}
