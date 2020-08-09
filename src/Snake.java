
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
    //Testing
    private boolean lose = false;
    
    //length of the snake can have
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    
    //initial postion of snake
    private final int[] initialXPos = new int[]{100, 75, 50};
    private final int initialYPos = 100;
    
    //direction of snake
    /*private boolean moveLeft = false;
    private boolean moveRight = false;
    private boolean moveUp = false;
    private boolean moveDown = false;*/
    private char direction;
    
    //icon for the snake
    private ImageIcon snakeImage;
    private ImageIcon foodImage;
    /*private ImageIcon titleImage;
    private ImageIcon snakeImage;
    private ImageIcon foodImage;
    private ImageIcon rightMouth;
    private ImageIcon leftMouth;
    private ImageIcon upMouth;
    private ImageIcon downMouth;*/
    
    private int lengthOfSnake = 3;
    private int moves = 0;
        
    private Timer timer;
    private int delay = 150;
    
    private int[] foodXPos = {25, 50, 75, 100, 125, 150, 175, 200, 225, 250, 275, 
        300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625, 650,
        675, 700, 725, 750, 775, 800, 825, 850};
    private int[] foodYPos = {75, 100, 125, 150, 175, 200, 225, 250, 275, 
        300, 325, 350, 375, 400, 425, 450, 475, 500, 525, 550, 575, 600, 625};    
    
    private Random random = new Random();
    private int xPos = random.nextInt(34);
    private int yPos = random.nextInt(23);
    
    private int scores = 0;
    
    public Snake(){
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        
        timer = new Timer(delay, this);
        timer.start();
    }
    
    public void paint(Graphics graphics){
        
        //set to default location
        if(moves == 0){

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
        graphics.drawRect(24, 10, 851, 55);
        
        //draw the title image
        snakeImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\snaketitle.jpg");
        snakeImage.paintIcon(this, graphics, 25, 11);
        
        //draw border for gameplay
        graphics.setColor(Color.WHITE);
        graphics.drawRect(24, 74, 851, 577);
        
        //draw background for the gameplay
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(25, 75, 850, 575);
        
        //draw score
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Scores: " + scores, 780, 30);
        
        //draw snake length
        graphics.setColor(Color.BLACK);
        graphics.setFont(new Font("arial", Font.PLAIN, 14));
        graphics.drawString("Length: " + lengthOfSnake, 780, 50);
    }
    
    //Initialize the snake image icon
    public void initSnakeIcon(Graphics graphics){
        if(moves == 0){
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
        foodImage = new ImageIcon("C:\\Users\\User\\Desktop\\SnakeGame\\src\\assets\\strawberry.png");
        
        if((foodXPos[xPos] == snakeXLength[0]) && (foodYPos[yPos] == snakeYLength[0])){
            scores++;
            lengthOfSnake++;
            xPos = random.nextInt(34);
            yPos = random.nextInt(23);
        }
        
        foodImage.paintIcon(this, graphics, foodXPos[xPos], foodYPos[yPos]);
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
                
                moves = 0;
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
                moves++;
                
                if(direction != 'a'){
                    
                    direction = 'd';
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_LEFT){
                moves++;
                
                if(direction != 'd'){
                    
                    direction = 'a';
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_UP){
                moves++;
                
                if(direction != 's'){
                    
                    direction = 'w';
                }
                
            }

            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                moves++;
                
                if(direction != 'w'){
                    
                    direction = 's';
                }

            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            moves = 0;
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
