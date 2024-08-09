package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.chat.ChatUtil;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.util.Hand;
import net.minecraft.util.text.TextFormatting;

@ModuleInfo(name = "Elytra Speed", description = "Позволяет вам ускоряться при помощи Элитры и феерверков.", category = Category.MOVEMENT)
public class ElytraSpeed extends Module {
    private final TimerUtil timerUtil = TimerUtil.create();

    int oldItem = -1;
    private final NumberValue speedBoost = new NumberValue("Скорость", this, 0.8, 0.3, 0.8, 0.1);
    public final BooleanValue autoJump = new BooleanValue("Авто прыжок", this, false);
    public final BooleanValue safeMode = new BooleanValue("Безопасный режим", this, false);

    boolean test1 = false;

    private final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.collidedHorizontally) {
            if (safeMode.getValue()) {
                ChatUtil.addText(TextFormatting.RED + "Вы столкнулись с блоком, перезапустите функцию!");
                toggle();
                return;
            }
        }
        if (getItemIndex(Items.FIREWORK_ROCKET) == -1 || mc.player.collidedHorizontally || !doesHotbarHaveItem(Items.ELYTRA)) {
            return;
        }
        if (autoJump.getValue() && !mc.gameSettings.keyBindJump.isKeyDown() && mc.player.isOnGround()) {
            mc.gameSettings.keyBindJump.setPressed(true);
        }
        if (mc.player.getActiveHand() == Hand.MAIN_HAND) {
            mc.playerController.onStoppedUsingItem(mc.player);
        }
        mc.gameSettings.keyBindBack.setPressed(false);
        mc.gameSettings.keyBindLeft.setPressed(false);
        mc.gameSettings.keyBindRight.setPressed(false);

        boolean test = false;
        if (mc.player.isOnGround() || mc.player.fallDistance != 0 && mc.gameSettings.keyBindJump.isKeyDown()) {
            if (timerUtil.hasReached(600)) {
                test = true;
            }
        }
        if (mc.player.isOnGround() && test) {
            mc.gameSettings.keyBindForward.setPressed(false);
            return;
        }
        if (mc.player.fallDistance == 0 && !mc.player.isOnGround()) {
            test = false;
        }

        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.ELYTRA) {
                if (!mc.player.isOnGround() && !mc.player.isInWater() && !mc.player.isInLava()) {
                    if (timerUtil.hasReached(550)) {
                        mc.playerController.windowClick(0, 6, i, ClickType.SWAP, mc.player);
                        mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_FALL_FLYING));
                        float speed = 0.9f + speedBoost.getValue().floatValue();
                        MoveUtil.setSpeed(speed);
                        mc.playerController.windowClick(0, 6, i, ClickType.SWAP, mc.player);
                        if (timerUtil.hasReached(550)) {
                            inventorySwapClick(Items.FIREWORK_ROCKET);
                            timerUtil.reset();
                        }
                    }
                }
            }
        }
    };

    private final Listener<MotionEvent> onMotion = event -> {
        mc.player.rotationPitchHead = 15;
        event.setPitch(15);
    };

    private final Listener<PacketEvent> onPacket = event -> {
        IPacket<?> packet = event.getPacket();
        if (packet instanceof SEntityMetadataPacket) {
            if (((SEntityMetadataPacket) packet).getEntityId() == mc.player.getEntityId()) {
                if (!mc.player.isElytraFlying()) {
                    event.setCancelled();
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

    @Override
    public void onDisable() {
        super.onDisable();
        if (oldItem != -1) {
            if (mc.player.inventory.armorInventory.get(2).getItem() == Items.ELYTRA) {
                mc.playerController.windowClick(0, oldItem < 9 ? oldItem + 36 : oldItem, 38, ClickType.SWAP, mc.player);
            }
            oldItem = -1;
        }
    }
}
