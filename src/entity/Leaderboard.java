/**
 *
 * @author yong0312
 */
package entity;

import adt.*;
import java.io.*;
import javax.swing.*;

public class Leaderboard implements Serializable {

    private String rankType;
    private SortedListInterface<Player> playerSortedList = new SortedArrayList<>();
    private static final String LEADERBOARD_FILE = "leaderboard.dat";

    public Leaderboard() {
    }

    public Leaderboard(String rankType, SortedListInterface<Player> playerSortedList) {
        this.rankType = rankType;
        this.playerSortedList = playerSortedList;
    }

    public String getRankType() {
        return rankType;
    }

    public void setRankType(String rankType) {
        this.rankType = rankType;
    }

    public SortedListInterface<Player> getPlayerSortedList() {
        return playerSortedList;
    }

    public void setPlayerSortedList(SortedListInterface<Player> playerSortedList) {
        this.playerSortedList = playerSortedList;
    }

    public void saveLeaderboardDataToFile(ListInterface<Leaderboard> leaderBoardList) {
        try {

            File file = new File(LEADERBOARD_FILE);
            System.out.println("***TRACE SAVE: " + file.getAbsolutePath());
            ObjectOutputStream ooStream = new ObjectOutputStream(new FileOutputStream(file));
            ooStream.writeObject(leaderBoardList);
            ooStream.close();

        } catch (FileNotFoundException ex) {

            JOptionPane.showMessageDialog(null, "File not found", "ERROR", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Cannot save to file", "ERROR", JOptionPane.ERROR_MESSAGE);

        }
    }

    public ListInterface<Leaderboard> readLeaderboardDataFromFile() {
        ListInterface<Leaderboard> leaderBoardList = null;

        try {
            File file = new File(LEADERBOARD_FILE);
            System.out.println("***TRACE READ: " + file.getAbsolutePath());
            ObjectInputStream oiStream = new ObjectInputStream(new FileInputStream(file));
            leaderBoardList = (adt.ArrayList) (oiStream.readObject());
            oiStream.close();

        } catch (FileNotFoundException ex) {

            JOptionPane.showMessageDialog(null, "File not found", "ERROR", JOptionPane.ERROR_MESSAGE);

        } catch (IOException ex) {

            JOptionPane.showMessageDialog(null, "Cannot read from file", "ERROR", JOptionPane.ERROR_MESSAGE);

        } catch (ClassNotFoundException ex) {

            JOptionPane.showMessageDialog(null, "Class not found", "ERROR", JOptionPane.ERROR_MESSAGE);

        }

        return leaderBoardList;
    }

    @Override
    public String toString() {
        return this.playerSortedList.toString();
    }
}
