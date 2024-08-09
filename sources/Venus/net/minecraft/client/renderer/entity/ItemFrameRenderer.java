/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Atlases;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.model.ModelManager;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.storage.MapData;
import net.optifine.Config;
import net.optifine.reflect.Reflector;
import net.optifine.reflect.ReflectorForge;
import net.optifine.shaders.Shaders;

public class ItemFrameRenderer
extends EntityRenderer<ItemFrameEntity> {
    private static final ModelResourceLocation LOCATION_MODEL = new ModelResourceLocation("item_frame", "map=false");
    private static final ModelResourceLocation LOCATION_MODEL_MAP = new ModelResourceLocation("item_frame", "map=true");
    private final Minecraft mc = Minecraft.getInstance();
    private final ItemRenderer itemRenderer;
    private static double itemRenderDistanceSq = 4096.0;

    public ItemFrameRenderer(EntityRendererManager entityRendererManager, ItemRenderer itemRenderer) {
        super(entityRendererManager);
        this.itemRenderer = itemRenderer;
    }

    @Override
    public void render(ItemFrameEntity itemFrameEntity, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        Object object;
        super.render(itemFrameEntity, f, f2, matrixStack, iRenderTypeBuffer, n);
        matrixStack.push();
        Direction direction = itemFrameEntity.getHorizontalFacing();
        Vector3d vector3d = this.getRenderOffset(itemFrameEntity, f2);
        matrixStack.translate(-vector3d.getX(), -vector3d.getY(), -vector3d.getZ());
        double d = 0.46875;
        matrixStack.translate((double)direction.getXOffset() * 0.46875, (double)direction.getYOffset() * 0.46875, (double)direction.getZOffset() * 0.46875);
        matrixStack.rotate(Vector3f.XP.rotationDegrees(itemFrameEntity.rotationPitch));
        matrixStack.rotate(Vector3f.YP.rotationDegrees(180.0f - itemFrameEntity.rotationYaw));
        boolean bl = itemFrameEntity.isInvisible();
        if (!bl) {
            object = this.mc.getBlockRendererDispatcher();
            ModelManager modelManager = ((BlockRendererDispatcher)object).getBlockModelShapes().getModelManager();
            ModelResourceLocation modelResourceLocation = itemFrameEntity.getDisplayedItem().getItem() instanceof FilledMapItem ? LOCATION_MODEL_MAP : LOCATION_MODEL;
            matrixStack.push();
            matrixStack.translate(-0.5, -0.5, -0.5);
            ((BlockRendererDispatcher)object).getBlockModelRenderer().renderModelBrightnessColor(matrixStack.getLast(), iRenderTypeBuffer.getBuffer(Atlases.getSolidBlockType()), null, modelManager.getModel(modelResourceLocation), 1.0f, 1.0f, 1.0f, n, OverlayTexture.NO_OVERLAY);
            matrixStack.pop();
        }
        if (!((ItemStack)(object = itemFrameEntity.getDisplayedItem())).isEmpty()) {
            boolean bl2 = ((ItemStack)object).getItem() instanceof FilledMapItem;
            if (bl) {
                matrixStack.translate(0.0, 0.0, 0.5);
            } else {
                matrixStack.translate(0.0, 0.0, 0.4375);
            }
            int n2 = bl2 ? itemFrameEntity.getRotation() % 4 * 2 : itemFrameEntity.getRotation();
            matrixStack.rotate(Vector3f.ZP.rotationDegrees((float)n2 * 360.0f / 8.0f));
            if (!Reflector.postForgeBusEvent(Reflector.RenderItemInFrameEvent_Constructor, itemFrameEntity, this, matrixStack, iRenderTypeBuffer, n)) {
                if (bl2) {
                    matrixStack.rotate(Vector3f.ZP.rotationDegrees(180.0f));
                    float f3 = 0.0078125f;
                    matrixStack.scale(0.0078125f, 0.0078125f, 0.0078125f);
                    matrixStack.translate(-64.0, -64.0, 0.0);
                    MapData mapData = ReflectorForge.getMapData((ItemStack)object, itemFrameEntity.world);
                    matrixStack.translate(0.0, 0.0, -1.0);
                    if (mapData != null) {
                        this.mc.gameRenderer.getMapItemRenderer().renderMap(matrixStack, iRenderTypeBuffer, mapData, true, n);
                    }
                } else {
                    matrixStack.scale(0.5f, 0.5f, 0.5f);
                    if (this.isRenderItem(itemFrameEntity)) {
                        this.itemRenderer.renderItem((ItemStack)object, ItemCameraTransforms.TransformType.FIXED, n, OverlayTexture.NO_OVERLAY, matrixStack, iRenderTypeBuffer);
                    }
                }
            }
        }
        matrixStack.pop();
    }

    @Override
    public Vector3d getRenderOffset(ItemFrameEntity itemFrameEntity, float f) {
        return new Vector3d((float)itemFrameEntity.getHorizontalFacing().getXOffset() * 0.3f, -0.25, (float)itemFrameEntity.getHorizontalFacing().getZOffset() * 0.3f);
    }

    @Override
    public ResourceLocation getEntityTexture(ItemFrameEntity itemFrameEntity) {
        return AtlasTexture.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    protected boolean canRenderName(ItemFrameEntity itemFrameEntity) {
        if (Minecraft.isGuiEnabled() && !itemFrameEntity.getDisplayedItem().isEmpty() && itemFrameEntity.getDisplayedItem().hasDisplayName() && this.renderManager.pointedEntity == itemFrameEntity) {
            double d = this.renderManager.squareDistanceTo(itemFrameEntity);
            float f = itemFrameEntity.isDiscrete() ? 32.0f : 64.0f;
            return d < (double)(f * f);
        }
        return true;
    }

    @Override
    protected void renderName(ItemFrameEntity itemFrameEntity, ITextComponent iTextComponent, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        super.renderName(itemFrameEntity, itemFrameEntity.getDisplayedItem().getDisplayName(), matrixStack, iRenderTypeBuffer, n);
    }

    private boolean isRenderItem(ItemFrameEntity itemFrameEntity) {
        Entity entity2;
        double d;
        if (Shaders.isShadowPass) {
            return true;
        }
        return !Config.zoomMode && (d = itemFrameEntity.getDistanceSq((entity2 = this.mc.getRenderViewEntity()).getPosX(), entity2.getPosY(), entity2.getPosZ())) > itemRenderDistanceSq;
    }

    public static void updateItemRenderDistance() {
        Minecraft minecraft = Minecraft.getInstance();
        double d = Config.limit(minecraft.gameSettings.fov, 1.0, 120.0);
        double d2 = Math.max(6.0 * (double)minecraft.getMainWindow().getHeight() / d, 16.0);
        itemRenderDistanceSq = d2 * d2;
    }

    @Override
    protected void renderName(Entity entity2, ITextComponent iTextComponent, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.renderName((ItemFrameEntity)entity2, iTextComponent, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public ResourceLocation getEntityTexture(Entity entity2) {
        return this.getEntityTexture((ItemFrameEntity)entity2);
    }

    @Override
    protected boolean canRenderName(Entity entity2) {
        return this.canRenderName((ItemFrameEntity)entity2);
    }

    @Override
    public void render(Entity entity2, float f, float f2, MatrixStack matrixStack, IRenderTypeBuffer iRenderTypeBuffer, int n) {
        this.render((ItemFrameEntity)entity2, f, f2, matrixStack, iRenderTypeBuffer, n);
    }

    @Override
    public Vector3d getRenderOffset(Entity entity2, float f) {
        return this.getRenderOffset((ItemFrameEntity)entity2, f);
    }
}

