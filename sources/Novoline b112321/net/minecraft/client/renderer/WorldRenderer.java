package net.minecraft.client.renderer;

import cc.novoline.modules.visual.XRay;
import cc.novoline.utils.ColorUtils;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;
import java.util.Arrays;
import java.util.BitSet;
import net.iX;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.WorldRenderer$1;
import net.minecraft.client.renderer.WorldRenderer$WorldRenderer$2;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.renderer.vertex.VertexFormatElement$EnumUsage;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import net.optifine.Config;
import net.optifine.TextureUtils;
import net.shadersmod.client.SVertexBuilder;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;

public class WorldRenderer {
   private static final String g = "CL_00000942";
   public IntBuffer rawIntBuffer;
   public FloatBuffer rawFloatBuffer;
   public int vertexCount;
   public int drawMode;
   public SVertexBuilder sVertexBuilder;
   private ByteBuffer byteBuffer;
   private ShortBuffer field_181676_c;
   private VertexFormatElement field_181677_f;
   private int field_181678_g;
   private boolean needsUpdate;
   private double xOffset;
   private double yOffset;
   private double zOffset;
   private VertexFormat vertexFormat;
   private boolean isDrawing;
   private EnumWorldBlockLayer blockLayer = null;
   private boolean[] drawnIcons = new boolean[256];
   private TextureAtlasSprite[] quadSprites = null;
   private TextureAtlasSprite[] quadSpritesPrev = null;
   private TextureAtlasSprite quadSprite = null;

   public WorldRenderer(int var1) {
      if(Config.isShaders()) {
         var1 *= 2;
      }

      this.byteBuffer = GLAllocation.createDirectByteBuffer(var1 * 4);
      this.rawIntBuffer = this.byteBuffer.asIntBuffer();
      this.field_181676_c = this.byteBuffer.asShortBuffer();
      this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
      SVertexBuilder.initVertexBuilder(this);
   }

   private static float func_181665_a(FloatBuffer var0, float var1, float var2, float var3, int var4, int var5) {
      float var6 = var0.get(var5);
      float var7 = var0.get(var5 + 1);
      float var8 = var0.get(var5 + 2);
      float var9 = var0.get(var5 + var4);
      float var10 = var0.get(var5 + var4 + 1);
      float var11 = var0.get(var5 + var4 + 2);
      float var12 = var0.get(var5 + var4 * 2);
      float var13 = var0.get(var5 + var4 * 2 + 1);
      float var14 = var0.get(var5 + var4 * 2 + 2);
      float var15 = var0.get(var5 + var4 * 3);
      float var16 = var0.get(var5 + var4 * 3 + 1);
      float var17 = var0.get(var5 + var4 * 3 + 2);
      float var18 = (var6 + var9 + var12 + var15) * 0.25F - var1;
      float var19 = (var7 + var10 + var13 + var16) * 0.25F - var2;
      float var20 = (var8 + var11 + var14 + var17) * 0.25F - var3;
      return var18 * var18 + var19 * var19 + var20 * var20;
   }

   private void func_181670_b(int var1) {
      if(Config.isShaders()) {
         var1 *= 2;
      }

      if(var1 > this.rawIntBuffer.remaining()) {
         int var2 = this.byteBuffer.capacity();
         int var3 = var2 % 2097152;
         int var4 = var3 + (((this.rawIntBuffer.position() + var1) * 4 - var3) / 2097152 + 1) * 2097152;
         LogManager.getLogger().warn("Needed to grow BufferBuilder buffer: Old size " + var2 + " bytes, new size " + var4 + " bytes.");
         int var5 = this.rawIntBuffer.position();
         ByteBuffer var6 = GLAllocation.createDirectByteBuffer(var4);
         this.byteBuffer.position(0);
         var6.put(this.byteBuffer);
         var6.rewind();
         this.byteBuffer = var6;
         this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
         this.rawIntBuffer = this.byteBuffer.asIntBuffer();
         this.rawIntBuffer.position(var5);
         this.field_181676_c = this.byteBuffer.asShortBuffer();
         this.field_181676_c.position(var5 << 1);
         if(this.quadSprites != null) {
            TextureAtlasSprite[] var7 = this.quadSprites;
            int var8 = this.getBufferQuadSize();
            this.quadSprites = new TextureAtlasSprite[var8];
            System.arraycopy(var7, 0, this.quadSprites, 0, Math.min(var7.length, this.quadSprites.length));
            this.quadSpritesPrev = null;
         }
      }

   }

