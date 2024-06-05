package ClassSub;

public class Class53
{
    private float opacity;
    private long lastMS;
    
    
    public Class53(final int n) {
        this.opacity = n;
        this.lastMS = System.currentTimeMillis();
    }
    
    public void interpolate(final float n) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long n2 = currentTimeMillis - this.lastMS;
        this.lastMS = currentTimeMillis;
        this.opacity = this.calculateCompensation(n, this.opacity, n2, 20);
    }
    
    public void interp(final float n, final int n2) {
        final long currentTimeMillis = System.currentTimeMillis();
        final long n3 = currentTimeMillis - this.lastMS;
        this.lastMS = currentTimeMillis;
        this.opacity = this.calculateCompensation(n, this.opacity, n3, n2);
    }
    
    public float getOpacity() {
        return (int)this.opacity;
    }
    
    public void setOpacity(final float opacity) {
        this.opacity = opacity;
    }
    
    public float calculateCompensation(final float n, float n2, long n3, final int n4) {
        final float n5 = n2 - n;
        if (n3 < 1L) {
            n3 = 1L;
        }
        if (n5 > n4) {
            n2 -= (float)((n4 * n3 / 16L < 0.25) ? 0.5 : (n4 * n3 / 16L));
            if (n2 < n) {
                n2 = n;
            }
        }
        else if (n5 < -n4) {
            n2 += (float)((n4 * n3 / 16L < 0.25) ? 0.5 : (n4 * n3 / 16L));
            if (n2 > n) {
                n2 = n;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
}
