package medicinetracker;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    @Override
    public void start(Stage stage) {
        
        DatabaseConnection.getConnection();
        stage.setTitle("💊 Medicine Tracker");
        LoginView loginView = new LoginView(stage);
        Scene scene = new Scene(loginView.getView(), 1000, 700);
         stage.setScene(scene);
        stage.show();
        stage.setOnCloseRequest(e -> {
            DatabaseConnection.closeConnection();
        });
    }
    
    
    public static void main(String[] args) {
        
        launch(args);
    }
}