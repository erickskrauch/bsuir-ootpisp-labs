package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDeepCopy;

import java.util.Objects;

public class Person implements IDeepCopy {

    protected String name;

    protected String surname;

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

    public Object DeepCopy() {
        return new Person(this.name, this.surname);
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = hashCode * prime + Objects.hashCode(this.name);
        hashCode = hashCode * prime + Objects.hashCode(this.surname);

        return hashCode;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(this.getClass())) {
            return false;
        }

        Person other = (Person)obj;

        return Objects.equals(this.getName(), other.getName())
            && Objects.equals(this.getSurname(), other.getSurname());
    }
}

