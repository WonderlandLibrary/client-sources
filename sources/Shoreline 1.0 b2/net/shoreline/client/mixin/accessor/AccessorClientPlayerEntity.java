package net.shoreline.client.mixin.accessor;

import net.minecraft.client.network.ClientPlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

/**
 *
 *
 * @author linus
 * @since 1.0
 *
 * @see ClientPlayerEntity
 */
@Mixin(ClientPlayerEntity.class)
public interface AccessorClientPlayerEntity
{
    /**
     *
     *
     * @return
     */
    @Accessor("lastX")
    double getLastX();

    /**
     *
     *
     * @return
     */
    @Accessor("lastBaseY")
    double getLastBaseY();

    /**
     *
     *
     * @return
     */
    @Accessor("lastZ")
    double getLastZ();
}
