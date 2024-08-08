package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.yaw.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class Yaw extends Ea
{
    public Yaw() {
        super("Yaw", new String[] { "Yaw", "RotationYaw", "Pitch" }, true, -9230451, Category.MOVEMENT);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Yaw.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Yaw.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Yaw.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Yaw.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Yaw.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Yaw.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Yaw.D;
    }
}
