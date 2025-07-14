import bagel.Image;
import bagel.util.Point;

import java.util.Random;

public class Demon extends Enemy implements Printable{
    private final static String DEMON_LEFT = "res/demon/demonLeft.png";
    private final static String DEMON_RIGHT = "res/demon/demonRight.png";

    private final static String DEMON_IN_LEFT = "res/demon/demonInvincibleLeft.png";
    private final static String DEMON_IN_RIGHT = "res/demon/demonInvincibleRight.png";
    private final static Image DEFAULT_IMAGE = new Image(DEMON_LEFT);
    private final static int MAX_HEALTH = 40;
    private final double INITIAL_SPEED;
    private final static double ATTACK_RANGE = 150;
    private static final String FIRE = "res/demon/demonFire.png";
    private final static int DAMAGE = 10;
    private Fire demonFire = new Fire(xPosition, yPosition, FIRE);

    public double getINITIAL_SPEED() {
        return INITIAL_SPEED;
    }

    public Demon(int xPosition, int yPosition){
        super(xPosition, yPosition);
        attackRange = ATTACK_RANGE;
        fire = demonFire;
        assignEnemy(xPosition, yPosition, DEMON_LEFT, DEMON_RIGHT,MAX_HEALTH);
        leftInImage = new Image(DEMON_IN_LEFT);
        rightInImage = new Image(DEMON_IN_RIGHT);
        damage = DAMAGE;
        INITIAL_SPEED = speed;
    }
    /**
     * implements printable interface
     */
    @Override
    public void printMessage(Entity entity) {
        System.out.println(this.getClass().getName() + " inflicts " + DAMAGE +
                " damage points on Fae. Fae's current health: "
                + ((Player) entity).getHealthBar().getCurrentHealth() + "/"
                + ((Player) entity).getHealthBar().getMAX_HEALTH());
    }


}
