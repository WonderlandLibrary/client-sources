package dev.luvbeeq.baritone.utils.accessor;

import net.minecraft.util.math.BlockPos;

public interface IPlayerControllerMP {

    void setIsHittingBlock(boolean isHittingBlock);

    BlockPos getCurrentBlock();

    void callSyncCurrentPlayItem();
}
