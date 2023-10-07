package main.model;

import javax.persistence.*;
import java.util.Set;

@Entity
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int balance;

    @OneToMany(mappedBy="account")
    private Set<Transactions> transactions;
}
