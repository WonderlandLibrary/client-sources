package net.futureclient.client.modules.miscellaneous;

import net.futureclient.client.modules.miscellaneous.antiafk.Listener2;
import net.futureclient.client.modules.miscellaneous.antiafk.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Zg;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.Value;
import java.util.Random;
import net.futureclient.client.Ea;

public class AntiAFK extends Ea
{
    private Random i;
    private Value<Boolean> tabComplete;
    private Value<Boolean> jump;
    private Timer I;
    private Value<Boolean> sneak;
    private Value<Boolean> interact;
    private Zg l;
    private Value<Boolean> message;
    private Value<Boolean> rotate;
    private Zg A;
    private Value<Boolean> move;
    private int K;
    private Value<Boolean> autoReply;
    private NumberValue delay;
    private Zg a;
    private Value<Boolean> punch;
    private Value<Boolean> clipDown;
    
    public static Minecraft getMinecraft() {
        return AntiAFK.D;
    }
    
    public AntiAFK() {
        super("AntiAFK", new String[] { "AntiAFK", "afk", "idle" }, true, -11532958, Category.MISCELLANEOUS);
        this.i = new Random();
        this.message = new Value<Boolean>(true, new String[] { "Message", "PM", "Command" });
        this.tabComplete = new Value<Boolean>(true, new String[] { "TabComplete", "tcomplete", "tab" });
        this.rotate = new Value<Boolean>(true, new String[] { "Rotate", "Rot", "Rotation", "Spin" });
        this.interact = new Value<Boolean>(true, new String[] { "Interact", "Interaction", "Break" });
        this.clipDown = new Value<Boolean>(true, new String[] { "ClipDown", "TpDown", "Tp", "Teleportion", "Vclip" });
        this.punch = new Value<Boolean>(true, new String[] { "Punch", "Pucn", "Hit", "Attack", "Swing" });
        this.jump = new Value<Boolean>(true, new String[] { "Jump", "Jum", "Hop" });
        this.sneak = new Value<Boolean>(true, new String[] { "Sneak", "Snek", "Sneake", "sn", "s" });
        this.autoReply = new Value<Boolean>(true, new String[] { "AutoReply", "Reply", "AR" });
        this.move = new Value<Boolean>(true, new String[] { "Move", "Moving", "m" });
        this.delay = new NumberValue(60.0f, 5.0f, 270.0f, 1, new String[] { "Delay", "d" });
        final int n = 11;
        this.A = new Zg();
        this.a = new Zg();
        this.l = new Zg();
        this.I = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.message;
        array[1] = this.tabComplete;
        array[2] = this.rotate;
        array[3] = this.move;
        array[4] = this.interact;
        array[5] = this.clipDown;
        array[6] = this.punch;
        array[7] = this.jump;
        array[8] = this.sneak;
        array[9] = this.autoReply;
        array[10] = this.delay;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return AntiAFK.D;
    }
    
    public static Value B(final AntiAFK antiAFK) {
        return antiAFK.interact;
    }
    
    public static Minecraft getMinecraft2() {
        return AntiAFK.D;
    }
    
    public static Value C(final AntiAFK antiAFK) {
        return antiAFK.message;
    }
    
    public static Minecraft getMinecraft3() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft4() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft5() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft6() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft7() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft8() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft9() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft10() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft11() {
        return AntiAFK.D;
    }
    
    public static Value c(final AntiAFK antiAFK) {
        return antiAFK.punch;
    }
    
    public static Minecraft getMinecraft12() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft13() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft14() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft15() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft16() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft17() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft18() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft19() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft20() {
        return AntiAFK.D;
    }
    
    public static Value b(final AntiAFK antiAFK) {
        return antiAFK.clipDown;
    }
    
    public static Zg b(final AntiAFK antiAFK) {
        return antiAFK.l;
    }
    
    public static Minecraft getMinecraft21() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft22() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft23() {
        return AntiAFK.D;
    }
    
    public static int e(final AntiAFK antiAFK) {
        return antiAFK.K;
    }
    
    public static Zg e(final AntiAFK antiAFK) {
        return antiAFK.a;
    }
    
    public static Minecraft getMinecraft24() {
        return AntiAFK.D;
    }
    
    public static Value e(final AntiAFK antiAFK) {
        return antiAFK.jump;
    }
    
    public static Minecraft getMinecraft25() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft26() {
        return AntiAFK.D;
    }
    
    public static Value i(final AntiAFK antiAFK) {
        return antiAFK.sneak;
    }
    
    public static Minecraft getMinecraft27() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft28() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft29() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft30() {
        return AntiAFK.D;
    }
    
    public static Value g(final AntiAFK antiAFK) {
        return antiAFK.rotate;
    }
    
    public static Minecraft getMinecraft31() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft32() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft33() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft34() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft35() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft36() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft37() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft38() {
        return AntiAFK.D;
    }
    
    public static Value K(final AntiAFK antiAFK) {
        return antiAFK.move;
    }
    
    public static Minecraft getMinecraft39() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft40() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft41() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft42() {
        return AntiAFK.D;
    }
    
    public static Value M(final AntiAFK antiAFK) {
        return antiAFK.autoReply;
    }
    
    public static Zg M(final AntiAFK antiAFK) {
        return antiAFK.A;
    }
    
    public static Minecraft getMinecraft43() {
        return AntiAFK.D;
    }
    
    public static int M(final AntiAFK antiAFK, final int k) {
        return antiAFK.K = k;
    }
    
    public static int M(final AntiAFK antiAFK) {
        return antiAFK.K++;
    }
    
    public static NumberValue M(final AntiAFK antiAFK) {
        return antiAFK.delay;
    }
    
    public static Timer M(final AntiAFK antiAFK) {
        return antiAFK.I;
    }
    
    public static Random M(final AntiAFK antiAFK) {
        return antiAFK.i;
    }
    
    public static Minecraft getMinecraft44() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft45() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft46() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft47() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft48() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft49() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft50() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft51() {
        return AntiAFK.D;
    }
    
    public static Value h(final AntiAFK antiAFK) {
        return antiAFK.tabComplete;
    }
    
    public static Minecraft getMinecraft52() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft53() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft54() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft55() {
        return AntiAFK.D;
    }
    
    public static Minecraft getMinecraft56() {
        return AntiAFK.D;
    }
}
