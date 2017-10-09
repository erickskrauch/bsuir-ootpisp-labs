package by.bsuir.ootpsp.task1;

import by.bsuir.ootpsp.task1.models.Person;
import by.bsuir.ootpsp.task1.models.Student;

import java.util.List;
import java.util.Map;

/*Определить класс TestCollections, в котором в качестве типа TKey используется
        класс Person, а в качестве типа TValue - класс Student. Класс содержит закрытые
        поля с коллекциями типов
         System.Collections.Generic.List<Person>;
 System.Collections.Generic.List<string>;
 System.Collections.Generic.Dictionary <Person, Student>;
 System.Collections.Generic.Dictionary <string, Student>.
        В классе TestCollections определить
         статический метод с одним целочисленным параметром типа int, который
        возвращает ссылку на объект типа Student и используется для
        автоматической генерации элементов коллекций;
         конструктор c параметром типа int (число элементов в коллекциях) для
        автоматического создания коллекций с заданным числом элементов;
         метод, который вычисляет время поиска элемента в списках List<Person> и
        List<string>,
        время поиска элемента по ключу и время поиска элемента по
        значению в коллекциях-словарях Dictionary<Person, Student> и
        Dictionary<string, Student>.*/

//TODO: need implement for last part of task 3
public class TestCollections {

    private List<Person> persons;

    private List<String> strings;

    private Map<Person, Student> personStudentMap;

    private Map<String, Student> stringStudentMap;

    public TestCollections(int collectionsSize) {

    }

}
