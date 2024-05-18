package baritone.launch.mixins;

import baritone.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ on.class })
public class MixinChunkProviderServer implements dd
{
    @Shadow
    @Final
    private ayf d;
    
    public MixinChunkProviderServer() {
        super();
    }
    
    @Override
    public ayf getChunkLoader() {
        return this.d;
    }
}
