package medicinetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class DashboardView {

    private BorderPane root;
    private Stage stage;
    private String username;

    public DashboardView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #e9d5ff, #fce7f3, #fff1f2);");

        // Sidebar
        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        // Main content
        VBox mainContent = new VBox(20);
        mainContent.setPadding(new Insets(30));
        mainContent.setAlignment(Pos.TOP_LEFT);

        Label header = new Label("Welcome, " + username + " 👋");
        header.setFont(Font.font("Poppins", 28));
        header.setTextFill(Color.web("#1e293b"));

        Label subHeader = new Label("Here's your medication summary:");
        subHeader.setFont(Font.font("Poppins", 16));
        subHeader.setTextFill(Color.web("#64748b"));

        // Stats Grid
        HBox statsGrid = new HBox(20);
        statsGrid.setAlignment(Pos.CENTER_LEFT);
        
        VBox stat1 = createStatCard("Active Meds", "8", "#ec4899");
        VBox stat2 = createStatCard("Today's Doses", "12", "#6366f1");
        VBox stat3 = createStatCard("Upcoming", "3", "#10b981");
        VBox stat4 = createStatCard("Adherence", "94%", "#f59e0b");
        
        statsGrid.getChildren().addAll(stat1, stat2, stat3, stat4);

        mainContent.getChildren().addAll(header, subHeader, statsGrid);
        root.setCenter(mainContent);
    }

    private VBox createStatCard(String label, String value, String color) {
        VBox card = new VBox(10);
        card.setPadding(new Insets(25));
        card.setStyle("-fx-background-color: white; -fx-background-radius: 15px; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 10, 0, 0, 3);");
        card.setPrefWidth(180);

        Label lblLabel = new Label(label);
        lblLabel.setFont(Font.font("Poppins", 12));
        lblLabel.setTextFill(Color.web("#64748b"));
        lblLabel.setStyle("-fx-font-weight: bold;");

        Label lblValue = new Label(value);
        lblValue.setFont(Font.font("Poppins", 32));
        lblValue.setTextFill(Color.web(color));
        lblValue.setStyle("-fx-font-weight: 900;");

        card.getChildren().addAll(lblLabel, lblValue);
        return card;
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(15);
        sidebar.setPadding(new Insets(30, 20, 30, 20));
        sidebar.setPrefWidth(260);
        sidebar.setStyle("-fx-background-color: #1e293b;");

        // Logo
        Label logo = new Label("💊 MedicineFX");
        logo.setFont(Font.font("Poppins", 24));
        logo.setTextFill(Color.WHITE);
        logo.setStyle("-fx-font-weight: 900;");
        logo.setPadding(new Insets(0, 0, 20, 0));

        // Navigation buttons
        Button dashboardBtn = createSidebarButton("📊 Dashboard", true);
        Button addBtn = createSidebarButton("➕ Add Medicine", false);
        Button listBtn = createSidebarButton("💊 Medicine List", false);
        Button remindersBtn = createSidebarButton("🔔 Reminders", false);
        Button historyBtn = createSidebarButton("📜 History", false);
        Button logoutBtn = createSidebarButton("🚪 Logout", false);

        // Button actions
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

        // Spacer
        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // User profile at bottom
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