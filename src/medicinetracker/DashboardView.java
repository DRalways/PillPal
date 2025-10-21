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

        Label subHeader = new Label("Here’s your medication summary:");
        subHeader.setFont(Font.font("Poppins", 16));
        subHeader.setTextFill(Color.web("#64748b"));

        mainContent.getChildren().addAll(header, subHeader);
        root.setCenter(mainContent);
    }

    private VBox createSidebar() {
        VBox sidebar = new VBox(20);
        sidebar.setPadding(new Insets(30));
        sidebar.setPrefWidth(240);
        sidebar.setStyle("-fx-background-color: #1e293b;");

        Label logo = new Label("💊 MedicineFX");
        logo.setFont(Font.font("Poppins", 22));
        logo.setTextFill(Color.WHITE);

        Button dashboardBtn = createSidebarButton("📊 Dashboard");
        Button addBtn = createSidebarButton("➕ Add Medicine");
        Button listBtn = createSidebarButton("💊 Medicine List");
        Button historyBtn = createSidebarButton("📜 History");
        Button logoutBtn = createSidebarButton("🚪 Logout");

        dashboardBtn.setOnAction(e -> {
            Scene scene = new Scene(new DashboardView(stage, username).getView(), 1200, 700);
            stage.setScene(scene);
        });

        addBtn.setOnAction(e -> {
            AddMedicineView addView = new AddMedicineView(stage, username);
            Scene scene = new Scene(addView.getView(), 1200, 700);
            stage.setScene(scene);
        });

        listBtn.setOnAction(e -> {
            MedicineListView listView = new MedicineListView(stage, username);
            Scene scene = new Scene(listView.getView(), 1200, 700);
            stage.setScene(scene);
        });

        historyBtn.setOnAction(e -> {
            HistoryView historyView = new HistoryView(stage, username);
            Scene scene = new Scene(historyView.getView(), 1200, 700);
            stage.setScene(scene);
        });

        logoutBtn.setOnAction(e -> {
            LoginView loginView = new LoginView(stage);
            Scene scene = new Scene(loginView.getView(), 1000, 700);
            stage.setScene(scene);
        });

        sidebar.getChildren().addAll(logo, dashboardBtn, addBtn, listBtn, historyBtn, logoutBtn);
        return sidebar;
    }

    private Button createSidebarButton(String text) {
        Button btn = new Button(text);
        btn.setMaxWidth(Double.MAX_VALUE);
        btn.setStyle(
            "-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 15px; "
          + "-fx-font-weight: bold; -fx-alignment: CENTER-LEFT;"
        );

        btn.setOnMouseEntered(e -> btn.setStyle("-fx-background-color: #334155; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER-LEFT;"));
        btn.setOnMouseExited(e -> btn.setStyle("-fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 15px; -fx-font-weight: bold; -fx-alignment: CENTER-LEFT;"));

        return btn;
    }

    // ✅ Getter for root layout
    public Pane getView() {
        return root;
    }
}