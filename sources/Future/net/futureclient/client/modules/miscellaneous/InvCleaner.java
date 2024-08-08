package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import net.futureclient.client.IA;
import net.futureclient.client.modules.miscellaneous.invcleaner.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import java.util.ArrayList;
import net.futureclient.client.Ea;

public class InvCleaner extends Ea
{
    public ArrayList<String> d;
    private Value<Boolean> cleanHotbar;
    private Timer D;
    private NumberValue delay;
    
    public InvCleaner() {
        super("InvCleaner", new String[] { "InvCleaner", "InvClean", "InventoryClean", "InventoryCleaner", "InventoryCleanerList" }, true, -10890357, Category.MISCELLANEOUS);
        this.delay = new NumberValue(0.05f, 0.0f, 1.0f, 5.941588215E-315, new String[] { "Delay", "Del", "D", "Speed", "Sped" });
        this.cleanHotbar = new Value<Boolean>(true, new String[] { "CleanHotbar", "Clean", "Hotbar", "HotbarClean", "Cleaner", "ch", "hc" });
        final int n = 2;
        this.D = new Timer();
        this.d = new ArrayList<String>();
        final Value[] array = new Value[n];
        array[0] = this.delay;
        array[1] = this.cleanHotbar;
        this.M(array);
        this.M(new n[] { (n)new Listener1(this) });
        new IA(this, "inventory_cleaner.txt");
    }
    
    public static Minecraft getMinecraft() {
        return InvCleaner.D;
    }
    
    public static Minecraft getMinecraft1() {
        return InvCleaner.D;
    }
    
    public static Minecraft getMinecraft2() {
        return InvCleaner.D;
    }
    
    public static Minecraft getMinecraft3() {
        return InvCleaner.D;
    }
    
    public static Value M(final InvCleaner invCleaner) {
        return invCleaner.cleanHotbar;
    }
    
    public static Minecraft getMinecraft4() {
        return InvCleaner.D;
    }
    
    public static NumberValue M(final InvCleaner invCleaner) {
        return invCleaner.delay;
    }
    
    public static Timer M(final InvCleaner invCleaner) {
        return invCleaner.D;
    }
}
