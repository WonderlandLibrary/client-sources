package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.*;
import me.darkmagician6.morbid.util.*;
import java.util.*;

public final class KillAura extends ModBase
{
    private boolean safety;
    private long prevMs;
    private int aSpeed;
    private double aRange;
    private double angle;
    public static sq curTarget;
    
    public KillAura() {
        super("KillAura", "R", true, ".t aura");
        this.prevMs = -1L;
        this.aSpeed = 15;
        this.aRange = 3.85;
        this.angle = 180.0;
        this.setDescription("Attacks players.");
    }
    
    @Override
    public void onEnable() {
        KillAura.curTarget = this.getTarget();
        final RotationManager rotationManager = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager.setPitch(MorbidWrapper.getPlayer().B);
        final RotationManager rotationManager2 = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager2.setYaw(MorbidWrapper.getPlayer().A);
    }
    
    @Override
    public void onDisable() {
        KillAura.curTarget = null;
        final RotationManager rotationManager = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager.setPitch(MorbidWrapper.getPlayer().B);
        final RotationManager rotationManager2 = Morbid.getRotationManager();
        this.getWrapper();
        rotationManager2.setYaw(MorbidWrapper.getPlayer().A);
    }
    
    @Override
    public void preMotionUpdate() {
        final long aps = 1000L / this.aSpeed;
        KillAura.curTarget = this.getTarget();
        if (KillAura.curTarget == null) {
            return;
        }
        this.facePlayer(KillAura.curTarget);
        while (System.nanoTime() / 1000000L - this.prevMs >= aps) {
            this.getWrapper();
            MorbidWrapper.getPlayer().bK();
            this.getWrapper();
            MorbidHelper.sendPacket(new dr(MorbidWrapper.getPlayer().k, KillAura.curTarget.k, 1));
            this.prevMs = System.nanoTime() / 1000000L;
        }
    }
    
    @Override
    public void onCommand(final String s) {
        final String[] split = s.split(" ");
        if (s.toLowerCase().startsWith(".ar")) {
            try {
                final double aRange = Double.parseDouble(split[1]);
                this.aRange = aRange;
                this.getWrapper();
                MorbidWrapper.addChat("Aura range set to: " + aRange);
            }
            catch (Exception e) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .ar [aura range]");
            }
            ModBase.setCommandExists(true);
        }
        if (s.toLowerCase().startsWith(".angle")) {
            try {
                final double angle = Double.parseDouble(split[1]);
                this.angle = angle;
                this.getWrapper();
                MorbidWrapper.addChat("Max angle set to: " + angle);
            }
            catch (Exception e) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .ar [max angle]");
            }
            ModBase.setCommandExists(true);
        }
        if (s.toLowerCase().startsWith(".as")) {
            try {
                final int auraSpeed = Integer.parseInt(split[1]);
                this.aSpeed = auraSpeed;
                this.getWrapper();
                MorbidWrapper.addChat("Aura speed set to: " + this.aSpeed);
            }
            catch (Exception e) {
                this.getWrapper();
                MorbidWrapper.addChat("§cError! Correct usage: .as min [attack speed]");
            }
            ModBase.setCommandExists(true);
        }
        if (s.toLowerCase().startsWith(".t safety")) {
            this.safety = !this.safety;
            ModBase.setCommandExists(true);
            this.getWrapper();
            MorbidWrapper.addChat("Safety toggled " + (this.safety ? "on" : "off"));
        }
    }
    
    private void facePlayer(final sq e) {
        final double u = e.u;
        this.getWrapper();
        final double x = u - MorbidWrapper.getPlayer().u;
        final double w = e.w;
        this.getWrapper();
        final double z = w - MorbidWrapper.getPlayer().w;
        final double v = e.v;
        this.getWrapper();
        final double y = v - MorbidWrapper.getPlayer().v + 1.4;
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
    
    private boolean isEntityVisable(final sq e) {
        this.getWrapper();
        final ard u = MorbidWrapper.getWorld().U();
        this.getWrapper();
        final double u2 = MorbidWrapper.getPlayer().u;
        this.getWrapper();
        final double n = MorbidWrapper.getPlayer().v + 0.12;
        this.getWrapper();
        final arc a = u.a(u2, n, MorbidWrapper.getPlayer().w);
        this.getWrapper();
        return this.rayTraceAllBlocks(a, MorbidWrapper.getWorld().U().a(e.u, e.v + 1.82, e.w)) == null;
    }
    
    private ara rayTraceAllBlocks(final arc v1, final arc v2) {
        this.getWrapper();
        return MorbidWrapper.getWorld().a(v1, v2, false, true);
    }
    
    private sq getTarget() {
        sq target = null;
        double maxAngle = this.angle;
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
                this.getWrapper();
                if (MorbidWrapper.getPlayer().d(e) > this.aRange || (this.safety && e.ai())) {
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
        final double y = MorbidWrapper.getPlayer().v + 0.12 - (target.v + 1.82);
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
}
