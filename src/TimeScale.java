public class TimeScale {

    private int currTime = 0;
    private double originalSpeed;
    private static final int MAX_TIME = 3;
    private static final int MIN_TIME = -3;
    private static final double CHANGE = 0.5;

    public int getCurrTime() {
        return currTime;
    }

    public void speedUp(Demon[] demons, Navec navec) {
        if (currTime<MAX_TIME) {
            currTime++;
            for (Demon demon :demons) {
                demon.speed = demon.getINITIAL_SPEED()*Math.pow(CHANGE, -currTime);
            }
            navec.speed = navec.getINITIAL_SPEED()*Math.pow(CHANGE, -currTime);
        }
    }
    public void speedDown(Demon[] demons, Navec navec) {
        if (currTime>MIN_TIME){
            currTime--;
            for (Demon demon :demons) {
                demon.speed = demon.getINITIAL_SPEED()*Math.pow(CHANGE, -currTime);
            }
            navec.speed = navec.getINITIAL_SPEED()*Math.pow(CHANGE, -currTime);

        }
    }

}
