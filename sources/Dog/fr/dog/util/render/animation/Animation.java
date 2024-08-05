package fr.dog.util.render.animation;

import fr.dog.util.math.TimeUtil;
import lombok.Getter;
import lombok.Setter;


/**
 * This code is part of Liticane's Animation Library.
 *
 * @author Liticane
 * @since 22/03/2024
 */
@Getter
@Setter
public class Animation {

    private final TimeUtil stopwatch = new TimeUtil();
    private Easing easing;

    private long duration;
    private double startPoint, endPoint, value;

    private boolean finished;

    public Animation(final Easing easing, final long duration) {
        this.easing = easing;
        this.duration = duration;
    }

    public void run(final double endPoint) {
        if (this.endPoint != endPoint) {
            this.endPoint = endPoint;
            this.reset();
        } else {
            this.finished = stopwatch.finished(duration);

            if (this.finished) {
                this.value = endPoint;
                return;
            }
        }

        final double newValue = this.easing.getFunction().apply(this.getProgress());

        if (this.value > endPoint) {
            this.value = this.startPoint - (this.startPoint - endPoint) * newValue;
        } else {
            this.value = this.startPoint + (endPoint - this.startPoint) * newValue;
        }
    }

    public double getProgress() {
        return (double) (System.currentTimeMillis() - this.stopwatch.getLastMS()) / (double) this.duration;
    }

    public void reset() {
        this.stopwatch.reset();
        this.startPoint = value;
        this.finished = false;
    }

    public void restart() {
        this.stopwatch.reset();
        this.startPoint = 0.0F;
        this.finished = false;
    }
}