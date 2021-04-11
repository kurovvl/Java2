package GU.Lesson3;

import java.util.*;


public class PhoneBook {

    private HashMap<String, HashSet<String>> book = new HashMap<>();

    /**
     * Получает список телефонов по идентификатору (фамилия)
     *
     * @param lastName Фамилия
     * @return Список телефонов
     */
    public String[] getPhones(String lastName) {
        return book.getOrDefault(lastName, new HashSet<>()).toArray(new String[0]);
    }


    /**
     * Задает телефон по идентификатору (фамилия)
     * @param lastName Фамилия
     * @param phone Телефон
     */
    public void pushPhone(String lastName, String phone){
        var phoneList = book.getOrDefault(lastName, new HashSet<>());
        phoneList.add(phone);
        book.put(lastName, phoneList);
    }
}
