package net.silentclient.client.mixin.mixins;

import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SoundManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import paulscode.sound.SoundSystem;

import java.util.*;

@Mixin(SoundManager.class)
public abstract class SoundManagerMixin {
    //#if MC==10809
    @Shadow public abstract boolean isSoundPlaying(ISound sound);

    @Shadow @Final private Map<String, ISound> playingSounds;

    private final List<String> silent$pausedSounds = new ArrayList<>();

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @Redirect(
            method = "pauseAllSounds",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/client/audio/SoundManager$SoundSystemStarterThread;pause(Ljava/lang/String;)V", remap = false)
    )
    private void silent$onlyPauseSoundIfNecessary(@Coerce SoundSystem soundSystem, String sound) {
        if (isSoundPlaying(playingSounds.get(sound))) {
            soundSystem.pause(sound);
            silent$pausedSounds.add(sound);
        }
    }

    @Redirect(
            method = "resumeAllSounds",
            at = @At(value = "INVOKE", target = "Ljava/util/Set;iterator()Ljava/util/Iterator;", remap = false)
    )
    private Iterator<String> silent$iterateOverPausedSounds(Set<String> keySet) {
        return silent$pausedSounds.iterator();
    }

    @Inject(method = "resumeAllSounds", at = @At("TAIL"))
    private void silent$clearPausedSounds(CallbackInfo ci) {
        silent$pausedSounds.clear();
    }
    //#endif

    @Redirect(
            method = "playSound",
            slice = @Slice(from = @At(value = "CONSTANT", args = "stringValue=Unable to play unknown soundEvent: {}", ordinal = 0)),
            at = @At(value = "INVOKE", target = "Lorg/apache/logging/log4j/Logger;warn(Lorg/apache/logging/log4j/Marker;Ljava/lang/String;[Ljava/lang/Object;)V", ordinal = 0, remap = false)
    )
    private void silent$silenceWarning(Logger instance, Marker marker, String s, Object[] objects) {
        // No-op
    }
}
