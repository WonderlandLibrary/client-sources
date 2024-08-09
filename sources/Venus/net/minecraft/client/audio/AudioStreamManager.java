/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import com.google.common.collect.Maps;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import net.minecraft.client.audio.AudioStreamBuffer;
import net.minecraft.client.audio.IAudioStream;
import net.minecraft.client.audio.OggAudioStream;
import net.minecraft.client.audio.OggAudioStreamWrapper;
import net.minecraft.client.audio.Sound;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;

public class AudioStreamManager {
    private final IResourceManager resourceManager;
    private final Map<ResourceLocation, CompletableFuture<AudioStreamBuffer>> bufferCache = Maps.newHashMap();

    public AudioStreamManager(IResourceManager iResourceManager) {
        this.resourceManager = iResourceManager;
    }

    public CompletableFuture<AudioStreamBuffer> createResource(ResourceLocation resourceLocation) {
        return this.bufferCache.computeIfAbsent(resourceLocation, this::lambda$createResource$1);
    }

    public CompletableFuture<IAudioStream> createStreamingResource(ResourceLocation resourceLocation, boolean bl) {
        return CompletableFuture.supplyAsync(() -> this.lambda$createStreamingResource$2(resourceLocation, bl), Util.getServerExecutor());
    }

    public void clearAudioBufferCache() {
        this.bufferCache.values().forEach(AudioStreamManager::lambda$clearAudioBufferCache$3);
        this.bufferCache.clear();
    }

    public CompletableFuture<?> preload(Collection<Sound> collection) {
        return CompletableFuture.allOf((CompletableFuture[])collection.stream().map(this::lambda$preload$4).toArray(AudioStreamManager::lambda$preload$5));
    }

    private static CompletableFuture[] lambda$preload$5(int n) {
        return new CompletableFuture[n];
    }

    private CompletableFuture lambda$preload$4(Sound sound) {
        return this.createResource(sound.getSoundAsOggLocation());
    }

    private static void lambda$clearAudioBufferCache$3(CompletableFuture completableFuture) {
        completableFuture.thenAccept(AudioStreamBuffer::deleteBuffer);
    }

    private IAudioStream lambda$createStreamingResource$2(ResourceLocation resourceLocation, boolean bl) {
        try {
            IResource iResource = this.resourceManager.getResource(resourceLocation);
            InputStream inputStream = iResource.getInputStream();
            return bl ? new OggAudioStreamWrapper(OggAudioStream::new, inputStream) : new OggAudioStream(inputStream);
        } catch (IOException iOException) {
            throw new CompletionException(iOException);
        }
    }

    private CompletableFuture lambda$createResource$1(ResourceLocation resourceLocation) {
        return CompletableFuture.supplyAsync(() -> this.lambda$createResource$0(resourceLocation), Util.getServerExecutor());
    }

    /*
     * Exception decompiling
     */
    private AudioStreamBuffer lambda$createResource$0(ResourceLocation var1_1) {
        /*
         * This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
         * 
         * org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:412)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:487)
         *     at org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:736)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:850)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:278)
         *     at org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:201)
         *     at org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:94)
         *     at org.benf.cfr.reader.entities.Method.analyse(Method.java:538)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:1055)
         *     at org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:942)
         *     at org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:257)
         *     at org.benf.cfr.reader.Driver.doJar(Driver.java:139)
         *     at org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:76)
         *     at org.benf.cfr.reader.Main.main(Main.java:54)
         *     at async.DecompilerRunnable.cfrDecompilation(DecompilerRunnable.java:348)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:309)
         *     at async.DecompilerRunnable.call(DecompilerRunnable.java:31)
         *     at java.util.concurrent.FutureTask.run(FutureTask.java:266)
         *     at java.util.concurrent.ThreadPoolExecutor.runWorker(ThreadPoolExecutor.java:1149)
         *     at java.util.concurrent.ThreadPoolExecutor$Worker.run(ThreadPoolExecutor.java:624)
         *     at java.lang.Thread.run(Thread.java:750)
         */
        throw new IllegalStateException("Decompilation failed");
    }
}

