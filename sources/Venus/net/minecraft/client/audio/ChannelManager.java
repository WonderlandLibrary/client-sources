/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.audio.SoundSource;
import net.minecraft.client.audio.SoundSystem;

public class ChannelManager {
    private final Set<Entry> channels = Sets.newIdentityHashSet();
    private final SoundSystem sndSystem;
    private final Executor soundExecutor;

    public ChannelManager(SoundSystem soundSystem, Executor executor) {
        this.sndSystem = soundSystem;
        this.soundExecutor = executor;
    }

    public CompletableFuture<Entry> requestSoundEntry(SoundSystem.Mode mode) {
        CompletableFuture<Entry> completableFuture = new CompletableFuture<Entry>();
        this.soundExecutor.execute(() -> this.lambda$requestSoundEntry$0(mode, completableFuture));
        return completableFuture;
    }

    public void runForAllSoundSources(Consumer<Stream<SoundSource>> consumer) {
        this.soundExecutor.execute(() -> this.lambda$runForAllSoundSources$2(consumer));
    }

    public void tick() {
        this.soundExecutor.execute(this::lambda$tick$3);
    }

    public void releaseAll() {
        this.channels.forEach(Entry::release);
        this.channels.clear();
    }

    private void lambda$tick$3() {
        Iterator<Entry> iterator2 = this.channels.iterator();
        while (iterator2.hasNext()) {
            Entry entry = iterator2.next();
            entry.source.tick();
            if (!entry.source.isStopped()) continue;
            entry.release();
            iterator2.remove();
        }
    }

    private void lambda$runForAllSoundSources$2(Consumer consumer) {
        consumer.accept(this.channels.stream().map(ChannelManager::lambda$runForAllSoundSources$1).filter(Objects::nonNull));
    }

    private static SoundSource lambda$runForAllSoundSources$1(Entry entry) {
        return entry.source;
    }

    private void lambda$requestSoundEntry$0(SoundSystem.Mode mode, CompletableFuture completableFuture) {
        SoundSource soundSource = this.sndSystem.getSource(mode);
        if (soundSource != null) {
            Entry entry = new Entry(this, soundSource);
            this.channels.add(entry);
            completableFuture.complete(entry);
        } else {
            completableFuture.complete(null);
        }
    }

    public class Entry {
        @Nullable
        private SoundSource source;
        private boolean released;
        final ChannelManager this$0;

        public boolean isReleased() {
            return this.released;
        }

        public Entry(ChannelManager channelManager, SoundSource soundSource) {
            this.this$0 = channelManager;
            this.source = soundSource;
        }

        public void runOnSoundExecutor(Consumer<SoundSource> consumer) {
            this.this$0.soundExecutor.execute(() -> this.lambda$runOnSoundExecutor$0(consumer));
        }

        public void release() {
            this.released = true;
            this.this$0.sndSystem.release(this.source);
            this.source = null;
        }

        private void lambda$runOnSoundExecutor$0(Consumer consumer) {
            if (this.source != null) {
                consumer.accept(this.source);
            }
        }
    }
}

