package im.expensive.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.network.play.client.CEntityActionPacket;

@FunctionRegister(
        name = "ElytraJump",
        type = Category.Movement
)
public class ElytraJump extends Function {

    public ElytraJump() {
        addSettings();
    }
    ItemStack currentStack = ItemStack.EMPTY;

    @Subscribe
    private void onUpdate(EventUpdate e) {
        this.currentStack = Minecraft.player.getItemStackFromSlot(EquipmentSlotType.CHEST);
        if (mc.gameSettings.keyBindJump.isPressed()) {
        if (this.currentStack.getItem() == Items.ELYTRA) {
            if (Minecraft.player.isOnGround()) {
                Minecraft.player.jump();
            } else if (ElytraItem.isUsable(this.currentStack) && !Minecraft.player.isElytraFlying()) {
                Minecraft.player.startFallFlying();
                Minecraft.player.connection.sendPacket(new CEntityActionPacket(Minecraft.player, CEntityActionPacket.Action.START_FALL_FLYING));
            }
        }
        }
    }

}