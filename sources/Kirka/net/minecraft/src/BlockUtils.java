/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import net.minecraft.block.Block;
import net.minecraft.src.Reflector;
import net.minecraft.src.ReflectorClass;
import net.minecraft.src.ReflectorMethod;

public class BlockUtils {
    private static ReflectorClass ForgeBlock = new ReflectorClass(Block.class);
    private static ReflectorMethod ForgeBlock_setLightOpacity = new ReflectorMethod(ForgeBlock, "setLightOpacity");
    private static boolean directAccessValid = true;

    public static void setLightOpacity(Block block, int opacity) {
        block3 : {
            if (directAccessValid) {
                try {
                    block.setLightOpacity(opacity);
                    return;
                }
                catch (IllegalAccessError var3) {
                    directAccessValid = false;
                    if (ForgeBlock_setLightOpacity.exists()) break block3;
                    throw var3;
                }
            }
        }
        Reflector.callVoid(block, ForgeBlock_setLightOpacity, opacity);
    }
}

