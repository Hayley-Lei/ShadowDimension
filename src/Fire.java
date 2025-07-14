import bagel.DrawOptions;
import bagel.Image;

public class Fire extends Entity{

    private boolean isDisplayed;

    public boolean isDisplayed() {
        return isDisplayed;
    }

    public void setDisplayed(boolean displayed) {
        isDisplayed = displayed;
    }

    public Fire(double x, double y, String image){
        super(x, y);
        entityCurrentImage = new Image(image);
        isDisplayed = false;
    }

    public void drawFire(double direction){
        DrawOptions rotation = new DrawOptions().setRotation(direction);
        entityCurrentImage.drawFromTopLeft(xPosition,yPosition,rotation);
    }
}
