import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class StartGameFrame implements Common {

	static JFrame f1;
    public StartGameFrame() 
    {	
    	f1 = new JFrame();

        f1.add(new Board());

        f1.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f1.setSize(BOARD_WIDTH+15, BOARD_HEIGHT+38);
        f1.setLocationRelativeTo(null);
        f1.setTitle("Top DownShooter Prototype");
        f1.setResizable(true);
        f1.setVisible(true);
     
    }

    public static void main(String[] args) {
        new StartGameFrame();
    }
}