package game.fields;

import game.Constants;

import java.awt.*;

public class Death extends Field{
    public Death(int x, int y,int previousX,int previousY) {
        super(x, y, previousX,previousY,Color.BLACK);
    }

    @Override
    public void show(Graphics g) {
        super.show(g);
//        g.setColor(color);
        g.fillRect(x,y, Constants.FIELD_SIZE,Constants.FIELD_SIZE);
    }
}
