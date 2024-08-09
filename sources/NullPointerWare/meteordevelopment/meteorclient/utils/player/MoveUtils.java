package meteordevelopment.meteorclient.utils.player;

import meteordevelopment.meteorclient.events.entity.player.PlayerMoveEvent;
import meteordevelopment.meteorclient.mixin.AccessorMinecraftClient;
import meteordevelopment.meteorclient.mixin.AccessorRenderTickCounter;
import meteordevelopment.meteorclient.mixininterface.IVec3d;
import meteordevelopment.meteorclient.systems.Globals;
import meteordevelopment.meteorclient.utils.Utils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketCallbacks;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.Objects;

import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static meteordevelopment.meteorclient.MeteorClient.mc;

public final class MoveUtils {

    static PlayerEntity player = mc.player;

    public static boolean isMoving()
    {
        return player.forwardSpeed != 0 || player.sidewaysSpeed != 0;
    }

    public static double motionY(final double motionY) {
        final Vec3d vec3d = mc.player.getVelocity();
        mc.player.setVelocity(vec3d.x, motionY, vec3d.z);
        return motionY;
    }

    public static void setHVelocity(double x, double z) {
        mc.player.setVelocity(x, mc.player.getVelocity().getY(), z);
    }

    public static void setYVelocity(double y) {
        Vec3d velocity = mc.player.getVelocity();
        mc.player.setVelocity(velocity.x, y, velocity.z);
    }

