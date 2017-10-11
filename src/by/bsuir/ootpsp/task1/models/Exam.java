package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDateAndCopy;

import java.time.LocalDateTime;
import java.util.Comparator;

public class Exam implements IDateAndCopy, Comparable {

    public static final Comparator<Exam> MARK_COMPARATOR = Comparator.comparing(Exam::getMark);

    public static final Comparator<Exam> PASS_DATE_COMPARATOR = new PassDateExamComparator();

    private String subject;

    private int mark;

    private LocalDateTime date;

    public Exam() {
        this("Unknown", 0, LocalDateTime.MIN);
    }

    public Exam(String subject, int mark, LocalDateTime date) {
        this.subject = subject;
        this.mark = mark;
        this.date = date;
    }

    public String toString() {
        return String.format("%s with mark %d at %s", this.subject, this.mark, this.date.toString());
    }

    public Object DeepCopy() {
        return new Exam(this.subject, this.mark, this.date);
    }

    public LocalDateTime getDateTime() {
        return this.date;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.date = dateTime;
    }

    @Override
    public int compareTo(Object o) {
        return Comparator.<String>naturalOrder().compare(this.getSubject(), ((Exam)o).getSubject());
    }

    public String getSubject() {
        return subject;
    }

    public int getMark() {
        return mark;
    }

    private static class PassDateExamComparator implements Comparator<Exam> {

        @Override
        public int compare(Exam o1, Exam o2) {
            return Comparator.<LocalDateTime>naturalOrder().compare(o1.getDateTime(), o2.getDateTime());
        }

    }

}
