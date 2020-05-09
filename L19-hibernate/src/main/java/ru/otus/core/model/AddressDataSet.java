package ru.otus.core.model;


import javax.persistence.*;

@Entity
@Table(name = "addresses")
public class AddressDataSet {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long id;

    @Column(name = "street")
    private String street;

    public AddressDataSet() {
    }

    public AddressDataSet(String street) {
        this.street = street;
    }

    public long getId() {
        return id;
    }

    public String getStreet() {
        return street;
    }

    @Override
    public String toString() {
        return "AddressDataSet{" +
                "id=" + id +
                ", street=" + street + "}";
    }
}
