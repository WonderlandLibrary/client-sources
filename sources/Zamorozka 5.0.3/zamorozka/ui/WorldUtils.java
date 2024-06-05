package zamorozka.ui;

import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.List;

public class WorldUtils implements MCUtil {

    public static boolean isFluid(BlockPos pos) {
        List<Material> fluids = Arrays.asList(Material.WATER, Material.LAVA);

        return fluids.contains(mc.world.getBlockState(pos).getMaterial());
    }


}
