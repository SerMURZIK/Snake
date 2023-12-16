package classes.accountClasses;

import classes.snakeClasses.Direction;

import java.util.List;

public class SaveSnakeClass {
    public String password;
    public short score, speed;
    public Direction direction;
    public List<Integer> x, y;

    public SaveSnakeClass(String password,
                          short score, short speed,
                          Direction direction,
                          List<Integer> x, List<Integer> y) {
        this.score = score;
        this.speed = speed;
        this.direction = direction;
        this.x = x;
        this.y = y;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public short getScore() {
        return score;
    }

    public short getSpeed() {
        return speed;
    }

    public Direction getDirection() {
        return direction;
    }

    public List<Integer> getX() {
        return x;
    }

    public List<Integer> getY() {
        return y;
    }
}