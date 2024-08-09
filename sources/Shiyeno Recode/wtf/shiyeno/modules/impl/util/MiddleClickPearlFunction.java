package wtf.shiyeno.modules.impl.util;

import net.minecraft.item.Items;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.client.CPlayerTryUseItemPacket;
import net.minecraft.util.Hand;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.game.EventMouseTick;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.impl.combat.AuraFunction;
import wtf.shiyeno.modules.impl.player.GappleCooldownFunction;
import wtf.shiyeno.modules.impl.player.GappleCooldownFunction.ItemEnum;
import wtf.shiyeno.util.world.InventoryUtil;

@FunctionAnnotation(
        name = "MiddleClickPearl",
        type = Type.Util
)
public class MiddleClickPearlFunction extends Function {
    public MiddleClickPearlFunction() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventMouseTick mouseTick) {
            if (mouseTick.getButton() == 2) {
                this.handleMouseTickEvent();
            }
        }
    }

    private void handleMouseTickEvent() {
        if (!mc.player.getCooldownTracker().hasCooldown(Items.ENDER_PEARL) && InventoryUtil.getPearls() >= 0) {
            InventoryUtil.inventorySwapClick(Items.ENDER_PEARL, true);
        }
    }

    private void sendHeldItemChangePacket(int itemSlot) {
        mc.player.connection.sendPacket(new CHeldItemChangePacket(itemSlot));
        GappleCooldownFunction cooldown = Managment.FUNCTION_MANAGER.gappleCooldownFunction;
        GappleCooldownFunction.ItemEnum itemEnum = ItemEnum.getItemEnum(Items.ENDER_PEARL);
        if (cooldown.state && itemEnum != null && cooldown.isCurrentItem(itemEnum)) {
            cooldown.lastUseItemTime.put(itemEnum.getItem(), System.currentTimeMillis());
        }
    }

    private void sendPlayerRotationPacket(float yaw, float pitch, boolean onGround) {
        AuraFunction var10000 = Managment.FUNCTION_MANAGER.auraFunction;
        if (AuraFunction.target != null) {
            mc.player.connection.sendPacket(new CPlayerPacket.RotationPacket(yaw, pitch, onGround));
        }
    }

    private void useItem(Hand hand) {
        mc.player.connection.sendPacket(new CPlayerTryUseItemPacket(hand));
        mc.player.swingArm(hand);
    }
}