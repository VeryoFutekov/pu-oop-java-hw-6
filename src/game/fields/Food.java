package game.fields;

import game.Constants;

import java.awt.*;

public class Food extends Field{

    public Food(int x, int y,int previousX,int previousY) {
        super(x, y,previousX,previousY, Color.ORANGE);
    }


}
