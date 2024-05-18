/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import com.google.common.collect.Queues;
/*     */ import com.google.common.util.concurrent.Futures;
/*     */ import com.google.common.util.concurrent.ListenableFuture;
/*     */ import com.google.common.util.concurrent.ListenableFutureTask;
/*     */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*     */ import java.util.List;
/*     */ import java.util.Queue;
/*     */ import java.util.concurrent.BlockingQueue;
/*     */ import java.util.concurrent.ThreadFactory;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ import net.minecraft.client.renderer.VertexBufferUploader;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.WorldVertexBufferUploader;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ import org.lwjgl.opengl.GL11;
/*     */ 
/*     */ public class ChunkRenderDispatcher
/*     */ {
/*  28 */   private static final Logger logger = LogManager.getLogger();
/*  29 */   private static final ThreadFactory threadFactory = (new ThreadFactoryBuilder()).setNameFormat("Chunk Batcher %d").setDaemon(true).build();
/*  30 */   private final List<ChunkRenderWorker> listThreadedWorkers = Lists.newArrayList();
/*  31 */   private final BlockingQueue<ChunkCompileTaskGenerator> queueChunkUpdates = Queues.newArrayBlockingQueue(100);
/*  32 */   private final BlockingQueue<RegionRenderCacheBuilder> queueFreeRenderBuilders = Queues.newArrayBlockingQueue(5);
/*  33 */   private final WorldVertexBufferUploader worldVertexUploader = new WorldVertexBufferUploader();
/*  34 */   private final VertexBufferUploader vertexUploader = new VertexBufferUploader();
/*  35 */   private final Queue<ListenableFutureTask<?>> queueChunkUploads = Queues.newArrayDeque();
/*     */   
/*     */   private final ChunkRenderWorker renderWorker;
/*     */   
/*     */   public ChunkRenderDispatcher() {
/*  40 */     for (int i = 0; i < 2; i++) {
/*     */       
/*  42 */       ChunkRenderWorker chunkrenderworker = new ChunkRenderWorker(this);
/*  43 */       Thread thread = threadFactory.newThread(chunkrenderworker);
/*  44 */       thread.start();
/*  45 */       this.listThreadedWorkers.add(chunkrenderworker);
/*     */     } 
/*     */     
/*  48 */     for (int j = 0; j < 5; j++)
/*     */     {
/*  50 */       this.queueFreeRenderBuilders.add(new RegionRenderCacheBuilder());
/*     */     }
/*     */     
/*  53 */     this.renderWorker = new ChunkRenderWorker(this, new RegionRenderCacheBuilder());
/*     */   }
/*     */ 
/*     */   
/*     */   public String getDebugInfo() {
/*  58 */     return String.format("pC: %03d, pU: %1d, aB: %1d", new Object[] { Integer.valueOf(this.queueChunkUpdates.size()), Integer.valueOf(this.queueChunkUploads.size()), Integer.valueOf(this.queueFreeRenderBuilders.size()) });
/*     */   }
/*     */   
/*     */   public boolean runChunkUploads(long p_178516_1_) {
/*     */     long i;
/*  63 */     boolean flag = false;
/*     */ 
/*     */     
/*     */     do {
/*  67 */       boolean flag1 = false;
/*     */       
/*  69 */       synchronized (this.queueChunkUploads) {
/*     */         
/*  71 */         if (!this.queueChunkUploads.isEmpty()) {
/*     */           
/*  73 */           ((ListenableFutureTask)this.queueChunkUploads.poll()).run();
/*  74 */           flag1 = true;
/*  75 */           flag = true;
/*     */         } 
/*     */       } 
/*     */       
/*  79 */       if (p_178516_1_ == 0L || !flag1) {
/*     */         break;
/*     */       }
/*     */ 
/*     */       
/*  84 */       i = p_178516_1_ - System.nanoTime();
/*     */     }
/*  86 */     while (i >= 0L);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  92 */     return flag;
/*     */   }
/*     */   
/*     */   public boolean updateChunkLater(RenderChunk chunkRenderer) {
/*     */     boolean flag1;
/*  97 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 102 */       final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/* 103 */       chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 107 */               ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */             }
/*     */           });
/* 110 */       boolean flag = this.queueChunkUpdates.offer(chunkcompiletaskgenerator);
/*     */       
/* 112 */       if (!flag)
/*     */       {
/* 114 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */       
/* 117 */       flag1 = flag;
/*     */     }
/*     */     finally {
/*     */       
/* 121 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 124 */     return flag1;
/*     */   }
/*     */   
/*     */   public boolean updateChunkNow(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 129 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 134 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskChunk();
/*     */ 
/*     */       
/*     */       try {
/* 138 */         this.renderWorker.processTask(chunkcompiletaskgenerator);
/*     */       }
/* 140 */       catch (InterruptedException interruptedException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 145 */       flag = true;
/*     */     }
/*     */     finally {
/*     */       
/* 149 */       chunkRenderer.getLockCompileTask().unlock();
/*     */     } 
/*     */     
/* 152 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopChunkUpdates() {
/* 157 */     clearChunkUpdates(); do {
/*     */     
/* 159 */     } while (runChunkUploads(0L));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 164 */     List<RegionRenderCacheBuilder> list = Lists.newArrayList();
/*     */     
/* 166 */     while (list.size() != 5) {
/*     */ 
/*     */       
/*     */       try {
/* 170 */         list.add(allocateRenderBuilder());
/*     */       }
/* 172 */       catch (InterruptedException interruptedException) {}
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 178 */     this.queueFreeRenderBuilders.addAll(list);
/*     */   }
/*     */ 
/*     */   
/*     */   public void freeRenderBuilder(RegionRenderCacheBuilder p_178512_1_) {
/* 183 */     this.queueFreeRenderBuilders.add(p_178512_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder allocateRenderBuilder() throws InterruptedException {
/* 188 */     return this.queueFreeRenderBuilders.take();
/*     */   }
/*     */ 
/*     */   
/*     */   public ChunkCompileTaskGenerator getNextChunkUpdate() throws InterruptedException {
/* 193 */     return this.queueChunkUpdates.take();
/*     */   }
/*     */   
/*     */   public boolean updateTransparencyLater(RenderChunk chunkRenderer) {
/*     */     boolean flag;
/* 198 */     chunkRenderer.getLockCompileTask().lock();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 203 */     try { final ChunkCompileTaskGenerator chunkcompiletaskgenerator = chunkRenderer.makeCompileTaskTransparency();
/*     */       
/* 205 */       if (chunkcompiletaskgenerator == null) {
/*     */         
/* 207 */         boolean bool = true;
/* 208 */         return bool;
/*     */       } 
/*     */       
/* 211 */       chunkcompiletaskgenerator.addFinishRunnable(new Runnable()
/*     */           {
/*     */             public void run()
/*     */             {
/* 215 */               ChunkRenderDispatcher.this.queueChunkUpdates.remove(chunkcompiletaskgenerator);
/*     */             }
/*     */           });
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 222 */     { chunkRenderer.getLockCompileTask().unlock(); }  chunkRenderer.getLockCompileTask().unlock();
/*     */ 
/*     */     
/* 225 */     return flag;
/*     */   }
/*     */ 
/*     */   
/*     */   public ListenableFuture<Object> uploadChunk(final EnumWorldBlockLayer player, final WorldRenderer p_178503_2_, final RenderChunk chunkRenderer, final CompiledChunk compiledChunkIn) {
/* 230 */     if (Minecraft.getMinecraft().isCallingFromMinecraftThread()) {
/*     */       
/* 232 */       if (OpenGlHelper.useVbo()) {
/*     */         
/* 234 */         uploadVertexBuffer(p_178503_2_, chunkRenderer.getVertexBufferByLayer(player.ordinal()));
/*     */       }
/*     */       else {
/*     */         
/* 238 */         uploadDisplayList(p_178503_2_, ((ListedRenderChunk)chunkRenderer).getDisplayList(player, compiledChunkIn), chunkRenderer);
/*     */       } 
/*     */       
/* 241 */       p_178503_2_.setTranslation(0.0D, 0.0D, 0.0D);
/* 242 */       return Futures.immediateFuture(null);
/*     */     } 
/*     */ 
/*     */     
/* 246 */     ListenableFutureTask<Object> listenablefuturetask = ListenableFutureTask.create(new Runnable()
/*     */         {
/*     */           public void run()
/*     */           {
/* 250 */             ChunkRenderDispatcher.this.uploadChunk(player, p_178503_2_, chunkRenderer, compiledChunkIn);
/*     */           }
/* 252 */         }null);
/*     */     
/* 254 */     synchronized (this.queueChunkUploads) {
/*     */       
/* 256 */       this.queueChunkUploads.add(listenablefuturetask);
/* 257 */       return (ListenableFuture<Object>)listenablefuturetask;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void uploadDisplayList(WorldRenderer p_178510_1_, int p_178510_2_, RenderChunk chunkRenderer) {
/* 264 */     GL11.glNewList(p_178510_2_, 4864);
/* 265 */     GlStateManager.pushMatrix();
/* 266 */     chunkRenderer.multModelviewMatrix();
/* 267 */     this.worldVertexUploader.func_181679_a(p_178510_1_);
/* 268 */     GlStateManager.popMatrix();
/* 269 */     GL11.glEndList();
/*     */   }
/*     */ 
/*     */   
/*     */   private void uploadVertexBuffer(WorldRenderer p_178506_1_, VertexBuffer vertexBufferIn) {
/* 274 */     this.vertexUploader.setVertexBuffer(vertexBufferIn);
/* 275 */     this.vertexUploader.func_181679_a(p_178506_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public void clearChunkUpdates() {
/* 280 */     while (!this.queueChunkUpdates.isEmpty()) {
/*     */       
/* 282 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.queueChunkUpdates.poll();
/*     */       
/* 284 */       if (chunkcompiletaskgenerator != null)
/*     */       {
/* 286 */         chunkcompiletaskgenerator.finish();
/*     */       }
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\chunk\ChunkRenderDispatcher.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */