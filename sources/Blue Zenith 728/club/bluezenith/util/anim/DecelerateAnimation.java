package club.bluezenith.util.anim;

public class DecelerateAnimation extends Animation {
    public DecelerateAnimation(int ms, double endPoint) {
        super(ms, endPoint);
    }

    public DecelerateAnimation(int ms, double endPoint, Animation.Direction direction) {
        super(ms, endPoint, direction);
    }

    protected double getEquation(double x) {
        double x1 = x / this.duration;
        return 1.0D - (x1 - 1.0D) * (x1 - 1.0D);
    }
}