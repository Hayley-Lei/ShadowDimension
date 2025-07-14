import bagel.Image;
import bagel.util.Point;

import java.util.Random;

public class Enemy extends Entity{
    protected HealthBar healthBar;

    private final static int FACING_SIDES = 2;
    private final static int FACING_LEFT = 0;
    private final static int TYPES = 2;
    private final static int AGGRESSIVE = 0;

    protected boolean isAggressive = false;
    protected boolean exists = true;
    protected boolean isInvincible = false;

    private final static int FONT_SIZE = 15;
    private final static int Y_OFFSET = 6;

    protected final static int INVINCIBLE_TIME = 3;
    protected int invincibleCounter = 0;


    protected double speed;
    private final static double SPEED_LOWER = 0.2;
    private final static double SPEED_UPPER = 0.7;

    private final static int UP = 0;
    private final static int DOWN = 1;
    private final static int LEFT = 2;
    private final static int RIGHT = 3;
    private final static int DIRECTIONS = 4;
    private int direction;
    protected double attackRange;
    protected Image leftImage;
    protected Image rightImage;
    protected Image leftInImage;
    protected Image rightInImage;

    private Random generator = new Random();
    private final static double PI = Math.PI;
    protected final double TOP_LEFT = 0;
    protected final double TOP_RIGHT = 0.5*PI;
    protected final double BOT_LEFT = -0.5*PI;
    protected final double BOT_RIGHT = 1*PI;

    protected Fire fire;
    protected int damage;
    private boolean facingRight;

    public Enemy(int xPosition, int yPosition){
        super(xPosition, yPosition);
    }

    /**
     * Assign values for navec and demons
     */
    public void assignEnemy(int xPosition, int yPosition, String leftImage, String rightImage, int maxHealth){
        this.leftImage = new Image(leftImage);
        this.rightImage = new Image(rightImage);

        // randomly decide where the demon is facing
        if (Math.abs(generator.nextInt())%FACING_SIDES == FACING_LEFT) {
            entityCurrentImage = this.leftImage;
            facingRight = false;
        } else {
            entityCurrentImage = this.rightImage ;
            facingRight = true;
        }
        // randomly decide if the demon is aggressive or not
        if (Math.abs(generator.nextInt())%TYPES == AGGRESSIVE || isAggressive) {
            isAggressive = true;
            switch (Math.abs(generator.nextInt())%DIRECTIONS) {
                case UP:
                    direction = UP;
                    break;
                case DOWN:
                    direction = DOWN;
                    break;
                case LEFT:
                    direction = LEFT;
                    entityCurrentImage = this.leftImage;
                    facingRight = false;
                    break;
                case RIGHT:
                    direction = RIGHT;
                    entityCurrentImage = this.rightImage;
                    facingRight = true;
                    break;
            }
            // randomly decide the moving speed
            speed = Math.abs(generator.nextDouble())%(SPEED_UPPER-SPEED_LOWER)+SPEED_LOWER;
        }
        healthBar = new HealthBar(xPosition, yPosition-Y_OFFSET, maxHealth, FONT_SIZE);

    }
    /**
     * checking for if the demon needs to turn around
     */
    public boolean turnAround(Level1 level1Objects) {
        if (level1Objects.checkOutOfBounds(this) ||
                hasStaticCollisions(this.getBoundingBox(), level1Objects.getTrees())!=null ||
                hasStaticCollisions(this.getBoundingBox(), level1Objects.getSinkholes())!=null){
            return true;
        }
        return false;
    }

    /**
     * different direction for different moving way
     */
    public void move(Level1 level1Objects){
        switch (direction){
            case UP:
                if (turnAround(level1Objects)){
                    yPosition += speed;
                    this.direction = DOWN;
                } else {
                    yPosition -= speed;
                }
                break;
            case DOWN:
                if (turnAround(level1Objects)){
                    yPosition -= speed;
                    this.direction = UP;
                } else {
                    yPosition += speed;
                }

                break;
            case LEFT:
                if (turnAround(level1Objects)){
                    xPosition += speed;
                    this.direction = RIGHT;
                } else {
                    xPosition -= speed;
                }
                break;
            case RIGHT:
                if (turnAround(level1Objects)){
                    xPosition -= speed;
                    this.direction = LEFT;
                } else {
                    xPosition += speed;
                }
                break;
        }
    }

