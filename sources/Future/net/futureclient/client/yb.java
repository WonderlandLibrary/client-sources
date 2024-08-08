package net.futureclient.client;

import net.futureclient.loader.mixin.common.wrapper.IMinecraft;
import net.futureclient.loader.mixin.common.wrapper.ITimer;
import net.minecraft.block.BlockLiquid;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.modules.movement.Speed;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.NumberValue;
import net.minecraft.block.Block;
import net.futureclient.client.utils.Value;

public class yb extends Ea
{
    private float l;
    private boolean L;
    public Value<Boolean> E;
    private Block A;
    private NumberValue j;
    private int K;
    public Value<Boolean> M;
    public R<nB> mode;
    private Timer a;
    public Value<Boolean> D;
    private double k;
    
    public static Minecraft getMinecraft() {
        return yb.D;
    }
    
    public yb() {
        super(AE.M("51\u00035"), new String[] { "Step", AE.M("\u00070\u0012*\f0\u000b5"), "SlideStep" }, false, -7285557, Category.MOVEMENT);
        this.j = new NumberValue(1.1f, 0.8f, 10.0f, 1.273197475E-314, new String[] { AE.M(". \u000f\"\u000e1"), "Stepheight", AE.M("\u0016\u0012 \u0016h. \u000f\"\u000e1"), "sh" });
        this.M = new Value<Boolean>(false, new String[] { AE.M("\u0017\u00033\u00037\u0015 "), "rev" });
        this.D = new Value<Boolean>(false, new String[] { AE.M("36\u0003\u0011\u000f(\u00037"), "Timer", AE.M("2,\u000b "), "Timr" });
        this.E = new Value<Boolean>(true, new String[] { AE.M("#+\u0012,\u0012<51\u00035"), "HorseStep", AE.M("\u0015\u000f\"51\u00035"), "PiggyStep", AE.M("-\u00151\u00035") });
        this.mode = new R<nB>(nB.d, new String[] { "Mode", AE.M("2<\u0016 "), "Mod" });
        final int n = 5;
        final int k = 0;
        final float l = 1.0f;
        this.a = new Timer();
        this.l = l;
        this.K = k;
        final Value[] array = new Value[n];
        array[0] = this.mode;
        array[1] = this.M;
        array[2] = this.D;
        array[3] = this.E;
        array[4] = this.j;
        this.M(array);
        this.M(new n[] { new iC(this), new kB(this), new OC(this), new LA(this), new Sb(this), new Wb(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft2() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft3() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft4() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft5() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft6() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft7() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft8() {
        return yb.D;
    }
    
    private boolean b() {
        final Speed speed = (Speed)pg.M().M().M((Class)db.class);
        final Freecam freecam;
        return ((freecam = (Freecam)pg.M().M().M((Class)dd.class)) == null || !freecam.M()) && (speed == null || !speed.M() || speed.mode.M().equals((Object)db.fC.K) || speed.mode.M().equals((Object)db.fC.E) || speed.mode.M().equals((Object)db.fC.A)) && (!yb.D.player.isInWater() && !(this.A instanceof BlockLiquid) && yb.D.player.onGround && !yb.D.player.isOnLadder() && !yb.D.gameSettings.keyBindJump.isKeyDown() && yb.D.player.collidedVertically && yb.D.player.fallDistance < 1.273197475E-314);
    }
    
    public void b() {
        final net.futureclient.client.modules.miscellaneous.Timer timer = (net.futureclient.client.modules.miscellaneous.Timer)pg.M().M().M((Class)Bc.class);
        if (yb.D.player != null) {
            if (timer != null && !timer.M()) {
                ((ITimer)((IMinecraft)yb.D).getTimer()).setTimerSpeed(1.0f);
            }
            if (yb.D.player.getRidingEntity() != null) {
                yb.D.player.getRidingEntity().stepHeight = 1.0f;
            }
        }
        super.b();
    }
    
    public static Minecraft getMinecraft9() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft10() {
        return yb.D;
    }
    
    public static int e(final yb yb) {
        return yb.K;
    }
    
    public static boolean e(final yb yb) {
        return yb.L;
    }
    
    public static Minecraft getMinecraft11() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft12() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft13() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft14() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft15() {
        return yb.D;
    }
    
    public static Timer M(final yb yb) {
        return yb.a;
    }
    
    public static boolean M(final yb yb) {
        return yb.b();
    }
    
    public static float M(final yb yb, final float l) {
        return yb.l = l;
    }
    
    public static int M(final yb yb, final int k) {
        return yb.K = k;
    }
    
    public static Minecraft getMinecraft16() {
        return yb.D;
    }
    
    public static int M(final yb yb) {
        return yb.K++;
    }
    
    public static Block M(final yb yb, final Block a) {
        return yb.A = a;
    }
    
    public static float M(final yb yb) {
        return yb.l;
    }
    
    public static double M(final yb yb, final double k) {
        return yb.k = k;
    }
    
    public static double M(final yb yb) {
        return yb.k;
    }
    
    public static NumberValue M(final yb yb) {
        return yb.j;
    }
    
    public static boolean M(final yb yb, final boolean l) {
        return yb.L = l;
    }
    
    public static Minecraft getMinecraft17() {
        return yb.D;
    }
    
    public static Minecraft getMinecraft18() {
        return yb.D;
    }
}
