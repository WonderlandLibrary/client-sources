package net.shoreline.client.mixin.accessor;

import net.minecraft.client.texture.PlayerSkinProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.io.File;

/**
 *
 *
 * @author linus
 * @since 1.0
 */
@Mixin(PlayerSkinProvider.class)
public interface AccessorPlayerSkinProvider
{
    /**
     *
     *
     * @return
     */
    @Accessor("skinCacheDir")
    File getSkinCacheDir();
}
