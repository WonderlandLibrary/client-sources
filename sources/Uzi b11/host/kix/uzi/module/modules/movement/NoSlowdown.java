package host.kix.uzi.module.modules.movement;

import com.darkmagician6.eventapi.SubscribeEvent;


import com.darkmagician6.eventapi.types.EventType;
import host.kix.uzi.events.UpdateEvent;
import host.kix.uzi.module.Module;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.potion.Potion;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

/**
 * Created by myche on 2/27/2017.
 */
public class NoSlowdown extends Module {

    public NoSlowdown() {
        super("NoSlowdown", 0, Category.MOVEMENT);
    }

    private double getBaseMoveSpeed() {
        double baseSpeed = 0.2873;
        if (this.mc.thePlayer.isPotionActive(Potion.moveSpeed)) {
            int amplifier = this.mc.thePlayer.getActivePotionEffect(Potion.moveSpeed).getAmplifier();
            baseSpeed *= 1.0 + 0.2 * (amplifier + 1);
        }
        return baseSpeed;
    }

    @SubscribeEvent
    public void onEvent(UpdateEvent pre) {
            if (this.mc.thePlayer.isBlocking()) {
                ItemStack currentItem = mc.thePlayer.inventory.getCurrentItem();
                if (currentItem == null)
                    return;
                if (currentItem.getItem().getItemUseAction(currentItem) != EnumAction.BLOCK)
                    return;
                if (mc.thePlayer.isBlocking()) {
                    boolean moving = mc.thePlayer.movementInput.moveForward != 0;
                    boolean strafing = mc.thePlayer.movementInput.moveStrafe != 0;
                    moving = moving || strafing;
                    block();
                    if (pre.type == EventType.PRE && moving)
                        unblock();
                } else
                    unblock();
            }
    }

    private void block() {
        mc.getNetHandler().addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
    }

    private void unblock() {
        mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
    }



}
