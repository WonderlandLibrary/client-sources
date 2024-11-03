package net.silentclient.client.mixin.mixins;

import net.minecraft.util.MathHelper;
import net.silentclient.client.math.RivensHalfMath;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(MathHelper.class)
public class MathHelperMixin {
    /**
     * @author kirillsaint
     * @reason custom math (faster)
     */
    @Overwrite
    public static float sin(float param) {
        return RivensHalfMath.sin(param);
    }

    /**
     * @author kirillsaint
     * @reason custom math (faster)
     */
    @Overwrite
    public static float cos(float param) {
        return RivensHalfMath.cos(param);
    }
}
