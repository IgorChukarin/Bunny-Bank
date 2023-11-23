package main.controller;
import main.Repository.AccountRepository;
import main.Repository.TransactionRepository;
import main.model.Account;
import main.model.Status;
import main.model.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Map;

@Controller
@RequestMapping("/")
public class DefaultController {

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    TransactionRepository transactionRepository;

    @GetMapping
    public String index(Map<String, Object> model) {
        Account account = accountRepository.findById(1).get();
        ArrayList<Transaction> transactions = (ArrayList<Transaction>) transactionRepository.findAll();
        for (Transaction transaction : transactions) {
            if (transaction.getStatus().equals(Status.ACCEPTED)) {
                int newBalance = account.getBalance() + transaction.getSum();
                account.setBalance(newBalance);
                transaction.setStatus(Status.SAVED);
                transactionRepository.save(transaction);
            }
        }
        accountRepository.save(account);
        Integer balance = account.getBalance();
        Integer percentage = balance / (700_000 / 100);
        model.put("balance", balance / 1000);
        model.put("percentage", percentage);
        return "index";
    }

    @PostMapping("/put")
    public String addTransaction(@RequestParam int sum) {
        Account account = accountRepository.findById(1).get();
        Transaction transaction = new Transaction();
        transaction.setSum(sum);
        transaction.setDate(LocalDate.now());
        transaction.setStatus(Status.WAITING);
        transaction.setAccount(account);
        transactionRepository.save(transaction);
        return "index";
    }

    @GetMapping("/transactions")
    public String showTransactions(Map<String, Object> model) {
        Iterable<Transaction> transactions = transactionRepository.findAll();
        model.put("transactions", transactions);
        return "transactions";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "about";
    }
}
