/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.blaze3d.vertex.VertexBuilderUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Matrix3f;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;
import net.optifine.IRandomEntity;
import net.optifine.RandomEntities;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.model.ModelSprite;
import net.optifine.render.BoxVertexPositions;
import net.optifine.render.VertexPosition;
import net.optifine.shaders.Shaders;

public class ModelRenderer {
    public float textureWidth = 64.0f;
    public float textureHeight = 32.0f;
    private int textureOffsetX;
    private int textureOffsetY;
    public float rotationPointX;
    public float rotationPointY;
    public float rotationPointZ;
    public float rotateAngleX;
    public float rotateAngleY;
    public float rotateAngleZ;
    public boolean mirror;
    public boolean showModel = true;
    public final ObjectList<ModelBox> cubeList = new ObjectArrayList<ModelBox>();
    public final ObjectList<ModelRenderer> childModels = new ObjectArrayList<ModelRenderer>();
    public List spriteList = new ArrayList();
    public boolean mirrorV = false;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float scaleZ = 1.0f;
    private ResourceLocation textureLocation = null;
    private String id = null;
    private ModelUpdater modelUpdater;
    private WorldRenderer renderGlobal = Config.getRenderGlobal();

    public ModelRenderer(Model model) {
        model.accept(this);
        this.setTextureSize(model.textureWidth, model.textureHeight);
    }

    public ModelRenderer(Model model, int n, int n2) {
        this(model.textureWidth, model.textureHeight, n, n2);
        model.accept(this);
    }

    public ModelRenderer(int n, int n2, int n3, int n4) {
        this.setTextureSize(n, n2);
        this.setTextureOffset(n3, n4);
    }

    private ModelRenderer() {
    }

    public ModelRenderer getModelAngleCopy() {
        ModelRenderer modelRenderer = new ModelRenderer();
        modelRenderer.copyModelAngles(this);
        return modelRenderer;
    }

    public void copyModelAngles(ModelRenderer modelRenderer) {
        this.rotateAngleX = modelRenderer.rotateAngleX;
        this.rotateAngleY = modelRenderer.rotateAngleY;
        this.rotateAngleZ = modelRenderer.rotateAngleZ;
        this.rotationPointX = modelRenderer.rotationPointX;
        this.rotationPointY = modelRenderer.rotationPointY;
        this.rotationPointZ = modelRenderer.rotationPointZ;
    }

    public void addChild(ModelRenderer modelRenderer) {
        this.childModels.add(modelRenderer);
    }

    public ModelRenderer setTextureOffset(int n, int n2) {
        this.textureOffsetX = n;
        this.textureOffsetY = n2;
        return this;
    }

    public ModelRenderer addBox(String string, float f, float f2, float f3, int n, int n2, int n3, float f4, int n4, int n5) {
        this.setTextureOffset(n4, n5);
        this.addBox(this.textureOffsetX, this.textureOffsetY, f, f2, f3, n, n2, n3, f4, f4, f4, this.mirror, true);
        return this;
    }

