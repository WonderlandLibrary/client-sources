package net.futureclient.client.modules.miscellaneous;

import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.modules.miscellaneous.autoeat.Listener2;
import net.futureclient.client.modules.miscellaneous.autoeat.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Ea;

public class AutoEat extends Ea
{
    private int j;
    private Timer K;
    public NumberValue hunger;
    private Timer d;
    public NumberValue health;
    private boolean D;
    private boolean k;
    
    public static Minecraft getMinecraft() {
        return AutoEat.D;
    }
    
    public AutoEat() {
        super("AutoEat", new String[] { "AutoEat", "AutoEater", "AutoEating", "AutoFood" }, true, 16773918, Category.MISCELLANEOUS);
        this.hunger = new NumberValue(19.0f, 0.0f, 19.0f, 1, new String[] { "Hunger", "FoodLevel" });
        this.health = new NumberValue(20.0f, 0.0f, 20.0f, 1, new String[] { "Health", "Helth", "Helt", "Hehatl" });
        final int n = 2;
        this.K = new Timer();
        this.d = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.hunger;
        array[1] = this.health;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return AutoEat.D;
    }
    
    public void B() {
        super.B();
        if (AutoEat.D.player != null) {
            this.j = AutoEat.D.player.inventory.currentItem;
        }
        final boolean d = false;
        this.K.e();
        this.D = d;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft3() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoEat.D;
    }
    
    public void b() {
        super.b();
        final int keyCode = AutoEat.D.gameSettings.keyBindUseItem.getKeyCode();
        final boolean k = false;
        KeyBinding.setKeyBindState(keyCode, k);
        AutoEat.D.player.inventory.currentItem = this.j;
        this.k = k;
    }
    
    public static Minecraft getMinecraft8() {
        return AutoEat.D;
    }
    
    public boolean b() {
        if (this.k) {
            final boolean b = true;
            this.d.e();
            return b;
        }
        return !this.d.e(1000L);
    }
    
    public static Minecraft getMinecraft9() {
        return AutoEat.D;
    }
    
    public static boolean e(final AutoEat autoEat, final boolean k) {
        return autoEat.k = k;
    }
    
    public static Minecraft getMinecraft10() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft11() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft12() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft15() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft16() {
        return AutoEat.D;
    }
    
    public static Timer M(final AutoEat autoEat) {
        return autoEat.K;
    }
    
    public static Minecraft getMinecraft17() {
        return AutoEat.D;
    }
    
    public static int M(final AutoEat autoEat, final int j) {
        return autoEat.j = j;
    }
    
    public static int M(final AutoEat autoEat) {
        return autoEat.j;
    }
    
    public static boolean M(final AutoEat autoEat) {
        return autoEat.D;
    }
    
    public static boolean M(final AutoEat autoEat, final boolean d) {
        return autoEat.D = d;
    }
    
    public static Minecraft getMinecraft18() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft19() {
        return AutoEat.D;
    }
    
    public static Minecraft getMinecraft20() {
        return AutoEat.D;
    }
}
