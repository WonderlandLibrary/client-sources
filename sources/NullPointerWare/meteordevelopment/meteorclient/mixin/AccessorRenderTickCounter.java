package meteordevelopment.meteorclient.mixin;


import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(RenderTickCounter.class)
public interface AccessorRenderTickCounter {


    @Mutable
    @Accessor("tickTime")
    void setTickTime(float tickTime);

    @Accessor("tickTime")
    float getTickTime();

    @Accessor("lastFrameDuration")
    void setLastFrameDuration(float lastFrameDuration);

    @Accessor("lastFrameDuration")
    float getLastFrameDuration();

}
