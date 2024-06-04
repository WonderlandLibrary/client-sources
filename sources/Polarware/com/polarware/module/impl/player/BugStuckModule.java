package com.polarware.module.impl.player;

import com.polarware.event.annotations.EventLink;
import com.polarware.event.bus.Listener;
import com.polarware.event.impl.other.BlockAABBEvent;
import com.polarware.module.Module;
import com.polarware.module.api.Category;
import com.polarware.module.api.ModuleInfo;
import com.polarware.util.interfaces.InstanceAccess;
import net.minecraft.block.BlockAir;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@ModuleInfo(name = "module.player.bugstuck.name", description = "module.player.bugstuck.description", category = Category.PLAYER)
public class BugStuckModule extends Module {

    private BlockPos blockToBreak;
    private boolean brokeBlock;

    @EventLink
    public Listener<BlockAABBEvent> onAABB = aabb -> {
        if (brokeBlock) {
            KeyBinding.setKeyBindState(mc.gameSettings.keyBindJump.getKeyCode(), true);
            if (mc.thePlayer.posY > blockToBreak.getY() + 0.7) {
                if (aabb.getBlock() instanceof BlockAir) {
                    final double x = aabb.getBlockPos().getX(), y = aabb.getBlockPos().getY(), z = aabb.getBlockPos().getZ();

                    if (y < InstanceAccess.mc.thePlayer.posY && aabb.getBlockPos().getY() == blockToBreak.getY()) {
                        aabb.setBoundingBox(AxisAlignedBB.fromBounds(-15, -1, -15, 15, 1, 15).offset(x, y, z));
                    }
                }
            }
        }
        if (!brokeBlock) {
            if(mc.thePlayer.playerLocation != null) {
                blockToBreak = mc.thePlayer.playerLocation.add(0, -2, 0);
            }
            if (!(mc.theWorld.getBlockState(blockToBreak).getBlock() instanceof BlockAir) && !brokeBlock) {
                mc.playerController.onPlayerDestroyBlock(blockToBreak, mc.thePlayer.getHorizontalFacing());
                brokeBlock = true;
            }
        }
    };

}
