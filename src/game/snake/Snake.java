package game.snake;

import game.fields.Field;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Snake {
    private List<Field> body;

    public Snake() {
        this.body = new ArrayList<>();
    }

    /**
     * add part to the body of the snake
     * @param bodyPart the new part
     */
    public void addToBody(Field bodyPart) {
        this.body.add(bodyPart);

    }

    /**
     * get cell from the snake
     * @param index index of the part
     * @return chosen cell
     */
    public Field getCell(int index) {
        return this.body.get(index);
    }

    /**
     * get size of the snake
     * @return
     */
    public int getSize(){
        return this.body.size();
    }


}
