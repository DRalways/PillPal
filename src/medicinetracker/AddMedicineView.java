package medicinetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class AddMedicineView {

    private BorderPane root;
    private Stage stage;
    private String username;

    public AddMedicineView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #e9d5ff, #fce7f3);");

        // Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Main content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40));
        mainContent.setAlignment(Pos.TOP_CENTER);
        mainContent.setMaxWidth(700);

        Label title = new Label("➕ Add New Medicine");
        title.setFont(Font.font("Poppins", 32));
        title.setTextFill(Color.web("#1e293b"));
        title.setStyle("-fx-font-weight: 900;");

        // Form container
        VBox formContainer = new VBox(20);
        formContainer.setStyle("-fx-background-color: white; -fx-background-radius: 20px; " +
                              "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 15, 0, 0, 5);");
        formContainer.setPadding(new Insets(40));

        // Create text fields
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., Aspirin");
        styleTextField(nameField);

        TextField dosageField = new TextField();
        dosageField.setPromptText("e.g., 500mg");
        styleTextField(dosageField);

        TextField frequencyField = new TextField();
        frequencyField.setPromptText("e.g., Twice daily");
        styleTextField(frequencyField);

        TextField timeField = new TextField();
        timeField.setPromptText("e.g., 8:00 AM, 8:00 PM");
        styleTextField(timeField);

        // Create labeled fields
        VBox nameBox = createLabeledField("Medicine Name", nameField);
        VBox dosageBox = createLabeledField("Dosage", dosageField);
        VBox frequencyBox = createLabeledField("Frequency", frequencyField);
        VBox timeBox = createLabeledField("Time", timeField);

        Label msg = new Label();
        msg.setFont(Font.font("Poppins", 14));
        msg.setStyle("-fx-font-weight: bold;");

        // Buttons
        HBox buttonBox = new HBox(15);
        buttonBox.setAlignment(Pos.CENTER);

        Button saveBtn = new Button("💾 Save Medicine");
        saveBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #6366f1, #a855f7); " +
            "-fx-text-fill: white; -fx-font-weight: bold; -fx-background-radius: 12px; " +
            "-fx-padding: 14px 40px; -fx-font-size: 15px; -fx-cursor: hand;"
        );

        Button cancelBtn = new Button("Cancel");
        cancelBtn.setStyle(
            "-fx-background-color: white; -fx-text-fill: #64748b; " +
            "-fx-border-color: #e2e8f0; -fx-border-width: 2px; -fx-border-radius: 12px; " +
            "-fx-background-radius: 12px; -fx-padding: 14px 40px; -fx-font-size: 15px; " +
            "-fx-font-weight: bold; -fx-cursor: hand;"
        );

        buttonBox.getChildren().addAll(saveBtn, cancelBtn);

        // ✅ UPDATED: Save button action with database
        saveBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String dosage = dosageField.getText().trim();
            String frequency = frequencyField.getText().trim();
            String time = timeField.getText().trim();

            if (name.isEmpty() || dosage.isEmpty() || frequency.isEmpty() || time.isEmpty()) {
                msg.setText("❌ Please fill all fields!");
                msg.setTextFill(Color.web("#ef4444"));
                return;
            }

            try {
                // Call database method with username
                boolean success = MedicineService.addMedicine(
                    username, name, dosage, frequency, time
                );
                
                if (success) {
                    msg.setText("✅ Medicine saved successfully!");
                    msg.setTextFill(Color.web("#10b981"));
                    nameField.clear();
                    dosageField.clear();
                    frequencyField.clear();
                    timeField.clear();
                } else {
                    msg.setText("❌ Error saving medicine!");
                    msg.setTextFill(Color.web("#ef4444"));
                }
            } catch (Exception ex) {
                msg.setText("❌ Database error!");
                msg.setTextFill(Color.web("#ef4444"));
                ex.printStackTrace();
            }
        });

        cancelBtn.setOnAction(e -> {
            DashboardView view = new DashboardView(stage, username);
            Scene scene = new Scene(view.getView(), 1200, 700);
            stage.setScene(scene);
        });

        formContainer.getChildren().addAll(nameBox, dosageBox, frequencyBox, timeBox, buttonBox, msg);

        // Center the form
        HBox centerContainer = new HBox(formContainer);
        centerContainer.setAlignment(Pos.CENTER);
        
        mainContent.getChildren().addAll(title, centerContainer);
        root.setCenter(mainContent);
    }

    private VBox createLabeledField(String labelText, TextField field) {
        VBox container = new VBox(8);
        
        Label label = new Label(labelText);
        label.setFont(Font.font("Poppins", 13));
        label.setTextFill(Color.web("#475569"));
        label.setStyle("-fx-font-weight: 700;");
        
        container.getChildren().addAll(label, field);
        return container;
    }

    private void styleTextField(TextField field) {
        field.setStyle(
            "-fx-background-radius: 12px; -fx-border-radius: 12px; " +
            "-fx-border-color: #e2e8f0; -fx-border-width: 2px; " +
            "-fx-padding: 14px 18px; -fx-font-size: 15px;"
        );
        
        field.focusedProperty().addListener((obs, wasFocused, isNowFocused) -> {
            if (isNowFocused) {
                field.setStyle(
                    "-fx-background-radius: 12px; -fx-border-radius: 12px; " +
                    "-fx-border-color: #ec4899; -fx-border-width: 2px; " +
                    "-fx-padding: 14px 18px; -fx-font-size: 15px;"
                );
            } else {
                field.setStyle(
                    "-fx-background-radius: 12px; -fx-border-radius: 12px; " +
                    "-fx-border-color: #e2e8f0; -fx-border-width: 2px; " +
                    "-fx-padding: 14px 18px; -fx-font-size: 15px;"
                );
            }
        });
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(30, 20, 30, 20));
        sidebar.setPrefWidth(260);
        sidebar.setStyle("-fx-background-color: #1e293b;");

        Label logo = new Label("💊 MedicineFX");
        logo.setFont(Font.font("Poppins", 24));
        logo.setTextFill(Color.WHITE);
        logo.setStyle("-fx-font-weight: 900;");
        logo.setPadding(new Insets(0, 0, 20, 0));

        Button dashboardBtn = createSidebarButton("📊 Dashboard", false);
        Button addBtn = createSidebarButton("➕ Add Medicine", true);
        Button listBtn = createSidebarButton("💊 Medicine List", false);
        Button remindersBtn = createSidebarButton("🔔 Reminders", false);
        Button historyBtn = createSidebarButton("📜 History", false);
        Button logoutBtn = createSidebarButton("🚪 Logout", false);

        dashboardBtn.setOnAction(e -> {
            DashboardView view = new DashboardView(stage, username);
            Scene scene = new Scene(view.getView(), 1200, 700);
            stage.setScene(scene);
        });

        addBtn.setOnAction(e -> {
            AddMedicineView view = new AddMedicineView(stage, username);
            Scene scene = new Scene(view.getView(), 1200, 700);
            stage.setScene(scene);
        });

        listBtn.setOnAction(e -> {
            MedicineListView view = new MedicineListView(stage, username);
            Scene scene = new Scene(view.getView(), 1200, 700);
            stage.setScene(scene);
        });

        remindersBtn.setOnAction(e -> {
            ReminderView view = new ReminderView(stage, username);
            Scene scene = new Scene(view.getView(), 1200, 700);
            stage.setScene(scene);
        });

        historyBtn.setOnAction(e -> {
            HistoryView view = new HistoryView(stage, username);
            Scene scene = new Scene(view.getView(), 1200, 700);
            stage.setScene(scene);
        });

        logoutBtn.setOnAction(e -> {
            LoginView view = new LoginView(stage);
            Scene scene = new Scene(view.getView(), 1000, 700);
            stage.setScene(scene);
        });

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        VBox userProfile = new VBox(5);
        userProfile.setPadding(new Insets(15));
        userProfile.setStyle("-fx-background-color: rgba(255,255,255,0.1); -fx-background-radius: 12px;");
        
        Label userName = new Label(username);
        userName.setFont(Font.font("Poppins", 16));
        userName.setTextFill(Color.WHITE);
        userName.setStyle("-fx-font-weight: bold;");
        
        Label userRole = new Label("Patient");
        userRole.setFont(Font.font("Poppins", 12));
        userRole.setTextFill(Color.web("#94a3b8"));
        
        userProfile.getChildren().addAll(userName, userRole);

        sidebar.getChildren().addAll(logo, dashboardBtn, addBtn, listBtn, remindersBtn, historyBtn, logoutBtn, spacer, userProfile);
        return sidebar;
    }

    private Button createSidebarButton(String text, boolean active) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setAlignment(Pos.CENTER_LEFT);
        btn.setPadding(new Insets(12, 20, 12, 20));
        btn.setFont(Font.font("Poppins", 14));
        
        if (active) {
            btn.setStyle(
                "-fx-background-color: linear-gradient(to right, #ec4899, #f43f5e); " +
                "-fx-text-fill: white; -fx-font-weight: bold; " +
                "-fx-background-radius: 10px; -fx-cursor: hand;"
            );
        } else {
            btn.setStyle(
                "-fx-background-color: transparent; -fx-text-fill: white; " +
                "-fx-font-weight: 600; -fx-background-radius: 10px; -fx-cursor: hand;"
            );
            
            btn.setOnMouseEntered(e -> 
                btn.setStyle(
                    "-fx-background-color: rgba(255,255,255,0.1); -fx-text-fill: white; " +
                    "-fx-font-weight: 600; -fx-background-radius: 10px; -fx-cursor: hand;"
                )
            );
            
            btn.setOnMouseExited(e -> 
                btn.setStyle(
                    "-fx-background-color: transparent; -fx-text-fill: white; " +
                    "-fx-font-weight: 600; -fx-background-radius: 10px; -fx-cursor: hand;"
                )
            );
        }

        return btn;
    }

    public Pane getView() {
        return root;
    }
}