package com.book_keeping.boo.Entity;

import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Data
@Table(name = "budget")
public class Budget {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "budget_id")
    private Integer budget_id;

    @Column(name = "budget_kind")
    private String budget_kind;

    @Column(name = "budget_total")
    private Float budget_total;

    @Column(name = "used_money")
    private Float used_money;

    @Column(name = "user_id")
    private Integer user_id;

    @Column(name = "budget_time")
    private String budget_time;

    @Column(name = "create_time")
    private LocalDateTime create_time;

    @Column(name = "update_time")
    private LocalDateTime update_time;

    @Column(name = "category_id")
    private Integer category_id;
}
