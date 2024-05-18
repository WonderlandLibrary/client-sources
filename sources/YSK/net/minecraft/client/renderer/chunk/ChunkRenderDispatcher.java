package net.minecraft.client.renderer.chunk;

import java.util.concurrent.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.client.*;
import com.google.common.collect.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import net.minecraft.client.renderer.vertex.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;

public class ChunkRenderDispatcher
{
    private final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates;
    private final Queue<ListenableFutureTask<?>> queueChunkUploads;
    private final VertexBufferUploader vertexUploader;
    private static final Logger logger;
    private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders;
    private final WorldVertexBufferUploader worldVertexUploader;
    private static final ThreadFactory threadFactory;
    private final ChunkRenderWorker renderWorker;
    private final List<ChunkRenderWorker> listThreadedWorkers;
    private static final String[] I;
    
    public void stopChunkUpdates() {
        this.clearChunkUpdates();
        while (this.runChunkUploads(0L)) {}
        final ArrayList arrayList = Lists.newArrayList();
        "".length();
        if (2 == 0) {
            throw null;
        }
        while (arrayList.size() != (0x65 ^ 0x60)) {
            try {
                arrayList.add(this.allocateRenderBuilder());
                "".length();
                if (3 < 1) {
                    throw null;
                }
                continue;
            }
            catch (InterruptedException ex) {}
        }
        this.queueFreeRenderBuilders.addAll((Collection<?>)arrayList);
    }
    
