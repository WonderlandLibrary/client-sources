package cn.Hanabi.injection.mixins;

import net.minecraft.client.gui.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ FontRenderer.class })
public abstract class MixinFontRenderer
{
    @Shadow
    private int[] colorCode;
}
