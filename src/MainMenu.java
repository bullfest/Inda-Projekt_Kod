import java.awt.*;
import java.util.ArrayList;
import java.util.concurrent.Callable;

/**
 * Created by August on 2015-05-20.
 */
public class MainMenu extends Drawable {
    ArrayList<Button> buttons = new ArrayList<Button>();
    ArrayList<Integer> pressedKeys;
    int selectedButton = 0;

    public MainMenu(ArrayList<Integer> pressedKeys) {
        this.pressedKeys = pressedKeys;
        buttons.add(new Button("Start", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Pirates.hideMenu();
                Pirates.startGame();
                Pirates.showMenu();
                return null;
            }
        }));
        buttons.add(new Button("Exit", new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                System.exit(1);
                return null;
            }
        }));
        buttons.get(selectedButton).select();
    }

    @Override
    public void draw(Graphics2D g2d) {
        int centerX = Pirates.WINDOW_WIDTH/2, centerY = Pirates.WINDOW_HEIGHT/2;
        int buttonSpace = Button.HEIGHT/2;
        int menuHeight = buttons.size()*Button.HEIGHT + (buttons.size()-1)*buttonSpace;
        int x = centerX-Button.WIDTH/2, y = centerY-menuHeight/2;

        for (Button b:buttons) {
            b.draw(g2d,x,y);
            y+= buttonSpace+Button.HEIGHT;
        }
    }

    public void update() {
        ArrayList<Integer> pressedKeysClone = (ArrayList<Integer>)pressedKeys.clone();
        for (Integer pressedKey : pressedKeysClone) {
            if (pressedKey == 38) { // Up
                buttons.get(selectedButton).deselect();
                selectedButton = (selectedButton-1+buttons.size())%buttons.size();
                buttons.get(selectedButton).select();
            }
            if (pressedKey == 40) { // Down
                buttons.get(selectedButton).deselect();
                selectedButton = (selectedButton+1)%buttons.size();
                buttons.get(selectedButton).select();

            }
            if (pressedKey == 10) { // Enter
                buttons.get(selectedButton).click();
            }

        }
    }


    class Button {
        final static int WIDTH = 50, HEIGHT = 30;
        String text;
        Callable<Void> c;
        boolean selected = false;

        Button(String text, Callable<Void> c) {
            this.text = text;
            this.c = c;
        }

        void click() {
            try {
                c.call();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        void select() {
            selected = true;
        }

        void deselect() {
            selected = false;
        }

        void draw(Graphics2D g2d, int x, int y) {
            if (selected)
                g2d.setColor(Color.GREEN);
            else
                g2d.setColor(Color.RED);
            g2d.drawRect(x,y,WIDTH,HEIGHT);
            g2d.setColor(Color.BLACK);
            g2d.drawString(text,x+10,y+HEIGHT/2+3);
        }
    }
}
