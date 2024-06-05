package me.superskidder.lune.events;

import me.superskidder.lune.manager.event.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

/**
 * @description:
 * @author: Qian_Xia
 * @create: 2020-08-24 19:00
 **/
public class EventMove extends Event {
    public static double x;
    public static double y;
    public static double z;
    private boolean isPre;
    private Packet packet;
    private boolean outgoing;

    public EventMove(double x, double y, double z, Packet packet, boolean outgoing) {
        EventMove.x = x;
        EventMove.y = y;
        EventMove.z = z;
        this.packet = packet;
        this.outgoing = outgoing;
        this.isPre = true;
    }

    public Packet getPacket() {
        return this.packet;
    }

    public double getX() {
        return x;
    }


    public static void setX(double x) {
        EventMove.x = x;
    }

    public double getY() {
        return y;
    }

    public static void setY(double y) {
        EventMove.y = y;
    }

    public double getZ() {
        return z;
    }

    public static void setZ(double z) {
        EventMove.z = z;
    }

    public boolean isPre() {
        return this.isPre;
    }

    public boolean isPost() {
        return !this.isPre;
    }

    public static void setMoveSpeed(double speed) {
        Minecraft mc = Minecraft.getMinecraft();
        double forward = mc.thePlayer.movementInput.moveForward;
        double strafe = mc.thePlayer.movementInput.moveStrafe;
        float yaw = mc.thePlayer.rotationYaw;
        if (forward == 0.0 && strafe == 0.0) {
            setX(0.0);
            setZ(0.0);
        } else {
            if (forward != 0.0) {
                if (strafe > 0.0) {
                    yaw += (float) (forward > 0.0 ? -45 : 45);
                } else if (strafe < 0.0) {
                    yaw += (float) (forward > 0.0 ? 45 : -45);
                }
                strafe = 0.0;
                forward = forward > 0.0 ? 1.0 : -1.0;
            }
            setX(forward * speed * Math.cos(Math.toRadians(yaw + 90.0f))
                    + strafe * speed * Math.sin(Math.toRadians(yaw + 90.0f)));
            setZ(forward * speed * Math.sin(Math.toRadians(yaw + 90.0f))
                    - strafe * speed * Math.cos(Math.toRadians(yaw + 90.0f)));
        }
    }
}
