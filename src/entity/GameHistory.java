package entity;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

public class GameHistory implements Serializable {

    private Date dateTime;
    private GameLevel level;
    private int score;

    //CONSTRUCTORS
    public GameHistory() {
    }

    public GameHistory(GameLevel level, int score) {
        this.dateTime = new Date();
        this.level = level;
        this.score = score;
    }

    //GETTERS
    public String getDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return formatter.format(this.dateTime);
    }

    public GameLevel getLevel() {
        return this.level;
    }

    public int getScore() {
        return this.score;
    }

    //SETTERS
    public void setLevel(GameLevel level) {
        this.level = level;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return this.getDateTime() + "\t" + this.getLevel() + "\t" + String.format("%03d", this.getScore());

    }
}
