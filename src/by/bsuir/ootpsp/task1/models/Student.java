package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDeepCopy;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * В классе Student определить
  свойство типа double ( только с методом get), в котором вычисляется
 средний балл как среднее значение оценок в списке сданных экзаменов;
  индексатор булевского типа (только с методом get) с одним параметром
 типа Education; значение индексатора равно true, если значение поля с
 10
 формой обучения студента совпадает со значением индекса, и false в
 противном случае;
  метод void AddExams ( params Exam [] ) для добавления элементов в
 список экзаменов;
  перегруженную версию виртуального метода string ToString() для
 формирования строки со значениями всех полей класса, включая список
 экзаменов;
  виртуальный метод string ToShortString(), который формирует строку со
 значениями всех полей класса без списка экзаменов, но со значением
 среднего балла.
 */
public class Student extends Person implements IDeepCopy {

    private Education education;

    private int groupNumber;

    private List<Exam> exams = new ArrayList<>();

    private List<Test> tests = new ArrayList<>();

    public Student() {
        this(new Person("unknown", "unknown"), Education.Specialist, 0);
    }

    // TODO: мы недовольны этим дерьмом
    public Student(Person person, Education education, int groupNumber) {
        super(person.getName(), person.getSurname());
        this.education = education;
        this.setGroupNumber(groupNumber);
    }

    public Person getPerson() {
        return new Person(this.getName(), this.getSurname());
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
            throw new IllegalArgumentException("Illegal griup number");
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
        return this.exams.stream().mapToInt(e -> e.mark).average().orElse(0);
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

    public Iterator<Exam> getPassedExamsWithMarkGreaterThan(int mark) {
        if (mark < 0 || 10 < mark) {
            throw new IllegalArgumentException("Illegal mark value");
        }

        return this.exams.stream().filter(exam -> exam.mark > mark).iterator();
    }

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.buildToStringHeader());
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
        StringBuilder result = new StringBuilder();
        result.append(this.buildToStringHeader());
        result.append(String.format("Average mark: %2.1f\n", this.getAverageMark()));

        return result.toString();
    }

    private String buildToStringHeader() {
        return String.format("%s (%d, %s)\n", super.toString(), this.groupNumber, this.education.name());
    }

    public Object DeepCopy() {
        Student student =  new Student(new Person(this.getName(), this.getSurname()), this.education, this.groupNumber);
        student.setExams(this.getExams().stream().map(e -> (Exam)e.DeepCopy()).collect(Collectors.toList()));
        student.setTests(this.getTests().stream().map(e -> (Test)e.DeepCopy()).collect(Collectors.toList()));

        return student;
    }

}
