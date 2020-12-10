package com.xebia.vaccnow.api.dto;

import java.time.LocalTime;

public class BranchDTO {

    private LocalTime vistingStartTime;
    private LocalTime visitingEndTime;
    private Boolean branchStatus;

    public BranchDTO(LocalTime vistingStartTime, LocalTime visitingEndTime) {
        this.vistingStartTime = vistingStartTime;
        this.visitingEndTime = visitingEndTime;
    }

    public BranchDTO(Boolean branchStatus) {
        this.branchStatus = branchStatus;
    }

    public BranchDTO() {
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

    public Boolean getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(Boolean branchStatus) {
        this.branchStatus = branchStatus;
    }
}
