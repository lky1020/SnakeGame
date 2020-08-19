
package entity;

import javax.swing.ImageIcon;
import adt.*;

public class GameLevel {
    // GameLevel Setting : 1 - Easy, 2 - Normal, 3 - Hard
    private int levelID;
    private final int easyTime = 3;
    private final int normalTime = 2;
    private final int hardTime = 1;
    
    
    // ADT
    private ListInterface<Obstacle> obstacle = new ArrayList<>();
    
    // ImageIcon
    private ImageIcon obstacleImage;
    
    // Empty Constructor
    public GameLevel(){}
    
    // Constructor with params
    public GameLevel(int levelID){
        this.levelID = levelID;
    }
    
    public int getPlayTime(){
        if(levelID == 2){
            return normalTime;
        }
        else if(levelID == 3){
            return hardTime;
        }
        else{
            return easyTime;
        }
    }
    
    // Getters
    public ListInterface<Obstacle> getObstacle() {
        return obstacle;
    }
    
    public ImageIcon getObstacleImg(){
        return obstacleImage;
    }
    
    public int getLevel(){
        return levelID;
    }
    
    // Setters
    public void setObstacleImg(ImageIcon obstacleImage){
        this.obstacleImage = obstacleImage;
    }
    
    public void setLevel(int levelID){
        this.levelID = levelID;
    }
    
    // Add Obstacle Position into the mormalObstacle ArrayList
    public void addObstaclePos(){
        // Switch for obstacle of different difficulty
        switch(levelID){
            case 2:
                // Random placed obstacle
                obstacle.add(new Obstacle(600,450));
                obstacle.add(new Obstacle(500,100));
                obstacle.add(new Obstacle(250,500));
                obstacle.add(new Obstacle(150,150));
                obstacle.add(new Obstacle(700,600));
                obstacle.add(new Obstacle(600,200));
                obstacle.add(new Obstacle(450,350));
                obstacle.add(new Obstacle(100,400));
                break;
            case 3:
                // Random placed obstacle
                obstacle.add(new Obstacle(150,150));
                obstacle.add(new Obstacle(700,600));
                obstacle.add(new Obstacle(500,100));
                obstacle.add(new Obstacle(400,575));
                obstacle.add(new Obstacle(725,300));
                obstacle.add(new Obstacle(100,425));
                obstacle.add(new Obstacle(800,175));
                obstacle.add(new Obstacle(800,175));
                
                // Top & Bottom Obstacle Wall
                for(int i = 325; i <= 525 ; i += 25){
                    obstacle.add(new Obstacle(i,200));
                    obstacle.add(new Obstacle(i,500));
                }
                
                // Left & Right Obstacle Wall
                for(int i = 275; i <= 400; i += 25){
                    obstacle.add(new Obstacle(250,i));
                    obstacle.add(new Obstacle(600,i));
                }
                break;
            // Default wall - None
            default:
                break;
        }
    }
}
