/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.GameSettings;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.ISoundEventAccessor;
import net.minecraft.client.audio.ISoundEventListener;
import net.minecraft.client.audio.ITickableSound;
import net.minecraft.client.audio.Sound;
import net.minecraft.client.audio.SoundEngine;
import net.minecraft.client.audio.SoundEventAccessor;
import net.minecraft.client.audio.SoundList;
import net.minecraft.client.audio.SoundListSerializer;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.resources.ReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class SoundHandler
extends ReloadListener<Loader> {
    public static final Sound MISSING_SOUND = new Sound("meta:missing_sound", 1.0f, 1.0f, 1, Sound.Type.FILE, false, false, 16);
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().registerTypeHierarchyAdapter(ITextComponent.class, new ITextComponent.Serializer()).registerTypeAdapter((Type)((Object)SoundList.class), new SoundListSerializer()).create();
    private static final TypeToken<Map<String, SoundList>> TYPE = new TypeToken<Map<String, SoundList>>(){};
    private final Map<ResourceLocation, SoundEventAccessor> soundRegistry = Maps.newHashMap();
    private final SoundEngine sndManager;

    public SoundHandler(IResourceManager iResourceManager, GameSettings gameSettings) {
        this.sndManager = new SoundEngine(this, gameSettings, iResourceManager);
    }

    @Override
    protected Loader prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        Loader loader = new Loader();
        iProfiler.startTick();
        for (String string : iResourceManager.getResourceNamespaces()) {
            iProfiler.startSection(string);
            try {
                for (IResource iResource : iResourceManager.getAllResources(new ResourceLocation(string, "sounds.json"))) {
                    iProfiler.startSection(iResource.getPackName());
                    try (InputStream inputStream = iResource.getInputStream();
                         InputStreamReader inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);){
                        iProfiler.startSection("parse");
                        Map<String, SoundList> map = JSONUtils.fromJSONUnlenient(GSON, inputStreamReader, TYPE);
                        iProfiler.endStartSection("register");
                        for (Map.Entry<String, SoundList> entry : map.entrySet()) {
                            loader.registerSoundEvent(new ResourceLocation(string, entry.getKey()), entry.getValue(), iResourceManager);
                        }
                        iProfiler.endSection();
                    } catch (RuntimeException runtimeException) {
                        LOGGER.warn("Invalid sounds.json in resourcepack: '{}'", (Object)iResource.getPackName(), (Object)runtimeException);
                    }
                    iProfiler.endSection();
                }
            } catch (IOException iOException) {
                // empty catch block
            }
            iProfiler.endSection();
        }
        iProfiler.endTick();
        return loader;
    }

    @Override
    protected void apply(Loader loader, IResourceManager iResourceManager, IProfiler iProfiler) {
        loader.preloadSounds(this.soundRegistry, this.sndManager);
        for (ResourceLocation resourceLocation : this.soundRegistry.keySet()) {
            String string;
            SoundEventAccessor soundEventAccessor = this.soundRegistry.get(resourceLocation);
            if (!(soundEventAccessor.getSubtitle() instanceof TranslationTextComponent) || I18n.hasKey(string = ((TranslationTextComponent)soundEventAccessor.getSubtitle()).getKey())) continue;
            LOGGER.debug("Missing subtitle {} for event: {}", (Object)string, (Object)resourceLocation);
        }
        if (LOGGER.isDebugEnabled()) {
            for (ResourceLocation resourceLocation : this.soundRegistry.keySet()) {
                if (Registry.SOUND_EVENT.containsKey(resourceLocation)) continue;
                LOGGER.debug("Not having sound event for: {}", (Object)resourceLocation);
            }
        }
        this.sndManager.reload();
    }

    private static boolean isValidSound(Sound sound, ResourceLocation resourceLocation, IResourceManager iResourceManager) {
        ResourceLocation resourceLocation2 = sound.getSoundAsOggLocation();
        if (!iResourceManager.hasResource(resourceLocation2)) {
            LOGGER.warn("File {} does not exist, cannot add it to event {}", (Object)resourceLocation2, (Object)resourceLocation);
            return true;
        }
        return false;
    }

    @Nullable
    public SoundEventAccessor getAccessor(ResourceLocation resourceLocation) {
        return this.soundRegistry.get(resourceLocation);
    }

    public Collection<ResourceLocation> getAvailableSounds() {
        return this.soundRegistry.keySet();
    }

    public void playOnNextTick(ITickableSound iTickableSound) {
        this.sndManager.playOnNextTick(iTickableSound);
    }

    public void play(ISound iSound) {
        this.sndManager.play(iSound);
    }

    public void playDelayed(ISound iSound, int n) {
        this.sndManager.playDelayed(iSound, n);
    }

    public void updateListener(ActiveRenderInfo activeRenderInfo) {
        this.sndManager.updateListener(activeRenderInfo);
    }

    public void pause() {
        this.sndManager.pause();
    }

    public void stop() {
        this.sndManager.stopAllSounds();
    }

    public void unloadSounds() {
        this.sndManager.unload();
    }

    public void tick(boolean bl) {
        this.sndManager.tick(bl);
    }

    public void resume() {
        this.sndManager.resume();
    }

    public void setSoundLevel(SoundCategory soundCategory, float f) {
        if (soundCategory == SoundCategory.MASTER && f <= 0.0f) {
            this.stop();
        }
        this.sndManager.setVolume(soundCategory, f);
    }

    public void stop(ISound iSound) {
        this.sndManager.stop(iSound);
    }

    public boolean isPlaying(ISound iSound) {
        return this.sndManager.isPlaying(iSound);
    }

    public void addListener(ISoundEventListener iSoundEventListener) {
        this.sndManager.addListener(iSoundEventListener);
    }

    public void removeListener(ISoundEventListener iSoundEventListener) {
        this.sndManager.removeListener(iSoundEventListener);
    }

    public void stop(@Nullable ResourceLocation resourceLocation, @Nullable SoundCategory soundCategory) {
        this.sndManager.stop(resourceLocation, soundCategory);
    }

    public String getDebugString() {
        return this.sndManager.getDebugString();
    }

    @Override
    protected void apply(Object object, IResourceManager iResourceManager, IProfiler iProfiler) {
        this.apply((Loader)object, iResourceManager, iProfiler);
    }

    @Override
    protected Object prepare(IResourceManager iResourceManager, IProfiler iProfiler) {
        return this.prepare(iResourceManager, iProfiler);
    }

    public static class Loader {
        private final Map<ResourceLocation, SoundEventAccessor> soundRegistry = Maps.newHashMap();

        protected Loader() {
        }

        private void registerSoundEvent(ResourceLocation resourceLocation, SoundList soundList, IResourceManager iResourceManager) {
            boolean bl;
            SoundEventAccessor soundEventAccessor = this.soundRegistry.get(resourceLocation);
            boolean bl2 = bl = soundEventAccessor == null;
            if (bl || soundList.canReplaceExisting()) {
                if (!bl) {
                    LOGGER.debug("Replaced sound event location {}", (Object)resourceLocation);
                }
                soundEventAccessor = new SoundEventAccessor(resourceLocation, soundList.getSubtitle());
                this.soundRegistry.put(resourceLocation, soundEventAccessor);
            }
            block4: for (Sound sound : soundList.getSounds()) {
                ResourceLocation resourceLocation2 = sound.getSoundLocation();
                soundEventAccessor.addSound(switch (sound.getType()) {
                    case Sound.Type.FILE -> {
                        if (!SoundHandler.isValidSound(sound, resourceLocation, iResourceManager)) continue block4;
                        yield sound;
                    }
                    case Sound.Type.SOUND_EVENT -> new ISoundEventAccessor<Sound>(){
                        final ResourceLocation val$resourcelocation;
                        final Sound val$sound;
                        final Loader this$0;
                        {
                            this.this$0 = loader;
                            this.val$resourcelocation = resourceLocation;
                            this.val$sound = sound;
                        }

                        @Override
                        public int getWeight() {
                            SoundEventAccessor soundEventAccessor = this.this$0.soundRegistry.get(this.val$resourcelocation);
                            return soundEventAccessor == null ? 0 : soundEventAccessor.getWeight();
                        }

                        @Override
                        public Sound cloneEntry() {
                            SoundEventAccessor soundEventAccessor = this.this$0.soundRegistry.get(this.val$resourcelocation);
                            if (soundEventAccessor == null) {
                                return MISSING_SOUND;
                            }
                            Sound sound = soundEventAccessor.cloneEntry();
                            return new Sound(sound.getSoundLocation().toString(), sound.getVolume() * this.val$sound.getVolume(), sound.getPitch() * this.val$sound.getPitch(), this.val$sound.getWeight(), Sound.Type.FILE, sound.isStreaming() || this.val$sound.isStreaming(), sound.shouldPreload(), sound.getAttenuationDistance());
                        }

                        @Override
                        public void enqueuePreload(SoundEngine soundEngine) {
                            SoundEventAccessor soundEventAccessor = this.this$0.soundRegistry.get(this.val$resourcelocation);
                            if (soundEventAccessor != null) {
                                soundEventAccessor.enqueuePreload(soundEngine);
                            }
                        }

                        @Override
                        public Object cloneEntry() {
                            return this.cloneEntry();
                        }
                    };
                    default -> throw new IllegalStateException("Unknown SoundEventRegistration type: " + sound.getType());
                });
            }
        }

        public void preloadSounds(Map<ResourceLocation, SoundEventAccessor> map, SoundEngine soundEngine) {
            map.clear();
            for (Map.Entry<ResourceLocation, SoundEventAccessor> entry : this.soundRegistry.entrySet()) {
                map.put(entry.getKey(), entry.getValue());
                entry.getValue().enqueuePreload(soundEngine);
            }
        }
    }
}

