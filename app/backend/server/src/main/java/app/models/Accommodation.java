package app.models;

import com.fasterxml.jackson.annotation.JsonView;
import app.serialization.ViewClasses;
import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "ACCOMMODATION")
public class Accommodation {

//    @SequenceGenerator(name="Accommodation_ids")
//    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Accommodation_ids")
    @Id
    @JsonView(ViewClasses.Summary.class)
    private int id;

    @JsonView(ViewClasses.Summary.class)
    private String name;

    @JsonView(ViewClasses.Summary.class)
    private String country;

    @JsonView(ViewClasses.Summary.class)
    private String city;

    @JsonView(ViewClasses.Summary.class)
    @Transient
    private String streetNameNr;

    @JsonView(ViewClasses.Summary.class)
    @Transient
    private String zipCode;

    @JsonView(ViewClasses.Summary.class)
    private String address;

    @JsonView(ViewClasses.Summary.class)
    private double price;

    @JsonView(ViewClasses.Summary.class)
    private String type;

    @JsonView(ViewClasses.Summary.class)
    @Transient
    private String description;

    @JsonView(ViewClasses.Summary.class)
    private String imgUrl;

    public Accommodation() {
    }

    public Accommodation(int id, String name, String country, String city, String streetNameNr, String zipCode, double price, String type, String description, String imgUrl) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.city = city;
        this.streetNameNr = streetNameNr;
        this.zipCode = zipCode;
        this.address = getAddress();
        this.price = price;
        this.type = type;
        this.description = description;
        this.imgUrl = imgUrl;
    }

    private String getAddress() {
        return this.streetNameNr + " " + this.zipCode;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation)) return false;
        return id == ((Accommodation) o).id;
    }

}