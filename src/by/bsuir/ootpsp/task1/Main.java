package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.*;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * В методе Main()
 5. Создать один объект типа Student, преобразовать данные в текстовый
 вид с помощью метода ToShortString() и вывести данные.
 6. Вывести значения индексатора для значений индекса Education.Specialist,
 Education.Bachelor и Education.SecondEducation.
 7. Присвоить значения всем определенным в типе Student свойствам,
 преобразовать данные в текстовый вид с помощью метода ToString() и
 вывести данные.
 8. C помощью метода AddExams( params Exam*+ ) добавить элементы в
 список экзаменов и вывести данные объекта Student, используя метод
 ToString().
 9. Сравнить время выполнения операций с элементами одномерного,
 двумерного прямоугольного и двумерного ступенчатого массивов с
 одинаковым числом элементов типа Exam.
 */
public class Main {

    public static void main(String[] args) {
        Person person = new Person("Rick", "Sanchez");
        Student student = new Student(person, Education.Specialist, 681061);
        System.out.println("First student: " + student.toShortString());
        System.out.println("Is Specialist: " + student.isEducationEqual(Education.Specialist));
        System.out.println("Is Bachelor: " + student.isEducationEqual(Education.Bachelor));
        System.out.println("Is SecondEducation: " + student.isEducationEqual(Education.SecondEducation));

        student.addExams(Arrays.asList(
            new Exam("Math", 7, LocalDateTime.of(2017, 6, 15, 16, 0)),
            new Exam("OAIP", 10, LocalDateTime.of(2017, 6, 18, 17, 0, 0)),
            new Exam("SIAOD", 10, LocalDateTime.of(2017, 6, 30, 18, 0, 0))
        ));

        System.out.println("Student with exams:");
        System.out.println(student);
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
