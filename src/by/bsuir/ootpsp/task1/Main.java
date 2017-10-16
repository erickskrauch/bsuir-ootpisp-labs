package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.Education;
import by.bsuir.ootpsp.task1.models.Exam;
import by.bsuir.ootpsp.task1.models.Person;
import by.bsuir.ootpsp.task1.models.Student;
import by.bsuir.ootpsp.task1.models.StudentCollection;
import by.bsuir.ootpsp.task1.models.StudentKeySelector;
import by.bsuir.ootpsp.task1.models.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

public class Main {

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

    private static final StudentKeySelector<String> NAME_SURNAME_KEY_SELECTOR =
        student -> student.getName() + " " + student.getSurname();

    private static final Function<Integer, Map.Entry<Person, Student>> TEST_ENTRY_GENERATOR =
        (number) -> {
            Person person = new Person("name_" + number, "surname _" + number, LocalDate.MIN);
            Student student = new Student(person, null, 200);
            return new AbstractMap.SimpleImmutableEntry<>(person, student);
        };

    public static void main(String[] args) {
        System.out.println("\n----------Task 3 (Advanced) started----------\n");
        showTricksWithExamsSort();
        StudentCollection<String> collection = createCollection();
        printCalculatedValues(collection);
        printSearchTimeForCollections();
        System.out.println("----------Task 3 (Advanced) ended----------");
    }

    private static void showTricksWithExamsSort() {
        List<Exam> exams = new ArrayList<>(5);
        exams.add(new Exam("Art", 10, LocalDateTime.of(1540, 6, 1, 12, 0)));
        exams.add(new Exam("Engineering", 9, LocalDateTime.of(1540, 6, 4, 12, 0)));
        exams.add(new Exam("Physics", 8, LocalDateTime.of(1540, 6, 7, 12, 0)));
        exams.add(new Exam("Philosophy", 7, LocalDateTime.of(1540, 6, 10, 12, 0)));
        exams.add(new Exam("Medicine", 6, LocalDateTime.of(1540, 6, 13, 12, 0)));
        exams.add(new Exam("Marketing", 3, LocalDateTime.of(1540, 6, 16, 12, 0)));
        exams.add(new Exam("Basis of Belarusian Ideology", 0, LocalDateTime.of(1540, 6, 19, 12, 0)));

        Person person = new Person("Leonardo", "da Vinci", LocalDate.of(1519, 5, 2));
        Student student = new Student(person, Education.Bachelor, 300);
        student.addExams(exams);

        student.sortExamsBySubject();
        System.out.println("Student with exams sorted by subject:");
        System.out.println(student);

        student.sortExamsByMark();
        System.out.println("Student with exams sorted by mark:");
        System.out.println(student);

        student.sortExamsByPassDate();
        System.out.println("Student with exams sorted by pass date:");
        System.out.println(student);
    }

    private static StudentCollection<String> createCollection() {
        Student[] students = new Student[STUDENTS_COUNT];
        for (int i = 0; i < STUDENTS_COUNT; i++) {
            students[i] = new Student(new Person(NAMES[i], SURNAMES[i], BIRTH_DATES[i]), EDUCATIONS[i], GROUP);
            students[i].setTests(Collections.singletonList(new Test(TEST, TEST_PASSES[i])));
            for (int j = 0; j < EXAMS_COUNT; j++) {
                students[i].getExams().add(new Exam(SUBJECTS[j], MARKS[j][i], PASS_DATES[j]));
            }
        }

        StudentCollection<String> collection = new StudentCollection<>(NAME_SURNAME_KEY_SELECTOR);
        collection.addStudents(students);

        System.out.println("Initial students collection:");
        System.out.println(collection.toShortString());

        return collection;
    }

    private static void printCalculatedValues(StudentCollection<String> collection) {
        System.out.println("Max avg mark:");
        System.out.println(collection.getMaxAverageMark());
        System.out.println();

        System.out.println("Specialists:");
        List<Map.Entry<String, Student>> specialists = collection.findByEducation(Education.Specialist);
        specialists.stream().map(Map.Entry::getValue).map(Student::toShortString).forEach(System.out::println);

        System.out.println("Students grouped by education:");
        Map<Education, List<Map.Entry<String, Student>>> groupedStudents = collection.groupByEducation();
        groupedStudents.forEach((key, value) -> {
            System.out.println(key);
            value.stream().map(Map.Entry::getValue).map(Student::toShortString).forEach(System.out::println);
        });
    }

    private static void printSearchTimeForCollections() {
        int collectionsSize = 1_000_000;
        TestCollections<Person, Student> test = new TestCollections<>(collectionsSize, TEST_ENTRY_GENERATOR);
        System.out.println("Collections size is " + collectionsSize + "\n");

        System.out.println("Searching first element from collections...");
        long[] firstElementSearchTime = test.getSearchTimeForEachCollection(1);
        printSearchTime(firstElementSearchTime);
        System.out.println();

        System.out.println("Searching middle element from collections...");
        long[] middleElementSearchTime = test.getSearchTimeForEachCollection(collectionsSize / 2);
        printSearchTime(middleElementSearchTime);
        System.out.println();

        System.out.println("Searching last element from collections...");
        long[] lastElementSearchTime = test.getSearchTimeForEachCollection(collectionsSize - 1);
        printSearchTime(lastElementSearchTime);
        System.out.println();

        System.out.println("Searching non existed element from collections...");
        long[] nonExistedElementSearchTime = test.getSearchTimeForEachCollection(-1);
        printSearchTime(nonExistedElementSearchTime);
        System.out.println();
    }

    private static void printSearchTime(long[] searchTimeForCollections) {
        System.out.println("Search key in keys list: " + searchTimeForCollections[0]);
        System.out.println("Search key in strings list: " + searchTimeForCollections[1]);
        System.out.println("Search by key in key-value map: " + searchTimeForCollections[2]);
        System.out.println("Search by key in string-value map: " + searchTimeForCollections[3]);
        System.out.println("Search by value in key-value map: " + searchTimeForCollections[4]);
    }

}
