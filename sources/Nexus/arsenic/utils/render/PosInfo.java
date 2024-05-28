package arsenic.utils.render;

public class PosInfo {
    float x;
    float y;

    public PosInfo(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public void moveY(float m) {
        y += m;
    }

    public void moveX(float m) {
        x += m;
    }

    public float getY() { return y; }

    public float getX() { return x; }

    public void setX(float x) { this.x = x; }

    public void setY(float y) { this.y = y; }
}
