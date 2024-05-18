package pw.latematt.xiv.mod.mods.render;

import net.minecraft.entity.item.EntityItem;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.HurtRenderEvent;
import pw.latematt.xiv.event.events.ItemRenderEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Rederpz
 */
public class AntiHurtcam extends Mod implements Listener<HurtRenderEvent> {
    public AntiHurtcam() {
        super("AntiHurtcam", ModType.RENDER);
    }

    @Override
    public void onEventCalled(HurtRenderEvent event) {
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
