package lab05.lab05_pop;

abstract public class MyThread extends Thread {
    protected boolean end = false;
    public void setEnd(boolean k) {
        end = k;
    }
}
