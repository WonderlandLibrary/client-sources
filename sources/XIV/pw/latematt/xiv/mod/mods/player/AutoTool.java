package pw.latematt.xiv.mod.mods.player;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.ClickBlockEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;
import pw.latematt.xiv.utils.BlockUtils;

/**
 * @author Matthew
 */
public class AutoTool extends Mod implements Listener<ClickBlockEvent> {
    public AutoTool() {
        super("AutoTool", ModType.PLAYER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEventCalled(ClickBlockEvent event) {
        mc.thePlayer.inventory.currentItem = BlockUtils.getBestTool(event.getPos());
        mc.playerController.updateController();
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
