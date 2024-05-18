package baritone;

import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.input.*;
import baritone.api.*;
import baritone.api.pathing.goals.*;
import java.awt.*;
import java.util.*;
import baritone.api.utils.*;
import org.lwjgl.util.glu.*;

public final class cq extends blk
{
    private final FloatBuffer a;
    private final FloatBuffer b;
    private final IntBuffer a;
    private final FloatBuffer c;
    private et a;
    private et b;
    
    public cq() {
        super();
        this.a = BufferUtils.createFloatBuffer(16);
        this.b = BufferUtils.createFloatBuffer(16);
        this.a = BufferUtils.createIntBuffer(16);
        this.c = BufferUtils.createFloatBuffer(3);
    }
    
    public final boolean d() {
        return false;
    }
    
    public final void a(int x, int y, final float n) {
        x = Mouse.getX();
        y = Mouse.getY();
        final bhe a = this.a(x, y, 0.0);
        final bhe a2 = this.a(x, y, 1.0);
        if (a != null && a2 != null) {
            final bhe bhe = new bhe(this.j.ac().h, this.j.ac().i, this.j.ac().j);
            final bhc a3;
            if ((a3 = this.j.f.a(a.e(bhe), a2.e(bhe), false, false, true)) != null && a3.a == bhc$a.b) {
                this.b = a3.a();
            }
        }
    }
    
    protected final void b(final int n, final int n2, final int n3) {
        if (this.a != null && !this.a.equals((Object)this.b)) {
            ((a)BaritoneAPI.getProvider().getPrimaryBaritone()).a.a(this.a, this.b);
            this.a = null;
        }
        else {
            BaritoneAPI.getProvider().getPrimaryBaritone().getCustomGoalProcess().setGoalAndPath(new GoalTwoBlocks(this.b));
        }
        this.a = null;
    }
    
    protected final void a(final int n, final int n2, final int n3) {
        this.a = this.b;
    }
    
    public final void a() {
        bus.c(2982, (FloatBuffer)this.a.clear());
        bus.c(2983, (FloatBuffer)this.b.clear());
        bus.a(2978, (IntBuffer)this.a.clear());
        if (this.b != null) {
            cu.a(this.j.aa(), Collections.singletonList(this.b), Color.CYAN);
            if (this.a != null && !this.a.equals((Object)this.b)) {
                bus.m();
                bus.a(770, 771, 1, 0);
                bus.c(Color.RED.getColorComponents(null)[0], Color.RED.getColorComponents(null)[1], Color.RED.getColorComponents(null)[2], 0.4f);
                bus.d((float)baritone.a.a().pathRenderLineWidthPixels.value);
                bus.z();
                bus.a(false);
                bus.j();
                final BetterBlockPos betterBlockPos = new BetterBlockPos(this.b);
                final BetterBlockPos betterBlockPos2 = new BetterBlockPos(this.a);
                cu.a(new bhb((double)Math.min(betterBlockPos.a, betterBlockPos2.a), (double)Math.min(betterBlockPos.b, betterBlockPos2.b), (double)Math.min(betterBlockPos.c, betterBlockPos2.c), (double)(Math.max(betterBlockPos.a, betterBlockPos2.a) + 1), (double)(Math.max(betterBlockPos.b, betterBlockPos2.b) + 1), (double)(Math.max(betterBlockPos.c, betterBlockPos2.c) + 1)));
                bus.k();
                bus.a(true);
                bus.y();
                bus.l();
            }
        }
    }
    
    private bhe a(final double n, final double n2, final double n3) {
        if (GLU.gluUnProject((float)n, (float)n2, (float)n3, this.a, this.b, this.a, (FloatBuffer)this.c.clear())) {
            return new bhe((double)this.c.get(0), (double)this.c.get(1), (double)this.c.get(2));
        }
        return null;
    }
}
