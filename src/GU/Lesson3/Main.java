package GU.Lesson3;

import java.util.Arrays;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {

    // 1. Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся).
    //   Найти и вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
    //   Посчитать сколько раз встречается каждое слово.
    // 2. Написать простой класс ТелефонныйСправочник, который хранит в себе список фамилий и телефонных номеров.
    //   В этот телефонный справочник с помощью метода add() можно добавлять записи.
    //   С помощью метода get() искать номер телефона по фамилии. Следует учесть, что под одной фамилией может быть
    //   несколько телефонов (в случае однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.

    public static void main(String[] args) {
        homeWorkPartOne();
        homeWorkPartTwo();
    }

    static void homeWorkPartOne(){
        String[] words = {"one", "two", "three", "apple",
                "book", "table", "car", "note",
                "one", "apple", "zero", "house",
                "array", "java", "keyboard", "java",
                "mouse", "car", "work", "one"};

        // Distinct он и в SQL Distinct
        System.out.println(Arrays.toString(Arrays.stream(words).distinct().toArray()));

        // Сначала сделал как я вижу
        HashMap<String, Integer> hm = new HashMap<>();
        for (String word : words)
            hm.put(word, hm.getOrDefault(word, 0) + 1);
        System.out.println(hm);

        // Потом подумал, что это можно уместить в одну строку, как в SQL - group by ... having count и тп, но
        // дальше System.out.println(Arrays.stream(words).collect(Collectors.groupingBy самостоятельно и при помощи
        // подсказчика (IntelliJ) не смог дойти
        // Потом решил посмотреть на stackoverflow как это делается - круто,
        // но непонятно, если применить к этому всему sorted - то что это за сортировка? на основании чего?

        System.out.println(Arrays.stream(words).collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));  // <<<<<<
        //System.out.println(Arrays.stream(words).sorted().collect(Collectors.groupingBy(Function.identity(), Collectors.counting())));
    }

    static void homeWorkPartTwo(){
        var book = new PhoneBook();
        book.pushPhone("Иванов", "555-55-55"); //
        book.pushPhone("Петров", "222-22-22");
        book.pushPhone("Сидоров", "333-33-33");
        book.pushPhone("Иванов", "555-55-55"); //
        book.pushPhone("Иванов", "999-99-99"); //

        System.out.println(Arrays.toString(book.getPhones("Иванов")));


    }
}

