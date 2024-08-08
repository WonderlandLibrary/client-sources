package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.parkour.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class Parkour extends Ea
{
    public Parkour() {
        super("Parkour", new String[] { "Parkour", "EdgeJump", "Parkourmaster", "Parkuur", "Park" }, true, -12275058, Category.MOVEMENT);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Parkour.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Parkour.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Parkour.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Parkour.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Parkour.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Parkour.D;
    }
}
