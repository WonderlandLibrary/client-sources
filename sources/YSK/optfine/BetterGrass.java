package optfine;

import net.minecraft.util.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import java.util.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.block.model.*;

public class BetterGrass
{
    private static IBakedModel modelEmpty;
    private static IBakedModel modelCubeGrassSnowy;
    private static IBakedModel modelCubeGrass;
    private static final String[] I;
    private static IBakedModel modelCubeMycelium;
    
    private static Block getBlockAt(final BlockPos blockPos, final EnumFacing enumFacing, final IBlockAccess blockAccess) {
        return blockAccess.getBlockState(blockPos.offset(enumFacing)).getBlock();
    }
    
    public static List getFaceQuads(final IBlockAccess blockAccess, final Block block, final BlockPos blockPos, final EnumFacing enumFacing, final List list) {
        if (enumFacing == EnumFacing.UP || enumFacing == EnumFacing.DOWN) {
            return list;
        }
        if (block instanceof BlockMycelium) {
            List<BakedQuad> list2;
            if (Config.isBetterGrassFancy()) {
                if (getBlockAt(blockPos.down(), enumFacing, blockAccess) == Blocks.mycelium) {
                    list2 = BetterGrass.modelCubeMycelium.getFaceQuads(enumFacing);
                    "".length();
                    if (1 < 1) {
                        throw null;
                    }
                }
                else {
                    list2 = (List<BakedQuad>)list;
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
            }
            else {
                list2 = BetterGrass.modelCubeMycelium.getFaceQuads(enumFacing);
            }
            return list2;
        }
        if (block instanceof BlockGrass) {
            final Block block2 = blockAccess.getBlockState(blockPos.up()).getBlock();
            int n;
            if (block2 != Blocks.snow && block2 != Blocks.snow_layer) {
                n = "".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            final int n2 = n;
            if (!Config.isBetterGrassFancy()) {
                if (n2 != 0) {
                    return BetterGrass.modelCubeGrassSnowy.getFaceQuads(enumFacing);
                }
                return BetterGrass.modelCubeGrass.getFaceQuads(enumFacing);
            }
            else if (n2 != 0) {
                if (getBlockAt(blockPos, enumFacing, blockAccess) == Blocks.snow_layer) {
                    return BetterGrass.modelCubeGrassSnowy.getFaceQuads(enumFacing);
                }
            }
            else if (getBlockAt(blockPos.down(), enumFacing, blockAccess) == Blocks.grass) {
                return BetterGrass.modelCubeGrass.getFaceQuads(enumFacing);
            }
        }
        return list;
    }
    
    static {
        I();
        BetterGrass.modelEmpty = new SimpleBakedModel(new ArrayList<BakedQuad>(), new ArrayList<List<BakedQuad>>(), "".length() != 0, "".length() != 0, null, null);
        BetterGrass.modelCubeMycelium = null;
        BetterGrass.modelCubeGrassSnowy = null;
        BetterGrass.modelCubeGrass = null;
    }
    
    public static void update() {
        BetterGrass.modelCubeGrass = BlockModelUtils.makeModelCube(BetterGrass.I["".length()], "".length());
        BetterGrass.modelCubeGrassSnowy = BlockModelUtils.makeModelCube(BetterGrass.I[" ".length()], -" ".length());
        BetterGrass.modelCubeMycelium = BlockModelUtils.makeModelCube(BetterGrass.I["  ".length()], -" ".length());
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\b\u0006 &\u0015\u0017\u000e(7L\u0007\u0003! \u001d\u0016@)1\u0017\u0016\u001c\u00117\u0019\u0015", "eoNCv");
        BetterGrass.I[" ".length()] = I("\b8&!5\u00170.0l\u0007=''=\u0016~;*9\u0012", "eQHDV");
        BetterGrass.I["  ".length()] = I("\u00059\u001d$\u0006\u001a1\u00155_\n<\u001c\"\u000e\u001b\u007f\u001e8\u0006\r<\u001a4\b7$\u001c1", "hPsAe");
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
}
