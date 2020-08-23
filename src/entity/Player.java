package entity;

import adt.*;
import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {

    private static int idTemp = 1001;
    private int id;
    private String name;
    private GameLevel currentLevel;
    private int highScore = 0;
    private ListInterfaceWithIterator<GameHistory> gameHistory;

    //CONSTRUCTORS
    public Player() {
    }

    public Player(String name) {
        this.id = idTemp++;
        this.name = name;
    }

    public Player(String name, ListInterfaceWithIterator<GameHistory> gameHistory) {
        this.id = idTemp++;
        this.name = name;
        this.gameHistory = gameHistory;

        this.getCurrentGameStatus();
    }
    
    //GETTERS AND SETTERS
    public static int getIdTemp() {
        return idTemp;
    }

    public static void setIdTemp(int idTemp) {
        Player.idTemp = idTemp;
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
    //Get Current Game Status to set the highSocre and currenLevel
    public void getCurrentGameStatus() {

        //start from 1
        for (int i = 1; i <= gameHistory.getLength(); i++) {
            if (gameHistory.getEntry(i).getScore() > this.gethighScore()) {
                this.currentLevel = gameHistory.getEntry(i).getLevel();
                this.highScore = gameHistory.getEntry(i).getScore();
            }
        }
    }

    @Override
    public int compareTo(Player o) {
        //Top to low
        if (this.gethighScore() > o.gethighScore()) {
            return 0;
        } else {
            return 1;
        }
    }

    @Override
    public String toString() {
        return this.getId() + "\t" + this.getName() + "\t"
                + this.getCurrentLevel() + "\t" + String.format("%03d", this.gethighScore());
    }
}
