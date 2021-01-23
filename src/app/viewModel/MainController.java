package app.viewModel;

import client.Client;
import client.SimpleClient;
import javafx.beans.property.Property;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.SplitPane;

import java.io.IOException;

public class MainController extends SplitPane {
    final Client client = new SimpleClient();

    Property<Boolean> booleanProperty = new SimpleBooleanProperty();
    Property<Boolean> enableProperty = new SimpleObjectProperty<>(false);

    @FXML
    MapController leftSide;

    @FXML
    RightSideController rightSide;

    public MainController() {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/app/components/app.fxml"));
        fxmlLoader.setRoot(this);
        fxmlLoader.setController(this);
        try {
            fxmlLoader.load();
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        bind();
    }

    public void bind() {
        leftSide.bind(client, enableProperty);
        rightSide.setClient(client);
        enableProperty.addListener((observable, oldValue, newValue) -> {
            if (newValue)
                rightSide.enableAll();
        });
    }
}
