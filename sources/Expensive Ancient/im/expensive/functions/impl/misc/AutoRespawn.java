package im.expensive.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.screen.DeathScreen;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;

@FunctionRegister(name = "AutoRespawn", type = Category.Misc)
public class AutoRespawn extends Function {

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player == null || mc.world == null) return;

        if (mc.currentScreen instanceof DeathScreen && mc.player.deathTime > 5) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen(null);
        }
    }
}
