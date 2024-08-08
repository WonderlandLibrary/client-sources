package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.Minecraft;
import java.util.ArrayList;
import net.futureclient.client.modules.miscellaneous.cheststealer.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.tB;
import net.futureclient.client.R;
import java.util.List;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class ChestStealer extends Ea
{
    private NumberValue delay;
    private Timer a;
    private static final List<String> D;
    private R<tB.Sd> mode;
    
    public ChestStealer() {
        super("ChestStealer", new String[] { "ChestStealer", "Steal", "Drop", "Chest", "Container" }, true, -357023, Category.MISCELLANEOUS);
        this.delay = new NumberValue(0.15f, 0.01f, 1.0f, 5.941588215E-315, new String[] { "Delay", "D" });
        this.mode = new R<tB.Sd>(tB.Sd.a, new String[] { "Mode", "m" });
        final int n = 2;
        this.a = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.delay;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    static {
        (D = new ArrayList<String>()).add("Upgrades");
        ChestStealer.D.add("Item");
        ChestStealer.D.add("Play");
        ChestStealer.D.add("The");
        ChestStealer.D.add("Teleport");
        ChestStealer.D.add("Select");
        ChestStealer.D.add("Effect");
        ChestStealer.D.add("Vault");
        ChestStealer.D.add("Game");
        ChestStealer.D.add("Particles");
        ChestStealer.D.add("Server");
        ChestStealer.D.add("Soul");
        ChestStealer.D.add("Setting");
        ChestStealer.D.add("Are");
        ChestStealer.D.add("Option");
        ChestStealer.D.add("SkyWars");
        ChestStealer.D.add("Kit");
        ChestStealer.D.add("Categories");
        ChestStealer.D.add("Class");
        ChestStealer.D.add("Auctions");
        ChestStealer.D.add("Ender");
        ChestStealer.D.add("Menu");
        ChestStealer.D.add("Shop");
        ChestStealer.D.add("Collectibles");
        ChestStealer.D.add("Lobby");
        ChestStealer.D.add("Profile");
    }
    
    public static Minecraft getMinecraft() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft1() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft2() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft3() {
        return ChestStealer.D;
    }
    
    public static List b() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft4() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft5() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft6() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft7() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft8() {
        return ChestStealer.D;
    }
    
    public static Minecraft getMinecraft9() {
        return ChestStealer.D;
    }
    
    public static Timer M(final ChestStealer chestStealer) {
        return chestStealer.a;
    }
    
    public static R M(final ChestStealer chestStealer) {
        return chestStealer.mode;
    }
    
    public static NumberValue M(final ChestStealer chestStealer) {
        return chestStealer.delay;
    }
    
    public static Minecraft getMinecraft10() {
        return ChestStealer.D;
    }
}
