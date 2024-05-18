package net.minecraft.src;

import java.util.*;
import org.lwjgl.opengl.*;

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
    public float field_82906_o;
    public float field_82908_p;
    public float field_82907_q;
    
    public ModelRenderer(final ModelBase par1ModelBase, final String par2Str) {
        this.textureWidth = 64.0f;
        this.textureHeight = 32.0f;
        this.compiled = false;
        this.displayList = 0;
        this.mirror = false;
        this.showModel = true;
        this.isHidden = false;
        this.cubeList = new ArrayList();
        this.baseModel = par1ModelBase;
        par1ModelBase.boxList.add(this);
        this.boxName = par2Str;
        this.setTextureSize(par1ModelBase.textureWidth, par1ModelBase.textureHeight);
    }
    
    public ModelRenderer(final ModelBase par1ModelBase) {
        this(par1ModelBase, null);
    }
    
    public ModelRenderer(final ModelBase par1ModelBase, final int par2, final int par3) {
        this(par1ModelBase);
        this.setTextureOffset(par2, par3);
    }
    
    public void addChild(final ModelRenderer par1ModelRenderer) {
        if (this.childModels == null) {
            this.childModels = new ArrayList();
        }
        this.childModels.add(par1ModelRenderer);
    }
    
    public ModelRenderer setTextureOffset(final int par1, final int par2) {
        this.textureOffsetX = par1;
        this.textureOffsetY = par2;
        return this;
    }
    
    public ModelRenderer addBox(String par1Str, final float par2, final float par3, final float par4, final int par5, final int par6, final int par7) {
        par1Str = String.valueOf(this.boxName) + "." + par1Str;
        final TextureOffset var8 = this.baseModel.getTextureOffset(par1Str);
        this.setTextureOffset(var8.textureOffsetX, var8.textureOffsetY);
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par2, par3, par4, par5, par6, par7, 0.0f).func_78244_a(par1Str));
        return this;
    }
    
    public ModelRenderer addBox(final float par1, final float par2, final float par3, final int par4, final int par5, final int par6) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, 0.0f));
        return this;
    }
    
    public void addBox(final float par1, final float par2, final float par3, final int par4, final int par5, final int par6, final float par7) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, par1, par2, par3, par4, par5, par6, par7));
    }
    
    public void setRotationPoint(final float par1, final float par2, final float par3) {
        this.rotationPointX = par1;
        this.rotationPointY = par2;
        this.rotationPointZ = par3;
    }
    
    public void render(final float par1) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            GL11.glTranslatef(this.field_82906_o, this.field_82908_p, this.field_82907_q);
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                    GL11.glCallList(this.displayList);
                    if (this.childModels != null) {
                        for (int var2 = 0; var2 < this.childModels.size(); ++var2) {
                            this.childModels.get(var2).render(par1);
                        }
                    }
                }
                else {
                    GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
                    GL11.glCallList(this.displayList);
                    if (this.childModels != null) {
                        for (int var2 = 0; var2 < this.childModels.size(); ++var2) {
                            this.childModels.get(var2).render(par1);
                        }
                    }
                    GL11.glTranslatef(-this.rotationPointX * par1, -this.rotationPointY * par1, -this.rotationPointZ * par1);
                }
            }
            else {
                GL11.glPushMatrix();
                GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GL11.glCallList(this.displayList);
                if (this.childModels != null) {
                    for (int var2 = 0; var2 < this.childModels.size(); ++var2) {
                        this.childModels.get(var2).render(par1);
                    }
                }
                GL11.glPopMatrix();
            }
            GL11.glTranslatef(-this.field_82906_o, -this.field_82908_p, -this.field_82907_q);
        }
    }
    
    public void renderWithRotation(final float par1) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            GL11.glPushMatrix();
            GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
            if (this.rotateAngleY != 0.0f) {
                GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GL11.glCallList(this.displayList);
            GL11.glPopMatrix();
        }
    }
    
    public void postRender(final float par1) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(par1);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
                }
            }
            else {
                GL11.glTranslatef(this.rotationPointX * par1, this.rotationPointY * par1, this.rotationPointZ * par1);
                if (this.rotateAngleZ != 0.0f) {
                    GL11.glRotatef(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GL11.glRotatef(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GL11.glRotatef(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
    }
    
    private void compileDisplayList(final float par1) {
        GL11.glNewList(this.displayList = GLAllocation.generateDisplayLists(1), 4864);
        final Tessellator var2 = Tessellator.instance;
        for (int var3 = 0; var3 < this.cubeList.size(); ++var3) {
            this.cubeList.get(var3).render(var2, par1);
        }
        GL11.glEndList();
        this.compiled = true;
    }
    
    public ModelRenderer setTextureSize(final int par1, final int par2) {
        this.textureWidth = par1;
        this.textureHeight = par2;
        return this;
    }
}
