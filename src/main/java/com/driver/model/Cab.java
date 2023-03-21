package com.driver.model;


import javax.persistence.*;

@Entity
@Table(name = "cab")
public class Cab {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int perkmRate;

    private boolean available;

    @OneToOne
    @JoinColumn
    private Driver driver;

    public Cab(){}


    public Cab(int id, int perkmRate, boolean available, Driver driver) {
        this.id = id;
        this.perkmRate = perkmRate;
        this.available = true;
        this.driver = driver;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPerkmRate() {
        return perkmRate;
    }

    public void setPerkmRate(int perkmRate) {
        this.perkmRate = perkmRate;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean available) {
        this.available = available;
    }

    public Driver getDriver() {
        return driver;
    }

    public void setDriver(Driver driver) {
        this.driver = driver;
    }
}
