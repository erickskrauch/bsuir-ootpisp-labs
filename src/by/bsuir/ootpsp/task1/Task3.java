package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.Education;
import by.bsuir.ootpsp.task1.models.Exam;
import by.bsuir.ootpsp.task1.models.Person;
import by.bsuir.ootpsp.task1.models.Student;
import by.bsuir.ootpsp.task1.models.StudentCollection;
import by.bsuir.ootpsp.task1.models.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

class Task3 {

    private static final int STUDENTS_COUNT = 3;
    private static final String[] NAMES = new String[] {"Lavrentiy", "Iosif", "Vladimir"};
    private static final String[] SURNAMES = new String[] {"Beria", "Stalin", "Lenin"};
    private static final LocalDate[] BIRTH_DATES = new LocalDate[] {
            LocalDate.of(1899, 3, 29),
            LocalDate.of(1878, 12, 18),
            LocalDate.of(1870, 4, 22),
    };
    private static final Education[] EDUCATIONS = new Education[] {
            Education.Specialist,
            Education.Specialist,
            Education.Bachelor
    };
    private static final int GROUP = 123;
    private static final int EXAMS_COUNT = 2;
    private static final String[] SUBJECTS = new String[] {"Economics", "Repressions"};
    private static final int[][] MARKS = new int[][] {
            {3, 6, 9},
            {8, 9, 4}
    };
    private static final LocalDateTime[] PASS_DATES = new LocalDateTime[] {
            LocalDateTime.of(1917, 10, 23, 15, 0),
            LocalDateTime.of(1917, 10, 28, 15, 0)
    };
    private static final String TEST = "Being nice";
    private static final boolean[] TEST_PASSES = new boolean[] {false, false, true};

    void execute() {
        System.out.println("\n----------Task 3 started----------\n");
        StudentCollection collection = createCollection();
        printCollectionSorted(collection);
        printCalculatedValues(collection);

        System.out.println("----------Task 3 ended----------");
    }

    private StudentCollection createCollection() {
        Student[] students = new Student[STUDENTS_COUNT];
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            students[i] = new Student(new Person(NAMES[i], SURNAMES[i], BIRTH_DATES[i]), EDUCATIONS[i], GROUP);
            students[i].setTests(Collections.singletonList(new Test(TEST, TEST_PASSES[i])));
            for (int j = 0; j < EXAMS_COUNT; j++) {
                students[i].getExams().add(new Exam(SUBJECTS[j], MARKS[j][i], PASS_DATES[j]));
            }
        }

        StudentCollection collection = new StudentCollection();
        collection.addStudents(students);
        
        System.out.println("Initial students collection:");
        System.out.println(collection.toShortString());
        
        return collection;
    }
    
    private void printCollectionSorted(StudentCollection collection) {
        System.out.println("Students sorted by surname:");
        collection.sortBySurname();
        System.out.println(collection.toShortString());

        System.out.println("Students sorted by birth date:");
        collection.sortByBirthDate();
        System.out.println(collection.toShortString());

        System.out.println("Students sorted by avg mark:");
        collection.sortByAvgMark();
        System.out.println(collection.toShortString());
    }
    
    private void printCalculatedValues(StudentCollection collection) {
        System.out.println("Max avg mark:");
        System.out.println(collection.getMaxAverageMark());
        System.out.println();

        System.out.println("Specialists:");
        StudentCollection specialistsCollection = new StudentCollection();
        specialistsCollection.addStudents(collection.getSpecialists().toArray(new Student[0]));
        System.out.println(specialistsCollection.toShortString());

        for (int i = 0; i <= 10; i++) {
            List<Student> sameMarkStudents = collection.getWithAvgMarkEqualTo(i);
            if (!sameMarkStudents.isEmpty()) {
                StudentCollection sameMarkCollection = new StudentCollection();
                sameMarkCollection.addStudents(sameMarkStudents.toArray(new Student[0]));
                System.out.println("Students with avg mark equal to " + i);
                System.out.println(sameMarkCollection.toShortString());
            }
        }
    }
}
