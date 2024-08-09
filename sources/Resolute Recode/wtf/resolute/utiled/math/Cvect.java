package wtf.resolute.utiled.math;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cvect {
    public float x;
    public float y;

    public Cvect(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Cvect() {
        this.x = 0;
        this.y = 0;
    }

    public Cvect(float q) {
        this.x = q;
        this.y = q;
    }

    /** get x and y from VEC4*/
    public Cvect(Cvect4 q) {
        this.x = q.x;
        this.y = q.y;
    }
    public Float x() {
        return x;
    }

    public Float y() {
        return y;
    }
    public Float z() {
        return y;
    }


    public Float w() {
        return x;
    }

    public Float h() {
        return y;
    }

    public Cvect mul(float x,float y) {
        return new Cvect(this.x * x, this.y * y);
    }

    public Cvect add(float x,float y) {
        return new Cvect(this.x + x, this.y + y);
    }
    public Cvect add(float f) {
        return new Cvect(this.x + f, this.y + f);
    }
    public Cvect add(Cvect f) {
        return new Cvect(this.x + f.x, this.y + f.y);
    }

    public Cvect divide(float x, float y) {
        return new Cvect(this.x / cm(x), this.y / cm(y));
    }

    public Cvect divide(float factor) {
        return new Cvect(this.x / cm(factor), this.y / cm(factor));
    }

    public float cm(float value) {
        return Math.max(0.001f, value);
    }
}
