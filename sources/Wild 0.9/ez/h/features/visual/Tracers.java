package ez.h.features.visual;

import java.awt.*;
import ez.h.managers.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import java.util.*;
import ez.h.event.*;
import ez.h.event.events.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class Tracers extends Feature
{
    OptionColor playersColor;
    OptionSlider width;
    OptionMode mode;
    OptionColor friendsColor;
    OptionColor color;
    OptionBoolean friends;
    OptionBoolean players;
    OptionColor mobsColor;
    OptionBoolean mobs;
    
    int getDistanceColor(final aed aed, final int n) {
        return Utils.getGradientOffset(this.color.getColor(), new Color(-1), Tracers.mc.h.getDistance((vg)aed) / (Tracers.mc.t.e * (0x61 ^ 0x71)), this.color.alpha).getRGB();
    }
    
    @EventTarget
    public void onRender2D(final EventRender3D eventRender3D) {
        if (this.mode.isMode("Lines")) {
            for (final vg vg : Tracers.mc.f.e) {
                if (vg instanceof acl) {
                    continue;
                }
                if (vg.F) {
                    continue;
                }
                if (!(vg instanceof ade) && !(vg instanceof zv) && !(vg instanceof adl) && !(vg instanceof zt) && !(vg instanceof aed)) {
                    continue;
                }
                if (vg instanceof aed && !this.players.enabled) {
                    continue;
                }
                if ((vg instanceof ade || vg instanceof zv || vg instanceof adl || vg instanceof zt) && !this.mobs.enabled) {
                    continue;
                }
                if (vg == Tracers.mc.h) {
                    continue;
                }
                if (FriendManager.isFriend(vg.h_()) && !this.friends.enabled) {
                    continue;
                }
                Color color = new Color(-1);
                if (vg instanceof aed) {
                    color = this.playersColor.getColor();
                }
                if (vg instanceof ade || vg instanceof zv || vg instanceof adl || vg instanceof zt) {
                    color = this.mobsColor.getColor();
                }
                final double n = vg.M + (vg.p - vg.M) * Tracers.mc.aj() - Tracers.mc.ac().o;
                final double n2 = vg.N + (vg.q - vg.N) * Tracers.mc.aj() - Tracers.mc.ac().p;
                final double n3 = vg.O + (vg.r - vg.O) * Tracers.mc.aj() - Tracers.mc.ac().q;
                bus.G();
                GL11.glEnable(1479 + 420 + 360 + 589);
                GL11.glLineWidth((this.width.getNum() == 0.0f) ? 0.1f : this.width.getNum());
                GL11.glDisable(1778 + 2038 - 2092 + 1205);
                GL11.glDisable(1563 + 3219 - 4513 + 3284);
                GL11.glEnable(2945 + 2478 - 2827 + 446);
                GL11.glBlendFunc(617 + 19 - 490 + 624, 738 + 653 - 1222 + 602);
                if (this.friends.enabled && FriendManager.isFriend(vg.h_())) {
                    RenderUtils.color(this.friendsColor.getColor().getRGB());
                }
                else {
                    RenderUtils.color(color.getRGB());
                }
                final bhe b = new bhe(0.0, 0.0, 1.0).a(-(float)Math.toRadians(Tracers.mc.h.w)).b(-(float)Math.toRadians(Tracers.mc.h.v));
                GL11.glBegin(2);
                GL11.glVertex3d(b.x, Tracers.mc.h.by() + b.y, b.z);
                GL11.glVertex3d(n, n2, n3);
                GL11.glEnd();
                GL11.glEnable(60 + 1378 + 90 + 1401);
                GL11.glEnable(3524 + 731 - 838 + 136);
                GL11.glDisable(258 + 2471 - 698 + 817);
                bus.I();
                bus.H();
            }
        }
    }
    
    @EventTarget
    public void render2D(final EventRender2D eventRender2D) {
        if (this.mode.isMode("Arrows")) {
            final bit bit = new bit(Tracers.mc);
            for (int i = 0; i < Tracers.mc.f.i.size(); ++i) {
                final aed aed = Tracers.mc.f.i.get(i);
                final double n = bit.a() / 2.0f - 49.01960784313725;
                final double n2 = bit.b() / 2.0f - 50.428643469490666;
                final double p = Tracers.mc.h.p;
                final double r = Tracers.mc.h.r;
                final double n3 = Tracers.mc.Y.renderPartialTicks;
                final double n4 = (aed.p + (aed.p - aed.M) * n3 - p) * 0.20000000298023224;
                final double n5 = (aed.r + (aed.r - aed.O) * n3 - r) * 0.20000000298023224;
                final double cos = Math.cos(Tracers.mc.h.v * 0.017453292519943295);
                final double sin = Math.sin(Tracers.mc.h.v * 0.017453292519943295);
                final double n6 = -(n5 * cos - n4 * sin);
                final double n7 = -(n4 * cos + n5 * sin);
                final double n8 = 0.0 - n7;
                final double n9 = 0.0 - n6;
                if (!aed.F && aed.cd() > 0.0f && aed != null && aed != Tracers.mc.h && rk.a(n8 * n8 + n9 * n9) < 46.0f) {
                    final double n10 = Math.atan2(n6 - 0.0, n7 - 0.0) * 180.0 / 3.141592653589793;
                    final double n11 = 50.0 * Math.cos(Math.toRadians(n10)) + n + 50.0;
                    final double n12 = 50.0 * Math.sin(Math.toRadians(n10)) + n2 + 50.0;
                    bus.G();
                    bus.b(n11, n12, 0.0);
                    bus.b((float)n10 - 90.0f, 0.0f, 0.0f, 1.0f);
                    RenderUtils.drawBlurredShadowCircle(-10.0f, -5.0f, 20.0f, 20.0f, 0x9B ^ 0x85, RenderUtils.injectAlpha(this.color.getColor().getRGB(), 0xC0 ^ 0xA4));
                    bus.a(15.0, 15.0, 1.0);
                    this.renderArrow(this.color.getColor().getRGB());
                    bus.H();
                }
            }
        }
    }
    
    void renderArrow(final int n) {
        GL11.glPushMatrix();
        GL11.glDisable(1371 + 1101 - 608 + 1065);
        GL11.glDisable(1052 + 119 - 1111 + 3493);
        GL11.glEnable(1034 + 1184 - 94 + 918);
        GL11.glBlendFunc(551 + 172 - 463 + 510, 503 + 688 - 619 + 199);
        GL11.glBegin(5);
        RenderUtils.color(this.color.getColor().getRGB());
        GL11.glVertex2d(0.0, 0.20000000298023224);
        GL11.glVertex2d(-0.4000000059604645, 0.0);
        GL11.glVertex2d(0.0, 1.0);
        GL11.glEnd();
        GL11.glBegin(5);
        RenderUtils.color(this.color.getColor().darker().getRGB());
        GL11.glVertex2d(0.4000000059604645, 0.0);
        GL11.glVertex2d(0.0, 0.20000000298023224);
        GL11.glVertex2d(0.0, 1.0);
        GL11.glEnd();
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        GL11.glEnable(2365 + 842 - 1858 + 1580);
        GL11.glEnable(832 + 535 + 82 + 2104);
        GL11.glPopMatrix();
    }
    
    @Override
    public void updateElements() {
        this.friendsColor.display = this.friends.enabled;
        this.playersColor.display = this.players.enabled;
        this.mobsColor.display = this.mobs.enabled;
        super.updateElements();
    }
    
    public Tracers() {
        super("Tracers", "\u0420\u0438\u0441\u0443\u0435\u0442 \u043b\u0438\u043d\u0438\u0438 \u0438 \u0441\u0442\u0440\u0435\u043b\u043e\u0447\u043a\u0438 \u043a \u0438\u0433\u0440\u043e\u043a\u0430\u043c.", Category.VISUAL);
        this.players = new OptionBoolean(this, "Players", true);
        this.mobs = new OptionBoolean(this, "Mobs", false);
        this.playersColor = new OptionColor(this, "Players Color", new Color(-1), true);
        this.mobsColor = new OptionColor(this, "Mobs Color", new Color(-1), true);
        this.mode = new OptionMode(this, "Mode", "Lines", new String[] { "Lines", "Arrows" }, 0);
        this.width = new OptionSlider(this, "Width", 0.7f, 0.1f, 3.0f, OptionSlider.SliderType.NULL);
        this.color = new OptionColor(this, "Color", new Color(10 + 36 + 157 + 52, 187 + 204 - 371 + 235, 79 + 211 - 260 + 225), true);
        this.friends = new OptionBoolean(this, "Friends", false);
        this.friendsColor = new OptionColor(this, "Friends Color", new Color(0xC ^ 0x18, 108 + 146 - 173 + 119, 184 + 9 - 66 + 73), true);
        this.addOptions(this.mode, this.players, this.mobs, this.playersColor, this.mobsColor, this.width, this.color, this.friends, this.friendsColor);
    }
}
