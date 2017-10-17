package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDeepCopy;

import java.io.Serializable;

public class Test implements IDeepCopy<Test>, Serializable {

    public final String subject;

    public final boolean isPassed;

    public Test() {
        this("", false);
    }

    public Test(String subject, boolean isPassed) {
        this.subject = subject;
        this.isPassed = isPassed;
    }

    public String toString() {
        return String.format("%s is %s", this.subject, this.isPassed ? "passed" : "not passed");
    }

    public Test deepCopy() {
        return new Test(this.subject, this.isPassed);
    }

}
