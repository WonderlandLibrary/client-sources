package wtf.shiyeno.modules.impl.player;

import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.Screen;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventUpdate;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;

@FunctionAnnotation(
        name = "AutoRespawn",
        type = Type.Player
)
public class AutoRespawnFunction extends Function {
    public AutoRespawnFunction() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventUpdate && mc.currentScreen instanceof DeathScreen && mc.player.deathTime > 2) {
            mc.player.respawnPlayer();
            mc.displayGuiScreen((Screen)null);
        }
    }
}