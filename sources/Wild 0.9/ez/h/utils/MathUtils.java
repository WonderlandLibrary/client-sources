package ez.h.utils;

import java.util.*;
import java.util.concurrent.*;
import ez.h.event.events.*;
import ez.h.event.*;
import ez.h.features.combat.*;

public class MathUtils
{
    static bib mc;
    public static float serverYaw;
    public static float serverPitch;
    
    public static float nextFloat(final float n, final float n2) {
        return (n != n2 && n2 - n > 0.0f) ? ((float)(n + (n2 - n) * Math.random())) : n;
    }
    
    public static float getPitchDifference(final aed aed, final aed aed2) {
        return -(aed.w - rk.g((float)(-Math.toDegrees(Math.atan2(aed.q - aed2.q, aed.r - aed2.r))))) % 360.0f;
    }
    
    public static bhc rayTrace(final vg vg, final float n, final float n2, final double n3, final float n4) {
        final bhe positionEyes = getPositionEyes(vg, n4);
        final bhe look = getLook(n, n2, n4);
        return MathUtils.mc.f.a(positionEyes, positionEyes.b(look.x * n3, look.y * n3, look.z * n3), false, false, true);
    }
    
    public static float clampRotation() {
        float v = MathUtils.mc.h.v;
        float n = 1.0f;
        if (MathUtils.mc.h.e.moveForward < 0.0f) {
            v += 180.0f;
            n = -0.5f;
        }
        else if (MathUtils.mc.h.e.moveForward > 0.0f) {
            n = 0.5f;
        }
        if (MathUtils.mc.h.e.a > 0.0f) {
            v -= 90.0f * n;
        }
        if (MathUtils.mc.h.e.a < 0.0f) {
            v += 90.0f * n;
        }
        return (float)Math.toRadians(v);
    }
    
    public static float[] getRotationsNeeded(final vg vg) {
        if (vg == null) {
            return null;
        }
        final bib z = bib.z();
        final double n = vg.p - z.h.p;
        final double n2 = vg.q + vg.by() / 2.0f - (z.h.q + z.h.by());
        final double n3 = vg.r - z.h.r;
        return new float[] { (z.h.v + rk.g((float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793) - 90.0f - z.h.v)) % 360.0f, (z.h.w + rk.g((float)(-(Math.atan2(n2, rk.a(n * n + n3 * n3)) * 180.0 / 3.141592653589793)) - z.h.w)) % 360.0f };
    }
    
    public static float[] getFacingRotations2(final int n, final double n2, final int n3) {
        final aet aet = new aet((amu)bib.z().f);
        aet.p = n + 0.5;
        aet.q = n2 + 0.5;
        aet.r = n3 + 0.5;
        return getRotationsNeeded((vg)aet);
    }
    
    public static int nextInt(final int n, final int n2) {
        return new Random().nextInt(n2 - n) + n;
    }
    
    static {
        MathUtils.mc = bib.z();
    }
    
    public static boolean isInRange(final float n, final float n2, final float n3) {
        return n2 <= n && n <= n3;
    }
    
    public static float[] getForward() {
        return new float[] { (float)(-Math.sin(Math.toRadians(MathUtils.mc.h.v))), (float)Math.cos(Math.toRadians(MathUtils.mc.h.v)) };
    }
    
    public static float getYaw(final bhe bhe) {
        final float n = (float)(StrictMath.atan2((float)(bhe.z - MathUtils.mc.h.r), (float)(bhe.x - MathUtils.mc.h.p)) * 180.0 / 3.141592653589793) - 90.0f;
        final float v = MathUtils.mc.h.v;
        return v + rk.g(n - v);
    }
    
    public static float easeInSine(final float n) {
        return (n == 0.0f) ? 0.0f : ((float)((n == 1.0f) ? 1.0 : (Math.pow(2.0, -10.0f * n) * Math.sin((n * 10.0f - 0.75) * 2.094395160675049) + 1.0)));
    }
    
    public static double smooth(final double n, double n2, final double n3, final boolean b, final double n4) {
        ++n2;
        double n5 = n2 + (n - n2) * ((Math.tanh(Math.toRadians(System.currentTimeMillis() * n3 % 360.0 - 180.0)) + 1.0) / 2.0);
        if (b) {
            n5 *= ThreadLocalRandom.current().nextDouble(n4, 1.0);
        }
        return Math.ceil(n5 * 1000.0) / 1000.0;
    }
    
