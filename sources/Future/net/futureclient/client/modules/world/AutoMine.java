package net.futureclient.client.modules.world;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.modules.world.automine.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.Category;
import net.futureclient.client.Ea;

public class AutoMine extends Ea
{
    public AutoMine() {
        super("AutoMine", new String[] { "AutoMine", "AM" }, true, -10111537, Category.WORLD);
        this.M(new n[] { new Listener1(this) });
    }
    
    public void b() {
        super.b();
        if (AutoMine.D.player != null) {
            KeyBinding.setKeyBindState(AutoMine.D.gameSettings.keyBindAttack.getKeyCode(), false);
        }
    }
    
    public static Minecraft getMinecraft() {
        return AutoMine.D;
    }
}
