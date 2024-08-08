package net.futureclient.client.modules.movement;

import net.futureclient.client.modules.movement.jesus.Listener6;
import net.futureclient.client.modules.movement.jesus.Listener5;
import net.futureclient.client.modules.movement.jesus.Listener4;
import net.futureclient.client.modules.movement.jesus.Listener3;
import net.futureclient.client.modules.movement.jesus.Listener2;
import net.futureclient.client.modules.movement.jesus.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.Cb;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class Jesus extends Ea
{
    private boolean a;
    private R<Cb.gA> mode;
    private Timer k;
    
    public static Minecraft getMinecraft() {
        return Jesus.D;
    }
    
    public Jesus() {
        super("Jesus", new String[] { "Jesus", "WaterWalk", "Dolphin", "Trampoline" }, true, -7807509, Category.MOVEMENT);
        this.k = new Timer();
        this.mode = new R<Cb.gA>(Cb.gA.d, new String[] { "Mode", "m" });
        this.M(new Value[] { this.mode });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this), new Listener4(this), new Listener5(this), new Listener6(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft8() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft9() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft10() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft11() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft12() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft13() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft14() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft15() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft16() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft17() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft18() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft19() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft20() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft21() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft22() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft23() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft24() {
        return Jesus.D;
    }
    
    public static Timer M(final Jesus jesus) {
        return jesus.k;
    }
    
    public static R M(final Jesus jesus) {
        return jesus.mode;
    }
    
    public static boolean M(final Jesus jesus) {
        return jesus.a;
    }
    
    public static boolean M(final Jesus jesus, final boolean a) {
        return jesus.a = a;
    }
    
    public static Minecraft getMinecraft25() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft26() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft27() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft28() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft29() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft30() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft31() {
        return Jesus.D;
    }
    
    public static Minecraft getMinecraft32() {
        return Jesus.D;
    }
}
