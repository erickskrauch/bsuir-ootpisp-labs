package by.bsuir.ootpsp.task1.models;

import by.bsuir.ootpsp.task1.IDeepCopy;

public class Test implements IDeepCopy {

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

    public Object DeepCopy() {
        return new Test(this.subject, this.isPassed);
    }

}
