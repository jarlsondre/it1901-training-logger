package traininglogger.ui;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {

  @Override
  public void start(final Stage stage) throws IOException {
    Scene scene = new Scene(new FXMLLoader(App.class.getResource("TrainingLogger.fxml")).load());
    stage.setScene(scene);
    stage.show();
  }

  public static void main(String[] args) {
    launch(args);
  }

}
