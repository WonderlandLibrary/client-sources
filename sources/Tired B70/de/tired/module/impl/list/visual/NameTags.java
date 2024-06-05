package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.extension.Extension;
import de.tired.api.util.font.CustomFont;
import de.tired.api.util.font.FontManager;
import de.tired.event.EventTarget;
import de.tired.event.events.Render2DEvent;
import de.tired.event.events.Render3DEvent;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import de.tired.module.ModuleManager;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntityItemStackRenderer;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.util.Arrays;
import java.util.List;

@ModuleAnnotation(name = "NameTags", category = ModuleCategory.RENDER, clickG = "Render big name over players")
public class NameTags extends Module {

    public float partialTicks;

    private double animationX = 0;

    @EventTarget
    public void onRender(Render2DEvent e) {
        partialTicks = e.getPartialTicks();

    }

    public void onRender2(Render3DEvent e) {

    }

    public static NameTags getInstance() {
        return ModuleManager.getInstance(NameTags.class);
    }

    public void doRenderFinal(boolean rect) {
        for (EntityPlayer player : MC.theWorld.playerEntities) {
            if (player == null || player.equals(MC.thePlayer) || !player.isEntityAlive() || player.isInvisible() || !Extension.EXTENSION.getGenerallyProcessor().renderProcessor.isInViewFrustrum(player) || player.getDistanceToEntity(MC.thePlayer) < 10)
                continue;
            final double xAxis = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.interpolate(player.posX, player.lastTickPosX, partialTicks);
            final double yAxis = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.interpolate(player.posY, player.lastTickPosY, partialTicks);
            final double zAxis = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.interpolate(player.posZ, player.lastTickPosZ, partialTicks);
            this.renderReal2D(player, xAxis, yAxis, zAxis, partialTicks, rect);
        }

    }

    public final void renderReal2D(EntityPlayer player, double x, double y, double z, float delta, boolean rect) {
        Entity camera = MC.getRenderViewEntity();
        assert (camera != null);
        MC.entityRenderer.setupCameraTransform(delta, 0);

        final float width = FontManager.IBMPlexSans.getStringWidth(player.getName()) / 20;

        final float height = .3F;

        final AxisAlignedBB aabb = new AxisAlignedBB(x - width, y, z - width, x + width, y + height + 0.05, z + width);
        final List<Vector3d> vectors = Arrays.asList(new Vector3d(aabb.minX, aabb.minY, aabb.minZ), new Vector3d(aabb.minX, aabb.maxY, aabb.minZ), new Vector3d(aabb.maxX, aabb.minY, aabb.minZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.minZ), new Vector3d(aabb.minX, aabb.minY, aabb.maxZ), new Vector3d(aabb.minX, aabb.maxY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.minY, aabb.maxZ), new Vector3d(aabb.maxX, aabb.maxY, aabb.maxZ));

        MC.entityRenderer.setupCameraTransform(delta, 0);
        Vector4d position = null;

        for (Vector3d vector : vectors) {
            vector = Extension.EXTENSION.getGenerallyProcessor().renderProcessor.project(vector.x - MC.getRenderManager().viewerPosX, vector.y - MC.getRenderManager().viewerPosY, vector.z - MC.getRenderManager().viewerPosZ);
            if (vector != null && vector.z >= 0.0 && vector.z < 1.0) {
                if (position == null) {
                    position = new Vector4d(vector.x, vector.y, vector.z, 0.0);
                }
                position.x = Math.min(vector.x, position.x);
                position.y = Math.min(vector.y, position.y);
                position.z = Math.max(vector.x, position.z);
                position.w = Math.max(vector.y, position.w);
            }
        }

        double durabilityWidth;
        double textWidth;
        float scale = 1;
        float upscale = 1.0f;
        MC.entityRenderer.setupOverlayRendering();
        if (position != null) {
            GL11.glPushMatrix();
            final double posX = position.x;
            final double posY = position.y;
            final double endPosX = position.z;
            String name = player.getGameProfile().getName();


            durabilityWidth = (endPosX - posX) / 2.0D;
            textWidth = (float) MC.fontRendererObj.getStringWidth(name) * scale;
            float tagX = (float) ((posX + durabilityWidth - textWidth / 2.0D) * (double) upscale);


            GL11.glScalef(scale, scale, scale);

            int yAxis = 51;

            if (rect) {
               Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlineRect(tagX - 2, (float)posY - yAxis, (float)tagX + (float)textWidth + 2, (float) posY - 34, Integer.MIN_VALUE);
            }
            MC.fontRendererObj.drawStringWithShadow(name, tagX, (float) (posY - yAxis) + 4, -1);
            GL11.glPopMatrix();

        }

    }

    public int calculateMiddle(String text, CustomFont fontRenderer, int x, float widht) {
        return (int) ((float) (x + widht) - (fontRenderer.getStringWidth(text) / 2f) - (float) widht / 2);
    }


    public void renderItem(ItemStack item, int xPos, int yPos, int zPos) {
        GL11.glPushMatrix();
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableAlpha();
        GlStateManager.alphaFunc(516, 0.1F);
        GlStateManager.enableBlend();
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        IBakedModel ibakedmodel = MC.getRenderItem().getItemModelMesher().getItemModel(item);
        MC.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
        GlStateManager.scale(16.0F, 16.0F, 0.0F);
        GL11.glTranslated((double)(((float)xPos - 7.85F) / 16.0F), (double)((float)(-5 + yPos) / 16.0F), (double)((float)zPos / 16.0F));
        GlStateManager.rotate(180.0F, 1.0F, 0.0F, 0.0F);
        GlStateManager.disableLighting();
        ibakedmodel.getItemCameraTransforms().applyTransform(ItemCameraTransforms.TransformType.GUI);
        if (ibakedmodel.isBuiltInRenderer()) {
            GlStateManager.rotate(180.0F, 0.0F, 1.0F, 0.0F);
            GlStateManager.translate(-0.5F, -0.5F, -0.5F);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableRescaleNormal();
            TileEntityItemStackRenderer.instance.renderByItem(item);
        } else {
            MC.getRenderItem().renderModel(ibakedmodel, -1);
        }

        GlStateManager.enableAlpha();
        GlStateManager.disableRescaleNormal();
        GlStateManager.disableLighting();
        GlStateManager.popMatrix();
    }

    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
