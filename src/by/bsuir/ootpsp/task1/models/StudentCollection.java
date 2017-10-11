package by.bsuir.ootpsp.task1.models;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class StudentCollection<T> {

    private StudentKeySelector<T> keySelector;

    private Map<T, Student> studentsMap;

    public StudentCollection(StudentKeySelector<T> keySelector) {
        this.keySelector = keySelector;
        this.studentsMap = new HashMap<>();
    }

    public void addDefaults() {
        for (int i = 0; i < 3; i++) {
            Student student = new Student();
            this.studentsMap.put(this.keySelector.apply(student), student);
        }
    }

    public void addStudents(Student[] newStudents) {
        Arrays.stream(newStudents).forEach(student -> this.studentsMap.put(this.keySelector.apply(student), student));
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        this.studentsMap.values().forEach(student -> builder.append(student).append("\n"));

        return builder.toString();
    }

    public String toShortString() {
        final StringBuilder builder = new StringBuilder();

        this.studentsMap.values().forEach(student -> builder
            .append(student.toShortString())
            .append("Exams count: ").append(student.getExams().size())
            .append("\n")
            .append("Tests count: ").append(student.getTests().size())
            .append("\n\n")
        );

        return builder.toString();
    }

    public double getMaxAverageMark() {
        return this.studentsMap.values().stream()
            .mapToDouble(Student::getAverageMark)
            .max()
            .orElse(0);
    }

    //TODO: hate this
    public List<Map.Entry<T, Student>> findByEducation(Education education) {
        return this.studentsMap.entrySet().stream()
            .filter(entry -> entry.getValue().isEducationEqual(Education.Specialist))
            .collect(Collectors.toList());
    }

    //TODO: i really hate this, but it is our task
    public Map<Education, List<Map.Entry<T, Student>>> groupByEducation() {
        return this.studentsMap.entrySet().stream()
            .collect(Collectors.groupingBy(entry -> entry.getValue().getEducation()));
    }

}
