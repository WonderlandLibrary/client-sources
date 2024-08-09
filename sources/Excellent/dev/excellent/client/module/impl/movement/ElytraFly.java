package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.time.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;

@ModuleInfo(name = "Elytra Fly", description = "Позволяет вам летать при помощи Элитры и феерверков.", category = Category.MOVEMENT)
public class ElytraFly extends Module {

    private final TimerUtil timerUtil = TimerUtil.create();
    public final TimerUtil timerUtil1 = TimerUtil.create();
    private final Listener<UpdateEvent> onUpdate = event -> {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA) {
                if (!mc.player.isOnGround() && !mc.player.isInWater() && !mc.player.isInLava() && !mc.player.isElytraFlying()) {
                    if (timerUtil1.hasReached(550)) {
                        mc.playerController.windowClick(0, 6, i, ClickType.SWAP, mc.player);
                        mc.player.startFallFlying();
                        mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                        mc.playerController.windowClick(0, 6, i, ClickType.SWAP, mc.player);
                        timerUtil1.reset();
                    }
                    // Использование фейерверка
                    if (timerUtil.hasReached(800)) { // Таймер фейерверка
                        if (mc.player.isElytraFlying()) {
                            inventorySwapClick(Items.FIREWORK_ROCKET);
                            timerUtil.reset();
                        }
                    }
                }
            }
        }
    };

    public static boolean doesHotbarHaveItem(Item item) {
        for (int i = 0; i < 9; ++i) {
            mc.player.inventory.getStackInSlot(i);
            if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                return true;
            }
        }
        return false;
    }

    public static int getItemIndex(Item item) {
        for (int i = 0; i < 45; i++) {
            if (Minecraft.getInstance().player.inventory.getStackInSlot(i).getItem() == item) {
                return i;
            }
        }
        return -1;
    }

    public static void inventorySwapClick(Item item) {

        if (getItemIndex(item) == -1) {
            return;
        }

        if (doesHotbarHaveItem(item)) {
            for (int i = 0; i < 9; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                    if (i != mc.player.inventory.currentItem) {
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(i));
                    }
                    mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    if (i != mc.player.inventory.currentItem) {
                        mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    }
                    break;
                }
            }
        }
        if (!doesHotbarHaveItem(item)) {
            for (int i = 0; i < 36; i++) {
                if (mc.player.inventory.getStackInSlot(i).getItem() == item) {
                    mc.playerController.windowClick(0, i, mc.player.inventory.currentItem % 8 + 1, ClickType.SWAP, mc.player);
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem % 8 + 1));
                    mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                    mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                    mc.playerController.windowClick(0, i, mc.player.inventory.currentItem % 8 + 1, ClickType.SWAP, mc.player);
                    break;
                }
            }
        }
    }
}
