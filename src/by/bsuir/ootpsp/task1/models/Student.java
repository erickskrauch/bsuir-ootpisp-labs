package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDeepCopy;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Student extends Person implements IDeepCopy {

    private Education education;

    private int groupNumber;

    private List<Exam> exams = new ArrayList<>();

    private List<Test> tests = new ArrayList<>();

    Student() {
        this(new Person("unknown", "unknown", LocalDate.MIN), Education.Specialist, 0);
    }

    public Student(Person person, Education education, int groupNumber) {
        super(person.getName(), person.getSurname(), person.getBirthDate());
        this.education = education;
        this.setGroupNumber(groupNumber);
    }

    public Person getPerson() {
        return new Person(this.getName(), this.getSurname(), this.getBirthDate());
    }

    public void setPerson(Person person) {
        this.name = person.getName();
        this.surname = person.getSurname();
    }

    public Education getEducation() {
        return education;
    }

    public void setEducation(Education education) {
        this.education = education;
    }

    public int getGroupNumber() {
        return groupNumber;
    }

    public void setGroupNumber(int groupNumber) {
        if (groupNumber <= 100 || 599 < groupNumber) {
            throw new IllegalArgumentException("Illegal group number");
        }

        this.groupNumber = groupNumber;
    }

    public List<Exam> getExams() {
        return this.exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

    public List<Test> getTests() {
        return this.tests;
    }

    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    public double getAverageMark() {
        return this.exams.stream().mapToInt(Exam::getMark).average().orElse(0);
    }

    public boolean isEducationEqual(Education education) {
        return this.education == education;
    }

    public void addExams(List<Exam> exams) {
        this.exams.addAll(exams);
    }

    public Iterator getPassed() {
        return Stream.concat(this.exams.stream(), this.tests.stream()).iterator();
    }

    public Iterator getPassedNames() {
        return new StudentEnumerator(this.exams, this.tests);
    }

    public Iterator<Exam> getPassedExamsWithMarkGreaterThan(int mark) {
        if (mark < 0 || 10 < mark) {
            throw new IllegalArgumentException("Illegal mark value");
        }

        return this.exams.stream().filter(exam -> exam.getMark() > mark).iterator();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.buildToStringHeader());
        result.append("\n");
        result.append("Exams:\n");
        for (Exam exam : this.exams) {
            result.append(String.format("* %s\n", exam));
        }

        result.append("\nTests:\n");
        for (Test test : this.tests) {
            result.append(String.format("* %s\n", test));
        }

        return result.toString();
    }

    public String toShortString() {
        return String.format("%s\nAverage mark: %2.1f\n", this.buildToStringHeader(), this.getAverageMark());
    }

    private String buildToStringHeader() {
        return String.format("%s\ngroup: %d, education: %s", super.toString(), this.groupNumber, this.education);
    }

    public Object DeepCopy() {
        Student student = new Student(
            new Person(this.getName(), this.getSurname(), this.getBirthDate()),
            this.education,
            this.groupNumber
        );
        student.setExams(this.getExams().stream().map(e -> (Exam) e.DeepCopy()).collect(Collectors.toList()));
        student.setTests(this.getTests().stream().map(e -> (Test) e.DeepCopy()).collect(Collectors.toList()));

        return student;
    }

    public void sortExamsBySubject() {
        this.exams.sort(null);
    }

    public void sortExamsByMark() {
        this.exams.sort(Exam.MARK_COMPARATOR);
    }

    public void sortExamsByPassDate() {
        this.exams.sort(Exam.PASS_DATE_COMPARATOR);
    }

    public class StudentEnumerator implements Iterator<String> {

        private final List<Exam> exams;
        private final List<Test> tests;

        private int iteratorIndex = 0;
        private boolean iterateOverTests = false;

        private StudentEnumerator(List<Exam> exams, List<Test> tests) {
            this.exams = exams;
            this.tests = tests;
        }

        public boolean hasNext() {
            if (!this.iterateOverTests && this.exams.size() <= this.iteratorIndex) {
                this.iterateOverTests = true;
                this.iteratorIndex = 0;
            }

            if (this.iterateOverTests) {
                return this.tests.size() > this.iteratorIndex;
            } else {
                return this.exams.size() > this.iteratorIndex;
            }
        }

        public String next() {
            if (this.iterateOverTests) {
                return this.tests.get(iteratorIndex++).subject;
            } else {
                return this.exams.get(iteratorIndex++).getSubject();
            }
        }

    }

}
