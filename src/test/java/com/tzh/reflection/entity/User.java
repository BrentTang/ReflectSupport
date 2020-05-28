package com.tzh.reflection.entity;


public class User {

    private static String id;

    private String name;
    private String password;
    private Double money;
    private Boolean honest;
    private Character rank;
    private int age;
    private char gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public char getGender() {
        return gender;
    }

    public void setGender(char gender) {
        this.gender = gender;
    }

    public Double getMoney() {
        return money;
    }

    public void setMoney(Double money) {
        this.money = money;
    }

    public static String getId() {
        return id;
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                "name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", money=" + money +
                ", honest=" + honest +
                ", rank=" + rank +
                ", age=" + age +
                ", gender=" + gender +
                '}';
    }
}