    public ModelRenderer addBox(float f, float f2, float f3, float f4, float f5, float f6) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, f, f2, f3, f4, f5, f6, 0.0f, 0.0f, 0.0f, this.mirror, true);
        return this;
    }

    public ModelRenderer addBox(float f, float f2, float f3, float f4, float f5, float f6, boolean bl) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, f, f2, f3, f4, f5, f6, 0.0f, 0.0f, 0.0f, bl, true);
        return this;
    }

    public void addBox(float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, f, f2, f3, f4, f5, f6, f7, f7, f7, this.mirror, true);
    }

    public void addBox(float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, f, f2, f3, f4, f5, f6, f7, f8, f9, this.mirror, true);
    }

    public void addBox(float f, float f2, float f3, float f4, float f5, float f6, float f7, boolean bl) {
        this.addBox(this.textureOffsetX, this.textureOffsetY, f, f2, f3, f4, f5, f6, f7, f7, f7, bl, true);
    }

    private void addBox(int n, int n2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, boolean bl2) {
        this.cubeList.add(new ModelBox(n, n2, f, f2, f3, f4, f5, f6, f7, f8, f9, bl, this.textureWidth, this.textureHeight));
    }

    public void setRotationPoint(float f, float f2, float f3) {
        this.rotationPointX = f;
        this.rotationPointY = f2;
        this.rotationPointZ = f3;
    }

    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2) {
        this.render(matrixStack, iVertexBuilder, n, n2, 1.0f, 1.0f, 1.0f, 1.0f);
    }

    public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        if (!(!this.showModel || this.cubeList.isEmpty() && this.childModels.isEmpty() && this.spriteList.isEmpty())) {
            int n3;
            RenderType renderType = null;
            IRenderTypeBuffer.Impl impl = null;
            if (this.textureLocation != null) {
                if (this.renderGlobal.renderOverlayEyes) {
                    return;
                }
                impl = iVertexBuilder.getRenderTypeBuffer();
                if (impl != null) {
                    IVertexBuilder iVertexBuilder2 = iVertexBuilder.getSecondaryBuilder();
                    renderType = impl.getLastRenderType();
                    iVertexBuilder = impl.getBuffer(this.textureLocation, iVertexBuilder);
                    if (iVertexBuilder2 != null) {
                        iVertexBuilder = VertexBuilderUtils.newDelegate(iVertexBuilder2, iVertexBuilder);
                    }
                }
            }
            if (this.modelUpdater != null) {
                this.modelUpdater.update();
            }
            matrixStack.push();
            this.translateRotate(matrixStack);
            this.doRender(matrixStack.getLast(), iVertexBuilder, n, n2, f, f2, f3, f4);
            int n4 = this.childModels.size();
            for (n3 = 0; n3 < n4; ++n3) {
                ModelRenderer modelRenderer = (ModelRenderer)this.childModels.get(n3);
                modelRenderer.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
            }
            n3 = this.spriteList.size();
            for (int i = 0; i < n3; ++i) {
                ModelSprite modelSprite = (ModelSprite)this.spriteList.get(i);
                modelSprite.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
            }
            matrixStack.pop();
            if (renderType != null) {
                impl.getBuffer(renderType);
            }
        }
    }

    public void translateRotate(MatrixStack matrixStack) {
        matrixStack.translate(this.rotationPointX / 16.0f, this.rotationPointY / 16.0f, this.rotationPointZ / 16.0f);
        if (this.rotateAngleZ != 0.0f) {
            matrixStack.rotate(Vector3f.ZP.rotation(this.rotateAngleZ));
        }
        if (this.rotateAngleY != 0.0f) {
            matrixStack.rotate(Vector3f.YP.rotation(this.rotateAngleY));
        }
        if (this.rotateAngleX != 0.0f) {
            matrixStack.rotate(Vector3f.XP.rotation(this.rotateAngleX));
        }
    }

    private void doRender(MatrixStack.Entry entry, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
        Matrix4f matrix4f = entry.getMatrix();
        Matrix3f matrix3f = entry.getNormal();
        boolean bl = Config.isShaders() && Shaders.useVelocityAttrib && Config.isMinecraftThread();
        int n3 = this.cubeList.size();
        for (int i = 0; i < n3; ++i) {
            IRandomEntity iRandomEntity;
            ModelBox modelBox = (ModelBox)this.cubeList.get(i);
            VertexPosition[][] vertexPositionArray = null;
            if (bl && (iRandomEntity = RandomEntities.getRandomEntityRendered()) != null) {
                vertexPositionArray = modelBox.getBoxVertexPositions(iRandomEntity.getId());
            }
            int n4 = modelBox.quads.length;
            for (int j = 0; j < n4; ++j) {
                TexturedQuad texturedQuad = modelBox.quads[j];
                if (texturedQuad == null) continue;
                if (vertexPositionArray != null) {
                    iVertexBuilder.setQuadVertexPositions(vertexPositionArray[j]);
                }
                Vector3f vector3f = iVertexBuilder.getTempVec3f(texturedQuad.normal);
                vector3f.transform(matrix3f);
                float f5 = vector3f.getX();
                float f6 = vector3f.getY();
                float f7 = vector3f.getZ();
                for (int k = 0; k < 4; ++k) {
                    PositionTextureVertex positionTextureVertex = texturedQuad.vertexPositions[k];
                    float f8 = positionTextureVertex.position.getX() / 16.0f;
                    float f9 = positionTextureVertex.position.getY() / 16.0f;
                    float f10 = positionTextureVertex.position.getZ() / 16.0f;
                    float f11 = matrix4f.getTransformX(f8, f9, f10, 1.0f);
                    float f12 = matrix4f.getTransformY(f8, f9, f10, 1.0f);
                    float f13 = matrix4f.getTransformZ(f8, f9, f10, 1.0f);
                    iVertexBuilder.addVertex(f11, f12, f13, f, f2, f3, f4, positionTextureVertex.textureU, positionTextureVertex.textureV, n2, n, f5, f6, f7);
                }
            }
        }
    }

    public ModelRenderer setTextureSize(int n, int n2) {
        this.textureWidth = n;
        this.textureHeight = n2;
        return this;
    }

    public ModelBox getRandomCube(Random random2) {
        return (ModelBox)this.cubeList.get(random2.nextInt(this.cubeList.size()));
    }

    public void addSprite(float f, float f2, float f3, int n, int n2, int n3, float f4) {
        this.spriteList.add(new ModelSprite(this, this.textureOffsetX, this.textureOffsetY, f, f2, f3, n, n2, n3, f4));
    }

    public ResourceLocation getTextureLocation() {
        return this.textureLocation;
    }

    public void setTextureLocation(ResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String string) {
        this.id = string;
    }

    public void addBox(int[][] nArray, float f, float f2, float f3, float f4, float f5, float f6, float f7) {
        this.cubeList.add(new ModelBox(nArray, f, f2, f3, f4, f5, f6, f7, f7, f7, this.mirror, this.textureWidth, this.textureHeight));
    }

    public ModelRenderer getChild(int n) {
        if (this.childModels == null) {
            return null;
        }
        return n >= 0 && n < this.childModels.size() ? (ModelRenderer)this.childModels.get(n) : null;
    }

    public ModelRenderer getChild(String string) {
        if (string == null) {
            return null;
        }
        if (this.childModels != null) {
            for (int i = 0; i < this.childModels.size(); ++i) {
                ModelRenderer modelRenderer = (ModelRenderer)this.childModels.get(i);
                if (!string.equals(modelRenderer.getId())) continue;
                return modelRenderer;
            }
        }
        return null;
    }

    public ModelRenderer getChildDeep(String string) {
        if (string == null) {
            return null;
        }
        ModelRenderer modelRenderer = this.getChild(string);
        if (modelRenderer != null) {
            return modelRenderer;
        }
        if (this.childModels != null) {
            for (int i = 0; i < this.childModels.size(); ++i) {
                ModelRenderer modelRenderer2 = (ModelRenderer)this.childModels.get(i);
                ModelRenderer modelRenderer3 = modelRenderer2.getChildDeep(string);
                if (modelRenderer3 == null) continue;
                return modelRenderer3;
            }
        }
        return null;
    }

    public void setModelUpdater(ModelUpdater modelUpdater) {
        this.modelUpdater = modelUpdater;
    }

    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("id: " + this.id + ", boxes: " + (this.cubeList != null ? Integer.valueOf(this.cubeList.size()) : null) + ", submodels: " + (this.childModels != null ? Integer.valueOf(this.childModels.size()) : null));
        return stringBuffer.toString();
    }

    public static class ModelBox {
        private final TexturedQuad[] quads;
        public final float posX1;
        public final float posY1;
        public final float posZ1;
        public final float posX2;
        public final float posY2;
        public final float posZ2;
        private BoxVertexPositions boxVertexPositions;

        public ModelBox(int n, int n2, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, float f10, float f11) {
            this.posX1 = f;
            this.posY1 = f2;
            this.posZ1 = f3;
            this.posX2 = f + f4;
            this.posY2 = f2 + f5;
            this.posZ2 = f3 + f6;
            this.quads = new TexturedQuad[6];
            float f12 = f + f4;
            float f13 = f2 + f5;
            float f14 = f3 + f6;
            f -= f7;
            f2 -= f8;
            f3 -= f9;
            f12 += f7;
            f13 += f8;
            f14 += f9;
            if (bl) {
                float f15 = f12;
                f12 = f;
                f = f15;
            }
            PositionTextureVertex positionTextureVertex = new PositionTextureVertex(f, f2, f3, 0.0f, 0.0f);
            PositionTextureVertex positionTextureVertex2 = new PositionTextureVertex(f12, f2, f3, 0.0f, 8.0f);
            PositionTextureVertex positionTextureVertex3 = new PositionTextureVertex(f12, f13, f3, 8.0f, 8.0f);
            PositionTextureVertex positionTextureVertex4 = new PositionTextureVertex(f, f13, f3, 8.0f, 0.0f);
            PositionTextureVertex positionTextureVertex5 = new PositionTextureVertex(f, f2, f14, 0.0f, 0.0f);
            PositionTextureVertex positionTextureVertex6 = new PositionTextureVertex(f12, f2, f14, 0.0f, 8.0f);
            PositionTextureVertex positionTextureVertex7 = new PositionTextureVertex(f12, f13, f14, 8.0f, 8.0f);
            PositionTextureVertex positionTextureVertex8 = new PositionTextureVertex(f, f13, f14, 8.0f, 0.0f);
            float f16 = n;
            float f17 = (float)n + f6;
            float f18 = (float)n + f6 + f4;
            float f19 = (float)n + f6 + f4 + f4;
            float f20 = (float)n + f6 + f4 + f6;
            float f21 = (float)n + f6 + f4 + f6 + f4;
            float f22 = n2;
            float f23 = (float)n2 + f6;
            float f24 = (float)n2 + f6 + f5;
            this.quads[2] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex6, positionTextureVertex5, positionTextureVertex, positionTextureVertex2}, f17, f22, f18, f23, f10, f11, bl, Direction.DOWN);
            this.quads[3] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex3, positionTextureVertex4, positionTextureVertex8, positionTextureVertex7}, f18, f23, f19, f22, f10, f11, bl, Direction.UP);
            this.quads[1] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex, positionTextureVertex5, positionTextureVertex8, positionTextureVertex4}, f16, f23, f17, f24, f10, f11, bl, Direction.WEST);
            this.quads[4] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex2, positionTextureVertex, positionTextureVertex4, positionTextureVertex3}, f17, f23, f18, f24, f10, f11, bl, Direction.NORTH);
            this.quads[0] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex6, positionTextureVertex2, positionTextureVertex3, positionTextureVertex7}, f18, f23, f20, f24, f10, f11, bl, Direction.EAST);
            this.quads[5] = new TexturedQuad(new PositionTextureVertex[]{positionTextureVertex5, positionTextureVertex6, positionTextureVertex7, positionTextureVertex8}, f20, f23, f21, f24, f10, f11, bl, Direction.SOUTH);
        }

        public ModelBox(int[][] nArray, float f, float f2, float f3, float f4, float f5, float f6, float f7, float f8, float f9, boolean bl, float f10, float f11) {
            this.posX1 = f;
            this.posY1 = f2;
            this.posZ1 = f3;
            this.posX2 = f + f4;
            this.posY2 = f2 + f5;
            this.posZ2 = f3 + f6;
            this.quads = new TexturedQuad[6];
            float f12 = f + f4;
            float f13 = f2 + f5;
            float f14 = f3 + f6;
            f -= f7;
            f2 -= f8;
            f3 -= f9;
            f12 += f7;
            f13 += f8;
            f14 += f9;
            if (bl) {
                float f15 = f12;
                f12 = f;
                f = f15;
            }
            PositionTextureVertex positionTextureVertex = new PositionTextureVertex(f, f2, f3, 0.0f, 0.0f);
            PositionTextureVertex positionTextureVertex2 = new PositionTextureVertex(f12, f2, f3, 0.0f, 8.0f);
            PositionTextureVertex positionTextureVertex3 = new PositionTextureVertex(f12, f13, f3, 8.0f, 8.0f);
            PositionTextureVertex positionTextureVertex4 = new PositionTextureVertex(f, f13, f3, 8.0f, 0.0f);
            PositionTextureVertex positionTextureVertex5 = new PositionTextureVertex(f, f2, f14, 0.0f, 0.0f);
            PositionTextureVertex positionTextureVertex6 = new PositionTextureVertex(f12, f2, f14, 0.0f, 8.0f);
            PositionTextureVertex positionTextureVertex7 = new PositionTextureVertex(f12, f13, f14, 8.0f, 8.0f);
            PositionTextureVertex positionTextureVertex8 = new PositionTextureVertex(f, f13, f14, 8.0f, 0.0f);
            this.quads[2] = this.makeTexturedQuad(new PositionTextureVertex[]{positionTextureVertex6, positionTextureVertex5, positionTextureVertex, positionTextureVertex2}, nArray[0], true, f10, f11, bl, Direction.DOWN);
            this.quads[3] = this.makeTexturedQuad(new PositionTextureVertex[]{positionTextureVertex3, positionTextureVertex4, positionTextureVertex8, positionTextureVertex7}, nArray[5], true, f10, f11, bl, Direction.UP);
            this.quads[1] = this.makeTexturedQuad(new PositionTextureVertex[]{positionTextureVertex, positionTextureVertex5, positionTextureVertex8, positionTextureVertex4}, nArray[5], false, f10, f11, bl, Direction.WEST);
            this.quads[4] = this.makeTexturedQuad(new PositionTextureVertex[]{positionTextureVertex2, positionTextureVertex, positionTextureVertex4, positionTextureVertex3}, nArray[5], false, f10, f11, bl, Direction.NORTH);
            this.quads[0] = this.makeTexturedQuad(new PositionTextureVertex[]{positionTextureVertex6, positionTextureVertex2, positionTextureVertex3, positionTextureVertex7}, nArray[5], false, f10, f11, bl, Direction.EAST);
            this.quads[5] = this.makeTexturedQuad(new PositionTextureVertex[]{positionTextureVertex5, positionTextureVertex6, positionTextureVertex7, positionTextureVertex8}, nArray[5], false, f10, f11, bl, Direction.SOUTH);
        }

        private TexturedQuad makeTexturedQuad(PositionTextureVertex[] positionTextureVertexArray, int[] nArray, boolean bl, float f, float f2, boolean bl2, Direction direction) {
            if (nArray == null) {
                return null;
            }
            return bl ? new TexturedQuad(positionTextureVertexArray, nArray[2], nArray[3], nArray[0], nArray[1], f, f2, bl2, direction) : new TexturedQuad(positionTextureVertexArray, nArray[0], nArray[1], nArray[2], nArray[3], f, f2, bl2, direction);
        }

        public VertexPosition[][] getBoxVertexPositions(int n) {
            if (this.boxVertexPositions == null) {
                this.boxVertexPositions = new BoxVertexPositions();
            }
            return (VertexPosition[][])this.boxVertexPositions.get(n);
        }
    }

    static class TexturedQuad {
        public final PositionTextureVertex[] vertexPositions;
        public final Vector3f normal;

        public TexturedQuad(PositionTextureVertex[] positionTextureVertexArray, float f, float f2, float f3, float f4, float f5, float f6, boolean bl, Direction direction) {
            this.vertexPositions = positionTextureVertexArray;
            float f7 = 0.0f / f5;
            float f8 = 0.0f / f6;
            if (Config.isAntialiasing()) {
                f7 = 0.05f / f5;
                f8 = 0.05f / f6;
                if (f3 < f) {
                    f7 = -f7;
                }
                if (f4 < f2) {
                    f8 = -f8;
                }
            }
            positionTextureVertexArray[0] = positionTextureVertexArray[0].setTextureUV(f3 / f5 - f7, f2 / f6 + f8);
            positionTextureVertexArray[1] = positionTextureVertexArray[5].setTextureUV(f / f5 + f7, f2 / f6 + f8);
            positionTextureVertexArray[2] = positionTextureVertexArray[5].setTextureUV(f / f5 + f7, f4 / f6 - f8);
            positionTextureVertexArray[3] = positionTextureVertexArray[5].setTextureUV(f3 / f5 - f7, f4 / f6 - f8);
            if (bl) {
                int n = positionTextureVertexArray.length;
                for (int i = 0; i < n / 2; ++i) {
                    PositionTextureVertex positionTextureVertex = positionTextureVertexArray[i];
                    positionTextureVertexArray[i] = positionTextureVertexArray[n - 1 - i];
                    positionTextureVertexArray[n - 1 - i] = positionTextureVertex;
                }
            }
            this.normal = direction.toVector3f();
            if (bl) {
                this.normal.mul(-1.0f, 1.0f, 1.0f);
            }
        }
    }

    static class PositionTextureVertex {
        public final Vector3f position;
        public final float textureU;
        public final float textureV;

        public PositionTextureVertex(float f, float f2, float f3, float f4, float f5) {
            this(new Vector3f(f, f2, f3), f4, f5);
        }

        public PositionTextureVertex setTextureUV(float f, float f2) {
            return new PositionTextureVertex(this.position, f, f2);
        }

        public PositionTextureVertex(Vector3f vector3f, float f, float f2) {
            this.position = vector3f;
            this.textureU = f;
            this.textureV = f2;
        }
    }
}

