package wtf.diablo.client.module.impl.misc;

import best.azura.eventbus.core.EventPriority;
import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import net.minecraft.util.BlockPos;
import wtf.diablo.client.event.impl.network.SendPacketEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.util.mc.player.InventoryUtil;

@ModuleMetaData(name = "Auto Tool", description = "Automatically swaps to the best item", category = ModuleCategoryEnum.MISC)
public final class AutoToolModule extends AbstractModule {

    @EventHandler(EventPriority.LOWER)
    public final Listener<SendPacketEvent> updateEventListener = e -> {
        if (mc.gameSettings.keyBindAttack.pressed) {
            if (mc.objectMouseOver != null) {
                final BlockPos pos = mc.objectMouseOver.getBlockPos();
                if (pos != null) {
                    mc.thePlayer.inventory.currentItem = InventoryUtil.getBestTool(mc.objectMouseOver.getBlockPos());
                    mc.playerController.updateController();
                }
            }
        }
    };

}
