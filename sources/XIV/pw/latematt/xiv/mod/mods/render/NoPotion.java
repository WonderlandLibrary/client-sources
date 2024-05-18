package pw.latematt.xiv.mod.mods.render;

import org.lwjgl.input.Keyboard;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.PotionRenderEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

/**
 * @author Rederpz
 */
public class NoPotion extends Mod implements Listener<PotionRenderEvent> {
    public NoPotion() {
        super("NoPotion", ModType.RENDER, Keyboard.KEY_NONE);
    }

    @Override
    public void onEventCalled(PotionRenderEvent event) {
        event.setCancelled(true);
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
