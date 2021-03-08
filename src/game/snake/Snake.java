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

    public void addToBody(Field bodyPart) {
        this.body.add(bodyPart);

    }

    public Field getCell(int index) {
        return this.body.get(index);
    }
}