    public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
        return this.queueFreeRenderBuilders.take();
    }
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("\u00132\u001c7/p\u0018\b-'8?\u001bya4", "PZiYD");
        ChunkRenderDispatcher.I[" ".length()] = I("$6uV]dF+ZX$ uV]e\u0011cV\u0019\u0016OoSI0", "TuOvx");
    }
    
    public ListenableFuture<Object> uploadChunk(final EnumWorldBlockLayer enumWorldBlockLayer, final WorldRenderer worldRenderer, final RenderChunk renderChunk, final CompiledChunk compiledChunk) {
        if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
            if (OpenGlHelper.useVbo()) {
                this.uploadVertexBuffer(worldRenderer, renderChunk.getVertexBufferByLayer(enumWorldBlockLayer.ordinal()));
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                this.uploadDisplayList(worldRenderer, ((ListedRenderChunk)renderChunk).getDisplayList(enumWorldBlockLayer, compiledChunk), renderChunk);
            }
            worldRenderer.setTranslation(0.0, 0.0, 0.0);
            return (ListenableFuture<Object>)Futures.immediateFuture((Object)null);
        }
        final ListenableFutureTask create = ListenableFutureTask.create((Runnable)new Runnable(this, enumWorldBlockLayer, worldRenderer, renderChunk, compiledChunk) {
            private final CompiledChunk val$compiledChunkIn;
            private final EnumWorldBlockLayer val$player;
            private final WorldRenderer val$p_178503_2_;
            private final RenderChunk val$chunkRenderer;
            final ChunkRenderDispatcher this$0;
            
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
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public void run() {
                this.this$0.uploadChunk(this.val$player, this.val$p_178503_2_, this.val$chunkRenderer, this.val$compiledChunkIn);
            }
        }, (Object)null);
        synchronized (this.queueChunkUploads) {
            this.queueChunkUploads.add((ListenableFutureTask<?>)create);
            final ListenableFutureTask<?> listenableFutureTask = (ListenableFutureTask<?>)create;
            // monitorexit(this.queueChunkUploads)
            return (ListenableFuture<Object>)listenableFutureTask;
        }
    }
    
    public ChunkRenderDispatcher() {
        this.listThreadedWorkers = (List<ChunkRenderWorker>)Lists.newArrayList();
        this.queueChunkUpdates = (BlockingQueue<ChunkCompileTaskGenerator>)Queues.newArrayBlockingQueue(0x1A ^ 0x7E);
        this.queueFreeRenderBuilders = (BlockingQueue<RegionRenderCacheBuilder>)Queues.newArrayBlockingQueue(0x4E ^ 0x4B);
        this.worldVertexUploader = new WorldVertexBufferUploader();
        this.vertexUploader = new VertexBufferUploader();
        this.queueChunkUploads = (Queue<ListenableFutureTask<?>>)Queues.newArrayDeque();
        int i = "".length();
        "".length();
        if (-1 >= 1) {
            throw null;
        }
        while (i < "  ".length()) {
            final ChunkRenderWorker chunkRenderWorker = new ChunkRenderWorker(this);
            ChunkRenderDispatcher.threadFactory.newThread(chunkRenderWorker).start();
            this.listThreadedWorkers.add(chunkRenderWorker);
            ++i;
        }
        int j = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (j < (0x42 ^ 0x47)) {
            this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
            ++j;
        }
        this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
    }
    
    public void freeRenderBuilder(final RegionRenderCacheBuilder regionRenderCacheBuilder) {
        this.queueFreeRenderBuilders.add(regionRenderCacheBuilder);
    }
    
    static BlockingQueue access$0(final ChunkRenderDispatcher chunkRenderDispatcher) {
        return chunkRenderDispatcher.queueChunkUpdates;
    }
    
    public boolean updateChunkNow(final RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();
        int length;
        try {
            final ChunkCompileTaskGenerator compileTaskChunk = renderChunk.makeCompileTaskChunk();
            try {
                this.renderWorker.processTask(compileTaskChunk);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
            }
            catch (InterruptedException ex) {}
            length = " ".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        finally {
            renderChunk.getLockCompileTask().unlock();
        }
        renderChunk.getLockCompileTask().unlock();
        return length != 0;
    }
    
    public boolean updateTransparencyLater(final RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();
        int n;
        try {
            final ChunkCompileTaskGenerator compileTaskTransparency = renderChunk.makeCompileTaskTransparency();
            if (compileTaskTransparency == null) {
                n = " ".length();
                return n != 0;
            }
            compileTaskTransparency.addFinishRunnable(new Runnable(this, compileTaskTransparency) {
                final ChunkRenderDispatcher this$0;
                private final ChunkCompileTaskGenerator val$chunkcompiletaskgenerator;
                
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
                        if (false) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
                
                @Override
                public void run() {
                    ChunkRenderDispatcher.access$0(this.this$0).remove(this.val$chunkcompiletaskgenerator);
                }
            });
            n = (this.queueChunkUpdates.offer(compileTaskTransparency) ? 1 : 0);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        finally {
            renderChunk.getLockCompileTask().unlock();
        }
        renderChunk.getLockCompileTask().unlock();
        return n != 0;
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
            if (3 >= 4) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        logger = LogManager.getLogger();
        threadFactory = new ThreadFactoryBuilder().setNameFormat(ChunkRenderDispatcher.I["".length()]).setDaemon((boolean)(" ".length() != 0)).build();
    }
    
    public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
        return this.queueChunkUpdates.take();
    }
    
    private void uploadVertexBuffer(final WorldRenderer worldRenderer, final VertexBuffer vertexBuffer) {
        this.vertexUploader.setVertexBuffer(vertexBuffer);
        this.vertexUploader.func_181679_a(worldRenderer);
    }
    
    public boolean updateChunkLater(final RenderChunk renderChunk) {
        renderChunk.getLockCompileTask().lock();
        boolean b;
        try {
            final ChunkCompileTaskGenerator compileTaskChunk = renderChunk.makeCompileTaskChunk();
            compileTaskChunk.addFinishRunnable(new Runnable(this, compileTaskChunk) {
                private final ChunkCompileTaskGenerator val$chunkcompiletaskgenerator;
                final ChunkRenderDispatcher this$0;
                
                @Override
                public void run() {
                    ChunkRenderDispatcher.access$0(this.this$0).remove(this.val$chunkcompiletaskgenerator);
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
                        if (2 <= -1) {
                            throw null;
                        }
                    }
                    return sb.toString();
                }
            });
            final boolean offer = this.queueChunkUpdates.offer(compileTaskChunk);
            if (!offer) {
                compileTaskChunk.finish();
            }
            b = offer;
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        finally {
            renderChunk.getLockCompileTask().unlock();
        }
        renderChunk.getLockCompileTask().unlock();
        return b;
    }
    
    public void clearChunkUpdates() {
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (!this.queueChunkUpdates.isEmpty()) {
            final ChunkCompileTaskGenerator chunkCompileTaskGenerator = this.queueChunkUpdates.poll();
            if (chunkCompileTaskGenerator != null) {
                chunkCompileTaskGenerator.finish();
            }
        }
    }
    
    private void uploadDisplayList(final WorldRenderer worldRenderer, final int n, final RenderChunk renderChunk) {
        GL11.glNewList(n, 2942 + 3333 - 1415 + 4);
        GlStateManager.pushMatrix();
        renderChunk.multModelviewMatrix();
        this.worldVertexUploader.func_181679_a(worldRenderer);
        GlStateManager.popMatrix();
        GL11.glEndList();
    }
    
    public boolean runChunkUploads(final long n) {
        int n2 = "".length();
        do {
            int n3 = "".length();
            synchronized (this.queueChunkUploads) {
                if (!this.queueChunkUploads.isEmpty()) {
                    this.queueChunkUploads.poll().run();
                    n3 = " ".length();
                    n2 = " ".length();
                }
                // monitorexit(this.queueChunkUploads)
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            if (n == 0L) {
                break;
            }
            if (n3 != 0) {
                continue;
            }
            "".length();
            if (false) {
                throw null;
            }
            break;
        } while (n - System.nanoTime() >= 0L);
        return n2 != 0;
    }
    
    public String getDebugInfo() {
        final String s = ChunkRenderDispatcher.I[" ".length()];
        final Object[] array = new Object["   ".length()];
        array["".length()] = this.queueChunkUpdates.size();
        array[" ".length()] = this.queueChunkUploads.size();
        array["  ".length()] = this.queueFreeRenderBuilders.size();
        return String.format(s, array);
    }
}
