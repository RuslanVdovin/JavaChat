import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class RegController {

    public TextField txtUsername;
    public TextField txtPass;


    public void Reg(ActionEvent actionEvent) throws IOException {

        MockAuthServiceImpl.getInstance().addUser(txtUsername.getText(), txtPass.getText());
        Parent chat = FXMLLoader.load(getClass().getResource("chat.fxml"));
        Stage stage = new Stage();
        stage.setTitle("Сетевой чат");
        stage.setScene(new Scene(chat));
        stage.setResizable(false);
        stage.show();
        txtPass.getScene().getWindow().hide();
    }

}
