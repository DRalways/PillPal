package medicinetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class MedicineListView {

    private BorderPane root;
    private Stage stage;
    private String username;

    public MedicineListView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #faf5ff, #fdf4ff);");

        VBox container = new VBox(15);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("💊 Your Medicines");
        title.setFont(Font.font("Poppins", 26));

        TableView<String> table = new TableView<>();
        TableColumn<String, String> nameCol = new TableColumn<>("Name");
        TableColumn<String, String> dosageCol = new TableColumn<>("Dosage");
        TableColumn<String, String> timeCol = new TableColumn<>("Time");

        table.getColumns().addAll(nameCol, dosageCol, timeCol);
        table.setPrefHeight(400);

        container.getChildren().addAll(title, table);
        root.setCenter(container);
    }

    public Pane getView() {
        return root;
    }
}