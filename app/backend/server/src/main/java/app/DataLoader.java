package app;

import app.models.Account;
import app.repositories.AccountJPARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args)  {

        System.out.println("Running CommandLine Startup");

        System.out.println();
        this.createInitialAccounts();

        System.out.println("Done!");
    }

    @Autowired
    private AccountJPARepository accountsRepo;

    private void createInitialAccounts() {

        // check whether the repo is empty
        List<Account> accounts = this.accountsRepo.findAll();
        if (!accounts.isEmpty()) return;

        System.out.println("Configuring some initial accounts in the repository");
        accounts.add(this.accountsRepo.save(new Account(1, "tobi@example.com", "Tobi Kok")));
        accounts.add(this.accountsRepo.save(new Account(2, "user2@example.com", "User2")));
        accounts.add(this.accountsRepo.save(new Account(3, "user3@example.com", "User3")));

        // updates will be persisted by the transaction
        for (Account a : accounts) {
            a.setPassword("test");
            System.out.println("Added account: " + a + " (initial password = 'welcome')");
        }
        accounts.get(0).setRole("Administrator");
    }

}