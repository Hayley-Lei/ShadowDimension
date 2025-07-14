import bagel.Image;
import bagel.Input;
import bagel.Keys;
import bagel.Window;
import bagel.util.Point;
import bagel.util.Rectangle;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.security.Key;

public class Level1 extends Level{
    private final static String WIN_MESSAGE = "CONGRATULATIONS!";
    private final static String INSTRUCTION_MESSAGE = "PRESS SPACE TO START\nPRESS A TO ATTACK\nDEFEAT NAVEC TO WIN";
    private final static int INS_X = 350;
    private final static int INS_Y = 350;
    private final static String LEVEL1_FILE = "res/level1.csv";
    private final Image BACKGROUND_IMAGE = new Image("res/background1.png");

    private final static int TREE_ARRAY_SIZE = 15;
    private final static int S_HOLE_ARRAY_SIZE = 5;
    private final static int DEMON_ARRAY_SIZE = 5;

    private final Tree[] trees = new Tree[TREE_ARRAY_SIZE];
    private final Demon[] demons = new Demon[DEMON_ARRAY_SIZE];
    private Navec navec;

    private final Sinkhole[] sinkholes = new Sinkhole[S_HOLE_ARRAY_SIZE];
    private Player player;
    private boolean hasStarted = false;
    private boolean gameOver = false;
    private boolean playerWin = false;
    private TimeScale timeScale;

    public Player getPlayer() {
        return player;
    }

    public Sinkhole[] getSinkholes() {
        return sinkholes;
    }

    public Tree[] getTrees() {
        return trees;
    }

    /**
     * set the previous health points from level 0
     */

    public Level1(){
        readCSV(LEVEL1_FILE);
        timeScale = new TimeScale();
    }

    public void drawStartPage(){
        INSTRUCTION_FONT.drawString(INSTRUCTION_MESSAGE,INS_X, INS_Y);
    }



    public void readCSV(String fileName){
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))){

            String line;
            int currentTreeCount = 0;
            int currentSinkholeCount = 0;
            int currentDemonCount = 0;

            while((line = reader.readLine()) != null){
                String[] sections = line.split(",");
                switch (sections[0]) {
                    case "Fae":
                        player = new Player(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
                        break;
                    case "Sinkhole":
                        sinkholes[currentSinkholeCount] = new Sinkhole(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentSinkholeCount++;
                        break;
                    case "Tree":
                        trees[currentTreeCount] = new Tree(Integer.parseInt(sections[1]),Integer.parseInt(sections[2]));
                        currentTreeCount++;
                        break;
                    case "Demon":
                        demons[currentDemonCount] = new Demon(Integer.parseInt(sections[1]),
                                Integer.parseInt(sections[2]));
                        currentDemonCount++;
                        break;
                    case "Navec":
                        navec = new Navec(Integer.parseInt(sections[1]), Integer.parseInt(sections[2]));
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
    public void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
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
        }

        if (hasStarted && !gameOver && !playerWin){
            BACKGROUND_IMAGE.draw(Window.getWidth()/2.0, Window.getHeight()/2.0);

            for(Tree current: trees){
                current.update();
            }
            for(Sinkhole current: sinkholes){
                current.update();
            }
            navec.update(this);
            /**
             * looping for demons (haven't finished yet)
             */
            for (Demon current: demons){
                current.update(this);
            }

            if (input.wasPressed(Keys.L)) {
                timeScale.speedUp(demons, navec);
                System.out.println(navec.speed);
                System.out.println("Speed up, Speed: " + timeScale.getCurrTime());
            }
            if (input.wasPressed(Keys.K)) {
                timeScale.speedDown(demons, navec);
                System.out.println(navec.speed);
                System.out.println("Slowed down, Speed: " + timeScale.getCurrTime());
            }


            player.update(input, null, this);

            if (player.isDead()){
                gameOver = true;
            }

            if (navec.healthBar.getCurrentHealth()<=0){
                playerWin = true;
            }
        }

    }


}
