package optfine;

import net.minecraft.client.settings.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.*;
import net.minecraft.world.chunk.*;

public class ClearWater
{
    private static final String[] I;
    
    public static void updateWaterOpacity(final GameSettings gameSettings, final World world) {
        if (gameSettings != null) {
            int n = "   ".length();
            if (gameSettings.ofClearWater) {
                n = " ".length();
            }
            BlockUtils.setLightOpacity(Blocks.water, n);
            BlockUtils.setLightOpacity(Blocks.flowing_water, n);
        }
        if (world != null) {
            final IChunkProvider chunkProvider = world.getChunkProvider();
            if (chunkProvider != null) {
                final Entity renderViewEntity = Config.getMinecraft().getRenderViewEntity();
                if (renderViewEntity != null) {
                    final int n2 = (int)renderViewEntity.posX / (0x5E ^ 0x4E);
                    final int n3 = (int)renderViewEntity.posZ / (0x43 ^ 0x53);
                    final int n4 = n2 - (417 + 224 - 450 + 321);
                    final int n5 = n2 + (199 + 358 - 475 + 430);
                    final int n6 = n3 - (241 + 122 - 348 + 497);
                    final int n7 = n3 + (333 + 38 + 126 + 15);
                    int length = "".length();
                    int i = n4;
                    "".length();
                    if (-1 == 2) {
                        throw null;
                    }
                    while (i < n5) {
                        int j = n6;
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                        while (j < n7) {
                            if (chunkProvider.chunkExists(i, j)) {
                                final Chunk provideChunk = chunkProvider.provideChunk(i, j);
                                if (provideChunk != null && !(provideChunk instanceof EmptyChunk)) {
                                    final int n8 = i << (0x41 ^ 0x45);
                                    final int n9 = j << (0xA9 ^ 0xAD);
                                    final int n10 = n8 + (0xB2 ^ 0xA2);
                                    final int n11 = n9 + (0x27 ^ 0x37);
                                    final BlockPosM blockPosM = new BlockPosM("".length(), "".length(), "".length());
                                    final BlockPosM blockPosM2 = new BlockPosM("".length(), "".length(), "".length());
                                    int k = n8;
                                    "".length();
                                    if (0 == 3) {
                                        throw null;
                                    }
                                    while (k < n10) {
                                        int l = n9;
                                        "".length();
                                        if (3 >= 4) {
                                            throw null;
                                        }
                                        while (l < n11) {
                                            blockPosM.setXyz(k, "".length(), l);
                                            final BlockPos precipitationHeight = world.getPrecipitationHeight(blockPosM);
                                            int length2 = "".length();
                                            "".length();
                                            if (0 <= -1) {
                                                throw null;
                                            }
                                            while (length2 < precipitationHeight.getY()) {
                                                blockPosM2.setXyz(k, length2, l);
                                                if (world.getBlockState(blockPosM2).getBlock().getMaterial() == Material.water) {
                                                    world.markBlocksDirtyVertical(k, l, blockPosM2.getY(), precipitationHeight.getY());
                                                    ++length;
                                                    "".length();
                                                    if (2 >= 4) {
                                                        throw null;
                                                    }
                                                    break;
                                                }
                                                else {
                                                    ++length2;
                                                }
                                            }
                                            ++l;
                                        }
                                        ++k;
                                    }
                                }
                            }
                            ++j;
                        }
                        ++i;
                    }
                    if (length > 0) {
                        String s = ClearWater.I["".length()];
                        if (Config.isMinecraftThread()) {
                            s = ClearWater.I[" ".length()];
                        }
                        Config.dbg(ClearWater.I["  ".length()] + s + ClearWater.I["   ".length()] + length + ClearWater.I[0x8F ^ 0x8B]);
                    }
                }
            }
        }
    }
    
    static {
        I();
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
            if (4 != 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x3F ^ 0x3A])["".length()] = I("<\u0006\u0011#\n=", "OccUo");
        ClearWater.I[" ".length()] = I("\u000e>\u000f\b-\u0019", "mRfmC");
        ClearWater.I["  ".length()] = I("5\"\r5;!/\u001c1;Vf", "vNhTI");
        ClearWater.I["   ".length()] = I("FG\u0007\b!\u0006\u0000\u001d\u0019(\u000bG", "ogumM");
        ClearWater.I[0x39 ^ 0x3D] = I("f\u0013/\u001d\u0003-\u0003", "FpGhm");
    }
}
