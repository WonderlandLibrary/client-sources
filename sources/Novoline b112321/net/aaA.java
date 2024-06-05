package net;

import net.aag;
import net.minecraft.client.model.ModelBase;
import org.lwjgl.opengl.GL11;

public class aaA extends aag {
   aag M;
   aag L;

   public aaA(ModelBase var1) {
      super(var1);
   }

   public aaA(ModelBase var1, String var2) {
      super(var1, var2);
   }

   public aaA(ModelBase var1, int var2, int var3) {
      super(var1, var2, var3);
   }

   public aaA a(aag var1) {
      this.M = var1;
      return this;
   }

   public aaA b(aag var1) {
      this.L = var1;
      return this;
   }

   public void postRender(float var1) {
      aag.c();
      this.g(var1);
      this.M.postRender(var1);
      if(!this.isHidden && this.showModel) {
         label35: {
            if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
               if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
                  break label35;
               }

               GL11.glTranslatef(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
               GL11.glRotatef(-this.B.d(), 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(this.B.c(), 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(this.B.e(), 0.0F, 0.0F, 1.0F);
            }

            GL11.glTranslatef(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
            GL11.glRotatef(-this.B.d(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.B.c(), 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(this.B.e(), 0.0F, 0.0F, 1.0F);
            if(this.rotateAngleZ != 0.0F) {
               GL11.glRotatef(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
            }

            if(this.rotateAngleY != 0.0F) {
               GL11.glRotatef(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
            }

            if(this.rotateAngleX != 0.0F) {
               GL11.glRotatef(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
            }
         }
      }

      this.L.postRender(var1);
      GL11.glTranslatef(-this.L.rotationPointX * var1, -this.L.rotationPointY * var1, -this.L.rotationPointZ * var1);
   }
}
