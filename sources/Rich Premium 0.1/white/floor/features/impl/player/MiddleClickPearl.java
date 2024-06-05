package white.floor.features.impl.player;

import org.lwjgl.input.Mouse;

import net.minecraft.client.Minecraft;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CPacketHeldItemChange;
import net.minecraft.network.play.client.CPacketPlayerTryUseItem;
import net.minecraft.util.EnumHand;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class MiddleClickPearl extends Feature {

    public MiddleClickPearl() {
        super("MiddleClickPearl","send pearl on scroll button.", 0, Category.PLAYER);
    }

    @EventTarget
    public void eventUpdate(EventUpdate event) {
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
        Minecraft.player.connection.sendPacket(new CPacketHeldItemChange(Minecraft.player.inventory.currentItem));
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }
}
