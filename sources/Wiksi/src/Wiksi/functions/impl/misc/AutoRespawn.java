package src.Wiksi.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.screen.DeathScreen;
import src.Wiksi.events.EventUpdate;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import net.minecraft.client.gui.screen.Screen;

@FunctionRegister(name = "AutoRespawn", type = Category.Misc)
public class AutoRespawn extends Function {

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (mc.player == null || mc.world == null) return;

        if (mc.currentScreen instanceof DeathScreen && mc.player.deathTime > 5) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen((Screen) null);
        }
    }
}
