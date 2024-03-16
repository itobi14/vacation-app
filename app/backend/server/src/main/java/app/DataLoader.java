package app;

import app.models.Accommodation;
import app.models.Account;
import app.repositories.AccommodationJPARepository;
import app.repositories.AccountJPARepository;
import ch.qos.logback.core.net.SyslogOutputStream;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import jakarta.transaction.Transactional;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Override
    @Transactional
    public void run(String... args) throws IOException {

        System.out.println();
        System.out.println("Running CommandLine Startup");

        System.out.println();
        this.loadDatabase();

        System.out.println("Done!");
    }

    @Autowired
    private AccountJPARepository accountsRepo;
    @Autowired
    private AccommodationJPARepository accommodationsRepo;

    private void loadDatabase() throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        JsonNode rootNode = mapper.readValue(getClass().getClassLoader().getResourceAsStream("db/mock-db.json"), JsonNode.class);

        List<Account> accounts = parseAccounts(rootNode);
        List<Accommodation> accommodations = parseAccommodations(rootNode);

        saveAccounts(accounts);
        saveAccommodations(accommodations);

        addFavoriteAccommodations(rootNode, accounts, accommodations);
    }

    private List<Account> parseAccounts(JsonNode rootNode) {
        List<Account> accounts = new ArrayList<>();
        JsonNode accountsNode = rootNode.path("accounts");
        for (JsonNode accountNode : accountsNode) {
            int id = accountNode.path("id").asInt();
            String firstName = accountNode.path("firstName").asText();
            String lastName = accountNode.path("lastName").asText();
            String email = accountNode.path("email").asText();
            String password = accountNode.path("password").asText();
            Account account = new Account(id, firstName, lastName, email, password);

            String profileImg = accountNode.path("profileImg").asText();
            account.setProfileImg(profileImg);

            accounts.add(account);
        }
        return accounts;
    }

    private List<Accommodation> parseAccommodations(JsonNode rootNode) {
        List<Accommodation> accommodations = new ArrayList<>();
        JsonNode accommodationsNode = rootNode.path("accommodations");
        for (JsonNode accommodationNode : accommodationsNode) {
            int id = accommodationNode.path("id").asInt();
            String name = accommodationNode.path("name").asText();
            String country = accommodationNode.path("country").asText();
            String city = accommodationNode.path("city").asText();
            String address = accommodationNode.path("address").asText();
            String postalCode = accommodationNode.path("postalCode").asText();
            double price = accommodationNode.path("price").asDouble();
            String type = accommodationNode.path("type").asText();
            String description = accommodationNode.path("description").asText();
            String image = accommodationNode.path("image").asText();

            Accommodation accommodation = new Accommodation(
                    id, name, country, city, address,
                    postalCode, price, type, description, image);
            accommodations.add(accommodation);
        }
        return accommodations;
    }

    private void saveAccounts(List<Account> accounts) {
        for (Account account : accounts) {
            accountsRepo.save(account);
        }
    }

    private void saveAccommodations(List<Accommodation> accommodations) {
        for (Accommodation accommodation : accommodations) {
            accommodationsRepo.save(accommodation);
        }
    }

    private void addFavoriteAccommodations(JsonNode rootNode, List<Account> accounts, List<Accommodation> accommodations) {
        for (JsonNode accountNode : rootNode.path("accounts")) {
            String email = accountNode.path("email").asText();
            Account account = findAccountByEmail(accounts, email);

            if (account != null) {
                for (JsonNode favoriteNode : accountNode.path("favorites")) {
                    long accommodationId = favoriteNode.path("id").asLong();
                    Accommodation accommodation = findAccommodationById(accommodations, accommodationId);

                    if (accommodation != null) {
                        account.addFavorite(accommodation);
                    }
                }
                accountsRepo.save(account);
            }
        }
    }

    private Account findAccountByEmail(List<Account> accounts, String email) {
        for (Account account : accounts) {
            if (account.getEmail().equals(email)) {
                return account;
            }
        }
        return null;
    }

    private Accommodation findAccommodationById(List<Accommodation> accommodations, long id) {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getId() == id) {
                return accommodation;
            }
        }
        return null;
    }

}