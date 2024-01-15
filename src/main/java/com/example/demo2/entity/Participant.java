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
    private String coach;

    @Min(value = 0, message = "Место - от 0 до 3")
    @NotNull(message = "Пропишите место")
    private Integer place;

    private boolean enable;


    public Participant() {

    }

    public Participant(Integer id, String surname, String name, String patronymic, String coach, Integer place, boolean enable) {
        this.id = id;
        this.surname = surname;
        this.name = name;
        this.patronymic = patronymic;
        this.coach = coach;
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
                ", coach='" + coach + '\'' +
                ", place=" + place +
                '}';
    }
    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}
