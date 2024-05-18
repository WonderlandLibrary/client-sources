/*     */ package net.minecraft.client.renderer.chunk;
/*     */ 
/*     */ import com.google.common.collect.Sets;
/*     */ import java.nio.FloatBuffer;
/*     */ import java.util.EnumMap;
/*     */ import java.util.HashSet;
/*     */ import java.util.Iterator;
/*     */ import java.util.Set;
/*     */ import java.util.concurrent.locks.ReentrantLock;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.block.state.IBlockState;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.BlockRendererDispatcher;
/*     */ import net.minecraft.client.renderer.GLAllocation;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.OpenGlHelper;
/*     */ import net.minecraft.client.renderer.RegionRenderCache;
/*     */ import net.minecraft.client.renderer.RenderGlobal;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
/*     */ import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.client.renderer.vertex.VertexBuffer;
/*     */ import net.minecraft.tileentity.TileEntity;
/*     */ import net.minecraft.util.AxisAlignedBB;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.EnumWorldBlockLayer;
/*     */ import net.minecraft.util.Vec3i;
/*     */ import net.minecraft.world.IBlockAccess;
/*     */ import net.minecraft.world.World;
/*     */ import optfine.BlockPosM;
/*     */ import optfine.Reflector;
/*     */ 
/*     */ 
/*     */ public class RenderChunk
/*     */ {
/*     */   private World world;
/*     */   private final RenderGlobal renderGlobal;
/*     */   public static int renderChunksUpdated;
/*     */   private BlockPos position;
/*  42 */   public CompiledChunk compiledChunk = CompiledChunk.DUMMY;
/*  43 */   private final ReentrantLock lockCompileTask = new ReentrantLock();
/*  44 */   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
/*  45 */   private ChunkCompileTaskGenerator compileTask = null;
/*  46 */   private final Set field_181056_j = Sets.newHashSet();
/*     */   private final int index;
/*  48 */   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
/*  49 */   private final VertexBuffer[] vertexBuffers = new VertexBuffer[(EnumWorldBlockLayer.values()).length];
/*     */   public AxisAlignedBB boundingBox;
/*  51 */   private int frameIndex = -1;
/*     */   private boolean needsUpdate = true;
/*     */   private EnumMap field_181702_p;
/*     */   private static final String __OBFID = "CL_00002452";
/*  55 */   private BlockPos[] positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
/*  56 */   private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
/*  57 */   private EnumWorldBlockLayer[] blockLayersSingle = new EnumWorldBlockLayer[1];
/*     */ 
/*     */   
/*     */   public RenderChunk(World worldIn, RenderGlobal renderGlobalIn, BlockPos blockPosIn, int indexIn) {
/*  61 */     this.world = worldIn;
/*  62 */     this.renderGlobal = renderGlobalIn;
/*  63 */     this.index = indexIn;
/*     */     
/*  65 */     if (!blockPosIn.equals(getPosition()))
/*     */     {
/*  67 */       setPosition(blockPosIn);
/*     */     }
/*     */     
/*  70 */     if (OpenGlHelper.useVbo())
/*     */     {
/*  72 */       for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++)
/*     */       {
/*  74 */         this.vertexBuffers[i] = new VertexBuffer(DefaultVertexFormats.BLOCK);
/*     */       }
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean setFrameIndex(int frameIndexIn) {
/*  81 */     if (this.frameIndex == frameIndexIn)
/*     */     {
/*  83 */       return false;
/*     */     }
/*     */ 
/*     */     
/*  87 */     this.frameIndex = frameIndexIn;
/*  88 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public VertexBuffer getVertexBufferByLayer(int layer) {
/*  94 */     return this.vertexBuffers[layer];
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPosition(BlockPos pos) {
/*  99 */     stopCompileTask();
/* 100 */     this.position = pos;
/* 101 */     this.boundingBox = new AxisAlignedBB(pos, pos.add(16, 16, 16));
/* 102 */     EnumFacing[] aenumfacing = EnumFacing.values();
/* 103 */     int i = aenumfacing.length;
/* 104 */     initModelviewMatrix();
/*     */     
/* 106 */     for (int j = 0; j < this.positionOffsets16.length; j++)
/*     */     {
/* 108 */       this.positionOffsets16[j] = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void resortTransparency(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/* 114 */     CompiledChunk compiledchunk = generator.getCompiledChunk();
/*     */     
/* 116 */     if (compiledchunk.getState() != null && !compiledchunk.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
/*     */       
/* 118 */       WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
/* 119 */       preRenderBlocks(worldrenderer, this.position);
/* 120 */       worldrenderer.setVertexState(compiledchunk.getState());
/* 121 */       postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, x, y, z, worldrenderer, compiledchunk);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void rebuildChunk(float x, float y, float z, ChunkCompileTaskGenerator generator) {
/*     */     RegionRenderCache regionrendercache;
/* 127 */     CompiledChunk compiledchunk = new CompiledChunk();
/* 128 */     boolean flag = true;
/* 129 */     BlockPos blockpos = this.position;
/* 130 */     BlockPos blockpos1 = blockpos.add(15, 15, 15);
/* 131 */     generator.getLock().lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 136 */       if (generator.getStatus() != ChunkCompileTaskGenerator.Status.COMPILING) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 141 */       if (this.world == null) {
/*     */         return;
/*     */       }
/*     */ 
/*     */       
/* 146 */       regionrendercache = new RegionRenderCache(this.world, blockpos.add(-1, -1, -1), blockpos1.add(1, 1, 1), 1);
/* 147 */       generator.setCompiledChunk(compiledchunk);
/*     */     }
/*     */     finally {
/*     */       
/* 151 */       generator.getLock().unlock();
/*     */     } 
/*     */     
/* 154 */     VisGraph var10 = new VisGraph();
/* 155 */     HashSet<TileEntity> var11 = Sets.newHashSet();
/*     */     
/* 157 */     if (!regionrendercache.extendedLevelsInChunkCache()) {
/*     */       
/* 159 */       renderChunksUpdated++;
/* 160 */       boolean[] aboolean = new boolean[(EnumWorldBlockLayer.values()).length];
/* 161 */       BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
/* 162 */       Iterator<BlockPosM> iterator = BlockPosM.getAllInBoxMutable(blockpos, blockpos1).iterator();
/* 163 */       boolean flag1 = Reflector.ForgeBlock_hasTileEntity.exists();
/* 164 */       boolean flag2 = Reflector.ForgeBlock_canRenderInLayer.exists();
/* 165 */       boolean flag3 = Reflector.ForgeHooksClient_setRenderLayer.exists();
/*     */       
/* 167 */       while (iterator.hasNext()) {
/*     */         boolean flag4; EnumWorldBlockLayer[] aenumworldblocklayer;
/* 169 */         BlockPosM blockposm = iterator.next();
/* 170 */         IBlockState iblockstate = regionrendercache.getBlockState((BlockPos)blockposm);
/* 171 */         Block block = iblockstate.getBlock();
/*     */         
/* 173 */         if (block.isOpaqueCube())
/*     */         {
/* 175 */           var10.func_178606_a((BlockPos)blockposm);
/*     */         }
/*     */ 
/*     */ 
/*     */         
/* 180 */         if (flag1) {
/*     */           
/* 182 */           flag4 = Reflector.callBoolean(iterator, Reflector.ForgeBlock_hasTileEntity, new Object[] { blockrendererdispatcher });
/*     */         }
/*     */         else {
/*     */           
/* 186 */           flag4 = block.hasTileEntity();
/*     */         } 
/*     */         
/* 189 */         if (flag4) {
/*     */           
/* 191 */           TileEntity tileentity = regionrendercache.getTileEntity(new BlockPos((Vec3i)blockposm));
/* 192 */           TileEntitySpecialRenderer tileentityspecialrenderer = TileEntityRendererDispatcher.instance.getSpecialRenderer(tileentity);
/*     */           
/* 194 */           if (tileentity != null && tileentityspecialrenderer != null) {
/*     */             
/* 196 */             compiledchunk.addTileEntity(tileentity);
/*     */             
/* 198 */             if (tileentityspecialrenderer.func_181055_a())
/*     */             {
/* 200 */               var11.add(tileentity);
/*     */             }
/*     */           } 
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 207 */         if (flag2) {
/*     */           
/* 209 */           aenumworldblocklayer = ENUM_WORLD_BLOCK_LAYERS;
/*     */         }
/*     */         else {
/*     */           
/* 213 */           aenumworldblocklayer = this.blockLayersSingle;
/* 214 */           aenumworldblocklayer[0] = block.getBlockLayer();
/*     */         } 
/*     */         
/* 217 */         for (int j = 0; j < aenumworldblocklayer.length; j++) {
/*     */           
/* 219 */           EnumWorldBlockLayer enumworldblocklayer = aenumworldblocklayer[j];
/*     */           
/* 221 */           if (flag2) {
/*     */             
/* 223 */             boolean flag5 = Reflector.callBoolean(block, Reflector.ForgeBlock_canRenderInLayer, new Object[] { enumworldblocklayer });
/*     */             
/* 225 */             if (!flag5) {
/*     */               continue;
/*     */             }
/*     */           } 
/*     */ 
/*     */           
/* 231 */           enumworldblocklayer = fixBlockLayer(block, enumworldblocklayer);
/*     */           
/* 233 */           if (flag3)
/*     */           {
/* 235 */             Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, new Object[] { enumworldblocklayer });
/*     */           }
/*     */           
/* 238 */           int k = enumworldblocklayer.ordinal();
/*     */           
/* 240 */           if (block.getRenderType() != -1) {
/*     */             
/* 242 */             WorldRenderer worldrenderer = generator.getRegionRenderCacheBuilder().getWorldRendererByLayerId(k);
/* 243 */             worldrenderer.setBlockLayer(enumworldblocklayer);
/*     */             
/* 245 */             if (!compiledchunk.isLayerStarted(enumworldblocklayer)) {
/*     */               
/* 247 */               compiledchunk.setLayerStarted(enumworldblocklayer);
/* 248 */               preRenderBlocks(worldrenderer, blockpos);
/*     */             } 
/*     */             
/* 251 */             aboolean[k] = aboolean[k] | blockrendererdispatcher.renderBlock(iblockstate, (BlockPos)blockposm, (IBlockAccess)regionrendercache, worldrenderer);
/*     */           }  continue;
/*     */         } 
/*     */       }  byte b;
/*     */       int i;
/*     */       EnumWorldBlockLayer[] arrayOfEnumWorldBlockLayer;
/* 257 */       for (i = (arrayOfEnumWorldBlockLayer = EnumWorldBlockLayer.values()).length, b = 0; b < i; ) { EnumWorldBlockLayer enumworldblocklayer1 = arrayOfEnumWorldBlockLayer[b];
/*     */         
/* 259 */         if (aboolean[enumworldblocklayer1.ordinal()])
/*     */         {
/* 261 */           compiledchunk.setLayerUsed(enumworldblocklayer1);
/*     */         }
/*     */         
/* 264 */         if (compiledchunk.isLayerStarted(enumworldblocklayer1))
/*     */         {
/* 266 */           postRenderBlocks(enumworldblocklayer1, x, y, z, generator.getRegionRenderCacheBuilder().getWorldRendererByLayer(enumworldblocklayer1), compiledchunk);
/*     */         }
/*     */         b++; }
/*     */     
/*     */     } 
/* 271 */     compiledchunk.setVisibility(var10.computeVisibility());
/* 272 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 276 */       HashSet hashset1 = Sets.newHashSet(var11);
/* 277 */       HashSet hashset2 = Sets.newHashSet(this.field_181056_j);
/* 278 */       hashset1.removeAll(this.field_181056_j);
/* 279 */       hashset2.removeAll(var11);
/* 280 */       this.field_181056_j.clear();
/* 281 */       this.field_181056_j.addAll(var11);
/* 282 */       this.renderGlobal.func_181023_a(hashset2, hashset1);
/*     */     }
/*     */     finally {
/*     */       
/* 286 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void finishCompileTask() {
/* 292 */     this.lockCompileTask.lock();
/*     */ 
/*     */     
/*     */     try {
/* 296 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE)
/*     */       {
/* 298 */         this.compileTask.finish();
/* 299 */         this.compileTask = null;
/*     */       }
/*     */     
/*     */     } finally {
/*     */       
/* 304 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public ReentrantLock getLockCompileTask() {
/* 310 */     return this.lockCompileTask;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator;
/* 315 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */     
/*     */     try {
/* 320 */       finishCompileTask();
/* 321 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
/* 322 */       chunkcompiletaskgenerator = this.compileTask;
/*     */     }
/*     */     finally {
/*     */       
/* 326 */       this.lockCompileTask.unlock();
/*     */     } 
/*     */     
/* 329 */     return chunkcompiletaskgenerator;
/*     */   }
/*     */   
/*     */   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
/*     */     ChunkCompileTaskGenerator chunkcompiletaskgenerator1;
/* 334 */     this.lockCompileTask.lock();
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 339 */     try { if (this.compileTask != null && this.compileTask.getStatus() == ChunkCompileTaskGenerator.Status.PENDING) {
/*     */         
/* 341 */         ChunkCompileTaskGenerator chunkcompiletaskgenerator2 = null;
/* 342 */         return chunkcompiletaskgenerator2;
/*     */       } 
/*     */       
/* 345 */       if (this.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator.Status.DONE) {
/*     */         
/* 347 */         this.compileTask.finish();
/* 348 */         this.compileTask = null;
/*     */       } 
/*     */       
/* 351 */       this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
/* 352 */       this.compileTask.setCompiledChunk(this.compiledChunk);
/* 353 */       ChunkCompileTaskGenerator chunkcompiletaskgenerator = this.compileTask;
/*     */        }
/*     */     
/*     */     finally
/*     */     
/* 358 */     { this.lockCompileTask.unlock(); }  this.lockCompileTask.unlock();
/*     */ 
/*     */     
/* 361 */     return chunkcompiletaskgenerator1;
/*     */   }
/*     */ 
/*     */   
/*     */   private void preRenderBlocks(WorldRenderer worldRendererIn, BlockPos pos) {
/* 366 */     worldRendererIn.begin(7, DefaultVertexFormats.BLOCK);
/* 367 */     worldRendererIn.setTranslation(-pos.getX(), -pos.getY(), -pos.getZ());
/*     */   }
/*     */ 
/*     */   
/*     */   private void postRenderBlocks(EnumWorldBlockLayer layer, float x, float y, float z, WorldRenderer worldRendererIn, CompiledChunk compiledChunkIn) {
/* 372 */     if (layer == EnumWorldBlockLayer.TRANSLUCENT && !compiledChunkIn.isLayerEmpty(layer)) {
/*     */       
/* 374 */       worldRendererIn.func_181674_a(x, y, z);
/* 375 */       compiledChunkIn.setState(worldRendererIn.func_181672_a());
/*     */     } 
/*     */     
/* 378 */     worldRendererIn.finishDrawing();
/*     */   }
/*     */ 
/*     */   
/*     */   private void initModelviewMatrix() {
/* 383 */     GlStateManager.pushMatrix();
/* 384 */     GlStateManager.loadIdentity();
/* 385 */     float f = 1.000001F;
/* 386 */     GlStateManager.translate(-8.0F, -8.0F, -8.0F);
/* 387 */     GlStateManager.scale(f, f, f);
/* 388 */     GlStateManager.translate(8.0F, 8.0F, 8.0F);
/* 389 */     GlStateManager.getFloat(2982, this.modelviewMatrix);
/* 390 */     GlStateManager.popMatrix();
/*     */   }
/*     */ 
/*     */   
/*     */   public void multModelviewMatrix() {
/* 395 */     GlStateManager.multMatrix(this.modelviewMatrix);
/*     */   }
/*     */ 
/*     */   
/*     */   public CompiledChunk getCompiledChunk() {
/* 400 */     return this.compiledChunk;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCompiledChunk(CompiledChunk compiledChunkIn) {
/* 405 */     this.lockCompiledChunk.lock();
/*     */ 
/*     */     
/*     */     try {
/* 409 */       this.compiledChunk = compiledChunkIn;
/*     */     }
/*     */     finally {
/*     */       
/* 413 */       this.lockCompiledChunk.unlock();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void stopCompileTask() {
/* 419 */     finishCompileTask();
/* 420 */     this.compiledChunk = CompiledChunk.DUMMY;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteGlResources() {
/* 425 */     stopCompileTask();
/* 426 */     this.world = null;
/*     */     
/* 428 */     for (int i = 0; i < (EnumWorldBlockLayer.values()).length; i++) {
/*     */       
/* 430 */       if (this.vertexBuffers[i] != null)
/*     */       {
/* 432 */         this.vertexBuffers[i].deleteGlBuffers();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPosition() {
/* 439 */     return this.position;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedsUpdate(boolean needsUpdateIn) {
/* 444 */     this.needsUpdate = needsUpdateIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isNeedsUpdate() {
/* 449 */     return this.needsUpdate;
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos func_181701_a(EnumFacing p_181701_1_) {
/* 454 */     return getPositionOffset16(p_181701_1_);
/*     */   }
/*     */ 
/*     */   
/*     */   public BlockPos getPositionOffset16(EnumFacing p_getPositionOffset16_1_) {
/* 459 */     int i = p_getPositionOffset16_1_.getIndex();
/* 460 */     BlockPos blockpos = this.positionOffsets16[i];
/*     */     
/* 462 */     if (blockpos == null) {
/*     */       
/* 464 */       blockpos = getPosition().offset(p_getPositionOffset16_1_, 16);
/* 465 */       this.positionOffsets16[i] = blockpos;
/*     */     } 
/*     */     
/* 468 */     return blockpos;
/*     */   }
/*     */ 
/*     */   
/*     */   private EnumWorldBlockLayer fixBlockLayer(Block p_fixBlockLayer_1_, EnumWorldBlockLayer p_fixBlockLayer_2_) {
/* 473 */     return (p_fixBlockLayer_2_ == EnumWorldBlockLayer.CUTOUT) ? ((p_fixBlockLayer_1_ instanceof net.minecraft.block.BlockRedstoneWire) ? p_fixBlockLayer_2_ : ((p_fixBlockLayer_1_ instanceof net.minecraft.block.BlockCactus) ? p_fixBlockLayer_2_ : EnumWorldBlockLayer.CUTOUT_MIPPED)) : p_fixBlockLayer_2_;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\renderer\chunk\RenderChunk.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */