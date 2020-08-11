package entity;


import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;


public class Snake extends JPanel implements KeyListener, ActionListener{
    //Testing 1
    private boolean lose = false;
    //testing 2
    private int width;
    private int height;
    //testing3
    
    //length of the snake can have
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    
    //initial postion of snake
    private final int[] initialXPos = new int[]{100, 75, 50};
    private final int initialYPos = 100;
    
    //direction of snake
    private char direction;
    
    //icon for the snake
    private ImageIcon snakeImage;
    
    private int lengthOfSnake = 3;
    private int movement = 0;
        
    private Timer timer;
    private int delay = 100;
    
    private int[] foodXPos = null;
    private int[] foodYPos = null;
    private Food food = new Food();
    
    private int scores = 0;
    
    public Snake(){}
    
    public Snake(int width, int height){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        this.width = width - 75; //set to 850
        this.height = height - 95; //set to 625
        
        //generate food range
        foodXPos = food.generateFoodRange(25, this.width);
        foodYPos = food.generateFoodRange(75, this.height);
        
        food.setxPos(food.generateFoodPositionCoordinate(foodXPos.length));
        food.setyPos(food.generateFoodPositionCoordinate(foodYPos.length));
        
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics graphics){
        
        //set to default location
        if(movement == 0){

            setInitialSnakePosition(this.initialXPos, this.initialYPos);
        }
        
        initComponents(graphics);
        
        initSnakeIcon(graphics);
        
        initFoodPosition(graphics);
        
        snakeGameOver(graphics);

        graphics.dispose();
    }

    //Initialize Snake Position
    public void setInitialSnakePosition(int[] initialXPos, int initialYPos){
        snakeXLength[2] = initialXPos[2];
        snakeXLength[1] = initialXPos[1];
        snakeXLength[0] = initialXPos[0];
            
        snakeYLength[2] = initialYPos;
        snakeYLength[1] = initialYPos;
        snakeYLength[0] = initialYPos;
    }
    
    //Initialize the component of the frame
    public void initComponents(Graphics graphics){
        //draw title image border
        graphics.setColor(Color.WHITE);
        graphics.drawRect(30, 10, this.width, 50);
        
        //draw the title image
        snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\snaketitle.jpg");
        snakeImage.paintIcon(this, graphics, 30, 11);
        
        //draw border for gameplay
        graphics.setColor(Color.WHITE);
        graphics.drawRect(30, 74, this.width, 585);
        
        //draw background for the gameplay
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(25, 75, this.width + 10, 585);
        
        //draw score
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Scores: " + scores, 780, 30);
        
        //draw snake length
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Length: " + lengthOfSnake, 35, 50);
        
        //draw time
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Time: " + lengthOfSnake, 780, 50);
    }
    
    //Initialize the snake image icon
    public void initSnakeIcon(Graphics graphics){
        if(movement == 0){
            //initial will be right mouth
            snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\rightmouth.png");
            snakeImage.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);
        }

        for(int i = 0; i < lengthOfSnake; i++){
            
            if(i == 0 && direction == 'd'){
                //right mouth
                snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\rightmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && direction == 'a'){
                //left mouth
                snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\leftmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && direction == 'w'){
                //up mouth
                snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\upmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && direction == 's'){
                //down mouth
                snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\downmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            
            //start draw the snake body
            if(i != 0){
                snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\snakeimage.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
        }
    }
    
    //Initialize the food position
    public void initFoodPosition(Graphics graphics){
        
        food.setFoodImage(new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\strawberry.png"));
        
        if((foodXPos[food.getxPos()] == snakeXLength[0]) && (foodYPos[food.getyPos()] == snakeYLength[0])){
            scores++;
            lengthOfSnake++;
            
            //update food position
            food.setxPos(food.generateFoodPositionCoordinate(foodXPos.length));
            food.setyPos(food.generateFoodPositionCoordinate(foodYPos.length));
        }
        
        food.getFoodImage().paintIcon(this, graphics, foodXPos[food.getxPos()], foodYPos[food.getyPos()]);
    }
    
    //Game Over
    public void snakeGameOver(Graphics graphics){
        //lose (head touch body)
        for(int i = 1; i < lengthOfSnake; i++){
            if((snakeXLength[i] == snakeXLength[0]) && (snakeYLength[i] == snakeYLength[0])){
                direction = 'n';
                
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Game Over", 300, 300);
                
                graphics.setFont(new Font("arial", Font.BOLD, 20));
                graphics.drawString("Space to RESTART", 350, 340);
                
                movement = 0;
                scores = 0;
                lengthOfSnake = 3;
                lose = true;
            }
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(lose == false){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                movement++;
                
                if(direction != 'a'){
                    
                    direction = 'd';
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_LEFT && movement != 0){
                movement++;
                
                if(direction != 'd'){
                    
                    direction = 'a';
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_UP){
                movement++;
                
                if(direction != 's'){
                    
                    direction = 'w';
                }
                
            }

            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                movement++;
                
                if(direction != 'w'){
                    
                    direction = 's';
                }

            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            movement = 0;
            scores = 0;
            lengthOfSnake = 3;
            lose = false;
            repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();
        
        if(direction == 'd'){
            for(int i = lengthOfSnake - 1; i >= 0; i--){
                snakeYLength[i + 1] = snakeYLength[i];
            }
            
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeXLength[i] = snakeXLength[i] + 25;
                }else{
                    snakeXLength[i] = snakeXLength[i - 1];
                }
                
                //snake touch border
                if(snakeXLength[i] > 850){
                    snakeXLength[i] = 25;
                }
            }
            
            //call paint
            repaint();

        }
        if(direction == 'a'){
            for(int i = lengthOfSnake - 1; i >= 0; i--){
                snakeYLength[i + 1] = snakeYLength[i];
            }
            
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeXLength[i] = snakeXLength[i] - 25;
                }else{
                    snakeXLength[i] = snakeXLength[i - 1];
                }
                
                //snake touch border
                if(snakeXLength[i] < 25){
                    snakeXLength[i] = 850;
                }
            }
            
            //call paint
            repaint();
        }
        if(direction == 'w'){
            for(int i = lengthOfSnake - 1; i >= 0; i--){
                snakeXLength[i + 1] = snakeXLength[i];
            }
            
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeYLength[i] = snakeYLength[i] - 25;
                }else{
                    snakeYLength[i] = snakeYLength[i - 1];
                }
                
                //snake touch border
                if(snakeYLength[i] < 75){
                    snakeYLength[i] = 625;
                }
            }
            
            //call paint
            repaint();
        }
        if(direction == 's'){
            for(int i = lengthOfSnake - 1; i >= 0; i--){
                snakeXLength[i + 1] = snakeXLength[i];
            }
            
            for(int i = lengthOfSnake; i >= 0; i--){
                if(i == 0){
                    snakeYLength[i] = snakeYLength[i] + 25;
                }else{
                    snakeYLength[i] = snakeYLength[i - 1];
                }
                
                //snake touch border
                if(snakeYLength[i] > 625){
                    snakeYLength[i] = 75;
                }
            }
            
            //call paint
            repaint();
        }
    }
}
