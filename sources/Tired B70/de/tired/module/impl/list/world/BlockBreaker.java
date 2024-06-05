package de.tired.module.impl.list.world;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.util.math.TimerUtil;
import de.tired.api.util.rotation.RotationSender;
import de.tired.event.EventTarget;
import de.tired.event.events.EventLook;
import de.tired.event.events.RotationEvent;
import de.tired.event.events.UpdateEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ListIterator;

@ModuleAnnotation(name = "BlockBreaker", category = ModuleCategory.PLAYER)
public class BlockBreaker extends Module {

    public float[] serverRots = new float[2];
    private final TimerUtil timerUtilBreakBlock = new TimerUtil();
    private final TimerUtil timerUtilBreakAround = new TimerUtil();
    private ListIterator<Object> blocks = null;

    public BlockPos pos;

    @EventTarget
    public void getLook(EventLook event) {

    }

    @EventTarget
    public void onRotation(RotationEvent e) {


    }

    @EventTarget
    public void onUpdate(UpdateEvent event) {

        for (int y = -5; y < 5; ++y) {
            for (int x = -5; x < 5; ++x) {
                for (int z = -5; z < 5; ++z) {

                    final BlockPos blockPos = MC.thePlayer.getPosition().add(x, y, (double) z);

                    final Block block = MC.theWorld.getBlockState(blockPos).getBlock();

                    float[] rotations;

                    if (block == Blocks.bed) {

                        pos = blockPos;

                        final long timeLeft = (long) (MC.playerController.curBlockDamageMP / 2.0F);

                        rotations = RotationSender.getBlockRotations(blockPos.getX(), blockPos.getY(), blockPos.getZ());


                        MC.gameSettings.keyBindPickBlock.pressed = !getBlocksArround(blockPos).isEmpty();
                        if (!getBlocksArround(blockPos).isEmpty()) {
                            if (timerUtilBreakAround.reachedTime(timeLeft)) {
                                MC.thePlayer.rotationYaw = rotations[0];
                                MC.thePlayer.rotationPitch = rotations[1];
                                timerUtilBreakAround.doReset();
                            }
                        } else {
                            if (!getBlocksArround(blockPos).isEmpty()) return;
                            MC.thePlayer.rotationYaw = rotations[0];
                            MC.thePlayer.rotationPitch = rotations[1];

                                doDestroy(blockPos);


                        }
                    } else {
                        MC.gameSettings.keyBindPickBlock.pressed = false;
                    }
                }

            }
        }
    }

    private void doDestroy(final BlockPos position) {
        sendPacket(new C0APacketAnimation());
        sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.START_DESTROY_BLOCK, position, EnumFacing.DOWN));
        sendPacket(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK, position, EnumFacing.DOWN));
    }

    private List getBlocksArround(BlockPos position) {
        ArrayList<BlockPos> arroundBlocks = new ArrayList<>();

        for (int y = 0; y < 2; ++y) {
            for (int x = -5; x < 5; ++x) {
                for (int z = -5; z < 5; ++z) {
                    final BlockPos blockPos = position.add(x, y, z);
                    final Block block = MC.theWorld.getBlockState(blockPos).getBlock();
                    if (block == Blocks.sandstone || block == Blocks.iron_block || block == Blocks.end_stone || block == Blocks.glass || block == Blocks.glowstone || block == Blocks.chest || block.getLocalizedName().contains("Chiseled Sandstone")) {
                        arroundBlocks.add(blockPos);
                    }
                }
            }
        }
        return Collections.singletonList(arroundBlocks);
    }


    @Override
    public void onState() {
        MC.gameSettings.keyBindPickBlock.pressed = false;
    }

    @Override
    public void onUndo() {
        MC.gameSettings.keyBindPickBlock.pressed = false;
    }
}
