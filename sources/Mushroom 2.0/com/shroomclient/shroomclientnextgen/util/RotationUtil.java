package com.shroomclient.shroomclientnextgen.util;

import com.shroomclient.shroomclientnextgen.mixin.EntityAccessor;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.CorrectGCD;
import net.minecraft.entity.Entity;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec2f;
import net.minecraft.util.math.Vec3d;

public class RotationUtil {

    static Rotation[] rotsNowYaw = null;
    static Rotation[] rotsNowPitch = null;
    static int rotTicksYaw = 0;
    static int rotTicksPitch = 0;

    static Rotation[] r1801 = {
        new RotationUtil.Rotation(2.1000001f, 2.3999023f),
        new RotationUtil.Rotation(3.300002f, 35.24991f),
        new RotationUtil.Rotation(2.5500016f, 71.249664f),
        new RotationUtil.Rotation(0.3000002f, 55.05005f),
        new RotationUtil.Rotation(0.1500001f, 15.599976f),
    };
    static Rotation[] r1802 = {
        new RotationUtil.Rotation(0.44999993f, 2.849884f),
        new RotationUtil.Rotation(0.5999999f, 43.05005f),
        new RotationUtil.Rotation(0.90000045f, 87.74979f),
    };
    static Rotation[] r1803 = {
        new RotationUtil.Rotation(1.2000003f, 17.249908f),
        new RotationUtil.Rotation(2.5500004f, 80.69974f),
        new RotationUtil.Rotation(2.1000004f, 64.49997f),
    };
    static Rotation[] r1804 = {
        new RotationUtil.Rotation(2.1000006f, 2.2499084f),
        new RotationUtil.Rotation(1.6500003f, 43.5f),
        new RotationUtil.Rotation(1.3500001f, 76.2001f),
        new RotationUtil.Rotation(1.500001f, 48.600098f),
    };
    static Rotation[] r1501 = {
        new RotationUtil.Rotation(3.450602f, 0.30004883f),
        new RotationUtil.Rotation(1.650001f, 23.700317f),
        new RotationUtil.Rotation(5.0999966f, 67.04907f),
    };
    static Rotation[] r1502 = {
        new RotationUtil.Rotation(7.199998f, 3.1502686f),
        new RotationUtil.Rotation(1.3500009f, 54.749756f),
        new RotationUtil.Rotation(2.7000012f, 64.34973f),
    };
    static Rotation[] r901 = {
        new RotationUtil.Rotation(0.60000026f, 18.300049f),
        new RotationUtil.Rotation(4.950003f, 46.20111f),
        new RotationUtil.Rotation(2.2499962f, 31.349976f),
    };
    static Rotation[] r601 = {
        new RotationUtil.Rotation(0.0f, 2.7004395f),
        new RotationUtil.Rotation(0.7500005f, 29.55011f),
        new RotationUtil.Rotation(4.4999976f, 41.250122f),
    };
    static Rotation[] r602 = {
        new RotationUtil.Rotation(3.1500015f, 13.800049f),
        new RotationUtil.Rotation(3.750002f, 39.450134f),
    };
    static Rotation[] r301 = {
        new RotationUtil.Rotation(0.60000014f, 4.5006104f),
        new RotationUtil.Rotation(0.1500001f, 16.950134f),
        new RotationUtil.Rotation(0.4500003f, 13.050293f),
    };
    static Rotation[] r302 = {
        new RotationUtil.Rotation(7.800002f, 17.100037f),
        new RotationUtil.Rotation(0.1500001f, 13.349731f),
    };
    static Rotation[] r101 = {
        new RotationUtil.Rotation(1.6500007f, 2.2503662f),
        new RotationUtil.Rotation(0.4500003f, 4.2006836f),
        new RotationUtil.Rotation(0.3000002f, 2.8504639f),
    };
    static Rotation[] r102 = {
        new RotationUtil.Rotation(0.3000002f, 0.7501221f),
        new RotationUtil.Rotation(0.9000006f, 6.0009766f),
    };
    static Rotation[] r103 = {
        new RotationUtil.Rotation(0.7500005f, 0.15002441f),
        new RotationUtil.Rotation(0.3000002f, 0.45007324f),
    };
    static Rotation[][] r180 = { r1801, r1802, r1803, r1804 };
    static Rotation[][] r150 = { r1501, r1502 };
    static Rotation[][] r90 = { r901 };
    static Rotation[][] r60 = { r601, r602 };
    static Rotation[][] r30 = { r301, r302 };
    static Rotation[][] r10 = { r101, r102, r103 };

    private static double wrapAngleTo180(double angle) {
        return angle - Math.floor(angle / 360 + 0.5) * 360;
    }

