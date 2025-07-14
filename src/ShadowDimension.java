import bagel.*;

/**
 * Skeleton Code for SWEN20003 Project 2, Semester 2, 2022
 *
 * Please enter your name below
 * @Jing Lei
 */
public class ShadowDimension extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DIMENSION";


    private Level0 level0;
    private Level1 level1;


    public ShadowDimension(){
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        level0 = new Level0();
        level1 = new Level1();
    }

    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDimension game = new ShadowDimension();
        game.run();
    }

    /**
     * Performs a state update.
     * allows the game to exit when the escape key is pressed.
     */
    @Override
    public void update(Input input) {
        if (!level0.isLevelUp()){
            level0.update(input);
        } else {
            level1.update(input);
        }
    }

}