    /**
     * Updating enemy actions.
     */
    public void update(Level1 level1Objects){
        if (this.exists) {
            entityCurrentImage.drawFromTopLeft(xPosition, yPosition);
            healthBar.renderHealthPoints();
            if (isAggressive) {
                position = new Point(xPosition, yPosition);
                move(level1Objects);

                // changing image based on directions
                switch (direction) {
                    case LEFT:
                        entityCurrentImage = leftImage;
                        facingRight = false;
                        break;
                    case RIGHT:
                        entityCurrentImage = rightImage;
                        facingRight = true;
                        break;
                }
                // makes the health bar moving along with the demon
                healthBar.setxPosition(xPosition);
                healthBar.setyPosition(yPosition - Y_OFFSET);

            }
            updateFire(level1Objects);

            collides(level1Objects.getPlayer());
            // checking if it is invincible
            if (this.isInvincible) {
                invincibleCounter++;
                if (facingRight) {
                    entityCurrentImage = rightInImage;
                } else {
                    entityCurrentImage = leftInImage;
                }
            }
            if (invincibleCounter / level1Objects.REFRESH_RATE >= INVINCIBLE_TIME) {
                isInvincible = false;
                invincibleCounter = 0;
                if (facingRight) {
                    entityCurrentImage = rightImage;
                } else {
                    entityCurrentImage = leftImage;
                }

            }
        }
    }

    /**
     * checking if player attacks enemy and change state.
     */
    public void collides(Player player){

        if (this.exists && player.getBoundingBox().intersects(this.getBoundingBox())) {

            // enemy being attacked
            if (player.isAttackState() && !this.isInvincible) {
                this.isInvincible = true;
                this.healthBar.setCurrentHealth(healthBar.getCurrentHealth() - player.getDamage());
                player.printMessage(this);
                if (this.healthBar.getCurrentHealth()<=0) {
                    this.exists = false;
                }
            }
        }
        // enemy attacking player
        if (this.exists && !player.isInvincibleState()
                && fire.getBoundingBox().intersects(player.getBoundingBox())) {
            player.setHealthPoints(player.getHealthPoints()-this.damage);
                if (this instanceof Demon) {
                    ((Demon) this).printMessage(player);
                } else if (this instanceof Navec) {
                    ((Navec) this).printMessage(player);
                }
                player.setInvincibleState(true);
        }

    }


    /**
     * every time enemy moves, fire position needs to be updated and draw.
     */
    public void updateFire(Level1 level1Objects){
        double xE = this.xPosition + leftImage.getWidth()/2.0;
        double yE = this.yPosition + leftImage.getHeight()/2.0;
        Point eCentre = new Point(xE, yE);

        double xP = level1Objects.getPlayer().entityCurrentImage.getWidth()/2.0
                + level1Objects.getPlayer().position.x;
        double yP = level1Objects.getPlayer().entityCurrentImage.getHeight()/2.0
                + level1Objects.getPlayer().position.y;
        Point pCentre = new Point(xP, yP);
        if (eCentre.distanceTo(pCentre)<=this.attackRange) {
            if (xP <= xE && yP <= yE) {
                fire.xPosition = xPosition - fire.entityCurrentImage.getWidth();
                fire.yPosition = yPosition - fire.entityCurrentImage.getHeight();
                fire.drawFire(TOP_LEFT);
                fire.position = new Point(fire.xPosition, fire.yPosition);
                fire.setDisplayed(true);
            } else if (xP <= xE && yP > yE) {
                fire.xPosition = xPosition - fire.entityCurrentImage.getWidth();
                fire.yPosition = yPosition + leftImage.getHeight();
                fire.drawFire(BOT_LEFT);
                fire.position = new Point(fire.xPosition, fire.yPosition);
                fire.setDisplayed(true);

            } else if (xP > xE && yP <= yE) {
                fire.xPosition = xPosition + leftImage.getWidth();
                fire.yPosition = yPosition - fire.entityCurrentImage.getHeight();
                fire.drawFire(TOP_RIGHT);
                fire.position = new Point(fire.xPosition, fire.yPosition);
                fire.setDisplayed(true);

            } else if (xP > xE && yP > yE) {
                fire.xPosition = xPosition + leftImage.getWidth();
                fire.yPosition = yPosition + leftImage.getHeight();
                fire.drawFire(BOT_RIGHT);
                fire.position = new Point(fire.xPosition, fire.yPosition);
                fire.setDisplayed(true);
            }
        } else {
            fire.setDisplayed(false);
        }
    }
}
