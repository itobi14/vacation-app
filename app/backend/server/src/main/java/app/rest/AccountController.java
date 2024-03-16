package app.rest;

import app.models.Accommodation;
import app.models.Account;
import app.repositories.EntityRepository;
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
    @Autowired
    EntityRepository<Accommodation> accommodationsRepo;

    /* ACCOUNTS ------------------------------------------------------------------------------------ */

    @JsonView(ViewClasses.Summary.class)
    @GetMapping(path = "", produces = "application/json")
    public List<Account> getAllAccounts() {
        return this.accountsRepo.findAll();
    }

    @GetMapping(path = "{id}", produces = "application/json")
    public ResponseEntity<Account> getAccount(@PathVariable() long id) {

        Account account = this.accountsRepo.findById(id);

        if (account == null) {
            throw new ResourceNotFoundException("Cannot provide an account with id = " + id);
        }

        return ResponseEntity.ok().body(account);
    }

    @PostMapping(path = "", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Account> createAccount(@RequestBody Account newAccount) {

        if (newAccount == null) {
            throw new NotAcceptableException(
                    "New account can not be null");
        }

        Account savedAccount = this.accountsRepo.save(newAccount);
        return ResponseEntity.ok().body(savedAccount);
    }

    /* FAVORITES ------------------------------------------------------------------------------------ */

    @GetMapping(path = "/{accountId}/favorites", produces = "application/json")
    public List<Accommodation> getAllFavorites(@PathVariable long accountId) {

        Account account = this.accountsRepo.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found with id: " + accountId);
        }
        return account.getFavorites();
    }

    @GetMapping(path = "/{accountId}/favorites/{accommodationId}", produces = "application/json")
    public ResponseEntity<Accommodation> getFavorite(
            @PathVariable long accountId,
            @PathVariable long accommodationId) {

        Account account = accountsRepo.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found with id: " + accountId);
        }

        Accommodation accommodation = accommodationsRepo.findById(accommodationId);

        // Check if accommodation is in user's favorites
        List<Accommodation> favorites = account.getFavorites();
        if (!favorites.contains(accommodation)) {
            throw new ResourceNotFoundException("Accommodation is not in favorites.");
        }

        return ResponseEntity.ok().body(accommodation);
    }

    @PostMapping("/{accountId}/favorites/{accommodationId}")
    public ResponseEntity<?> addFavorite(
            @PathVariable("accountId") long accountId,
            @PathVariable("accommodationId") long accommodationId) {

        Account account = accountsRepo.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found with id: " + accountId);
        }

        Accommodation accommodation = accommodationsRepo.findById(accommodationId);
        if (accommodation == null) {
            throw new ResourceNotFoundException("Accommodation not found with id: " + accommodationId);
        }

        // Check if accommodation is already in favorites
        List<Accommodation> favorites = account.getFavorites();
        if (favorites.contains(accommodation)) {
            return ResponseEntity.badRequest().body("Accommodation is already in favorites.");
        }

        // Add accommodation to favorites
        account.getFavorites().add(accommodation);
        accountsRepo.save(account);

        return ResponseEntity.ok(accommodation);
    }

    @DeleteMapping("/{accountId}/favorites/{accommodationId}")
    public ResponseEntity<?> deleteFavorite(
            @PathVariable("accountId") long accountId,
            @PathVariable("accommodationId") long accommodationId) {

        Account account = accountsRepo.findById(accountId);
        if (account == null) {
            throw new ResourceNotFoundException("Account not found with id: " + accountId);
        }

        Accommodation accommodation = accommodationsRepo.findById(accommodationId);
        if (accommodation == null) {
            throw new ResourceNotFoundException("Accommodation not found with id: " + accommodationId);
        }

        // Check if accommodation is in favorites
        List<Accommodation> favorites = account.getFavorites();
        if (!favorites.contains(accommodation)) {
            return ResponseEntity.badRequest().body("Accommodation is not in favorites.");
        }

        // Remove accommodation from favorites
        favorites.remove(accommodation);
        accountsRepo.save(account);

        return ResponseEntity.ok(accommodation);
    }

}