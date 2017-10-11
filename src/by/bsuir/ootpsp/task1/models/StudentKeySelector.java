package by.bsuir.ootpsp.task1.models;

import java.util.function.Function;

@FunctionalInterface
public interface StudentKeySelector<T> extends Function<Student, T> {

}
