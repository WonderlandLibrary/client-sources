/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.entity.model.CowModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.MooshroomEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3f;
import net.optifine.Config;

public class MooshroomMushroomLayer<T extends MooshroomEntity>
extends LayerRenderer<T, CowModel<T>> {
    private ModelRenderer modelRendererMushroom;
    private static final ResourceLocation LOCATION_MUSHROOM_RED = new ResourceLocation("textures/entity/cow/red_mushroom.png");
    private static final ResourceLocation LOCATION_MUSHROOM_BROWN = new ResourceLocation("textures/entity/cow/brown_mushroom.png");
    private static boolean hasTextureMushroomRed = false;
    private static boolean hasTextureMushroomBrown = false;

    public MooshroomMushroomLayer(IEntityRenderer<T, CowModel<T>> iEntityRenderer) {
        super(iEntityRenderer);
        IEntityRenderer<T, CowModel<T>> iEntityRenderer2 = iEntityRenderer;
        this.modelRendererMushroom = new ModelRenderer(iEntityRenderer2.getEntityModel());
        this.modelRendererMushroom.setTextureSize(16, 16);
        this.modelRendererMushroom.rotationPointX = 8.0f;
        this.modelRendererMushroom.rotationPointZ = 8.0f;
        this.modelRendererMushroom.rotateAngleY = MathHelper.PI / 4.0f;
        int[][] nArrayArray = new int[][]{null, null, {16, 16, 0, 0}, {16, 16, 0, 0}, null, null};
        this.modelRendererMushroom.addBox(nArrayArray, -10.0f, 0.0f, 0.0f, 20.0f, 16.0f, 0.0f, 0.0f);
        int[][] nArrayArray2 = new int[][]{null, null, null, null, {16, 16, 0, 0}, {16, 16, 0, 0}};
        this.modelRendererMushroom.addBox(nArrayArray2, 0.0f, 0.0f, -10.0f, 0.0f, 16.0f, 20.0f, 0.0f);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, T t, float f, float f2, float f3, float f4, float f5, float f6) {
        if (!((AgeableEntity)t).isChild() && !((Entity)t).isInvisible()) {
            BlockRendererDispatcher blockRendererDispatcher = Minecraft.getInstance().getBlockRendererDispatcher();
            BlockState blockState = ((MooshroomEntity)t).getMooshroomType().getRenderState();
            ResourceLocation resourceLocation = this.getCustomMushroom(blockState);
            IVertexBuilder iVertexBuilder = null;
            if (resourceLocation != null) {
                iVertexBuilder = iRenderTypeBuffer.getBuffer(RenderType.getEntityCutout(resourceLocation));
            }
            int n2 = LivingRenderer.getPackedOverlay(t, 0.0f);
            matrixStack.push();
            matrixStack.translate(0.2f, -0.35f, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-48.0f));
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(-0.5, -0.5, -0.5);
            if (resourceLocation != null) {
                this.modelRendererMushroom.render(matrixStack, iVertexBuilder, n, n2);
            } else {
                blockRendererDispatcher.renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, n2);
            }
            matrixStack.pop();
            matrixStack.push();
            matrixStack.translate(0.2f, -0.35f, 0.5);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(42.0f));
            matrixStack.translate(0.1f, 0.0, -0.6f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-48.0f));
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(-0.5, -0.5, -0.5);
            if (resourceLocation != null) {
                this.modelRendererMushroom.render(matrixStack, iVertexBuilder, n, n2);
            } else {
                blockRendererDispatcher.renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, n2);
            }
            matrixStack.pop();
            matrixStack.push();
            ((CowModel)this.getEntityModel()).getHead().translateRotate(matrixStack);
            matrixStack.translate(0.0, -0.7f, -0.2f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(-78.0f));
            matrixStack.scale(-1.0f, -1.0f, 1.0f);
            matrixStack.translate(-0.5, -0.5, -0.5);
            if (resourceLocation != null) {
                this.modelRendererMushroom.render(matrixStack, iVertexBuilder, n, n2);
            } else {
                blockRendererDispatcher.renderBlock(blockState, matrixStack, iRenderTypeBuffer, n, n2);
            }
            matrixStack.pop();
        }
    }

    private ResourceLocation getCustomMushroom(BlockState blockState) {
        Block block = blockState.getBlock();
        if (block == Blocks.RED_MUSHROOM && hasTextureMushroomRed) {
            return LOCATION_MUSHROOM_RED;
        }
        return block == Blocks.BROWN_MUSHROOM && hasTextureMushroomBrown ? LOCATION_MUSHROOM_BROWN : null;
    }

    public static void update() {
        hasTextureMushroomRed = Config.hasResource(LOCATION_MUSHROOM_RED);
        hasTextureMushroomBrown = Config.hasResource(LOCATION_MUSHROOM_BROWN);
    }

    @Override
    public void render(MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, Entity entity2, float f, float f2, float f3, float f4, float f5, float f6) {
        this.render(matrixStack, iRenderTypeBuffer, n, (T)((MooshroomEntity)entity2), f, f2, f3, f4, f5, f6);
    }
}

