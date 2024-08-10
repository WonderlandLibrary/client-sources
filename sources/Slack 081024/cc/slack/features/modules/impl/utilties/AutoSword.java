package cc.slack.features.modules.impl.utilties;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.player.InventoryUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C02PacketUseEntity;

@ModuleInfo(
        name = "AutoSword",
        category = Category.UTILITIES
)
public class AutoSword extends Module {

    @Listen
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packet = event.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                int slot = -1;
                float damage = 0;
                for (int i = 36; i < 45; i++) {
                    if (InventoryUtil.getSlot(i).getHasStack()) {
                        ItemStack itemStack = InventoryUtil.getSlot(i).getStack();
                        if (InventoryUtil.getDamage(itemStack) > damage) {
                            slot = i;
                            damage = InventoryUtil.getDamage(itemStack);
                        }
                    }
                }
                if (slot != -1) {
                    mc.thePlayer.inventory.currentItem = slot - 36;
                    mc.playerController.updateController();
                }
            }
        }
    }

}
