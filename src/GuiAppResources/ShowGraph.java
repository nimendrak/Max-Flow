package GuiAppResources;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ShowGraph extends Application {
    private int[][] graphMatrix;
    private int VERTICES;

    public ShowGraph(int[][] graphMatrix, int VERTICES) {
        this.graphMatrix = graphMatrix;
        this.VERTICES = VERTICES;
    }

    @Override
    public void start(Stage primaryStage) {
        Stage mainWindow = new Stage();
        mainWindow.setResizable(false);
        mainWindow.setTitle("Simulator");
        mainWindow.initModality(Modality.WINDOW_MODAL);

//      header section starts here

        HBox header = new HBox();
        header.setSpacing(10);

        Label headerLabel = new Label("Find Maximum Flow For a Given Network");
        headerLabel.setFont(new Font("Arial Bold", 18));
        headerLabel.setPadding(new Insets(5, 360, 0, 0));

        String[] types = {"Points Gained", "Goals Scored", "Num of Wins"};
        ComboBox<String> typeBox = new ComboBox<>(FXCollections.observableArrayList(types));
        typeBox.setPrefWidth(127);
        typeBox.getSelectionModel().selectFirst();
        typeBox.setStyle("-fx-border-color:#00A69C; -fx-border-radius: 10; -fx-border-width: 2;-fx-background-color: null; -fx-background-radius: 2;");

        Button generateBtn = new Button("Generate");
        new ButtonFX().addBtn(generateBtn, 120);

        header.getChildren().addAll(headerLabel, typeBox, generateBtn);

//      header section ends here and section 1 of the flowPane starts here

        VBox sectionOne = new VBox();
        sectionOne.setSpacing(25);

        HBox sortingOptions = new HBox();
        sortingOptions.setSpacing(10);

        ScrollPane pointsTable = new ScrollPane();
        pointsTable.setPrefHeight(550);
        pointsTable.setPrefWidth(980);

        Separator sectionOneSep = new Separator();
        sectionOneSep.setVisible(false);
        sectionOneSep.setOrientation(Orientation.VERTICAL);
        sectionOneSep.setPadding(new Insets(0, 8, 0, 8));

        sectionOne.getChildren().addAll(sortingOptions, pointsTable);

        generateBtn.setOnAction(event -> {
            pointsTable.setContent((Node) JungGraph.displayGraph(graphMatrix, VERTICES));
        });

//      section 1 of the flowPane ends here and section 2 starts here

        HBox footer = new HBox();
        footer.setPadding(new Insets(-10, 0, 0, 0));

        VBox footerSectionOne = new VBox();

        Label footerHeader = new Label("Abbreviations");
        footerHeader.setPadding(new Insets(7, 0, 10, 0));
        footerHeader.setFont(new Font("Arial Bold", 15));
        footerHeader.setStyle("-fx-underline: true");

        Label abbrev = new Label("W - Wins, L - Loss, D - Draw, MP - Matches Played," +
                " GR - Goals Received, GS - Goals Scored");
        abbrev.setFont(new Font("Arial Bold", 15));

        VBox footerSectionTwo = new VBox();
        footerSectionTwo.setPadding(new Insets(15, 0, 0, 257));

        Button closeBtn = new Button("Close");
        new ButtonFX().closeBtn(closeBtn, 75);

        closeBtn.setOnAction(e -> {
            mainWindow.close();
        });

        mainWindow.setOnCloseRequest(event -> {
            mainWindow.close();
        });

        footerSectionOne.getChildren().addAll(footerHeader, abbrev);
        footerSectionTwo.getChildren().addAll(closeBtn);
        footer.getChildren().addAll(footerSectionOne, footerSectionTwo);

        FlowPane flowPane = new FlowPane();
        flowPane.setHgap(10);
        flowPane.setVgap(10);
        flowPane.setPadding(new Insets(30));
        flowPane.getChildren().addAll(header, sectionOne, sectionOneSep, footer);

        Scene scene = new Scene(flowPane, 1045, 750);

        mainWindow.setScene(scene);
        mainWindow.showAndWait();
    }
}
