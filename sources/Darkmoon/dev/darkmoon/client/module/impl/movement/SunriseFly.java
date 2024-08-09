package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.input.EventSync;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventMotion;
import dev.darkmoon.client.event.player.EventMove;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.manager.notification.NotificationType;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.misc.TimerHelper;
import dev.darkmoon.client.utility.move.MovementUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import it.unimi.dsi.fastutil.booleans.BooleanSet;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;
import org.lwjgl.input.Keyboard;

@ModuleAnnotation(name = "SunriseFly", category = Category.MOVEMENT)
public class SunriseFly extends Module {
    private final NumberSetting xzSpeed = new NumberSetting("XZ-Speed", 1.55f, 0.1f, 10f, 0.1f);
    public static final BooleanSetting fireworks = new BooleanSetting("Fireworks", true);
    public final BooleanSetting bpsBypass = new BooleanSetting("Insane", true);
    public static NumberSetting fireSlot = new NumberSetting("Firework-Slot", 0, 0, 8, 1, () -> fireworks.get());
    private final NumberSetting speedY = new NumberSetting("Speed-Y", 0.1f, 0.1f, 10f, 0.01f);
    private boolean hasElytra, infiniteFlag, hasTouchedGround, elytraEquiped, flying, startFallFlying, ground;
    private float acceleration, accelerationY, height, prevClientPitch, infinitePitch, lastInfinitePitch;
    public static long lastStartFalling;


    @Override
    public void onEnable() {
        infiniteFlag = false;
        acceleration = 0;
        accelerationY = 0;
        ground = false;
        if (mc.player != null) {
            height = (float) mc.player.getPosY();
            if (!mc.player.isCreative()) mc.player.capabilities.allowFlying = false;
            mc.player.capabilities.isFlying = false;
        }
        hasElytra = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            acceleration = 0;
            ground = false;
            super.onDisable();
        }
    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if (mc.player.isInWater()) {
            this.setToggled(false);
        }
        sunriseInit();
    }

    private void sunriseInit() {
        if (mc.player.onGround && !mc.player.isInWater() || mc.player.fallDistance < 0.35) {
            mc.gameSettings.keyBindForward.pressed = false;
            mc.gameSettings.keyBindRight.pressed = false;
            mc.gameSettings.keyBindBack.pressed = false;
        }
        if (mc.player.collidedHorizontally) {
            acceleration = 0;
        }
        int elytra = InventoryUtility.getItemSlot(Items.ELYTRA);
        if (elytra == -1) return;
        if (mc.player.onGround && !mc.player.isInWater()) {
            ground = true;
            mc.player.jump();
            acceleration = 0;
            return;
        }
        if (mc.player.fallDistance <= 0) {
            return;
        }
        if (mc.player.fallDistance > 0.25f) {
            if (mc.gameSettings.keyBindSneak.pressed) {
                mc.player.setVelocity(mc.player.motionX, mc.player.motionY -= speedY.get(), mc.player.motionZ);
            } else if (mc.gameSettings.keyBindJump.pressed) {
                mc.player.setVelocity(mc.player.motionX, mc.player.motionY += speedY.get(), mc.player.motionZ);
            } else {
                takeOnChestPlate();
                if (mc.player.ticksExisted % 8 == 0) {
                    matrixDisabler(elytra);
                }
                if (bpsBypass.get()) {
                    MovementUtility.setMotion(Math.min((acceleration = (acceleration + 5.34F / xzSpeed.get())) / 100.0F, xzSpeed.get()));
                } else {
                    MovementUtility.setMotion(Math.min((acceleration = (acceleration + 7F / xzSpeed.get())) / 100.0F, xzSpeed.get()));
                }
                if (!MovementUtility.isMoving()) {
                    acceleration = 0;
                }
                if (mc.player.fallDistance > 0.35f)
                    mc.player.setVelocity(mc.player.motionX, -0.0033F, mc.player.motionZ);
            }
        }
    }
    @EventTarget
    public void onEvent(EventReceivePacket event) {
            if(event.getPacket() instanceof SPacketPlayerPosLook) {
                if(!mc.player.onGround) {
                    mc.player.setVelocity(mc.player.motionX, -0.0033F, mc.player.motionZ);
                }
                acceleration = 0;
        }
    }
 //   }
 public static void useFireworks() {
     Utility.mc.player.connection.sendPacket(new CPacketHeldItemChange(fireSlot.getInt()));
     Utility.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
     Utility.mc.player.connection.sendPacket(new CPacketHeldItemChange(Utility.mc.player.inventory.currentItem));
 }
    public static void matrixDisabler(int elytra) {
        elytra = elytra >= 0 && elytra < 9 ? elytra + 36 : elytra;
        if (elytra != -2) {
            mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        }
        mc.player.connection.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));

        if (elytra != -2) {
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
        }
        lastStartFalling = System.currentTimeMillis();
    }
    private void takeOnElytra() {
        int elytra = InventoryUtility.getItemSlot(Items.ELYTRA);
        if (elytra == -1) return;
        elytra = elytra >= 0 && elytra < 9 ? elytra + 36 : elytra;
        if (elytra != -2) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, elytra, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, elytra, 1, ClickType.PICKUP, mc.player);
            NetHandlerPlayClient var2 = mc.getConnection();
            var2.sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        }
    }
    private int getChestPlateSlot() {
        Item[] items = {Items.DIAMOND_CHESTPLATE, Items.IRON_CHESTPLATE, Items.GOLDEN_CHESTPLATE, Items.CHAINMAIL_CHESTPLATE, Items.LEATHER_CHESTPLATE};
        for (Item item : items) {
            int slot = InventoryUtility.getItemSlot(item);
            if (slot != -1) {
                return slot;
            }
        }
        return -1;
    }
    private void takeOnChestPlate() {
        int slot = getChestPlateSlot();
        if (slot == -1) return;
        if (slot != -2) {
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, 6, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(mc.player.inventoryContainer.windowId, slot, 1, ClickType.PICKUP, mc.player);
        }
    }
}
