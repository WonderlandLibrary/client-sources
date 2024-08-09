package dev.darkmoon.client.module.impl.movement;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.event.packet.EventReceivePacket;
import dev.darkmoon.client.event.player.EventUpdate;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.utility.misc.ChatUtility;
import dev.darkmoon.client.utility.move.MovementUtility;
import net.minecraft.init.Items;
import net.minecraft.inventory.ClickType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.minecraft.network.play.server.SPacketPlayerPosLook;

@ModuleAnnotation(name = "Flight", category = Category.MOVEMENT)
public class Flight extends Module {
    public ModeSetting mode = new ModeSetting("Mode", "Glide", "Vanilla", "Glide");
    public static NumberSetting speed = new NumberSetting("Speed XZ", 1, 0.1f, 10, 0.01f);
    public static NumberSetting motionY = new NumberSetting("Speed Y", 1.0f, 0.1f, 2.0f, 0.01f);
    private final BooleanSetting checkFlag = new BooleanSetting("Check Flag", true);

    public float currentSpeed = 0.0f;
    public static long lastStartFalling;

    @EventTarget
    public void onUpdate(EventUpdate event) {
        switch (mode.get()) {
            case "Glide":
                if (mc.player.onGround) {
                    mc.player.jump();
                } else {
                    mc.player.motionY = -0.01D;
                    MovementUtility.setMotion(speed.get());
                }
                break;
            case "Vanilla":
                mc.player.motionY = 0.0;
                if (mc.gameSettings.keyBindJump.isKeyDown()) {
                    mc.player.motionY += motionY.get();
                }
                if (mc.gameSettings.keyBindSneak.isKeyDown()) {
                    mc.player.motionY -= motionY.get();
                }
                MovementUtility.setMotion(speed.get());
                break;
        }
    }

    @EventTarget
    public void onReceivePacket(EventReceivePacket event) {
        if (event.getPacket() instanceof SPacketPlayerPosLook) {
            if (checkFlag.get()) {
                toggle();
            }
        }
    }

    public static void disabler(int elytra) {
        int n = elytra = elytra >= 0 && elytra < 9 ? elytra + 36 : elytra;
        if (elytra != -2) {
            mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
        }
        mc.getConnection().sendPacket(new CPacketEntityAction(mc.player, CPacketEntityAction.Action.START_FALL_FLYING));
        if (elytra != -2) {
            mc.playerController.windowClick(0, 6, 1, ClickType.PICKUP, mc.player);
            mc.playerController.windowClick(0, elytra, 1, ClickType.PICKUP, mc.player);
        }
        lastStartFalling = System.currentTimeMillis();
    }


    public static int getSlotIDFromItem(Item item) {
        for (ItemStack stack : mc.player.getArmorInventoryList()) {
            if (stack.getItem() != item) continue;
            return -2;
        }
        int slot = -1;
        for (int i = 0; i < 36; ++i) {
            ItemStack s = mc.player.inventory.getStackInSlot(i);
            if (s.getItem() != item) continue;
            slot = i;
            break;
        }
        if (slot < 9 && slot != -1) {
            slot += 36;
        }
        return slot;
    }
}
