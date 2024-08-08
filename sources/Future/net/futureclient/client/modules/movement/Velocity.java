package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.movement.velocity.Listener3;
import net.futureclient.client.modules.movement.velocity.Listener2;
import net.futureclient.client.modules.movement.velocity.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.ad;
import net.futureclient.client.R;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Velocity extends Ea
{
    public Value<Boolean> fishingHook;
    public Value<Boolean> pushable;
    public NumberValue vertical;
    public NumberValue horizontal;
    private R<ad.Dc> mode;
    public Value<Boolean> water;
    
    public Velocity() {
        super("Velocity", new String[] { "Velocity", "av", "antivelocity", "antivel", "knockback" }, true, -9206132, Category.MOVEMENT);
        this.mode = new R<ad.Dc>(ad.Dc.D, new String[] { "Mode", "m" });
        this.water = new Value<Boolean>(true, new String[] { "Water", "w" });
        this.pushable = new Value<Boolean>(false, new String[] { "Pushable", "Push" });
        this.fishingHook = new Value<Boolean>(true, new String[] { "FishingHook", "fishhook" });
        this.horizontal = new NumberValue(0.0, 0.0, 0.0, 1.273197475E-314, new String[] { "Horizontal", "h%", "HReduction", "RHeduce", "HLower", "HVelocity_reduction", "hv", "h" });
        this.vertical = new NumberValue(0.0, 0.0, 0.0, 1.273197475E-314, new String[] { "Vertical", "v%", "VReduction", "VReduce", "VLower", "VVelocity_reduction", "vv", "v" });
        this.M(new Value[] { this.mode, this.water, this.fishingHook, this.pushable, this.horizontal, this.vertical });
        this.M(new n[] { new Listener1(this), new Listener2(this), new Listener3(this) });
    }
    
    public static Minecraft getMinecraft() {
        return Velocity.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Velocity.D;
    }
    
    public static Minecraft getMinecraft2() {
        return Velocity.D;
    }
    
    public static Minecraft getMinecraft3() {
        return Velocity.D;
    }
    
    public static Minecraft getMinecraft4() {
        return Velocity.D;
    }
    
    public static R M(final Velocity velocity) {
        return velocity.mode;
    }
    
    public static Minecraft getMinecraft5() {
        return Velocity.D;
    }
}
