package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Arrays;
import java.util.List;
import java.util.PriorityQueue;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.renderer.vertex.VertexFormatElement;
import net.minecraft.client.util.QuadComparator;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.TextureUtils;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.GL11;
import shadersmod.client.SVertexBuilder;

public class WorldRenderer {
   private boolean needsUpdate;
   public IntBuffer rawIntBuffer;
   public int rawBufferIndex;
   private TextureAtlasSprite[] quadSprites = null;
   public SVertexBuilder sVertexBuilder;
   private int bufferSize;
   private VertexFormat vertexFormat;
   private int field_179012_p;
   private double field_178998_e;
   private double xOffset;
   private double yOffset;
   private boolean isDrawing;
   private ByteBuffer byteBuffer;
   private TextureAtlasSprite[] quadSpritesPrev = null;
   private TextureAtlasSprite quadSprite = null;
   private int field_179007_h;
   private double field_178995_f;
   private int field_178996_g;
   public int vertexCount;
   private boolean[] drawnIcons = new boolean[256];
   private EnumWorldBlockLayer blockLayer = null;
   private static final String __OBFID = "CL_00000942";
   private int field_179003_o;
   private double zOffset;
   public int drawMode;
   public FloatBuffer rawFloatBuffer;

   public void addVertex(double var1, double var3, double var5) {
      if (Config.isShaders()) {
         SVertexBuilder.beginAddVertex(this);
      }

      if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
         this.growBuffer(2097152);
      }

      List var7 = this.vertexFormat.func_177343_g();
      int var8 = var7.size();

      for(int var9 = 0; var9 < var8; ++var9) {
         VertexFormatElement var10 = (VertexFormatElement)var7.get(var9);
         int var11 = var10.func_177373_a() >> 2;
         int var12 = this.rawBufferIndex + var11;
         switch(var10.func_177375_c()) {
         case POSITION:
            this.rawIntBuffer.put(var12, Float.floatToRawIntBits((float)(var1 + this.xOffset)));
            this.rawIntBuffer.put(var12 + 1, Float.floatToRawIntBits((float)(var3 + this.yOffset)));
            this.rawIntBuffer.put(var12 + 2, Float.floatToRawIntBits((float)(var5 + this.zOffset)));
            break;
         case COLOR:
            this.rawIntBuffer.put(var12, this.field_179007_h);
            break;
         case UV:
            if (var10.func_177369_e() == 0) {
               this.rawIntBuffer.put(var12, Float.floatToRawIntBits((float)this.field_178998_e));
               this.rawIntBuffer.put(var12 + 1, Float.floatToRawIntBits((float)this.field_178995_f));
            } else {
               this.rawIntBuffer.put(var12, this.field_178996_g);
            }
            break;
         case NORMAL:
            this.rawIntBuffer.put(var12, this.field_179003_o);
         }
      }

