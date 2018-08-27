/**
* Java 2 course by GeekBrains. Homework 4.
* @author A.Usmanov
* @version dated 2018-08-21
* Создать окно для клиентской части чата: большое текстовое поле для отображения переписки
* в центре окна. Однострочное текстовое поле для ввода сообщений и кнопка для отсылки
* сообщений на нижней панели. Сообщение должно отсылаться либо по нажатию кнопки на
* форме, либо по нажатию кнопки Enter. При «отсылке» сообщение перекидывается из нижнего
* поля в центральное.
*/
public class Homework4 {
    public static void main(String[] args) {
        GeekMessenger.createInstance().setVisible(true);
    }
}
