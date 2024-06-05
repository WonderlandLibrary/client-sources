package net.shadersmod.client;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.Bg;
import net.acE;
import net.minecraft.block.Block;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class SVertexBuilder {
   int vertexSize;
   int offsetNormal;
   int offsetUV;
   int offsetUVCenter;
   boolean hasNormal;
   boolean hasTangent;
   boolean hasUV;
   boolean hasUVCenter;
   long[] entityData = new long[10];
   int entityDataIndex = 0;

   public SVertexBuilder() {
      this.entityData[this.entityDataIndex] = 0L;
   }

   public static void initVertexBuilder(WorldRenderer var0) {
      var0.sVertexBuilder = new SVertexBuilder();
   }

   public void pushEntity(long var1) {
      ++this.entityDataIndex;
      this.entityData[this.entityDataIndex] = var1;
   }

   public void popEntity() {
      this.entityData[this.entityDataIndex] = 0L;
      --this.entityDataIndex;
   }

   public static void pushEntity(IBlockState var0, BlockPos var1, IBlockAccess var2, WorldRenderer var3) {
      ShaderOption.p();
      Block var5 = var0.getBlock();
      if(var0 instanceof BlockStateBase) {
         BlockStateBase var8 = (BlockStateBase)var0;
         int var6 = var8.getBlockId();
         int var7 = var8.getMetadata();
      }

      int var11 = Block.getIdFromBlock(var5);
      int var13 = var5.getMetaFromState(var0);
      var11 = Bg.a(var11, var13);
      int var14 = var5.getRenderType();
      int var9 = ((var14 & '\uffff') << 16) + (var11 & '\uffff');
      int var10 = var13 & '\uffff';
      var3.sVertexBuilder.pushEntity(((long)var10 << 32) + (long)var9);
   }

   public static void popEntity(WorldRenderer var0) {
      var0.sVertexBuilder.popEntity();
   }

   public static boolean popEntity(boolean var0, WorldRenderer var1) {
      var1.sVertexBuilder.popEntity();
      return var0;
   }

   public static void endSetVertexFormat(WorldRenderer var0) {
      SVertexBuilder var2 = var0.sVertexBuilder;
      VertexFormat var3 = var0.getVertexFormat();
      var2.vertexSize = var3.getNextOffset() / 4;
      var2.hasNormal = var3.hasNormal();
      ShaderOption.p();
      var2.hasTangent = var2.hasNormal;
      var2.hasUV = var3.hasUvOffset(0);
      var2.offsetNormal = var2.hasNormal?var3.getNormalOffset() / 4:0;
      var2.offsetUV = var2.hasUV?var3.getUvOffsetById(0) / 4:0;
      var2.offsetUVCenter = 8;
   }

   public static void beginAddVertex(WorldRenderer var0) {
      String[] var1 = ShaderOption.p();
      if(var0.vertexCount == 0) {
         endSetVertexFormat(var0);
      }

   }

   public static void endAddVertex(WorldRenderer var0) {
      ShaderOption.p();
      SVertexBuilder var2 = var0.sVertexBuilder;
      if(var2.vertexSize == 14) {
         if(var0.drawMode == 7 && var0.vertexCount % 4 == 0) {
            var2.calcNormal(var0, var0.func_181664_j() - 4 * var2.vertexSize);
         }

         long var3 = var2.entityData[var2.entityDataIndex];
         int var5 = var0.func_181664_j() - 14 + 12;
         var0.rawIntBuffer.put(var5, (int)var3);
         var0.rawIntBuffer.put(var5 + 1, (int)(var3 >> 32));
      }

   }

   public static void beginAddVertexData(WorldRenderer var0, int[] var1) {
      String[] var2 = ShaderOption.p();
      if(var0.vertexCount == 0) {
         endSetVertexFormat(var0);
      }

      SVertexBuilder var3 = var0.sVertexBuilder;
      if(var3.vertexSize == 14) {
         long var4 = var3.entityData[var3.entityDataIndex];
         int var6 = 12;
         if(var6 + 1 < var1.length) {
            var1[var6] = (int)var4;
            var1[var6 + 1] = (int)(var4 >> 32);
            var6 = var6 + 14;
         }
      }

   }

   public static void endAddVertexData(WorldRenderer var0) {
      ShaderOption.p();
      SVertexBuilder var2 = var0.sVertexBuilder;
      if(var2.vertexSize == 14 && var0.drawMode == 7 && var0.vertexCount % 4 == 0) {
         var2.calcNormal(var0, var0.func_181664_j() - 4 * var2.vertexSize);
      }

   }

   public void calcNormal(WorldRenderer var1, int var2) {
      FloatBuffer var4 = var1.rawFloatBuffer;
      IntBuffer var5 = var1.rawIntBuffer;
      int var6 = var1.func_181664_j();
      float var7 = var4.get(var2 + 0 * this.vertexSize);
      float var8 = var4.get(var2 + 0 * this.vertexSize + 1);
      float var9 = var4.get(var2 + 0 * this.vertexSize + 2);
      float var10 = var4.get(var2 + 0 * this.vertexSize + this.offsetUV);
      float var11 = var4.get(var2 + 0 * this.vertexSize + this.offsetUV + 1);
      float var12 = var4.get(var2 + 1 * this.vertexSize);
      float var13 = var4.get(var2 + 1 * this.vertexSize + 1);
      float var14 = var4.get(var2 + 1 * this.vertexSize + 2);
      ShaderOption.p();
      float var15 = var4.get(var2 + 1 * this.vertexSize + this.offsetUV);
      float var16 = var4.get(var2 + 1 * this.vertexSize + this.offsetUV + 1);
      float var17 = var4.get(var2 + 2 * this.vertexSize);
      float var18 = var4.get(var2 + 2 * this.vertexSize + 1);
      float var19 = var4.get(var2 + 2 * this.vertexSize + 2);
      float var20 = var4.get(var2 + 2 * this.vertexSize + this.offsetUV);
      float var21 = var4.get(var2 + 2 * this.vertexSize + this.offsetUV + 1);
      float var22 = var4.get(var2 + 3 * this.vertexSize);
      float var23 = var4.get(var2 + 3 * this.vertexSize + 1);
      float var24 = var4.get(var2 + 3 * this.vertexSize + 2);
      float var25 = var4.get(var2 + 3 * this.vertexSize + this.offsetUV);
      float var26 = var4.get(var2 + 3 * this.vertexSize + this.offsetUV + 1);
      float var27 = var17 - var7;
      float var28 = var18 - var8;
      float var29 = var19 - var9;
      float var30 = var22 - var12;
      float var31 = var23 - var13;
      float var32 = var24 - var14;
      float var33 = var28 * var32 - var31 * var29;
      float var34 = var29 * var30 - var32 * var27;
      float var35 = var27 * var31 - var30 * var28;
      float var36 = var33 * var33 + var34 * var34 + var35 * var35;
      float var37 = (double)var36 != 0.0D?(float)(1.0D / Math.sqrt((double)var36)):1.0F;
      var33 = var33 * var37;
      var34 = var34 * var37;
      var35 = var35 * var37;
      var27 = var12 - var7;
      var28 = var13 - var8;
      var29 = var14 - var9;
      float var38 = var15 - var10;
      float var39 = var16 - var11;
      var30 = var17 - var7;
      var31 = var18 - var8;
      var32 = var19 - var9;
      float var40 = var20 - var10;
      float var41 = var21 - var11;
      float var42 = var38 * var41 - var40 * var39;
      float var43 = var42 != 0.0F?1.0F / var42:1.0F;
      float var44 = (var41 * var27 - var39 * var30) * var43;
      float var45 = (var41 * var28 - var39 * var31) * var43;
      float var46 = (var41 * var29 - var39 * var32) * var43;
      float var47 = (var38 * var30 - var40 * var27) * var43;
      float var48 = (var38 * var31 - var40 * var28) * var43;
      float var49 = (var38 * var32 - var40 * var29) * var43;
      var36 = var44 * var44 + var45 * var45 + var46 * var46;
      var37 = (double)var36 != 0.0D?(float)(1.0D / Math.sqrt((double)var36)):1.0F;
      var44 = var44 * var37;
      var45 = var45 * var37;
      var46 = var46 * var37;
      var36 = var47 * var47 + var48 * var48 + var49 * var49;
      var37 = (double)var36 != 0.0D?(float)(1.0D / Math.sqrt((double)var36)):1.0F;
      var47 = var47 * var37;
      var48 = var48 * var37;
      var49 = var49 * var37;
      float var50 = var35 * var45 - var34 * var46;
      float var51 = var33 * var46 - var35 * var44;
      float var52 = var34 * var44 - var33 * var45;
      float var53 = var47 * var50 + var48 * var51 + var49 * var52 < 0.0F?-1.0F:1.0F;
      int var54 = (int)(var33 * 127.0F) & 255;
      int var55 = (int)(var34 * 127.0F) & 255;
      int var56 = (int)(var35 * 127.0F) & 255;
      int var57 = (var56 << 16) + (var55 << 8) + var54;
      var5.put(var2 + 0 * this.vertexSize + this.offsetNormal, var57);
      var5.put(var2 + 1 * this.vertexSize + this.offsetNormal, var57);
      var5.put(var2 + 2 * this.vertexSize + this.offsetNormal, var57);
      var5.put(var2 + 3 * this.vertexSize + this.offsetNormal, var57);
      int var58 = ((int)(var44 * 32767.0F) & '\uffff') + (((int)(var45 * 32767.0F) & '\uffff') << 16);
      int var59 = ((int)(var46 * 32767.0F) & '\uffff') + (((int)(var53 * 32767.0F) & '\uffff') << 16);
      var5.put(var2 + 0 * this.vertexSize + 10, var58);
      var5.put(var2 + 0 * this.vertexSize + 10 + 1, var59);
      var5.put(var2 + 1 * this.vertexSize + 10, var58);
      var5.put(var2 + 1 * this.vertexSize + 10 + 1, var59);
      var5.put(var2 + 2 * this.vertexSize + 10, var58);
      var5.put(var2 + 2 * this.vertexSize + 10 + 1, var59);
      var5.put(var2 + 3 * this.vertexSize + 10, var58);
      var5.put(var2 + 3 * this.vertexSize + 10 + 1, var59);
      float var60 = (var10 + var15 + var20 + var25) / 4.0F;
      float var61 = (var11 + var16 + var21 + var26) / 4.0F;
      var4.put(var2 + 0 * this.vertexSize + 8, var60);
      var4.put(var2 + 0 * this.vertexSize + 8 + 1, var61);
      var4.put(var2 + 1 * this.vertexSize + 8, var60);
      var4.put(var2 + 1 * this.vertexSize + 8 + 1, var61);
      var4.put(var2 + 2 * this.vertexSize + 8, var60);
      var4.put(var2 + 2 * this.vertexSize + 8 + 1, var61);
      var4.put(var2 + 3 * this.vertexSize + 8, var60);
      var4.put(var2 + 3 * this.vertexSize + 8 + 1, var61);
      if(acE.b() == null) {
         ShaderOption.b(new String[3]);
      }

   }

   public static void calcNormalChunkLayer(WorldRenderer var0) {
      String[] var1 = ShaderOption.p();
      if(var0.getVertexFormat().hasNormal() && var0.drawMode == 7 && var0.vertexCount % 4 == 0) {
         SVertexBuilder var2 = var0.sVertexBuilder;
         endSetVertexFormat(var0);
         int var3 = var0.vertexCount * var2.vertexSize;
         byte var4 = 0;
         if(var4 < var3) {
            var2.calcNormal(var0, var4);
            int var10000 = var4 + var2.vertexSize * 4;
         }
      }

   }

   public static void drawArrays(int var0, int var1, int var2, WorldRenderer var3) {
      String[] var4 = ShaderOption.p();
      VertexFormat var5 = var3.getVertexFormat();
      int var6 = var5.getNextOffset();
      if(var6 == 56) {
         ByteBuffer var7 = var3.getByteBuffer();
         var7.position(32);
         GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, var6, var7);
         var7.position(40);
         GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, var6, var7);
         var7.position(48);
         GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, var6, var7);
         var7.position(0);
         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
         GL11.glDrawArrays(var0, var1, var2);
         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
      }

      GL11.glDrawArrays(var0, var1, var2);
   }
}
