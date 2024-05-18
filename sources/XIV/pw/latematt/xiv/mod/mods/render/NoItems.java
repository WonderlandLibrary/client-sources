package pw.latematt.xiv.mod.mods.render;

import net.minecraft.entity.item.EntityItem;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.event.Listener;
import pw.latematt.xiv.event.events.ItemRenderEvent;
import pw.latematt.xiv.mod.Mod;
import pw.latematt.xiv.mod.ModType;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Matthew
 */
public class NoItems extends Mod implements Listener<ItemRenderEvent> {
    private final List<EntityItem> itemsToBeAdded = new ArrayList<>();

    public NoItems() {
        super("NoItems", ModType.RENDER);
    }

    @Override
    public void onEventCalled(ItemRenderEvent event) {
        event.setCancelled(true);
        mc.theWorld.removeEntity(event.getItem());
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
