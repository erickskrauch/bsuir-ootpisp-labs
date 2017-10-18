package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.*;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Scanner;

public class Main {

    private static final String NAME = "Lavrentiy";

    private static final String SURNAME = "Beria";

    private static final LocalDate BIRTH_DATE = LocalDate.of(1899, 3, 29);

    private static final Education EDUCATION = Education.Specialist;

    private static final int GROUP = 123;

    private static final int EXAMS_COUNT = 2;

    private static final String[] SUBJECTS = new String[] {"Economics", "Repressions"};

    private static final int[][] MARKS = new int[][] { {3}, {8} };

    private static final LocalDateTime[] PASS_DATES = new LocalDateTime[] {
        LocalDateTime.of(1917, 10, 23, 15, 0),
        LocalDateTime.of(1917, 10, 28, 15, 0)
    };

    private static final String TEST = "Being nice";

    private static final boolean TEST_PASS = false;

    public static void main(String[] args) {
        System.out.println("\n----------Task 5 started----------\n");
        
        Student student = createStudent();
        System.out.println("Original student:");
        System.out.println(student);
        System.out.println("Copied student:");
        Student copied = student.deepCopy();
        System.out.println(copied);
        
        System.out.println("Enter file name:");
        Scanner scanner = new Scanner(System.in);
        String filename = scanner.nextLine();
        File file = new File(filename);
        if (!file.exists()) {
            System.out.println("File doesn't exist. Will create new one.");
            student.save(filename);
        } else {
            System.out.println("Will load student from file.");
            student.load(filename);
            System.out.println("Student loaded from file:");
            System.out.println(student);
        }
        
        student.addFromConsole();
        student.save(filename);
        System.out.println(student);
        
        System.out.println("Reload student from file");
        System.out.println(student);
        Student.load(filename, student);
        student.addFromConsole();
        Student.save(filename, student);
        System.out.println(student);
        
        System.out.println("----------Task 5 ended----------");
    }

    private static Student createStudent() {
        Student student = new Student(new Person(NAME, SURNAME, BIRTH_DATE), EDUCATION, GROUP);
        student.setTests(Collections.singletonList(new Test(TEST, TEST_PASS)));
        for (int j = 0; j < EXAMS_COUNT; j++) {
            student.getExams().add(new Exam(SUBJECTS[j], MARKS[j][0], PASS_DATES[j]));
        }

        return student;
    }

}
