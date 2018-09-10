import java.util.Scanner;
/**
 * Java 2 course by GeekBrains. Homework 6.
 * @author A.Usmanov
 * @version dated 2018-09-10
 * Написать консольный вариант клиент\серверного приложения, в котором пользователь может писать сообщения как на клиентской стороне, так и на серверной. Т.е. если на клиентской стороне написать «Привет», нажать Enter, то сообщение должно передаться на сервер и там отпечататься в консоли. Если сделать то же самое на серверной стороне, сообщение, соответственно, передаётся клиенту и печатается у него в консоли. Есть одна особенность, которую нужно учитывать: клиент или сервер может написать несколько сообщений подряд. Такую ситуацию необходимо корректно обработать.
 * Разобраться с кодом с занятия — он является фундаментом проекта-чата.
 * ВАЖНО! Сервер общается только с одним клиентом, т.е. не нужно запускать цикл, который будет ожидать второго/третьего/n-го клиента.
 */
public class Homework6 {
    private static final String MODE_SERVER = "server";
    private static final String MODE_CLIENT = "client";

    public static void main(String... args) {
        String mode = "";
        GeekChat chat = null;
        for (String arg:args) {
            if (arg.equalsIgnoreCase(MODE_SERVER)) {
                mode = MODE_SERVER;
                break;
            }
            else if (arg.equalsIgnoreCase(MODE_CLIENT)) {
                mode = MODE_CLIENT;
                break;
            }
        }
        if (mode==MODE_SERVER) {

            chat = new GeekChatServer();
        }
        else if (mode==MODE_CLIENT) {
            chat = new GeekChatClient();
        }
        else {
            System.out.println("please use SERVER or CLIENT key");
            return;
        }
        Thread threadChat = new Thread(chat);
        threadChat.start();
        Scanner scanner = new Scanner(System.in);
        while(threadChat.isAlive()) {
            chat.postMessage(scanner.nextLine());
        }
    }
}
