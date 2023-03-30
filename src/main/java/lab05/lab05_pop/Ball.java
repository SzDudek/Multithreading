package lab05.lab05_pop;

public class Ball extends MyThread {
    int xCord, yCord;
    int moveDirection;
    int sleepTime;
    Field field;
    BallGenerator generator;
    private synchronized void move() throws InterruptedException {
        if((moveDirection==1 && xCord== field.columns -1)||(moveDirection==-1 && xCord==0)){
            field.clearSquare(xCord,yCord);
            field.fieldCondition.get(yCord).set(xCord,0);
            field.ballRow.set(yCord,false);
            if(generator.isAlive()){
                generator.nextBall(1);
            }
            if(xCord==0){
                field.rightSideCondition.set(yCord, field.rightSideCondition.get(yCord)+1);
                field.updateCondition(xCord,yCord);
            }
            else{
                field.leftSideCondition.set(yCord, field.leftSideCondition.get(yCord)+1);
                field.updateCondition(xCord,yCord);
            }
            field.targetsTeam1.remove(this);
            field.targetsTeam2.remove(this);
            setEnd(true);
            field.threads.remove(this);
        }
        else if(field.fieldCondition.get(yCord).get(xCord+moveDirection)==1){
            switch (moveDirection){
                case -1:
                    moveDirection=1;
                    break;
                case 1:
                    moveDirection=-1;
                    break;
            }
            field.fieldCondition.get(yCord).set(xCord,0);
            field.clearSquare(xCord,yCord);
            xCord=xCord+moveDirection;
            field.fieldCondition.get(yCord).set(xCord,2);
            field.setBall(xCord,yCord);
        }
        else{
            field.fieldCondition.get(yCord).set(xCord,0);
            field.clearSquare(xCord,yCord);
            xCord=xCord+moveDirection;
            field.fieldCondition.get(yCord).set(xCord,2);
            field.setBall(xCord,yCord);
        }
    }

    @Override
    public void run() {
        while ( !end) {
            try{
                sleep(sleepTime);
            } catch(InterruptedException e) {
                System.err.println("Thread interrupted");
            }
            try {
                move();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try{
                sleep(sleepTime);
            } catch(InterruptedException e) {
                System.err.println("Thread interrupted");
            }
        }
    }

    public Ball(int xCord, int yCord, int sleepTime, Field field, BallGenerator generator){
        int rand = (int)(Math.random()*2);
        if(rand==0){
            moveDirection=1;
        }
        else{
            moveDirection=-1;
        }
        field.ballRow.set(yCord,true);
        this.generator=generator;
        this.field = field;
        this.xCord=xCord;
        this.yCord=yCord;
        this.sleepTime=sleepTime;
        field.targetsTeam1.put(this,true);
        field.targetsTeam2.put(this,true);
        field.fieldCondition.get(yCord).set(xCord,2);
        field.setBall(xCord,yCord);
    }
}

