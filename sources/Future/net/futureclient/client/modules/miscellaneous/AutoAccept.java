package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.miscellaneous.autoaccept.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Zg;
import net.futureclient.client.Ea;

public class AutoAccept extends Ea
{
    private Zg D;
    private NumberValue delay;
    
    public AutoAccept() {
        super("AutoAccept", new String[] { "AutoAccept", "accept", "tpaccept", "tpa" }, true, -11184726, Category.MISCELLANEOUS);
        this.delay = new NumberValue(3.0f, 0.0f, 10.0f, 1.273197475E-314, new String[] { "Delay", "Del", "d" });
        final int n = 1;
        this.D = new Zg();
        final Value[] array = new Value[n];
        array[0] = this.delay;
        this.M(array);
        this.M(new n[] { (n)new Listener1(this) });
    }
    
    public static Zg M(final AutoAccept autoAccept) {
        return autoAccept.D;
    }
    
    public static Minecraft getMinecraft() {
        return AutoAccept.D;
    }
    
    public static NumberValue M(final AutoAccept autoAccept) {
        return autoAccept.delay;
    }
}
