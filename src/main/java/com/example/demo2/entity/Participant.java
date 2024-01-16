package com.example.demo2.entity;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Participant implements Cloneable {

    private Integer id;
    @Size(min = 3, message = "Фамилия должна состоять хотя бы из 3 символов")
    @NotBlank
    private String surname;

    @Size(min = 3, message = "Имя должно состоять хотя бы из 3 символов")
    @NotBlank
    private String name;

    @Size(min = 3, message = "Отчество должно состоять хотя бы из 3 символов")
    @NotBlank
    private String patronymic;
    private String coach_name;

    private String coach_surname;

    private String coach_patronymic;

    @Min(value = 0, message = "Место - от 0 до 3")
    @NotNull(message = "Пропишите место")
    private Integer place;

    private boolean enable;


    public Participant() {

    }

    public Participant(Integer id, String surname, String name, String patronymic, String coach_name, String coach_surname, String coach_patronymic, Integer place, boolean enable) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.coach_name = coach_name;
        this.coach_surname = coach_surname;
        this.coach_patronymic = coach_patronymic;
        this.place = place;
        this.enable = enable;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatronymic() {
        return patronymic;
    }

    public void setPatronymic(String patronymic) {
        this.patronymic = patronymic;
    }

    public String getCoach_name() {
        return coach_name;
    }

    public void setCoach_name(String coach_name) {
        this.coach_name = coach_name;
    }

    public String getCoach_surname() {
        return coach_surname;
    }

    public void setCoach_surname(String coach_surname) {
        this.coach_surname = coach_surname;
    }

    public String getCoach_patronymic() {
        return coach_patronymic;
    }

    public void setCoach_patronymic(String coach_patronymic) {
        this.coach_patronymic = coach_patronymic;
    }

    public Integer getPlace() {
        return place;
    }

    public void setPlace(Integer place) {
        this.place = place;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

    @Override
    public String toString() {
        return "Participant{" +
                "id=" + id +
                ", surname='" + surname + '\'' +
                ", name='" + name + '\'' +
                ", patronymic='" + patronymic + '\'' +
                ", coach_name='" + coach_name + '\'' +
                ", coach_surname='" + coach_surname + '\'' +
                ", coach_patronymic='" + coach_patronymic + '\'' +
                ", place=" + place +
                ", enable=" + enable +
                '}';
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
