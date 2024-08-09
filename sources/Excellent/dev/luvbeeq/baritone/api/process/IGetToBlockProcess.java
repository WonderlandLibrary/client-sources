package dev.luvbeeq.baritone.api.process;

import dev.luvbeeq.baritone.api.utils.BlockOptionalMeta;
import net.minecraft.block.Block;

/**
 * but it rescans the world every once in a while so it doesn't get fooled by its cache
 */
public interface IGetToBlockProcess extends IBaritoneProcess {

    void getToBlock(BlockOptionalMeta block);

    default void getToBlock(Block block) {
        getToBlock(new BlockOptionalMeta(block));
    }

    boolean blacklistClosest();
}
