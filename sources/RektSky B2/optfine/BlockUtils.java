package optfine;

import net.minecraft.block.*;
import java.util.*;

public class BlockUtils
{
    private static ReflectorClass ForgeBlock;
    private static ReflectorMethod ForgeBlock_setLightOpacity;
    private static boolean directAccessValid;
    private static Map mapOriginalOpacity;
    
    public static void setLightOpacity(final Block p_setLightOpacity_0_, final int p_setLightOpacity_1_) {
        if (!BlockUtils.mapOriginalOpacity.containsKey(p_setLightOpacity_0_)) {
            BlockUtils.mapOriginalOpacity.put(p_setLightOpacity_0_, p_setLightOpacity_0_.getLightOpacity());
        }
        if (BlockUtils.directAccessValid) {
            try {
                p_setLightOpacity_0_.setLightOpacity(p_setLightOpacity_1_);
                return;
            }
            catch (IllegalAccessError illegalaccesserror) {
                BlockUtils.directAccessValid = false;
                if (!BlockUtils.ForgeBlock_setLightOpacity.exists()) {
                    throw illegalaccesserror;
                }
            }
        }
        Reflector.callVoid(p_setLightOpacity_0_, BlockUtils.ForgeBlock_setLightOpacity, p_setLightOpacity_1_);
    }
    
    public static void restoreLightOpacity(final Block p_restoreLightOpacity_0_) {
        if (BlockUtils.mapOriginalOpacity.containsKey(p_restoreLightOpacity_0_)) {
            final int i = BlockUtils.mapOriginalOpacity.get(p_restoreLightOpacity_0_);
            setLightOpacity(p_restoreLightOpacity_0_, i);
        }
    }
    
    static {
        BlockUtils.ForgeBlock = new ReflectorClass(Block.class);
        BlockUtils.ForgeBlock_setLightOpacity = new ReflectorMethod(BlockUtils.ForgeBlock, "setLightOpacity");
        BlockUtils.directAccessValid = true;
        BlockUtils.mapOriginalOpacity = new IdentityHashMap();
    }
}
