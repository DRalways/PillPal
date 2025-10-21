package medicinetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class LoginView {

    private BorderPane root;
    private Stage stage;

    public LoginView(Stage stage) {
        this.stage = stage;
        root = new BorderPane();

        // Background styling
        root.setStyle("-fx-background-color: linear-gradient(to right, #e9d5ff, #fce7f3);");

        // Center content
        VBox center = new VBox(15);
        center.setPadding(new Insets(40));
        center.setAlignment(Pos.CENTER);

        // App title
        Label title = new Label("💊 Medicine Tracker");
        title.setFont(Font.font("Poppins", 30));
        title.setTextFill(Color.web("#1e293b"));

        // Subtitle
        Label subtitle = new Label("Welcome back! Please login to continue");
        subtitle.setTextFill(Color.web("#64748b"));
        subtitle.setFont(Font.font("Poppins", 14));

        // Username field
        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        usernameField.setMaxWidth(280);
        usernameField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #e2e8f0;");

        // Password field
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Password");
        passwordField.setMaxWidth(280);
        passwordField.setStyle("-fx-background-radius: 10; -fx-border-radius: 10; -fx-border-color: #e2e8f0;");

        // Message label
        Label message = new Label();
        message.setTextFill(Color.web("#ef4444"));

        // Buttons
        Button loginBtn = new Button("Login");
        loginBtn.setStyle("-fx-background-color: linear-gradient(to right, #6366f1, #a855f7); -fx-text-fill: white; "
                + "-fx-font-weight: bold; -fx-background-radius: 10; -fx-padding: 10 40;");

        Button signupBtn = new Button("Sign Up");
        signupBtn.setStyle("-fx-background-color: white; -fx-text-fill: #6366f1; -fx-border-color: #6366f1; "
                + "-fx-border-radius: 10; -fx-background-radius: 10; -fx-padding: 10 40;");

        // Button container
        HBox buttonBox = new HBox(15, loginBtn, signupBtn);
        buttonBox.setAlignment(Pos.CENTER);

        // Actions
        loginBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                message.setText("Please enter username and password!");
                return;
            }

            if (!UserService.userExists(user)) {
                message.setText("User not found! Please sign up.");
                return;
            }

            // ✅ Open Dashboard
            DashboardView dashboard = new DashboardView(stage, user);
            Scene scene = new Scene(dashboard.getView(), 1200, 700);
            stage.setScene(scene);
        });

        signupBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                message.setText("Please enter username and password to sign up.");
                return;
            }

            try {
                UserService.addUser(user);
                message.setText("User created successfully! Now log in.");
            } catch (Exception ex) {
                ex.printStackTrace();
                message.setText("Error creating user!");
            }
        });

        center.getChildren().addAll(title, subtitle, usernameField, passwordField, buttonBox, message);
        root.setCenter(center);
    }

    // ✅ Getter for root layout
    public Pane getView() {
        return root;
    }
}