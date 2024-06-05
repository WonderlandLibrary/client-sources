package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import org.lwjgl.opengl.*;
import me.darkmagician6.morbid.*;
import java.util.*;

public final class Tracers extends ModBase
{
    public Tracers() {
        super("Tracers", "BACK", false, ".t tracer");
        this.setDescription("Draws lines to players.");
    }
    
    @Override
    public void onRenderHand() {
        GL11.glScalef(0.5f, 0.5f, 0.5f);
        GL11.glLineWidth(1.2f);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glBegin(1);
        this.getWrapper();
        for (final Object o : MorbidWrapper.getWorld().h) {
            final sq e = (sq)o;
            final String stripped = lf.a(e.bS);
            final double x = e.u - bgy.a.m;
            final double y = e.v - bgy.a.n;
            final double z = e.w - bgy.a.o;
            final sq sq = e;
            this.getWrapper();
            if (sq != MorbidWrapper.getPlayer()) {
                this.getWrapper();
                if (MorbidWrapper.getPlayer().d(e) > 34.0f) {
                    continue;
                }
                if (!e.R()) {
                    continue;
                }
                if (Morbid.getFriends().isFriend(e)) {
                    GL11.glColor3f(0.0f, 0.38f, 0.58f);
                }
                else if (e.ai()) {
                    GL11.glColor3f(0.8f, 0.38f, 0.0f);
                }
                else if (KillAura.curTarget != null && e == KillAura.curTarget) {
                    GL11.glColor3f(1.0f, 0.25f, 0.0f);
                }
                else {
                    this.getWrapper();
                    if (MorbidWrapper.getPlayer().d(e) <= 5.0f) {
                        GL11.glColor3f(0.0f, 0.0f, 0.0f);
                    }
                    else {
                        this.getWrapper();
                        if (MorbidWrapper.getPlayer().d(e) <= 34.0f) {
                            GL11.glColor3f(0.25f, 0.25f, 0.25f);
                        }
                    }
                }
                GL11.glVertex3d(0.0, 0.0, 0.0);
                GL11.glVertex3d(x, y, z);
                GL11.glVertex3d(x, y + 1.9, z);
                GL11.glVertex3d(x, y, z);
            }
        }
        GL11.glEnd();
        GL11.glScalef(2.0f, 2.0f, 2.0f);
        GL11.glDepthMask(true);
        GL11.glDisable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
}
