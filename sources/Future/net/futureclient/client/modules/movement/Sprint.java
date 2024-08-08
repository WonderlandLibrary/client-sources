package net.futureclient.client.modules.movement;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.futureclient.client.ZG;
import net.futureclient.client.modules.movement.sprint.Listener2;
import net.futureclient.client.modules.movement.sprint.Listener1;
import net.futureclient.client.n;
import net.futureclient.client.utils.Value;
import net.futureclient.client.Category;
import net.futureclient.client.hC;
import net.futureclient.client.R;
import net.futureclient.client.Ea;

public class Sprint extends Ea
{
    private R<hC.OA> mode;
    
    public Sprint() {
        super("Sprint", new String[] { "Sprint", "Spritn", "Spri", "Sprnt" }, true, -7030642, Category.MOVEMENT);
        this.mode = new R<hC.OA>(hC.OA.D, new String[] { "Mode", "m", "Type", "Mod" });
        this.M(new Value[] { this.mode });
        this.M(new n[] { new Listener1(this), new Listener2(this) });
    }
    
    private boolean b() {
        return !Sprint.D.player.isSneaking() && !Sprint.D.player.collidedHorizontally && ZG.b() && Sprint.D.player.getFoodStats().getFoodLevel() > 6.0f;
    }
    
    public void b() {
        super.b();
        KeyBinding.setKeyBindState(Sprint.D.gameSettings.keyBindSprint.getKeyCode(), false);
    }
    
    public static Minecraft getMinecraft() {
        return Sprint.D;
    }
    
    public static Minecraft getMinecraft1() {
        return Sprint.D;
    }
    
    public static R M(final Sprint sprint) {
        return sprint.mode;
    }
    
    public static boolean M(final Sprint sprint) {
        return sprint.b();
    }
}
