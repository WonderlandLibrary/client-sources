/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.math.StopWatch;
import mpp.venusfr.utils.player.InventoryUtil;
import mpp.venusfr.utils.player.MoveUtils;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.AirItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;

@FunctionRegister(name="ElytraFly", type=Category.Movement)
public class ElytraFly
extends Function {
    StopWatch stopWatch = new StopWatch();
    int oldSlot = -1;
    int bestSlot = -1;
    long delay;

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        this.bestSlot = InventoryUtil.getInstance().findBestSlotInHotBar();
        boolean bl = ElytraFly.mc.player.inventory.getStackInSlot(this.bestSlot).getItem() != Items.AIR;
        int n = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, true);
        int n2 = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.FIREWORK_ROCKET, false);
        if (InventoryUtil.getInstance().getSlotInInventory(Items.FIREWORK_ROCKET) == -1) {
            return;
        }
        int n3 = InventoryUtil.getInstance().getSlotInInventoryOrHotbar(Items.ELYTRA, false);
        if (n3 == -1) {
            this.print("\u0412\u043e\u0437\u044c\u043c\u0438\u0442\u0435 \u044d\u043b\u0438\u0442\u0440\u0443 \u0432 \u0445\u043e\u0442\u0431\u0430\u0440.");
            this.toggle();
            return;
        }
        if (ElytraFly.mc.player.isOnGround() && !ElytraFly.mc.gameSettings.keyBindJump.pressed) {
            ElytraFly.mc.gameSettings.keyBindJump.setPressed(false);
        }
        if (!(ElytraFly.mc.player.isInWater() || ElytraFly.mc.player.isOnGround() || ElytraFly.mc.player.isElytraFlying())) {
            if (!(ElytraFly.mc.player.inventory.armorItemInSlot(2).getItem() instanceof ElytraItem)) {
                ElytraFly.mc.playerController.windowClick(0, 6, n3, ClickType.SWAP, ElytraFly.mc.player);
                ElytraFly.mc.player.connection.sendPacket(new CEntityActionPacket(ElytraFly.mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                ElytraFly.mc.playerController.windowClick(0, 6, n3, ClickType.SWAP, ElytraFly.mc.player);
                if (this.stopWatch.isReached(500L)) {
                    this.swapAndUseFireWorkFromInv(n, n2, bl);
                    this.stopWatch.reset();
                }
            } else if (this.bestSlot != -1) {
                ElytraFly.mc.playerController.windowClick(0, 6, this.bestSlot, ClickType.SWAP, ElytraFly.mc.player);
            }
        }
    }

    public static int findNullSlot() {
        for (int i = 0; i < 36; ++i) {
            ItemStack itemStack = ElytraFly.mc.player.inventory.getStackInSlot(i);
            if (!(itemStack.getItem() instanceof AirItem)) continue;
            if (i < 9) {
                i += 36;
            }
            return i;
        }
        return 0;
    }

    private void swapAndUseFireWorkFromInv(int n, int n2, boolean bl) {
        if (n2 == -1) {
            if (n >= 0) {
                InventoryUtil.moveItem(n, this.bestSlot + 36, bl);
                if (bl && this.oldSlot == -1) {
                    this.oldSlot = n;
                }
                ElytraFly.mc.player.connection.sendPacket(new CHeldItemChangePacket(this.bestSlot));
                ElytraFly.mc.playerController.syncCurrentPlayItem();
                ElytraFly.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(Hand.MAIN_HAND));
                ElytraFly.mc.player.swingArm(Hand.MAIN_HAND);
                ElytraFly.mc.player.connection.sendPacket(new CHeldItemChangePacket(ElytraFly.mc.player.inventory.currentItem));
                ElytraFly.mc.playerController.syncCurrentPlayItem();
                MoveUtils.setMotion(MoveUtils.getMotion());
                if (this.oldSlot != -1) {
                    ElytraFly.mc.playerController.windowClick(0, this.oldSlot, 0, ClickType.PICKUP, ElytraFly.mc.player);
                    ElytraFly.mc.playerController.windowClick(0, this.bestSlot + 36, 0, ClickType.PICKUP, ElytraFly.mc.player);
                    ElytraFly.mc.playerController.windowClickFixed(0, this.oldSlot, 0, ClickType.PICKUP, ElytraFly.mc.player, 100);
                    this.oldSlot = -1;
                    this.bestSlot = -1;
                    ElytraFly.mc.player.resetActiveHand();
                }
            } else {
                this.print("\u0424\u0435\u0435\u0440\u0432\u0435\u0440\u043a\u0438 \u043d\u0435 \u043d\u0430\u0439\u0434\u0435\u043d\u044b!");
            }
        } else {
            this.useItem(n2, Hand.MAIN_HAND);
        }
    }

    private void useItem(int n, Hand hand) {
        if (ElytraFly.mc.player.getHeldItemMainhand().getItem() != Items.FIREWORK_ROCKET) {
            ElytraFly.mc.player.connection.sendPacket(new CHeldItemChangePacket(n));
        }
        ElytraFly.mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        ElytraFly.mc.player.swingArm(Hand.MAIN_HAND);
        if (ElytraFly.mc.player.getHeldItemMainhand().getItem() != Items.FIREWORK_ROCKET) {
            ElytraFly.mc.player.connection.sendPacket(new CHeldItemChangePacket(ElytraFly.mc.player.inventory.currentItem));
        }
    }
}

