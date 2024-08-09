package dev.darkmoon.client.utility.render.animation;

import dev.darkmoon.client.utility.misc.TimerHelper;

public abstract class Animation {
    public TimerHelper timerUtil = new TimerHelper();
    protected int duration;
    protected float endPoint;
    protected Direction direction;

    public Animation(int ms, float endPoint) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = Direction.FORWARDS;
    }

    public Animation(int ms, float endPoint, Direction direction) {
        this.duration = ms;
        this.endPoint = endPoint;
        this.direction = direction;
    }

    public boolean finished(Direction direction) {
        return isDone() && this.direction.equals(direction);
    }

    public float getLinearOutput() {
        return 1 - ((timerUtil.getTimePassed() / (float) duration) * endPoint);
    }

    public float getEndPoint() {
        return endPoint;
    }

    public void setEndPoint(float endPoint) {
        this.endPoint = endPoint;
    }

    public void reset() {
        timerUtil.reset();
    }

    public boolean isDone() {
        return timerUtil.hasReached(duration);
    }

    public void changeDirection() {
        setDirection(direction.opposite());
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        if (this.direction != direction) {
            this.direction = direction;
            timerUtil.setTime(System.currentTimeMillis() - (duration - Math.min(duration, timerUtil.getTimePassed())));
        }
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    protected boolean correctOutput() {
        return false;
    }

    public long getTimePassed() {
        return timerUtil.getTimePassed();
    }
    public Double getOutput2() {
        if (direction == Direction.FORWARDS) {
            if (isDone()) {
                return (double) endPoint;
            }

            return (double) (getEquation((float) (timerUtil.getTimePassed() / (double) duration)) * endPoint);
        } else {
            if (isDone()) {
                return 0.0;
            }

            if (correctOutput()) {
                double revTime = Math.min(duration, Math.max(0, duration - timerUtil.getTimePassed()));
                return (double) (getEquation((float) (revTime / (double) duration)) * endPoint);
            }

            return (double) ((1 - getEquation((float) (timerUtil.getTimePassed() / (double) duration))) * endPoint);
        }
    }
    public float getOutput() {
        if (direction == Direction.FORWARDS) {
            if (isDone())
                return endPoint;
            return (getEquation(timerUtil.getTimePassed()) * endPoint);
        } else {
            if (isDone()) return 0;
            if (correctOutput()) {
                float revTime = Math.min(duration, Math.max(0, duration - timerUtil.getTimePassed()));
                return getEquation(revTime) * endPoint;
            } else return (1 - getEquation(timerUtil.getTimePassed())) * endPoint;
        }
    }

    protected abstract float getEquation(float x);
}