      this.rawBufferIndex += this.vertexFormat.func_177338_f() >> 2;
      ++this.vertexCount;
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertex(this);
      }

   }

   public void setTextureUV(double var1, double var3) {
      if (!this.vertexFormat.func_177347_a(0) && !this.vertexFormat.func_177347_a(1)) {
         VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2);
         this.vertexFormat.func_177349_a(var5);
      }

      this.field_178998_e = var1;
      this.field_178995_f = var3;
   }

   public void func_178974_a(int var1, int var2) {
      int var3 = var1 >> 16 & 255;
      int var4 = var1 >> 8 & 255;
      int var5 = var1 & 255;
      this.func_178961_b(var3, var4, var5, var2);
   }

   public void startDrawingQuads() {
      this.startDrawing(7);
   }

   public void func_178960_a(float var1, float var2, float var3, float var4) {
      this.func_178961_b((int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F), (int)(var4 * 255.0F));
   }

   private int getBufferQuadSize() {
      int var1 = this.rawIntBuffer.capacity() * 4 / (this.vertexFormat.func_177338_f() * 4);
      return var1;
   }

   public WorldRenderer.State getVertexState(float var1, float var2, float var3) {
      int[] var4 = new int[this.rawBufferIndex];
      PriorityQueue var5 = new PriorityQueue(this.rawBufferIndex, new QuadComparator(this.rawFloatBuffer, (float)((double)var1 + this.xOffset), (float)((double)var2 + this.yOffset), (float)((double)var3 + this.zOffset), this.vertexFormat.func_177338_f() / 4));
      int var6 = this.vertexFormat.func_177338_f();
      TextureAtlasSprite[] var7 = null;
      int var8 = this.vertexFormat.func_177338_f() / 4 * 4;
      if (this.quadSprites != null) {
         var7 = new TextureAtlasSprite[this.vertexCount / 4];
      }

      int var9;
      for(var9 = 0; var9 < this.rawBufferIndex; var9 += var6) {
         var5.add(var9);
      }

      for(var9 = 0; !var5.isEmpty(); var9 += var6) {
         int var10 = (Integer)var5.remove();

         int var11;
         for(var11 = 0; var11 < var6; ++var11) {
            var4[var9 + var11] = this.rawIntBuffer.get(var10 + var11);
         }

         if (var7 != null) {
            var11 = var10 / var8;
            int var12 = var9 / var8;
            var7[var12] = this.quadSprites[var11];
         }
      }

      this.rawIntBuffer.clear();
      this.rawIntBuffer.put(var4);
      if (this.quadSprites != null) {
         System.arraycopy(var7, 0, this.quadSprites, 0, var7.length);
      }

      return new WorldRenderer.State(this, var4, this.rawBufferIndex, this.vertexCount, new VertexFormat(this.vertexFormat), var7);
   }

   public VertexFormat func_178973_g() {
      return this.vertexFormat;
   }

   public void func_178961_b(int var1, int var2, int var3, int var4) {
      if (!this.needsUpdate) {
         if (var1 > 255) {
            var1 = 255;
         }

         if (var2 > 255) {
            var2 = 255;
         }

         if (var3 > 255) {
            var3 = 255;
         }

         if (var4 > 255) {
            var4 = 255;
         }

         if (var1 < 0) {
            var1 = 0;
         }

         if (var2 < 0) {
            var2 = 0;
         }

         if (var3 < 0) {
            var3 = 0;
         }

         if (var4 < 0) {
            var4 = 0;
         }

         if (!this.vertexFormat.func_177346_d()) {
            VertexFormatElement var5 = new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.COLOR, 4);
            this.vertexFormat.func_177349_a(var5);
         }

         if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
            this.field_179007_h = var4 << 24 | var3 << 16 | var2 << 8 | var1;
         } else {
            this.field_179007_h = var1 << 24 | var2 << 16 | var3 << 8 | var4;
         }
      }

   }

   public void setPosition(int var1, int var2, int var3) {
      this.func_178961_b(var1, var2, var3, 255);
   }

   public int func_178989_h() {
      return this.vertexCount;
   }

   public void checkAndGrow() {
      if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
         this.growBuffer(2097152);
      }

   }

   public boolean isColorDisabled() {
      return this.needsUpdate;
   }

   public void drawMultiTexture() {
      if (this.quadSprites != null) {
         int var1 = Config.getMinecraft().getTextureMapBlocks().getCountRegisteredSprites();
         if (this.drawnIcons.length <= var1) {
            this.drawnIcons = new boolean[var1 + 1];
         }

         Arrays.fill(this.drawnIcons, false);
         int var2 = 0;
         int var3 = -1;
         int var4 = this.vertexCount / 4;

         for(int var5 = 0; var5 < var4; ++var5) {
            TextureAtlasSprite var6 = this.quadSprites[var5];
            if (var6 != null) {
               int var7 = var6.getIndexInMap();
               if (!this.drawnIcons[var7]) {
                  if (var6 == TextureUtils.iconGrassSideOverlay) {
                     if (var3 < 0) {
                        var3 = var5;
                     }
                  } else {
                     var5 = this.drawForIcon(var6, var5) - 1;
                     ++var2;
                     if (this.blockLayer != EnumWorldBlockLayer.TRANSLUCENT) {
                        this.drawnIcons[var7] = true;
                     }
                  }
               }
            }
         }

         if (var3 >= 0) {
            this.drawForIcon(TextureUtils.iconGrassSideOverlay, var3);
            ++var2;
         }

         if (var2 > 0) {
         }
      }

   }

   public void func_178975_e(float var1, float var2, float var3) {
      byte var4 = (byte)((int)(var1 * 127.0F));
      byte var5 = (byte)((int)(var2 * 127.0F));
      byte var6 = (byte)((int)(var3 * 127.0F));
      int var7 = this.vertexFormat.func_177338_f() >> 2;
      int var8 = (this.vertexCount - 4) * var7 + this.vertexFormat.func_177342_c() / 4;
      this.field_179003_o = var4 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
      this.rawIntBuffer.put(var8, this.field_179003_o);
      this.rawIntBuffer.put(var8 + var7, this.field_179003_o);
      this.rawIntBuffer.put(var8 + var7 * 2, this.field_179003_o);
      this.rawIntBuffer.put(var8 + var7 * 3, this.field_179003_o);
   }

   public void func_178968_d(int var1) {
      for(int var2 = 0; var2 < 4; ++var2) {
         this.func_178988_b(var1, var2 + 1);
      }

   }

   public void setSprite(TextureAtlasSprite var1) {
      if (this.quadSprites != null) {
         this.quadSprite = var1;
      }

   }

   private void growBuffer(int var1) {
      if (Config.isShaders()) {
         var1 *= 2;
      }

      LogManager.getLogger().warn(String.valueOf((new StringBuilder("Needed to grow BufferBuilder buffer: Old size ")).append(this.bufferSize * 4).append(" bytes, new size ").append(this.bufferSize * 4 + var1).append(" bytes.")));
      this.bufferSize += var1 / 4;
      ByteBuffer var2 = GLAllocation.createDirectByteBuffer(this.bufferSize * 4);
      this.rawIntBuffer.position(0);
      var2.asIntBuffer().put(this.rawIntBuffer);
      this.byteBuffer = var2;
      this.rawIntBuffer = this.byteBuffer.asIntBuffer();
      this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
      if (this.quadSprites != null) {
         TextureAtlasSprite[] var3 = this.quadSprites;
         int var4 = this.getBufferQuadSize();
         this.quadSprites = new TextureAtlasSprite[var4];
         System.arraycopy(var3, 0, this.quadSprites, 0, Math.min(var3.length, this.quadSprites.length));
         this.quadSpritesPrev = null;
      }

   }

   public void func_178994_b(float var1, float var2, float var3, int var4) {
      int var5 = this.getGLCallListForPass(var4);
      int var6 = MathHelper.clamp_int((int)(var1 * 255.0F), 0, 255);
      int var7 = MathHelper.clamp_int((int)(var2 * 255.0F), 0, 255);
      int var8 = MathHelper.clamp_int((int)(var3 * 255.0F), 0, 255);
      this.func_178972_a(var5, var6, var7, var8, 255);
   }

   public void func_178990_f(float var1, float var2, float var3) {
      for(int var4 = 0; var4 < 4; ++var4) {
         this.func_178994_b(var1, var2, var3, var4 + 1);
      }

   }

   public void markDirty() {
      this.needsUpdate = true;
   }

   private void func_178988_b(int var1, int var2) {
      int var3 = this.getGLCallListForPass(var2);
      int var4 = var1 >> 16 & 255;
      int var5 = var1 >> 8 & 255;
      int var6 = var1 & 255;
      int var7 = var1 >> 24 & 255;
      this.func_178972_a(var3, var4, var5, var6, var7);
   }

   public void func_178980_d(float var1, float var2, float var3) {
      if (!this.vertexFormat.func_177350_b()) {
         VertexFormatElement var4 = new VertexFormatElement(0, VertexFormatElement.EnumType.BYTE, VertexFormatElement.EnumUseage.NORMAL, 3);
         this.vertexFormat.func_177349_a(var4);
         this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.UBYTE, VertexFormatElement.EnumUseage.PADDING, 1));
      }

      byte var7 = (byte)((int)(var1 * 127.0F));
      byte var5 = (byte)((int)(var2 * 127.0F));
      byte var6 = (byte)((int)(var3 * 127.0F));
      this.field_179003_o = var7 & 255 | (var5 & 255) << 8 | (var6 & 255) << 16;
   }

   public void func_178963_b(int var1) {
      if (!this.vertexFormat.func_177347_a(1)) {
         if (!this.vertexFormat.func_177347_a(0)) {
            this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.UV, 2));
         }

         VertexFormatElement var2 = new VertexFormatElement(1, VertexFormatElement.EnumType.SHORT, VertexFormatElement.EnumUseage.UV, 2);
         this.vertexFormat.func_177349_a(var2);
      }

      this.field_178996_g = var1;
   }

   public void startDrawing(int var1) {
      if (this.isDrawing) {
         throw new IllegalStateException("Already building!");
      } else {
         this.isDrawing = true;
         this.reset();
         this.drawMode = var1;
         this.needsUpdate = false;
      }
   }

   public void func_178972_a(int var1, int var2, int var3, int var4, int var5) {
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         this.rawIntBuffer.put(var1, var5 << 24 | var4 << 16 | var3 << 8 | var2);
      } else {
         this.rawIntBuffer.put(var1, var2 << 24 | var3 << 16 | var4 << 8 | var5);
      }

   }

   public void setVertexFormat(VertexFormat var1) {
      this.vertexFormat = new VertexFormat(var1);
      if (Config.isShaders()) {
         SVertexBuilder.endSetVertexFormat(this);
      }

   }

   public void func_178978_a(float var1, float var2, float var3, int var4) {
      int var5 = this.getGLCallListForPass(var4);
      int var6 = this.rawIntBuffer.get(var5);
      int var7;
      int var8;
      int var9;
      if (ByteOrder.nativeOrder() == ByteOrder.LITTLE_ENDIAN) {
         var7 = (int)((float)(var6 & 255) * var1);
         var8 = (int)((float)(var6 >> 8 & 255) * var2);
         var9 = (int)((float)(var6 >> 16 & 255) * var3);
         var6 &= -16777216;
         var6 |= var9 << 16 | var8 << 8 | var7;
      } else {
         var7 = (int)((float)(this.field_179007_h >> 24 & 255) * var1);
         var8 = (int)((float)(this.field_179007_h >> 16 & 255) * var2);
         var9 = (int)((float)(this.field_179007_h >> 8 & 255) * var3);
         var6 &= 255;
         var6 |= var7 << 24 | var8 << 16 | var9 << 8;
      }

      if (this.needsUpdate) {
         var6 = -1;
      }

      this.rawIntBuffer.put(var5, var6);
   }

   public void addVertexWithUV(double var1, double var3, double var5, double var7, double var9) {
      if (this.quadSprite != null && this.quadSprites != null) {
         var7 = (double)this.quadSprite.toSingleU((float)var7);
         var9 = (double)this.quadSprite.toSingleV((float)var9);
         this.quadSprites[this.vertexCount / 4] = this.quadSprite;
      }

      this.setTextureUV(var7, var9);
      this.addVertex(var1, var3, var5);
   }

   public void func_178982_a(byte var1, byte var2, byte var3) {
      this.setPosition(var1 & 255, var2 & 255, var3 & 255);
   }

   public void func_178986_b(float var1, float var2, float var3) {
      this.setPosition((int)(var1 * 255.0F), (int)(var2 * 255.0F), (int)(var3 * 255.0F));
   }

   public void setBlockLayer(EnumWorldBlockLayer var1) {
      this.blockLayer = var1;
      if (var1 == null) {
         if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
         this.quadSprite = null;
      }

   }

   public boolean isMultiTexture() {
      return this.quadSprites != null;
   }

   public int getDrawMode() {
      return this.drawMode;
   }

   public void reset() {
      this.vertexCount = 0;
      this.rawBufferIndex = 0;
      this.vertexFormat.clear();
      this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
      if (this.blockLayer != null && Config.isMultiTexture()) {
         if (this.quadSprites == null) {
            this.quadSprites = this.quadSpritesPrev;
         }

         if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
            this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
         }
      } else {
         if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
      }

      this.quadSprite = null;
   }

   public void func_178981_a(int[] var1) {
      if (Config.isShaders()) {
         SVertexBuilder.beginAddVertexData(this, var1);
      }

      int var2 = this.vertexFormat.func_177338_f() / 4;
      this.vertexCount += var1.length / var2;
      this.rawIntBuffer.position(this.rawBufferIndex);
      this.rawIntBuffer.put(var1);
      this.rawBufferIndex += var1.length;
      if (Config.isShaders()) {
         SVertexBuilder.endAddVertexData(this);
      }

   }

   public int draw() {
      if (!this.isDrawing) {
         throw new IllegalStateException("Not building!");
      } else {
         this.isDrawing = false;
         if (this.vertexCount > 0) {
            this.byteBuffer.position(0);
            this.byteBuffer.limit(this.rawBufferIndex * 4);
         }

         this.field_179012_p = this.rawBufferIndex * 4;
         return this.field_179012_p;
      }
   }

   public int func_178976_e() {
      return this.field_179012_p;
   }

   public void func_178991_c(int var1) {
      int var2 = var1 >> 16 & 255;
      int var3 = var1 >> 8 & 255;
      int var4 = var1 & 255;
      this.setPosition(var2, var3, var4);
   }

   public int getGLCallListForPass(int var1) {
      return ((this.vertexCount - var1) * this.vertexFormat.func_177338_f() + this.vertexFormat.func_177340_e()) / 4;
   }

   private int drawForIcon(TextureAtlasSprite var1, int var2) {
      GL11.glBindTexture(3553, var1.glSpriteTextureId);
      int var3 = -1;
      int var4 = -1;
      int var5 = this.vertexCount / 4;

      for(int var6 = var2; var6 < var5; ++var6) {
         TextureAtlasSprite var7 = this.quadSprites[var6];
         if (var7 == var1) {
            if (var4 < 0) {
               var4 = var6;
            }
         } else if (var4 >= 0) {
            this.draw(var4, var6);
            if (this.blockLayer == EnumWorldBlockLayer.TRANSLUCENT) {
               return var6;
            }

            var4 = -1;
            if (var3 < 0) {
               var3 = var6;
            }
         }
      }

      if (var4 >= 0) {
         this.draw(var4, var5);
      }

      if (var3 < 0) {
         var3 = var5;
      }

      return var3;
   }

   public ByteBuffer func_178966_f() {
      return this.byteBuffer;
   }

   public WorldRenderer(int var1) {
      if (Config.isShaders()) {
         var1 *= 2;
      }

      this.bufferSize = var1;
      this.byteBuffer = GLAllocation.createDirectByteBuffer(var1 * 4);
      this.rawIntBuffer = this.byteBuffer.asIntBuffer();
      this.rawFloatBuffer = this.byteBuffer.asFloatBuffer();
      this.vertexFormat = new VertexFormat();
      this.vertexFormat.func_177349_a(new VertexFormatElement(0, VertexFormatElement.EnumType.FLOAT, VertexFormatElement.EnumUseage.POSITION, 3));
      SVertexBuilder.initVertexBuilder(this);
   }

   public void func_178962_a(int var1, int var2, int var3, int var4) {
      int var5 = (this.vertexCount - 4) * (this.vertexFormat.func_177338_f() / 4) + this.vertexFormat.func_177344_b(1) / 4;
      int var6 = this.vertexFormat.func_177338_f() >> 2;
      this.rawIntBuffer.put(var5, var1);
      this.rawIntBuffer.put(var5 + var6, var2);
      this.rawIntBuffer.put(var5 + var6 * 2, var3);
      this.rawIntBuffer.put(var5 + var6 * 3, var4);
   }

   private void draw(int var1, int var2) {
      int var3 = var2 - var1;
      if (var3 > 0) {
         int var4 = var1 * 4;
         int var5 = var3 * 4;
         GL11.glDrawArrays(this.drawMode, var4, var5);
      }

   }

   public void setTranslation(double var1, double var3, double var5) {
      this.xOffset = var1;
      this.yOffset = var3;
      this.zOffset = var5;
   }

   public void putSprite(TextureAtlasSprite var1) {
      if (this.quadSprites != null) {
         int var2 = this.vertexCount / 4;
         this.quadSprites[var2 - 1] = var1;
      }

   }

   public void func_178987_a(double var1, double var3, double var5) {
      if (this.rawBufferIndex >= this.bufferSize - this.vertexFormat.func_177338_f()) {
         this.growBuffer(2097152);
      }

      int var7 = this.vertexFormat.func_177338_f() / 4;
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

   public void setVertexState(WorldRenderer.State var1) {
      if (var1.func_179013_a().length > this.rawIntBuffer.capacity()) {
         this.growBuffer(2097152);
      }

      this.rawIntBuffer.clear();
      this.rawIntBuffer.put(var1.func_179013_a());
      this.rawBufferIndex = var1.getRawBufferIndex();
      this.vertexCount = var1.getVertexCount();
      this.vertexFormat = new VertexFormat(var1.func_179016_d());
      if (var1.stateQuadSprites != null) {
         if (this.quadSprites == null) {
            this.quadSprites = this.quadSpritesPrev;
         }

         if (this.quadSprites == null || this.quadSprites.length < this.getBufferQuadSize()) {
            this.quadSprites = new TextureAtlasSprite[this.getBufferQuadSize()];
         }

         System.arraycopy(var1.stateQuadSprites, 0, this.quadSprites, 0, var1.stateQuadSprites.length);
      } else {
         if (this.quadSprites != null) {
            this.quadSpritesPrev = this.quadSprites;
         }

         this.quadSprites = null;
      }

   }

   public class State {
      private final int field_179020_c;
      private final int field_179017_d;
      private static final String __OBFID = "CL_00002568";
      private final VertexFormat field_179018_e;
      public TextureAtlasSprite[] stateQuadSprites;
      final WorldRenderer this$0;
      private final int[] field_179019_b;

      public int[] func_179013_a() {
         return this.field_179019_b;
      }

      public int getRawBufferIndex() {
         return this.field_179020_c;
      }

      public State(WorldRenderer var1, int[] var2, int var3, int var4, VertexFormat var5) {
         this.this$0 = var1;
         this.field_179019_b = var2;
         this.field_179020_c = var3;
         this.field_179017_d = var4;
         this.field_179018_e = var5;
      }

      public VertexFormat func_179016_d() {
         return this.field_179018_e;
      }

      public State(WorldRenderer var1, int[] var2, int var3, int var4, VertexFormat var5, TextureAtlasSprite[] var6) {
         this.this$0 = var1;
         this.field_179019_b = var2;
         this.field_179020_c = var3;
         this.field_179017_d = var4;
         this.field_179018_e = var5;
         this.stateQuadSprites = var6;
      }

      public int getVertexCount() {
         return this.field_179017_d;
      }
   }

   static final class SwitchEnumUseage {
      static final int[] field_178959_a = new int[VertexFormatElement.EnumUseage.values().length];

      static {
         try {
            field_178959_a[VertexFormatElement.EnumUseage.POSITION.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178959_a[VertexFormatElement.EnumUseage.COLOR.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178959_a[VertexFormatElement.EnumUseage.UV.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178959_a[VertexFormatElement.EnumUseage.NORMAL.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
