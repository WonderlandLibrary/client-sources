package net.minecraft.client.model;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import optifine.ModelSprite;
import org.lwjgl.opengl.GL11;













public class ModelRenderer
{
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
  private ModelBase baseModel;
  public float offsetX;
  public float offsetY;
  public float offsetZ;
  private static final String __OBFID = "CL_00000874";
  public List spriteList;
  public boolean mirrorV;
  float savedScale;
  
  public ModelRenderer(ModelBase p_i1172_1_, String p_i1172_2_)
  {
    spriteList = new ArrayList();
    mirrorV = false;
    textureWidth = 64.0F;
    textureHeight = 32.0F;
    showModel = true;
    cubeList = Lists.newArrayList();
    baseModel = p_i1172_1_;
    boxList.add(this);
    boxName = p_i1172_2_;
    setTextureSize(textureWidth, textureHeight);
  }
  
  public ModelRenderer(ModelBase p_i1173_1_)
  {
    this(p_i1173_1_, null);
  }
  
  public ModelRenderer(ModelBase p_i46358_1_, int p_i46358_2_, int p_i46358_3_)
  {
    this(p_i46358_1_);
    setTextureOffset(p_i46358_2_, p_i46358_3_);
  }
  



  public void addChild(ModelRenderer p_78792_1_)
  {
    if (childModels == null)
    {
      childModels = Lists.newArrayList();
    }
    
    childModels.add(p_78792_1_);
  }
  
  public ModelRenderer setTextureOffset(int p_78784_1_, int p_78784_2_)
  {
    textureOffsetX = p_78784_1_;
    textureOffsetY = p_78784_2_;
    return this;
  }
  
