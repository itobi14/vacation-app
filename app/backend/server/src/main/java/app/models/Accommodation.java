package app.models;

import jakarta.persistence.*;
import java.util.*;

@Entity
@Table(name = "ACCOMMODATION")
public class Accommodation {

    @SequenceGenerator(name="Accommodation_ids", initialValue=101)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator="Accommodation_ids")
    @Id
    private int id;
    private String name;
    private double price;
    private String type;
    private String description;
    private String imageUrl;

    public Accommodation() {
    }

    public Accommodation(String name, double price, String type, String description, String imageUrl) {
        this.name = name;
        this.price = price;
        this.type = type;
        this.description = description;
        this.imageUrl = imageUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Accommodation)) return false;
        return id == ((Accommodation) o).id;
    }

    @Override
    public String toString() {
        return "Accommodation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                '}';
    }
}