    public static float lerp(final float n, final float n2, final float n3) {
        return n + n3 * (n2 - n);
    }
    
    @EventTarget
    public void onpacketsent(final EventPacketSend eventPacketSend) {
        if (eventPacketSend.packet instanceof lk) {
            MathUtils.serverYaw = ((lk)eventPacketSend.packet).d;
            MathUtils.serverPitch = ((lk)eventPacketSend.packet).e;
        }
    }
    
    public static float getYawDifference(final vg vg, final vg vg2) {
        final double n = vg.p - vg2.p;
        final double n2 = vg.r - vg2.r;
        float n3;
        if (n2 < 0.0 && n < 0.0) {
            n3 = (float)(90.0 + Math.toDegrees(Math.atan(n2 / n)));
        }
        else if (n2 < 0.0 && n > 0.0) {
            n3 = (float)(-90.0 + Math.toDegrees(Math.atan(n2 / n)));
        }
        else {
            n3 = (float)Math.toDegrees(-Math.atan(n / n2));
        }
        float n4 = -(vg.v - n3) % 360.0f;
        if (n4 >= 180.0f) {
            n4 -= 360.0f;
        }
        if (n4 < -180.0f) {
            n4 += 360.0f;
        }
        return n4;
    }
    
    public static float getAngleChange(final vp vp) {
        float n = Rotations.getDefaultRotations((aed)vp)[0];
        float n2 = Rotations.getDefaultRotations((aed)vp)[1];
        float v = MathUtils.mc.h.v;
        float w = MathUtils.mc.h.w;
        if (v < 0.0f) {
            v += 360.0f;
        }
        if (w < 0.0f) {
            w += 360.0f;
        }
        if (n < 0.0f) {
            n += 360.0f;
        }
        if (n2 < 0.0f) {
            n2 += 360.0f;
        }
        return Math.max(v, n) - Math.min(v, n) + (Math.max(w, n2) - Math.min(w, n2));
    }
    
    private static bhe getLook(final float n, final float n2, final float n3) {
        return getVectorForRotation(n2 - 2.0f + (n2 - (n2 - 2.0f)) * n3, n - 2.0f + (n - (n - 2.0f)) * n3);
    }
    
    public static double map(double n, final double n2, final double n3, final double n4, final double n5) {
        n = (n - n2) / (n3 - n2);
        return n4 + n * (n5 - n4);
    }
    
    public static float[] applyMouseSensitivity(final float n, final float n2, final boolean b) {
        final float max = Math.max(0.001f, MathUtils.mc.t.c);
        int n3 = (int)((n - MathUtils.serverYaw) / (max * ((max >= 0.5) ? max : 1.0f) / 2.0f));
        int n4 = (int)((n2 - MathUtils.serverPitch) / (max * ((max >= 0.5) ? max : 1.0f) / 2.0f)) * -1;
        if (b) {
            n3 -= (int)(n3 % 0.5 + 0.25);
            n4 -= (int)(n4 % 0.5 + 0.25);
        }
        final float n5 = max * 0.6f + 0.2f;
        final float n6 = n5 * n5 * n5 * 8.0f;
        final float n7 = n3 * n6;
        final float n8 = n4 * n6;
        final float n9 = (float)(MathUtils.serverYaw + n7 * 0.15);
        final float n10 = (float)(MathUtils.serverPitch - n8 * 0.15);
        final double n11 = ((float)Math.pow((float)((n9 - MathUtils.serverYaw) / 0.15) / n3 / 8.0f, 0.3333333432674408) - 0.2f) / 0.6f;
        return new float[] { n9, n10 };
    }
    
    public static double angleDifference(final double n, final double n2) {
        float n3 = (float)(Math.abs(n - n2) % 360.0);
        if (n3 > 180.0f) {
            n3 = 360.0f - n3;
        }
        return n3;
    }
    
