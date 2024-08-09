package mathax.client.mixin;

import net.minecraft.entity.projectile.PersistentProjectileEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(PersistentProjectileEntity.class)
public interface ProjectileInGroundAccessor {
    @Accessor("inGround")
    boolean getInGround();
}
