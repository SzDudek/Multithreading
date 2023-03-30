package lab05.lab05_pop;
/**
 * @author Szymon Dudek
 *
 */

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    public static AnchorPane root;
    @Override
    public void start(Stage stage) throws Exception {
        try {
            URL fxmlURL = getClass().getResource("Main.fxml");
            root = FXMLLoader.load(fxmlURL);
            Scene scene = new Scene(root);
            //scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
            stage.setTitle("Multithreading");
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void labelTabInit(int rows, int columns, Field field){
        GridPane labels = new GridPane();
        labels.setLayoutX(290);
        labels.setLayoutY(100);
        labels.setHgap(12);
        labels.setVgap(5);
        for(int i=0; i<rows; i++){
            for (int j=0; j<=columns+1;j++){
                Label label = new Label();
                GridPane.setConstraints(label,j,i);
                field.labels.get(i).add(label);
                labels.getChildren().add(label);
                if(j==(columns+1)/2){
                    field.labels.get(i).get(j).setText("_");
                }
                else if(j==0) {
                    if(field.leftSideCondition.get(i)>9){
                        field.labels.get(i).get(j).setText(String.valueOf(field.leftSideCondition.get(i)));
                    }
                    else {
                        field.labels.get(i).get(j).setText(String.valueOf("0"+ field.leftSideCondition.get(i)));
                    }

                }
                else if(j==columns+1){
                    if(field.leftSideCondition.get(i)>9){
                        field.labels.get(i).get(j).setText(String.valueOf(field.rightSideCondition.get(i)));
                    }
                    else {
                        field.labels.get(i).get(j).setText(String.valueOf("0"+ field.rightSideCondition.get(i)));
                    }
                }
                else {
                    field.labels.get(i).get(j).setText(".");
                }
            }
        }
        root.getChildren().add(labels);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