    public static double getBaseMoveSpeed() {
        double baseSpeed = 0.272;
        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            final int amplifier = Objects.requireNonNull(mc.player.getStatusEffect(StatusEffects.SPEED)).getAmplifier();
            baseSpeed *= 1.0 + (0.2 * amplifier);
        }
        return baseSpeed;
    }

    public static void fullFlightMove(PlayerMoveEvent event, double speed, boolean verticalSpeedMatch) {
        if (PlayerUtils.isMoving()) {
            double dir = getDir();

            double xDir = cos(Math.toRadians(dir + 90));
            double zDir = sin(Math.toRadians(dir + 90));

            ((IVec3d) event.movement).setXZ(xDir * speed, zDir * speed);
        } else {
            ((IVec3d) event.movement).setXZ(0, 0);
        }

        float ySpeed = 0;

        if (mc.options.jumpKey.isPressed())
            ySpeed += speed;
        if (mc.options.sneakKey.isPressed())
            ySpeed -= speed;
        ((IVec3d) event.movement).setY(verticalSpeedMatch ? ySpeed : ySpeed / 2);
    }

    private static double getDir() {
        double dir = 0;

        if (Utils.canUpdate()) {
            dir = mc.player.getYaw() + ((mc.player.forwardSpeed < 0) ? 180 : 0);

            if (mc.player.sidewaysSpeed > 0) {
                dir += -90F * ((mc.player.forwardSpeed < 0) ? -0.5F : ((mc.player.forwardSpeed > 0) ? 0.5F : 1F));
            } else if (mc.player.sidewaysSpeed < 0) {
                dir += 90F * ((mc.player.forwardSpeed < 0) ? -0.5F : ((mc.player.forwardSpeed > 0) ? 0.5F : 1F));
            }
        }
        return dir;
    }

    public static double getBaseMoveEventSpeed() {
        double defaultSpeed = 0.2873;

        if (mc.player.hasStatusEffect(StatusEffects.SPEED)) {
            final int amplifier = mc.player.getStatusEffect(StatusEffects.SPEED).getAmplifier();
            defaultSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }

        if (mc.player.hasStatusEffect(StatusEffects.SLOWNESS)) {
            final int amplifier = mc.player.getStatusEffect(StatusEffects.SLOWNESS).getAmplifier();
            defaultSpeed /= 1.0 + 0.2 * (amplifier + 1);
        }
        return defaultSpeed;
    }


    public static double getSpeedBoost(double times) {
        double boost = (MoveUtils.getBaseMoveSpeed() - 0.2875) * times;
        if (0 > boost)
            boost = 0;

        return boost;
    }

    public static double getSpeed()
    {
        return Math.hypot(player.getVelocity().getX(), player.getVelocity().getZ());
    }

    public static void setMotion(final double speed) {
        final Vec3d playerVel = player.getVelocity();

        double moveForward = player.forwardSpeed;
        double moveSideways = player.sidewaysSpeed;

        float yaw = player.getYaw(mc.getTickDelta());

        if (moveForward == 0 && moveSideways == 0) {
            player.setVelocity(playerVel.subtract(0, playerVel.y, 0));
        }
        else
        {

            if (moveForward != 0) {
                yaw += (float) (moveSideways > 0 ? moveForward > 0 ? -45.0D : 45.0D : moveSideways < 0 ? moveForward > 0 ? 45.0D : -45.0D : 0);
                moveSideways = 0;
                moveForward = moveForward > 0 ? 1 : moveForward < 0 ? -1 : 0;
            }
            final double cos = cos(Math.toRadians(yaw + 90.0D)), sin = sin(Math.toRadians(yaw + 90.0D));
            final double speedX = moveForward * speed * cos + moveSideways * speed * sin, speedZ = moveForward * speed * sin - moveSideways * speed * cos;

            player.setVelocity(speedX, playerVel.y, speedZ);
        }
    }

    public static void setMotion2(final double speed) {
        final Vec3d playerVel = player.getVelocity();

        double moveForward = player.forwardSpeed;
        double moveSideways = player.sidewaysSpeed;

        float yaw = player.getYaw(mc.getTickDelta());

        if (moveForward == 0 && moveSideways == 0)
        {
            player.setVelocity(playerVel.subtract(0, playerVel.y, 0));
        }
        else
        {

            if (moveForward != 0)
            {
                yaw += (float) (moveSideways > 0 ? moveForward > 0 ? -45.0D : 45.0D : moveSideways < 0 ? moveForward > 0 ? 45.0D : -45.0D : 0);
                moveSideways = 0;
                moveForward = moveForward > 0 ? 1 : moveForward < 0 ? -1 : 0;
            }
            final double cos = cos(Math.toRadians(yaw + 90.0D)), sin = sin(Math.toRadians(yaw + 90.0D));
            final double speedX = moveForward * speed * cos + moveSideways * speed * sin, speedZ = moveForward * speed * sin - moveSideways * speed * cos;

            player.setVelocity(speedX, playerVel.y, speedZ);
        }
    }

    public static void setMoveSpeed2(final PlayerMoveEvent event, final double speed, double strafe) {
        double forward = player.forwardSpeed;
        float yaw =  mc.player.getYaw();
        if (forward == 0.0 && strafe == 0.0) {
            if(event == null) {
                resetMotionXZ();
            } else {
                event.setX(0.0);
                event.setZ(0.0);
            }
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += ((forward > 0.0) ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += ((forward > 0.0) ? 45 : -45);
                }
                strafe = 0.0;
                if (forward > 0.0) {
                    forward = 1.0;
                } else if (forward < 0.0) {
                    forward = -1.0;
                }
            }
            double X = forward * speed * -sin(Math.toRadians(yaw)) + strafe * speed * cos(Math.toRadians(yaw));
            double Z = forward * speed * cos(Math.toRadians(yaw)) - strafe * speed * -sin(Math.toRadians(yaw));
            if(event == null) {
                mc.player.setVelocity(X, mc.player.getVelocity().getY(), Z);
            } else {
                event.setX(X);
                event.setZ(Z);
            }
        }
    }

    private static void sendNoEvent(Packet<?> packet) {
        Objects.requireNonNull(mc.getNetworkHandler()).getConnection().send(packet, (PacketCallbacks)null);
    }

    public static void damageCC(int amount) {
        final double dis = 0.06122453D, x = player.getX(), y = player.getY(), z = player.getZ();
        // final int amount = 49;
        int damagePackets;

        for (damagePackets = 0; damagePackets < amount; ++damagePackets) {
            sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(x, y + dis, z, false));
            sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, false));
        }
        sendNoEvent(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, true));
    }

    public static void damageNCP() {
        double x = mc.player.getX();
        double y = mc.player.getY();
        double z = mc.player.getZ();

        for (int i = 0; i < 4; i++) {
            for (double pos : Globals.jumpPositions) {
                sendPositionPacket(x, y + pos, z, false);
            }
            sendPositionPacket(x, y, z, false);
        }
        sendPositionPacket(x, y, z, true);
    }


    public static void VulcanBoost(final double Boost) {
        MinecraftClient client = MinecraftClient.getInstance();
        PlayerEntity player = client.player;
        if (player != null) {
            if (player.isTouchingWater()) {
                Vec3d lookVector = player.getRotationVector();

                double x = cos(Math.toRadians(player.getYaw() + 90.0F));
                double z = sin(Math.toRadians(player.getYaw() + 90.0F));

                Vec3d motionVector = new Vec3d(x, 0.3, z).normalize().multiply(Boost); // Set the desired velocity
                player.setVelocity(motionVector);
            }
        }
    }

    public static void sendPositionPacket(double x, double y, double z, boolean onGround) {
        if (mc.player.networkHandler == null) return;
        mc.player.networkHandler.sendPacket(new PlayerMoveC2SPacket.PositionAndOnGround(x, y, z, onGround));
    }

    public static double getDistanceToGround(final Entity entity) {
        final double playerX = mc.player.getX();
        final int playerHeight = (int) Math.floor(mc.player.getY());
        final double playerZ = mc.player.getZ();

        for (int height = playerHeight; height > 0; height--) {
            final BlockPos checkPosition = new BlockPos((int) playerX, height, (int) playerZ);

            //if (checkPosition.getY() <= -0) {
            //    ChatUtils.error("Player is over the void...");
            //    break;
            //}

            // Check if the block is solid
            if (!mc.world.isAir(checkPosition)) {
                return playerHeight - height;
            }
        }
        return 0;
    }

    public static boolean hasMovement() {
        final Vec3d playerMovement = mc.player.getVelocity();
        return playerMovement.getX() != 0 || playerMovement.getY() != 0 || playerMovement.getZ() != 0;
    }

    public static void resetMotionXZ() {
        mc.player.setVelocity(0, mc.player.getVelocity().getY(), 0);
    }

    public static void resetMotionXYZ() {
        mc.player.setVelocity(0, 0, 0);
    }

    public static void resetMotionXYZ(final PlayerMoveEvent e) {
        e.setX(0);
        e.setY(0);
        e.setY(0);
    }

    public static void setTimer(final float timer) {
        ((AccessorRenderTickCounter) ((AccessorMinecraftClient) mc).getRenderTickCounter()).setTickTime(50.0F / timer);
    }

    //MineMenClub Stuff

    private static double speed = 1.0; // Replace with your desired speed value
    public static double speed() {
        // For readibility, change pow to just ^
        return Math.sqrt(Math.pow(mc.player.getVelocity().getX(), 2) + Math.pow(mc.player.getVelocity().getZ(), 2));
    }

    public static void strafe(double speed) {
        strafe(speed, mc.player.getYaw());
    }

    public static void strafe(double speed, float yaw) {
        if (!PlayerUtils.isMoving()) {
            return;
        }

        double direction = getDirection(yaw);
        double motionX = -MathHelper.sin((float) direction) * speed; // Inverted direction
        double motionZ = MathHelper.cos((float) direction) * speed; // Inverted direction
        mc.player.setVelocity(new Vec3d(motionX, mc.player.getVelocity().getY(), motionZ));
    }

    public static Vec3d strafe(double speed, double strength, float yaw, Vec3d vec3d) {
        double prevX = vec3d.x * (1.0 - strength);
        double prevZ = vec3d.z * (1.0 - strength);
        double useSpeed = speed * strength;

        double angle = getDirection(yaw);
        return new Vec3d((-sin(angle) * useSpeed) + prevX, vec3d.y, (cos(angle) * useSpeed) + prevZ);
    }


    public static void strafe() {
        strafe(speed());
    }

    public static double getDirection() {
        return getDirection(mc.player.getYaw());
    }


    public static double getDirection(double yaw) {
        if (MinecraftClient.getInstance().player.input.movementForward < 0) {
            yaw += 180;
        }
        double forward = 1;
        if (MinecraftClient.getInstance().player.input.movementForward < 0) {
            forward = -0.5;
        } else if (MinecraftClient.getInstance().player.input.movementForward > 0) {
            forward = 0.5;
        }
        if (MinecraftClient.getInstance().player.input.movementSideways > 0) {
            yaw -= 90 * forward;
        }
        if (MinecraftClient.getInstance().player.input.movementSideways < 0) {
            yaw += 90 * forward;
        }
        return Math.toRadians(yaw);
    }
}
