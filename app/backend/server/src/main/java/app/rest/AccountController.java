package app.rest;

import app.models.Account;
import app.repositories.EntityRepository;
import app.security.JWToken;
import app.serialization.ViewClasses;
import app.exceptions.*;
import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/accounts")
public class AccountController {

    @Autowired
    EntityRepository<Account> accountsRepo;

    @JsonView(ViewClasses.Summary.class)
    @GetMapping(path = "", produces = "application/json")
    public List<Account> getAllAccounts() {
        return this.accountsRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable() long id) {

        Account account = this.accountsRepo.findById(id);

        if (account == null) {
            throw new ResourceNotFoundException("Cannot provide an account with id="+id);
        }

        return ResponseEntity.ok().body(account);
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount) {

        if (newAccount == null) {
            throw new NotAcceptableException(
                    "New account can not be empty");
        }

        Account savedAccount = this.accountsRepo.save(newAccount);
        return ResponseEntity.ok().body(savedAccount);
    }

    @DeleteMapping(path = "{id}")
    public Account deleteAccount(@PathVariable() long id,
                                    @RequestAttribute(name = JWToken.JWT_ATTRIBUTE_NAME) JWToken jwtInfo) {

        if (jwtInfo == null || !jwtInfo.isAdmin()) {
            throw new UnauthorizedException(
                    "Admin role is required to remove an account");
        }
        Account account = this.accountsRepo.deleteById(id);

        if (account == null) {
            throw new ResourceNotFoundException("Cannot delete an account with id="+id);
        }

        return account;
    }
}