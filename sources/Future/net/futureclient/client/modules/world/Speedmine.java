package net.futureclient.client.modules.world;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.world.speedmine.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class Speedmine extends Ea
{
    private NumberValue speed;
    private Value<Boolean> autoSwitch;
    private Value<Boolean> fastFall;
    
    public Speedmine() {
        super("Speedmine", new String[] { "Speedmine", "Spedmine", "Fastbreak", "sg", "sm", "fb" }, true, -6773135, Category.WORLD);
        this.fastFall = new Value<Boolean>(true, new String[] { "FastFall", "Fast-Fall", "fall", "ff" });
        this.autoSwitch = new Value<Boolean>(true, new String[] { "AutoSwitch", "Auto-Switch", "as", "auto" });
        this.speed = new NumberValue(0.8f, 0.0f, 1.0f, 1.273197475E-314, new String[] { "Speed", "S", "Spd" });
        this.M(new Value[] { this.speed, this.fastFall, this.autoSwitch });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Speedmine.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Speedmine.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Speedmine.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Speedmine.D;
    }
    
    public static Value e(final Speedmine speedmine) {
        return speedmine.fastFall;
    }
    
    public static Minecraft getMinecraft4() {
        return Speedmine.D;
    }
    
    public static Minecraft getMinecraft5() {
        return Speedmine.D;
    }
    
    public static Minecraft getMinecraft6() {
        return Speedmine.D;
    }
    
    public static Minecraft getMinecraft7() {
        return Speedmine.D;
    }
    
    public static Value M(final Speedmine speedmine) {
        return speedmine.autoSwitch;
    }
    
    public static Minecraft getMinecraft8() {
        return Speedmine.D;
    }
    
    public static NumberValue M(final Speedmine speedmine) {
        return speedmine.speed;
    }
    
    public static Minecraft getMinecraft9() {
        return Speedmine.D;
    }
}
