package net.minecraft.client.renderer.chunk;

import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.concurrent.locks.ReentrantLock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCactus;
import net.minecraft.block.BlockRedstoneWire;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RegionRenderCache;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexBuffer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.World;
import optifine.BlockPosM;
import optifine.Config;
import optifine.Reflector;
import optifine.ReflectorForge;
import shadersmod.client.SVertexBuilder;

public class RenderChunk {
   private final int field_178596_j;
   private final ReentrantLock field_178598_h;
   private World field_178588_d;
   private boolean field_178593_n;
   private final ReentrantLock field_178587_g;
   private final VertexBuffer[] field_178594_l;
   private ChunkCompileTaskGenerator field_178599_i;
   private BlockPos[] positionOffsets16;
   private EnumWorldBlockLayer[] blockLayersSingle;
   private BlockPos field_178586_f;
   private final FloatBuffer field_178597_k;
   private boolean playerUpdate;
   private int field_178595_m;
   private final RenderGlobal field_178589_e;
   public CompiledChunk field_178590_b;
   private boolean fixBlockLayer;
   private static EnumWorldBlockLayer[] ENUM_WORLD_BLOCK_LAYERS = EnumWorldBlockLayer.values();
   public static int field_178592_a;
   public AxisAlignedBB field_178591_c;
   private boolean isMipmaps;
   private static final String __OBFID = "CL_00002452";

   private EnumWorldBlockLayer fixBlockLayer(Block var1, EnumWorldBlockLayer var2) {
      if (this.isMipmaps) {
         if (var2 == EnumWorldBlockLayer.CUTOUT) {
            if (var1 instanceof BlockRedstoneWire) {
               return var2;
            }

            if (var1 instanceof BlockCactus) {
               return var2;
            }

            return EnumWorldBlockLayer.CUTOUT_MIPPED;
         }
      } else if (var2 == EnumWorldBlockLayer.CUTOUT_MIPPED) {
         return EnumWorldBlockLayer.CUTOUT;
      }

      return var2;
   }

   public void func_178580_a(CompiledChunk var1) {
      this.field_178598_h.lock();

      try {
         this.field_178590_b = var1;
         this.field_178598_h.unlock();
      } finally {
         this.field_178598_h.unlock();
      }
   }

   public void func_178581_b(float var1, float var2, float var3, ChunkCompileTaskGenerator var4) {
      CompiledChunk var5 = new CompiledChunk();
      boolean var6 = true;
      BlockPos var7 = this.field_178586_f;
      BlockPos var8 = var7.add(15, 15, 15);
      var4.func_178540_f().lock();

      RegionRenderCache var9;
      label262: {
         try {
            if (var4.func_178546_a() == ChunkCompileTaskGenerator.Status.COMPILING) {
               if (this.field_178588_d == null) {
                  return;
               }

               var9 = this.createRegionRenderCache(this.field_178588_d, var7.add(-1, -1, -1), var8.add(1, 1, 1), 1);
               if (Reflector.MinecraftForgeClient_onRebuildChunk.exists()) {
                  Reflector.call(Reflector.MinecraftForgeClient_onRebuildChunk, this.field_178588_d, this.field_178586_f, var9);
               }

               var4.func_178543_a(var5);
               break label262;
            }
         } finally {
            var4.func_178540_f().unlock();
         }

         return;
      }

      VisGraph var10 = new VisGraph();
      if (!var9.extendedLevelsInChunkCache()) {
         ++field_178592_a;
         Iterator var11 = BlockPosM.getAllInBoxMutable(var7, var8).iterator();
         boolean var12 = Reflector.ForgeBlock_hasTileEntity.exists();
         boolean var13 = Reflector.ForgeBlock_canRenderInLayer.exists();
         boolean var14 = Reflector.ForgeHooksClient_setRenderLayer.exists();

         while(var11.hasNext()) {
            BlockPosM var15 = (BlockPosM)var11.next();
            IBlockState var16 = var9.getBlockState(var15);
            Block var17 = var16.getBlock();
            if (var17.isOpaqueCube()) {
               var10.func_178606_a(var15);
            }

            if (ReflectorForge.blockHasTileEntity(var16)) {
               TileEntity var18 = var9.getTileEntity(new BlockPos(var15));
               if (var18 != null && TileEntityRendererDispatcher.instance.hasSpecialRenderer(var18)) {
                  var5.func_178490_a(var18);
               }
            }

            EnumWorldBlockLayer[] var28;
            if (var13) {
               var28 = ENUM_WORLD_BLOCK_LAYERS;
            } else {
               var28 = this.blockLayersSingle;
               var28[0] = var17.getBlockLayer();
            }

            for(int var19 = 0; var19 < var28.length; ++var19) {
               EnumWorldBlockLayer var20 = var28[var19];
               if (var13) {
                  boolean var21 = Reflector.callBoolean(var17, Reflector.ForgeBlock_canRenderInLayer, var20);
                  if (!var21) {
                     continue;
                  }
               }

               if (var14) {
                  Reflector.callVoid(Reflector.ForgeHooksClient_setRenderLayer, var20);
               }

               if (this.fixBlockLayer) {
                  var20 = this.fixBlockLayer(var17, var20);
               }

               int var30 = var20.ordinal();
               if (var17.getRenderType() != -1) {
                  WorldRenderer var22 = var4.func_178545_d().func_179039_a(var30);
                  var22.setBlockLayer(var20);
                  if (!var5.func_178492_d(var20)) {
                     var5.func_178493_c(var20);
                     this.func_178573_a(var22, var7);
                  }

                  if (Minecraft.getMinecraft().getBlockRendererDispatcher().func_175018_a(var16, var15, var9, var22)) {
                     var5.func_178486_a(var20);
                  }
               }
            }
         }

         EnumWorldBlockLayer[] var25 = ENUM_WORLD_BLOCK_LAYERS;
         int var26 = var25.length;

         for(int var27 = 0; var27 < var26; ++var27) {
            EnumWorldBlockLayer var29 = var25[var27];
            if (var5.func_178492_d(var29)) {
               if (Config.isShaders()) {
                  SVertexBuilder.calcNormalChunkLayer(var4.func_178545_d().func_179038_a(var29));
               }

               this.func_178584_a(var29, var1, var2, var3, var4.func_178545_d().func_179038_a(var29), var5);
            }
         }
      }

      var5.func_178488_a(var10.func_178607_a());
   }

