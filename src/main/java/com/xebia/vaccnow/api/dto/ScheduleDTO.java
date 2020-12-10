package com.xebia.vaccnow.api.dto;

import com.xebia.vaccnow.model.Status;

import java.time.LocalDateTime;

public class ScheduleDTO {
    private String emailId;
    private Long vaccineId;
    private Long branchId;
    private LocalDateTime localDateTime;
    private Status status;


    public ScheduleDTO() {
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Long getVaccineId() {
        return vaccineId;
    }

    public void setVaccineId(Long vaccineId) {
        this.vaccineId = vaccineId;
    }

    public Long getBranchId() {
        return branchId;
    }

    public void setBranchId(Long branchId) {
        this.branchId = branchId;
    }

    public LocalDateTime getLocalDateTime() {
        return localDateTime;
    }

    public void setLocalDateTime(LocalDateTime localDateTime) {
        this.localDateTime = localDateTime;
    }


    public static final class ScheduleDTOBuilder {
        private String emailId;
        private Long vaccineId;
        private Long branchId;
        private LocalDateTime localDateTime;

        public ScheduleDTOBuilder() {
        }

        public static ScheduleDTOBuilder aScheduleDTO() {
            return new ScheduleDTOBuilder();
        }

        public ScheduleDTOBuilder withEmailId(String emailId) {
            this.emailId = emailId;
            return this;
        }

        public ScheduleDTOBuilder withVaccineId(Long vaccineId) {
            this.vaccineId = vaccineId;
            return this;
        }

        public ScheduleDTOBuilder withBranchId(Long branchId) {
            this.branchId = branchId;
            return this;
        }

        public ScheduleDTOBuilder withLocalDateTime(LocalDateTime localDateTime) {
            this.localDateTime = localDateTime;
            return this;
        }

        public ScheduleDTO build() {
            ScheduleDTO scheduleDTO = new ScheduleDTO();
            scheduleDTO.setEmailId(emailId);
            scheduleDTO.setVaccineId(vaccineId);
            scheduleDTO.setBranchId(branchId);
            scheduleDTO.setLocalDateTime(localDateTime);
            return scheduleDTO;
        }
    }
}
