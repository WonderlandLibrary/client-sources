/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.audio.AudioStreamBuffer;
import net.minecraft.client.audio.AudioStreamManager;
import net.minecraft.client.audio.ChannelManager;
import net.minecraft.client.audio.IAudioStream;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventListener;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.Listener;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEngineExecutor;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.client.audio.SoundSystem;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class SoundEngine {
    private static final Marker LOG_MARKER = MarkerManager.getMarker("SOUNDS");
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Set<ResourceLocation> UNABLE_TO_PLAY = Sets.newHashSet();
    private final SoundHandler sndHandler;
    private final GameSettings options;
    private boolean loaded;
    private final SoundSystem sndSystem = new SoundSystem();
    private final Listener listener = this.sndSystem.getListener();
    private final AudioStreamManager audioStreamManager;
    private final SoundEngineExecutor executor = new SoundEngineExecutor();
    private final ChannelManager channelManager = new ChannelManager(this.sndSystem, this.executor);
    private int ticks;
    private final Map<ISound, ChannelManager.Entry> playingSoundsChannel = Maps.newHashMap();
    private final Multimap<SoundCategory, ISound> categorySounds = HashMultimap.create();
    private final List<ITickableSound> tickableSounds = Lists.newArrayList();
    private final Map<ISound, Integer> delayedSounds = Maps.newHashMap();
    private final Map<ISound, Integer> playingSoundsStopTime = Maps.newHashMap();
    private final List<ISoundEventListener> listeners = Lists.newArrayList();
    private final List<ITickableSound> tickableSoundsToPlayOnNextTick = Lists.newArrayList();
    private final List<Sound> soundsToPreload = Lists.newArrayList();

    public SoundEngine(SoundHandler soundHandler, GameSettings gameSettings, IResourceManager iResourceManager) {
        this.sndHandler = soundHandler;
        this.options = gameSettings;
        this.audioStreamManager = new AudioStreamManager(iResourceManager);
    }

    public void reload() {
        UNABLE_TO_PLAY.clear();
        for (SoundEvent soundEvent : Registry.SOUND_EVENT) {
            ResourceLocation resourceLocation = soundEvent.getName();
            if (this.sndHandler.getAccessor(resourceLocation) != null) continue;
            LOGGER.warn("Missing sound for event: {}", (Object)Registry.SOUND_EVENT.getKey(soundEvent));
            UNABLE_TO_PLAY.add(resourceLocation);
        }
        this.unload();
        this.load();
    }

    private synchronized void load() {
        if (!this.loaded) {
            try {
                this.sndSystem.init();
                this.listener.init();
                this.listener.setGain(this.options.getSoundLevel(SoundCategory.MASTER));
                this.audioStreamManager.preload(this.soundsToPreload).thenRun(this.soundsToPreload::clear);
                this.loaded = true;
                LOGGER.info(LOG_MARKER, "Sound engine started");
            } catch (RuntimeException runtimeException) {
                LOGGER.error(LOG_MARKER, "Error starting SoundSystem. Turning off sounds & music", (Throwable)runtimeException);
            }
        }
    }

    private float getVolume(@Nullable SoundCategory soundCategory) {
        return soundCategory != null && soundCategory != SoundCategory.MASTER ? this.options.getSoundLevel(soundCategory) : 1.0f;
    }

    public void setVolume(SoundCategory soundCategory, float f) {
        if (this.loaded) {
            if (soundCategory == SoundCategory.MASTER) {
                this.listener.setGain(f);
            } else {
                this.playingSoundsChannel.forEach(this::lambda$setVolume$1);
            }
        }
    }

    public void unload() {
        if (this.loaded) {
            this.stopAllSounds();
            this.audioStreamManager.clearAudioBufferCache();
            this.sndSystem.unload();
            this.loaded = false;
        }
    }

    public void stop(ISound iSound) {
        ChannelManager.Entry entry;
        if (this.loaded && (entry = this.playingSoundsChannel.get(iSound)) != null) {
            entry.runOnSoundExecutor(SoundSource::stop);
        }
    }

    public void stopAllSounds() {
        if (this.loaded) {
            this.executor.restart();
            this.playingSoundsChannel.values().forEach(SoundEngine::lambda$stopAllSounds$2);
            this.playingSoundsChannel.clear();
            this.channelManager.releaseAll();
            this.delayedSounds.clear();
            this.tickableSounds.clear();
            this.categorySounds.clear();
            this.playingSoundsStopTime.clear();
            this.tickableSoundsToPlayOnNextTick.clear();
        }
    }

    public void addListener(ISoundEventListener iSoundEventListener) {
        this.listeners.add(iSoundEventListener);
    }

    public void removeListener(ISoundEventListener iSoundEventListener) {
        this.listeners.remove(iSoundEventListener);
    }

    public void tick(boolean bl) {
        if (!bl) {
            this.tickNonPaused();
        }
        this.channelManager.tick();
    }

    private void tickNonPaused() {
        ++this.ticks;
        this.tickableSoundsToPlayOnNextTick.stream().filter(ISound::shouldPlaySound).forEach(this::play);
        this.tickableSoundsToPlayOnNextTick.clear();
        for (ITickableSound object2 : this.tickableSounds) {
            if (!object2.shouldPlaySound()) {
                this.stop(object2);
            }
            object2.tick();
            if (object2.isDonePlaying()) {
                this.stop(object2);
                continue;
            }
            float f = this.getClampedVolume(object2);
            float f2 = this.getClampedPitch(object2);
            Vector3d vector3d = new Vector3d(object2.getX(), object2.getY(), object2.getZ());
            ChannelManager.Entry entry = this.playingSoundsChannel.get(object2);
            if (entry == null) continue;
            entry.runOnSoundExecutor(arg_0 -> SoundEngine.lambda$tickNonPaused$3(f, f2, vector3d, arg_0));
        }
        Iterator<Object> iterator2 = this.playingSoundsChannel.entrySet().iterator();
        while (iterator2.hasNext()) {
            int n;
            Map.Entry entry = (Map.Entry)iterator2.next();
            ChannelManager.Entry entry2 = (ChannelManager.Entry)entry.getValue();
            ISound iSound = (ISound)entry.getKey();
            float f = this.options.getSoundLevel(iSound.getCategory());
            if (f <= 0.0f) {
                entry2.runOnSoundExecutor(SoundSource::stop);
                iterator2.remove();
                continue;
            }
            if (!entry2.isReleased() || (n = this.playingSoundsStopTime.get(iSound).intValue()) > this.ticks) continue;
            if (SoundEngine.canRepeatAndHasDelay(iSound)) {
                this.delayedSounds.put(iSound, this.ticks + iSound.getRepeatDelay());
            }
            iterator2.remove();
            LOGGER.debug(LOG_MARKER, "Removed channel {} because it's not playing anymore", (Object)entry2);
            this.playingSoundsStopTime.remove(iSound);
            try {
                this.categorySounds.remove((Object)iSound.getCategory(), iSound);
            } catch (RuntimeException runtimeException) {
                // empty catch block
            }
            if (!(iSound instanceof ITickableSound)) continue;
            this.tickableSounds.remove(iSound);
        }
        Iterator<Map.Entry<ISound, Integer>> iterator3 = this.delayedSounds.entrySet().iterator();
        while (iterator3.hasNext()) {
            Map.Entry<ISound, Integer> entry = iterator3.next();
            if (this.ticks < entry.getValue()) continue;
            ISound iSound = entry.getKey();
            if (iSound instanceof ITickableSound) {
                ((ITickableSound)iSound).tick();
            }
            this.play(iSound);
            iterator3.remove();
        }
    }

    private static boolean hasRepeatDelay(ISound iSound) {
        return iSound.getRepeatDelay() > 0;
    }

    private static boolean canRepeatAndHasDelay(ISound iSound) {
        return iSound.canRepeat() && SoundEngine.hasRepeatDelay(iSound);
    }

    private static boolean canRepeatAndHasNoDelay(ISound iSound) {
        return iSound.canRepeat() && !SoundEngine.hasRepeatDelay(iSound);
    }

    public boolean isPlaying(ISound iSound) {
        if (!this.loaded) {
            return true;
        }
        return this.playingSoundsStopTime.containsKey(iSound) && this.playingSoundsStopTime.get(iSound) <= this.ticks ? true : this.playingSoundsChannel.containsKey(iSound);
    }

    public void play(ISound iSound) {
        if (this.loaded && iSound.shouldPlaySound()) {
            SoundEventAccessor soundEventAccessor = iSound.createAccessor(this.sndHandler);
            ResourceLocation resourceLocation = iSound.getSoundLocation();
            if (soundEventAccessor == null) {
                if (UNABLE_TO_PLAY.add(resourceLocation)) {
                    LOGGER.warn(LOG_MARKER, "Unable to play unknown soundEvent: {}", (Object)resourceLocation);
                }
            } else {
                Sound sound = iSound.getSound();
                if (sound == SoundHandler.MISSING_SOUND) {
                    if (UNABLE_TO_PLAY.add(resourceLocation)) {
                        LOGGER.warn(LOG_MARKER, "Unable to play empty soundEvent: {}", (Object)resourceLocation);
                    }
                } else {
                    float f = iSound.getVolume();
                    float f2 = Math.max(f, 1.0f) * (float)sound.getAttenuationDistance();
                    SoundCategory soundCategory = iSound.getCategory();
                    float f3 = this.getClampedVolume(iSound);
                    float f4 = this.getClampedPitch(iSound);
                    ISound.AttenuationType attenuationType = iSound.getAttenuationType();
                    boolean bl = iSound.isGlobal();
                    if (f3 == 0.0f && !iSound.canBeSilent()) {
                        LOGGER.debug(LOG_MARKER, "Skipped playing sound {}, volume was zero.", (Object)sound.getSoundLocation());
                    } else {
                        boolean bl2;
                        Vector3d vector3d = new Vector3d(iSound.getX(), iSound.getY(), iSound.getZ());
                        if (!this.listeners.isEmpty()) {
                            boolean bl3 = bl2 = bl || attenuationType == ISound.AttenuationType.NONE || this.listener.getClientLocation().squareDistanceTo(vector3d) < (double)(f2 * f2);
                            if (bl2) {
                                for (ISoundEventListener completableFuture : this.listeners) {
                                    completableFuture.onPlaySound(iSound, soundEventAccessor);
                                }
                            } else {
                                LOGGER.debug(LOG_MARKER, "Did not notify listeners of soundEvent: {}, it is too far away to hear", (Object)resourceLocation);
                            }
                        }
                        if (this.listener.getGain() <= 0.0f) {
                            LOGGER.debug(LOG_MARKER, "Skipped playing soundEvent: {}, master volume was zero", (Object)resourceLocation);
                        } else {
                            bl2 = SoundEngine.canRepeatAndHasNoDelay(iSound);
                            boolean bl4 = sound.isStreaming();
                            CompletableFuture<ChannelManager.Entry> completableFuture = this.channelManager.requestSoundEntry(sound.isStreaming() ? SoundSystem.Mode.STREAMING : SoundSystem.Mode.STATIC);
                            ChannelManager.Entry entry = completableFuture.join();
                            if (entry == null) {
                                LOGGER.warn("Failed to create new sound handle");
                            } else {
                                LOGGER.debug(LOG_MARKER, "Playing sound {} for event {}", (Object)sound.getSoundLocation(), (Object)resourceLocation);
                                this.playingSoundsStopTime.put(iSound, this.ticks + 20);
                                this.playingSoundsChannel.put(iSound, entry);
                                this.categorySounds.put(soundCategory, iSound);
                                entry.runOnSoundExecutor(arg_0 -> SoundEngine.lambda$play$4(f4, f3, attenuationType, f2, bl2, bl4, vector3d, bl, arg_0));
                                if (!bl4) {
                                    this.audioStreamManager.createResource(sound.getSoundAsOggLocation()).thenAccept(arg_0 -> SoundEngine.lambda$play$6(entry, arg_0));
                                } else {
                                    this.audioStreamManager.createStreamingResource(sound.getSoundAsOggLocation(), bl2).thenAccept(arg_0 -> SoundEngine.lambda$play$8(entry, arg_0));
                                }
                                if (iSound instanceof ITickableSound) {
                                    this.tickableSounds.add((ITickableSound)iSound);
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void playOnNextTick(ITickableSound iTickableSound) {
        this.tickableSoundsToPlayOnNextTick.add(iTickableSound);
    }

    public void enqueuePreload(Sound sound) {
        this.soundsToPreload.add(sound);
    }

    private float getClampedPitch(ISound iSound) {
        return MathHelper.clamp(iSound.getPitch(), 0.5f, 2.0f);
    }

    private float getClampedVolume(ISound iSound) {
        return MathHelper.clamp(iSound.getVolume() * this.getVolume(iSound.getCategory()), 0.0f, 1.0f);
    }

    public void pause() {
        if (this.loaded) {
            this.channelManager.runForAllSoundSources(SoundEngine::lambda$pause$9);
        }
    }

    public void resume() {
        if (this.loaded) {
            this.channelManager.runForAllSoundSources(SoundEngine::lambda$resume$10);
        }
    }

    public void playDelayed(ISound iSound, int n) {
        this.delayedSounds.put(iSound, this.ticks + n);
    }

    public void updateListener(ActiveRenderInfo activeRenderInfo) {
        if (this.loaded && activeRenderInfo.isValid()) {
            Vector3d vector3d = activeRenderInfo.getProjectedView();
            Vector3f vector3f = activeRenderInfo.getViewVector();
            Vector3f vector3f2 = activeRenderInfo.getUpVector();
            this.executor.execute(() -> this.lambda$updateListener$11(vector3d, vector3f, vector3f2));
        }
    }

    public void stop(@Nullable ResourceLocation resourceLocation, @Nullable SoundCategory soundCategory) {
        if (soundCategory != null) {
            for (ISound iSound : this.categorySounds.get(soundCategory)) {
                if (resourceLocation != null && !iSound.getSoundLocation().equals(resourceLocation)) continue;
                this.stop(iSound);
            }
        } else if (resourceLocation == null) {
            this.stopAllSounds();
        } else {
            for (ISound iSound : this.playingSoundsChannel.keySet()) {
                if (!iSound.getSoundLocation().equals(resourceLocation)) continue;
                this.stop(iSound);
            }
        }
    }

    public String getDebugString() {
        return this.sndSystem.getDebugString();
    }

    private void lambda$updateListener$11(Vector3d vector3d, Vector3f vector3f, Vector3f vector3f2) {
        this.listener.setPosition(vector3d);
        this.listener.setOrientation(vector3f, vector3f2);
    }

    private static void lambda$resume$10(Stream stream) {
        stream.forEach(SoundSource::resume);
    }

    private static void lambda$pause$9(Stream stream) {
        stream.forEach(SoundSource::pause);
    }

    private static void lambda$play$8(ChannelManager.Entry entry, IAudioStream iAudioStream) {
        entry.runOnSoundExecutor(arg_0 -> SoundEngine.lambda$play$7(iAudioStream, arg_0));
    }

    private static void lambda$play$7(IAudioStream iAudioStream, SoundSource soundSource) {
        soundSource.playStreamableSounds(iAudioStream);
        soundSource.play();
    }

    private static void lambda$play$6(ChannelManager.Entry entry, AudioStreamBuffer audioStreamBuffer) {
        entry.runOnSoundExecutor(arg_0 -> SoundEngine.lambda$play$5(audioStreamBuffer, arg_0));
    }

    private static void lambda$play$5(AudioStreamBuffer audioStreamBuffer, SoundSource soundSource) {
        soundSource.bindBuffer(audioStreamBuffer);
        soundSource.play();
    }

    private static void lambda$play$4(float f, float f2, ISound.AttenuationType attenuationType, float f3, boolean bl, boolean bl2, Vector3d vector3d, boolean bl3, SoundSource soundSource) {
        soundSource.setPitch(f);
        soundSource.setGain(f2);
        if (attenuationType == ISound.AttenuationType.LINEAR) {
            soundSource.setLinearAttenuation(f3);
        } else {
            soundSource.setNoAttenuation();
        }
        soundSource.setLooping(bl && !bl2);
        soundSource.updateSource(vector3d);
        soundSource.setRelative(bl3);
    }

    private static void lambda$tickNonPaused$3(float f, float f2, Vector3d vector3d, SoundSource soundSource) {
        soundSource.setGain(f);
        soundSource.setPitch(f2);
        soundSource.updateSource(vector3d);
    }

    private static void lambda$stopAllSounds$2(ChannelManager.Entry entry) {
        entry.runOnSoundExecutor(SoundSource::stop);
    }

    private void lambda$setVolume$1(ISound iSound, ChannelManager.Entry entry) {
        float f = this.getClampedVolume(iSound);
        entry.runOnSoundExecutor(arg_0 -> SoundEngine.lambda$setVolume$0(f, arg_0));
    }

    private static void lambda$setVolume$0(float f, SoundSource soundSource) {
        if (f <= 0.0f) {
            soundSource.stop();
        } else {
            soundSource.setGain(f);
        }
    }
}

