package app.models;

import app.security.SecureHasher;
import app.serialization.ViewClasses;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import java.util.*;

@NamedQueries({
        @NamedQuery(name="Accounts_find_by_email",
                query = "select a from Account a where a.email = ?1")
})
@Entity
@Table(name = "ACCOUNT")
public class Account {

    @JsonView(ViewClasses.Shallow.class)
    @SequenceGenerator(name="Account_ids", initialValue=100001)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Accounts_ids")
    @Id
    private long id = 0L;
    // identification of an Account
    // unique id-s are generated by the back-end persistence layer

    @JsonView(ViewClasses.Shallow.class)
    private String name;
    @JsonView(ViewClasses.Summary.class)
    private String email = "";

    @JsonView(ViewClasses.Summary.class)
    private String role = "Regular User";

    private String hashedPassword = null;

    public Account() {
    }

    public Account(long id) {
        this.id = id;
    }

    public Account(long id, String email, String name) {
        this(id);
        this.setEmail(email);
        this.setName(name);
    }

    /**
     * Hash the given password in combination with the account identification (id)
     * and some extra characters for extra security.
     * Different accounts with the same password will deliver different hashes
     * @param password
     * @return
     */
    public String hashPassword(String password) {
        return SecureHasher.secureHash("Id-" + this.getId() + ":" + password);
    }
    public void setPassword(String newPassword) {
        this.setHashedPassword(this.hashPassword(newPassword));
    }

    /**
     * Verify whether the hash of the given password
     * matches the correct hash of the account's true password
     * (without actually knowing the correct password: only its hash has been kept in store)
     * @param password
     * @return
     */
    public boolean verifyPassword(String password) {
        return this.hashPassword(password).equals(this.getHashedPassword());
    }

    private static final Random randomizer = new Random();

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String callName) {
        this.name = callName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Account)) return false;
        return id == ((Account) o).id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return String.format("{ login=%s, callName=%s, id=%d }", this.email, this.name, this.id);
    }
}