package ez.h.ui.particle;

import ez.h.*;
import ez.h.utils.*;
import io.netty.util.internal.*;
import org.lwjgl.opengl.*;

public class Particle
{
    private double velocityY;
    private double gravity;
    private double mass;
    private double time;
    double y;
    private double velocityX;
    double x;
    
    public void render(final double n) {
        final float n2 = (float)((this.x + this.y) / (bib.z().d + bib.z().e)) / 3.0f + Main.millis / 10000.0f;
        RenderUtils.color(-1);
        final bve a = bve.a();
        final buk c = a.c();
        c.a(7, cdy.e);
        c.b(this.x, this.y + 1.0 - n, 0.0).d();
        c.b(this.x + 1.0, this.y + 1.0 - n, 0.0).d();
        c.b(this.x + 0.5, this.y - n, 0.0).d();
        c.b(this.x, this.y - n, 0.0).d();
        a.b();
    }
    
    public static double[] pointFromAngle(final double n, final double n2, final double n3, double n4) {
        n4 *= 3.141592653589793;
        n4 /= 180.0;
        return new double[] { Math.cos(n4) * n3 + n, Math.sin(n4) * n3 + n2 };
    }
    
    public static double angularPoints(final double n, final double n2, final double n3, final double n4) {
        double n5 = Math.atan2(n4 - n2, n3 - n) * 180.0 / 3.141592653589793;
        if (n5 < 0.0) {
            n5 += 360.0;
        }
        return n5;
    }
    
    public void update(final bit bit, final float n, final int n2, int n3, final double n4, final double n5) {
        n3 += (int)n4;
        this.time = 0.01;
        if (this.velocityX > 10.0) {
            this.velocityX *= 0.98;
        }
        if (this.velocityY > 10.0) {
            this.velocityY *= 0.98;
        }
        if (n != 0.0f) {
            final double n6 = n2 - this.x;
            final double n7 = n3 - this.y;
            final double n8 = Math.pow(n6, 2.0) + Math.pow(n7, 2.0);
            final double sqrt = Math.sqrt(n8);
            final double n9 = n6 / sqrt;
            final double n10 = n7 / sqrt;
            double n11 = n * this.gravity * this.mass * n9 / n8;
            double n12 = n * this.gravity * this.mass * n10 / n8;
            final double sqrt2 = Math.sqrt(n11 * n11 + n12 * n12);
            if (sqrt2 >= 10500.0) {
                final double angularPoints = angularPoints(0.0, 0.0, n11, n12);
                final double min = Math.min(10500.0, 10000.0 + sqrt2 / 10000.0);
                n11 = min * Math.cos(Math.toRadians(angularPoints));
                n12 = min * Math.sin(Math.toRadians(angularPoints));
            }
            this.velocityX += n11 * this.time;
            this.velocityY += n12 * this.time;
        }
        if (Math.abs(this.velocityX) < 0.04 && Math.abs(this.velocityY) < 0.04) {
            this.velocityX = ThreadLocalRandom.current().nextDouble(-100.0, 100.0);
            this.velocityY = ThreadLocalRandom.current().nextDouble(-100.0, 100.0);
        }
        final double x = this.x;
        final double y = this.y;
        this.x += this.velocityX * this.time;
        this.y += this.velocityY * this.time;
        int n13 = 0;
        if (this.x >= bit.a()) {
            this.x = x;
            n13 = -1;
        }
        if (this.x <= 0.0) {
            this.x = x;
            n13 = -1;
        }
        if (this.y >= bit.b() + n4) {
            this.y = y;
            n13 = 1;
        }
        if (this.y > bit.b() + n4 + this.velocityY * this.time + 1.0) {
            this.y = bit.b() + n4 - 2.0 - ThreadLocalRandom.current().nextDouble(0.0, 0.1);
            this.velocityY += n5 * (Math.abs(this.x - Display.getWidth() / 2.0) / (Display.getWidth() / 2.0f) * 7.0);
        }
        if (this.y <= 0.0) {
            this.y = y;
            n13 = 1;
        }
        if (n13 != 0) {
            switch (n13) {
                case -1: {
                    this.velocityX = -this.velocityX;
                    break;
                }
                case 1: {
                    this.velocityY = -this.velocityY;
                    break;
                }
            }
        }
    }
    
    public Particle() {
        this.gravity = 7500.0;
        this.mass = 10000.0;
        this.time = 0.1;
        this.x = ThreadLocalRandom.current().nextInt(0, bib.z().d);
        this.y = ThreadLocalRandom.current().nextInt(0, bib.z().e);
        this.velocityX = ThreadLocalRandom.current().nextDouble(-1.0, 1.0) * 100.0;
        this.velocityY = ThreadLocalRandom.current().nextDouble(-1.0, 1.0) * 100.0;
    }
}
