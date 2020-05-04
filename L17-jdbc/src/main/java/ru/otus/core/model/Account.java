package ru.otus.core.model;

import ru.otus.core.Id;

public class Account {

    @Id
    long no;
    String type;
    int rest;

    public Account(long no, String type, int rest) {
        this.no = no;
        this.type = type;
        this.rest = rest;
    }

    @Override
    public String toString() {
        return "Account{" +
                "no=" + no +
                ", type='" + type + '\'' +
                ", rest=" + rest +
                '}';
    }
}
