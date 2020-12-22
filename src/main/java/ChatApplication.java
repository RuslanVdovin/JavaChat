import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class ChatApplication extends Application {


    @Override
    public void start(Stage primaryStage) throws Exception {
        new ChatApplication();
        Parent authorization = FXMLLoader.load(getClass().getResource("authorization.fxml"));
        primaryStage.setTitle("Авторизация");
        primaryStage.setScene(new Scene(authorization));
        primaryStage.setResizable(true);
        primaryStage.show();
    }

}
