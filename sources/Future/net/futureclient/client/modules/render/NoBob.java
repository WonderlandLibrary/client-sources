package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.nobob.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class NoBob extends Ea
{
    public NoBob() {
        super("NoBob", new String[] { "NoBob" }, true, -10399265, Category.RENDER);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return NoBob.D;
    }
}
