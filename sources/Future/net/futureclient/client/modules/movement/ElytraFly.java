package net.futureclient.client.modules.movement;

import net.futureclient.client.modules.movement.elytrafly.Listener3;
import net.futureclient.client.modules.movement.elytrafly.Listener2;
import net.futureclient.client.modules.movement.elytrafly.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.AE;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Fb;
import net.futureclient.client.R;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class ElytraFly extends Ea
{
    private boolean H;
    public Value<Boolean> antiKick;
    private double L;
    public Value<Boolean> E;
    public Value<Boolean> stopInWater;
    private int j;
    public Value<Boolean> K;
    public R<Fb.RB> mode;
    private Timer d;
    public Value<Boolean> instantFly;
    private Timer D;
    private NumberValue speed;
    
    public static Minecraft getMinecraft() {
        return ElytraFly.D;
    }
    
    public ElytraFly() {
        super("ElytraFly", new String[] { AE.M("\u0000\n<\u00127\u0007\u0003\n<"), "Elytra", AE.M("\u0000\n<\u00127\u0007&\t+\u00127\t)"), "Elytra+", AE.M("#)\u001f1\u0014$\u0016)\u00136") }, true, -13131319, Category.MOVEMENT);
        this.D = new Timer();
        this.d = new Timer();
        this.mode = new R<Fb.RB>(Fb.RB.a, new String[] { "Mode", AE.M("2<\u0016 "), "Method", AE.M("\u0003\n<+ \u0012-\t!") });
        this.stopInWater = new Value<Boolean>(true, new String[] { "StopInWater", AE.M("6\u000f2"), "Stop", AE.M("2\u00071\u00037\u00151\t5") });
        this.instantFly = new Value<Boolean>(true, new String[] { "InstantFly", AE.M("/+\u00151\u0007#\n<"), "if" });
        this.K = new Value<Boolean>(true, new String[] { AE.M("51\t5)+!7\t0\b!"), "StopONG", AE.M("\u00151\t5\u00017\t0\b!"), "OnGround", AE.M(")+!7\t0\b!51\t5"), "sog" });
        this.E = new Value<Boolean>(true, new String[] { AE.M("'0\u0012*'&\u0005 \n \u0014$\u0012 "), "AutoSpeed", AE.M("\u0016\u0016 \u0003!35"), "AutoSpeedUp", AE.M("'&\u0005 \n \u0014$\u0012 ") });
        this.antiKick = new Value<Boolean>(true, new String[] { "AntiKick", AE.M("(*-,\u0005."), "CircleStrafer", AE.M("\u0016\u00127\u0007#\u00037") });
        this.speed = new NumberValue(0.0, 1.273197475E-314, 0.0, 1.273197475E-314, new String[] { "Speed", AE.M("6\u0016!"), "s" });
        this.M(new Value[] { this.mode, this.K, this.stopInWater, this.instantFly, this.E, this.antiKick, this.speed });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return ElytraFly.D;
    }
    
    public void B() {
        super.B();
        this.d.e();
    }
    
    public static Minecraft getMinecraft2() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft3() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft4() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft5() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft6() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft7() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft8() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft9() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft10() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft11() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft12() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft13() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft14() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft15() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft16() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft17() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft18() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft19() {
        return ElytraFly.D;
    }
    
    public void b() {
        final int j = 0;
        final boolean h = true;
        super.b();
        this.H = h;
        this.j = j;
    }
    
    public static Minecraft getMinecraft20() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft21() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft22() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft23() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft24() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft25() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft26() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft27() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft28() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft29() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft30() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft31() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft32() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft33() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft34() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft35() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft36() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft37() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft38() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft39() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft40() {
        return ElytraFly.D;
    }
    
    public static int M(final ElytraFly elytraFly) {
        return elytraFly.j;
    }
    
    public static Minecraft getMinecraft41() {
        return ElytraFly.D;
    }
    
    public static NumberValue M(final ElytraFly elytraFly) {
        return elytraFly.speed;
    }
    
    public static int M(final ElytraFly elytraFly, final int j) {
        return elytraFly.j = j;
    }
    
    public static boolean M(final ElytraFly elytraFly) {
        return elytraFly.H;
    }
    
    public static boolean M(final ElytraFly elytraFly, final boolean h) {
        return elytraFly.H = h;
    }
    
    public static double M(final ElytraFly elytraFly, final double l) {
        return elytraFly.L = l;
    }
    
    public static double M(final ElytraFly elytraFly) {
        return elytraFly.L;
    }
    
    public static Timer M(final ElytraFly elytraFly) {
        return elytraFly.D;
    }
    
    public static Minecraft getMinecraft42() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft43() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft44() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft45() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft46() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft47() {
        return ElytraFly.D;
    }
    
    public static Minecraft getMinecraft48() {
        return ElytraFly.D;
    }
}
