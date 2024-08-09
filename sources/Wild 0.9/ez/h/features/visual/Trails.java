package ez.h.features.visual;

import ez.h.animengine.*;
import java.awt.*;
import ez.h.*;
import ez.h.utils.*;
import org.lwjgl.opengl.*;
import java.util.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;
import ez.h.event.events.*;
import ez.h.event.*;

public class Trails extends Feature
{
    OptionSlider fade;
    OptionSlider length;
    OptionBoolean onlySelf;
    OptionMode colorMode;
    HashMap<aed, ArrayList<Point>> pointsAll;
    OptionColor first;
    ArrayList<Point> points;
    OptionBoolean smoothending;
    OptionColor second;
    OptionBoolean smoothUp;
    OptionSlider alpha;
    
    void renderAll() {
        for (final aed aed : Trails.mc.f.i) {
            for (final Point point : aed.points) {
                if (aed.points.indexOf(point) >= aed.points.size() - 1) {
                    continue;
                }
                final Point point2 = aed.points.get(aed.points.indexOf(point) + 1);
                float num = this.alpha.getNum();
                if (this.smoothending.enabled) {
                    num = this.alpha.getNum() * Easings.easeInOutCubic(aed.points.indexOf(point) / (float)aed.points.size());
                }
                Color color = Color.WHITE;
                final String mode = this.colorMode.getMode();
                switch (mode) {
                    case "Gradient": {
                        color = Utils.getGradientOffset(this.first.getColor(), this.second.getColor(), aed.points.indexOf(point) / this.fade.getNum() + Main.millis / 1000.0f, (int)num);
                        break;
                    }
                    case "Single": {
                        color = RenderUtils.injectAlpha(this.first.getColor().getRGB(), (int)num);
                        break;
                    }
                    case "Astolfo": {
                        color = RenderUtils.injectAlpha(RenderUtils.astolfo(5000.0f, aed.points.indexOf(point) / aed.points.size() * (0x2D ^ 0x27)).getRGB(), (int)num);
                        break;
                    }
                    case "Rainbow": {
                        color = RenderUtils.injectAlpha(Color.getHSBColor(aed.points.indexOf(point) / this.fade.getNum() + Main.millis / 1000.0f, 1.0f, 1.0f).getRGB(), (int)num);
                        break;
                    }
                    case "Aquafresh": {
                        color = Utils.getGradientOffset(Utils.getGradientOffset(Color.BLUE, Color.RED, Main.millis / 2000.0f, 58 + 31 - 29 + 140), Color.WHITE.brighter(), aed.points.indexOf(point) / this.fade.getNum() + Main.millis / 1000.0f, (int)num);
                        break;
                    }
                }
                GL11.glBegin(8);
                final double n2 = point.x - Trails.mc.ac().o;
                final double n3 = point.y - Trails.mc.ac().p;
                final double n4 = point.z - Trails.mc.ac().q;
                final double n5 = point2.x - Trails.mc.ac().o;
                final double n6 = point2.y - Trails.mc.ac().p;
                final double n7 = point2.z - Trails.mc.ac().q;
                RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), this.smoothUp.enabled ? 0 : rk.a((int)(num / 1.75f), 0, 177 + 62 - 47 + 63)).getRGB());
                GL11.glVertex3d(n2, n3 + Trails.mc.h.H, n4);
                RenderUtils.color(color.getRGB());
                GL11.glVertex3d(n2, n3 + 0.09, n4);
                RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), this.smoothUp.enabled ? 0 : rk.a((int)(num / 1.75f), 0, 128 + 218 - 145 + 54)).getRGB());
                GL11.glVertex3d(n5, n6 + Trails.mc.h.H, n7);
                RenderUtils.color(color.getRGB());
                GL11.glVertex3d(n5, n6 + 0.09, n7);
                GL11.glEnd();
            }
        }
    }
    
    public Trails() {
        super("Trails", "\u0420\u0438\u0441\u0443\u0435\u0442 \u0442\u0440\u0435\u0439\u043b \u0437\u0430 \u0434\u0432\u0438\u0436\u0435\u043d\u0438\u0435\u043c \u0438\u0433\u0440\u043e\u043a\u0430.", Category.VISUAL);
        this.points = new ArrayList<Point>();
        this.pointsAll = new HashMap<aed, ArrayList<Point>>();
        this.onlySelf = new OptionBoolean(this, "Only Self", false);
        this.length = new OptionSlider(this, "Length", 50.0f, 1.0f, 500.0f, OptionSlider.SliderType.NULLINT);
        this.fade = new OptionSlider(this, "Fade", 100.0f, 1.0f, 100.0f, OptionSlider.SliderType.NULLINT);
        this.alpha = new OptionSlider(this, "Alpha", 255.0f, 0.0f, 255.0f, OptionSlider.SliderType.NULLINT);
        this.smoothending = new OptionBoolean(this, "Smooth Ending", true);
        this.smoothUp = new OptionBoolean(this, "Smooth Up", false);
        this.colorMode = new OptionMode(this, "Color Mode", "Gradient", new String[] { "Gradient", "Single", "Astolfo", "Rainbow", "Aquafresh" }, 0);
        this.first = new OptionColor(this, "First Color", new Color(0, 192 + 55 - 184 + 137, 38 + 196 - 169 + 135));
        this.second = new OptionColor(this, "Second Color", new Color(0, 0, 0));
        this.addOptions(this.onlySelf, this.length, this.fade, this.smoothending, this.smoothUp, this.alpha, this.first, this.second, this.colorMode);
    }
    
    @Override
    public void deltaTickEvent() {
        this.points.forEach(point -> ++point.age);
        if (!this.onlySelf.enabled) {
            Trails.mc.f.i.forEach(aed -> aed.points.forEach(point2 -> ++point2.age));
        }
        super.deltaTickEvent();
    }
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        if (Trails.mc.h == null || Trails.mc.f == null || Trails.mc.f.i.size() == 0) {
            return;
        }
        this.points.removeIf(point -> this.points.indexOf(point) >= this.length.getNum() || point.age >= this.length.getNum());
        if (!this.onlySelf.enabled) {
            for (final aed aed : Trails.mc.f.i) {
                aed.points.removeIf(point2 -> this.points.indexOf(point2) >= this.length.getNum() || point2.age >= this.length.getNum());
                if (aed == Trails.mc.h) {
                    continue;
                }
                if (Math.abs(aed.p - aed.M) == 0.0 && Math.abs(aed.r - aed.O) == 0.0) {
                    continue;
                }
                aed.points.add(new Point((float)(aed.M + (aed.p - aed.M) * Trails.mc.aj()), (float)(aed.N + (aed.q - aed.N) * Trails.mc.aj()), (float)(aed.O + (aed.r - aed.O) * Trails.mc.aj())));
            }
        }
        if (Math.abs(Trails.mc.h.p - Trails.mc.h.M) != 0.0 || Math.abs(Trails.mc.h.r - Trails.mc.h.O) != 0.0) {
            this.points.add(new Point((float)(Trails.mc.h.M + (Trails.mc.h.p - Trails.mc.h.M) * Trails.mc.aj()), (float)(Trails.mc.h.N + (Trails.mc.h.q - Trails.mc.h.N) * Trails.mc.aj()), (float)(Trails.mc.h.O + (Trails.mc.h.r - Trails.mc.h.O) * Trails.mc.aj())));
        }
        this.renderSelf();
    }
    
    void renderSelf() {
        bus.G();
        bus.a(238 + 427 - 404 + 255, 0.01f);
        bus.z();
        bus.m();
        bus.d();
        bus.r();
        bus.a(bus.r.l, bus.l.j, bus.r.e, bus.l.n);
        bus.j(547 + 3993 + 2100 + 785);
        if (!this.onlySelf.enabled) {
            this.renderAll();
        }
        for (final Point point : this.points) {
            if (this.points.indexOf(point) >= this.points.size() - 1) {
                continue;
            }
            final Point point2 = this.points.get(this.points.indexOf(point) + 1);
            float num = this.alpha.getNum();
            if (this.smoothending.enabled) {
                num = this.alpha.getNum() * Easings.easeInOutCubic(this.points.indexOf(point) / (float)this.points.size());
            }
            Color color = Color.WHITE;
            final String mode = this.colorMode.getMode();
            switch (mode) {
                case "Gradient": {
                    color = Utils.getGradientOffset(this.first.getColor(), this.second.getColor(), this.points.indexOf(point) / this.fade.getNum() + Main.millis / 1000.0f, (int)num);
                    break;
                }
                case "Single": {
                    color = RenderUtils.injectAlpha(this.first.getColor().getRGB(), (int)num);
                    break;
                }
                case "Astolfo": {
                    color = RenderUtils.injectAlpha(RenderUtils.astolfo(5000.0f, this.points.indexOf(point) / this.points.size() * (0xB6 ^ 0xBC)).getRGB(), (int)num);
                    break;
                }
                case "Rainbow": {
                    color = RenderUtils.injectAlpha(Color.getHSBColor(this.points.indexOf(point) / this.fade.getNum() + Main.millis / 1000.0f, 1.0f, 1.0f).getRGB(), (int)num);
                    break;
                }
                case "Aquafresh": {
                    color = Utils.getGradientOffset(Utils.getGradientOffset(Color.BLUE, Color.RED, Main.millis / 2000.0f, 30 + 135 - 104 + 139), Color.WHITE.brighter(), this.points.indexOf(point) / this.fade.getNum() + Main.millis / 1000.0f, (int)num);
                    break;
                }
            }
            GL11.glBegin(8);
            final double n2 = point.x - Trails.mc.ac().o;
            final double n3 = point.y - Trails.mc.ac().p;
            final double n4 = point.z - Trails.mc.ac().q;
            final double n5 = point2.x - Trails.mc.ac().o;
            final double n6 = point2.y - Trails.mc.ac().p;
            final double n7 = point2.z - Trails.mc.ac().q;
            RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), this.smoothUp.enabled ? 0 : rk.a((int)(num / 1.75f), 0, 236 + 101 - 177 + 95)).getRGB());
            GL11.glVertex3d(n2, n3 + Trails.mc.h.H, n4);
            RenderUtils.color(color.getRGB());
            GL11.glVertex3d(n2, n3 + 0.09, n4);
            RenderUtils.color(new Color(color.getRed(), color.getGreen(), color.getBlue(), this.smoothUp.enabled ? 0 : rk.a((int)(num / 1.75f), 0, 138 + 127 - 200 + 190)).getRGB());
            GL11.glVertex3d(n5, n6 + Trails.mc.h.H, n7);
            RenderUtils.color(color.getRGB());
            GL11.glVertex3d(n5, n6 + 0.09, n7);
            GL11.glEnd();
            final Point point3 = point;
            ++point3.age;
        }
        bus.j(3508 + 5093 - 3733 + 2556);
        bus.l();
        bus.e();
        bus.q();
        bus.y();
        bus.I();
        bus.H();
    }
    
    @Override
    public void updateElements() {
        this.first.display = !this.colorMode.isMode("Rainbow");
        this.second.display = this.colorMode.isMode("Gradient");
        super.updateElements();
    }
    
    @Override
    public void onDisable() {
        this.points.clear();
        super.onDisable();
    }
    
    public class Point
    {
        public final float y;
        public float age;
        public final float z;
        public final float x;
        
        public Point(final float x, final float y, final float z) {
            this.x = x;
            this.y = y;
            this.z = z;
        }
    }
}
