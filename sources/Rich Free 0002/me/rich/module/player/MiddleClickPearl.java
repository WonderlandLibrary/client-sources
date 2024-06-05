package me.rich.module.player;

import org.lwjgl.input.Mouse;

import me.rich.event.EventTarget;
import me.rich.event.events.EventPreMotionUpdate;
import me.rich.module.Category;
import me.rich.module.Feature;
import me.rich.notifications.NotificationPublisher;
import me.rich.notifications.NotificationType;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;

public class MiddleClickPearl
extends Feature {
    public MiddleClickPearl() {
        super("MiddleClickPearl",0, Category.PLAYER);
    }

    @EventTarget
    public void onPreMotion(EventPreMotionUpdate event) {
        if (Mouse.isButtonDown(2)) {
            for (int i = 0; i < 9; ++i) {
                ItemStack itemStack = Minecraft.player.inventory.getStackInSlot(i);
                if (itemStack.getItem() != Items.ENDER_PEARL) continue;
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(i));
                Minecraft.player.connection.sendPacket(new CPacketPlayerTryUseItem(EnumHand.MAIN_HAND));
                Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
            }
        }
    }

    @Override
    public void onDisable() {
        NotificationPublisher.queue(getName(), "was disabled.", NotificationType.INFO);
        Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
        super.onDisable();
    }
    
    @Override
    public void onEnable() {
        NotificationPublisher.queue(getName(), "was enabled.", NotificationType.INFO);
        super.onEnable();
    }
}
