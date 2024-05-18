package net.minecraft.client.model;

import optfine.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.renderer.*;
import com.google.common.collect.*;
import java.util.*;

public class ModelRenderer
{
    public float rotationPointX;
    private int displayList;
    public float rotateAngleX;
    public boolean mirror;
    public float rotationPointZ;
    private int textureOffsetY;
    public List childModels;
    public boolean showModel;
    public float rotationPointY;
    private boolean compiled;
    public List cubeList;
    public List spriteList;
    public float rotateAngleZ;
    public float offsetX;
    private int textureOffsetX;
    public boolean mirrorV;
    public boolean isHidden;
    private static final String __OBFID;
    public float textureHeight;
    public float rotateAngleY;
    public float offsetY;
    public float textureWidth;
    public float offsetZ;
    public final String boxName;
    private static final String[] I;
    private ModelBase baseModel;
    
    private static void I() {
        (I = new String["  ".length()])["".length()] = I("b", "LSwvw");
        ModelRenderer.I[" ".length()] = I("\u0001\u001f\u0019}~rcvuyv", "BSFMN");
    }
    
    public void renderWithRotation(final float n) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(n);
            }
            GlStateManager.pushMatrix();
            GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
            if (this.rotateAngleY != 0.0f) {
                GlStateManager.rotate(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
            }
            if (this.rotateAngleX != 0.0f) {
                GlStateManager.rotate(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
            }
            if (this.rotateAngleZ != 0.0f) {
                GlStateManager.rotate(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
            }
            GlStateManager.callList(this.displayList);
            GlStateManager.popMatrix();
        }
    }
    
    public void setRotationPoint(final float rotationPointX, final float rotationPointY, final float rotationPointZ) {
        this.rotationPointX = rotationPointX;
        this.rotationPointY = rotationPointY;
        this.rotationPointZ = rotationPointZ;
    }
    
    public ModelRenderer setTextureOffset(final int textureOffsetX, final int textureOffsetY) {
        this.textureOffsetX = textureOffsetX;
        this.textureOffsetY = textureOffsetY;
        return this;
    }
    
    public ModelRenderer setTextureSize(final int n, final int n2) {
        this.textureWidth = n;
        this.textureHeight = n2;
        return this;
    }
    
    public void addSprite(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final float n7) {
        this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, n7));
    }
    
    public ModelRenderer addBox(String string, final float n, final float n2, final float n3, final int n4, final int n5, final int n6) {
        string = String.valueOf(this.boxName) + ModelRenderer.I["".length()] + string;
        final TextureOffset textureOffset = this.baseModel.getTextureOffset(string);
        this.setTextureOffset(textureOffset.textureOffsetX, textureOffset.textureOffsetY);
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, 0.0f).setBoxName(string));
        return this;
    }
    
    public void render(final float n) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(n);
            }
            GlStateManager.translate(this.offsetX, this.offsetY, this.offsetZ);
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX == 0.0f && this.rotationPointY == 0.0f && this.rotationPointZ == 0.0f) {
                    GlStateManager.callList(this.displayList);
                    if (this.childModels != null) {
                        int i = "".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        while (i < this.childModels.size()) {
                            ((ModelRenderer)this.childModels.get(i)).render(n);
                            ++i;
                        }
                        "".length();
                        if (0 == 3) {
                            throw null;
                        }
                    }
                }
                else {
                    GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                    GlStateManager.callList(this.displayList);
                    if (this.childModels != null) {
                        int j = "".length();
                        "".length();
                        if (4 <= 1) {
                            throw null;
                        }
                        while (j < this.childModels.size()) {
                            ((ModelRenderer)this.childModels.get(j)).render(n);
                            ++j;
                        }
                    }
                    GlStateManager.translate(-this.rotationPointX * n, -this.rotationPointY * n, -this.rotationPointZ * n);
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
            }
            else {
                GlStateManager.pushMatrix();
                GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                if (this.rotateAngleZ != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
                GlStateManager.callList(this.displayList);
                if (this.childModels != null) {
                    int k = "".length();
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    while (k < this.childModels.size()) {
                        ((ModelRenderer)this.childModels.get(k)).render(n);
                        ++k;
                    }
                }
                GlStateManager.popMatrix();
            }
            GlStateManager.translate(-this.offsetX, -this.offsetY, -this.offsetZ);
        }
    }
    
    public void addBox(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final float n7) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, n7));
    }
    
    public ModelRenderer(final ModelBase modelBase) {
        this(modelBase, null);
    }
    
    public ModelRenderer(final ModelBase modelBase, final int n, final int n2) {
        this(modelBase);
        this.setTextureOffset(n, n2);
    }
    
    private void compileDisplayList(final float n) {
        GL11.glNewList(this.displayList = GLAllocation.generateDisplayLists(" ".length()), 1105 + 3815 - 3787 + 3731);
        final WorldRenderer worldRenderer = Tessellator.getInstance().getWorldRenderer();
        int i = "".length();
        "".length();
        if (2 < 0) {
            throw null;
        }
        while (i < this.cubeList.size()) {
            ((ModelBox)this.cubeList.get(i)).render(worldRenderer, n);
            ++i;
        }
        int j = "".length();
        "".length();
        if (3 >= 4) {
            throw null;
        }
        while (j < this.spriteList.size()) {
            ((ModelSprite)this.spriteList.get(j)).render(Tessellator.getInstance(), n);
            ++j;
        }
        GL11.glEndList();
        this.compiled = (" ".length() != 0);
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ModelRenderer addBox(final float n, final float n2, final float n3, final int n4, final int n5, final int n6) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, 0.0f));
        return this;
    }
    
    public void addChild(final ModelRenderer modelRenderer) {
        if (this.childModels == null) {
            this.childModels = Lists.newArrayList();
        }
        this.childModels.add(modelRenderer);
    }
    
    public void postRender(final float n) {
        if (!this.isHidden && this.showModel) {
            if (!this.compiled) {
                this.compileDisplayList(n);
            }
            if (this.rotateAngleX == 0.0f && this.rotateAngleY == 0.0f && this.rotateAngleZ == 0.0f) {
                if (this.rotationPointX != 0.0f || this.rotationPointY != 0.0f || this.rotationPointZ != 0.0f) {
                    GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                    "".length();
                    if (false) {
                        throw null;
                    }
                }
            }
            else {
                GlStateManager.translate(this.rotationPointX * n, this.rotationPointY * n, this.rotationPointZ * n);
                if (this.rotateAngleZ != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleZ * 57.295776f, 0.0f, 0.0f, 1.0f);
                }
                if (this.rotateAngleY != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleY * 57.295776f, 0.0f, 1.0f, 0.0f);
                }
                if (this.rotateAngleX != 0.0f) {
                    GlStateManager.rotate(this.rotateAngleX * 57.295776f, 1.0f, 0.0f, 0.0f);
                }
            }
        }
    }
    
    public ModelRenderer addBox(final float n, final float n2, final float n3, final int n4, final int n5, final int n6, final boolean b) {
        this.cubeList.add(new ModelBox(this, this.textureOffsetX, this.textureOffsetY, n, n2, n3, n4, n5, n6, 0.0f, b));
        return this;
    }
    
    public ModelRenderer(final ModelBase baseModel, final String boxName) {
        this.spriteList = new ArrayList();
        this.mirrorV = ("".length() != 0);
        this.textureWidth = 64.0f;
        this.textureHeight = 32.0f;
        this.showModel = (" ".length() != 0);
        this.cubeList = Lists.newArrayList();
        this.baseModel = baseModel;
        baseModel.boxList.add(this);
        this.boxName = boxName;
        this.setTextureSize(baseModel.textureWidth, baseModel.textureHeight);
    }
    
    static {
        I();
        __OBFID = ModelRenderer.I[" ".length()];
    }
}
