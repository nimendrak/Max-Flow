package GuiAppResources;

import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

//button styling
class ButtonFX {
    void closeBtn(Button closeBtn, double prefWidth) {
        closeBtn.setPrefWidth(prefWidth);
        closeBtn.setPadding(new Insets(8));
        closeBtn.setFont(new Font("Arial Bold", 15));
        closeBtn.setTextFill(Paint.valueOf("f4f4f4"));
        closeBtn.setStyle("-fx-background-color: rgba(227,35,109,1); -fx-background-radius: 20;");
        closeBtn.setOnMouseEntered(event -> {
            closeBtn.setStyle("-fx-background-color: rgba(175,33,90,1); -fx-background-radius: 20;");
        });
        closeBtn.setOnMouseExited(event -> {
            closeBtn.setStyle("-fx-background-color: rgba(227,35,109,1); -fx-background-radius: 20;");
        });
        closeBtn.setCursor(Cursor.HAND);
    }

    void addBtn(Button addBtn, double prefWidth) {
        addBtn.setPrefWidth(prefWidth);
        addBtn.setPadding(new Insets(8));
        addBtn.setFont(new Font("Arial Bold", 15));
        addBtn.setTextFill(Paint.valueOf("f4f4f4"));
        addBtn.setStyle("-fx-background-color: #178A94; -fx-background-radius: 20;");
        addBtn.setOnMouseEntered(event -> {
            addBtn.setStyle("-fx-background-color: rgb(0,100,93); -fx-background-radius: 20;");
        });
        addBtn.setOnMouseExited(event -> {
            addBtn.setStyle("-fx-background-color: #178A94; -fx-background-radius: 20;");
        });
        addBtn.setCursor(Cursor.HAND);
    }
}