   public void func_178570_a(float var1, float var2, float var3, ChunkCompileTaskGenerator var4) {
      CompiledChunk var5 = var4.func_178544_c();
      if (var5.func_178487_c() != null && !var5.func_178491_b(EnumWorldBlockLayer.TRANSLUCENT)) {
         WorldRenderer var6 = var4.func_178545_d().func_179038_a(EnumWorldBlockLayer.TRANSLUCENT);
         this.func_178573_a(var6, this.field_178586_f);
         var6.setVertexState(var5.func_178487_c());
         this.func_178584_a(EnumWorldBlockLayer.TRANSLUCENT, var1, var2, var3, var6, var5);
      }

   }

   private void func_178584_a(EnumWorldBlockLayer var1, float var2, float var3, float var4, WorldRenderer var5, CompiledChunk var6) {
      if (var1 == EnumWorldBlockLayer.TRANSLUCENT && !var6.func_178491_b(var1)) {
         var6.func_178494_a(var5.getVertexState(var2, var3, var4));
      }

      var5.draw();
   }

   public void func_178566_a() {
      this.func_178585_h();
      this.field_178588_d = null;

      for(int var1 = 0; var1 < EnumWorldBlockLayer.values().length; ++var1) {
         if (this.field_178594_l[var1] != null) {
            this.field_178594_l[var1].func_177362_c();
         }
      }

   }

   public void func_178585_h() {
      this.func_178578_b();
      this.field_178590_b = CompiledChunk.field_178502_a;
   }

   public boolean func_178569_m() {
      return this.field_178593_n;
   }

   public boolean func_178577_a(int var1) {
      if (this.field_178595_m == var1) {
         return false;
      } else {
         this.field_178595_m = var1;
         return true;
      }
   }

   public ReentrantLock func_178579_c() {
      return this.field_178587_g;
   }

