package com.xebia.vaccnow.model;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Entity
public class Branch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private LocalTime vistingStartTime;
    private LocalTime visitingEndTime;
    @OneToMany(mappedBy = "branch")
    private List<Vaccine> vaccineList;
    @OneToOne(mappedBy = "branch")
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

    public LocalTime getVistingStartTime() {
        return vistingStartTime;
    }

    public void setVistingStartTime(LocalTime vistingStartTime) {
        this.vistingStartTime = vistingStartTime;
    }

    public LocalTime getVisitingEndTime() {
        return visitingEndTime;
    }

    public void setVisitingEndTime(LocalTime visitingEndTime) {
        this.visitingEndTime = visitingEndTime;
    }


    public static final class BranchBuilder {
        private Long id;
        private String name;
        private LocalTime vistingStartTime;
        private LocalTime visitingEndTime;

        public BranchBuilder() {
        }

        public static BranchBuilder aBranch() {
            return new BranchBuilder();
        }

        public BranchBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public BranchBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public BranchBuilder withVistingStartTime(LocalTime vistingStartTime) {
            this.vistingStartTime = vistingStartTime;
            return this;
        }

        public BranchBuilder withVisitingEndTime(LocalTime visitingEndTime) {
            this.visitingEndTime = visitingEndTime;
            return this;
        }

        public Branch build() {
            Branch branch = new Branch();
            branch.setId(id);
            branch.setName(name);
            branch.setVistingStartTime(vistingStartTime);
            branch.setVisitingEndTime(visitingEndTime);
            return branch;
        }
    }

    public Branch() {
    }
}
