package me.superskidder.lune.modules.movement;

import me.superskidder.lune.events.EventPostUpdate;
import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.combat.KillAura;
import me.superskidder.lune.utils.player.MoveUtils;
import me.superskidder.lune.utils.player.PlayerUtil;
import me.superskidder.lune.utils.timer.Timer;
import me.superskidder.lune.values.type.Mode;
import net.minecraft.item.ItemSword;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class NoSlowDown extends Mod {
    public static Mode<Enum<?>> mode;
    Timer timer = new Timer();

    public NoSlowDown() {
        super("NoSlow", ModCategory.Movement,"No slowdown when you using item");
        this.addValues(mode = new Mode<>("Mode", NFMode.values(), NFMode.Vanilla));
    }

    @EventTarget
    private void onPreUpdate(EventPreUpdate e) {
        if (mode.getModeAsString().equalsIgnoreCase("Motion")
                && (mc.thePlayer.isBlocking() && PlayerUtil.isMoving() && isOnGround(0.40))
               ) {
            if (!mc.thePlayer.isBlocking() || !PlayerUtil.isMoving())
                return;
            if (!mc.thePlayer.onGround && !MoveUtils.isOnGround(0.5))
                return;
            mc.getNetHandler().getNetworkManager().sendPacket(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-.8, -.8, -.8), EnumFacing.DOWN));
        }
        if (mc.thePlayer.inventory.getCurrentItem() != null) {
            if (mode.getValue() == NFMode.Hypixel && KillAura.target == null && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword && mc.thePlayer.onGround) {
                mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(-1, -1, -1), EnumFacing.DOWN));
            }
        }
    }

    @EventTarget
    private void onUpdate(EventUpdate e) {
        if (mode.getModeAsString().equalsIgnoreCase("NCP") && isHoldingSword() && mc.thePlayer.isBlocking()
                && mc.thePlayer.isMoving() && mc.thePlayer.onGround && KillAura.target == null) {
            mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }

    @EventTarget
    private void onPost(EventPostUpdate e) {
        if (mode.getModeAsString().equalsIgnoreCase("Motion")
                && (mc.thePlayer.isBlocking() && PlayerUtil.isMoving() && this.isOnGround(0.40))
                ) {
            if (!this.timer.delay(65.0f))
                return;
            mc.getNetHandler().getNetworkManager().sendPacket(new C08PacketPlayerBlockPlacement(
                    new BlockPos(-.8, -.8, -.8), 255, mc.thePlayer.getCurrentEquippedItem(), 0, 0, 0));
            this.timer.reset();
        }
        if (mode.getModeAsString().equalsIgnoreCase("NCP") && isHoldingSword() && mc.thePlayer.isBlocking()
                && mc.thePlayer.isMoving() && mc.thePlayer.onGround
                && (KillAura.target == null || KillAura.target == null)) {
            mc.thePlayer.sendQueue.getNetworkManager()
                    .sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.getHeldItem()));
        }
        if (mode.getModeAsString().equalsIgnoreCase("Hypixel") && isHoldingSword() && mc.thePlayer.isBlocking()
                && mc.thePlayer.isMoving() && mc.thePlayer.onGround && KillAura.target == null) {
            mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(new BlockPos(-1, -1, -1), 255,
                    mc.thePlayer.inventory.getCurrentItem(), 0.0F, 0.0F, 0.0F));
        }
    }

    private boolean isHoldingSword() {
        return mc.thePlayer.getCurrentEquippedItem() != null
                && mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemSword;
    }

    public static boolean isOnGround(double height) {
        if (!mc.theWorld.getCollidingBoundingBoxes(mc.thePlayer,
                mc.thePlayer.getEntityBoundingBox().offset(0.0, -height, 0.0)).isEmpty()) {
            return true;
        }
        return false;
    }

    public static enum NFMode {
        NCP, Hypixel, Vanilla, Motion,
        ;
    }
}
