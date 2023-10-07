package main.model;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
public class Transactions {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private int sum;
    private LocalDate date;
    private boolean approved;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account account;
}
