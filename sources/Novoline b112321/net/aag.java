package net;

import net.a87;
import net.acE;
import net.gL;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import org.lwjgl.opengl.GL11;

public class aag extends ModelRenderer {
   public a87 F = new a87();
   public a87 B = new a87();
   public float K;
   public float A;
   public float C;
   public int G;
   public int D;
   public boolean J;
   private int E;
   public boolean I = false;
   private static acE[] H;

   public aag(ModelBase var1) {
      super(var1);
      this.K = this.A = this.C = 1.0F;
   }

   public aag(ModelBase var1, String var2) {
      super(var1, var2);
      this.K = this.A = this.C = 1.0F;
   }

   public aag(ModelBase var1, int var2, int var3) {
      super(var1, var2, var3);
      this.G = var2;
      this.D = var3;
      this.K = this.A = this.C = 1.0F;
   }

   public void g(float var1) {
      this.rotateAngleX = (float)((double)(this.F.c() / 180.0F) * 3.141592653589793D);
      this.rotateAngleY = (float)((double)(this.F.d() / 180.0F) * 3.141592653589793D);
      this.rotateAngleZ = (float)((double)(this.F.e() / 180.0F) * 3.141592653589793D);
   }

   public aag a(boolean var1) {
      this.I = var1;
      return this;
   }

   public void f(float var1) {
      this.E = GLAllocation.generateDisplayLists(1);
      c();
      GL11.glNewList(this.E, 4864);
      WorldRenderer var3 = Tessellator.getInstance().getWorldRenderer();
      int var4 = 0;
      if(var4 < this.cubeList.size()) {
         ((ModelBox)this.cubeList.get(var4)).render(var3, var1);
         ++var4;
      }

      GL11.glEndList();
      this.J = true;
   }

   public void render(float var1) {
      c();
      this.g(var1);
      if(!this.J) {
         this.f(var1);
      }

      label23: {
         GL11.glTranslatef(this.offsetX, this.offsetY, this.offsetZ);
         if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
            if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
               GL11.glRotatef(-this.B.d(), 0.0F, 1.0F, 0.0F);
               GL11.glRotatef(this.B.c(), 1.0F, 0.0F, 0.0F);
               GL11.glRotatef(this.B.e(), 0.0F, 0.0F, 1.0F);
               GL11.glScalef(this.K, this.A, this.C);
               if(!this.isHidden & this.showModel) {
                  GL11.glCallList(this.E);
               }

               if(!this.I && !(!this.isHidden & this.showModel) || this.childModels == null) {
                  break label23;
               }

               int var3 = 0;
               if(var3 >= this.childModels.size()) {
                  break label23;
               }

               ((ModelRenderer)this.childModels.get(var3)).render(var1);
               ++var3;
            }

            GL11.glTranslatef(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
            GL11.glRotatef(-this.B.d(), 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(this.B.c(), 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(this.B.e(), 0.0F, 0.0F, 1.0F);
            GL11.glScalef(this.K, this.A, this.C);
            if(!this.isHidden & this.showModel) {
               GL11.glCallList(this.E);
            }

            if((this.I || !this.isHidden & this.showModel) && this.childModels != null) {
               int var5 = 0;
               if(var5 < this.childModels.size()) {
                  ((ModelRenderer)this.childModels.get(var5)).render(var1);
                  ++var5;
               }
            }

            GL11.glTranslatef(-this.rotationPointX * var1, -this.rotationPointY * var1, -this.rotationPointZ * var1);
         }

         GL11.glPushMatrix();
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

         GL11.glScalef(this.K, this.A, this.C);
         if(!this.isHidden & this.showModel) {
            GL11.glCallList(this.E);
         }

         if((this.I || !this.isHidden & this.showModel) && this.childModels != null) {
            int var7 = 0;
            if(var7 < this.childModels.size()) {
               ((ModelRenderer)this.childModels.get(var7)).render(var1);
               ++var7;
            }
         }

         GL11.glPopMatrix();
      }

      GL11.glTranslatef(-this.offsetX, -this.offsetY, -this.offsetZ);
   }

   public void e(float var1) {
      this.F.a(var1);
      this.B.a(var1);
      this.g(var1);
   }

   public void renderWithRotation(float var1) {
      this.g(var1);
      super.renderWithRotation(var1);
   }

   public void postRender(float var1) {
      c();
      this.g(var1);
      if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
         if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
            return;
         }

         GL11.glTranslatef(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
         GL11.glRotatef(-this.B.d(), 0.0F, 1.0F, 0.0F);
         GL11.glRotatef(this.B.c(), 1.0F, 0.0F, 0.0F);
         GL11.glRotatef(this.B.e(), 0.0F, 0.0F, 1.0F);
         GL11.glScalef(this.K, this.A, this.C);
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

      GL11.glScalef(this.K, this.A, this.C);
   }

   public aag a(float var1, float var2, float var3) {
      this.rotationPointX = var1;
      this.rotationPointY = var2;
      this.rotationPointZ = var3;
      return this;
   }

   public aag c(float var1, float var2, float var3) {
      this.offsetX = var1;
      this.offsetY = var2;
      this.offsetZ = var3;
      return this;
   }

   public aag d(float var1, float var2, float var3) {
      this.K = var1;
      this.A = var2;
      this.C = var3;
      return this;
   }

   public aag d() {
      this.K = this.A = this.C = 1.0F;
      return this;
   }

   public void a(aag var1) {
      acE[] var2 = c();
      if(var1 != null) {
         this.rotateAngleX = var1.rotateAngleX;
         this.rotateAngleY = var1.rotateAngleY;
         this.rotateAngleZ = var1.rotateAngleZ;
         this.F.e.set(var1.F.e);
         this.F.f.set(var1.F.f);
         this.F.b.set(var1.F.b);
         this.F.a.set(var1.F.a);
         this.F.d.set(var1.F.d);
         this.B.e.set(var1.B.e);
         this.B.f.set(var1.B.f);
         this.B.b.set(var1.B.b);
         this.B.a.set(var1.B.a);
         this.B.d.set(var1.B.d);
         this.K = var1.K;
         this.A = var1.A;
         this.C = var1.C;
      }

   }

   public void addBox(float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      this.cubeList.add(new gL(this, this.G, this.D, var1, var2, var3, var4, var5, var6, var7));
   }

   public gL a() {
      return (gL)this.cubeList.get(0);
   }

   public aag b(float var1, float var2, float var3) {
      this.a().b(var1, var2, var3);
      return this;
   }

   public aag f(float var1, float var2, float var3) {
      this.a().c(var1, var2, var3);
      return this;
   }

   public aag e(float var1, float var2, float var3) {
      this.a().a(var1, var2, var3);
      return this;
   }

   public aag b() {
      this.a().a(this);
      this.J = false;
      return this;
   }

   public static void b(acE[] var0) {
      H = var0;
   }

   public static acE[] c() {
      return H;
   }

   static {
      if(c() != null) {
         b(new acE[5]);
      }

   }
}
