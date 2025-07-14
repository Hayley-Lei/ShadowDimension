import bagel.Image;

public class Tree extends Entity{
    private final Image TREE = new Image("res/tree.png");

    public Tree(double xPosition, double yPosition) {
        super(xPosition, yPosition);
        super.entityCurrentImage = TREE;
    }

    /**
     * Method that performs state update
     */
    protected void update() {
        TREE.drawFromTopLeft(this.position.x, this.position.y);
    }

}