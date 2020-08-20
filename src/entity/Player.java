
package entity;

import adt.*;
import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable{

    private int id;
    private String name;
    private GameLevel currentLevel;
    private int highScore;
    private ListInterfaceWithIterator<GameHistory> gameHistory;
    
    public Player(){}
    public Player(int id, String name, GameLevel currentLevel, int highScore, ListInterfaceWithIterator<GameHistory> gameHistory){
        this.id = id;
        this.name = name;
        this.currentLevel = currentLevel;
        this.highScore = highScore;
        this.gameHistory = gameHistory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public GameLevel getCurrentLevel() {
        return currentLevel;
    }

    public void setCurrentLevel(GameLevel currentLevel) {
        this.currentLevel = currentLevel;
    }
    
    public int gethighScore() {
        return highScore;
    }

    public void sethighScore(int highScore) {
        this.highScore = highScore;
    }

    public ListInterfaceWithIterator<GameHistory> getGameHistory() {
        return this.gameHistory;
    }

    public void setGameHistory(ListInterfaceWithIterator<GameHistory> gameHistory) {
        this.gameHistory = gameHistory;
    }
    
    //FUNCTION
    //GENERATE SCORE AFTER EACH GAME
    public void generateScore(int score){
        this.gameHistory.add(new GameHistory(currentLevel, score)); //new GameHistory(score,lvl)
        if(this.highScore < score)
            this.highScore = score;
    }
    
    @Override
    public int compareTo(Player o) {
        //Top to low
        if(this.gethighScore()  > o.gethighScore()){
            return 0;
        }
        else
            return 1;
    }
    
    @Override
    public String toString(){
        return this.getId() + "\t" + this.getName() + "\t" + 
               String.format("%03d", this.gethighScore());
    }
}
