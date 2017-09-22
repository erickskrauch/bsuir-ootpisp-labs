package by.bsuir.ootpsp.task1.models;

import java.time.LocalDateTime;

/**
 * Определить класс Exam, который имеет три открытых автореализуемых
 свойства, доступных для чтения и записи:
  свойство типа string, в котором хранится название предмета;
  свойство типа int, в котором хранится оценка;
  свойство типа System.DateTime для даты экзамена
 */
public class Exam {

    public String subjectName;

    public int mark;

    public LocalDateTime date;

    public Exam() {
        this("Unknown", 0, LocalDateTime.MIN);
    }

    public Exam(String subjectName, int mark, LocalDateTime date) {
        this.subjectName = subjectName;
        this.mark = mark;
        this.date = date;
    }

    public String toString() {
        return String.format("%s with mark %d at %s", this.subjectName, this.mark, this.date.toString());
    }

}
