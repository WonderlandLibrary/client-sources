package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventUpdate;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.math.StopWatch;
import fun.ellant.utils.player.InventoryUtil;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;

@FunctionRegister(name="LeaveFly", type=Category.MOVEMENT, desc = "Позволяет ливать на элитрах")
public class LeaveFly
        extends Function {
    private final StopWatch timerUtil = new StopWatch();

    @Subscribe
    public void onUpdate(EventUpdate event) {
        if (InventoryUtil.getItemSlot(Items.FIREWORK_ROCKET) == -1) {
            return;
        }
        if (!InventoryUtil.doesHotbarHaveItem(Items.ELYTRA)) {
            return;
        }
        if (LeaveFly.mc.player.isOnGround() && !LeaveFly.mc.gameSettings.keyBindJump.isKeyDown()) {
            LeaveFly.mc.gameSettings.keyBindJump.setPressed(true);
        }
        if (!(LeaveFly.mc.player.isInWater() || LeaveFly.mc.player.isOnGround() || LeaveFly.mc.player.isElytraFlying())) {
            for (int i = 0; i < 36; ++i) {
                if (LeaveFly.mc.player.inventory.getStackInSlot(i).getItem() != Items.ELYTRA) continue;
                LeaveFly.mc.playerController.windowClick(0, 6, i, ClickType.SWAP, LeaveFly.mc.player);
                LeaveFly.mc.player.connection.sendPacket(new CEntityActionPacket(LeaveFly.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                LeaveFly.mc.playerController.windowClick(0, 6, i, ClickType.SWAP, LeaveFly.mc.player);
                if (!this.timerUtil.isReached(600L)) continue;
                InventoryUtil.inventorySwapClick(Items.FIREWORK_ROCKET);
                this.timerUtil.reset();
            }
        }
    }
}
