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
    @SequenceGenerator(name="Account_ids")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Accounts_ids")
    @Id
    private long id = 0L;

    @JsonView(ViewClasses.Summary.class)
    private String firstName;

    @JsonView(ViewClasses.Summary.class)
    private String lastName;

    @JsonView(ViewClasses.Summary.class)
    private String email;

    @JsonView(ViewClasses.Summary.class)
    private String password;

    private String role = "Regular User";

    @OneToMany
    @JoinColumn(name = "favorite_id")
    private List<Accommodation> favorites;

    @OneToMany
    @JoinColumn(name = "order_id")
    private List<Accommodation> orderHistory;

    @Transient
    private String hashedPassword = null;

    public Account() {
    }

    public Account(long id) {
        this.id = id;
    }

    public Account(String firstName, String lastName, String email, String password) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
    }

    public String getFullName() {
        return this.firstName + " " + this.lastName;
    }

//    public String hashPassword(String password) {
//        return SecureHasher.secureHash("Id-" + this.getId() + ":" + password);
//    }
//    public void setPassword(String newPassword) {
//        this.setHashedPassword(this.hashPassword(newPassword));
//    }

//    public boolean verifyPassword(String password) {
//        return this.hashPassword(password).equals(this.getHashedPassword());
//    }

    public boolean verifyPassword(String password) {
        return this.password.equals(this.password);
    }

    public long getId() {
        return this.id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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
        return String.format("{ ID = %d, Full Name = %s, Email = %s }", this.id, getFullName(), this.email);
    }
}