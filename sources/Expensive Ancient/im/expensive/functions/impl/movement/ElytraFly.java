package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;

import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.player.InventoryUtil;
import im.expensive.utils.player.MoveUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.*;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;

@FunctionRegister(name = "ElytraFly", type = Category.Movement)
public class ElytraFly extends Function {
    StopWatch stopWatch = new StopWatch();
    int oldSlot = -1;
    int bestSlot = -1;
    long delay;

    @Subscribe
    public void onUpdate(EventUpdate e) {
        bestSlot = InventoryUtil.getInstance().findBestSlotInHotBar();
        boolean slotNotNull = mc.player.inventory.getStackInSlot(bestSlot).getItem() != Items.AIR;
        int invSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, false);
        int hbSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, true);

        if (InventoryUtil.getInstance().getSlotInInventory(Items.FIREWORK_ROCKET) == -1) {
            return;
        }

        int elytraSlot = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.ELYTRA, true);
        if (elytraSlot == -1) {
            print("Возьмите элитру в хотбар.");
            toggle();
            return;
        }

        if (mc.player.isOnGround() && !mc.gameSettings.keyBindJump.pressed) {
            mc.gameSettings.keyBindJump.setPressed(true);
        }
        if (!mc.player.isInWater() && !mc.player.isOnGround() && !mc.player.isElytraFlying()) {
            if (!(mc.player.inventory.armorItemInSlot(2).getItem() instanceof ElytraItem)) {
                mc.playerController.windowClick(0, 6, elytraSlot, ClickType.SWAP, mc.player);
                mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                mc.playerController.windowClick(0, 6, elytraSlot, ClickType.SWAP, mc.player);
                if (this.stopWatch.isReached(500)) {
                    swapAndUseFireWorkFromInv(invSlot, hbSlot, slotNotNull);
                    this.stopWatch.reset();
                }
            } else if (bestSlot != -1){
                mc.playerController.windowClick(0, 6, bestSlot, ClickType.SWAP, mc.player);
            }
        }
    }

    public static int findNullSlot() {
        for (int i = 0; i < 36; i++) {
            ItemStack stack = mc.player.inventory.getStackInSlot(i);
            if (stack.getItem() instanceof AirItem) {
                if (i < 9) {
                    i += 36;
                }
                return i;
            }
        }
        return 999;
    }

    private void swapAndUseFireWorkFromInv(int slot, int hbSlot, boolean slotNotNull) {
        if (hbSlot == -1) {
            if (slot >= 0) {
                InventoryUtil.moveItem(slot, bestSlot + 36, slotNotNull);
                if (slotNotNull && oldSlot == -1) {
                    oldSlot = slot;
                }

                mc.player.connection.sendPacket(new CHeldItemChangePacket(bestSlot));
                mc.playerController.syncCurrentPlayItem();
                mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                mc.player.swingArm(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
                mc.playerController.syncCurrentPlayItem();
                MoveUtils.setMotion(MoveUtils.getMotion());
                if (oldSlot != -1) {
                    mc.playerController.windowClick(0, oldSlot, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, mc.player);
                    mc.playerController.windowClickFixed(0, oldSlot, 0, ClickType.PICKUP, mc.player, 100);
                    oldSlot = -1;
                    bestSlot = -1;
                    mc.player.resetActiveHand();
                }

            } else {
                print("Феерверки не найдены!");
            }
        } else {
            useItem(hbSlot, Hand.MAIN_HAND);
        }
    }

    private void useItem(int slot, Hand hand) {
        if (mc.player.getHeldItemMainhand().getItem() != Items.FIREWORK_ROCKET) {
            mc.player.connection.sendPacket(new CHeldItemChangePacket(slot));
        }
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        mc.player.swingArm(Hand.MAIN_HAND);
        if (mc.player.getHeldItemMainhand().getItem() != Items.FIREWORK_ROCKET) {
            mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
        }
    }

}
