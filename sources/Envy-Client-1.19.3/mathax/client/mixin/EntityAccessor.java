package mathax.client.mixin;

import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Entity.class)
public interface EntityAccessor {
    @Invoker("setFlag")
    void invokeSetFlag(int index, boolean value);

    @Accessor("touchingWater")
    void setInWater(boolean touchingWater);
}
