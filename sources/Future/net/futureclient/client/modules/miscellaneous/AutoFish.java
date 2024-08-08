package net.futureclient.client.modules.miscellaneous;

import net.futureclient.client.IE;
import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.item.ItemFishingRod;
import net.futureclient.client.modules.miscellaneous.autofish.Listener2;
import net.futureclient.client.modules.miscellaneous.autofish.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class AutoFish extends Ea
{
    private NumberValue castingDelay;
    private Value<Boolean> openInventory;
    private int M;
    private int d;
    private int a;
    private NumberValue maxSoundDist;
    private boolean k;
    
    public static Minecraft getMinecraft() {
        return AutoFish.D;
    }
    
    public AutoFish() {
        super("AutoFish", new String[] { "AutoFish", "Fish", "Fishing" }, true, -6732630, Category.MISCELLANEOUS);
        this.openInventory = new Value<Boolean>(true, new String[] { "Open Inventory", "OpenInventory", "Open", "Inv", "Inventory", "OpenInv" });
        this.castingDelay = new NumberValue(15.0f, 10.0f, 25.0f, 1, new String[] { "CastingDelay", "CastDelay", "CastDel", "cd" });
        this.maxSoundDist = new NumberValue(0.0, 0.0, 0.0, 1.273197475E-314, new String[] { "MaxSoundDist", "MaxSoundDistance", "MaxSoundist", "MaxDist", "msd", "md" });
        this.M(new Value[] { this.openInventory, this.castingDelay, this.maxSoundDist });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static int B(final AutoFish autoFish) {
        return autoFish.d--;
    }
    
    public static Minecraft getMinecraft1() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoFish.D;
    }
    
    public static int C(final AutoFish autoFish) {
        return autoFish.a;
    }
    
    private void C() {
        if (!AutoFish.D.player.inventory.getCurrentItem().isEmpty() || AutoFish.D.player.inventory.getCurrentItem().getItem() instanceof ItemFishingRod) {
            if (!this.openInventory.M()) {
                if (AutoFish.D.currentScreen instanceof GuiChat || AutoFish.D.currentScreen == null) {
                    ((IMinecraft)AutoFish.D).clickMouse(IE.RD.D);
                    final int m = 0;
                    this.d = this.castingDelay.B().intValue();
                    this.M = m;
                }
            }
            else {
                ((IMinecraft)AutoFish.D).clickMouse(IE.RD.D);
                final int i = 0;
                this.d = this.castingDelay.B().intValue();
                this.M = i;
            }
        }
    }
    
    public static Minecraft getMinecraft3() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AutoFish.D;
    }
    
    public static int b(final AutoFish autoFish) {
        return autoFish.d;
    }
    
    public void b() {
        final int m = 0;
        final int d = 0;
        super.b();
        this.d = d;
        this.M = m;
    }
    
    public static Minecraft getMinecraft9() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft10() {
        return AutoFish.D;
    }
    
    public static int e(final AutoFish autoFish) {
        return autoFish.M++;
    }
    
    public static Minecraft getMinecraft11() {
        return AutoFish.D;
    }
    
    public static int i(final AutoFish autoFish) {
        return autoFish.a--;
    }
    
    public static Minecraft getMinecraft12() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft15() {
        return AutoFish.D;
    }
    
    public static int M(final AutoFish autoFish) {
        return autoFish.a++;
    }
    
    public static NumberValue M(final AutoFish autoFish) {
        return autoFish.maxSoundDist;
    }
    
    public static boolean M(final AutoFish autoFish, final boolean k) {
        return autoFish.k = k;
    }
    
    public static Minecraft getMinecraft16() {
        return AutoFish.D;
    }
    
    public static void M(final AutoFish autoFish) {
        autoFish.C();
    }
    
    public static boolean M(final AutoFish autoFish) {
        return autoFish.k;
    }
    
    public static Minecraft getMinecraft17() {
        return AutoFish.D;
    }
    
    public static Minecraft getMinecraft18() {
        return AutoFish.D;
    }
    
    public static int h(final AutoFish autoFish) {
        return autoFish.M;
    }
    
    public static Minecraft getMinecraft19() {
        return AutoFish.D;
    }
}
