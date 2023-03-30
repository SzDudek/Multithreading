package lab05.lab05_pop;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import static lab05.lab05_pop.Main.labelTabInit;


public class Controller implements Initializable {

    private SpinnerValueFactory<Integer> valueFactoryPlayers;
    private SpinnerValueFactory<Integer> valueFactoryBalls;

    private int rows = 10, columns = 9, players, balls, playerSpeed, ballSpeed;
    private Field field;

    private final String[] speeds ={"1","2","3"};

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label columnsCount;
    @FXML
    private Label rowsCount;
    @FXML
    private Label playerCount;
    @FXML
    private Label ballsCount;
    @FXML
    private Label vPlayers;
    @FXML
    private Label vBalls;
    @FXML
    private Label notification;


    @FXML
    private TextField columnsVal;
    @FXML
    private TextField rowVal;

    @FXML
    private Spinner<Integer> playersSpinner;
    @FXML
    private Spinner<Integer> ballsSpinner;

    @FXML
    private ChoiceBox<String> vPlayersChoice;
    @FXML
    private ChoiceBox<String> vBallsChoice;

    @FXML
    private Button startButton;
    @FXML
    private Button endButton;
    @FXML
    private Button columnsConfirm;
    @FXML
    private Button rowsConfirm;

    public void confirmColumns(){
        columns = Integer.parseInt(columnsVal.getText());
        System.out.println(columns);
    }

    public void confirmRows(){
        rows = Integer.parseInt(rowVal.getText());
        System.out.println(rows);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         valueFactoryPlayers = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, rows);
         valueFactoryBalls = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, rows);

        valueFactoryPlayers.setValue(2);
        valueFactoryBalls.setValue(2);

        playersSpinner.setValueFactory(valueFactoryPlayers);
        ballsSpinner.setValueFactory(valueFactoryBalls);

        players = playersSpinner.getValue();
        balls = ballsSpinner.getValue();

        startButton.setOnAction(actionEvent -> {
            if(columns %2==1){
                notification.setTextFill(Color.LAWNGREEN);
                notification.setText("Simulation is running");
                field = new Field(columns, rows, playerSpeed, ballSpeed, players, balls);
                labelTabInit(rows, columns, field);
                field.game();

            }
            else {
                notification.setTextFill(Color.RED);
                notification.setText("Count of columns must be an odd number!");
            }
        });

        endButton.setOnAction(actionEvent -> {
            System.out.println(field.threads);
            for(MyThread w : field.threads){
                w.setEnd(true);
            }

            System.out.print("\nRight side: ");
            for(int score : field.leftSideCondition){
                System.out.print(score+" ");
            }
            System.out.print("\nLeft side: ");
            for (int score : field.rightSideCondition){
                System.out.print(score+" ");
            }
            System.out.println("\n");
            for(ArrayList<Integer> array : field.fieldCondition){
                for(int i : array){
                    System.out.print(i);
                }
                System.out.println();
            }
            notification.setTextFill(Color.DARKGRAY
            );
            notification.setText("End of simulation");
        });

        playersSpinner.valueProperty().addListener((observableValue, integer, t1) -> players = playersSpinner.getValue());

        ballsSpinner.valueProperty().addListener((observableValue, integer, t1) -> balls = ballsSpinner.getValue());

        rowsConfirm.setOnAction(actionEvent -> {
            confirmRows();
            valueFactoryPlayers = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, rows);
            valueFactoryBalls = new SpinnerValueFactory.IntegerSpinnerValueFactory(2, rows);

            if(playersSpinner.getValue()> rows){
                valueFactoryPlayers.setValue(2);
                playersSpinner.setValueFactory(valueFactoryPlayers);
            }
            else {
                valueFactoryPlayers.setValue(playersSpinner.getValue());
                playersSpinner.setValueFactory(valueFactoryPlayers);
            }

            if(ballsSpinner.getValue()> rows){
                valueFactoryBalls.setValue(3);
                ballsSpinner.setValueFactory(valueFactoryBalls);
            }
            else{
                valueFactoryBalls.setValue(ballsSpinner.getValue());
                ballsSpinner.setValueFactory(valueFactoryBalls);
            }
        });

        vPlayersChoice.getItems().addAll(speeds);
        vBallsChoice.getItems().addAll(speeds);
        vPlayersChoice.setOnAction(this::setPlayerSpeed);
        vBallsChoice.setOnAction(this::setBallSpeed);
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public int getColumns() {
        return columns;
    }

    public void setColumns(int columns) {
        this.columns = columns;
    }

    public void setPlayerSpeed(ActionEvent event){
        playerSpeed =Integer.parseInt(vPlayersChoice.getValue());
        System.out.println(playerSpeed);
    }

    public void setBallSpeed(ActionEvent event){
        ballSpeed =Integer.parseInt(vBallsChoice.getValue());
        System.out.println(ballSpeed);
    }

    public int getPlayers() { return players; }

    public int getBalls() { return balls; }

    public int getPlayerSpeed() { return playerSpeed; }

    public int getBallSpeed() { return ballSpeed; }
}