    public static Rotation getRotation(Vec3d from, Vec3d to) {
        double diffX = to.x - from.x;
        double diffY = to.y - from.y;
        double diffZ = to.z - from.z;
        double dist = Math.sqrt(diffX * diffX + diffZ * diffZ);

        float pitch = (float) -Math.atan2(dist, diffY);
        float yaw = (float) Math.atan2(diffZ, diffX);
        pitch = (float) wrapAngleTo180(((pitch * 180F) / Math.PI + 90) * -1);
        yaw = (float) wrapAngleTo180(((yaw * 180) / Math.PI) - 90);

        return new Rotation(pitch, yaw);
    }

    public static Rotation getRotation(Vec3d vec3) {
        return getRotation(
            new Vec3d(
                C.p().getX(),
                C.p().getY() + C.p().getEyeHeight(C.p().getPose()),
                C.p().getZ()
            ),
            vec3
        );
    }

    public static Rotation getRotation(BlockPos block) {
        return getRotation(
            new Vec3d(
                block.getX() + 0.5,
                block.getY() + 0.5,
                block.getZ() + 0.5
            )
        );
    }

    public static Vec2f getRotation(double x, double y, double z) {
        Rotation rot = getRotation(new Vec3d(x, y, z));
        return new Vec2f(rot.pitch, rot.yaw);
    }

    public static Vec2f getRotation(Entity entity) {
        Rotation rot = getRotation(
            new Vec3d(entity.getX(), entity.getEyeY(), entity.getZ())
        );
        return new Vec2f(rot.pitch, rot.yaw);
    }

    public static double getRotationDifference(Vec2f a, Vec2f b) {
        return Math.hypot(
            getAngleDifference(a.y, b.y),
            getAngleDifference(a.x, b.x)
        );
    }

    // wurst ahh rots
    public static void snapTo(Rotation rot, boolean packet) {
        ((EntityAccessor) C.p()).setPitch(rot.pitch);
        ((EntityAccessor) C.p()).setYaw(rot.yaw);
        if (packet) PacketUtil.sendPacket(
            new PlayerMoveC2SPacket.LookAndOnGround(
                rot.yaw,
                rot.pitch,
                C.p().isOnGround()
            ),
            false
        );
    }

    public static void snapTo(BlockPos bp, boolean packet) {
        snapTo(getRotation(bp), packet);
    }

    public static float yawTo360(float yaw) {
        return yaw + 180;
    }

    public static float wrapAngleTo180_float(float value) {
        value %= 360.0F;
        if (value >= 180.0F) {
            value -= 360.0F;
        }

        if (value < -180.0F) {
            value += 360.0F;
        }

        return value;
    }

    public static float _360ToYaw(float _360) {
        return wrapAngleTo180_float(_360);
    }

    public static Rotation getLimitedRotation(
        Rotation currentRotation,
        Rotation targetRotation,
        float turnSpeed
    ) {
        Vec2f rot = getLimitedRotation(
            new Vec2f(currentRotation.pitch, currentRotation.yaw),
            new Vec2f(targetRotation.pitch, targetRotation.yaw),
            turnSpeed
        );
        return new Rotation(rot.x, rot.y);
    }

    public static Vec2f getLimitedRotation(
        Vec2f currentRotation,
        Vec2f targetRotation,
        float turnSpeed
    ) {
        float pitchToReturn =
            currentRotation.x +
            MathHelper.clamp(
                getAngleDifference(targetRotation.x, currentRotation.x),
                -turnSpeed,
                turnSpeed
            );
        float yawToReturn =
            currentRotation.y +
            MathHelper.clamp(
                getAngleDifference(targetRotation.y, currentRotation.y),
                -turnSpeed,
                turnSpeed
            );

        if (ModuleManager.isEnabled(CorrectGCD.class)) {
            // make int out of number, then multi again, should work idk
            pitchToReturn = ((int) (pitchToReturn / CorrectGCD.GCDnumber)) *
            CorrectGCD.GCDnumber;
            yawToReturn = ((int) (yawToReturn / CorrectGCD.GCDnumber)) *
            CorrectGCD.GCDnumber;
        }

        return new Vec2f(pitchToReturn, yawToReturn);
    }

    public static Rotation getSmoothRotation(
        Rotation current,
        Rotation target,
        float smooth
    ) {
        Vec2f rot = getSmoothRotation(
            new Vec2f(current.pitch, current.yaw),
            new Vec2f(target.pitch, target.yaw),
            smooth
        );
        return new Rotation(rot.x, rot.y);
    }

