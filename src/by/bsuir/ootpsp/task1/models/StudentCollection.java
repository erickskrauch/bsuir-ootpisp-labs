package by.bsuir.ootpsp.task1.models;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class StudentCollection {

    private List<Student> students = new ArrayList<>();

    public void addDefaults() {
        for (int i = 0; i < 3; i++) {
            this.students.add(new Student());
        }
    }

    public void addStudents(Student[] newStudents) {
        this.students.addAll(Arrays.asList(newStudents));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        this.students.forEach(student -> builder.append(student).append("\n"));

        return builder.toString();
    }

    public String toShortString() {
        final StringBuilder builder = new StringBuilder();

        this.students.forEach(
                student -> builder
                        .append(student.toShortString())
                        .append("Exams count: ").append(student.getExams().size())
                        .append("\n")
                        .append("Tests count: ").append(student.getTests().size())
                        .append("\n\n")
        );

        return builder.toString();
    }

    @SuppressWarnings("unchecked")
    public void sortBySurname() {
        Collections.sort(this.students);
    }

    public void sortByBirthDate() {
        this.students.sort(Person.BIRTH_DATE_COMPARATOR);
    }

    public void sortByAvgMark() {
        this.students.sort(Student.AVG_MAR_COMPARATOR);
    }

    public double getMaxAverageMark() {
        return this.students.stream()
                .mapToDouble(Student::getAverageMark)
                .max()
                .orElse(0);
    }

    public List<Student> getSpecialists() {
        return this.students.stream()
                .filter(student -> student.isEducationEqual(Education.Specialist))
                .collect(Collectors.toList());
    }

    public List<Student> getWithAvgMarkEqualTo(int mark) {
        return this.students.stream()
                .filter(student -> mark <= student.getAverageMark() && student.getAverageMark() < (mark + 1))
                .collect(Collectors.toList());
    }
}