   public void func_181674_a(float var1, float var2, float var3) {
      int var4 = this.vertexCount / 4;
      float[] var5 = new float[var4];

      for(int var6 = 0; var6 < var4; ++var6) {
         var5[var6] = func_181665_a(this.rawFloatBuffer, (float)((double)var1 + this.xOffset), (float)((double)var2 + this.yOffset), (float)((double)var3 + this.zOffset), this.vertexFormat.func_181719_f(), var6 * this.vertexFormat.getNextOffset());
      }

      Integer[] var15 = new Integer[var4];

      for(int var7 = 0; var7 < var15.length; ++var7) {
         var15[var7] = Integer.valueOf(var7);
      }

      Arrays.sort(var15, new WorldRenderer$1(this, var5));
      BitSet var16 = new BitSet();
      int var8 = this.vertexFormat.getNextOffset();
      int[] var9 = new int[var8];

      for(int var17 = 0; (var17 = var16.nextClearBit(var17)) < var15.length; ++var17) {
         int var11 = var15[var17].intValue();
         if(var11 != var17) {
            this.rawIntBuffer.limit(var11 * var8 + var8);
            this.rawIntBuffer.position(var11 * var8);
            this.rawIntBuffer.get(var9);
            int var12 = var11;

            for(int var13 = var15[var11].intValue(); var12 != var17; var13 = var15[var13].intValue()) {
               this.rawIntBuffer.limit(var13 * var8 + var8);
               this.rawIntBuffer.position(var13 * var8);
               IntBuffer var14 = this.rawIntBuffer.slice();
               this.rawIntBuffer.limit(var12 * var8 + var8);
               this.rawIntBuffer.position(var12 * var8);
               this.rawIntBuffer.put(var14);
               var16.set(var12);
               var12 = var13;
            }

            this.rawIntBuffer.limit(var17 * var8 + var8);
            this.rawIntBuffer.position(var17 * var8);
            this.rawIntBuffer.put(var9);
         }

         var16.set(var17);
      }

      this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
      this.rawIntBuffer.position(this.func_181664_j());
      if(this.quadSprites != null) {
         TextureAtlasSprite[] var18 = new TextureAtlasSprite[this.vertexCount / 4];
         int var19 = this.vertexFormat.func_181719_f() / 4 * 4;

         for(int var20 = 0; var20 < var15.length; ++var20) {
            int var21 = var15[var20].intValue();
            var18[var20] = this.quadSprites[var21];
         }

         System.arraycopy(var18, 0, this.quadSprites, 0, var18.length);
      }

   }

   public iX b() {
      this.rawIntBuffer.rewind();
      int var1 = this.func_181664_j();
      this.rawIntBuffer.limit(var1);
      int[] var2 = new int[var1];
      this.rawIntBuffer.get(var2);
      this.rawIntBuffer.limit(this.rawIntBuffer.capacity());
      this.rawIntBuffer.position(var1);
      TextureAtlasSprite[] var3 = null;
      if(this.quadSprites != null) {
         int var4 = this.vertexCount / 4;
         var3 = new TextureAtlasSprite[var4];
         System.arraycopy(this.quadSprites, 0, var3, 0, var4);
      }

      return new iX(var2, new VertexFormat(this.vertexFormat), var3);
   }

   public int func_181664_j() {
      return this.vertexCount * this.vertexFormat.func_181719_f();
   }

