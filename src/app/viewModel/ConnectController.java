package app.viewModel;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.io.IOException;

public class ConnectController extends Pane {
    @FXML
    TextField ipTextField;
    @FXML
    TextField portTextField;
    @FXML
    Button accept;

    StringProperty ipProperty, portProperty;

    public ConnectController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/components/connect.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);

        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        ipProperty = new SimpleStringProperty();
        portProperty = new SimpleStringProperty();

    }

    public void bind(StringProperty ipProperty, StringProperty portProperty) {
        ipProperty.bind(this.ipProperty);
        portProperty.bind(this.portProperty);
    }

    @FXML
    public void sendData(MouseEvent mouseEvent) {
        ipProperty.setValue(ipTextField.getText());
        portProperty.setValue(portTextField.getText());
    }

    @FXML
    public void closeWindow(MouseEvent sendData) {
        ((Stage) this.getScene().getWindow()).close();
    }
}