    public static Vec2f getSmoothRotation(
        Vec2f current,
        Vec2f target,
        float smooth
    ) {
        float pitchToReturn = wrapAngleTo180_float(
            current.x + (target.x - current.x) / smooth
        );
        float yawToReturn = wrapAngleTo180_float(
            current.y + (target.y - current.y) / smooth
        );

        if (ModuleManager.isEnabled(CorrectGCD.class)) {
            // make int out of number, then multi again, should work idk
            pitchToReturn = ((int) (pitchToReturn / CorrectGCD.GCDnumber)) *
            CorrectGCD.GCDnumber;
            yawToReturn = ((int) (yawToReturn / CorrectGCD.GCDnumber)) *
            CorrectGCD.GCDnumber;
        }

        return new Vec2f(pitchToReturn, yawToReturn);
    }

    public static Rotation getLegitRotation(Rotation current, Rotation target) {
        Vec2f rot = getLegitRotation(
            new Vec2f(current.pitch, current.yaw),
            new Vec2f(target.pitch, target.yaw)
        );
        return new Rotation(rot.x, rot.y);
    }

    // awesome code so real $$$$
    public static Vec2f getLegitRotation(Vec2f current, Vec2f target) {
        Vec2f rotLimited = getLimitedRotation(
            new Vec2f(current.x, current.y),
            new Vec2f(target.x, target.y),
            (float) (Math.random() * 3f)
        );

        float direction = rotLimited.y - current.y;

        // create yaw and pitch amounts
        float yaw;
        float pitch;

        // picks which yaw we need
        if (rotsNowYaw == null || rotTicksYaw > rotsNowYaw.length - 1) {
            rotsNowYaw = getLegitYawRotationFromAngle(
                current,
                target,
                direction
            );
            rotTicksYaw = 0;
        }

        if (rotsNowPitch == null || rotTicksPitch > rotsNowPitch.length - 1) {
            rotsNowPitch = getLegitPitchRotationFromAngle(
                current,
                target,
                direction
            );
            rotTicksPitch = 0;
        }

        // does the rotation
        yaw = rotsNowYaw[rotTicksYaw].yaw;
        yaw += rotTicksYaw;
        yaw *= direction;

        pitch = rotsNowPitch[rotTicksPitch].yaw / 2;
        if (target.x < wrapAngleTo180(current.x)) pitch *= -0.5f;

        //increment ticks + return rotations
        rotTicksYaw++;
        rotTicksPitch++;

        float pitchToReturn = Math.max(Math.min(current.x + pitch, 90), -90);
        float yawToReturn = current.y + yaw;

        if (ModuleManager.isEnabled(CorrectGCD.class)) {
            // make int out of number, then multi again, should work idk
            pitchToReturn = ((int) (pitchToReturn / CorrectGCD.GCDnumber)) *
            CorrectGCD.GCDnumber;
            yawToReturn = ((int) (yawToReturn / CorrectGCD.GCDnumber)) *
            CorrectGCD.GCDnumber;
        }

        return new Vec2f(pitchToReturn, yawToReturn);
    }

    public static Rotation[] getLegitYawRotationFromAngle(
        Vec2f current,
        Vec2f target,
        float direction
    ) {
        float angleDif = getAngleDifference(current.y, target.y);
        if (angleDif < 0) angleDif *= -1;

        if (angleDif > 150) return r180[(int) (Math.random() * r180.length)];
        if (angleDif > 90) return r150[(int) (Math.random() * r150.length)];
        if (angleDif > 60) return r90[(int) (Math.random() * r90.length)];
        if (angleDif > 30) return r60[(int) (Math.random() * r60.length)];
        if (angleDif > 10) return r30[(int) (Math.random() * r30.length)];
        return r10[(int) (Math.random() * r10.length)];
    }

    public static Rotation[] getLegitPitchRotationFromAngle(
        Vec2f current,
        Vec2f target,
        float direction
    ) {
        float angleDif = getAngleDifference(current.x, target.x);
        if (angleDif < 0) angleDif *= -1;

        if (angleDif > 150) return r180[(int) (Math.random() * r180.length)];
        if (angleDif > 90) return r150[(int) (Math.random() * r150.length)];
        if (angleDif > 60) return r90[(int) (Math.random() * r90.length)];
        if (angleDif > 30) return r60[(int) (Math.random() * r60.length)];
        if (angleDif > 10) return r30[(int) (Math.random() * r30.length)];
        return r10[(int) (Math.random() * r10.length)];
    }

    public static float getAngleDifference(float a, float b) {
        return ((((a - b) % 360.0f) + 540.0f) % 360.0f) - 180.0f;
    }

    public static class Rotation {

        public float pitch;
        public float yaw;

        public Rotation(float pitch, float yaw) {
            this.pitch = pitch;
            this.yaw = yaw;
        }
    }
}
