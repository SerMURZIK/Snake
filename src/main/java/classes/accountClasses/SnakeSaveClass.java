package classes.accountClasses;

import classes.snakeClasses.*;

import java.util.List;


public class SnakeSaveClass {
    public String password;
    public int xApple, yApple;
    public short score, speed;
    public Direction direction;
    public List<Integer> x, y;

    public SnakeSaveClass(String password,
                          int xApple, int yApple,
                          short score, short speed,
                          Direction direction,
                          List<Integer> x, List<Integer> y) {
        this.xApple = xApple;
        this.yApple = yApple;
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

    public int get_xApple() {
        return xApple;
    }

    public int get_yApple() {
        return yApple;
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
