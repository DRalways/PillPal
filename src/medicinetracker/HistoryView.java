package medicinetracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class HistoryView {
    private BorderPane root;
    private Stage stage;
    private String username;
    public HistoryView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #fce7f3, #e9d5ff);");

        // Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Main content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(40));

        Label title = new Label("📜 Medication History");
        title.setFont(Font.font("Poppins", 32));
        title.setTextFill(Color.web("#1e293b"));
        title.setStyle("-fx-font-weight: 900;");

        // Stats
        HBox stats = new HBox(15);
        stats.setAlignment(Pos.CENTER_LEFT);
        stats.getChildren().addAll(
            simpleStat("This Week", "95%"),
            simpleStat("This Month", "87%"),
            simpleStat("Streak Days", "14"),
            simpleStat("Total Meds", "42")
        );

        // Chart
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(x, y);
        chart.setTitle("Adherence Over Time");
        chart.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Adherence %");
        series.getData().add(new XYChart.Data<>("Mon", 80));
        series.getData().add(new XYChart.Data<>("Tue", 85));
        series.getData().add(new XYChart.Data<>("Wed", 92));
        series.getData().add(new XYChart.Data<>("Thu", 88));
        series.getData().add(new XYChart.Data<>("Fri", 95));
        series.getData().add(new XYChart.Data<>("Sat", 90));
        series.getData().add(new XYChart.Data<>("Sun", 93));
        chart.getData().add(series);
        chart.setPrefHeight(250);

        // Table
        TableView<Medicine> table = new TableView<>();
        table.setStyle("-fx-background-color: white; -fx-background-radius: 15px;");
        
        TableColumn<Medicine, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("2025-10-26"));
        dateCol.setPrefWidth(150);

        TableColumn<Medicine, String> medCol = new TableColumn<>("Medicine");
        medCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));
        medCol.setPrefWidth(200);

        TableColumn<Medicine, String> dosageCol = new TableColumn<>("Dosage");
        dosageCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getDosage()));
        dosageCol.setPrefWidth(150);

        TableColumn<Medicine, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));
        statusCol.setPrefWidth(150);

        table.getColumns().addAll(dateCol, medCol, dosageCol, statusCol);
        table.setItems(loadTableData());
        table.setPrefHeight(280);

        mainContent.getChildren().addAll(title, stats, chart, table);
        root.setCenter(mainContent);
    }

    private VBox simpleStat(String label, String value) {
        VBox c = new VBox(8);
        c.setPadding(new Insets(20));
        c.setStyle("-fx-background-color: white; -fx-background-radius: 15px; " +
                  "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 10,0,0,4);");
        c.setPrefWidth(180);
        
        Label lbl = new Label(label);
        lbl.setFont(Font.font("Poppins", 12));
        lbl.setTextFill(Color.web("#64748b"));
        lbl.setStyle("-fx-font-weight: bold;");
        
        Label val = new Label(value);
        val.setFont(Font.font("Poppins", 28));
        val.setTextFill(Color.web("#1e293b"));
        val.setStyle("-fx-font-weight: 900;");
        
        c.getChildren().addAll(lbl, val);
        return c;
    }

    private ObservableList<Medicine> loadTableData() {
        try {
            java.util.List<Medicine> list = MedicineService.getMedicines();
            if (list != null && !list.isEmpty()) {
                return FXCollections.observableArrayList(list);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return FXCollections.observableArrayList(
            new Medicine("Paracetamol", "500mg", "Twice a day", "08:00 AM", "Completed"),
            new Medicine("Vitamin D", "1000 IU", "Once daily", "09:00 AM", "Completed"),
            new Medicine("Aspirin", "81mg", "Once daily", "08:00 AM", "Completed")
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
        Button listBtn = createSidebarButton("💊 Medicine List", false);
        Button remindersBtn = createSidebarButton("🔔 Reminders", false);
        Button historyBtn = createSidebarButton("📜 History", true);
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

    public Parent getView() {
        return root;
    }
}