
package entity;

import java.util.Random;
import javax.swing.ImageIcon;

public class Food {
    
    //ImageIcon
    private ImageIcon foodImage;   
    
    //food position
    private Random random = new Random();
    private int xPos;
    private int yPos;
    
    public Food(){}

    public ImageIcon getFoodImage() {
        return foodImage;
    }

    public void setFoodImage(ImageIcon foodImage) {
        this.foodImage = foodImage;
    }

    public int getXPos() {
        return xPos;
    }

    public void setXPos(int xPos) {
        this.xPos = xPos;
    }

    public int getYPos() {
        return yPos;
    }

    public void setYPos(int yPos) {
        this.yPos = yPos;
    }
    
    public int[] generateFoodRange(int minWidth, int maxWidth){
        
        //get total range for xRange array
        int range = 0;
        
        for(int i = minWidth; i <= maxWidth; i += 25){
            range++;
        }
        
        int[] xRange = new int[range];
        
        //get the range for the x
        for(int i = 0; i < range; i++){
            
            xRange[i] = minWidth;
            minWidth += 25;
            
        }
        
        return xRange;
    }
    
    public int generateFoodPositionCoordinate(int length){
        
        return random.nextInt(length);
    }
    
}
