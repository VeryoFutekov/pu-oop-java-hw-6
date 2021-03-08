package game;

import game.fields.Death;
import game.fields.Field;
import game.fields.Food;
import game.snake.Body;
import game.snake.Snake;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class Window extends JFrame implements ActionListener {


    private Field[][] fields;
    private Snake snake;
    private int points;
    private Timer timer;
    private String direction = "none";

    public Window() throws HeadlessException {
        timer = new Timer(2000, this);


        super.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                int code = e.getKeyCode();

                if (!direction.equals("down") && code == KeyEvent.VK_UP) {
                    direction = "up";
                } else if (!direction.equals("up") && code == KeyEvent.VK_DOWN) {
                    direction = "down";
                } else if (!direction.equals("right") && code == KeyEvent.VK_LEFT) {
                    direction = "left";
                } else if (!direction.equals("left") && code == KeyEvent.VK_RIGHT) {
                    direction = "right";
                }
            }
        });

    }


    public void init() {
        this.setSize(800, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fields = new Field[8][8];
        snake = new Snake();
        timer.start();
        fieldInitialization();
    }

    private void fieldInitialization() {
        Random random = new Random();
        generateSnake(random);
        generateObstacles(random);
        generateFood(random);

    }

    private void generateSnake(Random random) {
        int x = random.nextInt(7);
        int y = random.nextInt(7);
        Field snakeHead = fields[y][x];

        while (snakeHead != null) {
            x = random.nextInt(7);
            y = random.nextInt(7);
            snakeHead = fields[y][x];

        }

        Field headOfSnake = new Field(x, y, x, y, Color.RED);
        fields[y][x] = headOfSnake;
        this.snake.addToBody(headOfSnake);

    }

    private void generateObstacles(Random random) {
        int count = random.nextInt(11);

        for (int i = 0; i < count; i++) {
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            Field field = fields[row][col];

            if (field == null) {
                fields[row][col] = new Death(col, row, col, row);
                System.out.println(i);
                continue;
            }
            i--;
        }

    }

    ;

    private void generateFood(Random random) {
        int count = random.nextInt(10) + 20;

        for (int i = 0; i < count; i++) {
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            Field cell = fields[row][col];

            if (cell == null) {
                fields[row][col] = new Food(col, row, col, row);
                continue;
            }
            i--;

        }
    }

    @Override
    public void paint(Graphics g) {
        super.paint(g);

        for (int row = 0; row < 8; row++) {
            for (int col = 0; col < 8; col++) {
                if (fields[row][col] != null) {
                    fields[row][col].show(g);
                }
            }
        }
    }


    ;

    @Override
    public void actionPerformed(ActionEvent e) {
        Field head = this.snake.getCell(0);
        int headX = head.getX();
        int headY = head.getY();

        Point nextPositions = findNextPosition(headX, headY);

        if (nextPositions == null) {
            return;
        }

        nextPositions = validatePositions(nextPositions);
        int nextPositionX = nextPositions.x;
        int nextPositionY = nextPositions.y;


        this.move(nextPositionX, nextPositionY, headX, headY);

        repaint();

    }

    private void move(int nextPositionX, int nextPositionY, int currentPX, int currentPY) {
        Field targetCell = fields[nextPositionY][nextPositionX];

        if (targetCell instanceof Body || targetCell instanceof Death) {
            showModal();
        } else if (targetCell instanceof Food) {
            points += 15;
        }

        setHeadPositions(nextPositionX, nextPositionY, currentPX, currentPY);

        int counter = 0;


        while (counter < snake.getSize() - 1) {
            Field previous = snake.getCell(counter);
            Field next = snake.getCell(counter + 1);

            next.setPreviousX(next.getX());
            next.setPreviousY(next.getY());

            next.setX(previous.getPreviousX());
            next.setY(previous.getPreviousY());

            fields[previous.getY()][previous.getX()] = previous;
            fields[next.getY()][next.getX()] = next;

            counter++;

        }


        Field lastPart = snake.getCell(snake.getSize() - 1);
        fields[lastPart.getY()][lastPart.getX()] = lastPart;
        fields[lastPart.getPreviousY()][lastPart.getPreviousX()] = null;


    }

    private void setHeadPositions(int nextPositionX, int nextPositionY, int currentPX, int currentPositionY) {
        snake.getCell(0).setPreviousX(currentPX);
        snake.getCell(0).setPreviousY(currentPositionY);
        snake.getCell(0).setX(nextPositionX);
        snake.getCell(0).setY(nextPositionY);
    }

    private void showModal() {
    }


    private Point validatePositions(Point nextPositions) {
        int nextPositionX = (int) nextPositions.getX();
        int nextPositionY = (int) nextPositions.getY();


        if (nextPositionX == 8) {
            nextPositionX = 0;
        } else if (nextPositionX == -1) {
            nextPositionX = 7;
        }

        if (nextPositionY == 8) {
            nextPositionY = 0;
        } else if (nextPositionY == -1) {
            nextPositionY = 7;
        }


        return new Point(nextPositionX, nextPositionY);
    }


    private Point findNextPosition(int headX, int headY) {
        int nextHeadPositionX;
        int nextHeadPositionY;


        switch (direction) {
            case "left":
                nextHeadPositionX = headX - 1;
                nextHeadPositionY = headY;

                break;
            case "right":
                nextHeadPositionX = headX + 1;
                nextHeadPositionY = headY;

                break;
            case "down":
                nextHeadPositionY = headY + 1;
                nextHeadPositionX = headX;

                break;
            case "up":
                nextHeadPositionY = headY - 1;
                nextHeadPositionX = headX;

                break;
            default:
                return null;
        }

        return new Point(nextHeadPositionX, nextHeadPositionY);
    }
}
