package game.snake;

import game.Constants;
import game.fields.Field;

import java.awt.*;

public class Head extends Field {

    public Head(int x, int y,int previousX,int previousY) {
        super(x, y, previousX,previousY,Color.RED);
    }

}
