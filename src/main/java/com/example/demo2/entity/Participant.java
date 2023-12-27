package com.example.demo2.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.Comparator;

@Entity
@Table(name = "participant")
public class Participant {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Integer id;
    @Column
    @Size(min = 5, message = "ФИО должно состоять хотя бы из 5 символов")
    @NotBlank
    private String fio;
    @Column
    private String coach;
    @Column
    @Min(value = 0, message = "Место - от 0 до 3")
    @NotNull(message = "Пропишите место")
    private Integer place;


    public Participant() {

    }

    public Participant(Integer id, String fio, String coach, Integer place) {
        this.id = id;
        this.fio = fio;
        this.coach = coach;
        this.place = place;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public String getCoach() {
        return coach;
    }

    public void setCoach(String coach) {
        this.coach = coach;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", fio='" + fio + '\'' +
                ", coach='" + coach + '\'' +
                ", place=" + place +
                '}';
    }
}
