package classes.snakeClasses.snakeEntityClasses;

public abstract class SnakeInfo {
    private short score = 0;
    public static final short startSpeed = 100;
    private short speed = startSpeed;

    public short getScore() {
        return score;
    }

    public void setScore(short score) {
        this.score = score;
    }

    public short getSpeed() {
        return speed;
    }

    public void setSpeed(short speed) {
        this.speed = speed;
    }
}
