package ez.h.features.another;

import java.util.*;
import ez.h.event.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import ez.h.event.events.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class XRayBypass extends Feature
{
    OptionBoolean emerald;
    public LinkedList<et> blocks;
    public LinkedList<et> renderBlocks;
    OptionSlider rad;
    public int distance;
    OptionSlider delay;
    OptionSlider expandUp;
    int lastX;
    boolean stopRender;
    int blocksSize;
    et posToColor;
    OptionBoolean iron;
    OptionBoolean gold;
    OptionBoolean diamond;
    OptionSlider expandDown;
    int progress;
    
    @EventTarget
    public void renderWorld(final EventRender3D eventRender3D) {
        if (XRayBypass.mc.h == null) {
            this.blocks = null;
            this.progress = 0;
            return;
        }
        this.distance = (int)this.rad.getNum();
        XRayBypass.mc.t.aE = 101.0f;
        this.colorClickBlock();
        if (this.blocks == null) {
            this.posToColor = null;
            return;
        }
        if (this.renderBlocks.isEmpty() || this.stopRender) {
            return;
        }
        try {
            for (final et et : this.renderBlocks) {
                final aow u = XRayBypass.mc.f.o(et).u();
                if (this.diamond.isEnabled() && u == aox.ag) {
                    this.drawOre(aox.ag, 0.0f, 0.9f, 1.0f, et, u);
                }
                if (this.gold.isEnabled() && u == aox.o) {
                    this.drawOre(aox.o, 1.0f, 1.0f, 0.0f, et, u);
                }
                if (this.iron.isEnabled() && u == aox.p) {
                    this.drawOre(aox.p, 0.6f, 0.6f, 0.6f, et, u);
                }
                if (this.emerald.isEnabled() && u == aox.bP) {
                    this.drawOre(aox.bP, 0.0f, 1.0f, 0.2f, et, u);
                }
            }
        }
        catch (Exception ex) {}
    }
    
    public LinkedList<et> getBlock(final int n) {
        final LinkedList<et> list = new LinkedList<et>();
        final double n2 = XRayBypass.mc.h.p - 2.0;
        final double n3 = XRayBypass.mc.h.q - 2.0;
        final double n4 = XRayBypass.mc.h.r - 2.0;
        for (int i = (int)n2 - n; i <= (int)n2 + n; ++i) {
            for (int j = (int)n4 - n; j <= (int)n4 + n; ++j) {
                for (int c = XRayBypass.mc.f.c(i, j), k = 0; k <= c; ++k) {
                    final int n5 = (int)this.expandDown.getNum();
                    final int n6 = (int)this.expandUp.getNum();
                    final int n7 = (int)n3;
                    if (k <= n6 + n7) {
                        if (k >= n7 - n5) {
                            final et et = new et(i, k, j);
                            final aow u = XRayBypass.mc.f.o(et).u();
                            if (XRayBypass.mc.f.o(et).u() instanceof asp) {
                                if (u == aox.ag && this.diamond.isEnabled()) {
                                    list.add(et);
                                }
                                if (u == aox.bP && this.emerald.isEnabled()) {
                                    list.add(et);
                                }
                                if (u == aox.o && this.gold.isEnabled()) {
                                    list.add(et);
                                }
                                if (u == aox.p && this.iron.isEnabled()) {
                                    list.add(et);
                                }
                            }
                        }
                    }
                }
            }
        }
        return list;
    }
    
    public void drawOre(final aow aow, final float n, final float n2, final float n3, final et et, final aow aow2) {
        if (aow2 == aow) {
            final double n4 = et.p() - XRayBypass.mc.ac().h;
            final double n5 = et.q() - XRayBypass.mc.ac().i;
            final double n6 = et.r() - XRayBypass.mc.ac().j;
            bus.G();
            bus.z();
            bus.j();
            bus.r();
            bus.m();
            bus.a(false);
            bus.d(1.0f);
            bus.b(n4, n5, n6);
            GL11.glColor4f(n, n2, n3, 0.15f);
            RenderUtils.Boxes.drawSolidBox(aow.j);
            GL11.glColor4f(n, n2, n3, 0.65f);
            RenderUtils.Boxes.drawOutlinedBox(aow.j);
            bus.q();
            bus.l();
            bus.a(true);
            bus.y();
            bus.k();
            bus.H();
            bus.I();
        }
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        if (XRayBypass.mc.h == null || XRayBypass.mc.f == null) {
            super.onEnable();
            this.setEnabled(false);
            return;
        }
        this.progress = 0;
        this.renderBlocks = new LinkedList<et>();
        this.stopRender = false;
        this.lastX = (int)(XRayBypass.mc.h.q - 2.0);
        final Iterator<et> iterator;
        et posToColor;
        final Iterator<et> iterator2;
        et et;
        new Thread(() -> {
            this.blocks = this.getBlock(this.distance);
            this.blocksSize = this.blocks.size();
            this.blocks.iterator();
            while (iterator.hasNext()) {
                posToColor = iterator.next();
                if (!this.isEnabled()) {
                    return;
                }
                else {
                    this.posToColor = posToColor;
                    try {
                        XRayBypass.mc.h.d.a((ht)new lp(lp.a.a, posToColor, (new fa[] { fa.c, fa.f, fa.d, fa.e })[Math.round(XRayBypass.mc.h.v / 90.0f) & 0x3].d()));
                        Thread.sleep((int)this.delay.getNum());
                        ++this.progress;
                        this.sheduleUse(96 + 186 - 243 + 361, posToColor);
                    }
                    catch (Exception ex) {}
                }
            }
            if (this.progress == this.blocks.size()) {
                this.blocksSize = this.renderBlocks.size();
                try {
                    this.progress = 0;
                    Thread.sleep(800L);
                    this.progress = 0;
                    this.renderBlocks.iterator();
                    while (iterator2.hasNext()) {
                        et = iterator2.next();
                        if (!(!this.isEnabled())) {
                            if (et == null) {
                                continue;
                            }
                            else {
                                XRayBypass.mc.h.d.a((ht)new lp(lp.a.a, et, (new fa[] { fa.c, fa.f, fa.d, fa.e })[Math.round(XRayBypass.mc.h.v / 90.0f) & 0x3].d()));
                                Thread.sleep((int)this.delay.getNum());
                                ++this.progress;
                            }
                        }
                    }
                }
                catch (Exception ex2) {}
            }
            return;
        }, "ByP776ass").start();
        super.onEnable();
    }
    
    public void colorClickBlock() {
        final et posToColor = this.posToColor;
        if (posToColor == null) {
            return;
        }
        final double n = posToColor.p() - XRayBypass.mc.ac().h;
        final double n2 = this.lastX - this.expandDown.getNum() - XRayBypass.mc.ac().i;
        final double n3 = posToColor.r() - XRayBypass.mc.ac().j;
        bus.G();
        bus.z();
        bus.j();
        bus.r();
        bus.m();
        bus.a(false);
        bus.d(1.0f);
        GL11.glTranslated(n, n2, n3);
        GL11.glColor4f(0.1f, 0.9f, 0.0f, 0.4f);
        RenderUtils.Boxes.drawSolidBox(new bhb(0.0, 0.0, 0.0, 1.0, (double)(this.expandDown.getNum() + this.expandUp.getNum()), 1.0));
        GL11.glColor4f(0.1f, 0.9f, 0.0f, 1.0f);
        RenderUtils.Boxes.drawOutlinedBox(new bhb(0.0, 0.0, 0.0, 1.0, (double)(this.expandDown.getNum() + this.expandUp.getNum()), 1.0));
        bus.q();
        bus.l();
        bus.a(true);
        bus.y();
        bus.k();
        bus.H();
        bus.I();
    }
    
    private void sheduleUse(final int n, final et et) {
        if (et == null) {
            return;
        }
        new Thread(() -> {
            try {
                Thread.sleep(n);
                this.stopRender = true;
                if (XRayBypass.mc.f.o(et).u() instanceof asp) {
                    this.renderBlocks.add(et);
                }
                this.stopRender = false;
            }
            catch (Exception ex) {}
        }, "12354").start();
    }
    
    @EventTarget
    public void renderHud(final EventRender2D eventRender2D) {
        if (XRayBypass.mc.h == null) {
            this.blocks = null;
            this.progress = 0;
            return;
        }
        if (this.blocks == null) {
            return;
        }
        final int blocksSize = this.blocksSize;
        final bip k = XRayBypass.mc.k;
        final bit bit = new bit(XRayBypass.mc);
        final String string = "§7" + this.progress + " / §a" + blocksSize;
        k.a(string, bit.a() / 2.0f - k.a(string) + k.a(string) / 2.0f, 5.0f, -1, true);
        if (blocksSize == this.progress) {
            this.posToColor = null;
        }
    }
    
    public XRayBypass() {
        super("XRayBypass", "\u0423\u0431\u0438\u0440\u0430\u0435\u0442 \u0444\u0430\u043d\u0442\u043e\u043c\u043d\u044b\u0435 \u0440\u0443\u0434\u044b.", Category.ANOTHER);
        this.distance = (0xAF ^ 0xBB);
        this.rad = new OptionSlider(this, "Radius", 20.0f, 5.0f, 80.0f, OptionSlider.SliderType.NULLINT);
        this.expandUp = new OptionSlider(this, "Up", 20.0f, 5.0f, 40.0f, OptionSlider.SliderType.NULLINT);
        this.expandDown = new OptionSlider(this, "Down", 20.0f, 5.0f, 40.0f, OptionSlider.SliderType.NULLINT);
        this.delay = new OptionSlider(this, "Delay", 9.0f, 1.0f, 25.0f, OptionSlider.SliderType.MS);
        this.diamond = new OptionBoolean(this, "Diamond", true);
        this.emerald = new OptionBoolean(this, "Emerald", true);
        this.gold = new OptionBoolean(this, "Gold", true);
        this.iron = new OptionBoolean(this, "Iron", true);
        this.addOptions(this.rad, this.expandUp, this.expandDown, this.delay, this.diamond, this.emerald, this.gold, this.iron);
    }
}
