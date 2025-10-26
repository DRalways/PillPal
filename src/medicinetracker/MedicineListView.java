package medicinetracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import java.util.List;

public class MedicineListView {

    private BorderPane root;
    private Stage stage;
    private String username;

    public MedicineListView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #faf5ff, #fdf4ff);");

        // Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Main content
        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(40));
        mainContent.setAlignment(Pos.TOP_LEFT);

        Label title = new Label("💊 Your Medicines");
        title.setFont(Font.font("Poppins", 32));
        title.setTextFill(Color.web("#1e293b"));
        title.setStyle("-fx-font-weight: 900;");

        // Table
        TableView<Medicine> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        
        TableColumn<Medicine, String> nameCol = new TableColumn<>("Medicine Name");
        nameCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getName()));
        nameCol.setPrefWidth(250);

        TableColumn<Medicine, String> dosageCol = new TableColumn<>("Dosage");
        dosageCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getDosage()));
        dosageCol.setPrefWidth(150);

        TableColumn<Medicine, String> frequencyCol = new TableColumn<>("Frequency");
        frequencyCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getFrequency()));
        frequencyCol.setPrefWidth(200);

        TableColumn<Medicine, String> timeCol = new TableColumn<>("Time");
        timeCol.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getTime()));
        timeCol.setPrefWidth(150);

        table.getColumns().addAll(nameCol, dosageCol, frequencyCol, timeCol);
        table.setPrefHeight(500);

        // Load data
        ObservableList<Medicine> medicines = loadMedicines();
        table.setItems(medicines);

        mainContent.getChildren().addAll(title, table);
        root.setCenter(mainContent);
    }

    private ObservableList<Medicine> loadMedicines() {
        try {
            List<Medicine> list = MedicineService.getMedicines();
            if (list != null && !list.isEmpty()) {
                return FXCollections.observableArrayList(list);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        
        // Return sample data if no medicines found
        return FXCollections.observableArrayList(
            new Medicine("Aspirin", "500mg", "Twice daily", "8:00 AM, 8:00 PM", "Active"),
            new Medicine("Vitamin D", "1000 IU", "Once daily", "9:00 AM", "Active")
        );
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
        Button addBtn = createSidebarButton("➕ Add Medicine", false);
        Button listBtn = createSidebarButton("💊 Medicine List", true);
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