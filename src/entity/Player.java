
package entity;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable{

    private int id;
    private String name;
    private int score;
    private GameLevel level;
    
    public Player(){}
    public Player(int id, String name, GameLevel level, int score){
        this.id = id;
        this.name = name;
        this.level = level;
        this.score = score;
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

    public GameLevel getLevel() {
        return level;
    }

    public void setLevel(GameLevel level) {
        this.level = level;
    }
    
    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
    
    @Override
    public int compareTo(Player o) {
        if(this.getScore()  > o.getScore()){
            return 1;
        }
        else
            return 0;
    }
    
    @Override
    public String toString(){
        return this.getId() + "\t" + this.getName() + "\t" + 
               this.getLevel() + "\t" + String.format("%03d", this.getScore());
    }
}
