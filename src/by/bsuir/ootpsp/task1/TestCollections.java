package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.Education;
import by.bsuir.ootpsp.task1.models.Person;
import by.bsuir.ootpsp.task1.models.Student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestCollections {

    private static Student createStudent(int param) {
        return new Student(
            new Person("name_" + param, "surname_" + param, LocalDate.MIN),
            Education.Specialist,
            300
        );
    }

    private List<Person> persons = new ArrayList<>();

    private List<String> personsInStrings = new ArrayList<>();

    private Map<Person, Student> personStudentMap = new HashMap<>();

    private Map<String, Student> stringStudentMap = new HashMap<>();

    public TestCollections(int collectionsSize) {
        for (int i = 0; i < collectionsSize; i++) {
            Student student = createStudent(i);
            persons.add(student.getPerson());
            personsInStrings.add(student.getPerson().toString());
            personStudentMap.put(student.getPerson(), student);
            stringStudentMap.put(student.getPerson().toString(), student);
        }
    }

    public long[] getSearchTimeForEachCollection(Person person) {
        long[] searchTimeArray = new long[4];
        searchTimeArray[0] = getExecutionTime(() -> persons.indexOf(person));
        searchTimeArray[1] = getExecutionTime(() -> personsInStrings.indexOf(person.toString()));
        searchTimeArray[2] = getExecutionTime(() -> personStudentMap.get(person));
        searchTimeArray[3] = getExecutionTime(() -> stringStudentMap.get(person.toString()));

        return searchTimeArray;
    }

    private long getExecutionTime(Action action) {
        long startTime = System.currentTimeMillis();
        action.execute();
        long endTime = System.currentTimeMillis();

        return endTime - startTime;
    }

    @FunctionalInterface
    private interface Action {

        void execute();

    }

}
