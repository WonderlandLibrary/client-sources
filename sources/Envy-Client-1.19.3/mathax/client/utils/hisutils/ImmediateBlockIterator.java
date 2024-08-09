package mathax.client.utils.hisutils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.function.BiConsumer;

import static mathax.client.MatHax.mc;

public class ImmediateBlockIterator {

    private static boolean disablecurrent;

    /**
     * @param blocks empty for running fuction for every block without filter
     */
    public static void register(int horizontalradius, int verticalradius, BiConsumer<BlockPos, BlockState> function, Block... blocks) {
        if (mc.world == null || mc.player == null) return;


        int px = (int) mc.player.getX();
        int py = (int) mc.player.getY();
        int pz = (int) mc.player.getZ();

        for (int x = px - horizontalradius; x <= px + horizontalradius; x++) {
            for (int z = pz - horizontalradius; z <= pz + horizontalradius; z++) {
                for (int y = Math.max(mc.world.getBottomY(), py - verticalradius); y <= py + verticalradius; y++) {
                    if (y > mc.world.getTopY()) break;

                    BlockPos blockPos = new BlockPos(x, y, z);

                    BlockState blockState = mc.world.getBlockState(blockPos);

                    int dx = Math.abs(x - px);
                    int dy = Math.abs(y - py);
                    int dz = Math.abs(z - pz);


                    if (dx <= horizontalradius && dy <= verticalradius && dz <= horizontalradius) {
                        if (blocks.length == 0 || Arrays.stream(blocks).anyMatch(block -> block.equals(blockState.getBlock()))) {
                            disablecurrent = false;
                            function.accept(blockPos, blockState);
                            if (disablecurrent) return;
                        }
                    }
                }
            }
        }
    }

    public static void disableCurrent() {
        disablecurrent = true;
    }
}
