package client;


import entity.Snake;
import java.awt.Color;
import javax.swing.JFrame;

public class SnakeGame {

    public static void main(String[] args){
        
        int width = 925;
        int height = 720;
        
        JFrame jframe = new JFrame();
        Snake gameplay = new Snake(width, height);
        
        jframe.setBounds(10, 10, width, height);
        jframe.setBackground(Color.DARK_GRAY);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jframe.add(gameplay);
    }
}
