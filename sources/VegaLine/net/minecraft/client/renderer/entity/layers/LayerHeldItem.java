/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity.layers;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumHandSide;
import org.lwjgl.opengl.GL11;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.impl.Panic;

public class LayerHeldItem
implements LayerRenderer<EntityLivingBase> {
    protected final RenderLivingBase<?> livingEntityRenderer;

    public LayerHeldItem(RenderLivingBase<?> livingEntityRendererIn) {
        this.livingEntityRenderer = livingEntityRendererIn;
    }

    @Override
    public void doRenderLayer(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
        ItemStack itemstack1;
        boolean flag = entitylivingbaseIn.getPrimaryHand() == EnumHandSide.RIGHT;
        ItemStack itemstack = flag ? entitylivingbaseIn.getHeldItemOffhand() : entitylivingbaseIn.getHeldItemMainhand();
        ItemStack itemStack = itemstack1 = flag ? entitylivingbaseIn.getHeldItemMainhand() : entitylivingbaseIn.getHeldItemOffhand();
        if (!itemstack.isEmpty() || !itemstack1.isEmpty()) {
            GlStateManager.pushMatrix();
            if (this.livingEntityRenderer.getMainModel().isChild) {
                float f = 0.5f;
                GlStateManager.translate(0.0f, 0.75f, 0.0f);
                GlStateManager.scale(0.5f, 0.5f, 0.5f);
            }
            Runnable renderModel = () -> {
                this.renderHeldItem(entitylivingbaseIn, itemstack1, ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND, EnumHandSide.RIGHT);
                this.renderHeldItem(entitylivingbaseIn, itemstack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND, EnumHandSide.LEFT);
            };
            Client.moduleManager.modules.stream().filter(m -> m.actived).forEach(m -> m.preRenderLivingBase(entitylivingbaseIn, renderModel, true));
            renderModel.run();
            Client.moduleManager.modules.stream().filter(m -> m.actived).forEach(m -> m.postRenderLivingBase(entitylivingbaseIn, renderModel, true));
            GlStateManager.popMatrix();
        }
    }

    private void renderHeldItem(EntityLivingBase p_188358_1_, ItemStack p_188358_2_, ItemCameraTransforms.TransformType p_188358_3_, EnumHandSide handSide) {
        if (!p_188358_2_.isEmpty()) {
            EnumHandSide s;
            GlStateManager.pushMatrix();
            this.func_191361_a(handSide);
            if (p_188358_1_.isSneaking()) {
                GlStateManager.translate(0.0f, 0.2f, 0.0f);
            }
            GlStateManager.rotate(-90.0f, 1.0f, 0.0f, 0.0f);
            GlStateManager.rotate(180.0f, 0.0f, 1.0f, 0.0f);
            boolean flag = handSide == EnumHandSide.LEFT;
            GlStateManager.translate((float)(flag ? -1 : 1) / 16.0f, 0.125f, -0.625f);
            int modelType = 0;
            if (p_188358_1_ instanceof EntityPlayer) {
                EntityPlayer player = (EntityPlayer)p_188358_1_;
                if (modelType == 1) {
                    GlStateManager.translate(0.0f, 0.3f, 0.0f);
                }
                if (modelType == 3) {
                    GlStateManager.translate(flag ? -0.1f : 0.1f, 0.0f, -0.3f);
                }
                if (modelType == 4) {
                    GlStateManager.translate(0.0f, 0.0f, -0.3f);
                }
            }
            EnumHandSide enumHandSide = s = p_188358_1_.getActiveHand() == EnumHand.MAIN_HAND ? EnumHandSide.RIGHT : EnumHandSide.LEFT;
            if (!Panic.stop && p_188358_1_.isEating() && (p_188358_1_.getActiveHand() == EnumHand.OFF_HAND || !flag && p_188358_1_.getActiveHand() == EnumHand.MAIN_HAND) && handSide == s) {
                float tts = 5.0f;
                float upPC = ((float)(p_188358_1_.getItemInUseCount() + 1) - 1.0f * Minecraft.getMinecraft().getRenderPartialTicks()) % tts / tts;
                upPC = (double)upPC > 0.5 ? 1.0f - upPC : upPC;
                int side = flag ? 1 : -1;
                GL11.glRotated(-(30.0f + upPC * 5.0f), 1.0, 0.0, 0.0);
                GL11.glRotated(-10 * side, 0.0, 0.0, 1.0);
                GL11.glTranslated(0.2f * (float)side, 0.45f, 0.4f);
                GL11.glRotated(80.0, 1.0, 0.0, 0.0);
                GL11.glRotated(20 * side, 0.0, 0.0, -1.0);
            }
            Minecraft.getMinecraft().getItemRenderer().renderItemSide(p_188358_1_, p_188358_2_, p_188358_3_, flag);
            GlStateManager.popMatrix();
        }
    }

    protected void func_191361_a(EnumHandSide p_191361_1_) {
        ((ModelBiped)this.livingEntityRenderer.getMainModel()).postRenderArm(0.0625f, p_191361_1_);
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }
}

