package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDeepCopy;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.Objects;

public class Person implements IDeepCopy, Comparable {

    private static final DateTimeFormatter BIRTH_DATE_FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yyyy");

    private static final Comparator<Person> SURNAME_COMPARATOR = Comparator
        .nullsLast(Comparator.comparing(Person::getSurname));

    static final Comparator<Person> BIRTH_DATE_COMPARATOR = Comparator
        .nullsLast(Comparator.comparing(Person::getBirthDate));

    String name;

    String surname;

    private LocalDate birthDate;

    public Person(String name, String surname, LocalDate birthDate) {
        this.name = name;
        this.surname = surname;
        this.birthDate = birthDate;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public String toString() {
        return String.format(
            "%s %s (%s)",
            this.getName(),
            this.getSurname(),
            BIRTH_DATE_FORMATTER.format(this.birthDate));
    }

    public Object DeepCopy() {
        return new Person(this.name, this.surname, this.birthDate);
    }

    @Override
    public int compareTo(Object o) {
        return SURNAME_COMPARATOR.compare(this, (Person) o);
    }

    public int hashCode() {
        int prime = 31;
        int hashCode = 1;
        hashCode = hashCode * prime + Objects.hashCode(this.name);
        hashCode = hashCode * prime + Objects.hashCode(this.surname);
        hashCode = hashCode * prime + Objects.hashCode(this.birthDate);

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
            && Objects.equals(this.getSurname(), other.getSurname())
            && Objects.equals(this.getBirthDate(), other.getBirthDate());
    }
}

