package by.bsuir.ootpsp.task1.models;

public class Person {

    private final String name;

    private final String surname;

    public Person(String name, String surname) {
        this.name = name;
        this.surname = surname;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public String toString() {
        return String.format("%s %s", this.getName(), this.getSurname());
    }

}

