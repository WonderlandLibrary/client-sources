package cc.slack.features.modules.impl.movement.longjumps.impl.verus;

import cc.slack.start.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.events.impl.player.MoveEvent;
import cc.slack.features.modules.impl.movement.LongJump;
import cc.slack.features.modules.impl.movement.longjumps.ILongJump;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.InventoryUtil;
import cc.slack.utils.player.MovementUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemFishingRod;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class VerusBowLJ implements ILongJump {

    boolean back, bowd, receivedS12;
    int ticks;
    double moveSpeed = 0;


    @Override
    public void onEnable() {
        ticks = 0;

       back = receivedS12 = bowd = false;

        ItemStack bow = null;
        int slot = -1;
        for(int i = 36; i < 45; ++i) {
            if(InventoryUtil.getSlot(i).getHasStack()) {
                ItemStack itemStack = InventoryUtil.getSlot(i).getStack();
                if(itemStack.getItem() instanceof ItemBow || itemStack.getItem() instanceof ItemFishingRod) {
                    bow = itemStack;
                    slot = i - 36;
                }
            }
        }

        if(bow != null) {
            PacketUtil.sendNoEvent(new C09PacketHeldItemChange(slot));
            PacketUtil.sendNoEvent(new C08PacketPlayerBlockPlacement(bow));
        } else {
            back = true;
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        MovementUtil.strafe(0);
        event.setPitch(-90);
        if(ticks >= 3 && !bowd) {
            bowd = true;
            PacketUtil.sendNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            PacketUtil.sendNoEvent(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
        }

        if(!receivedS12) {
            MovementUtil.strafe(0);
            event.setPitch(-90);
        } else {
            if(event.getState() == State.PRE) {
                if(MovementUtil.isOnGround(3) && mc.thePlayer.motionY < 0) {
                    moveSpeed = 0.25;
                    mc.thePlayer.motionY = -0.0784000015258789;
                }
                moveSpeed -= 0.0001;
                MovementUtil.strafe((float) moveSpeed);
                if (mc.thePlayer.onGround) Slack.getInstance().getModuleManager().getInstance(LongJump.class).toggle();
            }
        }

        if(event.getState() == State.PRE) {
            ticks++;
        }
    }

    @Override
    public void onMove(MoveEvent event) {
        if(!receivedS12) {
            event.setX(0);
            event.setZ(0);
            event.cancel();
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if(event.getPacket() instanceof S12PacketEntityVelocity) {
            S12PacketEntityVelocity packet = event.getPacket();
            if(packet.getEntityID() != mc.thePlayer.getEntityId()) return;
            receivedS12 = true;
            MovementUtil.setVClip(4);
            moveSpeed = Slack.getInstance().getModuleManager().getInstance(LongJump.class).speedValue.getValue();
            MovementUtil.strafe((float) moveSpeed);
            }
        }

    @Override
    public String toString() {
        return "Verus Bow";
    }
}
