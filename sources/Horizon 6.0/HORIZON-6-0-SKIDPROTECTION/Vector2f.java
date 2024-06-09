package HORIZON-6-0-SKIDPROTECTION;

import java.io.Serializable;

public strictfp class Vector2f implements Serializable
{
    private static final long Ý = 1339934L;
    public float HorizonCode_Horizon_È;
    public float Â;
    
    public Vector2f() {
    }
    
    public Vector2f(final float[] coords) {
        this.HorizonCode_Horizon_È = coords[0];
        this.Â = coords[1];
    }
    
    public Vector2f(final double theta) {
        this.HorizonCode_Horizon_È = 1.0f;
        this.Â = 0.0f;
        this.HorizonCode_Horizon_È(theta);
    }
    
    public strictfp void HorizonCode_Horizon_È(double theta) {
        if (theta < -360.0 || theta > 360.0) {
            theta %= 360.0;
        }
        if (theta < 0.0) {
            theta += 360.0;
        }
        double oldTheta = this.HorizonCode_Horizon_È();
        if (theta < -360.0 || theta > 360.0) {
            oldTheta %= 360.0;
        }
        if (theta < 0.0) {
            oldTheta += 360.0;
        }
        final float len = this.áˆºÑ¢Õ();
        this.HorizonCode_Horizon_È = len * (float)FastTrig.Â(StrictMath.toRadians(theta));
        this.Â = len * (float)FastTrig.HorizonCode_Horizon_È(StrictMath.toRadians(theta));
    }
    
    public strictfp Vector2f Â(final double theta) {
        this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È() + theta);
        return this;
    }
    
    public strictfp Vector2f Ý(final double theta) {
        this.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È() - theta);
        return this;
    }
    
    public strictfp double HorizonCode_Horizon_È() {
        double theta = StrictMath.toDegrees(StrictMath.atan2(this.Â, this.HorizonCode_Horizon_È));
        if (theta < -360.0 || theta > 360.0) {
            theta %= 360.0;
        }
        if (theta < 0.0) {
            theta += 360.0;
        }
        return theta;
    }
    
    public strictfp float Â() {
        return this.HorizonCode_Horizon_È;
    }
    
    public strictfp float Ý() {
        return this.Â;
    }
    
    public Vector2f(final Vector2f other) {
        this(other.Â(), other.Ý());
    }
    
    public Vector2f(final float x, final float y) {
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
    }
    
    public strictfp void HorizonCode_Horizon_È(final Vector2f other) {
        this.HorizonCode_Horizon_È(other.Â(), other.Ý());
    }
    
    public strictfp float Â(final Vector2f other) {
        return this.HorizonCode_Horizon_È * other.Â() + this.Â * other.Ý();
    }
    
    public strictfp Vector2f HorizonCode_Horizon_È(final float x, final float y) {
        this.HorizonCode_Horizon_È = x;
        this.Â = y;
        return this;
    }
    
    public strictfp Vector2f Ø­áŒŠá() {
        return new Vector2f(-this.Â, this.HorizonCode_Horizon_È);
    }
    
    public strictfp Vector2f HorizonCode_Horizon_È(final float[] pt) {
        return this.HorizonCode_Horizon_È(pt[0], pt[1]);
    }
    
    public strictfp Vector2f Âµá€() {
        return new Vector2f(-this.HorizonCode_Horizon_È, -this.Â);
    }
    
    public strictfp Vector2f Ó() {
        this.HorizonCode_Horizon_È = -this.HorizonCode_Horizon_È;
        this.Â = -this.Â;
        return this;
    }
    
    public strictfp Vector2f Ý(final Vector2f v) {
        this.HorizonCode_Horizon_È += v.Â();
        this.Â += v.Ý();
        return this;
    }
    
    public strictfp Vector2f Ø­áŒŠá(final Vector2f v) {
        this.HorizonCode_Horizon_È -= v.Â();
        this.Â -= v.Ý();
        return this;
    }
    
    public strictfp Vector2f HorizonCode_Horizon_È(final float a) {
        this.HorizonCode_Horizon_È *= a;
        this.Â *= a;
        return this;
    }
    
    public strictfp Vector2f à() {
        final float l = this.áˆºÑ¢Õ();
        if (l == 0.0f) {
            return this;
        }
        this.HorizonCode_Horizon_È /= l;
        this.Â /= l;
        return this;
    }
    
    public strictfp Vector2f Ø() {
        final Vector2f cp = this.ÂµÈ();
        cp.à();
        return cp;
    }
    
    public strictfp float áŒŠÆ() {
        return this.HorizonCode_Horizon_È * this.HorizonCode_Horizon_È + this.Â * this.Â;
    }
    
    public strictfp float áˆºÑ¢Õ() {
        return (float)Math.sqrt(this.áŒŠÆ());
    }
    
    public strictfp void HorizonCode_Horizon_È(final Vector2f b, final Vector2f result) {
        final float dp = b.Â(this);
        result.HorizonCode_Horizon_È = dp * b.Â();
        result.Â = dp * b.Ý();
    }
    
    public strictfp Vector2f ÂµÈ() {
        return new Vector2f(this.HorizonCode_Horizon_È, this.Â);
    }
    
    @Override
    public strictfp String toString() {
        return "[Vector2f " + this.HorizonCode_Horizon_È + "," + this.Â + " (" + this.áˆºÑ¢Õ() + ")]";
    }
    
    public strictfp float Âµá€(final Vector2f other) {
        return (float)Math.sqrt(this.Ó(other));
    }
    
    public strictfp float Ó(final Vector2f other) {
        final float dx = other.Â() - this.Â();
        final float dy = other.Ý() - this.Ý();
        return dx * dx + dy * dy;
    }
    
    @Override
    public strictfp int hashCode() {
        return 997 * (int)this.HorizonCode_Horizon_È ^ 991 * (int)this.Â;
    }
    
    @Override
    public strictfp boolean equals(final Object other) {
        if (other instanceof Vector2f) {
            final Vector2f o = (Vector2f)other;
            return o.HorizonCode_Horizon_È == this.HorizonCode_Horizon_È && o.Â == this.Â;
        }
        return false;
    }
}
