package dev.darkmoon.client.module.impl.combat;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.CPacketPlayerDigging;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;

@ModuleAnnotation(name = "FastBow", category = Category.COMBAT)
public class FastBow extends Module {
    private final NumberSetting delay = new NumberSetting("Delay", 3.0f, 1.0f, 10, 0.5f);

    @EventTarget
    public void onUpdate(EventUpdate event) {
        if (mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && mc.player.isHandActive()
                && mc.player.getItemInUseMaxCount() >= delay.get()) {
            mc.player.connection.sendPacket(new CPacketPlayerDigging(CPacketPlayerDigging.Action.RELEASE_USE_ITEM,
                    BlockPos.ORIGIN, mc.player.getHorizontalFacing()));
            mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
            mc.player.stopActiveHand();
        }
    }
}
