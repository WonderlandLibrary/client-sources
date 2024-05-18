package baritone.launch.mixins;

import baritone.*;
import java.io.*;
import org.spongepowered.asm.mixin.*;

@Mixin({ aye.class })
public class MixinAnvilChunkLoader implements db
{
    @Shadow
    @Final
    private File d;
    
    public MixinAnvilChunkLoader() {
        super();
    }
    
    @Override
    public File getChunkSaveLocation() {
        return this.d;
    }
}