    public static boolean canSeeEntityAtFov(final vg vg, final float n) {
        return angleDifference((float)(Math.toDegrees(Math.atan2(vg.r - MathUtils.mc.h.r, vg.p - MathUtils.mc.h.p)) - 90.0), MathUtils.mc.h.v) <= n;
    }
    
    public static bhe getPositionEyes(final vg vg, final float n) {
        return new bhe(vg.m + (vg.p - vg.m) * n, vg.n + (vg.q - vg.n) * n + vg.by(), vg.o + (vg.r - vg.o) * n);
    }
    
    public static bhe getVectorForRotation(final float n, final float n2) {
        final float b = rk.b(-n2 * 0.017453292f - 3.1415927f);
        final float a = rk.a(-n2 * 0.017453292f - 3.1415927f);
        final float n3 = -rk.b(-n * 0.017453292f);
        return new bhe((double)(a * n3), (double)rk.a(-n * 0.017453292f), (double)(b * n3));
    }
    
    public static class Rotations
    {
        public static float[] getIntaveRotations(final aed aed) {
            final bhe deltaPlayer = getDeltaPlayer(aed);
            final float n = (float)deltaPlayer.x;
            final float n2 = (float)deltaPlayer.y;
            final float n3 = (float)deltaPlayer.z;
            return new float[] { (float)(Math.toDegrees(Math.atan2(n3, n)) - 90.05000305175781), (float)(Math.toDegrees(-Math.atan2(n2, (float)Math.hypot(n, n3))) - 21.0 + Math.random() * 2.0) };
        }
        
        private static float updateRotation(final float n, final float n2, final float n3) {
            float g = rk.g(n2 - n);
            if (g > n3) {
                g = n3;
            }
            if (g < -n3) {
                g = -n3;
            }
            return n + g;
        }
        
        public static float[] getDefaultRotations(final vp vp) {
            final double n = vp.p - MathUtils.mc.h.p;
            final double n2 = vp.q - MathUtils.mc.h.q;
            final double n3 = vp.r - MathUtils.mc.h.r;
            return new float[] { (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793 - 90.0), rk.a((float)(-Math.atan2(n2, (float)Math.hypot(n, n3)) * 180.0 / 3.141592653589793), -90.0f, 90.0f) };
        }
        
        public static float[] getDefaultRotations(final aed aed) {
            final double n = aed.p - MathUtils.mc.h.p;
            final double n2 = aed.q - MathUtils.mc.h.q;
            final double n3 = aed.r - MathUtils.mc.h.r;
            return new float[] { (float)(Math.atan2(n3, n) * 180.0 / 3.141592653589793 - 90.0), rk.a((float)(-Math.atan2(n2, (float)Math.hypot(n, n3)) * 180.0 / 3.141592653589793), -90.0f, 90.0f) };
        }
        
        public static float[] getNexusRotations(final aed aed) {
            final bhe deltaPlayer = getDeltaPlayer(aed);
            final float n = (float)deltaPlayer.x;
            final float n2 = (float)deltaPlayer.y;
            final float n3 = (float)deltaPlayer.z;
            return new float[] { (float)(Math.toDegrees(Math.atan2(n3, n)) - 89.5), (float)(Math.toDegrees(-Math.atan2(n2, (float)Math.hypot(n, n3))) - 17.0 + Math.random()) };
        }
        
        public static float[] getRotations(final vp vp, final float n) {
            return new float[] { updateRotation(MathUtils.mc.h.v, getDefaultRotations((aed)vp)[0], n), updateRotation(MathUtils.mc.h.w, getDefaultRotations((aed)vp)[1], n) };
        }
        
        public static float[] getDefaultRotations(final float n, final float n2, final float n3) {
            final double n4 = n - MathUtils.mc.h.p;
            final double n5 = n2 - MathUtils.mc.h.q;
            final double n6 = n3 - MathUtils.mc.h.r;
            return new float[] { (float)Math.toDegrees(Math.atan2(n6, n4) - 90.0), rk.a((float)Math.toDegrees(-Math.atan2(n5, (float)Math.hypot(n4, n6))), -90.0f, 90.0f) };
        }
        
