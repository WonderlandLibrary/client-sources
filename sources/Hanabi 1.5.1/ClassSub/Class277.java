package ClassSub;

import java.io.*;

private static class Class277 implements Serializable
{
    protected Class224 pt;
    protected Class277 prev;
    protected Class277 next;
    protected double nx;
    protected double ny;
    protected double angle;
    protected double dist;
    
    
    public Class277(final Class224 pt) {
        this.pt = pt;
    }
    
    public void unlink() {
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
    }
    
    public void insertBefore(final Class277 class277) {
        this.prev.next = class277;
        class277.prev = this.prev;
        class277.next = this;
        this.prev = class277;
    }
    
    public void insertAfter(final Class277 class277) {
        this.next.prev = class277;
        class277.prev = this;
        class277.next = this.next;
        this.next = class277;
    }
    
    private double hypot(final double n, final double n2) {
        return Math.sqrt(n * n + n2 * n2);
    }
    
    public void computeAngle() {
        if (this.prev.pt.equals(this.pt)) {
            final Class224 pt = this.pt;
            pt.x += 0.01f;
        }
        final double n = this.pt.x - this.prev.pt.x;
        final double n2 = this.pt.y - this.prev.pt.y;
        final double hypot = this.hypot(n, n2);
        final double nx = n / hypot;
        final double n3 = n2 / hypot;
        if (this.next.pt.equals(this.pt)) {
            final Class224 pt2 = this.pt;
            pt2.y += 0.01f;
        }
        final double n4 = this.next.pt.x - this.pt.x;
        final double n5 = this.next.pt.y - this.pt.y;
        final double hypot2 = this.hypot(n4, n5);
        final double n6 = n4 / hypot2;
        final double ny = n5 / hypot2;
        final double n7 = -n3;
        final double n8 = nx;
        this.nx = (n7 - ny) * 0.5;
        this.ny = (n8 + n6) * 0.5;
        if (this.nx * this.nx + this.ny * this.ny < 1.0E-5) {
            this.nx = nx;
            this.ny = ny;
            this.angle = 1.0;
            if (nx * n6 + n3 * ny > 0.0) {
                this.nx = -nx;
                this.ny = -n3;
            }
        }
        else {
            this.angle = this.nx * n6 + this.ny * ny;
        }
    }
    
    public double getAngle(final Class277 class277) {
        final double n = class277.pt.x - this.pt.x;
        final double n2 = class277.pt.y - this.pt.y;
        return (this.nx * n + this.ny * n2) / this.hypot(n, n2);
    }
    
    public boolean isConcave() {
        return this.angle < 0.0;
    }
    
    public boolean isInfront(final double n, final double n2) {
        final boolean b = (this.prev.pt.y - this.pt.y) * n + (this.pt.x - this.prev.pt.x) * n2 >= 0.0;
        final boolean b2 = (this.pt.y - this.next.pt.y) * n + (this.next.pt.x - this.pt.x) * n2 >= 0.0;
        return (this.angle < 0.0) ? (b | b2) : (b & b2);
    }
    
    public boolean isInfront(final Class277 class277) {
        return this.isInfront(class277.pt.x - this.pt.x, class277.pt.y - this.pt.y);
    }
}
