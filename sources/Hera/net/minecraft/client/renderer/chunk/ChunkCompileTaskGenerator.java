/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Lists;
/*     */ import java.util.List;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.client.renderer.RegionRenderCacheBuilder;
/*     */ 
/*     */ public class ChunkCompileTaskGenerator
/*     */ {
/*     */   private final RenderChunk renderChunk;
/*  11 */   private final ReentrantLock lock = new ReentrantLock();
/*  12 */   private final List<Runnable> listFinishRunnables = Lists.newArrayList();
/*     */   private final Type type;
/*     */   private RegionRenderCacheBuilder regionRenderCacheBuilder;
/*     */   private CompiledChunk compiledChunk;
/*  16 */   private Status status = Status.PENDING;
/*     */   
/*     */   private boolean finished;
/*     */   
/*     */   public ChunkCompileTaskGenerator(RenderChunk renderChunkIn, Type typeIn) {
/*  21 */     this.renderChunk = renderChunkIn;
/*  22 */     this.type = typeIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public Status getStatus() {
/*  27 */     return this.status;
/*     */   }
/*     */ 
/*     */   
/*     */   public RenderChunk getRenderChunk() {
/*  32 */     return this.renderChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/*  37 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/*  42 */     this.compiledChunk = compiledChunkIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public RegionRenderCacheBuilder getRegionRenderCacheBuilder() {
/*  47 */     return this.regionRenderCacheBuilder;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setRegionRenderCacheBuilder(RegionRenderCacheBuilder regionRenderCacheBuilderIn) {
/*  52 */     this.regionRenderCacheBuilder = regionRenderCacheBuilderIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setStatus(Status statusIn) {
/*  57 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/*  61 */       this.status = statusIn;
/*     */     }
/*     */     finally {
/*     */       
/*  65 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void finish() {
/*  71 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/*  75 */       if (this.type == Type.REBUILD_CHUNK && this.status != Status.DONE)
/*     */       {
/*  77 */         this.renderChunk.setNeedsUpdate(true);
/*     */       }
/*     */       
/*  80 */       this.finished = true;
/*  81 */       this.status = Status.DONE;
/*     */       
/*  83 */       for (Runnable runnable : this.listFinishRunnables)
/*     */       {
/*  85 */         runnable.run();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/*  90 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addFinishRunnable(Runnable p_178539_1_) {
/*  96 */     this.lock.lock();
/*     */ 
/*     */     
/*     */     try {
/* 100 */       this.listFinishRunnables.add(p_178539_1_);
/*     */       
/* 102 */       if (this.finished)
/*     */       {
/* 104 */         p_178539_1_.run();
/*     */       }
/*     */     }
/*     */     finally {
/*     */       
/* 109 */       this.lock.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLock() {
/* 115 */     return this.lock;
/*     */   }
/*     */ 
/*     */   
/*     */   public Type getType() {
/* 120 */     return this.type;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isFinished() {
/* 125 */     return this.finished;
/*     */   }
/*     */   
/*     */   public enum Status
/*     */   {
/* 130 */     PENDING,
/* 131 */     COMPILING,
/* 132 */     UPLOADING,
/* 133 */     DONE;
/*     */   }
/*     */   
/*     */   public enum Type
/*     */   {
/* 138 */     REBUILD_CHUNK,
/* 139 */     RESORT_TRANSPARENCY;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\chunk\ChunkCompileTaskGenerator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */