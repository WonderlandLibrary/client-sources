package me.vaziak.sensation.client.impl.movement;

import me.vaziak.sensation.client.api.Category;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.event.annotations.Collect;
import me.vaziak.sensation.client.api.event.events.PlayerSlowdownEvent;
import me.vaziak.sensation.client.api.event.events.PlayerUpdateEvent;
import me.vaziak.sensation.client.api.event.events.SendPacketEvent;
import me.vaziak.sensation.client.api.property.impl.StringsProperty;
import me.vaziak.sensation.utils.math.TimerUtil;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;


public class NoSlowdown extends Module {
    protected StringsProperty prop_mode = new StringsProperty("Mode", "How this cheat will function.", null, false, false, new String[]{"Vanilla", "NCP", "Matrix", "Position"}, new Boolean[]{true, false, false, false, false});
    private boolean blocking;
    TimerUtil timer;
    int counter;//Will be used in Matrix noslow

    public void onEnable() {
        counter = 0;
    }

    public NoSlowdown() {
        super("No Slow", Category.MOVEMENT);
        registerValue(prop_mode);
        timer = new TimerUtil();
    }


    @Collect
    public void onSendPacket(SendPacketEvent sendPacketEvent) {

        if (sendPacketEvent.getPacket() instanceof C07PacketPlayerDigging) {
            blocking = false;
        }

        if (sendPacketEvent.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            blocking = true;
        }
    }

    @Collect
    public void onPlayerSlowdown(PlayerSlowdownEvent playerSlowdownEvent) {
        playerSlowdownEvent.setCancelled(true);

        if (prop_mode.getValue().get("Matrix") && playerSlowdownEvent.isCancelled() && mc.gameSettings.keyBindUseItem.isKeyDown()  && mc.thePlayer.isMoving()) {
            if (!holdingSword() && mc.thePlayer.isUsingItem()) {
                mc.thePlayer.setSprinting(false);
            }
            counter += 1;
            if (counter == 3) {
                if (holdingSword() && blocking) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                    blocking = false;
                }
            }

            if (counter >= 6 && holdingSword() && !blocking) {
                mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                blocking = true;
                counter = 0;
            }

        }
    }

    @Collect
    public void onPlayerUpdate(PlayerUpdateEvent e) {
        setMode(prop_mode.getSelectedStrings().get(0));

        if (e.isPre()) {
            if (holdingSword()) {
                if (blocking && (!mc.thePlayer.isUsingItem() || !mc.thePlayer.isBlocking())) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0,0,0), EnumFacing.DOWN));
                    blocking = false;
                }
            }
            if (prop_mode.getValue().get("Position") && mc.thePlayer.isUsingItem() && mc.thePlayer.ticksExisted % 3 == 0) {
                for (double i = e.getPosY(); i < getGroundLevel() + .42; i+= .42 / 2) {
                    e.setPosY(i);
                }
            }

            if (prop_mode.getValue().get("NCP")) {
                if (holdingSword() && mc.thePlayer.isBlocking() && blocking && mc.thePlayer.isMoving()) {
                    if (timer.hasPassed(52)) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                        blocking = false;
                        timer.reset();
                    }
                }
            }
        } else {
            if (prop_mode.getValue().get("NCP")) {
                if (holdingSword() && mc.thePlayer.isBlocking() && !blocking) {
                    mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-0.146969696D, -0.146969696D, -0.146969696D), 255, mc.thePlayer.getHeldItem(), 0, 0, 0));
                    blocking = true;
                }
            }
        }
    }

    public double getGroundLevel() {
        for (int i = (int) Math.round(mc.thePlayer.posY); i > 0; --i) {
            AxisAlignedBB box = mc.thePlayer.getEntityBoundingBox().addCoord(0, 0, 0);
            box.minY = i - 1;
            box.maxY = i;
            if (isColliding(box) && box.minY <= mc.thePlayer.posY)
                return i;
        }
        return 0;
    }

    private boolean isColliding(AxisAlignedBB box) {
        return mc.theWorld.checkBlockCollision(box);
    }

    private boolean holdingSword() {
        if (mc.thePlayer.getCurrentEquippedItem() != null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }
}
