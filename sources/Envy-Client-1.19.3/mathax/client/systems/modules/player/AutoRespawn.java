package mathax.client.systems.modules.player;

import mathax.client.eventbus.EventHandler;
import mathax.client.eventbus.EventPriority;
import mathax.client.events.game.OpenScreenEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.systems.modules.Modules;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.item.Items;

public class AutoRespawn extends Module {
    public AutoRespawn() {
        super(Categories.Player, Items.COMMAND_BLOCK, "auto-respawn", "Automatically respawns after death.");
    }

    @EventHandler(priority = EventPriority.HIGH)
    private void onOpenScreenEvent(OpenScreenEvent event) {
        if (!(event.screen instanceof DeathScreen)) return;
        mc.player.requestRespawn();
        event.cancel();
    }
}
