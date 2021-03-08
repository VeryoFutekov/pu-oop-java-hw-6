package game.snake;

import game.Constants;
import game.fields.Field;

import java.awt.*;

public class Body extends Field {

    public Body(int x, int y,int previousX,int previousY) {
        super(x, y, previousX,previousY,Color.YELLOW);
    }


}
