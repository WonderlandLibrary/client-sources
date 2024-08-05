package fr.dog.module.impl.player;

import fr.dog.component.impl.packet.BlinkComponent;
import fr.dog.event.annotations.SubscribeEvent;
import fr.dog.event.impl.network.PacketReceiveEvent;
import fr.dog.event.impl.player.PlayerNetworkTickEvent;
import fr.dog.module.Module;
import fr.dog.module.ModuleCategory;
import fr.dog.util.player.MoveUtil;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.BlockPos;

public class AntiVoid extends Module {
    private boolean blink;
    private BlockPos lastGroundPos;

    public AntiVoid() {
        super("AntiVoid", ModuleCategory.PLAYER);
    }

    @Override
    protected void onDisable() {
        BlinkComponent.onDisable();
        blink = false;
        super.onDisable();
    }

    @SubscribeEvent
    public void onUpdate(PlayerNetworkTickEvent event) {
        if (mc.thePlayer == null) return;
        updateLastGroundPos();
        // Removed void fall handling logic from here
    }

    @SubscribeEvent
    public void onPacketReceive(PacketReceiveEvent event) {
        if (!(event.getPacket() instanceof S08PacketPlayerPosLook)) return;
        // Check if the player is over the void and has a significant fall distance
        if (isOverVoid() && mc.thePlayer.fallDistance >= 3) {
            resetPlayerPositionToLastGround();
        }
    }

    private void updateLastGroundPos() {
        if (mc.thePlayer.onGround && mc.thePlayer.ticksExisted % 3 == 0) {
            lastGroundPos = mc.thePlayer.getPosition();
        }
    }

    private void handleVoidFall(PlayerNetworkTickEvent event) {
        if (isOverVoid()) {
            if (!blink) {
                BlinkComponent.onEnable();
                blink = true;
            }
            if (mc.thePlayer.fallDistance >= 3) {
                resetPlayerPositionToLastGround();
                blink = false;
                BlinkComponent.onDisable();
            }
        } else if (blink) {
            BlinkComponent.onDisable();
            blink = false;
        }
    }

    private boolean isOverVoid() {
        for (double y = mc.thePlayer.posY; y >= 0.0; y--) {
            if (!(mc.theWorld.getBlockState(new BlockPos(mc.thePlayer.posX, y, mc.thePlayer.posZ)).getBlock() instanceof BlockAir)) {
                return false;
            }
        }
        return true;
    }

    private boolean shouldCorrectPosition() {
        return mc.thePlayer.fallDistance >= 4 && isOverVoid() && blink;
    }

    private void correctPlayerPosition(S08PacketPlayerPosLook packet) {
        packet.x = lastGroundPos.getX();
        packet.y = lastGroundPos.getY() + 0.3;
        packet.z = lastGroundPos.getZ();
        packet.yaw = mc.thePlayer.rotationYaw;
        packet.pitch = mc.thePlayer.rotationPitch;
    }

    private void resetPlayerPositionToLastGround() {
        if (lastGroundPos != null) {
            mc.thePlayer.setPositionAndUpdate(lastGroundPos.getX(), lastGroundPos.getY(), lastGroundPos.getZ());
            mc.thePlayer.fallDistance = 0;
            BlinkComponent.onDisable();
            blink = false;
        }
    }
    // e
}