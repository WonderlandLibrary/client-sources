package net.minecraft.client.model;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.Vec3;
import optifine.Config;
import shadersmod.client.SVertexBuilder;

public class TexturedQuad {
   private static final String __OBFID = "CL_00000850";
   private boolean invertNormal;
   public PositionTextureVertex[] vertexPositions;
   public int nVertices;

   public void flipFace() {
      PositionTextureVertex[] var1 = new PositionTextureVertex[this.vertexPositions.length];

      for(int var2 = 0; var2 < this.vertexPositions.length; ++var2) {
         var1[var2] = this.vertexPositions[this.vertexPositions.length - var2 - 1];
      }

      this.vertexPositions = var1;
   }

   public TexturedQuad(PositionTextureVertex[] var1, int var2, int var3, int var4, int var5, float var6, float var7) {
      this(var1);
      float var8 = 0.0F / var6;
      float var9 = 0.0F / var7;
      var1[0] = var1[0].setTexturePosition((float)var4 / var6 - var8, (float)var3 / var7 + var9);
      var1[1] = var1[1].setTexturePosition((float)var2 / var6 + var8, (float)var3 / var7 + var9);
      var1[2] = var1[2].setTexturePosition((float)var2 / var6 + var8, (float)var5 / var7 - var9);
      var1[3] = var1[3].setTexturePosition((float)var4 / var6 - var8, (float)var5 / var7 - var9);
   }

   public TexturedQuad(PositionTextureVertex[] var1) {
      this.vertexPositions = var1;
      this.nVertices = var1.length;
   }

   public void func_178765_a(WorldRenderer var1, float var2) {
      Vec3 var3 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[0].vector3D);
      Vec3 var4 = this.vertexPositions[1].vector3D.subtractReverse(this.vertexPositions[2].vector3D);
      Vec3 var5 = var4.crossProduct(var3).normalize();
      var1.startDrawingQuads();
      if (Config.isShaders()) {
         SVertexBuilder.startTexturedQuad(var1);
      }

      if (this.invertNormal) {
         var1.func_178980_d(-((float)var5.xCoord), -((float)var5.yCoord), -((float)var5.zCoord));
      } else {
         var1.func_178980_d((float)var5.xCoord, (float)var5.yCoord, (float)var5.zCoord);
      }

      for(int var6 = 0; var6 < 4; ++var6) {
         PositionTextureVertex var7 = this.vertexPositions[var6];
         var1.addVertexWithUV(var7.vector3D.xCoord * (double)var2, var7.vector3D.yCoord * (double)var2, var7.vector3D.zCoord * (double)var2, (double)var7.texturePositionX, (double)var7.texturePositionY);
      }

      Tessellator.getInstance().draw();
   }
}
