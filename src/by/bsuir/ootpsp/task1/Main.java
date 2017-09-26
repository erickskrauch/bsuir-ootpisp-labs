package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Person person1 = new Person("Vlad", "Pisetskiy");
        Person person2 = new Person("Vlad", "Pisetskiy");

        System.out.println("Is links equal: " + (person1 == person2));
        System.out.println("Is persons equal: " + person1.equals(person2));

        Student student = new Student(person1, Education.Specialist, 161);

        student.addExams(Arrays.asList(
            new Exam("Physics", 2, LocalDateTime.of(2017, 5, 11, 17, 0)),
            new Exam("Math", 7, LocalDateTime.of(2017, 6, 15, 16, 0)),
            new Exam("OAIP", 10, LocalDateTime.of(2017, 6, 18, 17, 0, 0)),
            new Exam("SIAOD", 10, LocalDateTime.of(2017, 6, 30, 18, 0, 0))
        ));

        student.setTests(Arrays.asList(
            new Test("религоведение", true),
            new Test("философия", true),
            new Test("бег на 100 метров", false)
        ));

        System.out.println("Student with exams and tests:");
        System.out.println(student);

        System.out.println("All students exams and tests:");
        student.getPassedNames().forEachRemaining(p -> System.out.println(p.toString()));
    }

    private void testPerformance() {
        // TODO: it really don't working
        final int size = 10000;
        List<Exam> examList = new Random().ints(size).boxed().map(i -> new Exam("", i, LocalDateTime.MIN)).collect(Collectors.toList());
        Exam[] arr = examList.toArray(new Exam[size]);
        Exam[][] matrix = new Exam[100][100];
        for (int i = 0; i < matrix.length; i++) {
            matrix[i] = new Exam[100];
            for (int j = 0; j < matrix[i].length; j++) {
                matrix[i][j] = arr[i * matrix.length + j];
            }
        }
    }

}
