package net.futureclient.client.modules.world;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.client.Minecraft;
import net.futureclient.client.modules.world.fastplace.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.utils.Value;
import net.futureclient.client.utils.NumberValue;
import net.futureclient.client.Ea;

public class FastPlace extends Ea
{
    private NumberValue delay;
    private Value<Boolean> autoPlace;
    private Value<Boolean> autoJump;
    private Value<Boolean> autoSwitch;
    
    public FastPlace() {
        super("FastPlace", new String[] { "FastPlace", "place", "fp" }, true, -2525659, Category.WORLD);
        this.delay = new NumberValue(1.0f, 0.0f, 4.0f, 1, new String[] { "Delay", "d" });
        this.autoPlace = new Value<Boolean>(false, new String[] { "AutoPlace", "click", "clicker" });
        this.autoJump = new Value<Boolean>(false, new String[] { "AutoJump", "Jump" });
        this.autoSwitch = new Value<Boolean>(false, new String[] { "AutoSwitch", "Switch" });
        this.M(new Value[] { this.delay, this.autoPlace, this.autoJump, this.autoSwitch });
        this.M(new n[] { new Listener1(this) });
    }
    
    public static Minecraft getMinecraft() {
        return FastPlace.D;
    }
    
    public static Minecraft getMinecraft1() {
        return FastPlace.D;
    }
    
    public void b() {
        super.b();
        KeyBinding.setKeyBindState(FastPlace.D.gameSettings.keyBindJump.getKeyCode(), false);
    }
    
    public static Minecraft getMinecraft2() {
        return FastPlace.D;
    }
    
    public static Value b(final FastPlace fastPlace) {
        return fastPlace.autoSwitch;
    }
    
    public static Value e(final FastPlace fastPlace) {
        return fastPlace.autoJump;
    }
    
    public static Minecraft getMinecraft3() {
        return FastPlace.D;
    }
    
    public static Minecraft getMinecraft4() {
        return FastPlace.D;
    }
    
    public static Minecraft getMinecraft5() {
        return FastPlace.D;
    }
    
    public static Value M(final FastPlace fastPlace) {
        return fastPlace.autoPlace;
    }
    
    public static NumberValue M(final FastPlace fastPlace) {
        return fastPlace.delay;
    }
}
