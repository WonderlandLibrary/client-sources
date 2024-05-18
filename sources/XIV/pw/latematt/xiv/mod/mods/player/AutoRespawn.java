package pw.latematt.xiv.mod.mods.player;

import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.PlayerDeathEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Matthew
 */
public class AutoRespawn extends Mod implements Listener<PlayerDeathEvent> {
    public AutoRespawn() {
        super("AutoRespawn", ModType.PLAYER);
    }

    @Override
    public void onEventCalled(PlayerDeathEvent event) {
        mc.thePlayer.respawnPlayer();
    }

    @Override
    public void onEnabled() {
        XIV.getInstance().getListenerManager().add(this);
    }

    @Override
    public void onDisabled() {
        XIV.getInstance().getListenerManager().remove(this);
    }
}
