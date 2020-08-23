package entity;


import adt.*;
import client.SnakeGameplay;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;


public class Snake extends JPanel implements KeyListener, ActionListener{
    
    private static final int TOTAL_FOOD = 100;
    
    private boolean isLose = false;
    private boolean isExit = false;
    
    private int boardWidth;
    private int boardHeight;
    
    //Player
    private Player player = null;
    
    //level
    int minutes;
    int seconds;
    Timer snakeLife;
    
    //length of the snake can have
    private int[] snakeXLength = new int[750];
    private int[] snakeYLength = new int[750];
    
    //initial postion of snake
    private final int[] INITIAL_X_POS = new int[]{100, 75, 50};
    private final int INITIAL_Y_POS = 100;
    
    //direction of snake
    private char direction;
    
    //icon for the snake
    private ImageIcon snakeImage;
    
    private int lengthOfSnake = 3;
    private boolean movement = false;
        
    private Timer snakeSpeed;
    private int snakeDelay = 100;
    
    private int[] foodXPos = null;
    private int[] foodYPos = null;
    private Food foodPosition = new Food();
    
    private int scores = 0;
    
    ////ADT
    private GameLevel level = new GameLevel();
    private QueueInterface<Food> foodQueue = new CircularLinkedQueue<>();
    
    public Snake(){}
    
    public Snake(int boardWidth, int boardHeight, int gameLevelResult, Player player){

        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        //Player
        this.player = player;
        
        //Level
        level.setLevel(gameLevelResult);//set the level of the game
        this.setMinutes(level.getPlayTime()); //only will use minutes after this
        initSnakeTime(this.getMinutes());
        
        //Set board size
        this.setBoardWidth(boardWidth - 75); //set to 850
        this.setBoardHeight(boardHeight - 95); //set to 625

        //generate foodPosition range
        this.setFoodXPos(foodPosition.generateFoodRange(25, this.getBoardWidth()));
        this.setFoodYPos(foodPosition.generateFoodRange(75, this.getBoardHeight()));
        
        //Generate All Food and queue it
        this.generateTotalFood();
        
        //Add obstacle into arrayList
        level.addObstaclePos();
        
        //the speed of snake
        snakeSpeed = new Timer(snakeDelay, this);
        snakeSpeed.start();
        
        //snake life(time)
        snakeTime();
        snakeLife.start();
    }
    
    //setter && getters
    public boolean getIsLose() {
        return isLose;
    }

    public void setIsLose(boolean isLose) {
        this.isLose = isLose;
    }

    public boolean getIsExit() {
        return isExit;
    }

    public void setIsExit(boolean isExit) {    
        this.isExit = isExit;
    }

    public int getBoardWidth() {
        return boardWidth;
    }

    public void setBoardWidth(int boardWidth) {
        this.boardWidth = boardWidth;
    }

    public int getBoardHeight() {
        return boardHeight;
    }

    public void setBoardHeight(int boardHeight) {    
        this.boardHeight = boardHeight;
    }

    public int getMinutes() {
        return minutes;
    }
    
    public void setMinutes(int minutes) {
        this.minutes = minutes;
        this.setSeconds(0);
    }

    public int getSeconds() {
        return seconds;
    }

    public void setSeconds(int seconds) {
        this.seconds = seconds;
    }

    public int[] getFoodXPos() {
        return foodXPos;
    }

    public void setFoodXPos(int[] foodXPos) {
        this.foodXPos = foodXPos;
    }

    public int[] getFoodYPos() {
        return foodYPos;
    }

    public void setFoodYPos(int[] foodYPos) {
        this.foodYPos = foodYPos;
    }
    
    //Start snake
    public void generateTotalFood(){
        for(int i = 0; i < TOTAL_FOOD; i++){
            Food foodTemp = new Food();
            
            foodTemp.setXPos(foodTemp.generateFoodPositionCoordinate(foodXPos.length));
            foodTemp.setYPos(foodTemp.generateFoodPositionCoordinate(foodYPos.length));
            
            //add into queue
            foodQueue.enqueue(foodTemp);
            
        }
        
        foodPosition = foodQueue.getFront();
    }
    