  public ModelRenderer addBox(String p_78786_1_, float p_78786_2_, float p_78786_3_, float p_78786_4_, int p_78786_5_, int p_78786_6_, int p_78786_7_)
  {
    p_78786_1_ = boxName + "." + p_78786_1_;
    TextureOffset var8 = baseModel.getTextureOffset(p_78786_1_);
    setTextureOffset(textureOffsetX, textureOffsetY);
    cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_78786_2_, p_78786_3_, p_78786_4_, p_78786_5_, p_78786_6_, p_78786_7_, 0.0F).func_78244_a(p_78786_1_));
    return this;
  }
  
  public ModelRenderer addBox(float p_78789_1_, float p_78789_2_, float p_78789_3_, int p_78789_4_, int p_78789_5_, int p_78789_6_)
  {
    cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_78789_1_, p_78789_2_, p_78789_3_, p_78789_4_, p_78789_5_, p_78789_6_, 0.0F));
    return this;
  }
  
  public ModelRenderer addBox(float p_178769_1_, float p_178769_2_, float p_178769_3_, int p_178769_4_, int p_178769_5_, int p_178769_6_, boolean p_178769_7_)
  {
    cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_178769_1_, p_178769_2_, p_178769_3_, p_178769_4_, p_178769_5_, p_178769_6_, 0.0F, p_178769_7_));
    return this;
  }
  



  public void addBox(float p_78790_1_, float p_78790_2_, float p_78790_3_, int p_78790_4_, int p_78790_5_, int p_78790_6_, float p_78790_7_)
  {
    cubeList.add(new ModelBox(this, textureOffsetX, textureOffsetY, p_78790_1_, p_78790_2_, p_78790_3_, p_78790_4_, p_78790_5_, p_78790_6_, p_78790_7_));
  }
  
  public void setRotationPoint(float p_78793_1_, float p_78793_2_, float p_78793_3_)
  {
    rotationPointX = p_78793_1_;
    rotationPointY = p_78793_2_;
    rotationPointZ = p_78793_3_;
  }
  
  public void render(float p_78785_1_)
  {
    if ((!isHidden) && (showModel))
    {
      if (!compiled)
      {
        compileDisplayList(p_78785_1_);
      }
      
      GlStateManager.translate(offsetX, offsetY, offsetZ);
      

      if ((rotateAngleX == 0.0F) && (rotateAngleY == 0.0F) && (rotateAngleZ == 0.0F))
      {
        if ((rotationPointX == 0.0F) && (rotationPointY == 0.0F) && (rotationPointZ == 0.0F))
        {
          GlStateManager.callList(displayList);
          
          if (childModels != null)
          {
            for (int var2 = 0; var2 < childModels.size(); var2++)
            {
              ((ModelRenderer)childModels.get(var2)).render(p_78785_1_);
            }
          }
        }
        else
        {
          GlStateManager.translate(rotationPointX * p_78785_1_, rotationPointY * p_78785_1_, rotationPointZ * p_78785_1_);
          GlStateManager.callList(displayList);
          
          if (childModels != null)
          {
            for (int var2 = 0; var2 < childModels.size(); var2++)
            {
              ((ModelRenderer)childModels.get(var2)).render(p_78785_1_);
            }
          }
          
          GlStateManager.translate(-rotationPointX * p_78785_1_, -rotationPointY * p_78785_1_, -rotationPointZ * p_78785_1_);
        }
      }
      else
      {
        GlStateManager.pushMatrix();
        GlStateManager.translate(rotationPointX * p_78785_1_, rotationPointY * p_78785_1_, rotationPointZ * p_78785_1_);
        
        if (rotateAngleZ != 0.0F)
        {
          GlStateManager.rotate(rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
        }
        
        if (rotateAngleY != 0.0F)
        {
          GlStateManager.rotate(rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
        }
        
        if (rotateAngleX != 0.0F)
        {
          GlStateManager.rotate(rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
        }
        
        GlStateManager.callList(displayList);
        
        if (childModels != null)
        {
          for (int var2 = 0; var2 < childModels.size(); var2++)
          {
            ((ModelRenderer)childModels.get(var2)).render(p_78785_1_);
          }
        }
        
        GlStateManager.popMatrix();
      }
      
      GlStateManager.translate(-offsetX, -offsetY, -offsetZ);
    }
  }
  
  public void renderWithRotation(float p_78791_1_)
  {
    if ((!isHidden) && (showModel))
    {
      if (!compiled)
      {
        compileDisplayList(p_78791_1_);
      }
      
      GlStateManager.pushMatrix();
      GlStateManager.translate(rotationPointX * p_78791_1_, rotationPointY * p_78791_1_, rotationPointZ * p_78791_1_);
      
      if (rotateAngleY != 0.0F)
      {
        GlStateManager.rotate(rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
      }
      
      if (rotateAngleX != 0.0F)
      {
        GlStateManager.rotate(rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
      }
      
      if (rotateAngleZ != 0.0F)
      {
        GlStateManager.rotate(rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
      }
      
      GlStateManager.callList(displayList);
      GlStateManager.popMatrix();
    }
  }
  



  public void postRender(float p_78794_1_)
  {
    if ((!isHidden) && (showModel))
    {
      if (!compiled)
      {
        compileDisplayList(p_78794_1_);
      }
      
      if ((rotateAngleX == 0.0F) && (rotateAngleY == 0.0F) && (rotateAngleZ == 0.0F))
      {
        if ((rotationPointX != 0.0F) || (rotationPointY != 0.0F) || (rotationPointZ != 0.0F))
        {
          GlStateManager.translate(rotationPointX * p_78794_1_, rotationPointY * p_78794_1_, rotationPointZ * p_78794_1_);
        }
      }
      else
      {
        GlStateManager.translate(rotationPointX * p_78794_1_, rotationPointY * p_78794_1_, rotationPointZ * p_78794_1_);
        
        if (rotateAngleZ != 0.0F)
        {
          GlStateManager.rotate(rotateAngleZ * 57.295776F, 0.0F, 0.0F, 1.0F);
        }
        
        if (rotateAngleY != 0.0F)
        {
          GlStateManager.rotate(rotateAngleY * 57.295776F, 0.0F, 1.0F, 0.0F);
        }
        
        if (rotateAngleX != 0.0F)
        {
          GlStateManager.rotate(rotateAngleX * 57.295776F, 1.0F, 0.0F, 0.0F);
        }
      }
    }
  }
  



  private void compileDisplayList(float p_78788_1_)
  {
    if (displayList == 0)
    {
      savedScale = p_78788_1_;
      displayList = GLAllocation.generateDisplayLists(1);
    }
    
    GL11.glNewList(displayList, 4864);
    WorldRenderer var2 = Tessellator.getInstance().getWorldRenderer();
    

    for (int i = 0; i < cubeList.size(); i++)
    {
      ((ModelBox)cubeList.get(i)).render(var2, p_78788_1_);
    }
    
    for (i = 0; i < spriteList.size(); i++)
    {
      ModelSprite sprite = (ModelSprite)spriteList.get(i);
      sprite.render(Tessellator.getInstance(), p_78788_1_);
    }
    
    GL11.glEndList();
    compiled = true;
  }
  



  public ModelRenderer setTextureSize(int p_78787_1_, int p_78787_2_)
  {
    textureWidth = p_78787_1_;
    textureHeight = p_78787_2_;
    return this;
  }
  
  public void addSprite(float posX, float posY, float posZ, int sizeX, int sizeY, int sizeZ, float sizeAdd)
  {
    spriteList.add(new ModelSprite(this, textureOffsetX, textureOffsetY, posX, posY, posZ, sizeX, sizeY, sizeZ, sizeAdd));
  }
  
  public boolean getCompiled()
  {
    return compiled;
  }
  
  public int getDisplayList()
  {
    return displayList;
  }
  
  public void resetDisplayList()
  {
    if (compiled)
    {
      compiled = false;
      compileDisplayList(savedScale);
    }
  }
}
