// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.mixin.mixins;

import org.spongepowered.asm.mixin.injection.Inject;
import me.perry.mcdonalds.features.modules.misc.Tracker;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;
import net.minecraft.entity.Entity;
import com.google.common.base.Predicate;
import java.util.List;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin({ World.class })
public class MixinWorld
{
    @Redirect(method = { "getEntitiesWithinAABB(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;Lcom/google/common/base/Predicate;)Ljava/util/List;" }, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/chunk/Chunk;getEntitiesOfTypeWithinAABB(Ljava/lang/Class;Lnet/minecraft/util/math/AxisAlignedBB;Ljava/util/List;Lcom/google/common/base/Predicate;)V"))
    public <T extends Entity> void getEntitiesOfTypeWithinAABBHook(final Chunk chunk, final Class<? extends T> entityClass, final AxisAlignedBB aabb, final List<T> listToFill, final Predicate<? super T> filter) {
        try {
            chunk.getEntitiesOfTypeWithinAABB((Class)entityClass, aabb, (List)listToFill, (Predicate)filter);
        }
        catch (Exception ex) {}
    }
    
    @Inject(method = { "onEntityAdded" }, at = { @At("HEAD") })
    private void onEntityAdded(final Entity entityIn, final CallbackInfo ci) {
        if (Tracker.getInstance().isOn()) {
            Tracker.getInstance().onSpawnEntity(entityIn);
        }
    }
}
