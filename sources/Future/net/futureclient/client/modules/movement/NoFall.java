package net.futureclient.client.modules.movement;

import net.futureclient.client.modules.movement.nofall.Listener2;
import net.futureclient.client.modules.movement.nofall.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.minecraft.init.Items;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;
import net.minecraft.item.ItemStack;
import net.futureclient.client.Ic;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class NoFall extends Ea
{
    private R<Ic.zc> mode;
    private ItemStack D;
    private Timer k;
    
    public static Minecraft getMinecraft() {
        return NoFall.D;
    }
    
    public NoFall() {
        super("NoFall", new String[] { "NoFall", "0fall", "nf" }, true, -12727218, Category.MOVEMENT);
        this.mode = new R<Ic.zc>(Ic.zc.k, new String[] { "Mode", "Mod", "Type", "Method" });
        final int n = 1;
        this.D = new ItemStack(Items.WATER_BUCKET);
        this.k = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.mode;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft2() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft3() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft4() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft5() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft6() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft7() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft8() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft9() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft10() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft11() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft12() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft13() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft14() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft15() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft16() {
        return NoFall.D;
    }
    
    public static R M(final NoFall noFall) {
        return noFall.mode;
    }
    
    public static Timer M(final NoFall noFall) {
        return noFall.k;
    }
    
    public static Minecraft getMinecraft17() {
        return NoFall.D;
    }
    
    public static ItemStack M(final NoFall noFall) {
        return noFall.D;
    }
    
    public static Minecraft getMinecraft18() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft19() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft20() {
        return NoFall.D;
    }
    
    public static Minecraft getMinecraft21() {
        return NoFall.D;
    }
}
