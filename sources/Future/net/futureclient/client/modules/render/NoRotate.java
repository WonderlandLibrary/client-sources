package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.norotate.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class NoRotate extends Ea
{
    public NoRotate() {
        super("NoRotate", new String[] { "NoRotate", "Rotations", "Rotate" }, false, -12234273, Category.RENDER);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return NoRotate.D;
    }
    
    public static Minecraft getMinecraft1() {
        return NoRotate.D;
    }
    
    public static Minecraft getMinecraft2() {
        return NoRotate.D;
    }
    
    public static Minecraft getMinecraft3() {
        return NoRotate.D;
    }
}
