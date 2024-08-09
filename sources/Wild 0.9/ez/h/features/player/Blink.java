package ez.h.features.player;

import ez.h.features.*;
import java.awt.*;
import ez.h.ui.clickgui.options.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import java.util.*;
import ez.h.event.*;
import ez.h.event.events.*;

public class Blink extends Feature
{
    bue oldPlayer;
    OptionColor color;
    boolean disabling;
    ArrayList<Trail> trails;
    ArrayList<ht<?>> blinkList;
    
    public Blink() {
        super("Blink", "\u0421\u0438\u043c\u0443\u043b\u0438\u0440\u0443\u0435\u0442 \u043b\u0430\u0433 \u0441\u0435\u0440\u0432\u0435\u0440\u0430.", Category.PLAYER);
        this.blinkList = new ArrayList<ht<?>>();
        this.trails = new ArrayList<Trail>();
        this.color = new OptionColor(this, "Trail Color", new Color(-1));
        this.addOptions(this.color);
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        if (this.disabling) {
            return;
        }
        if (this.blinkList.size() < 2) {
            return;
        }
        if (Blink.mc.h.s != 0.0 || Blink.mc.h.u != 0.0) {
            this.trails.add(new Trail((float)(Blink.mc.h.M + (Blink.mc.h.p - Blink.mc.h.M) * eventRender3D.getPartialTicks()), (float)(Blink.mc.h.N + (Blink.mc.h.q - Blink.mc.h.N) * eventRender3D.getPartialTicks()), (float)(Blink.mc.h.O + (Blink.mc.h.r - Blink.mc.h.O) * eventRender3D.getPartialTicks())));
        }
        bus.G();
        bus.m();
        GL11.glBlendFunc(87 + 339 + 261 + 83, 323 + 471 - 455 + 432);
        GL11.glEnable(659 + 2738 - 818 + 269);
        bus.j();
        bus.z();
        bus.d();
        bus.g();
        GL11.glLineWidth(3.0f);
        GL11.glShadeModel(7423 + 3009 - 7536 + 4529);
        GL11.glDisable(2877 + 2624 - 3065 + 448);
        GL11.glEnable(1504 + 188 + 281 + 956);
        for (final Trail trail : this.trails) {
            if (this.trails.indexOf(trail) >= this.trails.size() - 1) {
                continue;
            }
            final Trail trail2 = this.trails.get(this.trails.indexOf(trail) + 1);
            GL11.glBegin(2);
            final double n = trail.posX - Blink.mc.ac().o;
            final double n2 = trail.posY - Blink.mc.ac().p;
            final double n3 = trail.posZ - Blink.mc.ac().q;
            final double n4 = trail2.posX - Blink.mc.ac().o;
            final double n5 = trail2.posY - Blink.mc.ac().p;
            final double n6 = trail2.posZ - Blink.mc.ac().q;
            RenderUtils.color(this.color.getColor().getRGB());
            GL11.glVertex3d(n, n2, n3);
            RenderUtils.color(this.color.getColor().getRGB());
            GL11.glVertex3d(n4, n5, n6);
            GL11.glEnd();
        }
        bus.e();
        GL11.glShadeModel(6334 + 5328 - 10238 + 6000);
        GL11.glDisable(2169 + 1763 - 3731 + 2647);
        GL11.glEnable(570 + 2155 - 2110 + 2269);
        bus.m();
        bus.y();
        bus.k();
        bus.l();
        bus.I();
        bus.H();
    }
    
    @EventTarget
    public void onPacketSent(final EventPacketSend eventPacketSend) {
        if (this.disabling) {
            return;
        }
        if (eventPacketSend.packet instanceof lk || eventPacketSend.packet instanceof li || eventPacketSend.packet instanceof lp) {
            this.blinkList.add(eventPacketSend.packet);
            eventPacketSend.setCancelled(true);
        }
    }
    
    @Override
    public void onDisable() {
        Blink.mc.f.e(-123);
        this.oldPlayer = null;
        this.disabling = true;
        this.trails.clear();
        final Iterator<ht<?>> iterator = this.blinkList.iterator();
        while (iterator.hasNext()) {
            Blink.mc.h.d.a((ht)iterator.next());
        }
        this.blinkList.clear();
        this.disabling = false;
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        (this.oldPlayer = new bue((amu)Blink.mc.f, Blink.mc.h.da())).b(Blink.mc.h.p, Blink.mc.h.q, Blink.mc.h.r);
        this.oldPlayer.aP = Blink.mc.h.aP;
        this.oldPlayer.u((vg)Blink.mc.h);
        this.oldPlayer.v = Blink.mc.h.v;
        Blink.mc.f.a(-123, (vg)this.oldPlayer);
        super.onEnable();
    }
    
    class Trail
    {
        public float posY;
        public float posZ;
        public float posX;
        
        public Trail(final float posX, final float posY, final float posZ) {
            this.posX = posX;
            this.posY = posY;
            this.posZ = posZ;
        }
    }
}
