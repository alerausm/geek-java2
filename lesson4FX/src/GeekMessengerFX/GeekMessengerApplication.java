package GeekMessengerFX;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
/**
 * Java 2 course by GeekBrains. Homework 4.1.
 * @author A.Usmanov
 * @version dated 2018-08-24
 * Создать окно для клиентской части чата: большое текстовое поле для отображения переписки
 * в центре окна. Однострочное текстовое поле для ввода сообщений и кнопка для отсылки
 * сообщений на нижней панели. Сообщение должно отсылаться либо по нажатию кнопки на
 * форме, либо по нажатию кнопки Enter. При «отсылке» сообщение перекидывается из нижнего
 * поля в центральное.
 *
 * Это реализация в JavaFX
 */
public class GeekMessengerApplication extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        GeekMessengerModel model = new GeekMessengerModel();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("GeekMessengerView.fxml"));
        Parent view = loader.load();
        GeekMessengerController controller  = loader.getController();

        controller.initModel(model);
        primaryStage.setTitle("Geek Messanger");
        primaryStage.setScene(new Scene(view));
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
