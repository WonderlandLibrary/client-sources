package net.minecraft.client.renderer.entity;

import club.strifeclient.Client;
import club.strifeclient.module.implementations.visual.ItemPhysics;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import java.util.Random;

import static org.lwjgl.opengl.GL11.glRotatef;

public class RenderEntityItem extends Render<EntityItem> {
    private final RenderItem itemRenderer;
    private final Random field_177079_e = new Random();

    public RenderEntityItem(RenderManager renderManagerIn, RenderItem p_i46167_2_) {
        super(renderManagerIn);
        this.itemRenderer = p_i46167_2_;
        this.shadowSize = 0.15F;
        this.shadowOpaque = 0.75F;
    }

    private int func_177077_a(EntityItem itemIn, double p_177077_2_, double p_177077_4_, double p_177077_6_, float p_177077_8_, IBakedModel model) {
        ItemStack itemstack = itemIn.getEntityItem();
        Item item = itemstack.getItem();

        if (item == null) {
            return 0;
        } else {
            if (physics == null)
                physics = Client.INSTANCE.getModuleManager().getModule(ItemPhysics.class);

            boolean flag = model.isGui3d();
            int i = this.getMergedStackCount(itemstack);
            float f = 0.25F;
            float f1 = MathHelper.sin(((float) itemIn.getAge() + p_177077_8_) / 10.0F + itemIn.hoverStart) * 0.1F + 0.1F;
            final ItemPhysics.ItemRotateMode rotateMode = physics.rotateModeSetting.getValue();
            if (physics.isEnabled()) {
                f1 = 0;
                f = itemIn.getEntityItem().getItem() instanceof ItemBlock ? 0.12F : rotateMode == ItemPhysics.ItemRotateMode.NONE ? 0.21F : 0.01F;
            }
            float f2 = model.getItemCameraTransforms().getTransform(ItemCameraTransforms.TransformType.GROUND).scale.y;
            GlStateManager.translate((float) p_177077_2_, (float) p_177077_4_ + f1 + f * f2, (float) p_177077_6_);

            if (flag || this.renderManager.options != null) {
                float f3 = (((float) itemIn.getAge() + p_177077_8_) / 20.0F + itemIn.hoverStart) * (180F / (float) Math.PI);
                if (!physics.isEnabled() || rotateMode == ItemPhysics.ItemRotateMode.NATURAL)
                    GlStateManager.rotate(f3, 0.0F, 1.0F, 0.0F);
            }

            if (!flag) {
                float f6 = -0.0F * (float) (i - 1) * 0.5F;
                float f4 = -0.0F * (float) (i - 1) * 0.5F;
                float f5 = -0.046875F * (float) (i - 1) * 0.5F;
                GlStateManager.translate(f6, f4, f5);
            }

            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            return i;
        }
    }

    private int getMergedStackCount(ItemStack stack) {
        int i = 1;

        if (stack.stackSize > 48) {
            i = 5;
        } else if (stack.stackSize > 32) {
            i = 4;
        } else if (stack.stackSize > 16) {
            i = 3;
        } else if (stack.stackSize > 1) {
            i = 2;
        }

        return i;
    }

    private ItemPhysics physics;

    public void doRender(EntityItem entity, double x, double y, double z, float entityYaw, float partialTicks) {
        ItemStack itemstack = entity.getEntityItem();
        this.field_177079_e.setSeed(187L);
        boolean flag = false;

        if (this.bindEntityTexture(entity)) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).setBlurMipmap(false, false);
            flag = true;
        }

        GlStateManager.enableRescaleNormal();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
        GlStateManager.pushMatrix();
        IBakedModel ibakedmodel = this.itemRenderer.getItemModelMesher().getItemModel(itemstack);
        int i = this.func_177077_a(entity, x, y, z, partialTicks, ibakedmodel);

        if (physics == null)
            physics = Client.INSTANCE.getModuleManager().getModule(ItemPhysics.class);

        int rotationSpeed = 10000;
        int rotation = (int) ((System.currentTimeMillis() + entity.getEntityId() * rotationSpeed) % 360);
        if (physics.isEnabled()) {
            final ItemPhysics.ItemRotateMode rotateMode = physics.rotateModeSetting.getValue();
            if (rotateMode != ItemPhysics.ItemRotateMode.NONE) {
                if (entity.onGround)
                    rotation = 90;
                glRotatef(rotation / 2f, 0, 1, 0);
                glRotatef(rotation, 1, 0, 0);
            }
        }

        for (int j = 0; j < i; ++j) {
            if (ibakedmodel.isGui3d()) {
                GlStateManager.pushMatrix();

                if (j > 0) {
                    float f = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f1 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    float f2 = (this.field_177079_e.nextFloat() * 2.0F - 1.0F) * 0.15F;
                    GlStateManager.translate(f, f1, f2);
                }

                GlStateManager.scale(0.5F, 0.5F, 0.5F);
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
            } else {
                GlStateManager.pushMatrix();
                ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GROUND);
                this.itemRenderer.renderItem(itemstack, ibakedmodel);
                GlStateManager.popMatrix();
                float f3 = ibakedmodel.getItemCameraTransforms().ground.scale.x;
                float f4 = ibakedmodel.getItemCameraTransforms().ground.scale.y;
                float f5 = ibakedmodel.getItemCameraTransforms().ground.scale.z;
                GlStateManager.translate(0.0F * f3, 0.0F * f4, 0.046875F * f5);
            }
        }

        GlStateManager.popMatrix();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableBlend();
        this.bindEntityTexture(entity);

        if (flag) {
            this.renderManager.renderEngine.getTexture(this.getEntityTexture(entity)).restoreLastBlurMipmap();
        }

        super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }

    protected ResourceLocation getEntityTexture(EntityItem entity) {
        return TextureMap.locationBlocksTexture;
    }
}
