package me.darkmagician6.morbid.util;

import org.lwjgl.opengl.*;
import me.darkmagician6.morbid.*;
import java.util.*;

public class Utils
{
    public static void drawBoundingBox(final aqx axisalignedbb) {
        final bgd tessellator = bgd.a;
        tessellator.b();
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a();
        tessellator.b();
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a();
        tessellator.b();
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a();
        tessellator.b();
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a();
        tessellator.b();
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a();
        tessellator.b();
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.f);
        tessellator.a(axisalignedbb.a, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.a, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.c);
        tessellator.a(axisalignedbb.d, axisalignedbb.e, axisalignedbb.f);
        tessellator.a(axisalignedbb.d, axisalignedbb.b, axisalignedbb.f);
        tessellator.a();
    }
    
    public static void drawOutlinedBoundingBox(final aqx par1AxisAlignedBB) {
        final bgd var2 = bgd.a;
        var2.b(3);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a();
        var2.b(3);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a();
        var2.b(1);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.c);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.d, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.b, par1AxisAlignedBB.f);
        var2.a(par1AxisAlignedBB.a, par1AxisAlignedBB.e, par1AxisAlignedBB.f);
        var2.a();
    }
    
    public static void drawESP(final double d, final double d1, final double d2, final double r, final double b, final double g) {
        GL11.glPushMatrix();
        GL11.glEnable(3042);
        GL11.glBlendFunc(770, 771);
        GL11.glLineWidth(1.5f);
        GL11.glDisable(2896);
        GL11.glDisable(3553);
        GL11.glEnable(2848);
        GL11.glDisable(2929);
        GL11.glDepthMask(false);
        GL11.glColor4d(r, g, b, 0.18250000476837158);
        drawBoundingBox(new aqx(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glColor4d(r, g, b, 1.0);
        drawOutlinedBoundingBox(new aqx(d, d1, d2, d + 1.0, d1 + 1.0, d2 + 1.0));
        GL11.glLineWidth(2.0f);
        GL11.glDisable(2848);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glDepthMask(true);
        GL11.glDisable(3042);
        GL11.glPopMatrix();
    }
    
    public static double getDistance(final double X, final double Y, final double Z) {
        final double posX = X - MorbidWrapper.mcObj().g.u;
        final double posY = Y - MorbidWrapper.mcObj().g.v;
        final double posZ = Z - MorbidWrapper.mcObj().g.w;
        return Math.abs(Math.sqrt(posX * posX + posY * posY + posZ * posZ));
    }
    
    public static sq getClosestPlayer() {
        double distance = 9.99999999999E11;
        sq target = null;
        for (final Object entity : MorbidWrapper.mcObj().e.h) {
            final sq e = (sq)entity;
            if (e instanceof sq) {
                if (e == MorbidWrapper.mcObj().g) {
                    continue;
                }
                if (getDistance(e.u, e.v, e.w) >= distance) {
                    continue;
                }
                distance = getDistance(e.u, e.v, e.w);
                target = e;
            }
        }
        return target;
    }
}
