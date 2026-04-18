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

        // Enhanced gradient background
        root.setStyle("-fx-background-color: linear-gradient(to bottom right, #e9d5ff, #fce7f3, #fef3c7);");

        // Decorative circles
        StackPane decorativeLayer = new StackPane();
        decorativeLayer.setPickOnBounds(false);
        
        Region circle1 = new Region();
        circle1.setPrefSize(350, 350);
        circle1.setStyle("-fx-background-color: rgba(236, 72, 153, 0.15); -fx-background-radius: 175px;");
        circle1.setTranslateX(-200);
        circle1.setTranslateY(-200);
        
        Region circle2 = new Region();
        circle2.setPrefSize(250, 250);
        circle2.setStyle("-fx-background-color: rgba(168, 85, 247, 0.12); -fx-background-radius: 125px;");
        circle2.setTranslateX(300);
        circle2.setTranslateY(250);
        
        Region circle3 = new Region();
        circle3.setPrefSize(180, 180);
        circle3.setStyle("-fx-background-color: rgba(99, 102, 241, 0.1); -fx-background-radius: 90px;");
        circle3.setTranslateX(350);
        circle3.setTranslateY(-150);

        decorativeLayer.getChildren().addAll(circle1, circle2, circle3);

        // Center content container
        VBox centerContainer = new VBox(25);
        centerContainer.setAlignment(Pos.CENTER);
        centerContainer.setMaxWidth(500);

        // Login card
        VBox loginCard = new VBox(25);
        loginCard.setPadding(new Insets(50, 60, 50, 60));
        loginCard.setAlignment(Pos.CENTER);
        loginCard.setStyle(
            "-fx-background-color: white; " +
            "-fx-background-radius: 30px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.15), 30, 0, 0, 10);"
        );
        loginCard.setMaxWidth(480);

        // Logo/Icon
        Label logoIcon = new Label("💊");
        logoIcon.setFont(Font.font(60));
        logoIcon.setStyle(
            "-fx-background-color: linear-gradient(to right, #ec4899, #f43f5e); " +
            "-fx-background-radius: 20px; " +
            "-fx-padding: 20px 30px;"
        );

        // App title
        Label title = new Label("Medicine Tracker");
        title.setFont(Font.font("Poppins", 38));
        title.setTextFill(Color.web("#1e293b"));
        title.setStyle("-fx-font-weight: 900;");

        // Subtitle
        Label subtitle = new Label("Welcome back! Please login to continue");
        subtitle.setTextFill(Color.web("#64748b"));
        subtitle.setFont(Font.font("Poppins", 15));
        subtitle.setStyle("-fx-font-weight: 500;");

        // Spacing
        Region spacer1 = new Region();
        spacer1.setPrefHeight(10);

        // Username field with label
        VBox usernameBox = new VBox(8);
        Label usernameLabel = new Label("USERNAME");
        usernameLabel.setFont(Font.font("Poppins", 11));
        usernameLabel.setTextFill(Color.web("#475569"));
        usernameLabel.setStyle("-fx-font-weight: 800; -fx-letter-spacing: 1px;");

        TextField usernameField = new TextField();
        usernameField.setPromptText("Enter your username");
        usernameField.setPrefHeight(50);
        usernameField.setStyle(
            "-fx-background-color: #f8fafc; " +
            "-fx-background-radius: 14px; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-width: 2.5px; " +
            "-fx-border-radius: 14px; " +
            "-fx-padding: 0 20px; " +
            "-fx-font-size: 15px; " +
            "-fx-font-family: 'Poppins';"
        );
        
        usernameField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                usernameField.setStyle(
                    "-fx-background-color: white; " +
                    "-fx-background-radius: 14px; " +
                    "-fx-border-color: #ec4899; " +
                    "-fx-border-width: 2.5px; " +
                    "-fx-border-radius: 14px; " +
                    "-fx-padding: 0 20px; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: 'Poppins'; " +
                    "-fx-effect: dropshadow(gaussian, rgba(236, 72, 153, 0.2), 12, 0, 0, 3);"
                );
            } else {
                usernameField.setStyle(
                    "-fx-background-color: #f8fafc; " +
                    "-fx-background-radius: 14px; " +
                    "-fx-border-color: #e2e8f0; " +
                    "-fx-border-width: 2.5px; " +
                    "-fx-border-radius: 14px; " +
                    "-fx-padding: 0 20px; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: 'Poppins';"
                );
            }
        });

        usernameBox.getChildren().addAll(usernameLabel, usernameField);

        // Password field with label
        VBox passwordBox = new VBox(8);
        Label passwordLabel = new Label("PASSWORD");
        passwordLabel.setFont(Font.font("Poppins", 11));
        passwordLabel.setTextFill(Color.web("#475569"));
        passwordLabel.setStyle("-fx-font-weight: 800; -fx-letter-spacing: 1px;");

        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefHeight(50);
        passwordField.setStyle(
            "-fx-background-color: #f8fafc; " +
            "-fx-background-radius: 14px; " +
            "-fx-border-color: #e2e8f0; " +
            "-fx-border-width: 2.5px; " +
            "-fx-border-radius: 14px; " +
            "-fx-padding: 0 20px; " +
            "-fx-font-size: 15px; " +
            "-fx-font-family: 'Poppins';"
        );

        passwordField.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                passwordField.setStyle(
                    "-fx-background-color: white; " +
                    "-fx-background-radius: 14px; " +
                    "-fx-border-color: #ec4899; " +
                    "-fx-border-width: 2.5px; " +
                    "-fx-border-radius: 14px; " +
                    "-fx-padding: 0 20px; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: 'Poppins'; " +
                    "-fx-effect: dropshadow(gaussian, rgba(236, 72, 153, 0.2), 12, 0, 0, 3);"
                );
            } else {
                passwordField.setStyle(
                    "-fx-background-color: #f8fafc; " +
                    "-fx-background-radius: 14px; " +
                    "-fx-border-color: #e2e8f0; " +
                    "-fx-border-width: 2.5px; " +
                    "-fx-border-radius: 14px; " +
                    "-fx-padding: 0 20px; " +
                    "-fx-font-size: 15px; " +
                    "-fx-font-family: 'Poppins';"
                );
            }
        });

        passwordBox.getChildren().addAll(passwordLabel, passwordField);

        // Message label
        Label message = new Label();
        message.setFont(Font.font("Poppins", 13));
        message.setStyle("-fx-font-weight: 700;");

        // Spacing
        Region spacer2 = new Region();
        spacer2.setPrefHeight(5);

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button loginBtn = new Button("LOGIN");
        loginBtn.setPrefWidth(180);
        loginBtn.setPrefHeight(50);
        loginBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #6366f1, #a855f7); " +
            "-fx-text-fill: white; " +
            "-fx-font-weight: 900; " +
            "-fx-background-radius: 14px; " +
            "-fx-font-size: 15px; " +
            "-fx-cursor: hand; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-letter-spacing: 1px;"
        );

        loginBtn.setOnMouseEntered(e -> {
            loginBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #6366f1, #a855f7); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: 900; " +
                "-fx-background-radius: 14px; " +
                "-fx-font-size: 15px; " +
                "-fx-cursor: hand; " +
                "-fx-font-family: 'Poppins'; " +
                "-fx-letter-spacing: 1px; " +
                "-fx-effect: dropshadow(gaussian, rgba(99, 102, 241, 0.4), 15, 0, 0, 5);"
            );
        });

        loginBtn.setOnMouseExited(e -> {
            loginBtn.setStyle(
                "-fx-background-color: linear-gradient(to right, #6366f1, #a855f7); " +
                "-fx-text-fill: white; " +
                "-fx-font-weight: 900; " +
                "-fx-background-radius: 14px; " +
                "-fx-font-size: 15px; " +
                "-fx-cursor: hand; " +
                "-fx-font-family: 'Poppins'; " +
                "-fx-letter-spacing: 1px;"
            );
        });

        Button signupBtn = new Button("SIGN UP");
        signupBtn.setPrefWidth(180);
        signupBtn.setPrefHeight(50);
        signupBtn.setStyle(
            "-fx-background-color: white; " +
            "-fx-text-fill: #6366f1; " +
            "-fx-border-color: #6366f1; " +
            "-fx-border-width: 2.5px; " +
            "-fx-border-radius: 14px; " +
            "-fx-background-radius: 14px; " +
            "-fx-font-weight: 900; " +
            "-fx-font-size: 15px; " +
            "-fx-cursor: hand; " +
            "-fx-font-family: 'Poppins'; " +
            "-fx-letter-spacing: 1px;"
        );

        signupBtn.setOnMouseEntered(e -> {
            signupBtn.setStyle(
                "-fx-background-color: #f8fafc; " +
                "-fx-text-fill: #6366f1; " +
                "-fx-border-color: #6366f1; " +
                "-fx-border-width: 2.5px; " +
                "-fx-border-radius: 14px; " +
                "-fx-background-radius: 14px; " +
                "-fx-font-weight: 900; " +
                "-fx-font-size: 15px; " +
                "-fx-cursor: hand; " +
                "-fx-font-family: 'Poppins'; " +
                "-fx-letter-spacing: 1px;"
            );
        });

        signupBtn.setOnMouseExited(e -> {
            signupBtn.setStyle(
                "-fx-background-color: white; " +
                "-fx-text-fill: #6366f1; " +
                "-fx-border-color: #6366f1; " +
                "-fx-border-width: 2.5px; " +
                "-fx-border-radius: 14px; " +
                "-fx-background-radius: 14px; " +
                "-fx-font-weight: 900; " +
                "-fx-font-size: 15px; " +
                "-fx-cursor: hand; " +
                "-fx-font-family: 'Poppins'; " +
                "-fx-letter-spacing: 1px;"
            );
        });

        buttonBox.getChildren().addAll(loginBtn, signupBtn);

        // ✅ UPDATED: Login button action with database validation
        loginBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                message.setText("⚠️ Please enter username and password!");
                message.setTextFill(Color.web("#ef4444"));
                return;
            }

            // Use database validation
            if (UserService.validateLogin(user, pass)) {
                DashboardView dashboard = new DashboardView(stage, user);
                Scene scene = new Scene(dashboard.getView(), 1200, 700);
                stage.setScene(scene);
            } else {
                message.setText("❌ Invalid username or password!");
                message.setTextFill(Color.web("#ef4444"));
            }
        });

        // ✅ UPDATED: Sign up button action with database
        signupBtn.setOnAction(e -> {
            String user = usernameField.getText().trim();
            String pass = passwordField.getText().trim();

            if (user.isEmpty() || pass.isEmpty()) {
                message.setText("⚠️ Please enter username and password to sign up.");
                message.setTextFill(Color.web("#ef4444"));
                return;
            }

            try {
                // Add user to database
                boolean success = UserService.addUser(user, pass);
                
                if (success) {
                    message.setText("✅ User created successfully! Now log in.");
                    message.setTextFill(Color.web("#10b981"));
                    passwordField.clear();
                } else {
                    message.setText("❌ User already exists!");
                    message.setTextFill(Color.web("#ef4444"));
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                message.setText("❌ Error creating user!");
                message.setTextFill(Color.web("#ef4444"));
            }
        });

        loginCard.getChildren().addAll(
            logoIcon, title, subtitle, spacer1,
            usernameBox, passwordBox, spacer2,
            buttonBox, message
        );

        centerContainer.getChildren().add(loginCard);

        // Stack everything
        StackPane mainStack = new StackPane();
        mainStack.getChildren().addAll(decorativeLayer, centerContainer);

        root.setCenter(mainStack);
    }

    public Pane getView() {
        return root;
    }
}