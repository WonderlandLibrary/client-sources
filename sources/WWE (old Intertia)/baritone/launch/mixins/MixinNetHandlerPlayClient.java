package baritone.launch.mixins;

import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.callback.*;
import baritone.api.*;
import baritone.api.event.events.type.*;
import baritone.api.event.events.*;
import java.util.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin({ brz.class })
public class MixinNetHandlerPlayClient
{
    public MixinNetHandlerPlayClient() {
        super();
    }
    
    @Inject(method = { "handleChunkData" }, at = { @At(value = "INVOKE", target = "net/minecraft/world/chunk/Chunk.read(Lnet/minecraft/network/PacketBuffer;IZ)V") })
    private void preRead(final je je, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player().d == (brz)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.PRE, je.e() ? ChunkEvent$Type.POPULATE_FULL : ChunkEvent$Type.POPULATE_PARTIAL, je.b(), je.c()));
            }
        }
    }
    
    @Inject(method = { "handleChunkData" }, at = { @At("RETURN") })
    private void postHandleChunkData(final je je, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player().d == (brz)this) {
                baritone.getGameEventHandler().onChunkEvent(new ChunkEvent(EventState.POST, je.e() ? ChunkEvent$Type.POPULATE_FULL : ChunkEvent$Type.POPULATE_PARTIAL, je.b(), je.c()));
            }
        }
    }
    
    @Inject(method = { "handleCombatEvent" }, at = { @At(value = "INVOKE", target = "net/minecraft/client/Minecraft.displayGuiScreen(Lnet/minecraft/client/gui/GuiScreen;)V") })
    private void onPlayerDeath(final jo jo, final CallbackInfo callbackInfo) {
        final Iterator<IBaritone> iterator = BaritoneAPI.getProvider().getAllBaritones().iterator();
        while (iterator.hasNext()) {
            final IBaritone baritone;
            if ((baritone = iterator.next()).getPlayerContext().player().d == (brz)this) {
                baritone.getGameEventHandler().onPlayerDeath();
            }
        }
    }
}