    @Override
    public void paint(Graphics graphics){
        if(this.getIsExit() != true){
            //set to default location
            if(movement == false){

                setInitialSnakePosition(this.INITIAL_X_POS, this.INITIAL_Y_POS);
            }

            initComponents(graphics);

            initSnakeIcon(graphics);
            
            snakeGameOver(graphics);

            initFoodPosition(graphics);

            initObstaclePosition(graphics);
            
            
            
            graphics.dispose();
            
        }else{
            SnakeGameplay snakeGameplay = new SnakeGameplay(player);
            snakeGameplay.setVisible(true);
            JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            topFrame.dispose();
        }
    }

    //Initialize Snake Position
    public void setInitialSnakePosition(int[] INITIAL_X_POS, int INITIAL_Y_POS){
        snakeXLength[2] = INITIAL_X_POS[2];
        snakeXLength[1] = INITIAL_X_POS[1];
        snakeXLength[0] = INITIAL_X_POS[0];
            
        snakeYLength[2] = INITIAL_Y_POS;
        snakeYLength[1] = INITIAL_Y_POS;
        snakeYLength[0] = INITIAL_Y_POS;
    }
    
    //Initialize the component of the frame
    public void initComponents(Graphics graphics){
        
        //draw title image border
        graphics.setColor(Color.WHITE);
        graphics.drawRect(30, 10, this.getBoardWidth(), 50);
        
        graphics.setColor(Color.WHITE);
        graphics.fillRect(30, 10, this.getBoardWidth(), 50);
        
        //draw the title image
        snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\snaketitle.jpg");
        snakeImage.paintIcon(this, graphics, 420, 11);
        
        //draw border for gameplay
        graphics.setColor(Color.WHITE);
        graphics.drawRect(30, 74, this.getBoardWidth(), 585);
        
        //draw background for the gameplay
        graphics.setColor(Color.LIGHT_GRAY);
        graphics.fillRect(25, 75, this.getBoardWidth() + 10, 585);
        
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
        graphics.drawString("Time: " + this.getMinutes() + ":" + String.format("%02d", this.getSeconds()), 780, 50);
    }
    
    //Initialize the snake image icon
    public void initSnakeIcon(Graphics graphics){
        if(movement == false){
            //initial will be right mouth
            snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\rightmouth.png");
            snakeImage.paintIcon(this, graphics, snakeXLength[0], snakeYLength[0]);
        }

        for(int i = 0; i < lengthOfSnake; i++){
            
            if(i == 0 && direction == 'd'){
                //right mouth
                snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\rightmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && direction == 'a'){
                //left mouth
                snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\leftmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && direction == 'w'){
                //up mouth
                snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\upmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            if(i == 0 && direction == 's'){
                //down mouth
                snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\downmouth.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
            
            //start draw the snake body
            if(i != 0){
                snakeImage = new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\snakeimage.png");
                snakeImage.paintIcon(this, graphics, snakeXLength[i], snakeYLength[i]);
            }
        }
    }
    
    //Game Over (will call before initFoodPosition to prevent food not eaten and scores not refresh)
    public void snakeGameOver(Graphics graphics){
        
        boolean gameOver = false;
        
        //isLose (head touch tail || head touch obstacle)
        for(int i = 1; i < lengthOfSnake; i++){
            if((snakeXLength[i] == snakeXLength[0]) && (snakeYLength[i] == snakeYLength[0])){

                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Game Over", 300, 300);
                
                gameOver = true;

            }
        }
        
        // Check for obstacle - If snake hit obstacle then game over
        for(int i = 1; i <= level.getObstacle().getLength(); i++){
            if((snakeXLength[0] == level.getObstacle().getEntry(i).getPosX()) && (snakeYLength[0]) == level.getObstacle().getEntry(i).getPosY()){
                
                graphics.setColor(Color.WHITE);
                graphics.setFont(new Font("arial", Font.BOLD, 50));
                graphics.drawString("Game Over", 300, 300);
                
                gameOver = true;
            }
        }
        
        //Time's up
        if(this.getMinutes() == 0 && this.getSeconds() == 0){

            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("arial", Font.BOLD, 50));
            graphics.drawString("Time's Up", 320, 300);

            gameOver = true;
        }
        
        //Food Finish
        if(foodQueue.isEmpty()){
            
            graphics.setColor(Color.WHITE);
            graphics.setFont(new Font("arial", Font.BOLD, 50));
            graphics.drawString("Game Completed", 230, 300);
            
            gameOver = true;
        }
        
        if(gameOver){
            //Not allow key input anymore
            direction = 'n';
            
            graphics.setFont(new Font("arial", Font.BOLD, 20));
            graphics.drawString("Space to RESTART", 350, 340);

            graphics.setFont(new Font("arial", Font.BOLD, 18));
            graphics.drawString("Esc to Main Menu", 360, 380);
            
            //Prevent snake move
            movement = false;
            
            //Inidcate Game Over of Game Finish (Food Finish || Time's Up || Game Over)
            this.setIsLose(true);
        }
        
    }
    
    //Initialize the foodPosition position
    public void initFoodPosition(Graphics graphics){

        if(!foodQueue.isEmpty()){
            foodPosition.setFoodImage(new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\strawberry.png"));
        
            if((this.getFoodXPos()[foodPosition.getXPos()] == snakeXLength[0]) && (this.getFoodYPos()[foodPosition.getYPos()] == snakeYLength[0])){
                scores++;
                lengthOfSnake++;
                
                //dequeue the first position after snake eat the food
                foodQueue.dequeue();
            }

            foodPosition.getFoodImage().paintIcon(this, graphics, this.getFoodXPos()[foodPosition.getXPos()], this.getFoodYPos()[foodPosition.getYPos()]);

            //getfront for the foodPosition after dequeue
            foodPosition = foodQueue.getFront();
            
        }
    }
    
    //Draw the obstacle
    private void initObstaclePosition(Graphics graphics) {
        level.setObstacleImg(new ImageIcon(System.getProperty("user.dir") + "\\src\\assets\\obstacle.png"));
        
        for(int i = 1; i <= level.getObstacle().getLength(); i++){
            level.getObstacleImg().paintIcon(this, graphics, level.getObstacle().getEntry(i).getPosX(), level.getObstacle().getEntry(i).getPosY());

            //Prevent Food and Obstacle in same position (food will set at initFoodPosition
            if(!foodQueue.isEmpty() && (foodXPos[foodPosition.getXPos()] == level.getObstacle().getEntry(i).getPosX()) && (foodYPos[foodPosition.getYPos()] == level.getObstacle().getEntry(i).getPosY())){
                
                //Dequeue the food that duplicate the obstacle
                foodQueue.dequeue();
                
                //getfront for the foodPosition after dequeue
                foodPosition = foodQueue.getFront();
            }

        }
    }

    public void initSnakeTime(int minutes){
        this.setMinutes(minutes);
        this.setSeconds(0);
    }
    
    //Timer for the snake game
    public void snakeTime(){
        snakeLife = new Timer(1000, new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent e) {
                
                if(movement != false){
                    if(seconds == 0){
                        minutes--;
                        seconds = 59;
                    }else{
                        seconds--;
                    }
                }
            }
        });
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        
    }

