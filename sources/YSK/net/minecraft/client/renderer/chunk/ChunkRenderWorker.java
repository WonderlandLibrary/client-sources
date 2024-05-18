package net.minecraft.client.renderer.chunk;

import net.minecraft.client.renderer.*;
import net.minecraft.client.*;
import net.minecraft.crash.*;
import com.google.common.collect.*;
import net.minecraft.util.*;
import com.google.common.util.concurrent.*;
import java.util.concurrent.*;
import net.minecraft.entity.*;
import java.util.*;
import org.apache.logging.log4j.*;

public class ChunkRenderWorker implements Runnable
{
    private static final Logger LOGGER;
    private final ChunkRenderDispatcher chunkRenderDispatcher;
    private static final String[] I;
    private final RegionRenderCacheBuilder regionRenderCacheBuilder;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher chunkRenderDispatcher) {
        this(chunkRenderDispatcher, null);
    }
    
    private static void I() {
        (I = new String[0x1C ^ 0x1A])["".length()] = I("5:&\u001e\u001e\u000f .N\n\u0013+i\u001a\u0001F''\u001a\u000b\u0014<<\u001e\u001a", "fNInn");
        ChunkRenderWorker.I[" ".length()] = I("\n\u0004!\u0019!!\u000b2Z* \u0010;\u0011:", "HeUzI");
        ChunkRenderWorker.I["  ".length()] = I("(\u001f\f>\bK\u0005\u001c>\u0007\u000e\u0005Y$\u0002\u0018\u001cY'\u0002\u0018W", "kwyPc");
        ChunkRenderWorker.I["   ".length()] = I("Q\"\u001e*-Q\u001cV*;\u00010\u0015;&\u0015u\u001f;c\u0005:V-&Q%\u0013!'\u0018;\u0011tc\u00182\u0018 1\u0018;\u0011o7\u0010&\u001d", "qUvOC");
        ChunkRenderWorker.I[0xC4 ^ 0xC0] = I(".\t\u00174\bM\u0013\u00074\u0007\b\u0013B.\u0002\u001e\nB-\u0002\u001eA", "mabZc");
        ChunkRenderWorker.I[0x50 ^ 0x55] = I("M\u0015\u001c$6M+T$ \u001d\u0007\u00175=\tB\u001d5x\u0019\rT#=M\u0001\u001b,(\u0004\u000e\u001d/?VB\u0015#7\u001f\u0016\u001d/?M\u0016\u001523", "mbtAX");
    }
    
    static void access$0(final ChunkRenderWorker chunkRenderWorker, final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        chunkRenderWorker.freeRenderBuilder(chunkCompileTaskGenerator);
    }
    
    @Override
    public void run() {
        try {
            do {
                this.processTask(this.chunkRenderDispatcher.getNextChunkUpdate());
                "".length();
            } while (1 > -1);
            throw null;
        }
        catch (InterruptedException ex) {
            ChunkRenderWorker.LOGGER.debug(ChunkRenderWorker.I["".length()]);
        }
        catch (Throwable t) {
            Minecraft.getMinecraft().crashed(Minecraft.getMinecraft().addGraphicsAndWorldToCrashReport(CrashReport.makeCrashReport(t, ChunkRenderWorker.I[" ".length()])));
        }
    }
    
    private RegionRenderCacheBuilder getRegionRenderCacheBuilder() throws InterruptedException {
        RegionRenderCacheBuilder regionRenderCacheBuilder;
        if (this.regionRenderCacheBuilder != null) {
            regionRenderCacheBuilder = this.regionRenderCacheBuilder;
            "".length();
            if (-1 == 4) {
                throw null;
            }
        }
        else {
            regionRenderCacheBuilder = this.chunkRenderDispatcher.allocateRenderBuilder();
        }
        return regionRenderCacheBuilder;
    }
    
    protected void processTask(final ChunkCompileTaskGenerator chunkCompileTaskGenerator) throws InterruptedException {
        chunkCompileTaskGenerator.getLock().lock();
        try {
            if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.PENDING) {
                if (!chunkCompileTaskGenerator.isFinished()) {
                    ChunkRenderWorker.LOGGER.warn(ChunkRenderWorker.I["  ".length()] + chunkCompileTaskGenerator.getStatus() + ChunkRenderWorker.I["   ".length()]);
                }
                return;
            }
            chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.COMPILING);
            "".length();
            if (0 <= -1) {
                throw null;
            }
        }
        finally {
            chunkCompileTaskGenerator.getLock().unlock();
        }
        chunkCompileTaskGenerator.getLock().unlock();
        final Entity renderViewEntity = Minecraft.getMinecraft().getRenderViewEntity();
        if (renderViewEntity == null) {
            chunkCompileTaskGenerator.finish();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            chunkCompileTaskGenerator.setRegionRenderCacheBuilder(this.getRegionRenderCacheBuilder());
            final float n = (float)renderViewEntity.posX;
            final float n2 = (float)renderViewEntity.posY + renderViewEntity.getEyeHeight();
            final float n3 = (float)renderViewEntity.posZ;
            final ChunkCompileTaskGenerator.Type type = chunkCompileTaskGenerator.getType();
            if (type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                chunkCompileTaskGenerator.getRenderChunk().rebuildChunk(n, n2, n3, chunkCompileTaskGenerator);
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else if (type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                chunkCompileTaskGenerator.getRenderChunk().resortTransparency(n, n2, n3, chunkCompileTaskGenerator);
            }
            chunkCompileTaskGenerator.getLock().lock();
            try {
                if (chunkCompileTaskGenerator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
                    if (!chunkCompileTaskGenerator.isFinished()) {
                        ChunkRenderWorker.LOGGER.warn(ChunkRenderWorker.I[0xBA ^ 0xBE] + chunkCompileTaskGenerator.getStatus() + ChunkRenderWorker.I[0x34 ^ 0x31]);
                    }
                    this.freeRenderBuilder(chunkCompileTaskGenerator);
                    return;
                }
                chunkCompileTaskGenerator.setStatus(ChunkCompileTaskGenerator.Status.UPLOADING);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            finally {
                chunkCompileTaskGenerator.getLock().unlock();
            }
            chunkCompileTaskGenerator.getLock().unlock();
            final CompiledChunk compiledChunk = chunkCompileTaskGenerator.getCompiledChunk();
            final ArrayList arrayList = Lists.newArrayList();
            if (type == ChunkCompileTaskGenerator.Type.REBUILD_CHUNK) {
                final EnumWorldBlockLayer[] values;
                final int length = (values = EnumWorldBlockLayer.values()).length;
                int i = "".length();
                "".length();
                if (3 >= 4) {
                    throw null;
                }
                while (i < length) {
                    final EnumWorldBlockLayer enumWorldBlockLayer = values[i];
                    if (compiledChunk.isLayerStarted(enumWorldBlockLayer)) {
                        arrayList.add(this.chunkRenderDispatcher.uploadChunk(enumWorldBlockLayer, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumWorldBlockLayer), chunkCompileTaskGenerator.getRenderChunk(), compiledChunk));
                    }
                    ++i;
                }
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else if (type == ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY) {
                arrayList.add(this.chunkRenderDispatcher.uploadChunk(EnumWorldBlockLayer.TRANSLUCENT, chunkCompileTaskGenerator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT), chunkCompileTaskGenerator.getRenderChunk(), compiledChunk));
            }
            final ListenableFuture allAsList = Futures.allAsList((Iterable)arrayList);
            chunkCompileTaskGenerator.addFinishRunnable(new Runnable(this, allAsList) {
                final ChunkRenderWorker this$0;
                private final ListenableFuture val$listenablefuture;
                
                @Override
                public void run() {
                    this.val$listenablefuture.cancel((boolean)("".length() != 0));
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
            Futures.addCallback(allAsList, (FutureCallback)new FutureCallback<List<Object>>(this, chunkCompileTaskGenerator, compiledChunk) {
                private final ChunkCompileTaskGenerator val$generator;
                final ChunkRenderWorker this$0;
                private static final String[] I;
                private final CompiledChunk val$lvt_7_1_;
                
                static {
                    I();
                }
                
                public void onSuccess(final Object o) {
                    this.onSuccess((List<Object>)o);
                }
                
                private static void I() {
                    (I = new String["   ".length()])["".length()] = I("\u0005\u001c:\u001a=f\u0006*\u001a2#\u0006o\u000075\u001fo\u000375T", "FtOtV");
                    ChunkRenderWorker$2.I[" ".length()] = I("h1?/,h\u000fw/:8#4>',f>>b<)w('h3'&-)\">$%sf6(-:2>$%h269)", "HFWJB");
                    ChunkRenderWorker$2.I["  ".length()] = I("\u0000)\u0006\u0013= %\u0006\u0010x1$\u001d\u00193", "RLhwX");
                }
                
                private static String I(final String s, final String s2) {
                    final StringBuilder sb = new StringBuilder();
                    final char[] charArray = s2.toCharArray();
                    int length = "".length();
                    final char[] charArray2 = s.toCharArray();
                    final int length2 = charArray2.length;
                    int i = "".length();
                    while (i < length2) {
                        sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                        ++length;
                        ++i;
                        "".length();
                        if (3 == 4) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                public void onSuccess(final List<Object> list) {
                    ChunkRenderWorker.access$0(this.this$0, this.val$generator);
                    this.val$generator.getLock().lock();
                    Label_0163: {
                        try {
                            if (this.val$generator.getStatus() == ChunkCompileTaskGenerator.Status.UPLOADING) {
                                this.val$generator.setStatus(ChunkCompileTaskGenerator.Status.DONE);
                                this.val$generator.getLock().unlock();
                                "".length();
                                if (3 == -1) {
                                    throw null;
                                }
                                break Label_0163;
                            }
                            else if (!this.val$generator.isFinished()) {
                                ChunkRenderWorker.access$1().warn(ChunkRenderWorker$2.I["".length()] + this.val$generator.getStatus() + ChunkRenderWorker$2.I[" ".length()]);
                                "".length();
                                if (0 >= 4) {
                                    throw null;
                                }
                            }
                        }
                        finally {
                            this.val$generator.getLock().unlock();
                        }
                        this.val$generator.getLock().unlock();
                        return;
                    }
                    this.val$generator.getRenderChunk().setCompiledChunk(this.val$lvt_7_1_);
                }
                
                public void onFailure(final Throwable t) {
                    ChunkRenderWorker.access$0(this.this$0, this.val$generator);
                    if (!(t instanceof CancellationException) && !(t instanceof InterruptedException)) {
                        Minecraft.getMinecraft().crashed(CrashReport.makeCrashReport(t, ChunkRenderWorker$2.I["  ".length()]));
                    }
                }
            });
        }
    }
    
    static Logger access$1() {
        return ChunkRenderWorker.LOGGER;
    }
    
    private void freeRenderBuilder(final ChunkCompileTaskGenerator chunkCompileTaskGenerator) {
        if (this.regionRenderCacheBuilder == null) {
            this.chunkRenderDispatcher.freeRenderBuilder(chunkCompileTaskGenerator.getRegionRenderCacheBuilder());
        }
    }
    
    public ChunkRenderWorker(final ChunkRenderDispatcher chunkRenderDispatcher, final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.chunkRenderDispatcher = chunkRenderDispatcher;
        this.regionRenderCacheBuilder = regionRenderCacheBuilder;
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
}
