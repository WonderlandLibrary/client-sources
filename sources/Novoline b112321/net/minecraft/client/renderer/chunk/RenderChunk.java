package net.minecraft.client.renderer.chunk;

import com.google.common.collect.Sets;
import java.nio.FloatBuffer;
import java.util.EnumMap;
import java.util.Set;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator$Status;
import net.minecraft.client.renderer.chunk.ChunkCompileTaskGenerator$Type;
import net.minecraft.client.renderer.chunk.CompiledChunk;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import net.optifine.Config;
import net.optifine.Reflector;

public class RenderChunk {
   private World world;
   private final RenderGlobal renderGlobal;
   public static int renderChunksUpdated;
   private BlockPos position;
   public CompiledChunk compiledChunk = CompiledChunk.DUMMY;
   private final ReentrantLock lockCompileTask = new ReentrantLock();
   private final ReentrantLock lockCompiledChunk = new ReentrantLock();
   private ChunkCompileTaskGenerator compileTask = null;
   private final Set field_181056_j = Sets.newHashSet();
   private final int index;
   private final FloatBuffer modelviewMatrix = GLAllocation.createDirectFloatBuffer(16);
   private final VertexBuffer[] vertexBuffers = new VertexBuffer[EnumWorldBlockLayer.values().length];
   public AxisAlignedBB boundingBox;
   private int frameIndex = -1;
   private boolean needsUpdate = true;
   private EnumMap field_181702_p;
   private static final String t = "CL_00002452";
   private BlockPos[] positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
   private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
   private EnumWorldBlockLayer[] l = new EnumWorldBlockLayer[1];
   private boolean isMipmaps = Config.be();
   private boolean j = !Reflector.BetterFoliageClient.exists();
   private boolean playerUpdate = false;

   public RenderChunk(World var1, RenderGlobal var2, BlockPos var3, int var4) {
      this.world = var1;
      this.renderGlobal = var2;
      this.index = var4;
      if(!var3.equals(this.getPosition())) {
         this.setPosition(var3);
      }

      if(OpenGlHelper.useVbo()) {
         for(int var5 = 0; var5 < EnumWorldBlockLayer.values().length; ++var5) {
            this.vertexBuffers[var5] = new VertexBuffer(DefaultVertexFormats.BLOCK);
         }
      }

   }

   public boolean setFrameIndex(int var1) {
      if(this.frameIndex == var1) {
         return false;
      } else {
         this.frameIndex = var1;
         return true;
      }
   }

   public VertexBuffer getVertexBufferByLayer(int var1) {
      return this.vertexBuffers[var1];
   }

   public void resortTransparency(float var1, float var2, float var3, ChunkCompileTaskGenerator var4) {
      CompiledChunk var5 = var4.getCompiledChunk();
      if(var5.b() != null && !var5.isLayerEmpty(EnumWorldBlockLayer.TRANSLUCENT)) {
         WorldRenderer var6 = var4.getRegionRenderCacheBuilder().getWorldRendererByLayer(EnumWorldBlockLayer.TRANSLUCENT);
         this.preRenderBlocks(var6, this.position);
         var6.a(var5.b());
         this.postRenderBlocks(EnumWorldBlockLayer.TRANSLUCENT, var1, var2, var3, var6, var5);
      }

   }

   public void rebuildChunk(float param1, float param2, float param3, ChunkCompileTaskGenerator param4) {
      // $FF: Couldn't be decompiled
   }

   protected void finishCompileTask() {
      this.lockCompileTask.lock();
      RenderChunk var10000 = this;

      try {
         if(var10000.compileTask != null && this.compileTask.getStatus() != ChunkCompileTaskGenerator$Status.DONE) {
            this.compileTask.finish();
            this.compileTask = null;
         }
      } finally {
         this.lockCompileTask.unlock();
      }

   }

   public ReentrantLock getLockCompileTask() {
      return this.lockCompileTask;
   }

   public ChunkCompileTaskGenerator makeCompileTaskChunk() {
      this.lockCompileTask.lock();
      RenderChunk var10000 = this;

      ChunkCompileTaskGenerator var1;
      try {
         var10000.finishCompileTask();
         this.compileTask = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator$Type.REBUILD_CHUNK);
         var1 = this.compileTask;
      } finally {
         this.lockCompileTask.unlock();
      }

