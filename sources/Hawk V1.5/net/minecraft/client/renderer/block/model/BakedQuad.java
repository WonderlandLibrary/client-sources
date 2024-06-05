package net.minecraft.client.renderer.block.model;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.pipeline.IVertexConsumer;
import net.minecraftforge.client.model.pipeline.IVertexProducer;
import optifine.Config;
import optifine.Reflector;

public class BakedQuad implements IVertexProducer {
   private int[] vertexDataSingle = null;
   private TextureAtlasSprite sprite = null;
   private static final String __OBFID = "CL_00002512";
   protected int[] field_178215_a;
   protected final int field_178213_b;
   protected final EnumFacing face;

   public BakedQuad(int[] var1, int var2, EnumFacing var3) {
      this.field_178215_a = var1;
      this.field_178213_b = var2;
      this.face = var3;
      this.fixVertexData();
   }

   private void fixVertexData() {
      if (Config.isShaders()) {
         if (this.field_178215_a.length == 28) {
            this.field_178215_a = expandVertexData(this.field_178215_a);
         }
      } else if (this.field_178215_a.length == 56) {
         this.field_178215_a = compactVertexData(this.field_178215_a);
      }

   }

   public boolean func_178212_b() {
      return this.field_178213_b != -1;
   }

   public int[] getVertexDataSingle() {
      if (this.vertexDataSingle == null) {
         this.vertexDataSingle = makeVertexDataSingle(this.func_178209_a(), this.getSprite());
      }

      return this.vertexDataSingle;
   }

   public String toString() {
      return String.valueOf((new StringBuilder("vertex: ")).append(this.field_178215_a.length / 7).append(", tint: ").append(this.field_178213_b).append(", facing: ").append(this.face).append(", sprite: ").append(this.sprite));
   }

   private static int[] compactVertexData(int[] var0) {
      int var1 = var0.length / 4;
      int var2 = var1 / 2;
      int[] var3 = new int[var2 * 4];

      for(int var4 = 0; var4 < 4; ++var4) {
         System.arraycopy(var0, var4 * var1, var3, var4 * var2, var2);
      }

      return var3;
   }

   public EnumFacing getFace() {
      return this.face;
   }

   private static int[] makeVertexDataSingle(int[] var0, TextureAtlasSprite var1) {
      int[] var2 = (int[])var0.clone();
      int var3 = var1.sheetWidth / var1.getIconWidth();
      int var4 = var1.sheetHeight / var1.getIconHeight();
      int var5 = var2.length / 4;

      for(int var6 = 0; var6 < 4; ++var6) {
         int var7 = var6 * var5;
         float var8 = Float.intBitsToFloat(var2[var7 + 4]);
         float var9 = Float.intBitsToFloat(var2[var7 + 4 + 1]);
         float var10 = var1.toSingleU(var8);
         float var11 = var1.toSingleV(var9);
         var2[var7 + 4] = Float.floatToRawIntBits(var10);
         var2[var7 + 4 + 1] = Float.floatToRawIntBits(var11);
      }

      return var2;
   }

   public BakedQuad(int[] var1, int var2, EnumFacing var3, TextureAtlasSprite var4) {
      this.field_178215_a = var1;
      this.field_178213_b = var2;
      this.face = var3;
      this.sprite = var4;
      this.fixVertexData();
   }

   public TextureAtlasSprite getSprite() {
      if (this.sprite == null) {
         this.sprite = getSpriteByUv(this.func_178209_a());
      }

      return this.sprite;
   }

   public int func_178211_c() {
      return this.field_178213_b;
   }

   private static TextureAtlasSprite getSpriteByUv(int[] var0) {
      float var1 = 1.0F;
      float var2 = 1.0F;
      float var3 = 0.0F;
      float var4 = 0.0F;
      int var5 = var0.length / 4;

      for(int var6 = 0; var6 < 4; ++var6) {
         int var7 = var6 * var5;
         float var8 = Float.intBitsToFloat(var0[var7 + 4]);
         float var9 = Float.intBitsToFloat(var0[var7 + 4 + 1]);
         var1 = Math.min(var1, var8);
         var2 = Math.min(var2, var9);
         var3 = Math.max(var3, var8);
         var4 = Math.max(var4, var9);
      }

      float var10 = (var1 + var3) / 2.0F;
      float var11 = (var2 + var4) / 2.0F;
      TextureAtlasSprite var12 = Minecraft.getMinecraft().getTextureMapBlocks().getIconByUV((double)var10, (double)var11);
      return var12;
   }

   public void pipe(IVertexConsumer var1) {
      Reflector.callVoid(Reflector.LightUtil_putBakedQuad, var1, this);
   }

   private static int[] expandVertexData(int[] var0) {
      int var1 = var0.length / 4;
      int var2 = var1 * 2;
      int[] var3 = new int[var2 * 4];

      for(int var4 = 0; var4 < 4; ++var4) {
         System.arraycopy(var0, var4 * var1, var3, var4 * var2, var1);
      }

      return var3;
   }

   public int[] func_178209_a() {
      this.fixVertexData();
      return this.field_178215_a;
   }
}
