package com.spy.model;

import com.spy.dto.Gender;
import org.springframework.data.annotation.Id;

import java.math.BigInteger;
import java.time.LocalDate;
import java.util.Objects;

public class User {
    @Id
    private BigInteger id;
    private String givenName;
    private String familyName;
    private LocalDate birthDate;
    private String city;
    private Gender gender;

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public String getGivenName() {
        return givenName;
    }

    public void setGivenName(String givenName) {
        this.givenName = givenName;
    }

    public String getFamilyName() {
        return familyName;
    }

    public void setFamilyName(String familyName) {
        this.familyName = familyName;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", givenName='" + givenName + '\'' +
                ", familyName='" + familyName + '\'' +
                ", birthDate=" + birthDate +
                ", city='" + city + '\'' +
                ", gender=" + gender +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) &&
                Objects.equals(givenName, user.givenName) &&
                Objects.equals(familyName, user.familyName) &&
                Objects.equals(birthDate, user.birthDate) &&
                Objects.equals(city, user.city) &&
                gender == user.gender;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, givenName, familyName, birthDate, city, gender);
    }
}
