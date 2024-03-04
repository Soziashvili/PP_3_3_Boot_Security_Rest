package ru.kata.spring.boot_security.demo.dto;

public class UserDto {

    private String name;
    private Short amount;

    public UserDto(String name, Short amount) {
        this.name = name;
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Short getAmount() {
        return amount;
    }

    public void setAmount(Short amount) {
        this.amount = amount;
    }
}
