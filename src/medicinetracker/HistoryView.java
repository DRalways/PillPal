package medicinetracker;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;

/**
 * HistoryView — returns Parent; used by caller's scene switching
 * Save as src/medicinetracker/HistoryView.java
 */
public class HistoryView {
    private VBox root;

    public HistoryView() {
        root = new VBox(16);
        root.setPadding(new Insets(20));
        root.setStyle("-fx-background-color: linear-gradient(to right, #fce7f3, #e9d5ff);");

        Label title = new Label("Medication History");
        title.setFont(Font.font("Segoe UI", 22));
        title.setStyle("-fx-font-weight: 800; -fx-text-fill: #1e293b;");

        // filters
        HBox filters = new HBox(10);
        filters.setAlignment(Pos.CENTER_LEFT);
        DatePicker from = new DatePicker();
        DatePicker to = new DatePicker();
        ComboBox<String> medDropdown = new ComboBox<>();
        medDropdown.getItems().addAll("All");
        Button apply = new Button("Apply");
        apply.getStyleClass().add("btn-primary");
        filters.getChildren().addAll(new Label("From"), from, new Label("To"), to, medDropdown, apply);

        // stats
        HBox stats = new HBox(12);
        stats.setAlignment(Pos.CENTER_LEFT);
        stats.getChildren().addAll(
            simpleStat("This Week", "95%"),
            simpleStat("This Month", "87%"),
            simpleStat("Streak Days", "14"),
            simpleStat("Total Meds", "42")
        );

        // chart placeholder
        CategoryAxis x = new CategoryAxis();
        NumberAxis y = new NumberAxis();
        LineChart<String, Number> chart = new LineChart<>(x, y);
        chart.setTitle("Adherence (placeholder)");
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.getData().add(new XYChart.Data<>("Mon", 80));
        series.getData().add(new XYChart.Data<>("Tue", 85));
        series.getData().add(new XYChart.Data<>("Wed", 92));
        chart.getData().add(series);
        chart.setPrefHeight(220);

        // table
        TableView<Medicine> table = new TableView<>();
        TableColumn<Medicine, String> dateCol = new TableColumn<>("Date");
        dateCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty("2025-10-01"));

        TableColumn<Medicine, String> medCol = new TableColumn<>("Medicine");
        medCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getName()));

        TableColumn<Medicine, String> statusCol = new TableColumn<>("Status");
        statusCol.setCellValueFactory(c -> new javafx.beans.property.SimpleStringProperty(c.getValue().getStatus()));

        table.getColumns().addAll(dateCol, medCol, statusCol);
        table.setItems(loadTableData());
        table.setPrefHeight(240);

        root.getChildren().addAll(title, filters, stats, chart, table);
    }

    public HistoryView(Stage stage, String username) {
        //TODO Auto-generated constructor stub
    }

    private VBox simpleStat(String label, String value) {
        VBox c = new VBox(4);
        c.setPadding(new Insets(12));
        c.setStyle("-fx-background-color: white; -fx-background-radius: 12px; -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.06), 8,0,0,4);");
        c.getChildren().addAll(new Label(label), new Label(value));
        return c;
    }

    private ObservableList<Medicine> loadTableData() {
        try {
            java.util.List<Medicine> list = MedicineService.getMedicines();
            if (list != null) return FXCollections.observableArrayList(list);
        } catch (Exception ex) {
            // ignore, fallback
        }
        return FXCollections.observableArrayList(
            new Medicine("Paracetamol", "500mg", "Twice a day", "08:00 AM", "Completed"),
            new Medicine("Vitamin D", "1000 IU", "Weekly", "09:00 AM", "Completed")
        );
    }

    public Parent getView() {
        return root;
    }
}