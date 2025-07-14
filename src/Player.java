import bagel.*;
import bagel.util.Colour;
import bagel.util.Point;

public class Player extends Entity implements Printable{
    private final static String FAE_LEFT = "res/fae/faeLeft.png";
    private final static String FAE_RIGHT = "res/fae/faeRight.png";
    private final static int MAX_HEALTH_POINTS = 100;
    private final static double MOVE_SIZE = 2;
    private final static int WIN_X = 950;
    private final static int WIN_Y = 670;

    private final static int HEALTH_X = 20;
    private final static int HEALTH_Y = 25;
    private final static int FONT_SIZE = 30;

    private Point prevPosition;
    private Image currentImage;
    private boolean facingRight;
    private HealthBar healthBar;

    private final static String FAE_ATTACK_LEFT = "res/fae/faeAttackLeft.png";
    private final static String FAE_ATTACK_RIGHT = "res/fae/faeAttackRight.png";
    private boolean attackState = false;
    private boolean invincibleState = false;
    private boolean attackInCoolDown = false;
    private final static int ATTACK_TIME = 1000;
    private final static int COOLDOWN_TIME = 2000;
    private final static int INVINCIBLE_TIME = 3000;
    private final static double MILLISECONDS = 0.06;
    private int attackCounter = 0;
    private int coolDownCounter = 0;
    private int invincibleCounter = 0;
    private int damage = 20;
    public boolean isAttackState() {
        return attackState;
    }

    public boolean isInvincibleState() {
        return invincibleState;
    }

    public void setInvincibleState(boolean invincibleState) {
        this.invincibleState = invincibleState;
    }

    public int getDamage() {
        return damage;
    }

    public Player(int startX, int startY){
        super(startX, startY);
        this.currentImage = new Image(FAE_RIGHT);
        super.entityCurrentImage = currentImage;
        this.facingRight = true;
        this.healthBar = new HealthBar(HEALTH_X, HEALTH_Y, MAX_HEALTH_POINTS, FONT_SIZE);
    }


    /**
     * Attacking method
     */
    public void attack(Input input, Level1 level1Objects) {
        if (input.wasPressed(Keys.A) && !attackInCoolDown){
            attackState = true;

        }
        if (attackState) {
            attackCounter ++;
            if (facingRight) {
                this.currentImage = new Image(FAE_ATTACK_RIGHT);
            } else {
                this.currentImage = new Image(FAE_ATTACK_LEFT);
            }
        }
        if (attackCounter/MILLISECONDS==ATTACK_TIME) {
            attackState = false;
            attackCounter=0;
            attackInCoolDown = true;
            if (facingRight) {
                this.currentImage = new Image(FAE_RIGHT);
            } else {
                this.currentImage = new Image(FAE_LEFT);
            }
        }
        if (attackInCoolDown){
            coolDownCounter++;
        }
        if (coolDownCounter/MILLISECONDS==COOLDOWN_TIME) {
            attackInCoolDown = false;
            coolDownCounter=0;
        }
    }
    /**
     * Method that performs state update
     */
    public void update(Input input, Level0 level0Object, Level1 level1Object){

        if (input.isDown(Keys.UP)){
            setPrevPosition();
            move(0, -MOVE_SIZE);
        } else if (input.isDown(Keys.DOWN)){
            setPrevPosition();
            move(0, MOVE_SIZE);
        } else if (input.isDown(Keys.LEFT)){
            setPrevPosition();
            move(-MOVE_SIZE,0);
            if (facingRight) {
                this.currentImage = new Image(FAE_LEFT);
                facingRight = !facingRight;
            }
        } else if (input.isDown(Keys.RIGHT)){
            setPrevPosition();
            move(MOVE_SIZE,0);
            if (!facingRight) {
                this.currentImage = new Image(FAE_RIGHT);
                facingRight = !facingRight;
            }
        }
        attack(input, level1Object);

        //checking invincible state.
        if (invincibleState) {
            invincibleCounter ++;

        }
        if (invincibleCounter/MILLISECONDS>=INVINCIBLE_TIME) {
            invincibleState = false;
            invincibleCounter = 0;
        }

        this.currentImage.drawFromTopLeft(position.x, position.y);

        // switching between level0 and level1.
        if(level0Object!=null){
            checkCollisions(level0Object.getPlayer(), level0Object, level1Object);
            healthBar.renderHealthPoints();
            if(level0Object.checkOutOfBounds(this)){
                this.moveBack();
            }
        } else {
            checkCollisions(level1Object.getPlayer(), null, level1Object);
            healthBar.renderHealthPoints();
            if(level1Object.checkOutOfBounds(this)){
                this.moveBack();
            }
        }
    }


    /**
     * Method that stores Fae's previous position
     */
    private void setPrevPosition(){
        this.prevPosition = new Point(position.x, position.y);
    }

    /**
     * Method that moves Fae back to previous position
     */
    public void moveBack(){
        this.position = prevPosition;
    }

    /**
     * Method that moves Fae given the direction
     */
    private void move(double xMove, double yMove){
        double newX = position.x + xMove;
        double newY = position.y + yMove;
        this.position = new Point(newX, newY);
    }

    /**
     * Method that checks if Fae's health has depleted
     */
    public boolean isDead() {
        return healthBar.getCurrentHealth() <= 0;
    }

    /**
     * Method that checks if Fae has found the gate
     */
    public boolean reachedGate(){
        return (this.position.x >= WIN_X) && (this.position.y >= WIN_Y);
    }

    public Point getPosition() {
        return position;
    }

    public Image getCurrentImage() {
        return currentImage;
    }

    public int getHealthPoints() {
        return healthBar.getCurrentHealth();
    }

    public void setHealthPoints(int healthPoints) {
        this.healthBar.setCurrentHealth(healthPoints);
    }

    public static int getMaxHealthPoints() {
        return MAX_HEALTH_POINTS;
    }

    public HealthBar getHealthBar() {
        return healthBar;
    }

    @Override
    public void printMessage(Entity entity) {
        if (entity instanceof Demon) {
            System.out.println("Fae inflicts " + damage +
                    " damage points on Demon. Demon's current health: "
                    + ((Demon) entity).healthBar.getCurrentHealth() + "/"
                    + ((Demon) entity).healthBar.getMAX_HEALTH());
        } else if (entity instanceof Navec) {
            System.out.println("Fae inflicts " + damage +
                    " damage points on Navec. Navec's current health: "
                    + ((Navec) entity).healthBar.getCurrentHealth() + "/"
                    + ((Navec) entity).healthBar.getMAX_HEALTH());

        }

    }
}