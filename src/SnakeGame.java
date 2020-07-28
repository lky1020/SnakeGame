
import java.awt.Color;
import javax.swing.JFrame;


public class SnakeGame {
    public static void main(String[] args){
        JFrame jframe = new JFrame();
        Snake gameplay = new Snake();
        
        jframe.setBounds(10, 10, 905, 700);
        jframe.setBackground(Color.DARK_GRAY);
        jframe.setResizable(false);
        jframe.setVisible(true);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        jframe.add(gameplay);
    }
}
