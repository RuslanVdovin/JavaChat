import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class AuthController {

    public PasswordField password;
    public TextField username;
    public Text erMessage;

    public void loginAction(ActionEvent actionEvent) throws IOException {
        boolean auth = MockAuthServiceImpl.getInstance()
                .auth(username.getText(), password.getText());
        if (auth) {
            Parent chat = FXMLLoader.load(getClass().getResource("chat.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Сетевой чат");
            stage.setScene(new Scene(chat));
            stage.setResizable(false);
            stage.show();
            username.getScene().getWindow().hide();
        } else {
            erMessage.setText("Неправильно введен логин или пароль!");
            username.clear();
            password.clear();
        }
    }

    public void createAccount(MouseEvent mouseEvent) throws IOException {
        Parent registration = FXMLLoader.load(getClass().getResource("registration.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Регистрация");
        stage.setScene(new Scene(registration));
        stage.setResizable(false);
        stage.show();
        username.getScene().getWindow().hide();
    }
}
