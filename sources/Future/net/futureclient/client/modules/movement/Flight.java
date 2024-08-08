package net.futureclient.client.modules.movement;

import net.futureclient.client.ZG;
import net.futureclient.client.modules.movement.flight.Listener5;
import net.futureclient.client.modules.movement.flight.Listener4;
import net.futureclient.client.modules.movement.flight.Listener3;
import net.futureclient.client.modules.movement.flight.Listener2;
import net.futureclient.client.modules.movement.flight.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.ZC;
import net.futureclient.client.R;
import net.futureclient.client.utils.Timer;
import java.util.Random;
import net.futureclient.client.Ea;

public class Flight extends Ea
{
    private double i;
    private final Random C;
    private Timer g;
    private R<ZC.SB> mode;
    private int B;
    private int H;
    private boolean l;
    private Value<Boolean> animation;
    private NumberValue gommeSpeed;
    private Value<Boolean> glide;
    private Value<Boolean> antiFallDamage;
    private Value<Boolean> damage;
    private NumberValue speed;
    private NumberValue aacy;
    private NumberValue glideSpeed;
    private NumberValue gommeDown;
    private Value<Boolean> antiKick;
    
    public static Minecraft getMinecraft() {
        return Flight.D;
    }
    
    public Flight() {
        super("Flight", new String[] { "Flight", "Fly" }, true, -4545358, Category.MOVEMENT);
        this.animation = new Value<Boolean>(true, new String[] { "Animation", "ani" });
        this.damage = new Value<Boolean>(false, new String[] { "Damage", "d", "dmg" });
        this.glide = new Value<Boolean>(false, new String[] { "Glide", "g" });
        this.antiFallDamage = new Value<Boolean>(false, new String[] { "Anti Fall Damage", "Anti-Fall-Damage", "AntiFallDamage", "NoFallDamage", "NoFall" });
        this.antiKick = new Value<Boolean>(false, new String[] { "Anti Kick", "Anti-Kick", "ak", "antikick" });
        this.glideSpeed = new NumberValue(1.155044749E-314, 0.0, 0.0, 1.273197475E-314, new String[] { "GlideSpeed", "Glide-Speed", "gs", "glidespeed" });
        this.speed = new NumberValue(0.0, 1.273197475E-314, 0.0, new String[] { "Speed", "spd", "s" });
        this.aacy = new NumberValue(3.395193264E-315, 0.0, 0.0, 5.941588215E-315, new String[] { "AACY", "y", "aac" });
        this.gommeDown = new NumberValue(1.0, 0.0, 0.0, 1.273197475E-314, new String[] { "GommeDown", "Down", "DownAmount", "GoDown", "downvalue" });
        this.gommeSpeed = new NumberValue(1.273197475E-314, 0.0, 1.0, 1.273197475E-314, new String[] { "GommeSpeed", "GomSped", "GommeSped" });
        this.mode = new R<ZC.SB>(ZC.SB.a, new String[] { "Mode", "Type", "fly" });
        final int n = 11;
        this.g = new Timer();
        this.C = new Random();
        final Value[] array = new Value[n];
        array[0] = this.damage;
        array[1] = this.mode;
        array[2] = this.glideSpeed;
        array[3] = this.animation;
        array[4] = this.glide;
        array[5] = this.speed;
        array[6] = this.aacy;
        array[7] = this.gommeSpeed;
        array[8] = this.gommeDown;
        array[9] = this.antiFallDamage;
        array[10] = this.antiKick;
        this.M(array);
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this) });
    }
    
    public static Value B(final Flight flight) {
        return flight.animation;
    }
    
    public static NumberValue B(final Flight flight) {
        return flight.glideSpeed;
    }
    
    public void B() {
        super.B();
        if (Flight.D.player != null) {
            final boolean l = false;
            this.i = Flight.D.player.posY - 1.0;
            this.l = l;
        }
        if (this.damage.M()) {
            ZG.b();
        }
    }
    
    public static Minecraft getMinecraft1() {
        return Flight.D;
    }
    
    public static NumberValue C(final Flight flight) {
        return flight.gommeDown;
    }
    
    public static Minecraft getMinecraft2() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft17() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft19() {
        return Flight.D;
    }
    
    public static NumberValue b(final Flight flight) {
        return flight.aacy;
    }
    
    public static Minecraft getMinecraft20() {
        return Flight.D;
    }
    
    public void b() {
        super.b();
        if (this.mode.M() == ZC.SB.j && Flight.D.player != null) {
            this.B = 0;
            Flight.D.player.jumpMovementFactor = 0.02f;
        }
    }
    
    public static Value b(final Flight flight) {
        return flight.antiKick;
    }
    
    public static Minecraft getMinecraft21() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft22() {
        return Flight.D;
    }
    
    public static NumberValue e(final Flight flight) {
        return flight.gommeSpeed;
    }
    
    public static Value e(final Flight flight) {
        return flight.glide;
    }
    
    public static Minecraft getMinecraft23() {
        return Flight.D;
    }
    
    public static int e(final Flight flight) {
        return flight.B;
    }
    
    public static int e(final Flight flight, final int b) {
        return flight.B = b;
    }
    
    public static Minecraft getMinecraft24() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft25() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft26() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft27() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft28() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft29() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft30() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft31() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft32() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft33() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft34() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft35() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft36() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft37() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft38() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft39() {
        return Flight.D;
    }
    
    public static boolean M(final Flight flight, final boolean l) {
        return flight.l = l;
    }
    
    public static int M(final Flight flight) {
        return flight.H;
    }
    
    public static NumberValue M(final Flight flight) {
        return flight.speed;
    }
    
    public static Value M(final Flight flight) {
        return flight.antiFallDamage;
    }
    
    public static boolean M(final Flight flight) {
        return flight.l;
    }
    
    public static double M(final Flight flight) {
        return flight.i;
    }
    
    public static double M(final Flight flight, final double i) {
        return flight.i = i;
    }
    
    public static Random M(final Flight flight) {
        return flight.C;
    }
    
    public static R M(final Flight flight) {
        return flight.mode;
    }
    
    public static int M(final Flight flight, final int h) {
        return flight.H = h;
    }
    
    public static Timer M(final Flight flight) {
        return flight.g;
    }
    
    public static Minecraft getMinecraft40() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft41() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft42() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft43() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft44() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft45() {
        return Flight.D;
    }
    
    public static Minecraft getMinecraft46() {
        return Flight.D;
    }
}
