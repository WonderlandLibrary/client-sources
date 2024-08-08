package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.heaven.Listener2;
import net.futureclient.client.modules.miscellaneous.heaven.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class Heaven extends Ea
{
    private NumberValue speed;
    
    public Heaven() {
        super("Heaven", new String[] { "Heaven", "GoHeaven", "AutoHeaven", "Blessing" }, true, -10887189, Category.MISCELLANEOUS);
        this.speed = new NumberValue(0.75f, -10.0f, 10.0f, 1.273197475E-314, new String[] { "Speed", "Sped" });
        this.M(new Value[] { this.speed });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Heaven.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Heaven.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Heaven.D;
    }
    
    public static NumberValue M(final Heaven heaven) {
        return heaven.speed;
    }
}
