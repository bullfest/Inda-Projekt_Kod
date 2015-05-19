import java.awt.*;
import java.util.*;

public class HpDisplay extends Drawable {
    private int height = 15;
    private ArrayList<Ship> ships = new ArrayList<Ship>();
    
    public HpDisplay(ArrayList<Ship> ships) {
        this.ships = ships;
    }
    
    public void draw(Graphics2D g2d) {
        g2d.setColor(Color.RED);
        int xPos = 0;
        for (int i=0; i < ships.size();i++) {
            g2d.drawString("Player "+(i+1)+": "+ships.get(i).getHitPoints(), xPos, height);
            xPos += 100;
        }
    }
}
