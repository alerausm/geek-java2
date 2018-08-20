import java.util.*;

/**
 * Java 2 course by GeekBrains. Homework 3. Task2
 * @author A.Usmanov
 * @version dated 2018-08-20
 *
 * Написать простой класс Телефонный Справочник, который хранит в себе список фамилий и
 * телефонных номеров. В этот телефонный справочник с помощью метода add() можно
 * добавлять записи, а с помощью метода get() искать номер телефона по фамилии. Следует
 * учесть, что под одной фамилией может быть несколько телефонов (в случае
 * однофамильцев), тогда при запросе такой фамилии должны выводиться все телефоны.
 *
 * Желательно как можно меньше добавлять своего, чего нет в задании (т.е. не надо в телефонную
 * запись добавлять еще дополнительные поля (имя, отчество, адрес), делать взаимодействие с
 * пользователем через консоль и т.д. Консоль желательно не использовать (в том числе Scanner),
 * тестировать просто из метода main(), прописывая add() и get().
 */
public class Task2 {
    public static void run() {

        PhoneBook book = new PhoneBook();
        book.add(new PhoneBook.Contact("Adam","054548548"));
        book.add(new PhoneBook.Contact("Barbara","054548549"));
        book.add(new PhoneBook.Contact("Eugene","054548552"));
        book.add(new PhoneBook.Contact("Franklin","054548553"));
        book.add(new PhoneBook.Contact("Dora","054548551"));
        book.add(new PhoneBook.Contact("Franklin","054548553 (3)"));
        book.add(new PhoneBook.Contact("Fred","05454855545"));
        book.add(new PhoneBook.Contact("Celine","054548550"));
        book.add(new PhoneBook.Contact("Dora","054548551 (2)"));
        book.add(new PhoneBook.Contact("Dora","054548551 (2)"));//<-- специально пытаемся записать дубль записи (все совпадает), в итоге останется только один
        book.add(new PhoneBook.Contact("Franklin","054548553 (2)"));
        book.add(new PhoneBook.Contact("Gerard","054548554"));

        System.out.println("Entities of book:");
        for (PhoneBook.Contact contact:book) System.out.println(contact);

        System.out.print("Find Barbara:");
        System.out.println(book.get("Barbara"));

        System.out.print("Find Dora:");
        System.out.println(book.get("Dora"));

        System.out.print("Find Franklin:");
        System.out.println(book.get("Franklin"));

        System.out.print("Find all started with Fr:");
        System.out.println(book.like("Fr"));


        System.out.print("Find Yoohoo:");
        System.out.println(book.get("Yoohoo"));

    }

    public static class PhoneBook extends TreeSet<PhoneBook.Contact> {
        public PhoneBook() {
            super(Comparator.comparing(Contact::getName,String.CASE_INSENSITIVE_ORDER).thenComparing(Contact::getPhone,String.CASE_INSENSITIVE_ORDER));
        }

        public PhoneBook get(String name) {
            PhoneBook result = new PhoneBook();
            for (Contact contact:this.tailSet(new Contact(name,null))) {
                if (!contact.getName().equals(name)) break;
                result.add(contact);
            }
            return result;
        }

        /*
        * Additional method. Search all contacts started with defined sequence
        */
        public PhoneBook like(String name) {
            PhoneBook result = new PhoneBook();
            for (Contact contact:this.tailSet(new Contact(name,null))) {
                if (!contact.getName().toUpperCase().startsWith(name.toUpperCase())) break;
                result.add(contact);
            }
            return result;
        }

        public static class Contact {
            final private String name;
            final private String phone;

            public Contact(String name, String phone) {
                this.name = (name==null)?"":name;
                this.phone = (phone==null)?"":phone;
            }

            public String getName() {
                return name;
            }
            public String getPhone() {
                return phone;
            }

            @Override
            public String toString() {
                return String.format(Locale.getDefault(),"%s: %s",getName(),getPhone());
            }
        }
    }
}
