package lab05.lab05_pop;

import javafx.application.Platform;
import javafx.scene.control.Label;

import java.util.ArrayList;
import java.util.HashMap;

import static java.lang.Thread.sleep;

public class Field {
    static int columns;
    static int rows;
    static int sleepPlayers;
    static int sleepBalls;
    static int playerCount;
    static int ballCount;
    ArrayList<Integer> leftSideCondition;
    ArrayList<Integer> rightSideCondition;
    ArrayList<ArrayList<Integer>> fieldCondition; // 0-> puste 1-> pilka  2->gracz
    ArrayList<Boolean> ballRow;
    ArrayList<ArrayList<Label>> labels;
    ArrayList<MyThread> threads;
    HashMap<Ball, Boolean> targetsTeam1;
    HashMap<Ball, Boolean> targetsTeam2;
    public synchronized void game(){
        BallGenerator generator = new BallGenerator(sleepBalls, ballCount,this);
        generator.start();
        for(int i = 0; i< playerCount; i++){
            if(i%2==0){
                Player nowygracz1 = new Player(0,i, sleepPlayers,this,1);
                threads.add(nowygracz1);
            }
            else {
                Player nowygracz1 = new Player(0, rows -i, sleepPlayers,this,1);
                threads.add(nowygracz1);
            }
        }
        for (int i = 0; i< playerCount; i++){
            if(i%2==0){
                Player nowygracz2 = new Player(columns -1,i, sleepPlayers,this,2);
                threads.add(nowygracz2);
            }
            else {
                Player nowygracz2 = new Player(columns -1, rows -i, sleepPlayers,this,2);
                threads.add(nowygracz2);
            }
        }
        for(MyThread myThread : threads){
            myThread.start();
        }
        threads.add(generator);

    }
    public synchronized void setBall(int xCord, int yCord){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labels.get(yCord).get(xCord+1).setText("#");
            }
        });
    }
    public synchronized void setPlayer(int xCord, int yCord){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                labels.get(yCord).get(xCord+1).setText("X");
            }
        });
    }
    public synchronized void clearSquare(int xCord, int yCord){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(xCord==(columns)/2){
                    labels.get(yCord).get(xCord+1).setText("_");
                }
                else{
                    labels.get(yCord).get(xCord+1).setText(".");
                }
            }
        });
    }
    public synchronized void updateCondition(int xCord, int yCord){
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                if(xCord==0){
                    if(rightSideCondition.get(yCord)>9){
                        labels.get(yCord).get(0).setText(String.valueOf(rightSideCondition.get(yCord)));
                    }
                    else {
                        labels.get(yCord).get(0).setText(String.valueOf("0"+ rightSideCondition.get(yCord)));
                    }
                }
                else {
                    if(leftSideCondition.get(yCord)>9){
                        labels.get(yCord).get(columns +1).setText(String.valueOf(leftSideCondition.get(yCord)));
                    }
                    else {
                        labels.get(yCord).get(columns +1).setText(String.valueOf("0"+ leftSideCondition.get(yCord)));
                    }
                }
            }
        });
    }
    public Field(int columns, int rows, int szybkoscGraczy, int szybkoscPilek, int playerCount, int ballCount){
        Field.columns = columns;
        Field.rows = rows;
        Field.playerCount = playerCount;
        Field.ballCount = ballCount;
        switch (szybkoscGraczy) {
            case 1 -> sleepPlayers = 500;
            case 2 -> sleepPlayers = 250;
            case 3 -> sleepPlayers = 100;
        }
        switch (szybkoscPilek) {
            case 1 -> sleepBalls = 500;
            case 2 -> sleepBalls = 250;
            case 3 -> sleepBalls = 100;
        }
        targetsTeam1 = new HashMap<>();
        targetsTeam2 = new HashMap<>();
        threads = new ArrayList<>();
        leftSideCondition = new ArrayList<>();
        rightSideCondition = new ArrayList<>();
        fieldCondition = new ArrayList<>();
        ballRow = new ArrayList<>();
        labels = new ArrayList<>();
        //ZAPELNIANIE MAP
        for(int i = 0; i< rows; i++){
            leftSideCondition.add(0);
            rightSideCondition.add(0);
            ballRow.add(false);
            labels.add(new ArrayList<>());
        }
        for(int i = 0; i< rows; i++){
            fieldCondition.add(new ArrayList<>());
            //stanPlanszy.get(i).add(-1);
            for (int j = 0; j< columns; j++){
                fieldCondition.get(i).add(0);
            }
            //stanPlanszy.get(i).add(-1);
        }
    }
}