   public ChunkCompileTaskGenerator func_178574_d() {
      this.field_178587_g.lock();

      try {
         this.func_178578_b();
         this.field_178599_i = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.REBUILD_CHUNK);
         ChunkCompileTaskGenerator var1 = this.field_178599_i;
         this.field_178587_g.unlock();
         return var1;
      } finally {
         this.field_178587_g.unlock();
      }
   }

   private boolean isWorldPlayerUpdate() {
      if (this.field_178588_d instanceof WorldClient) {
         WorldClient var1 = (WorldClient)this.field_178588_d;
         return var1.isPlayerUpdate();
      } else {
         return false;
      }
   }

   public ChunkCompileTaskGenerator func_178582_e() {
      this.field_178587_g.lock();

      Object var4;
      try {
         ChunkCompileTaskGenerator var2;
         if (this.field_178599_i == null || this.field_178599_i.func_178546_a() != ChunkCompileTaskGenerator.Status.PENDING) {
            if (this.field_178599_i != null && this.field_178599_i.func_178546_a() != ChunkCompileTaskGenerator.Status.DONE) {
               this.field_178599_i.func_178542_e();
               this.field_178599_i = null;
            }

            this.field_178599_i = new ChunkCompileTaskGenerator(this, ChunkCompileTaskGenerator.Type.RESORT_TRANSPARENCY);
            this.field_178599_i.func_178543_a(this.field_178590_b);
            var2 = this.field_178599_i;
            this.field_178587_g.unlock();
            return var2;
         }

         var2 = null;
         var4 = var2;
      } finally {
         this.field_178587_g.unlock();
      }

      return (ChunkCompileTaskGenerator)var4;
   }

   public boolean func_178583_l() {
      this.field_178587_g.lock();

      try {
         boolean var1 = this.field_178599_i == null || this.field_178599_i.func_178546_a() == ChunkCompileTaskGenerator.Status.PENDING;
         this.field_178587_g.unlock();
         return var1;
      } finally {
         this.field_178587_g.unlock();
      }
   }

   public VertexBuffer func_178565_b(int var1) {
      return this.field_178594_l[var1];
   }

   public void func_178575_a(boolean var1) {
      this.field_178593_n = var1;
      if (this.field_178593_n) {
         if (this.isWorldPlayerUpdate()) {
            this.playerUpdate = true;
         }
      } else {
         this.playerUpdate = false;
      }

   }

   public CompiledChunk func_178571_g() {
      return this.field_178590_b;
   }

   protected RegionRenderCache createRegionRenderCache(World var1, BlockPos var2, BlockPos var3, int var4) {
      return new RegionRenderCache(var1, var2, var3, var4);
   }

   private void func_178567_n() {
      GlStateManager.pushMatrix();
      GlStateManager.loadIdentity();
      float var1 = 1.000001F;
      GlStateManager.translate(-8.0F, -8.0F, -8.0F);
      GlStateManager.scale(var1, var1, var1);
      GlStateManager.translate(8.0F, 8.0F, 8.0F);
      GlStateManager.getFloat(2982, this.field_178597_k);
      GlStateManager.popMatrix();
   }

   public BlockPos func_178568_j() {
      return this.field_178586_f;
   }

   public RenderChunk(World var1, RenderGlobal var2, BlockPos var3, int var4) {
      this.positionOffsets16 = new BlockPos[EnumFacing.VALUES.length];
      this.blockLayersSingle = new EnumWorldBlockLayer[1];
      this.isMipmaps = Config.isMipmaps();
      this.fixBlockLayer = !Reflector.BetterFoliageClient.exists();
      this.playerUpdate = false;
      this.field_178590_b = CompiledChunk.field_178502_a;
      this.field_178587_g = new ReentrantLock();
      this.field_178598_h = new ReentrantLock();
      this.field_178599_i = null;
      this.field_178597_k = GLAllocation.createDirectFloatBuffer(16);
      this.field_178594_l = new VertexBuffer[EnumWorldBlockLayer.values().length];
      this.field_178595_m = -1;
      this.field_178593_n = true;
      this.field_178588_d = var1;
      this.field_178589_e = var2;
      this.field_178596_j = var4;
      if (!var3.equals(this.func_178568_j())) {
         this.func_178576_a(var3);
      }

      if (OpenGlHelper.func_176075_f()) {
         for(int var5 = 0; var5 < EnumWorldBlockLayer.values().length; ++var5) {
            this.field_178594_l[var5] = new VertexBuffer(DefaultVertexFormats.field_176600_a);
         }
      }

   }

   public boolean isPlayerUpdate() {
      return this.playerUpdate;
   }

   protected void func_178578_b() {
      this.field_178587_g.lock();

      try {
         if (this.field_178599_i != null && this.field_178599_i.func_178546_a() != ChunkCompileTaskGenerator.Status.DONE) {
            this.field_178599_i.func_178542_e();
            this.field_178599_i = null;
         }
      } finally {
         this.field_178587_g.unlock();
      }

   }

   public BlockPos getPositionOffset16(EnumFacing var1) {
      int var2 = var1.getIndex();
      BlockPos var3 = this.positionOffsets16[var2];
      if (var3 == null) {
         var3 = this.func_178568_j().offset(var1, 16);
         this.positionOffsets16[var2] = var3;
      }

      return var3;
   }

   private void func_178573_a(WorldRenderer var1, BlockPos var2) {
      var1.startDrawing(7);
      var1.setVertexFormat(DefaultVertexFormats.field_176600_a);
      var1.setTranslation((double)(-var2.getX()), (double)(-var2.getY()), (double)(-var2.getZ()));
   }

   public void func_178576_a(BlockPos var1) {
      this.func_178585_h();
      this.field_178586_f = var1;
      this.field_178591_c = new AxisAlignedBB(var1, var1.add(16, 16, 16));
      this.func_178567_n();

      for(int var2 = 0; var2 < this.positionOffsets16.length; ++var2) {
         this.positionOffsets16[var2] = null;
      }

   }

   public void func_178572_f() {
      GlStateManager.multMatrix(this.field_178597_k);
   }
}
