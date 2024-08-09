package mathax.client.systems.modules.player;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.mathax.MouseScrollEvent;
import mathax.client.events.world.TickEvent;
import mathax.client.mixininterface.IClientPlayerInteractionManager;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.item.Items;

public class InventoryScroll extends Module {
    private boolean open;

    public InventoryScroll() {
        super(Categories.Misc, Items.WOODEN_HOE, "inventory-scroll", "Allows you to scroll in your hotbar while having a screen opened.");
    }

    @Override
    public boolean onActivate() {
        open = false;
        return false;
    }

    @EventHandler
    private void onPreTick(TickEvent.Pre event) {
        open = mc.currentScreen != null;
    }

    @EventHandler
    private void onMouseScroll(MouseScrollEvent event) {
        if (open) {
            int slot = mc.player.getInventory().selectedSlot;
            if (slot <= 0 && -event.value < 0) slot = 8;
            else if (slot >= 8 && -event.value > 0) slot = 0;
            else slot += (int) -event.value;

            mc.player.getInventory().selectedSlot = slot > 8 ? 8 : Math.max(slot, 0);
            ((IClientPlayerInteractionManager) mc.interactionManager).syncSelected();
        }
    }
}
