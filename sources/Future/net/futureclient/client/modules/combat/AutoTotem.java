package net.futureclient.client.modules.combat;

import net.minecraft.util.EnumHand;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.futureclient.client.modules.combat.autototem.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Ea;

public class AutoTotem extends Ea
{
    private Timer a;
    public NumberValue delay;
    public boolean k;
    
    public static Minecraft getMinecraft() {
        return AutoTotem.D;
    }
    
    public AutoTotem() {
        super("AutoTotem", new String[] { "AutoTotem", "Totem", "AutoRevive" }, true, -4598640, Category.COMBAT);
        this.k = false;
        this.a = new Timer();
        this.delay = new NumberValue(0.0f, 0.0f, 2.0f, 5.941588215E-315, new String[] { "Delay", "Deley", "Del", "D" });
        this.M(new Value[] { this.delay });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft3() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AutoTotem.D;
    }
    
    public void b() {
        final boolean k = false;
        super.b();
        this.k = k;
    }
    
    public static Minecraft getMinecraft9() {
        return AutoTotem.D;
    }
    
    public int e() {
        int n = 0;
        final ItemStack[] array;
        final int length = (array = (ItemStack[])AutoTotem.D.player.inventory.mainInventory.toArray((Object[])new ItemStack[n])).length;
        int i = 0;
        int n2 = 0;
        while (i < length) {
            final ItemStack itemStack;
            if ((itemStack = array[n2]) != null && itemStack.getItem() == Items.TOTEM_OF_UNDYING) {
                ++n;
            }
            i = ++n2;
        }
        if (AutoTotem.D.player.getHeldItem(EnumHand.OFF_HAND).getItem() == Items.TOTEM_OF_UNDYING) {
            ++n;
        }
        return n;
    }
    
    public static Minecraft getMinecraft10() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft11() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft12() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AutoTotem.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AutoTotem.D;
    }
    
    public static Timer M(final AutoTotem autoTotem) {
        return autoTotem.a;
    }
    
    public static Minecraft getMinecraft15() {
        return AutoTotem.D;
    }
}
