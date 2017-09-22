package by.bsuir.ootpsp.task1.models;

import java.util.ArrayList;
import java.util.List;

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
public class Student {

    private Person person;

    private Education education;

    private int groupNumber;

    private List<Exam> exams = new ArrayList<>();

    public Student() {
        this(new Person("unknown", "unknown"), Education.Specialist, 0);
    }

    public Student(Person person, Education education, int groupNumber) {
        this.person = person;
        this.education = education;
        this.groupNumber = groupNumber;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
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
        this.groupNumber = groupNumber;
    }

    public List<Exam> getExams() {
        return exams;
    }

    public void setExams(List<Exam> exams) {
        this.exams = exams;
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

    public String toString() {
        StringBuilder result = new StringBuilder();
        result.append(this.buildToStringHeader());
        for (Exam exam : this.exams) {
            result.append(String.format("* %s\n", exam));
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
        return String.format("%s (%d, %s)\n", this.person.toString(), this.groupNumber, this.education.name());
    }

}
