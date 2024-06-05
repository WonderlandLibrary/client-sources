package optifine;

import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.resources.model.*;

public class BetterGrass
{
    private static IBakedModel modelEmpty;
    private static IBakedModel modelCubeMycelium;
    private static IBakedModel modelCubeGrassSnowy;
    private static IBakedModel modelCubeGrass;
    
    public static void update() {
        BetterGrass.modelCubeGrass = BlockModelUtils.makeModelCube("minecraft:blocks/grass_top", 0);
        BetterGrass.modelCubeGrassSnowy = BlockModelUtils.makeModelCube("minecraft:blocks/snow", -1);
        BetterGrass.modelCubeMycelium = BlockModelUtils.makeModelCube("minecraft:blocks/mycelium_top", -1);
    }
    
    public static List getFaceQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing facing, final List quads) {
        if (facing == EnumFacing.UP || facing == EnumFacing.DOWN) {
            return quads;
        }
        if (block instanceof BlockMycelium) {
            return Config.isBetterGrassFancy() ? ((getBlockAt(blockPos.offsetDown(), facing, blockAccess) == Blocks.mycelium) ? BetterGrass.modelCubeMycelium.func_177551_a(facing) : quads) : BetterGrass.modelCubeMycelium.func_177551_a(facing);
        }
        if (block instanceof BlockGrass) {
            final Block blockUp = blockAccess.getBlockState(blockPos.offsetUp()).getBlock();
            final boolean snowy = blockUp == Blocks.snow || blockUp == Blocks.snow_layer;
            if (!Config.isBetterGrassFancy()) {
                if (snowy) {
                    return BetterGrass.modelCubeGrassSnowy.func_177551_a(facing);
                }
                return BetterGrass.modelCubeGrass.func_177551_a(facing);
            }
            else if (snowy) {
                if (getBlockAt(blockPos, facing, blockAccess) == Blocks.snow_layer) {
                    return BetterGrass.modelCubeGrassSnowy.func_177551_a(facing);
                }
            }
            else if (getBlockAt(blockPos.offsetDown(), facing, blockAccess) == Blocks.grass) {
                return BetterGrass.modelCubeGrass.func_177551_a(facing);
            }
        }
        return quads;
    }
    
    private static Block getBlockAt(final BlockPos blockPos, final EnumFacing facing, final IBlockAccess blockAccess) {
        final BlockPos pos = blockPos.offset(facing);
        final Block block = blockAccess.getBlockState(pos).getBlock();
        return block;
    }
    
    static {
        BetterGrass.modelEmpty = new SimpleBakedModel(new ArrayList(), new ArrayList(), false, false, null, null);
        BetterGrass.modelCubeMycelium = null;
        BetterGrass.modelCubeGrassSnowy = null;
        BetterGrass.modelCubeGrass = null;
    }
}
