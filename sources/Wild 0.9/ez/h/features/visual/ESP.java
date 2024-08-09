package ez.h.features.visual;

import ez.h.event.events.*;
import java.awt.*;
import org.lwjgl.opengl.*;
import ez.h.utils.*;
import org.lwjgl.util.glu.*;
import ez.h.*;
import java.util.*;
import ez.h.event.*;
import ez.h.features.*;
import ez.h.ui.clickgui.options.*;

public class ESP extends Feature
{
    OptionBoolean easing;
    public OptionColor mobsColor;
    public OptionMode mode;
    public OptionColor playersColor;
    public OptionBoolean players;
    public static OptionSlider glowStrength;
    public OptionBoolean mobs;
    
    @EventTarget
    public void onRender(final EventRender3D eventRender3D) {
        this.setSuffix(this.mode.getMode());
        if (this.mode.isMode("Chams")) {
            return;
        }
        for (final vg vg : ESP.mc.f.e) {
            if (vg instanceof acl) {
                continue;
            }
            if (vg.F) {
                continue;
            }
            if (vg == ESP.mc.h) {
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
            Color color = new Color(-1);
            if (vg instanceof aed) {
                color = this.playersColor.getColor();
            }
            if (vg instanceof ade || vg instanceof zv || vg instanceof adl || vg instanceof zt) {
                color = this.mobsColor.getColor();
            }
            final double n = vg.M + (vg.p - vg.M) * eventRender3D.getPartialTicks() - ESP.mc.ac().h;
            final double n2 = vg.N + (vg.q - vg.N) * eventRender3D.getPartialTicks() - ESP.mc.ac().i;
            final double n3 = vg.O + (vg.r - vg.O) * eventRender3D.getPartialTicks() - ESP.mc.ac().j;
            final Color color2 = new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
            bus.G();
            bus.m();
            bus.j();
            GL11.glNormal3f(0.0f, 1.0f, 0.0f);
            GL11.glBlendFunc(724 + 767 - 1089 + 368, 272 + 32 + 139 + 328);
            bus.z();
            if (this.mode.isMode("Stipple")) {
                GL11.glTranslatef((float)n, (float)(n2 + 1.0), (float)n3);
                if (ESP.mc.t.aw == 2) {
                    bus.b(-ESP.mc.ac().e, 0.0f, 1.0f, 0.0f);
                    bus.b(-ESP.mc.ac().f, 1.0f, 0.0f, 0.0f);
                }
                else {
                    bus.b(-ESP.mc.h.v, 0.0f, 1.0f, 0.0f);
                }
                GL11.glEnable(2546 + 488 - 350 + 164);
                GL11.glLineWidth(3.0f);
                RenderUtils.color(color.getRGB());
                GL11.glEnable(1511 + 2337 - 2370 + 1374);
                GL11.glLineStipple(1, (short)(138 + 104 - 232 + 245));
                bus.j(1856 + 7144 - 2567 + 992);
                GL11.glBegin(2);
                RenderUtils.color(color.getRGB());
                GL11.glVertex2d(-0.5, -1.0);
                GL11.glVertex2d(-0.5, 1.0);
                RenderUtils.color(color.brighter().getRGB());
                GL11.glVertex2d(0.5, 1.0);
                GL11.glVertex2d(0.5, -1.0);
                GL11.glEnd();
                bus.j(5802 + 3982 - 4666 + 2306);
                GL11.glDisable(1380 + 2422 - 2509 + 1559);
                GL11.glDisable(2666 + 1868 - 4358 + 2672);
                bus.I();
            }
            if (this.mode.isMode("LukovieKoltsa")) {
                GL11.glTranslatef((float)n, (float)n2, (float)n3);
                if (ESP.mc.t.aw == 2) {
                    bus.b(-ESP.mc.ac().e, 0.0f, 1.0f, 0.0f);
                    bus.b(-ESP.mc.ac().f, 1.0f, 0.0f, 0.0f);
                }
                else {
                    bus.b(-ESP.mc.h.v, 0.0f, 1.0f, 0.0f);
                }
                RenderUtils.drawCircleOut(0.0, vg.H / 2.0f, 0.5, 0.0, 360.0f, 10.0f, color.getRGB());
            }
            if (this.mode.isMode("Box")) {
                RenderUtils.drawOutline(new bhb(n - vg.G / 2.0f, n2, n3 - vg.G / 2.0f, n + vg.G / 2.0f, n2 + vg.H, n3 + vg.G / 2.0f), 1.6f, color2);
            }
            if (this.mode.isMode("2D")) {
                GL11.glTranslatef((float)n, (float)n2, (float)n3);
                if (ESP.mc.t.aw == 2) {
                    bus.b(-ESP.mc.ac().e, 0.0f, 1.0f, 0.0f);
                    bus.b(-ESP.mc.ac().f, 1.0f, 0.0f, 0.0f);
                }
                else {
                    bus.b(-ESP.mc.h.v, 0.0f, 1.0f, 0.0f);
                }
                RenderUtils.drawGradientRect(-0.5f, 0.0f, vg.G, vg.H + 0.1f, color.getRGB(), this.easing.enabled ? 0 : color.getRGB());
            }
            if (this.mode.isMode("Cylinder")) {
                GL11.glTranslated(n, n2 + 2.0, n3);
                GL11.glRotated(90.0, 1.0, 0.0, 0.0);
                GL11.glDisable(551 + 2212 - 1361 + 1482);
                GL11.glBlendFunc(96 + 357 + 118 + 199, 367 + 133 + 16 + 255);
                GL11.glShadeModel(5754 + 3991 - 5358 + 3038);
                final Cylinder cylinder = new Cylinder();
                RenderUtils.color(color.getRGB());
                cylinder.setDrawStyle(14350 + 72625 - 49751 + 62788);
                cylinder.setOrientation(93131 + 81887 - 137506 + 62509);
                cylinder.draw(0.62f, 0.65f, 2.0f, 0x10 ^ 0x30, 1);
                GL11.glShadeModel(3318 + 2917 - 803 + 1992);
                GL11.glEnable(2757 + 1589 - 3275 + 1813);
            }
            if (this.mode.isMode("Sims")) {
                GL11.glEnable(28788 + 26967 - 32815 + 9883);
                GL11.glPolygonOffset(1.0f, -1.1E7f);
                GL11.glTranslated(0.0, Math.sin(Main.millis / 200.0f) / 3.0, 0.0);
                GL11.glEnable(2019 + 422 - 1291 + 1779);
                GL11.glDisable(1717 + 2119 - 1169 + 217);
                final double n4 = n2 + 3.5;
                GL11.glColor4f(0.1f, 1.0f, 0.1f, 1.0f);
                GL11.glBegin(5);
                GL11.glVertex3d(n, n4, n3);
                GL11.glVertex3d(n - 0.25, n4 - 0.4, n3);
                GL11.glVertex3d(n, n4 - 0.4000000059604645, n3 + 0.25);
                GL11.glColor4f(0.1f, 0.8f, 0.1f, 1.0f);
                GL11.glVertex3d(n, n4 - 0.800000011920929, n3);
                GL11.glEnd();
                GL11.glColor4f(0.1f, 0.75f, 0.1f, 1.0f);
                GL11.glBegin(5);
                GL11.glVertex3d(n, n4, n3);
                GL11.glVertex3d(n + 0.25, n4 - 0.4, n3);
                GL11.glVertex3d(n, n4 - 0.4000000059604645, n3 + 0.25);
                GL11.glColor4f(0.1f, 0.7f, 0.1f, 1.0f);
                GL11.glVertex3d(n, n4 - 0.800000011920929, n3);
                GL11.glEnd();
                GL11.glColor4f(0.1f, 1.0f, 0.1f, 1.0f);
                GL11.glBegin(5);
                GL11.glVertex3d(n, n4, n3);
                GL11.glVertex3d(n + 0.25, n4 - 0.4, n3);
                GL11.glVertex3d(n, n4 - 0.4000000059604645, n3 - 0.25);
                GL11.glColor4f(0.1f, 0.8f, 0.1f, 1.0f);
                GL11.glVertex3d(n, n4 - 0.800000011920929, n3);
                GL11.glEnd();
                GL11.glColor4f(0.1f, 0.75f, 0.1f, 1.0f);
                GL11.glBegin(5);
                GL11.glVertex3d(n, n4, n3);
                GL11.glVertex3d(n - 0.25, n4 - 0.4, n3);
                GL11.glVertex3d(n, n4 - 0.4000000059604645, n3 - 0.25);
                GL11.glColor4f(0.1f, 0.7f, 0.1f, 1.0f);
                GL11.glVertex3d(n, n4 - 0.800000011920929, n3);
                GL11.glEnd();
                GL11.glEnable(1786 + 2881 - 2316 + 533);
                GL11.glDisable(26525 + 17603 - 20848 + 9543);
                GL11.glPolygonOffset(1.0f, 1100000.0f);
            }
            bus.k();
            bus.y();
            bus.I();
            bus.H();
        }
    }
    
    @Override
    public void updateElements() {
        ESP.glowStrength.display = this.mode.isMode("Glow");
        this.easing.display = this.mode.isMode("2D");
        this.playersColor.display = this.players.enabled;
        this.mobsColor.display = this.players.enabled;
        super.updateElements();
    }
    
    public ESP() {
        super("ESP", "\u0412\u044b \u043c\u043e\u0436\u0435\u0442\u0435 \u0432\u0438\u0434\u0435\u0442\u044c \u0441\u0443\u0449\u043d\u043e\u0441\u0442\u0435\u0439 \u0447\u0435\u0440\u0435\u0437 \u0441\u0442\u0435\u043d\u044b.", Category.VISUAL);
        this.mode = new OptionMode(this, "Mode", "Chams", new String[] { "Chams", "Sims", "Box", "Cylinder", "Stipple", "2D", "LukovieKoltsa", "Cosmo" }, 0);
        this.players = new OptionBoolean(this, "Players", true);
        this.mobs = new OptionBoolean(this, "Mobs", false);
        this.playersColor = new OptionColor(this, "Players Color", new Color(0, 0, 0), true);
        this.mobsColor = new OptionColor(this, "Mobs Color", new Color(0, 0, 0), true);
        this.easing = new OptionBoolean(this, "Easing", true);
        ESP.glowStrength = new OptionSlider(this, "Glow Strength", 3.0f, 1.0f, 10.0f, OptionSlider.SliderType.NULLINT);
        this.addOptions(this.mode, this.players, this.mobs, this.playersColor, this.mobsColor, ESP.glowStrength, this.easing);
    }
}
