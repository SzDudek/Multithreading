package lab05.lab05_pop;

public class BallGenerator extends MyThread {
    int sleepTime, ballCount;
    Field field;
    private synchronized void generate(int ile){
        double randomSquare;
        for(int i = 0; i<ile; i++){
            while (true){
                randomSquare = Math.floor(Math.random()*(Field.rows));
                if(!field.ballRow.get((int) randomSquare)){
                    break;
                }
            }
            int poleX = (Field.columns)/2;
            System.out.println("Generating the ball...");
            Ball nowa = new Ball(poleX,(int) randomSquare,sleepTime, field, this);
            field.threads.add(nowa);
            nowa.start();
        }
    }
    public void run(){
        generate(ballCount);
        while ( !end) {
            //WYSWIETLAJ STAN
            try{
                sleep(sleepTime);
            } catch(InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }

    public BallGenerator(int sleepTime, int ballCount, Field field){
        this.field = field;
        this.sleepTime=sleepTime;
        end = false;
        this.ballCount = ballCount;
    }

    public synchronized void nextBall(int x){
        generate(x);
    }
}
