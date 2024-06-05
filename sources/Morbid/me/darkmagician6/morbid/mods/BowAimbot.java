package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import org.lwjgl.input.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.util.*;
import org.lwjgl.opengl.*;
import java.util.*;

public final class BowAimbot extends ModBase
{
    private sq target;
    private final double gravityConstant = 0.044;
    private int count;
    
    public BowAimbot() {
        super("BowAimbot", "H", true, ".t bow");
        this.setDescription("Aims at players with a bow.");
    }
    
    @Override
    public void preMotionUpdate() {
        this.getWrapper();
        if (MorbidWrapper.getPlayer().cd() != null) {
            this.getWrapper();
            if (MorbidWrapper.mcObj().s == null) {
                this.getWrapper();
                if (MorbidWrapper.getPlayer().cd().b().equals(wk.l) && Mouse.isButtonDown(1)) {
                    try {
                        this.target = this.getTarget();
                        if (this.target == null) {
                            return;
                        }
                        final float var1 = this.getPitchA(this.target);
                        this.facePlayer(this.target);
                        Morbid.getRotationManager().setPitch(Morbid.getRotationManager().getPitch() + var1);
                        Morbid.getRotationManager().setYaw(this.getYaw(this.target));
                        this.count = 0;
                    }
                    catch (Exception ex) {}
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.target = this.getTarget();
        final RotationManager rotationManager = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager.setPitch(MorbidWrapper.getPlayer().B);
        final RotationManager rotationManager2 = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager2.setYaw(MorbidWrapper.getPlayer().A);
    }
    
    @Override
    public void onDisable() {
        final RotationManager rotationManager = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager.setPitch(MorbidWrapper.getPlayer().B);
        final RotationManager rotationManager2 = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager2.setYaw(MorbidWrapper.getPlayer().A);
        this.target = null;
    }
    
    @Override
    public void onRenderHand() {
        if (!this.isEnabled()) {
            return;
        }
        final sq e = this.target;
        if (e == null) {
            return;
        }
        final double x = e.u - bgy.a.m;
        final double y = e.v - bgy.a.n;
        final double z = e.w - bgy.a.o;
        GL11.glLineWidth(1.4f);
        GL11.glColor3f(1.0f, 0.0f, 0.0f);
        GL11.glEnable(2848);
        GL11.glDisable(3553);
        GL11.glDisable(2896);
        GL11.glDepthMask(false);
        GL11.glDisable(2929);
        GL11.glBegin(1);
        GL11.glVertex3d(x, y + 1.9, z);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDepthMask(true);
        GL11.glDisable(2929);
        GL11.glEnable(3553);
        GL11.glDisable(2848);
    }
    
    private void facePlayer(final sq e) {
        final double u = e.u;
        this.getWrapper();
        final double x = u - MorbidWrapper.getPlayer().u;
        final double w = e.w;
        this.getWrapper();
        final double z = w - MorbidWrapper.getPlayer().w;
        final double n = e.v + e.e() / 1.4;
        this.getWrapper();
        final double n2 = n - MorbidWrapper.getPlayer().v;
        this.getWrapper();
        final double y = n2 + MorbidWrapper.getPlayer().e() / 1.4;
        final double helper = kx.a(x * x + z * z);
        float yaw = (float)Math.toDegrees(-Math.atan(x / z));
        final float pitch = (float)(-Math.toDegrees(Math.atan(y / helper)));
        if (z < 0.0 && x < 0.0) {
            yaw = (float)(90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        else if (z < 0.0 && x > 0.0) {
            yaw = (float)(-90.0 + Math.toDegrees(Math.atan(z / x)));
        }
        Morbid.getRotationManager().setPitch(pitch);
        Morbid.getRotationManager().setYaw(yaw);
    }
    
    private float getYaw(final mp e) {
        final double u = e.u;
        this.getWrapper();
        final double xDistance = u - MorbidWrapper.getPlayer().u;
        final double w = e.w;
        this.getWrapper();
        final double zDistance = w - MorbidWrapper.getPlayer().w;
        return (float)(Math.atan2(zDistance, xDistance) * 180.0 / 3.141592653589793) - 90.0f;
    }
    
    public float getPitchA(final ng t) {
        final double u = t.u;
        this.getWrapper();
        final double xDiff = u - MorbidWrapper.getPlayer().u;
        final double w = t.w;
        this.getWrapper();
        final double zDiff = w - MorbidWrapper.getPlayer().w;
        final double v = t.v;
        this.getWrapper();
        final double yDiff = v - MorbidWrapper.getPlayer().v;
        final double x;
        final double dist = x = -kx.a(xDiff * xDiff + zDiff * zDiff);
        final double v2 = t.v;
        this.getWrapper();
        final double n = v2 - MorbidWrapper.getPlayer().v;
        final double v3 = t.v;
        this.getWrapper();
        double y = -kx.a(n * (v3 - MorbidWrapper.getPlayer().v));
        y = -0.5;
        final double vSq = this.getVelocity() * this.getVelocity();
        final double vQb = vSq * vSq;
        final double g = 0.044;
        float pitch = 0.0f;
        final float topHalf = (float)(vSq - kx.a(vQb - g * (g * (x * x) + 2.0 * (y * vSq))));
        final float bottomHalf = (float)(g * x);
        final float var1 = topHalf / bottomHalf;
        pitch = (float)Math.atan(var1);
        return pitch * 100.0f;
    }
    
    private double getVelocity() {
        this.getWrapper();
        final int n = MorbidWrapper.getPlayer().bV().n();
        this.getWrapper();
        final int var6 = n - MorbidWrapper.getPlayer().bW();
        float var7 = var6 / 20.0f;
        var7 = (var7 * var7 + var7 * 2.0f) / 3.0f;
        if (var7 > 1.0f) {
            var7 = 1.0f;
        }
        return var7 * 3.2;
    }
    
    private sq getTarget() {
        sq target = null;
        double maxAngle = 180.0;
        this.getWrapper();
        for (final Object o : MorbidWrapper.getWorld().h) {
            if (o != null) {
                final Object o2 = o;
                this.getWrapper();
                if (o2 == MorbidWrapper.getPlayer()) {
                    continue;
                }
                final sq e = (sq)o;
                if (Morbid.getFriends().isFriend(e) || !this.isEntityVisable(e)) {
                    continue;
                }
                if (!e.R()) {
                    continue;
                }
                final float[] aimAngles = this.getYawAndPitch(e);
                final double curAngle = this.getDistanceBetweenAngles(aimAngles[0]);
                if (curAngle >= maxAngle) {
                    continue;
                }
                target = e;
                maxAngle = curAngle;
            }
        }
        return target;
    }
    
    private float[] getYawAndPitch(final sq target) {
        final double u = target.u;
        this.getWrapper();
        final double x = u - MorbidWrapper.getPlayer().u;
        final double w = target.w;
        this.getWrapper();
        final double z = w - MorbidWrapper.getPlayer().w;
        this.getWrapper();
        final double v = MorbidWrapper.getPlayer().v;
        this.getWrapper();
        final double y = v + MorbidWrapper.getPlayer().e() - (target.v + target.e());
        final double helper = kx.a(x * x + z * z);
        final float newYaw = (float)(Math.atan2(z, x) * 180.0 / 3.141592653589793) - 90.0f;
        final float newPitch = (float)(Math.atan2(y, helper) * 180.0 / 3.141592653589793);
        return new float[] { newYaw, newPitch };
    }
    
    private float getDistanceBetweenAngles(final float f1) {
        this.getWrapper();
        float angle = Math.abs(f1 - MorbidWrapper.getPlayer().A) % 360.0f;
        if (angle > 180.0f) {
            angle = 360.0f - angle;
        }
        return angle;
    }
    
    private boolean isEntityVisable(final sq e) {
        this.getWrapper();
        final ard u = MorbidWrapper.getWorld().U();
        this.getWrapper();
        final double u2 = MorbidWrapper.getPlayer().u;
        this.getWrapper();
        final double v = MorbidWrapper.getPlayer().v;
        this.getWrapper();
        final double n = v + MorbidWrapper.getPlayer().e();
        this.getWrapper();
        final arc a = u.a(u2, n, MorbidWrapper.getPlayer().w);
        this.getWrapper();
        return this.rayTraceAllBlocks(a, MorbidWrapper.getWorld().U().a(e.u, e.v + e.e(), e.w)) == null;
    }
    
    private ara rayTraceAllBlocks(final arc v1, final arc v2) {
        this.getWrapper();
        return MorbidWrapper.getWorld().a(v1, v2, false, true);
    }
}
