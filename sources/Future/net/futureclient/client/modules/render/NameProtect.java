package net.futureclient.client.modules.render;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.render.nameprotect.Listener2;
import net.futureclient.client.modules.render.nameprotect.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class NameProtect extends Ea
{
    public NameProtect() {
        super("NameProtect", new String[] { "NameProtect", "Naemprotect", "name", "protect", "np" }, false, 290805247, Category.RENDER);
        this.M(new n[] { (n)new Listener1(this), (n)new Listener2(this) });
    }
    
    public static Minecraft getMinecraft() {
        return NameProtect.D;
    }
}