    @Override
    public void keyPressed(KeyEvent e) {
        
        if(this.getIsLose() == false){
            if(e.getKeyCode() == KeyEvent.VK_RIGHT){
                movement = true;
                
                if(direction != 'a'){
                    
                    direction = 'd';
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_LEFT && movement == true){
                movement = true;
                
                if(direction != 'd'){
                    
                    direction = 'a';
                }
            }

            if(e.getKeyCode() == KeyEvent.VK_UP){
                movement = true;
                
                if(direction != 's'){
                    
                    direction = 'w';
                }
                
            }

            if(e.getKeyCode() == KeyEvent.VK_DOWN){
                movement = true;
                
                if(direction != 'w'){
                    
                    direction = 's';
                }

            }
        }
        else if(e.getKeyCode() == KeyEvent.VK_SPACE){
            movement = false;
            scores = 0;
            lengthOfSnake = 3;
            this.setMinutes(this.level.getPlayTime());
            
            //Generate All Food and queue it
            foodQueue.clear();  //clear previous foodQueue
            this.generateTotalFood();
        
            this.setIsLose(false);
            repaint();
        }
        else if(e.getKeyCode() == KeyEvent.VK_ESCAPE){
            //Save the scores to the GameHistory to pass back to snakeGameplay.java
            if(player.getGameHistory() != null){
                
                player.getGameHistory().add(new GameHistory(this.level, this.scores));
                
            }else{
                
                ListInterfaceWithIterator<GameHistory> gameHistory = new LinkedListWithIterator<>();
                
                gameHistory.add(new GameHistory(this.level, this.scores));
                player.setGameHistory(gameHistory);

            }  
            
            scores = 0;
            lengthOfSnake = 3;
            
            this.setIsExit(true);
            repaint();
        }

    }

    @Override
    public void keyReleased(KeyEvent e) {
        
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        snakeSpeed.start();
        
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
