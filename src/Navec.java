import bagel.Image;

public class Navec extends Enemy implements Printable{
    private final static String NAVEC_LEFT = "res/navec/navecLeft.png";
    private final static String NAVEC_RIGHT = "res/navec/navecRight.png";
    private final static String NAVEC_IN_LEFT = "res/navec/navecInvincibleLeft.png";
    private final static String NAVEC_IN_RIGHT = "res/navec/navecInvincibleRight.png";
    private final static Image DEFAULT_IMAGE = new Image(NAVEC_LEFT);

    private final static int NAVEC_MAX_HEALTH = 80;
    private final static double NAVEC_ATTACK_RANGE = 200;
    private static final String FIRE = "res/navec/navecFire.png";
    private static final int DAMAGE = 20;
    private final double INITIAL_SPEED;

    private Fire navecFire = new Fire(xPosition, yPosition, FIRE);

    public double getINITIAL_SPEED() {
        return INITIAL_SPEED;
    }

    public Navec(int xPosition, int yPosition){
        super(xPosition, yPosition);
        isAggressive = true;
        attackRange = NAVEC_ATTACK_RANGE;
        fire = navecFire;
        assignEnemy(xPosition, yPosition, NAVEC_LEFT, NAVEC_RIGHT,NAVEC_MAX_HEALTH);
        leftInImage = new Image(NAVEC_IN_LEFT);
        rightInImage = new Image(NAVEC_IN_RIGHT);
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
