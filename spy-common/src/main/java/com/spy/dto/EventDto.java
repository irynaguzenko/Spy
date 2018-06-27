package com.spy.dto;

//import org.springframework.data.mongodb.core.geo.GeoJsonPoint;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

public class EventDto {
    @NotNull
    private String name;
    @NotNull
    private LocalDateTime date;
    @NotNull
    private Long openingPeriodBeforeStartInMin;
    @NotNull
//    private GeoJsonPoint location;
    private String description;

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

//    public GeoJsonPoint getLocation() {
//        return location;
//    }
//
//    public void setLocation(GeoJsonPoint location) {
//        this.location = location;
//    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
