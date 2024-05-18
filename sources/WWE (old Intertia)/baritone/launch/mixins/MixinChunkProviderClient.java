package baritone.launch.mixins;

import baritone.*;
import it.unimi.dsi.fastutil.longs.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ brx.class })
public class MixinChunkProviderClient implements dc
{
    @Shadow
    @Final
    private Long2ObjectMap<axw> c;
    
    public MixinChunkProviderClient() {
        super();
    }
    
    @Override
    public Long2ObjectMap<axw> loadedChunks() {
        return this.c;
    }
}
