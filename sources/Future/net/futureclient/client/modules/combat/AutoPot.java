package net.futureclient.client.modules.combat;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import java.util.Iterator;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemAir;
import net.futureclient.client.modules.combat.autopot.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class AutoPot extends Ea
{
    private NumberValue delay;
    public Value<Boolean> 19Fix;
    private double E;
    private double A;
    public boolean j;
    public Timer K;
    private NumberValue slot;
    private Value<Boolean> onGroundOnly;
    private int a;
    private double D;
    private NumberValue health;
    
    public static Minecraft getMinecraft() {
        return AutoPot.D;
    }
    
    public AutoPot() {
        super("AutoPot", new String[] { "AutoPot", "AutoHeal" }, true, 16724787, Category.COMBAT);
        this.health = new NumberValue(0.0, 1.0, 0.0, 0.0, new String[] { "Health", "h", "<3" });
        this.delay = new NumberValue(0.7f, 0.01f, 1.0f, 5.941588215E-315, new String[] { "Delay", "d" });
        this.slot = new NumberValue(6.0f, 1.0f, 9.0f, 1, new String[] { "Slot", "lot", "hotbar" });
        this.19Fix = new Value<Boolean>(true, new String[] { "1.9Fix", "handfix", "1.10handfix", "fix" });
        this.onGroundOnly = new Value<Boolean>(false, new String[] { "OnGroundOnly", "OnlyOnground", "GroundPot", "Onground" });
        final int n = 5;
        this.a = -1;
        this.K = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.onGroundOnly;
        array[1] = this.health;
        array[2] = this.delay;
        array[3] = this.slot;
        array[4] = this.19Fix;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    public void B() {
        this.a = -1;
        super.B();
    }
    
    public static Minecraft getMinecraft1() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft2() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft3() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft9() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft10() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft11() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft12() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft15() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft16() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft17() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft18() {
        return AutoPot.D;
    }
    
    public static double b(final AutoPot autoPot, final double a) {
        return autoPot.A = a;
    }
    
    public static NumberValue b(final AutoPot autoPot) {
        return autoPot.delay;
    }
    
    public static Minecraft getMinecraft19() {
        return AutoPot.D;
    }
    
    public int b() {
        int n = 0;
        int i = 9;
        int n2 = 9;
        while (i < 45) {
            final ItemStack stack;
            if (!((stack = AutoPot.D.player.inventoryContainer.getSlot(n2).getStack()).getItem() instanceof ItemAir) && this.M(stack)) {
                n += stack.getCount();
            }
            i = ++n2;
        }
        return n;
    }
    
    public static double b(final AutoPot autoPot) {
        return autoPot.D;
    }
    
    public void b() {
        this.j = false;
        super.b();
    }
    
    public static Minecraft getMinecraft20() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft21() {
        return AutoPot.D;
    }
    
    public static int e(final AutoPot autoPot) {
        return autoPot.a;
    }
    
    public int e() {
        int n = -1;
        int i = 1;
        int n2 = 1;
        while (i < 45) {
            final ItemStack stack;
            if (AutoPot.D.player.inventoryContainer.getSlot(n2).getHasStack() && (stack = AutoPot.D.player.inventoryContainer.getSlot(n2).getStack()).getItem() instanceof ItemPotion && PotionUtils.getEffectsFromStack(stack) != null) {
                final Iterator iterator = PotionUtils.getEffectsFromStack(stack).iterator();
                while (iterator.hasNext()) {
                    if (iterator.next().getPotion() == MobEffects.INSTANT_HEALTH) {
                        n = n2;
                    }
                }
            }
            i = ++n2;
        }
        return n;
    }
    
    public static NumberValue e(final AutoPot autoPot) {
        return autoPot.slot;
    }
    
    public static double e(final AutoPot autoPot) {
        return autoPot.A;
    }
    
    public static double e(final AutoPot autoPot, final double d) {
        return autoPot.D = d;
    }
    
    public static Minecraft getMinecraft22() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft23() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft24() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft25() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft26() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft27() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft28() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft29() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft30() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft31() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft32() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft33() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft34() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft35() {
        return AutoPot.D;
    }
    
    public boolean M(final ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemPotion && ((ItemPotion)itemStack.getItem()).hasEffect(itemStack)) {
            final Iterator<PotionEffect> iterator = PotionUtils.getEffectsFromStack(itemStack).iterator();
            while (iterator.hasNext()) {
                if (iterator.next().getPotion() == MobEffects.INSTANT_HEALTH) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public static Minecraft getMinecraft36() {
        return AutoPot.D;
    }
    
    public static double M(final AutoPot autoPot, final double e) {
        return autoPot.E = e;
    }
    
    public static int M(final AutoPot autoPot) {
        return --autoPot.a;
    }
    
    public static double M(final AutoPot autoPot) {
        return autoPot.E;
    }
    
    public void M(final int n, final int n2) {
        AutoPot.D.playerController.windowClick(AutoPot.D.player.inventoryContainer.windowId, n, n2, ClickType.SWAP, (EntityPlayer)AutoPot.D.player);
    }
    
    public static Value M(final AutoPot autoPot) {
        return autoPot.onGroundOnly;
    }
    
    public static NumberValue M(final AutoPot autoPot) {
        return autoPot.health;
    }
    
    public static int M(final AutoPot autoPot, final int a) {
        return autoPot.a = a;
    }
    
    public static Minecraft getMinecraft37() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft38() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft39() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft40() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft41() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft42() {
        return AutoPot.D;
    }
    
    public static Minecraft getMinecraft43() {
        return AutoPot.D;
    }
}
