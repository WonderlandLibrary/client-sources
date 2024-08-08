package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.middleclick.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class MiddleClick extends Ea
{
    public MiddleClick() {
        super("MiddleClick", new String[] { "MiddleClick", "MiddleClickFriends", "mcf", "middleclick" }, false, -15641089, Category.MISCELLANEOUS);
        this.M(new n[] { new Listener1(this) });
        this.M(true);
    }
    
    public static Minecraft getMinecraft() {
        return MiddleClick.D;
    }
    
    public static Minecraft getMinecraft1() {
        return MiddleClick.D;
    }
    
    public static Minecraft getMinecraft2() {
        return MiddleClick.D;
    }
    
    public static Minecraft getMinecraft3() {
        return MiddleClick.D;
    }
}
