package club.bluezenith.util.anim;

import club.bluezenith.util.client.MillisTimer;

public abstract class Animation {
    public MillisTimer timer = new MillisTimer();

    protected int duration;

    protected double endPoint;

    protected Enum<Direction> direction;

    public Animation(int ms, double endPoint) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }

    public enum Direction {
        FORWARDS(new int[] { 0, 0 }),
        BACKWARDS(new int[] { 0, 0 }),
        UP(new int[] { 0, -1 }),
        DOWN(new int[] { 0, 1 }),
        LEFT(new int[] { -1, 0 }),
        RIGHT(new int[] { 1, 0 });

        private final int[] xy;

        Direction(int[] xy) {
            this.xy = xy;
        }

        public int[] getXy() {
            return this.xy;
        }
    }

    public Animation(int ms, double endPoint, Enum<Direction> direction) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public double getTimerOutput() {
        return this.timer.millis / this.duration;
    }

    public double getEndPoint() {
        return this.endPoint;
    }

    public void reset() {
        this.timer.reset();
    }

    public boolean isDone() {
        return this.timer.hasTimeReached(this.duration);
    }

    public void changeDirection() {
        if (this.direction == Direction.FORWARDS) {
            this.direction = Direction.BACKWARDS;
        } else {
            this.direction = Direction.FORWARDS;
        }
        this.timer.millis = System.currentTimeMillis() - this.duration - Math.min(this.duration, this.timer.millis);
    }

    public Enum<Direction> getDirection() {
        return this.direction;
    }

    public void setDirection(Enum<Direction> direction) {
        if (this.direction != direction) {
            this.timer.millis = System.currentTimeMillis() - this.duration - Math.min(this.duration, this.timer.millis);
            this.direction = direction;
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public double getOutput() {
        if (this.direction == Direction.FORWARDS) {
            if (isDone())
                return this.endPoint;
            return getEquation(this.timer.millis) * this.endPoint;
        }
        if (isDone())
            return 0.0D;
        return (1.0D - getEquation(this.timer.millis)) * this.endPoint;
    }

    protected abstract double getEquation(double paramDouble);
}
