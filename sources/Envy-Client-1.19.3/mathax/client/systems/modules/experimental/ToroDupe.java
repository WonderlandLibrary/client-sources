package mathax.client.systems.modules.experimental;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import net.minecraft.client.gui.screen.ingame.GenericContainerScreen;
import net.minecraft.entity.Entity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.item.Items;
import net.minecraft.screen.slot.SlotActionType;
import net.minecraft.util.Hand;

public class ToroDupe extends Module {
    public ToroDupe() {
        super(Categories.Experimental, Items.WANDERING_TRADER_SPAWN_EGG, "ToroDupe", "Toro Dupe by Colonizadores.");
    }

    private int timer = 0;
    private boolean drop = false;
    private boolean hasDismount = false;
    private Entity boat = null;

    private void dropAll() {
        if (mc.currentScreen instanceof GenericContainerScreen) {
            for (int i = 0; i<27; i++) {
                if (!mc.player.currentScreenHandler.getSlot(i).getStack().isEmpty()) {
                    mc.interactionManager.clickSlot(mc.player.currentScreenHandler.syncId, i, -1, SlotActionType.THROW, mc.player);
                }
            }
        }
    }

    @EventHandler
    private void onTickChest(TickEvent.Post event) {
        if (!mc.player.isSneaking()) {
            mc.options.sneakKey.setPressed(true);
        }
        Iterable<Entity> entities = mc.world.getEntities();
        if (entities == null) return;
        if(mc.currentScreen instanceof GenericContainerScreen) return;
        entities.forEach(entity -> {
            if (entity instanceof BoatEntity && mc.player.distanceTo(entity) < 5) {
                if (!entity.hasPassengers()) {
                    if (mc.player.isSneaking()) {
                        if (!(mc.currentScreen instanceof GenericContainerScreen)) {
                            mc.interactionManager.interactEntity(mc.player, entity, Hand.MAIN_HAND);
                        }
                    }
                }
            }
        });
    }

    @EventHandler
    private void onTickBoat(TickEvent.Post event) {
        Iterable<Entity> entities = mc.world.getEntities();
        entities.forEach(entity -> {
            if (mc.player.distanceTo(entity) > 5) return;
            if (entity instanceof BoatEntity) {
                boat = entity;
            }
        });
    }

    @EventHandler
    private void onTickDrop(TickEvent.Post event) {
        if (boat != null && !boat.hasPassengers()) {
            hasDismount = true;
        }
        if (hasDismount) {
            timer++;
            if (timer >= 10 && boat.hasPassengers()) {
                dropAll();
                timer = 0;
                hasDismount = false;
            }
        }
    }
}
