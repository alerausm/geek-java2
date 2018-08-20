import java.util.*;

/**
 * Java 2 course by GeekBrains. Homework 3. Task 1
 * @author A.Usmanov
 * @version dated 2018-08-20
 * Создать массив с набором слов (10-20 слов, должны встречаться повторяющиеся). Найти и
 * вывести список уникальных слов, из которых состоит массив (дубликаты не считаем).
 * Посчитать, сколько раз встречается каждое слово.
 */
public class Task1 {
    public static void run() {
        String[] array = {"январь", "февраль","март","апрель","май","июнь","июль","август","сентябрь","октябрь","ноябрь","декабрь","апрель","май","июнь","май","июнь","июль","август","сентябрь"};
        System.out.println(String.format(Locale.getDefault(),"Source array: %s", Arrays.toString(array)));
        List<String> list = new ArrayList<>(Arrays.asList(array));
        Collections.sort(list);
        String currentWord = null;
        int counter = 0;
        for (String word:list) {
            if (currentWord==null || !currentWord.equals(word)) {
                if (currentWord!=null) System.out.println(String.format(Locale.getDefault(),"%s: %d",currentWord,counter));
                currentWord = word;
                counter = 0;
            }
            counter++;
        }
        if (currentWord!=null) System.out.println(String.format(Locale.getDefault(),"%s: %d",currentWord,counter));
    }
}
