package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.event.packet.EventSendPacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.manager.notification.Notification;
import dev.darkmoon.client.manager.notification.NotificationManager;
import dev.darkmoon.client.manager.notification.NotificationType;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.Utility;
import dev.darkmoon.client.utility.move.MovementUtility;
import dev.darkmoon.client.utility.player.InventoryUtility;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.minecraft.util.EnumHand;

@ModuleAnnotation(name = "FWorkFly", category = Category.MOVEMENT)
public class SunriseFirework extends Module {
    public static NumberSetting xzSpeed = new NumberSetting("XZ-Speed", 1, 1, 1.9f, 0.1f);
    public static NumberSetting ySpeed = new NumberSetting("Y-Speed", 0.5f, 0.1f, 4, 0.1f);
    public static NumberSetting fireSlot = new NumberSetting("Firework-Slot", 0, 0, 8, 1);
    private int lastItem = -1;
    private float acceleration;

    @EventTarget
    public void onUpdate(EventUpdate event) {
        InventoryPlayer inventory = mc.player.inventory;
        ItemStack itemStack = inventory.getStackInSlot(fireSlot.getInt());
        if (itemStack.getItem() == Items.FIREWORKS) {
        } else {
            int elytra = InventoryUtility.getItemSlot(Items.ELYTRA);
            Utility.mc.playerController.windowClick(0, (elytra < 9) ? (elytra + 36) : elytra, 1, ClickType.PICKUP, mc.player);
            Utility.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
            Utility.mc.playerController.windowClick(0, (elytra < 9) ? (elytra + 36) : elytra, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, InventoryUtility.getItemSlot(Items.FIREWORKS), fireSlot.getInt(), ClickType.SWAP, mc.player);
        }
        if (InventoryUtility.getItemSlot(Items.FIREWORKS) == -1) {
            NotificationManager.notify(NotificationType.INFO, "FWorkFly", "Возьмите фейерверки!", 3);
            toggle();
            return;
        }

        if (InventoryUtility.getFireworks() >= 0) {
            if (mc.player.wasFallFlying) {
                if (InventoryUtility.getFireworks() >= 0) {
                    mc.player.motionY = -0.008D;
                    if (!MovementUtility.isMoving()) {
                        Utility.mc.player.motionX = 0;
                        Utility.mc.player.motionZ = 0;
                        acceleration = 0f;
                    } else {
                        double[] moveDirection = forward(lerp(0f, xzSpeed.get(), Math.min(acceleration, 1f)));
                        Utility.mc.player.motionX = moveDirection[0];
                        Utility.mc.player.motionZ = moveDirection[1];
                        acceleration += 0.1f;
                    }
                }
                if (!Utility.mc.player.isSneaking() && Utility.mc.gameSettings.keyBindJump.pressed) {
                    Utility.mc.player.motionY = ySpeed.get();
                }
                if (Utility.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    Utility.mc.player.motionY = -ySpeed.get();

                }
                if (mc.player.ticksExisted % 25 == 0) {
                    useFirework();
                }
            } else {
                if (mc.player.ticksExisted % 8 == 0) {
                    mc.player.jump();
                    Utility.mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
                    useFirework();
                }
            }
        }
    }
    public static double lerp(double old, double newVal, double amount) {
        return (1.0 - amount) * old + amount * newVal;
    }

    @EventTarget
    public void onEventPacket(EventSendPacket event){
        if(event.getPacket() instanceof SPacketPlayerPosLook) {
            acceleration = 0;
        }
    }
    @Override
    public void onEnable() {
        acceleration = 0;
        if (mc.player != null && mc.world != null) {
            if (InventoryUtility.getFireworks() >= 0) {
                if (!(mc.player.inventory.getItemStack().getItem() instanceof net.minecraft.item.ItemElytra)) {
                    int elytra = InventoryUtility.getItemSlot(Items.ELYTRA);
                    Utility.mc.playerController.windowClick(0, (elytra < 9) ? (elytra + 36) : elytra, 1, ClickType.PICKUP, mc.player);
                    Utility.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                    Utility.mc.playerController.windowClick(0, (elytra < 9) ? (elytra + 36) : elytra, 1, ClickType.PICKUP, mc.player);
                }
            }
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        acceleration = 0;
        if (mc.player != null && mc.world != null) {
            if (InventoryUtility.getFireworks() >= 0) {
                int armor = InventoryUtility.getSlotWithArmor();
                Utility.mc.playerController.windowClick(0, (armor < 9) ? (armor + 36) : armor, 1, ClickType.PICKUP, mc.player);
                Utility.mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
                Utility.mc.playerController.windowClick(0, (armor < 9) ? (armor + 36) : armor, 1, ClickType.PICKUP, (EntityPlayer) mc.player);
            }
        }
        super.onDisable();
    }

    public static double[] forward(double speed) {
        float forward = Utility.mc.player.movementInput.moveForward;
        float side = Utility.mc.player.movementInput.moveStrafe;
        float yaw = Utility.mc.player.prevRotationYaw + (Utility.mc.player.rotationYaw - Utility.mc.player.prevRotationYaw) * Utility.mc.getRenderPartialTicks();
        if (forward != 0.0f) {
            if (side > 0.0f) {
                yaw += (float)(forward > 0.0f ? -45 : 45);
            } else if (side < 0.0f) {
                yaw += (float)(forward > 0.0f ? 45 : -45);
            }
            side = 0.0f;
            if (forward > 0.0f) {
                forward = 1.0f;
            } else if (forward < 0.0f) {
                forward = -1.0f;
            }
        }
        double sin = Math.sin(Math.toRadians(yaw + 90.0f));
        double cos = Math.cos(Math.toRadians(yaw + 90.0f));
        double posX = (double)forward * speed * cos + (double)side * speed * sin;
        double posZ = (double)forward * speed * sin - (double)side * speed * cos;
        return new double[]{posX, posZ};
    }

    public void useFirework() {
        Utility.mc.player.connection.sendPacket(new CPacketHeldItemChange(fireSlot.getInt()));
        Utility.mc.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
        Utility.mc.player.connection.sendPacket(new CPacketHeldItemChange(Utility.mc.player.inventory.currentItem));
    }
}