   public void a(iX var1) {
      this.rawIntBuffer.clear();
      this.func_181670_b(var1.b().length);
      this.rawIntBuffer.put(var1.b());
      this.vertexCount = var1.c();
      this.vertexFormat = new VertexFormat(var1.a());
      if(iX.a(var1) != null) {
         if(this.quadSprites == null) {
            this.quadSprites = this.quadSpritesPrev;
         }

         if(this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
            this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
         }

         TextureAtlasSprite[] var2 = iX.a(var1);
         System.arraycopy(var2, 0, this.quadSprites, 0, var2.length);
      } else {
         if(this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
      }

   }

   public void reset() {
      this.vertexCount = 0;
      this.field_181677_f = null;
      this.field_181678_g = 0;
      this.quadSprite = null;
   }

   public void begin(int var1, VertexFormat var2) {
      if(this.isDrawing) {
         throw new IllegalStateException("Already building!");
      } else {
         this.isDrawing = true;
         this.reset();
         this.drawMode = var1;
         this.vertexFormat = var2;
         this.field_181677_f = var2.getElement(this.field_181678_g);
         this.needsUpdate = false;
         this.byteBuffer.limit(this.byteBuffer.capacity());
         if(Config.isShaders()) {
            SVertexBuilder.endSetVertexFormat(this);
         }

         if(Config.isMultiTexture()) {
            if(this.blockLayer != null) {
               if(this.quadSprites == null) {
                  this.quadSprites = this.quadSpritesPrev;
               }

               if(this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
                  this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
               }
            }
         } else {
            if(this.quadSprites != null) {
               this.quadSpritesPrev = this.quadSprites;
            }

            this.quadSprites = null;
         }

      }
   }

   public WorldRenderer tex(double var1, double var3) {
      if(this.quadSprite != null && this.quadSprites != null) {
         var1 = (double)this.quadSprite.toSingleU((float)var1);
         var3 = (double)this.quadSprite.toSingleV((float)var3);
         this.quadSprites[this.vertexCount / 4] = this.quadSprite;
      }

      int var5 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
      switch(WorldRenderer$WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
      case 1:
         this.byteBuffer.putFloat(var5, (float)var1);
         this.byteBuffer.putFloat(var5 + 4, (float)var3);
         break;
      case 2:
      case 3:
         this.byteBuffer.putInt(var5, (int)var1);
         this.byteBuffer.putInt(var5 + 4, (int)var3);
         break;
      case 4:
      case 5:
         this.byteBuffer.putShort(var5, (short)((int)var3));
         this.byteBuffer.putShort(var5 + 2, (short)((int)var1));
         break;
      case 6:
      case 7:
         this.byteBuffer.put(var5, (byte)((int)var3));
         this.byteBuffer.put(var5 + 1, (byte)((int)var1));
      }

      this.func_181667_k();
      return this;
   }

   public WorldRenderer lightmap(int var1, int var2) {
      int var3 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
      switch(WorldRenderer$WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
      case 1:
         this.byteBuffer.putFloat(var3, (float)var1);
         this.byteBuffer.putFloat(var3 + 4, (float)var2);
         break;
      case 2:
      case 3:
         this.byteBuffer.putInt(var3, var1);
         this.byteBuffer.putInt(var3 + 4, var2);
         break;
      case 4:
      case 5:
         this.byteBuffer.putShort(var3, (short)var2);
         this.byteBuffer.putShort(var3 + 2, (short)var1);
         break;
      case 6:
      case 7:
         this.byteBuffer.put(var3, (byte)var2);
         this.byteBuffer.put(var3 + 1, (byte)var1);
      }

      this.func_181667_k();
      return this;
   }

   public void putBrightness4(int var1, int var2, int var3, int var4) {
      int var5 = (this.vertexCount - 4) * this.vertexFormat.func_181719_f() + this.vertexFormat.getUvOffsetById(1) / 4;
      int var6 = this.vertexFormat.getNextOffset() >> 2;
      this.rawIntBuffer.put(var5, var1);
      this.rawIntBuffer.put(var5 + var6, var2);
      this.rawIntBuffer.put(var5 + var6 * 2, var3);
      this.rawIntBuffer.put(var5 + var6 * 3, var4);
   }

   public void putPosition(double var1, double var3, double var5) {
      int var7 = this.vertexFormat.func_181719_f();
      int var8 = (this.vertexCount - 4) * var7;

      for(int var9 = 0; var9 < 4; ++var9) {
         int var10 = var8 + var9 * var7;
         int var11 = var10 + 1;
         int var12 = var11 + 1;
         this.rawIntBuffer.put(var10, Float.floatToRawIntBits((float)(var1 + this.xOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var10))));
         this.rawIntBuffer.put(var11, Float.floatToRawIntBits((float)(var3 + this.yOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var11))));
         this.rawIntBuffer.put(var12, Float.floatToRawIntBits((float)(var5 + this.zOffset) + Float.intBitsToFloat(this.rawIntBuffer.get(var12))));
      }

   }

   public int getColorIndex(int var1) {
      return ((this.vertexCount - var1) * this.vertexFormat.getNextOffset() + this.vertexFormat.getColorOffset()) / 4;
   }

   public void putColorMultiplier(float var1, float var2, float var3, int var4) {
      int var5 = this.getColorIndex(var4);
      int var6 = -1;
      if(!this.needsUpdate) {
         var6 = this.rawIntBuffer.get(var5);
         if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            int var7 = (int)((float)(var6 & 255) * var1);
            int var8 = (int)((float)(var6 >> 8 & 255) * var2);
            int var9 = (int)((float)(var6 >> 16 & 255) * var3);
            var6 = var6 & -16777216;
            var6 = var6 | var9 << 16 | var8 << 8 | var7;
            if(XRay.isEnabled) {
               var6 = ColorUtils.getColor(var7, var8, var9, XRay.alpha);
            }

            if(!RenderGlobal.ak) {
               var6 = ColorUtils.getColor(var7, var8, var9, 0);
            }
         } else {
            int var13 = (int)((float)(var6 >> 24 & 255) * var1);
            int var14 = (int)((float)(var6 >> 16 & 255) * var2);
            int var15 = (int)((float)(var6 >> 8 & 255) * var3);
            var6 = var6 & 255;
            var6 = var6 | var13 << 24 | var14 << 16 | var15 << 8;
            if(XRay.isEnabled) {
               var6 = ColorUtils.getColor(var13, var14, var15, XRay.alpha);
            }

            if(!RenderGlobal.ak) {
               var6 = ColorUtils.getColor(var13, var14, var15, 0);
            }
         }
      }

      this.rawIntBuffer.put(var5, var6);
   }

   private void putColor(int var1, int var2) {
      int var3 = this.getColorIndex(var2);
      int var4 = var1 >> 16 & 255;
      int var5 = var1 >> 8 & 255;
      int var6 = var1 & 255;
      int var7 = var1 >> 24 & 255;
      this.putColorRGBA(var3, var4, var5, var6, var7);
   }

   public void putColorRGB_F(float var1, float var2, float var3, int var4) {
      int var5 = this.getColorIndex(var4);
      int var6 = MathHelper.clamp_int((int)(var1 * 255.0F), 0, 255);
      int var7 = MathHelper.clamp_int((int)(var2 * 255.0F), 0, 255);
      int var8 = MathHelper.clamp_int((int)(var3 * 255.0F), 0, 255);
      this.putColorRGBA(var5, var6, var7, var8, 255);
   }

   public void putColorRGBA(int var1, int var2, int var3, int var4, int var5) {
      if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.rawIntBuffer.put(var1, var5 << 24 | var4 << 16 | var3 << 8 | var2);
      } else {
         this.rawIntBuffer.put(var1, var2 << 24 | var3 << 16 | var4 << 8 | var5);
      }

   }

   public void markDirty() {
      this.needsUpdate = true;
   }

   public WorldRenderer color(float var1, float var2, float var3, float var4) {
      return this.color((int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F), (int)(var4 * 255.0F));
   }

   public WorldRenderer color(int var1, int var2, int var3, int var4) {
      if(!this.needsUpdate) {
         int var5 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
         switch(WorldRenderer$WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
         case 1:
            this.byteBuffer.putFloat(var5, (float)var1 / 255.0F);
            this.byteBuffer.putFloat(var5 + 4, (float)var2 / 255.0F);
            this.byteBuffer.putFloat(var5 + 8, (float)var3 / 255.0F);
            this.byteBuffer.putFloat(var5 + 12, (float)var4 / 255.0F);
            break;
         case 2:
         case 3:
            this.byteBuffer.putFloat(var5, (float)var1);
            this.byteBuffer.putFloat(var5 + 4, (float)var2);
            this.byteBuffer.putFloat(var5 + 8, (float)var3);
            this.byteBuffer.putFloat(var5 + 12, (float)var4);
            break;
         case 4:
         case 5:
            this.byteBuffer.putShort(var5, (short)var1);
            this.byteBuffer.putShort(var5 + 2, (short)var2);
            this.byteBuffer.putShort(var5 + 4, (short)var3);
            this.byteBuffer.putShort(var5 + 6, (short)var4);
            break;
         case 6:
         case 7:
            if(ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
               this.byteBuffer.put(var5, (byte)var1);
               this.byteBuffer.put(var5 + 1, (byte)var2);
               this.byteBuffer.put(var5 + 2, (byte)var3);
               this.byteBuffer.put(var5 + 3, (byte)var4);
            } else {
               this.byteBuffer.put(var5, (byte)var4);
               this.byteBuffer.put(var5 + 1, (byte)var3);
               this.byteBuffer.put(var5 + 2, (byte)var2);
               this.byteBuffer.put(var5 + 3, (byte)var1);
            }
         }

         this.func_181667_k();
      }

      return this;
   }

   public void addVertexData(int[] var1) {
      if(Config.isShaders()) {
         SVertexBuilder.beginAddVertexData(this, var1);
      }

      this.func_181670_b(var1.length);
      this.rawIntBuffer.position(this.func_181664_j());
      this.rawIntBuffer.put(var1);
      this.vertexCount += var1.length / this.vertexFormat.func_181719_f();
      if(Config.isShaders()) {
         SVertexBuilder.endAddVertexData(this);
      }

   }

   public void endVertex() {
      ++this.vertexCount;
      this.func_181670_b(this.vertexFormat.func_181719_f());
      this.field_181678_g = 0;
      this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
      if(Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }

   }

   public WorldRenderer pos(double var1, double var3, double var5) {
      if(Config.isShaders()) {
         SVertexBuilder.beginAddVertex(this);
      }

      int var7 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
      switch(WorldRenderer$WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
      case 1:
         this.byteBuffer.putFloat(var7, (float)(var1 + this.xOffset));
         this.byteBuffer.putFloat(var7 + 4, (float)(var3 + this.yOffset));
         this.byteBuffer.putFloat(var7 + 8, (float)(var5 + this.zOffset));
         break;
      case 2:
      case 3:
         this.byteBuffer.putInt(var7, Float.floatToRawIntBits((float)(var1 + this.xOffset)));
         this.byteBuffer.putInt(var7 + 4, Float.floatToRawIntBits((float)(var3 + this.yOffset)));
         this.byteBuffer.putInt(var7 + 8, Float.floatToRawIntBits((float)(var5 + this.zOffset)));
         break;
      case 4:
      case 5:
         this.byteBuffer.putShort(var7, (short)((int)(var1 + this.xOffset)));
         this.byteBuffer.putShort(var7 + 2, (short)((int)(var3 + this.yOffset)));
         this.byteBuffer.putShort(var7 + 4, (short)((int)(var5 + this.zOffset)));
         break;
      case 6:
      case 7:
         this.byteBuffer.put(var7, (byte)((int)(var1 + this.xOffset)));
         this.byteBuffer.put(var7 + 1, (byte)((int)(var3 + this.yOffset)));
         this.byteBuffer.put(var7 + 2, (byte)((int)(var5 + this.zOffset)));
      }

      this.func_181667_k();
      return this;
   }

   public void putNormal(float var1, float var2, float var3) {
      int var4 = (byte)((int)(var1 * 127.0F)) & 255;
      int var5 = (byte)((int)(var2 * 127.0F)) & 255;
      int var6 = (byte)((int)(var3 * 127.0F)) & 255;
      int var7 = var4 | var5 << 8 | var6 << 16;
      int var8 = this.vertexFormat.getNextOffset() >> 2;
      int var9 = (this.vertexCount - 4) * var8 + this.vertexFormat.getNormalOffset() / 4;
      this.rawIntBuffer.put(var9, var7);
      this.rawIntBuffer.put(var9 + var8, var7);
      this.rawIntBuffer.put(var9 + var8 * 2, var7);
      this.rawIntBuffer.put(var9 + var8 * 3, var7);
   }

   private void func_181667_k() {
      ++this.field_181678_g;
      this.field_181678_g %= this.vertexFormat.getElementCount();
      this.field_181677_f = this.vertexFormat.getElement(this.field_181678_g);
      if(this.field_181677_f.getUsage() == VertexFormatElement$EnumUsage.PADDING) {
         this.func_181667_k();
      }

   }

   public WorldRenderer normal(float var1, float var2, float var3) {
      int var4 = this.vertexCount * this.vertexFormat.getNextOffset() + this.vertexFormat.func_181720_d(this.field_181678_g);
      switch(WorldRenderer$WorldRenderer$2.field_181661_a[this.field_181677_f.getType().ordinal()]) {
      case 1:
         this.byteBuffer.putFloat(var4, var1);
         this.byteBuffer.putFloat(var4 + 4, var2);
         this.byteBuffer.putFloat(var4 + 8, var3);
         break;
      case 2:
      case 3:
         this.byteBuffer.putInt(var4, (int)var1);
         this.byteBuffer.putInt(var4 + 4, (int)var2);
         this.byteBuffer.putInt(var4 + 8, (int)var3);
         break;
      case 4:
      case 5:
         this.byteBuffer.putShort(var4, (short)((int)(var1 * 32767.0F) & '\uffff'));
         this.byteBuffer.putShort(var4 + 2, (short)((int)(var2 * 32767.0F) & '\uffff'));
         this.byteBuffer.putShort(var4 + 4, (short)((int)(var3 * 32767.0F) & '\uffff'));
         break;
      case 6:
      case 7:
         this.byteBuffer.put(var4, (byte)((int)(var1 * 127.0F) & 255));
         this.byteBuffer.put(var4 + 1, (byte)((int)(var2 * 127.0F) & 255));
         this.byteBuffer.put(var4 + 2, (byte)((int)(var3 * 127.0F) & 255));
      }

      this.func_181667_k();
      return this;
   }

   public void setTranslation(double var1, double var3, double var5) {
      this.xOffset = var1;
      this.yOffset = var3;
      this.zOffset = var5;
   }

   public void finishDrawing() {
      if(!this.isDrawing) {
         throw new IllegalStateException("Not building!");
      } else {
         this.isDrawing = false;
         this.byteBuffer.position(0);
         this.byteBuffer.limit(this.func_181664_j() * 4);
      }
   }

   public ByteBuffer getByteBuffer() {
      return this.byteBuffer;
   }

   public VertexFormat getVertexFormat() {
      return this.vertexFormat;
   }

   public int getVertexCount() {
      return this.vertexCount;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public void putColor4(int var1) {
      for(int var2 = 0; var2 < 4; ++var2) {
         this.putColor(var1, var2 + 1);
      }

   }

   public void putColorRGB_F4(float var1, float var2, float var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         this.putColorRGB_F(var1, var2, var3, var4 + 1);
      }

   }

   public void putSprite(TextureAtlasSprite var1) {
      if(this.quadSprites != null) {
         int var2 = this.vertexCount / 4;
         this.quadSprites[var2 - 1] = var1;
      }

   }

   public void setSprite(TextureAtlasSprite var1) {
      if(this.quadSprites != null) {
         this.quadSprite = var1;
      }

   }

   public boolean isMultiTexture() {
      return this.quadSprites != null;
   }

   public void drawMultiTexture() {
      if(this.quadSprites != null) {
         int var1 = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
         if(this.drawnIcons.length <= var1) {
            this.drawnIcons = new boolean[var1 + 1];
         }

         Arrays.fill(this.drawnIcons, false);
         int var2 = 0;
         int var3 = -1;
         int var4 = this.vertexCount / 4;

         for(int var5 = 0; var5 < var4; ++var5) {
            TextureAtlasSprite var6 = this.quadSprites[var5];
            int var7 = var6.getIndexInMap();
            if(!this.drawnIcons[var7]) {
               if(var6 == TextureUtils.iconGrassSideOverlay) {
                  var3 = var5;
               } else {
                  var5 = this.drawForIcon(var6, var5) - 1;
                  ++var2;
                  if(this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                     this.drawnIcons[var7] = true;
                  }
               }
            }
         }

         this.drawForIcon(TextureUtils.iconGrassSideOverlay, var3);
         ++var2;
      }

   }

   private int drawForIcon(TextureAtlasSprite var1, int var2) {
      GL11.glBindTexture(3553, var1.glSpriteTextureId);
      boolean var3 = true;
      int var4 = -1;
      int var5 = this.vertexCount / 4;

      for(int var6 = var2; var6 < var5; ++var6) {
         TextureAtlasSprite var7 = this.quadSprites[var6];
         if(var7 == var1) {
            var4 = var6;
         } else {
            this.draw(var4, var6);
            if(this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
               return var6;
            }

            var4 = -1;
         }
      }

      this.draw(var4, var5);
      return var5;
   }

   private void draw(int var1, int var2) {
      int var3 = var2 - var1;
      int var4 = var1 * 4;
      int var5 = var3 * 4;
      GL11.glDrawArrays(this.drawMode, var4, var5);
   }

   public void setBlockLayer(EnumWorldBlockLayer var1) {
      this.blockLayer = var1;
      if(this.quadSprites != null) {
         this.quadSpritesPrev = this.quadSprites;
      }

      this.quadSprites = null;
      this.quadSprite = null;
   }

   private int getBufferQuadSize() {
      return this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.func_181719_f() * 4);
   }

   public void checkAndGrow() {
      this.func_181670_b(this.vertexFormat.func_181719_f());
   }

   public boolean isColorDisabled() {
      return this.needsUpdate;
   }

   public void startDrawingQuads() {
      this.begin(7, DefaultVertexFormats.POSITION_TEX);
   }

   private static IllegalStateException a(IllegalStateException var0) {
      return var0;
   }
}
