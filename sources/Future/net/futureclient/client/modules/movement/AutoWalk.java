package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.modules.movement.autowalk.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class AutoWalk extends Ea
{
    public AutoWalk() {
        super("AutoWalk", new String[] { "AutoWalk", "AutoRun" }, true, -6710870, Category.MOVEMENT);
        this.M(new n[] { new Listener1(this) });
    }
    
    public void b() {
        super.b();
        if (AutoWalk.D.player != null) {
            KeyBinding.setKeyBindState(AutoWalk.D.gameSettings.keyBindForward.getKeyCode(), false);
        }
    }
    
    public static Minecraft getMinecraft() {
        return AutoWalk.D;
    }
    
    public static Minecraft getMinecraft1() {
        return AutoWalk.D;
    }
}
