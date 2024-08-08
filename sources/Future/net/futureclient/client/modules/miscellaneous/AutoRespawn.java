package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.autorespawn.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class AutoRespawn extends Ea
{
    public AutoRespawn() {
        super("AutoRespawn", new String[] { "AutoRespawn", "NoDeathScreen" }, true, -15641191, Category.MISCELLANEOUS);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return AutoRespawn.D;
    }
    
    public static Minecraft getMinecraft1() {
        return AutoRespawn.D;
    }
}
