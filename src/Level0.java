import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Level0 extends Level{
    private final static String GAME_TITLE = "SHADOW DIMENSION";
    private final static int TITLE_X = 260;
    private final static int TITLE_Y = 250;
    private final static int INS_X_OFFSET = 90;
    private final static int INS_Y_OFFSET = 190;
    private final static String INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nUSE ARROW KEYS TO FIND GATE";
    private final static String WIN_MESSAGE = "LEVEL COMPLETE!";
    private final int RENDER_TIME = 3;
    private int timeCounter = 0;
    private boolean levelUp = false;

    private final static String LEVEL0_FILE = "res/level0.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background0.png");

    private final int WALL_ARRAY_SIZE = 52;
    private final int S_HOLE_ARRAY_SIZE = 5;
    private final Wall[] walls = new Wall[WALL_ARRAY_SIZE];
    private final Sinkhole[] sinkholes = new Sinkhole[S_HOLE_ARRAY_SIZE];
    private Player player;

    public Player getPlayer() {
        return player;
    }

    public Wall[] getWalls() {
        return walls;
    }

    public Sinkhole[] getSinkholes() {
        return sinkholes;
    }

    private boolean hasStarted = false;
    private boolean gameOver = false;
    private boolean playerWin = false;

    public Level0(){
        readCSV(LEVEL0_FILE);
    }
    public void drawStartPage(){
        TITLE_FONT.drawString(GAME_TITLE, TITLE_X, TITLE_Y);
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE,TITLE_X + INS_X_OFFSET, TITLE_Y + INS_Y_OFFSET);
    }

    public boolean isLevelUp() {
        return levelUp;
    }

    public void readCSV(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line;
            int currentWallCount = 0;
            int currentSinkholeCount = 0;

            while((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Fae":
                        player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Wall":
                        walls[currentWallCount] = new Wall(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        currentWallCount++;
                        break;
                    case "Sinkhole":
                        sinkholes[currentSinkholeCount] = new Sinkhole(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentSinkholeCount++;
                        break;
                    case "TopLeft":
                        topLeft = new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "BottomRight":
                        bottomRight = new Point(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                }
            }
        } catch (IOException e){
            e.printStackTrace();
            System.exit(-1);
        }

    }

    public void update(Input input){
        if (input.wasPressed(Keys.ESCAPE)){
            Window.close();
        }
        /**
         * fast way to get to level 1.
         */
        if (input.wasPressed(Keys.W)){
            levelUp = true;
        }

        if(!hasStarted){
            drawStartPage();
            if (input.wasPressed(Keys.SPACE)){
                hasStarted = true;
            }
        }

        if (gameOver){
            drawMessage(END_MESSAGE);
        } else if (playerWin) {
            drawMessage(WIN_MESSAGE);
            timeCounter++;
            if (timeCounter/REFRESH_RATE == RENDER_TIME){
                levelUp = true;
            }
        }

        // game is running
        if (hasStarted && !gameOver && !playerWin){
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for(Wall current: walls){
                current.update();
            }
            for(Sinkhole current: sinkholes){
                current.update();
            }
            player.update(input, this, null);

            if (player.isDead()){
                gameOver = true;
            }

            if (player.reachedGate()){
                playerWin = true;
            }
        }

    }

}
