package game;

import game.fields.Field;
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

        timer = new Timer(2000, this);
        this.init();

    }


    /**
     * method init
     */
    public void init() {
        this.setSize(800, 800);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        fields = new Field[8][8];
        snake = new Snake();
        points = 0;
        direction = "none";
        timer.stop();
        timer.start();
        fieldInitialization();
    }

    /**
     * field init
     */
    private void fieldInitialization() {
        Random random = new Random();
        generateSnake(random);
        generateObstacles(random);
        generateFood(random);

    }

    /**
     * snake generation
     * @param random instance of random
     */
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

    /**
     * obstacles generation
     * @param random instance of random
     */
    private void generateObstacles(Random random) {
        int count = random.nextInt(11) + 5;

        for (int i = 0; i < count; i++) {
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            Field field = fields[row][col];

            if (field == null) {
                fields[row][col] = new Field(col, row, col, row, Color.BLACK);

                continue;
            }
            i--;
        }

    }



    /**
     * food generation
     * @param random instance of random
     */
    private void generateFood(Random random) {
        int count = random.nextInt(10) + 20;

        for (int i = 0; i < count; i++) {
            int row = random.nextInt(8);
            int col = random.nextInt(8);

            Field cell = fields[row][col];

            if (cell == null) {
                fields[row][col] = new Field(col, row, col, row, Color.ORANGE);
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

        if (nextPositions != null) {
            nextPositions = validatePositions(nextPositions);
            int nextPositionX = nextPositions.x;
            int nextPositionY = nextPositions.y;


            this.move(nextPositionX, nextPositionY, headX, headY);
        }
        repaint();

    }

    /**
     * move method
     * @param nextPositionX nextPosition of X
     * @param nextPositionY nextPosition of Y
     * @param currentPX current position of Px
     * @param currentPY current position py
     */
    private void move(int nextPositionX, int nextPositionY, int currentPX, int currentPY) {
        Field targetCell = fields[nextPositionY][nextPositionX];

        Color targetColor;
        try {
            targetColor = targetCell.getColor();
        } catch (NullPointerException ex) {

            targetColor = null;
        }

        if (Color.YELLOW.equals(targetColor) || Color.BLACK.equals(targetColor)) {
            showModal("you lost");
            return;
        } else if (Color.ORANGE.equals(targetColor)) {

            points += 15;

            if (points >= 300) {
                showModal("you win");
                return;

            }
            Field lastPart = this.snake.getCell(snake.getSize() - 1);
            Field body = new Field(lastPart.getPreviousX(), lastPart.getPreviousY(), lastPart.getPreviousX(), lastPart.getPreviousY(), Color.YELLOW);
            snake.addToBody(body);
        }
        setHeadPositions(nextPositionX, nextPositionY, currentPX, currentPY);

        moveParts();

        moveLastPart();


    }

    /**
     * move last part of the snake
     */
    private void moveLastPart() {
        Field lastPart = snake.getCell(snake.getSize() - 1);
        fields[lastPart.getY()][lastPart.getX()] = lastPart;
        fields[lastPart.getPreviousY()][lastPart.getPreviousX()] = null;
    }

    /**
     * move parts of the snake
     */
    private void moveParts() {
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
    }

    /**
     * set the head position
     * @param nextPositionX   next position x
     * @param nextPositionY next position y
     * @param currentPX current position x
     * @param currentPositionY next position y
     */
    private void setHeadPositions(int nextPositionX, int nextPositionY, int currentPX, int currentPositionY) {
        snake.getCell(0).setPreviousX(currentPX);
        snake.getCell(0).setPreviousY(currentPositionY);
        snake.getCell(0).setX(nextPositionX);
        snake.getCell(0).setY(nextPositionY);
    }


    /**
     * show dialog
     * @param message message of the dialog
     */
    private void showModal(String message) {

        JDialog jDialog = new JDialog(this, true);


        jDialog.setLayout(new FlowLayout());

        jDialog.add(new JLabel("The game finished: " + message + "-" + points + "points"));

        setButtons(jDialog);

        jDialog.setSize(300, 200);
        jDialog.setVisible(true);

    }

    /**
     * set buttons of the dialog
     * @param jDialog dialog instance
     */
    private void setButtons(JDialog jDialog) {
        JButton exitBtn = new JButton("Exit the game");
        exitBtn.addActionListener(e -> System.exit(0));
        JButton restartBtn = new JButton("Restart the game");
        restartBtn.addActionListener(e -> this.init());

        jDialog.add(exitBtn);
        jDialog.add(restartBtn);
    }


    /**
     * validate positions
     * @param nextPositions next positions
     * @return point with validated position
     */
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


    /**
     * find the next posiiton
     * @param headX
     * @param headY
     * @return point of next position
     */
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
