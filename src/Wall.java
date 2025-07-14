import bagel.Image;

public class Wall extends Entity{
    private final Image WALL = new Image("res/wall.png");

    public Wall(double xPosition, double yPosition) {
        super(xPosition, yPosition);
        super.entityCurrentImage = WALL;
    }

    /**
     * Method that performs state update
     */
    protected void update() {
        WALL.drawFromTopLeft(this.position.x, this.position.y);
    }

}