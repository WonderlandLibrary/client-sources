package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventMotion;
import fun.ellant.events.EventPacket;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.utils.player.InventoryUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import fun.ellant.utils.player.timerHelper;
@FunctionRegister(name = "ElytraHighJump", type = Category.MOVEMENT, desc = "Очень высоко прыгает на элитрах")
public class ElytraHighJump extends Function {
    private final timerHelper timerUtil = new timerHelper();

    public ElytraHighJump() {
    }
    private final Minecraft mc = Minecraft.getInstance();


    @Subscribe
    public void onEvent(EventMotion event) {
        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            float test = mc.player.rotationPitch - 90.0F;
            event.setPitch(test);
            mc.player.rotationPitchHead = test;
        }

        if (mc.gameSettings.keyBindJump.isKeyDown()) {
            if (mc.player.rotationPitch > 15.0F) {
                mc.player.rotationPitch = 20.0F;
            }

            for (int i = 0; i < 9; ++i) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA && !mc.player.isOnGround() && mc.player.fallDistance == 0.0F && this.timerUtil.hasTimeElapsed(200L, false)) {
                    mc.playerController.windowClick(0, 6, i, ClickType.SWAP, mc.player);
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                    mc.player.motion.y = 1.399999976158142;
                    mc.playerController.windowClick(0, 6, i, ClickType.SWAP, mc.player);
                    if (this.timerUtil.hasTimeElapsed(700L, false)) {
                        InventoryUtil.inventorySwapClick(Items.FIREWORK_ROCKET);
                        this.timerUtil.reset();
                    }
                }
            }
        }
    }
    @Subscribe
    public void onPacket(EventPacket event) {
        if (event.getPacket() instanceof SEntityMetadataPacket && ((SEntityMetadataPacket) event.getPacket()).getEntityId() == mc.player.getEntityId()) {
            event.cancel();
        }
    }
    public void onDisable() {
        super.onDisable();
    }


}
