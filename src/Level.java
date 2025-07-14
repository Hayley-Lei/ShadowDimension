import bagel.Font;
import bagel.Input;
import bagel.Window;
import bagel.util.Point;

public abstract class Level {

    protected final static int TITLE_FONT_SIZE = 75;
    protected final static int INSTRUCTION_FONT_SIZE = 40;
    protected final static int REFRESH_RATE = 60;
    protected final Font TITLE_FONT = new Font("res/frostbite.ttf", TITLE_FONT_SIZE);
    protected final Font INSTRUCTION_FONT = new Font("res/frostbite.ttf", INSTRUCTION_FONT_SIZE);
    protected Point topLeft;
    protected Point bottomRight;
    protected final static String END_MESSAGE = "GAME OVER!";




    public abstract void drawStartPage();
    public abstract void update(Input input);
    public abstract void readCSV(String fileName);

    public void drawMessage(String message){
        TITLE_FONT.drawString(message, (Window.getWidth()/2.0 - (TITLE_FONT.getWidth(message)/2.0)),
                (Window.getHeight()/2.0 + (TITLE_FONT_SIZE/2.0)));
    }
    public boolean checkOutOfBounds(Entity entity){
        Point currentPosition = entity.position;
        if ((currentPosition.y > bottomRight.y) || (currentPosition.y < topLeft.y) || (currentPosition.x < topLeft.x)
                || (currentPosition.x > bottomRight.x)){
            return true;
        }
        return false;
    }

}
