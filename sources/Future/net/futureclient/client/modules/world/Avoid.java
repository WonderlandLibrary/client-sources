package net.futureclient.client.modules.world;

import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.world.avoid.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Ea;

public class Avoid extends Ea
{
    private Value<Boolean> unloaded;
    private Value<Boolean> fire;
    private Value<Boolean> cactus;
    
    public Avoid() {
        super("Avoid", new String[] { "Avoid", "AntiVoid" }, false, -10561537, Category.WORLD);
        this.fire = new Value<Boolean>(true, new String[] { "Fire", "f" });
        this.cactus = new Value<Boolean>(true, new String[] { "Cactus", "c" });
        this.unloaded = new Value<Boolean>(true, new String[] { "Unloaded", "Void", "AntiVoid" });
        this.M(new Value[] { this.fire, this.cactus, this.unloaded });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Value b(final Avoid avoid) {
        return avoid.fire;
    }
    
    public static Value e(final Avoid avoid) {
        return avoid.cactus;
    }
    
    public static Minecraft getMinecraft() {
        return Avoid.D;
    }
    
    public static Value M(final Avoid avoid) {
        return avoid.unloaded;
    }
    
    public static Minecraft getMinecraft1() {
        return Avoid.D;
    }
}
