package app.models;

import com.fasterxml.jackson.annotation.JsonView;
import app.serialization.ViewClasses;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "ACCOMMODATION")
public class Accommodation {

    @SequenceGenerator(name="Accommodation_ids")
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Accommodation_ids")
    @Id
    private int id;

    @JsonView(ViewClasses.Summary.class)
    private String name;

    @JsonView(ViewClasses.Summary.class)
    private String country;

    @JsonView(ViewClasses.Summary.class)
    private String city;

    @Transient
    private String streetNameNr;

    @Transient
    private String zipcode;

    @JsonView(ViewClasses.Summary.class)
    private String address;

    @JsonView(ViewClasses.Summary.class)
    private double price;

    @JsonView(ViewClasses.Summary.class)
    private String type;

    @Transient
    private String description;

    @JsonView(ViewClasses.Summary.class)
    private String imageUrl;

    public Accommodation() {
    }

    public Accommodation(String name, String country, String city, String streetNameNr, String zipcode, double price, String type, String description, String imageUrl) {
        this.name = name;
        this.country = country;
        this.city = city;
        this.streetNameNr = streetNameNr;
        this.zipcode = zipcode;
        this.address = getAddress();
        this.price = price;
        this.type = type;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    private String getAddress() {
        return this.streetNameNr + " " + this.zipcode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation)) return false;
        return id == ((Accommodation) o).id;
    }

}