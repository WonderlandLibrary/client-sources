package com.client.glowclient.modules.server;

import net.minecraftforge.event.world.*;
import net.minecraft.item.*;
import com.client.glowclient.utils.*;
import net.minecraft.init.*;
import net.minecraft.util.math.*;
import net.minecraft.util.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.multiplayer.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;

public class NoGhostBlocks extends ModuleContainer
{
    @SubscribeEvent
    public void M(final BlockEvent$BreakEvent blockEvent$BreakEvent) {
        if (!(Wrapper.mc.player.getHeldItemMainhand().getItem() instanceof ItemBlock)) {
            final BlockPos position = Wrapper.mc.player.getPosition();
            int n;
            int i = n = -4;
            while (i <= 4) {
                int n2;
                int j = n2 = -4;
                while (j <= 4) {
                    int n3;
                    int k = n3 = -4;
                    while (k <= 4) {
                        final BlockPos blockPos = new BlockPos(position.getX() + n, position.getY() + n2, position.getZ() + n3);
                        if (Wrapper.mc.world.getBlockState(blockPos).getBlock().equals(Blocks.AIR)) {
                            final PlayerControllerMP playerController = Wrapper.mc.playerController;
                            final EntityPlayerSP player = Wrapper.mc.player;
                            final WorldClient world = Wrapper.mc.world;
                            final BlockPos blockPos2 = blockPos;
                            final EnumFacing down = EnumFacing.DOWN;
                            final double n4 = 0.5;
                            playerController.processRightClickBlock(player, world, blockPos2, down, new Vec3d(n4, n4, n4), EnumHand.MAIN_HAND);
                        }
                        k = ++n3;
                    }
                    j = ++n2;
                }
                i = ++n;
            }
        }
    }
    
    @Override
    public boolean A() {
        return false;
    }
    
    public NoGhostBlocks() {
        super(Category.SERVER, "NoGhostBlocks", false, -1, "Makes ghost blocks much more infrequent");
    }
}
