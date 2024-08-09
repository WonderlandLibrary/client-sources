/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.tileentity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import net.minecraft.block.AbstractSignBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.StandingSignBlock;
import net.minecraft.block.WallSignBlock;
import net.minecraft.block.WoodType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.client.renderer.model.RenderMaterial;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRendererDispatcher;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.SignTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.optifine.Config;
import net.optifine.CustomColors;
import net.optifine.shaders.Shaders;

public class SignTileEntityRenderer
extends TileEntityRenderer<SignTileEntity> {
    private final SignModel model = new SignModel();
    private static double textRenderDistanceSq = 4096.0;

    public SignTileEntityRenderer(TileEntityRendererDispatcher tileEntityRendererDispatcher) {
        super(tileEntityRendererDispatcher);
    }

    @Override
    public void render(SignTileEntity signTileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        BlockState blockState = signTileEntity.getBlockState();
        matrixStack.push();
        float f2 = 0.6666667f;
        if (blockState.getBlock() instanceof StandingSignBlock) {
            matrixStack.translate(0.5, 0.5, 0.5);
            var9_9 = -((float)(blockState.get(StandingSignBlock.ROTATION) * 360) / 16.0f);
            matrixStack.rotate(Vector3f.YP.rotationDegrees(var9_9));
            this.model.signStick.showModel = true;
        } else {
            matrixStack.translate(0.5, 0.5, 0.5);
            var9_9 = -blockState.get(WallSignBlock.FACING).getHorizontalAngle();
            matrixStack.rotate(Vector3f.YP.rotationDegrees(var9_9));
            matrixStack.translate(0.0, -0.3125, -0.4375);
            this.model.signStick.showModel = false;
        }
        matrixStack.push();
        matrixStack.scale(0.6666667f, -0.6666667f, -0.6666667f);
        RenderMaterial renderMaterial = SignTileEntityRenderer.getMaterial(blockState.getBlock());
        IVertexBuilder iVertexBuilder = renderMaterial.getBuffer(iRenderTypeBuffer, this.model::getRenderType);
        this.model.signBoard.render(matrixStack, iVertexBuilder, n, n2);
        this.model.signStick.render(matrixStack, iVertexBuilder, n, n2);
        matrixStack.pop();
        if (SignTileEntityRenderer.isRenderText(signTileEntity)) {
            FontRenderer fontRenderer = this.renderDispatcher.getFontRenderer();
            float f3 = 0.010416667f;
            matrixStack.translate(0.0, 0.3333333432674408, 0.046666666865348816);
            matrixStack.scale(0.010416667f, -0.010416667f, 0.010416667f);
            int n3 = signTileEntity.getTextColor().getTextColor();
            if (Config.isCustomColors()) {
                n3 = CustomColors.getSignTextColor(n3);
            }
            double d = 0.4;
            int n4 = (int)((double)NativeImage.getRed(n3) * 0.4);
            int n5 = (int)((double)NativeImage.getGreen(n3) * 0.4);
            int n6 = (int)((double)NativeImage.getBlue(n3) * 0.4);
            int n7 = NativeImage.getCombined(0, n6, n5, n4);
            int n8 = 20;
            for (int i = 0; i < 4; ++i) {
                IReorderingProcessor iReorderingProcessor = signTileEntity.func_242686_a(i, arg_0 -> SignTileEntityRenderer.lambda$render$0(fontRenderer, arg_0));
                if (iReorderingProcessor == null) continue;
                float f4 = -fontRenderer.func_243245_a(iReorderingProcessor) / 2;
                fontRenderer.func_238416_a_(iReorderingProcessor, f4, i * 10 - 20, n7, false, matrixStack.getLast().getMatrix(), iRenderTypeBuffer, false, 0, n);
            }
        }
        matrixStack.pop();
    }

    public static RenderMaterial getMaterial(Block block) {
        WoodType woodType = block instanceof AbstractSignBlock ? ((AbstractSignBlock)block).getWoodType() : WoodType.OAK;
        return Atlases.SIGN_MATERIALS.get(woodType);
    }

    private static boolean isRenderText(SignTileEntity signTileEntity) {
        if (Shaders.isShadowPass) {
            return true;
        }
        if (!Config.zoomMode) {
            BlockPos blockPos = signTileEntity.getPos();
            Entity entity2 = Minecraft.getInstance().getRenderViewEntity();
            double d = entity2.getDistanceSq(blockPos.getX(), blockPos.getY(), blockPos.getZ());
            if (d > textRenderDistanceSq) {
                return true;
            }
        }
        return false;
    }

    public static void updateTextRenderDistance() {
        Minecraft minecraft = Minecraft.getInstance();
        double d = Config.limit(minecraft.gameSettings.fov, 1.0, 120.0);
        double d2 = Math.max(1.5 * (double)minecraft.getMainWindow().getHeight() / d, 16.0);
        textRenderDistanceSq = d2 * d2;
    }

    @Override
    public void render(TileEntity tileEntity, float f, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n, int n2) {
        this.render((SignTileEntity)tileEntity, f, matrixStack, iRenderTypeBuffer, n, n2);
    }

    private static IReorderingProcessor lambda$render$0(FontRenderer fontRenderer, ITextComponent iTextComponent) {
        List<IReorderingProcessor> list = fontRenderer.trimStringToWidth(iTextComponent, 90);
        return list.isEmpty() ? IReorderingProcessor.field_242232_a : list.get(0);
    }

    public static final class SignModel
    extends Model {
        public final ModelRenderer signBoard = new ModelRenderer(64, 32, 0, 0);
        public final ModelRenderer signStick;

        public SignModel() {
            super(RenderType::getEntityCutoutNoCull);
            this.signBoard.addBox(-12.0f, -14.0f, -1.0f, 24.0f, 12.0f, 2.0f, 0.0f);
            this.signStick = new ModelRenderer(64, 32, 0, 14);
            this.signStick.addBox(-1.0f, -2.0f, -1.0f, 2.0f, 14.0f, 2.0f, 0.0f);
        }

        @Override
        public void render(MatrixStack matrixStack, IVertexBuilder iVertexBuilder, int n, int n2, float f, float f2, float f3, float f4) {
            this.signBoard.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
            this.signStick.render(matrixStack, iVertexBuilder, n, n2, f, f2, f3, f4);
        }
    }
}

