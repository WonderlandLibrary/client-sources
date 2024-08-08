package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.fastswim.Listener2;
import net.futureclient.client.modules.movement.fastswim.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.mc;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class FastSwim extends Ea
{
    private int a;
    private boolean D;
    private R<mc.rc> mode;
    
    public FastSwim() {
        super("FastSwim", new String[] { "FastSwim", "fastswim", "swim" }, true, 52479, Category.MOVEMENT);
        this.mode = new R<mc.rc>(mc.rc.D, new String[] { "Mode", "m" });
        this.M(new Value[] { this.mode });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft() {
        return FastSwim.D;
    }
    
    public static int M(final FastSwim fastSwim, final int a) {
        return fastSwim.a = a;
    }
    
    public static boolean M(final FastSwim fastSwim) {
        return fastSwim.D;
    }
    
    public static R M(final FastSwim fastSwim) {
        return fastSwim.mode;
    }
    
    public static boolean M(final FastSwim fastSwim, final boolean d) {
        return fastSwim.D = d;
    }
    
    public static int M(final FastSwim fastSwim) {
        return fastSwim.a;
    }
    
    public static Minecraft getMinecraft1() {
        return FastSwim.D;
    }
}
