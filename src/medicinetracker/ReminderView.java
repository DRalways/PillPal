package medicinetracker;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.*;
import javafx.util.Duration;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class ReminderView {
    private BorderPane root;
    private Stage stage;
    private String username;

    public ReminderView(Stage stage, String username) {
        this.stage = stage;
        this.username = username;

        root = new BorderPane();
        root.setStyle("-fx-background-color: linear-gradient(to right, #fce7f3, #e9d5ff);");

        VBox sidebar = createSidebar();
        root.setLeft(sidebar);

        VBox mainContent = new VBox(25);
        mainContent.setPadding(new Insets(40));

        // Header
        HBox header = new HBox(20);
        header.setAlignment(Pos.CENTER_LEFT);
        
        Label title = new Label("🔔 Reminders");
        title.setFont(Font.font("Poppins", 38));
        title.setTextFill(Color.web("#1e293b"));
        title.setStyle("-fx-font-weight: 900;");

        Button testReminderBtn = new Button("🔊 Test Reminder");
        testReminderBtn.setStyle(
            "-fx-background-color: linear-gradient(to right, #f59e0b, #f97316); " +
            "-fx-text-fill: white; -fx-font-weight: bold; " +
            "-fx-background-radius: 12px; -fx-padding: 12px 25px; " +
            "-fx-cursor: hand; -fx-font-size: 14px;"
        );

        testReminderBtn.setOnAction(e -> showTestReminder());

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        header.getChildren().addAll(title, spacer, testReminderBtn);

        Label subtitle = new Label("Today's medication schedule - " + 
            java.time.LocalDate.now().format(java.time.format.DateTimeFormatter.ofPattern("EEEE, MMMM d, yyyy")));
        subtitle.setFont(Font.font("Poppins", 15));
        subtitle.setTextFill(Color.web("#64748b"));
        subtitle.setStyle("-fx-font-weight: 600;");

        // Reminder cards container
        VBox remindersContainer = new VBox(18);
        
        // Load medicines and create reminder cards
        try {
            java.util.List<Medicine> medicines = MedicineService.getMedicines();
            if (medicines != null && !medicines.isEmpty()) {
                for (Medicine med : medicines) {
                    remindersContainer.getChildren().add(createReminderCard(med));
                }
            } else {
                // Add sample reminders if no medicines found
                remindersContainer.getChildren().addAll(
                    createReminderCard(new Medicine("Aspirin", "500mg", "Twice daily", "08:00 AM", "Pending")),
                    createReminderCard(new Medicine("Vitamin D", "1000 IU", "Once daily", "09:00 AM", "Completed")),
                    createReminderCard(new Medicine("Metformin", "850mg", "Twice daily", "12:00 PM", "Upcoming"))
                );
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            Label errorLabel = new Label("⚠️ Error loading reminders");
            errorLabel.setTextFill(Color.web("#ef4444"));
            remindersContainer.getChildren().add(errorLabel);
        }

        // Scroll pane for reminders
        ScrollPane scrollPane = new ScrollPane(remindersContainer);
        scrollPane.setFitToWidth(true);
        scrollPane.setStyle("-fx-background: transparent; -fx-background-color: transparent;");
        scrollPane.setPrefHeight(500);

        mainContent.getChildren().addAll(header, subtitle, scrollPane);
        root.setCenter(mainContent);
    }

    private HBox createReminderCard(Medicine medicine) {
        HBox card = new HBox(25);
        card.setPadding(new Insets(25, 30, 25, 30));
        card.setAlignment(Pos.CENTER_LEFT);
        card.setStyle(
            "-fx-background-color: white; -fx-background-radius: 18px; " +
            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 4);"
        );

        // Time box
        VBox timeBox = new VBox(5);
        timeBox.setAlignment(Pos.CENTER);
        timeBox.setPadding(new Insets(18));
        timeBox.setPrefWidth(100);
        
        String status = medicine.getStatus();
        String timeBoxColor = "#ec4899"; // Default pink
        
        if (status.equalsIgnoreCase("Completed")) {
            timeBoxColor = "#10b981"; // Green
        } else if (status.equalsIgnoreCase("Upcoming")) {
            timeBoxColor = "#3b82f6"; // Blue
        } else if (status.equalsIgnoreCase("Missed")) {
            timeBoxColor = "#ef4444"; // Red
        }
        
        timeBox.setStyle(
            "-fx-background-color: linear-gradient(to bottom right, " + timeBoxColor + ", " + timeBoxColor + "DD); " +
            "-fx-background-radius: 15px;"
        );

        String time = medicine.getTime();
        String[] timeParts = time.split(" ");
        String timeValue = timeParts.length > 0 ? timeParts[0] : time;
        String timePeriod = timeParts.length > 1 ? timeParts[1] : "";

        Label timeLabel = new Label(timeValue);
        timeLabel.setFont(Font.font("Poppins", 24));
        timeLabel.setTextFill(Color.WHITE);
        timeLabel.setStyle("-fx-font-weight: 900;");

        Label periodLabel = new Label(timePeriod);
        periodLabel.setFont(Font.font("Poppins", 13));
        periodLabel.setTextFill(Color.web("#ffffff"));
        periodLabel.setStyle("-fx-font-weight: 700;");

        timeBox.getChildren().addAll(timeLabel, periodLabel);

        // Medicine details
        VBox detailsBox = new VBox(8);
        detailsBox.setAlignment(Pos.CENTER_LEFT);
        HBox.setHgrow(detailsBox, Priority.ALWAYS);

        Label medicineName = new Label(medicine.getName());
        medicineName.setFont(Font.font("Poppins", 22));
        medicineName.setTextFill(Color.web("#1e293b"));
        medicineName.setStyle("-fx-font-weight: 900;");

        Label medicineDetails = new Label(medicine.getDosage() + " • " + medicine.getFrequency());
        medicineDetails.setFont(Font.font("Poppins", 14));
        medicineDetails.setTextFill(Color.web("#64748b"));
        medicineDetails.setStyle("-fx-font-weight: 600;");

        detailsBox.getChildren().addAll(medicineName, medicineDetails);

        // Status badge
        Label statusBadge = new Label(status.toUpperCase());
        statusBadge.setPadding(new Insets(8, 20, 8, 20));
        statusBadge.setFont(Font.font("Poppins", 12));
        statusBadge.setStyle("-fx-font-weight: 800;");
        
        if (status.equalsIgnoreCase("Completed")) {
            statusBadge.setStyle(
                "-fx-background-color: #d1fae5; -fx-text-fill: #065f46; " +
                "-fx-background-radius: 20px; -fx-font-weight: 800;"
            );
        } else if (status.equalsIgnoreCase("Pending")) {
            statusBadge.setStyle(
                "-fx-background-color: #fef3c7; -fx-text-fill: #92400e; " +
                "-fx-background-radius: 20px; -fx-font-weight: 800;"
            );
        } else if (status.equalsIgnoreCase("Upcoming")) {
            statusBadge.setStyle(
                "-fx-background-color: #dbeafe; -fx-text-fill: #1e40af; " +
                "-fx-background-radius: 20px; -fx-font-weight: 800;"
            );
        } else if (status.equalsIgnoreCase("Missed")) {
            statusBadge.setStyle(
                "-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; " +
                "-fx-background-radius: 20px; -fx-font-weight: 800;"
            );
        }

        // Action buttons
        VBox actionsBox = new VBox(10);
        actionsBox.setAlignment(Pos.CENTER);

        if (!status.equalsIgnoreCase("Completed")) {
            Button takeBtn = new Button("✓");
            takeBtn.setPrefSize(45, 45);
            takeBtn.setStyle(
                "-fx-background-color: #d1fae5; -fx-text-fill: #065f46; " +
                "-fx-background-radius: 12px; -fx-font-size: 20px; " +
                "-fx-font-weight: bold; -fx-cursor: hand;"
            );
            
            takeBtn.setOnMouseEntered(e -> {
                takeBtn.setStyle(
                    "-fx-background-color: #10b981; -fx-text-fill: white; " +
                    "-fx-background-radius: 12px; -fx-font-size: 20px; " +
                    "-fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(16, 185, 129, 0.4), 10, 0, 0, 3);"
                );
            });
            
            takeBtn.setOnMouseExited(e -> {
                takeBtn.setStyle(
                    "-fx-background-color: #d1fae5; -fx-text-fill: #065f46; " +
                    "-fx-background-radius: 12px; -fx-font-size: 20px; " +
                    "-fx-font-weight: bold; -fx-cursor: hand;"
                );
            });

            takeBtn.setOnAction(e -> {
                showMedicineTakenNotification(medicine.getName());
                // Update status in UI
                statusBadge.setText("COMPLETED");
                statusBadge.setStyle(
                    "-fx-background-color: #d1fae5; -fx-text-fill: #065f46; " +
                    "-fx-background-radius: 20px; -fx-font-weight: 800;"
                );
                timeBox.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, #10b981, #10b981DD); " +
                    "-fx-background-radius: 15px;"
                );
                actionsBox.getChildren().clear();
            });

            Button skipBtn = new Button("⤬");
            skipBtn.setPrefSize(45, 45);
            skipBtn.setStyle(
                "-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; " +
                "-fx-background-radius: 12px; -fx-font-size: 20px; " +
                "-fx-font-weight: bold; -fx-cursor: hand;"
            );
            
            skipBtn.setOnMouseEntered(e -> {
                skipBtn.setStyle(
                    "-fx-background-color: #ef4444; -fx-text-fill: white; " +
                    "-fx-background-radius: 12px; -fx-font-size: 20px; " +
                    "-fx-font-weight: bold; -fx-cursor: hand; " +
                    "-fx-effect: dropshadow(gaussian, rgba(239, 68, 68, 0.4), 10, 0, 0, 3);"
                );
            });
            
            skipBtn.setOnMouseExited(e -> {
                skipBtn.setStyle(
                    "-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; " +
                    "-fx-background-radius: 12px; -fx-font-size: 20px; " +
                    "-fx-font-weight: bold; -fx-cursor: hand;"
                );
            });

            skipBtn.setOnAction(e -> {
                statusBadge.setText("MISSED");
                statusBadge.setStyle(
                    "-fx-background-color: #fee2e2; -fx-text-fill: #991b1b; " +
                    "-fx-background-radius: 20px; -fx-font-weight: 800;"
                );
                timeBox.setStyle(
                    "-fx-background-color: linear-gradient(to bottom right, #ef4444, #ef4444DD); " +
                    "-fx-background-radius: 15px;"
                );
                actionsBox.getChildren().clear();
            });

            actionsBox.getChildren().addAll(takeBtn, skipBtn);
        }

        card.getChildren().addAll(timeBox, detailsBox, statusBadge, actionsBox);

        // Hover effect
        card.setOnMouseEntered(e -> {
            card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 18px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.15), 20, 0, 0, 8); " +
                "-fx-cursor: hand;"
            );
        });

        card.setOnMouseExited(e -> {
            card.setStyle(
                "-fx-background-color: white; -fx-background-radius: 18px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.08), 15, 0, 0, 4);"
            );
        });

        return card;
    }

    private void showTestReminder() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("🔔 Medicine Reminder");
        alert.setHeaderText("Time to take your medicine!");
        alert.setContentText("💊 Aspirin 500mg\n⏰ 8:00 AM\n\nDon't forget to take your medication!");
        
        // Custom styling
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: white; " +
            "-fx-font-family: 'Poppins';"
        );
        
        // Play sound (you can add actual sound file here)
        playReminderSound();
        
        alert.showAndWait();
    }

    private void showMedicineTakenNotification(String medicineName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("✅ Medicine Taken");
        alert.setHeaderText("Great job!");
        alert.setContentText("You've marked " + medicineName + " as taken.\n\nKeep up the good work! 🎉");
        
        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle(
            "-fx-background-color: white; " +
            "-fx-font-family: 'Poppins';"
        );
        
        alert.showAndWait();
    }

    private void playReminderSound() {
        // Simple beep sound (cross-platform)
        java.awt.Toolkit.getDefaultToolkit().beep();
        
        // If you want to play a custom sound file, use this instead:
        /*
        try {
            String soundFile = "reminder.wav"; // Put your sound file in resources
            java.io.InputStream audioSrc = getClass().getResourceAsStream(soundFile);
            java.io.InputStream bufferedIn = new java.io.BufferedInputStream(audioSrc);
            javax.sound.sampled.AudioInputStream audioStream = javax.sound.sampled.AudioSystem.getAudioInputStream(bufferedIn);
            javax.sound.sampled.Clip clip = javax.sound.sampled.AudioSystem.getClip();
            clip.open(audioStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        */
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
        Button remindersBtn = createSidebarButton("🔔 Reminders", true);
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