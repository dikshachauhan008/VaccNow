package com.xebia.vaccnow.model;

import javax.persistence.*;

@Entity
public class Vaccine {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private int price;
    @ManyToOne
    @JoinColumn(name = "branch_id")
    private Branch branch;
    @OneToOne(mappedBy = "vaccine")
    private Schedule schedule;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Branch getBranch() {
        return branch;
    }

    public void setBranch(Branch branch) {
        this.branch = branch;
    }

    public Vaccine(Long id, String name, int price) {
        this.id = id;
        this.name = name;
        this.price = price;
    }

    public Vaccine() {
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
