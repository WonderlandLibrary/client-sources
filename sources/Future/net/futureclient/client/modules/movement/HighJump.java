package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.highjump.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class HighJump extends Ea
{
    private Value<Boolean> inAir;
    private NumberValue height;
    
    public HighJump() {
        super("HighJump", new String[] { "HighJump", "AACHighJump", "AACJump" }, true, -11127454, Category.MOVEMENT);
        this.inAir = new Value<Boolean>(false, new String[] { "InAir", "Air", "OnGroundOnly", "OnGround", "GroundOnly", "Ground" });
        this.height = new NumberValue(1.4429571377E-314, 0.0, 1.0, new String[] { "Height", "Heigh", "Hight", "High", "Size", "Scaling", "Scale" });
        this.M(new Value[] { this.inAir, this.height });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return HighJump.D;
    }
    
    public static Minecraft getMinecraft1() {
        return HighJump.D;
    }
    
    public static Minecraft getMinecraft2() {
        return HighJump.D;
    }
    
    public static Value M(final HighJump highJump) {
        return highJump.inAir;
    }
    
    public static NumberValue M(final HighJump highJump) {
        return highJump.height;
    }
}
