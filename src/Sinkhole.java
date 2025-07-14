import bagel.Image;
import bagel.Input;
import bagel.util.Point;
import bagel.util.Rectangle;

public class Sinkhole extends Entity implements Printable{
    private final Image SINKHOLE = new Image("res/sinkhole.png");
    private final static int DAMAGE_POINTS = 30;
    private boolean isActive;

    public Sinkhole(int startX, int startY){
        super(startX, startY);
        super.entityCurrentImage = SINKHOLE;
        this.isActive = true;
    }

    /**
     * Method that performs state update
     */
    public void update() {
        if (isActive){
            SINKHOLE.drawFromTopLeft(this.position.x, this.position.y);
        }
    }

    public int getDamagePoints(){
        return DAMAGE_POINTS;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    @Override
    public void printMessage(Entity entity) {
        System.out.println(this.getClass().getName() + " inflicts " + DAMAGE_POINTS +
                " damage points on Fae. Fae's current health: "
                + ((Player) entity).getHealthBar().getCurrentHealth() + "/"
                + ((Player) entity).getHealthBar().getMAX_HEALTH());

    }
}