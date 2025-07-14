import bagel.DrawOptions;
import bagel.Font;
import bagel.util.Colour;

public class HealthBar {
    private double xPosition;
    private double yPosition;

    public void setxPosition(double xPosition) {
        this.xPosition = xPosition;
    }

    public void setyPosition(double yPosition) {
        this.yPosition = yPosition;
    }

    private int currentHealth;

    private final int MAX_HEALTH;

    public int getMAX_HEALTH() {
        return MAX_HEALTH;
    }

    private final static int ORANGE_BOUNDARY = 65;
    private final static int RED_BOUNDARY = 35;
    private final static int LOWEST_HEALTH = 0;
    private final int FONT_SIZE;

    private final Font FONT;

    private final static DrawOptions COLOUR = new DrawOptions();
    private final static Colour GREEN = new Colour(0, 0.8, 0.2);
    private final static Colour ORANGE = new Colour(0.9, 0.6, 0);
    private final static Colour RED = new Colour(1, 0, 0);

    public HealthBar(double xPosition, double yPosition, int MAX_HEALTH, int FONT_SIZE) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.MAX_HEALTH = MAX_HEALTH;
        this.currentHealth = MAX_HEALTH;
        this.FONT_SIZE = FONT_SIZE;
        this.FONT = new Font("res/frostbite.ttf", FONT_SIZE);
    }
    /**
     * Method that renders the current health as a percentage on screen
     */
    public void renderHealthPoints(){
        double percentageHP = ((double) currentHealth/MAX_HEALTH) * 100;
        if (percentageHP <= RED_BOUNDARY){
            COLOUR.setBlendColour(RED);
        } else if (percentageHP <= ORANGE_BOUNDARY){
            COLOUR.setBlendColour(ORANGE);
        } else {
            COLOUR.setBlendColour(GREEN);
        }
        FONT.drawString(Math.round(percentageHP) + "%", xPosition, yPosition, COLOUR);
    }

    public void setCurrentHealth(int currentHealth) {
        this.currentHealth = currentHealth;
    }

    public int getCurrentHealth() {
        return Math.max(currentHealth, LOWEST_HEALTH);
    }
}
