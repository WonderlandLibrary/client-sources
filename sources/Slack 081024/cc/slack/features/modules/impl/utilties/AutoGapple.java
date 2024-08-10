package cc.slack.features.modules.impl.utilties;

import cc.slack.features.modules.api.settings.impl.NumberValue;
import  cc.slack.utils.player.InventoryUtil;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.other.TimeUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.init.Items;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;

@ModuleInfo(
        name = "AutoGapple",
        category = Category.UTILITIES
)
public class AutoGapple extends Module {


    private final NumberValue<Double> healthValue = new NumberValue<>("Health", 10.0D, 1.0D, 20.0D, 0.5D);
    private final NumberValue<Integer> delayValue = new NumberValue<>("Delay", 100, 50, 1000, 50);

    private final TimeUtil timer = new TimeUtil();

    public AutoGapple() {
        addSettings(healthValue, delayValue);
    }


    @Listen
    public void onUpdate (UpdateEvent event) {
        if (!InventoryUtil.hasItemWithCheck(Items.golden_apple, true, true)) return;

        if (InventoryUtil.hasItemWithCheck(Items.golden_apple, false, true) && !InventoryUtil.hasItemWithCheck(Items.golden_apple, true, false)) {
            int emptySlot = InventoryUtil.getEmptySlot() == -1 ? 7 : InventoryUtil.getEmptySlot() - 36;

            int gappleSlot = InventoryUtil.findItem(9, 36, Items.golden_apple);

            if(gappleSlot == -1) return;

            InventoryUtil.swap(gappleSlot, emptySlot);
        }

        if(mc.thePlayer.getHealth() < 0 || mc.thePlayer.getHealth() > healthValue.getValue() || !timer.hasReached(delayValue.getValue())) return;

        int gappleSlot = InventoryUtil.findItem(36, 45, Items.golden_apple);
        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(gappleSlot - 36));
        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(BlockPos.ORIGIN, 255, mc.thePlayer.inventory.mainInventory[gappleSlot - 36], 0, 0, 0));
        for(int i = 0; i < 35; i++) {
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer(false));
        }
        mc.getNetHandler().addToSendQueue(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        if(mc.thePlayer.isBlocking()) {
            mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.getCurrentEquippedItem()));
        }

        timer.reset();
    }
}