        public static float[] getMatrixRotations(final aed aed) {
            final float n = (float)(aed.p - MathUtils.mc.h.p);
            final float n2 = (float)(aed.q - (MathUtils.mc.h.q + MathUtils.mc.h.by()));
            final float n3 = (float)(aed.r - MathUtils.mc.h.r);
            return new float[] { (float)(Math.toDegrees(Math.atan2(n3, n)) - 90.0 + 0.7853981633974483 * Math.sin((double)System.currentTimeMillis())), (float)(Math.toDegrees(-Math.atan2(n2, (float)Math.hypot(n, n3))) - 20.0 + (0.7853981633974483 + Math.sin((double)System.currentTimeMillis()))) };
        }
        
        public static float getGCD() {
            final float n = MathUtils.mc.t.c * 0.6f + 0.2f;
            return n * n * n * 1.2f;
        }
        
        public static bhe getDeltaPlayer(final aed aed) {
            if (KillAura.resolver.enabled) {
                return new bhe((double)((float)(aed.M + (aed.p - aed.M) * MathUtils.mc.aj()) - (float)(MathUtils.mc.h.M + (MathUtils.mc.h.p - MathUtils.mc.h.M) * MathUtils.mc.aj())), (double)((float)(aed.N + (aed.q - aed.N) * MathUtils.mc.aj()) - ((float)(MathUtils.mc.h.N + (MathUtils.mc.h.q - MathUtils.mc.h.N) * MathUtils.mc.aj()) + MathUtils.mc.h.by() + 0.13f)), (double)((float)(aed.O + (aed.r - aed.O) * MathUtils.mc.aj()) - (float)(MathUtils.mc.h.O + (MathUtils.mc.h.r - MathUtils.mc.h.O) * MathUtils.mc.aj())));
            }
            return new bhe((double)(float)(aed.p - MathUtils.mc.h.p), (double)(float)(aed.q - (MathUtils.mc.h.q + MathUtils.mc.h.by())), (double)(float)(aed.r - MathUtils.mc.h.r));
        }
        
        public static float[] getAACRotations(final aed aed) {
            final bhe deltaPlayer = getDeltaPlayer(aed);
            final float n = (float)deltaPlayer.x;
            final float n2 = (float)deltaPlayer.y;
            final float n3 = (float)deltaPlayer.z;
            return new float[] { rk.g((float)Math.toDegrees(Math.atan2(n3, n)) - 90.0f), (float)rk.g((float)(-Math.toDegrees(Math.atan2(n2, Math.hypot(n, n3)))) - 15.0f + Math.random() * 4.0) };
        }
        
        public static float[] getDefaultRotations(final double n, final double n2, final double n3) {
            final double n4 = n - MathUtils.mc.h.p;
            final double n5 = n2 - MathUtils.mc.h.q;
            final double n6 = n3 - MathUtils.mc.h.r;
            return new float[] { (float)Math.toDegrees(Math.atan2(n6, n4) - 90.0), rk.a((float)Math.toDegrees(-Math.atan2(n5, (float)Math.hypot(n4, n6))), -90.0f, 90.0f) };
        }
        
        public static float getYawByMotion() {
            return -(float)Math.toDegrees(Math.atan2(MathUtils.mc.h.s, MathUtils.mc.h.u));
        }
        
        public static float[] getSunriseRotations(final aed aed) {
            final bhe deltaPlayer = getDeltaPlayer(aed);
            final float n = (float)deltaPlayer.x;
            final float n2 = (float)deltaPlayer.y;
            final float n3 = (float)deltaPlayer.z;
            return new float[] { (float)(Math.toDegrees(Math.atan2(n3, n)) - 90.0), (float)(Math.toDegrees(-Math.atan2(n2, (float)Math.hypot(n, n3))) - 20.0) };
        }
        
        public static float[] getMatrix2Rotations(final aed aed) {
            final bhe deltaPlayer = getDeltaPlayer(aed);
            final float n = (float)deltaPlayer.x;
            final float n2 = (float)deltaPlayer.y + aed.by() + 0.085231f;
            final float n3 = (float)deltaPlayer.z;
            return new float[] { (float)(Math.toDegrees(Math.atan2(n3, n)) - 90.0 + MathUtils.nextFloat(-2.0f, 2.0f)) * 1.0001f, (float)Math.toDegrees(-Math.atan2(n2, (float)Math.hypot(n, n3))) };
        }
    }
}
