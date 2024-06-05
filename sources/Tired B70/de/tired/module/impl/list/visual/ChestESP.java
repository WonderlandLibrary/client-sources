package de.tired.module.impl.list.visual;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.extension.Extension;
import de.tired.api.extension.processors.extensions.generally.RenderProcessor;
import de.tired.event.EventTarget;
import de.tired.event.events.Render3DEvent;
import de.tired.event.events.Render3DEvent2;
import de.tired.event.events.Render3DEventPRE;
import de.tired.module.Module;
import de.tired.module.ModuleCategory;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnnotation(name = "ChestESP", category = ModuleCategory.RENDER)
public class ChestESP extends Module {

    @EventTarget
    public void onRender3D(Render3DEvent2 event) {
        for (TileEntity e : MC.theWorld.loadedTileEntityList) {

            final double x = (double) e.getPos().getX() - RenderManager.renderPosX;
            final double y = (double) e.getPos().getY() - RenderManager.renderPosY;
            final double z = (double) e.getPos().getZ() - RenderManager.renderPosZ;

            GL11.glPushMatrix();
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_LINE_SMOOTH);
            GL11.glEnable(GL11.GL_CULL_FACE);
            GL11.glDisable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(false);
            RenderProcessor.glColor(new Color(ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getRed(),ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getGreen(),ClickGUI.getInstance().colorPickerSetting.ColorPickerC.getBlue(), 85).getRGB());
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawBoundingBox(new AxisAlignedBB(x + e.getBlockType().getBlockBoundsMinX(), y + e.getBlockType().getBlockBoundsMinY(), z + e.getBlockType().getBlockBoundsMinZ(), x + e.getBlockType().getBlockBoundsMaxX(), y + e.getBlockType().getBlockBoundsMaxY(), z + e.getBlockType().getBlockBoundsMaxZ()));
            Extension.EXTENSION.getGenerallyProcessor().renderProcessor.drawOutlinedBoundingBox(new AxisAlignedBB(x + e.getBlockType().getBlockBoundsMinX(), y + e.getBlockType().getBlockBoundsMinY(), z + e.getBlockType().getBlockBoundsMinZ(), x + e.getBlockType().getBlockBoundsMaxX(), y + e.getBlockType().getBlockBoundsMaxY(), z + e.getBlockType().getBlockBoundsMaxZ()));
            GL11.glDisable(GL11.GL_LINE_SMOOTH);
            GL11.glDisable(GL11.GL_CULL_FACE);
            GL11.glEnable(GL11.GL_TEXTURE_2D);
            GL11.glEnable(GL11.GL_DEPTH_TEST);
            GL11.glDepthMask(true);
            GL11.glDisable(GL11.GL_BLEND);
            GL11.glPopMatrix();

        }
    }


    @Override
    public void onState() {

    }

    @Override
    public void onUndo() {

    }
}
