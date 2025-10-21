package medicinetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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

        VBox container = new VBox(15);
        container.setPadding(new Insets(40));
        container.setAlignment(Pos.TOP_CENTER);

        Label title = new Label("➕ Add New Medicine");
        title.setFont(Font.font("Poppins", 26));

        TextField nameField = new TextField();
        nameField.setPromptText("Medicine Name");

        TextField dosageField = new TextField();
        dosageField.setPromptText("Dosage (e.g. 500mg)");

        TextField timeField = new TextField();
        timeField.setPromptText("Time (e.g. Morning, 8 AM)");

        Button saveBtn = new Button("💾 Save");
        saveBtn.setStyle("-fx-background-color: #6366f1; -fx-text-fill: white; -fx-font-weight: bold;");

        Label msg = new Label();

        saveBtn.setOnAction(e -> {
            String name = nameField.getText().trim();
            String dosage = dosageField.getText().trim();
            String time = timeField.getText().trim();

            if (name.isEmpty() || dosage.isEmpty() || time.isEmpty()) {
                msg.setText("Please fill all fields!");
                return;
            }

            msg.setText("✅ Medicine saved for " + username + "!");
            nameField.clear();
            dosageField.clear();
            timeField.clear();
        });

        container.getChildren().addAll(title, nameField, dosageField, timeField, saveBtn, msg);
        root.setCenter(container);
    }

    public Pane getView() {
        return root;
    }
}