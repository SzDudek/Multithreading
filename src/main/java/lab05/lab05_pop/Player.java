package lab05.lab05_pop;

public class Player extends MyThread {
    int xCord,yCord;
    int sleepTime;
    int teamId;
    Field field;
    public synchronized void move(){
        System.out.println("Move");
        Ball targetedBall = findTarget();
        if(targetedBall!=null && targetedBall.yCord>this.yCord && field.fieldCondition.get(yCord+1).get(xCord)!=1){
            field.fieldCondition.get(yCord).set(xCord,0);
            field.clearSquare(xCord,yCord);
            yCord=yCord+1;
            field.fieldCondition.get(yCord).set(xCord,1);
            field.setPlayer(xCord,yCord);
        }
        else if(targetedBall!=null && targetedBall.yCord<this.yCord && field.fieldCondition.get(yCord-1).get(xCord)!=1){
            field.fieldCondition.get(yCord).set(xCord,0);
            field.clearSquare(xCord,yCord);
            yCord=yCord-1;
            field.fieldCondition.get(yCord).set(xCord,1);
            field.setPlayer(xCord,yCord);
        }

        if(teamId==1){
            field.targetsTeam1.put(targetedBall,true);
        }
        else {
            field.targetsTeam2.put(targetedBall,true);
        }

    }

    public synchronized Ball findTarget(){
        Ball closest = null;
        double minDist=Double.POSITIVE_INFINITY;
        int cDist;
        if(teamId==1){
            for(Ball pilka : field.targetsTeam1.keySet()){
                cDist=(int) Math.sqrt(Math.pow(this.xCord-pilka.xCord,2)+Math.pow(this.yCord-pilka.yCord,2));
                if(cDist<minDist && field.targetsTeam1.get(pilka)){
                    closest=pilka;
                    minDist = (double) cDist;
                }
            }
            if(closest!=null){
                field.targetsTeam1.put(closest,false);
            }
        }
        else{
            for(Ball ball : field.targetsTeam2.keySet()){
                cDist=(int) Math.sqrt(Math.pow(this.xCord-ball.xCord,2)+Math.pow(this.yCord-ball.yCord,2));
                if(cDist<minDist && field.targetsTeam2.get(ball)){
                    closest=ball;
                    minDist = (double) cDist;
                }
            }
            if(closest!=null){
                field.targetsTeam2.put(closest,false);
            }
        }
        System.out.println(field.targetsTeam1);
        System.out.println(field.targetsTeam2);
       return closest;
    }
    @Override
    public void run() {
        while ( !end) {
            move();
            try{
                sleep(sleepTime);
            } catch(InterruptedException e) {
                System.err.println("Thread interrupted");
            }
        }
    }

    public Player(int xCord, int yCord, int sleepTime, Field field, int teamId){
        this.teamId=teamId;
        this.field = field;
        this.xCord=xCord;
        this.yCord=yCord;
        this.sleepTime=sleepTime;
        field.fieldCondition.get(yCord).set(xCord,1);
        field.setPlayer(xCord,yCord);
    }
}

