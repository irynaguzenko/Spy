package com.spy.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;

public class Event {
    @Id
    private BigInteger id;
    private String name;
    private LocalDateTime date;
    private Long openingPeriodBeforeStartInMin;
    private Point location;
    @DBRef
    private User admin;
    @DBRef
    private List<User> participants;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDateTime getDate() {
        return date;
    }

    public void setDate(LocalDateTime date) {
        this.date = date;
    }

    public Long getOpeningPeriodBeforeStartInMin() {
        return openingPeriodBeforeStartInMin;
    }

    public void setOpeningPeriodBeforeStartInMin(Long openingPeriodBeforeStartInMin) {
        this.openingPeriodBeforeStartInMin = openingPeriodBeforeStartInMin;
    }

    public Point getLocation() {
        return location;
    }

    public void setLocation(Point location) {
        this.location = location;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public List<User> getParticipants() {
        return participants;
    }

    public void setParticipants(List<User> participants) {
        this.participants = participants;
    }
}
