package net.minecraft.client.model;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.TextureOffset;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.optifine.ModelSprite;
import org.lwjgl.opengl.GL11;

public class ModelRenderer {
   public float textureWidth;
   public float textureHeight;
   private int textureOffsetX;
   private int textureOffsetY;
   public float rotationPointX;
   public float rotationPointY;
   public float rotationPointZ;
   public float rotateAngleX;
   public float rotateAngleY;
   public float rotateAngleZ;
   private boolean compiled;
   private int displayList;
   public boolean mirror;
   public boolean showModel;
   public boolean isHidden;
   public List cubeList;
   public List childModels;
   public final String boxName;
   private final ModelBase baseModel;
   public float offsetX;
   public float offsetY;
   public float offsetZ;
   private static final String h = "CL_00000874";
   public List spriteList;
   public boolean mirrorV;
   float savedScale;

   public ModelRenderer(ModelBase var1, String var2) {
      this.spriteList = new ArrayList();
      this.mirrorV = false;
      this.textureWidth = 64.0F;
      this.textureHeight = 32.0F;
      this.showModel = true;
      this.cubeList = Lists.newArrayList();
      this.baseModel = var1;
      var1.boxList.add(this);
      this.boxName = var2;
      this.setTextureSize(var1.textureWidth, var1.textureHeight);
   }

   public ModelRenderer(ModelBase var1) {
      this(var1, (String)null);
   }

   public ModelRenderer(ModelBase var1, int var2, int var3) {
      this(var1);
      this.setTextureOffset(var2, var3);
   }

   public void addChild(ModelRenderer var1) {
      if(this.childModels == null) {
         this.childModels = Lists.newArrayList();
      }

      this.childModels.add(var1);
   }

   public ModelRenderer setTextureOffset(int var1, int var2) {
      this.textureOffsetX = var1;
      this.textureOffsetY = var2;
      return this;
   }

   public ModelRenderer addBox(String var1, float var2, float var3, float var4, int var5, int var6, int var7) {
      var1 = this.boxName + "." + var1;
      TextureOffset var8 = this.baseModel.getTextureOffset(var1);
      this.setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
      this.cubeList.add((new ModelBox(this, this.textureOffsetX, this.textureOffsetY, var2, var3, var4, var5, var6, var7, 0.0F)).setBoxName(var1));
      return this;
   }

   public ModelRenderer addBox(float var1, float var2, float var3, int var4, int var5, int var6) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, var1, var2, var3, var4, var5, var6, 0.0F));
      return this;
   }

   public ModelRenderer addBox(float var1, float var2, float var3, int var4, int var5, int var6, boolean var7) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, var1, var2, var3, var4, var5, var6, 0.0F, var7));
      return this;
   }

   public void addBox(float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, var1, var2, var3, var4, var5, var6, var7));
   }

   public void setRotationPoint(float var1, float var2, float var3) {
      this.rotationPointX = var1;
      this.rotationPointY = var2;
      this.rotationPointZ = var3;
   }

   public void render(float var1) {
      if(!this.isHidden && this.showModel) {
         if(!this.compiled) {
            this.compileDisplayList(var1);
         }

         GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
         if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
            if(this.rotationPointX == 0.0F && this.rotationPointY == 0.0F && this.rotationPointZ == 0.0F) {
               GlStateManager.callList(this.displayList);
               if(this.childModels != null) {
                  for(Object var7 : this.childModels) {
                     ((ModelRenderer)var7).render(var1);
                  }
               }
            } else {
               GlStateManager.translate(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
               GlStateManager.callList(this.displayList);
               if(this.childModels != null) {
                  for(Object var6 : this.childModels) {
                     ((ModelRenderer)var6).render(var1);
                  }
               }

               GlStateManager.translate(-this.rotationPointX * var1, -this.rotationPointY * var1, -this.rotationPointZ * var1);
            }
         } else {
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
            this.rotateHand();
            GlStateManager.callList(this.displayList);
            if(this.childModels != null) {
               for(Object var3 : this.childModels) {
                  ((ModelRenderer)var3).render(var1);
               }
            }

            GlStateManager.popMatrix();
         }

         GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
      }

   }

   private void rotateHand() {
      if(this.rotateAngleZ != 0.0F) {
         GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
      }

      this.renderHand0();
   }

   private void renderHand0() {
      float var1 = 57.295776F;
      if(this.rotateAngleY != 0.0F) {
         GlStateManager.rotate(this.rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
      }

      if(this.rotateAngleX != 0.0F) {
         GlStateManager.rotate(this.rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
      }

   }

   public void renderWithRotation(float var1) {
      if(!this.isHidden && this.showModel) {
         if(!this.compiled) {
            this.compileDisplayList(var1);
         }

         GlStateManager.pushMatrix();
         GlStateManager.translate(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
         this.renderHand0();
         if(this.rotateAngleZ != 0.0F) {
            GlStateManager.rotate(this.rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
         }

         GlStateManager.callList(this.displayList);
         GlStateManager.popMatrix();
      }

   }

   public void postRender(float var1) {
      if(!this.isHidden && this.showModel) {
         if(!this.compiled) {
            this.compileDisplayList(var1);
         }

         if(this.rotateAngleX == 0.0F && this.rotateAngleY == 0.0F && this.rotateAngleZ == 0.0F) {
            if(this.rotationPointX != 0.0F || this.rotationPointY != 0.0F || this.rotationPointZ != 0.0F) {
               GlStateManager.translate(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
            }
         } else {
            GlStateManager.translate(this.rotationPointX * var1, this.rotationPointY * var1, this.rotationPointZ * var1);
            this.rotateHand();
         }
      }

   }

   private void compileDisplayList(float var1) {
      if(this.displayList == 0) {
         this.savedScale = var1;
         this.displayList = GLAllocation.generateDisplayLists(1);
      }

      GL11.glNewList(this.displayList, 4864);
      WorldRenderer var2 = Tessellator.getInstance().getWorldRenderer();

      for(Object var4 : this.cubeList) {
         ((ModelBox)var4).render(var2, var1);
      }

      for(Object var7 : this.spriteList) {
         ModelSprite var5 = (ModelSprite)var7;
         var5.render(Tessellator.getInstance(), var1);
      }

      GL11.glEndList();
      this.compiled = true;
   }

   public ModelRenderer setTextureSize(int var1, int var2) {
      this.textureWidth = (float)var1;
      this.textureHeight = (float)var2;
      return this;
   }

   public void addSprite(float var1, float var2, float var3, int var4, int var5, int var6, float var7) {
      this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, var1, var2, var3, var4, var5, var6, var7));
   }

   public boolean getCompiled() {
      return this.compiled;
   }

   public int getDisplayList() {
      return this.displayList;
   }

   public void resetDisplayList() {
      if(this.compiled) {
         this.compiled = false;
         this.compileDisplayList(this.savedScale);
      }

   }
}
