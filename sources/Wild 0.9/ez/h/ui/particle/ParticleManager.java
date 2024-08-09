package ez.h.ui.particle;

import org.lwjgl.input.*;
import java.util.*;

public class ParticleManager
{
    private final ArrayList<Particle> particles;
    
    public void init() {
        this.particles.clear();
        for (int n = (int)(8000.0 / Math.sqrt(4852800.0) * Math.sqrt(bib.z().d * bib.z().d + bib.z().e * bib.z().e)), i = 0; i < n; ++i) {
            this.particles.add(new Particle());
        }
    }
    
    public void draw(final double n, final double n2, final boolean b) {
        float n3 = -1.0E-4f;
        if (Mouse.isButtonDown(0)) {
            n3 = 4.0f;
        }
        if (Mouse.isButtonDown(1)) {
            n3 = -4.0f;
        }
        final bit bit = new bit(bib.z());
        final int a = bit.a();
        final int b2 = bit.b();
        final int n4 = Mouse.getX() * a / bib.z().d;
        final int n5 = b2 - Mouse.getY() * b2 / bib.z().e - 1;
        bus.m();
        bus.z();
        bus.a(763 + 711 - 1253 + 549, 296 + 239 - 332 + 568, 1, 0);
        for (final Particle particle : this.particles) {
            if (b) {
                particle.update(bit, n3, n4, n5, n, n2);
            }
            particle.render(n);
        }
        bus.y();
        bus.l();
    }
    
    public ParticleManager() {
        this.particles = new ArrayList<Particle>();
        this.init();
    }
    
    public Particle getNearest(final Particle particle) {
        double n = 1000000.0;
        Particle particle2 = null;
        for (final Particle particle3 : this.particles) {
            if (particle == particle3) {
                continue;
            }
            final double n2 = particle.x - particle3.x;
            final double n3 = particle.y - particle3.y;
            final double sqrt = Math.sqrt(n2 * n2 + n3 * n3);
            if (n > sqrt) {
                n = sqrt;
                particle2 = particle3;
            }
            if (sqrt < 1.0) {
                return particle3;
            }
        }
        return particle2;
    }
}
