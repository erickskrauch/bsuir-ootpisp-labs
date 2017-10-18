package by.bsuir.ootpsp.task1.models;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.stream.Stream;

public class Student extends Person implements Serializable {

    public static boolean save(String filename, Student student) {
        Objects.requireNonNull(student);

        return student.save(filename);
    }

    public static boolean load(String filename, Student student) {
        Objects.requireNonNull(student);

        return student.load(filename);
    }

    static final Comparator<Student> AVG_MAR_COMPARATOR = Comparator.comparing(Student::getAverageMark);

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

    public Iterator getPassedNames() {
        return new StudentEnumerator(this.exams, this.tests);
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
        return this.buildToStringHeader() +
            "\n" +
            String.format("Average mark: %2.1f\n", this.getAverageMark());
    }

    private String buildToStringHeader() {
        return super.toString() +
            "\n" +
            String.format("group: %d, education: %s", this.groupNumber, this.education);
    }

    public Student deepCopy() {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            ObjectOutputStream studentOut = new ObjectOutputStream(out);
            studentOut.writeObject(this);
            ObjectInputStream studentIn = new ObjectInputStream(new ByteArrayInputStream(out.toByteArray()));

            return (Student)studentIn.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException("Exception while copying student", e);
        }
    }

    public boolean save(String filename) {
        try(FileOutputStream out = new FileOutputStream(new File(filename))) {
            ObjectOutputStream studentOut = new ObjectOutputStream(out);
            studentOut.writeObject(this);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean load(String filename) {
        try(FileInputStream in = new FileInputStream(new File(filename))) {
            ObjectInputStream studentIn = new ObjectInputStream(in);
            Student loaded = (Student)studentIn.readObject();
            this.name = loaded.name;
            this.surname = loaded.surname;
            this.birthDate = loaded.birthDate;
            this.education = loaded.education;
            this.groupNumber = loaded.groupNumber;
            this.exams = loaded.exams;
            this.tests = loaded.tests;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return false;
    }

    public boolean addFromConsole() {
        System.out.println("Enter exam data (format - <subject name>#<mark[0 - 10]>#<pass date[D.M.YYYY HH:mm]>):");
        Scanner scanner = new Scanner(System.in);
        String[] data = scanner.nextLine().split("#");

        if (data.length < 3) {
            System.out.println("Invalid exam data");

            return false;
        }

        String subject = data[0];
        if (subject.trim().isEmpty()) {
            System.out.println("Invalid subject data");
    
            return false;
        }

        if (!data[1].matches("\\d|10")) {
            System.out.println("Invalid mark data");

            return false;
        }

        int mark = Integer.parseInt(data[1]);


        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("d.M.y HH:mm");
        LocalDateTime passDate = null;
        try {
            passDate = LocalDateTime.parse(data[2], dtf);
        } catch (DateTimeParseException e) {
            System.out.println("Invalid pass date data");

            return false;
        }

        this.exams.add(new Exam(subject, mark, passDate));
        
        return true;
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
                return this.exams.get(iteratorIndex++).subjectName;
            }
        }

    }

}
