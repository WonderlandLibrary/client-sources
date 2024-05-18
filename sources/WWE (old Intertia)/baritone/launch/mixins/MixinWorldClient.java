package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import baritone.api.*;
import baritone.api.event.events.type.*;
import baritone.api.event.events.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ bsb.class })
public class MixinWorldClient
{
    public MixinWorldClient() {
        super();
    }
    
    @Inject(method = { "doPreChunk" }, at = { @At("HEAD") })
    private void preDoPreChunk(final int n, final int n2, final boolean b, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().world() == (bsb)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.PRE, b ? ChunkEvent$Type.LOAD : ChunkEvent$Type.UNLOAD, n, n2));
            }
        }
    }
    
    @Inject(method = { "doPreChunk" }, at = { @At("RETURN") })
    private void postDoPreChunk(final int n, final int n2, final boolean b, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().world() == (bsb)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, b ? ChunkEvent$Type.LOAD : ChunkEvent$Type.UNLOAD, n, n2));
            }
        }
    }
}
