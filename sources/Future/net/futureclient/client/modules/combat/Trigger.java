package net.futureclient.client.modules.combat;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.combat.trigger.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.AE;
import net.futureclient.client.HD;
import net.futureclient.client.R;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Trigger extends Ea
{
    private Value<Boolean> E;
    private NumberValue A;
    private Timer j;
    private Value<Boolean> weaponCheck;
    private NumberValue M;
    private R<HD.lg> d;
    private Value<Boolean> teamCheck;
    private Value<Boolean> D;
    private transient double k;
    
    public Trigger() {
        super("Trigger", new String[] { AE.M("\u0011\u0014,\u0001\"\u00037"), "ac", AE.M("&\n,\u0005.\u00037"), "AutoClicker" }, true, -4115980, Category.COMBAT);
        this.d = new R<HD.lg>(HD.lg.k, new String[] { AE.M("\u0006\u000e \u0005."), "AttackCheck", AE.M("'1\u0012$\u0005."), "ac" });
        this.D = new Value<Boolean>(true, new String[] { AE.M("/+\u0010,\u0015,\u0004)\u0003\u0006\u000e \u0005."), "Invisibles", AE.M("\f\b3\u000f6"), "ic", AE.M(",") });
        this.teamCheck = new Value<Boolean>(false, new String[] { "TeamCheck", AE.M("2 \u0007("), "AttackTeammates", AE.M("1"), "tc" });
        this.E = new Value<Boolean>(true, new String[] { AE.M("\u0003\u0014,\u0003+\u0002\u0006\u000e \u0005."), "Friends", AE.M("\u0004\u00121\u0007&\r\u0003\u0014,\u0003+\u00026"), "Betray", AE.M("#") });
        this.weaponCheck = new Value<Boolean>(true, new String[] { "WeaponCheck", AE.M("1 \u00075\t+"), "w" });
        this.A = new NumberValue(8.0f, 0.1f, 20.0f, 1.273197475E-314, new String[] { AE.M("\u0004\u00121\u0007&\r\u0016\u0016 \u0003!"), "CPS", AE.M("\u0005)\u000f&\r6"), "click" });
        this.M = new NumberValue(2.0f, 0.1f, 10.0f, 1.273197475E-314, new String[] { AE.M("\u0017\u0007+\u0002*\u000b\u0016\u0016 \u0003!"), "randomsped", AE.M("\u00146\u0016 \u0003!") });
        final int n = 7;
        this.k = 0.0;
        this.j = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.d;
        array[1] = this.D;
        array[2] = this.teamCheck;
        array[3] = this.E;
        array[4] = this.weaponCheck;
        array[5] = this.A;
        array[6] = this.M;
        this.M(array);
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Trigger.D;
    }
    
    public static Value B(final Trigger trigger) {
        return trigger.teamCheck;
    }
    
    public static Minecraft getMinecraft1() {
        return Trigger.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Trigger.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Trigger.D;
    }
    
    public static Value b(final Trigger trigger) {
        return trigger.weaponCheck;
    }
    
    public static Value e(final Trigger trigger) {
        return trigger.E;
    }
    
    public static NumberValue e(final Trigger trigger) {
        return trigger.M;
    }
    
    public static Minecraft getMinecraft4() {
        return Trigger.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Trigger.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Trigger.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Trigger.D;
    }
    
    public static Value M(final Trigger trigger) {
        return trigger.D;
    }
    
    public static double M(final Trigger trigger, final double k) {
        return trigger.k = k;
    }
    
    public static Minecraft getMinecraft8() {
        return Trigger.D;
    }
    
    public static NumberValue M(final Trigger trigger) {
        return trigger.A;
    }
    
    public static double M(final Trigger trigger) {
        return trigger.k;
    }
    
    public static R M(final Trigger trigger) {
        return trigger.d;
    }
    
    public static Timer M(final Trigger trigger) {
        return trigger.j;
    }
    
    public static Minecraft getMinecraft9() {
        return Trigger.D;
    }
}
