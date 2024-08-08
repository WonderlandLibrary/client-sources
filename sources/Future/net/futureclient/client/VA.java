package net.futureclient.client;

import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.Timer;
import net.futureclient.client.utils.NumberValue;

public class VA extends Ea
{
    private NumberValue speed;
    private Timer a;
    private Timer D;
    public Value<Boolean> k;
    
    public static Minecraft getMinecraft() {
        return VA.D;
    }
    
    public VA() {
        super(AE.M("\u0000\b1\u000f1\u001f\u0016\u0016 \u0003!"), new String[] { "EntitySpeed", AE.M(".*\u00146\u0003\u0016\u0016 \u0003!"), "HorseHax", AE.M(".7\t6\u0003\u0016\u0016 \u0003!"), "HorsSped", AE.M("\r\t7\u0015 "), "Horseped" }, true, -12279274, Category.MOVEMENT);
        this.k = new Value<Boolean>(false, new String[] { AE.M("\u0004\b1\u000f\u0016\u00120\u0005."), "NoStuck", AE.M("'\u0016") });
        this.speed = new NumberValue(3.8f, 0.1f, 3.8f, new String[] { "Speed", AE.M("55\u0003!"), "S" });
        final int n = 2;
        this.a = new Timer();
        this.D = new Timer();
        final Value[] array = new Value[n];
        array[0] = this.k;
        array[1] = this.speed;
        this.M(array);
        this.M(new n[] { new Rb(this) });
    }
    
    public static Minecraft getMinecraft1() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft2() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft3() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft4() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft5() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft6() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft7() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft8() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft9() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft10() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft11() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft12() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft13() {
        return VA.D;
    }
    
    public static Timer e(final VA va) {
        return va.D;
    }
    
    public static Minecraft getMinecraft14() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft15() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft16() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft17() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft18() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft19() {
        return VA.D;
    }
    
    public static NumberValue M(final VA va) {
        return va.speed;
    }
    
    public static Minecraft getMinecraft20() {
        return VA.D;
    }
    
    public static Timer M(final VA va) {
        return va.a;
    }
    
    public static Minecraft getMinecraft21() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft22() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft23() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft24() {
        return VA.D;
    }
    
    public static Minecraft getMinecraft25() {
        return VA.D;
    }
}
