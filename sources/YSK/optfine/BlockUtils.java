package optfine;

import net.minecraft.block.*;
import java.util.*;

public class BlockUtils
{
    private static final String[] I;
    private static ReflectorClass ForgeBlock;
    private static boolean directAccessValid;
    private static ReflectorMethod ForgeBlock_setLightOpacity;
    private static Map mapOriginalOpacity;
    
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
            if (-1 != -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        BlockUtils.ForgeBlock = new ReflectorClass(Block.class);
        BlockUtils.ForgeBlock_setLightOpacity = new ReflectorMethod(BlockUtils.ForgeBlock, BlockUtils.I["".length()]);
        BlockUtils.directAccessValid = (" ".length() != 0);
        BlockUtils.mapOriginalOpacity = new IdentityHashMap();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\t\u0002\u0002\u0006\u0000\u001d\u000f\u0002\u0005\u0019\u001b\u0004\u001f>\u0010", "zgvJi");
    }
    
    public static void setLightOpacity(final Block block, final int lightOpacity) {
        if (!BlockUtils.mapOriginalOpacity.containsKey(block)) {
            BlockUtils.mapOriginalOpacity.put(block, block.getLightOpacity());
        }
        if (BlockUtils.directAccessValid) {
            try {
                block.setLightOpacity(lightOpacity);
                return;
            }
            catch (IllegalAccessError illegalAccessError) {
                BlockUtils.directAccessValid = ("".length() != 0);
                if (!BlockUtils.ForgeBlock_setLightOpacity.exists()) {
                    throw illegalAccessError;
                }
            }
        }
        final ReflectorMethod forgeBlock_setLightOpacity = BlockUtils.ForgeBlock_setLightOpacity;
        final Object[] array = new Object[" ".length()];
        array["".length()] = lightOpacity;
        Reflector.callVoid(block, forgeBlock_setLightOpacity, array);
    }
    
    public static void restoreLightOpacity(final Block block) {
        if (BlockUtils.mapOriginalOpacity.containsKey(block)) {
            setLightOpacity(block, BlockUtils.mapOriginalOpacity.get(block));
        }
    }
}