      return var1;
   }

   public ChunkCompileTaskGenerator makeCompileTaskTransparency() {
      // $FF: Couldn't be decompiled
   }

   private void preRenderBlocks(WorldRenderer var1, BlockPos var2) {
      var1.begin(7, DefaultVertexFormats.BLOCK);
      var1.setTranslation((double)(-var2.getX()), (double)(-var2.getY()), (double)(-var2.getZ()));
   }

   private void postRenderBlocks(EnumWorldBlockLayer var1, float var2, float var3, float var4, WorldRenderer var5, CompiledChunk var6) {
      if(var1 == EnumWorldBlockLayer.TRANSLUCENT && !var6.isLayerEmpty(var1)) {
         var5.func_181674_a(var2, var3, var4);
         var6.a(var5.b());
      }

      var5.finishDrawing();
   }

   private void initModelviewMatrix() {
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      float var1 = 1.000001F;
      GlStateManager.translate(-8.0F, -8.0F, -8.0F);
      GlStateManager.scale(var1, var1, var1);
      GlStateManager.translate(8.0F, 8.0F, 8.0F);
      GlStateManager.getFloat(2982, this.modelviewMatrix);
      GlStateManager.popMatrix();
   }

   public void multModelviewMatrix() {
      GlStateManager.multMatrix(this.modelviewMatrix);
   }

   public CompiledChunk getCompiledChunk() {
      return this.compiledChunk;
   }

   public void setCompiledChunk(CompiledChunk var1) {
      this.lockCompiledChunk.lock();
      RenderChunk var10000 = this;
      CompiledChunk var10001 = var1;

      try {
         var10000.compiledChunk = var10001;
      } finally {
         this.lockCompiledChunk.unlock();
      }

   }

   public void stopCompileTask() {
      this.finishCompileTask();
      this.compiledChunk = CompiledChunk.DUMMY;
   }

   public void deleteGlResources() {
      this.stopCompileTask();
      this.world = null;

      for(int var1 = 0; var1 < EnumWorldBlockLayer.values().length; ++var1) {
         if(this.vertexBuffers[var1] != null) {
            this.vertexBuffers[var1].deleteGlBuffers();
         }
      }

   }

   public BlockPos getPosition() {
      return this.position;
   }

   public void setPosition(BlockPos var1) {
      this.stopCompileTask();
      this.position = var1;
      this.boundingBox = new AxisAlignedBB(var1, var1.a(16, 16, 16));
      this.initModelviewMatrix();

      for(int var2 = 0; var2 < this.positionOffsets16.length; ++var2) {
         this.positionOffsets16[var2] = null;
      }

   }

   public boolean isNeedsUpdate() {
      return this.needsUpdate;
   }

   public void setNeedsUpdate(boolean var1) {
      this.needsUpdate = var1;
      if(this.needsUpdate) {
         if(this.isWorldPlayerUpdate()) {
            this.playerUpdate = true;
         }
      } else {
         this.playerUpdate = false;
      }

   }

   public BlockPos func_181701_a(EnumFacing var1) {
      return this.getPositionOffset16(var1);
   }

   public BlockPos getPositionOffset16(EnumFacing var1) {
      int var2 = var1.getIndex();
      BlockPos var3 = this.positionOffsets16[var2];
      var3 = this.getPosition().offset(var1, 16);
      this.positionOffsets16[var2] = var3;
      return var3;
   }

   private boolean isWorldPlayerUpdate() {
      if(this.world instanceof WorldClient) {
         WorldClient var1 = (WorldClient)this.world;
         return var1.isPlayerUpdate();
      } else {
         return false;
      }
   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   protected RegionRenderCache createRegionRenderCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      return new RegionRenderCache(var1, var2, var3, var4);
   }

   private EnumWorldBlockLayer fixBlockLayer(Block var1, EnumWorldBlockLayer var2) {
      if(this.isMipmaps) {
         if(var2 == EnumWorldBlockLayer.CUTOUT) {
            if(var1 instanceof BlockRedstoneWire) {
               return var2;
            }

            if(var1 instanceof BlockCactus) {
               return var2;
            }

            return EnumWorldBlockLayer.CUTOUT_MIPPED;
         }
      } else if(var2 == EnumWorldBlockLayer.CUTOUT_MIPPED) {
         return EnumWorldBlockLayer.CUTOUT;
      }

      return var2;
   }
}
