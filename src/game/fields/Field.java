package game.fields;

import game.Constants;

import java.awt.*;

public class Field {

    protected int x;
    protected int y;

    protected int previousX;
    protected int previousY;

    protected Color color;


    public Field(int x, int y, int previousX, int previousY, Color color) {
        this.color = color;
        this.x=x* Constants.FIELD_SIZE;
        this.y=y*Constants.FIELD_SIZE;
        this.previousX = previousX;
        this.previousY = previousY;
    }

    /**
     * render
     * @param g
     */
    public void show(Graphics g) {
        g.setColor(color);
        g.fillRect(x, y, Constants.FIELD_SIZE, Constants.FIELD_SIZE);

    }

    public Color getColor() {
        return color;
    }

    public int getX() {
        return x / Constants.FIELD_SIZE;
    }

    public int getY() {
        return y / Constants.FIELD_SIZE;
    }

    public void setPreviousX(int previousX) {
        this.previousX = previousX;
    }

    public void setPreviousY(int previousY) {
        this.previousY = previousY;
    }

    public int getPreviousX() {
        return previousX;
    }

    public int getPreviousY() {
        return previousY;
    }

    public void setX(int x) {
        this.x = x * Constants.FIELD_SIZE;
    }

    public void setY(int y) {
        this.y = y * Constants.FIELD_SIZE;
    }
}
