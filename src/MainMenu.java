import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by August on 2015-05-20.
 */
public class MainMenu extends Container {
    private JButton startGameButton;
    private JPanel menu;

    public MainMenu() {
        startGameButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    Pirates.clickStart();
                    System.out.print("test");
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        });
    }

    public JPanel getContentPane() {
        return menu;
    }
}
