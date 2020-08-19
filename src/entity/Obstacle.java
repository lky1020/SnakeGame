
package entity;

import javax.swing.ImageIcon;

/**
 *
 * @author KXian
 */
public class Obstacle {
    
    // Variable for coordinate(x,y)
    private int posX;
    private int posY;
    
    // ImageIcon
    private ImageIcon obstacleImage;
    
    // Empty Constructor
    public Obstacle(){}

    // Constructor with params
    public Obstacle(int posX, int posY){
        this.posX = posX;
        this.posY = posY;
    }
    
    // Getters and setters
    public int getPosX(){
        return posX;
    }

    public void setPosX(int posX) {
        this.posX = posX;
    }

    public int getPosY() {
        return posY;
    }   
    
    public void setPosY(int posY) {
        this.posY = posY;
    }

    public ImageIcon getObstacleImg() {
        return obstacleImage;
    }
    
    public void setObstacleImg(ImageIcon obstacleImage){
        this.obstacleImage = obstacleImage;
    }
}
