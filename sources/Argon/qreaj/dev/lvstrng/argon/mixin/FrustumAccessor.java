// 
// Decompiled by the rizzer xd
// 

package dev.lvstrng.argon.mixin;

import net.minecraft.client.render.Frustum;
import org.joml.FrustumIntersection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin({Frustum.class})
public interface FrustumAccessor {
    @Accessor
    FrustumIntersection getFrustumIntersection();

    @Accessor
    void setFrustumIntersection(final FrustumIntersection p0);

    @Accessor("x")
    double getX();

    @Accessor("x")
    void setX(final double p0);

    @Accessor("y")
    double getY();

    @Accessor("y")
    void setY(final double p0);

    @Accessor("z")
    double getZ();

    @Accessor("z")
    void setZ(final double p0);
}
