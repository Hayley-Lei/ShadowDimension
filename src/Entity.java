import bagel.Image;
import bagel.util.Point;
import bagel.util.Rectangle;



public abstract class Entity{
    protected double xPosition;
    protected double yPosition;
    protected Image entityCurrentImage;
    protected Point position;

    public Entity(double xPosition, double yPosition) {
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        position = new Point(xPosition, yPosition);
    }

    protected Rectangle getBoundingBox() {
        return new Rectangle(position, entityCurrentImage.getWidth(), entityCurrentImage.getHeight());
    }

    /**
     * Checking for collisions with walls and trees, return a sinkhole collided and set it negative.
     */
    public Entity hasStaticCollisions(Rectangle checkingBox, Entity[] entities){
        for (Entity entity : entities){
            Rectangle entityBox = entity.getBoundingBox();
            if (checkingBox.intersects(entityBox)) {
                if (entity instanceof Sinkhole && !((Sinkhole) entity).isActive()) {
                    return null;
                }
                return entity;
            }
        }
        return null;
    }

    /**
     * Setting collision checking method for both level0 and level1
     */
    public void checkCollisions(Player player, Level0 level0Object, Level1 level1Object){
        Rectangle playerBox = new Rectangle(player.getPosition(), player.getCurrentImage().getWidth(),
                player.getCurrentImage().getHeight());
        Sinkhole hole;
        if (level0Object!=null) {

            if (hasStaticCollisions(playerBox, level0Object.getWalls())!=null) {
                player.moveBack();
            }

            if ((hole = (Sinkhole) hasStaticCollisions(playerBox, level0Object.getSinkholes()))!=null
                    && hole.isActive()){
                player.getHealthBar().setCurrentHealth(Math.max(player.getHealthPoints() - hole.getDamagePoints(), 0));
                player.moveBack();
                hole.setActive(false);
                hole.printMessage(player);
            }
        } else {
            if (hasStaticCollisions(playerBox, level1Object.getTrees())!=null) {
                player.moveBack();
            }
            if ((hole = (Sinkhole) hasStaticCollisions(playerBox, level1Object.getSinkholes()))!=null
                    && hole.isActive()){
                player.getHealthBar().setCurrentHealth(Math.max(player.getHealthPoints() - hole.getDamagePoints(), 0));
                player.moveBack();
                hole.setActive(false);
                hole.printMessage(player);
            }


        }
    }

}
