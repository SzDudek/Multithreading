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

    private SpinnerValueFactory<Integer> valueFactoryGracze;
    private SpinnerValueFactory<Integer> valueFactoryPilki;

    private int wiersze = 10, kolumny = 9, gracze, pilki, szybkoscGraczy, szybkoscPilek;
    private Field field;

    private final String[] szybkosci ={"1","2","3"};

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Label ileKolumn;
    @FXML
    private Label ileWierszy;
    @FXML
    private Label ileGraczy;
    @FXML
    private Label ilePilek;
    @FXML
    private Label vGraczy;
    @FXML
    private Label vPilek;
    @FXML
    private Label powiadomienie;


    @FXML
    private TextField wartKolumn;
    @FXML
    private TextField wartWierszy;

    @FXML
    private Spinner<Integer> graczeSpinner;
    @FXML
    private Spinner<Integer> pilkiSpinner;

    @FXML
    private ChoiceBox<String> vGraczyChoice;
    @FXML
    private ChoiceBox<String> vPilekChoice;

    @FXML
    private Button startButton;
    @FXML
    private Button endButton;
    @FXML
    private Button kolumnyConfirm;
    @FXML
    private Button wierszeConfirm;

    public void confirmKolumny(){
        kolumny = Integer.parseInt(wartKolumn.getText());
        System.out.println(kolumny);
    }

    public void confirmWiersze(){
        wiersze = Integer.parseInt(wartWierszy.getText());
        System.out.println(wiersze);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

         valueFactoryGracze = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,wiersze);
         valueFactoryPilki = new SpinnerValueFactory.IntegerSpinnerValueFactory(3, wiersze);

        valueFactoryGracze.setValue(2);
        valueFactoryPilki.setValue(2);

        graczeSpinner.setValueFactory(valueFactoryGracze);
        pilkiSpinner.setValueFactory(valueFactoryPilki);

        gracze=graczeSpinner.getValue();
        pilki=pilkiSpinner.getValue();

        startButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if(kolumny%2==1){
                    powiadomienie.setTextFill(Color.LAWNGREEN);
                    powiadomienie.setText("Symulacja Uruchomiona");
                    field = new Field(kolumny,wiersze, szybkoscGraczy, szybkoscPilek, gracze, pilki);
                    labelTabInit(wiersze,kolumny, field);
                /*
                for(ArrayList<Integer> array : plansza.stanPlanszy){
                    for(int i : array){
                        System.out.print(i);
                    }
                    System.out.println();
                }*/
                    field.game();

                }
                else {
                    powiadomienie.setTextFill(Color.RED);
                    powiadomienie.setText("Liczba kolum musi byc nieparzysta!");
                }
            }
        });

        endButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                System.out.println(field.threads);
                for(MyThread w : field.threads){
                    w.setEnd(true);
                }

                System.out.print("\nStrona prawa: ");
                for(int score : field.leftSideCondition){
                    System.out.print(score+" ");
                }
                System.out.print("\nStrona lewa: ");
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
                powiadomienie.setTextFill(Color.DARKGRAY
                );
                powiadomienie.setText("Koniec Symulacji");
            }
        });

        graczeSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                gracze=graczeSpinner.getValue();
            }
        });

        pilkiSpinner.valueProperty().addListener(new ChangeListener<Integer>() {
            @Override
            public void changed(ObservableValue<? extends Integer> observableValue, Integer integer, Integer t1) {
                pilki=pilkiSpinner.getValue();
            }
        });

        wierszeConfirm.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                confirmWiersze();
                valueFactoryGracze = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,wiersze);
                valueFactoryPilki = new SpinnerValueFactory.IntegerSpinnerValueFactory(2,wiersze);

                if(graczeSpinner.getValue()>wiersze){
                    valueFactoryGracze.setValue(2);
                    graczeSpinner.setValueFactory(valueFactoryGracze);
                }
                else {
                    valueFactoryGracze.setValue(graczeSpinner.getValue());
                    graczeSpinner.setValueFactory(valueFactoryGracze);
                }

                if(pilkiSpinner.getValue()>wiersze){
                    valueFactoryPilki.setValue(3);
                    pilkiSpinner.setValueFactory(valueFactoryPilki);
                }
                else{
                    valueFactoryPilki.setValue(pilkiSpinner.getValue());
                    pilkiSpinner.setValueFactory(valueFactoryPilki);
                }
            }
        });

        vGraczyChoice.getItems().addAll(szybkosci);
        vPilekChoice.getItems().addAll(szybkosci);
        vGraczyChoice.setOnAction(this::setSzybkoscGraczy);
        vPilekChoice.setOnAction(this::setSzybkoscPilek);
    }

    public int getWiersze() {
        return wiersze;
    }

    public void setWiersze(int wiersze) {
        this.wiersze = wiersze;
    }

    public int getKolumny() {
        return kolumny;
    }

    public void setKolumny(int kolumny) {
        this.kolumny = kolumny;
    }

    public void setSzybkoscGraczy(ActionEvent event){
        szybkoscGraczy=Integer.parseInt(vGraczyChoice.getValue());
        System.out.println(szybkoscGraczy);
    }

    public void setSzybkoscPilek(ActionEvent event){
        szybkoscPilek=Integer.parseInt(vPilekChoice.getValue());
        System.out.println(szybkoscPilek);
    }

    public int getGracze() { return gracze; }

    public int getPilki() { return pilki; }

    public int getSzybkoscGraczy() { return szybkoscGraczy; }

    public int getSzybkoscPilek() { return szybkoscPilek; }
}
