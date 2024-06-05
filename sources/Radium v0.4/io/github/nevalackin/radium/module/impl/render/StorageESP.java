package io.github.nevalackin.radium.module.impl.render;

import io.github.nevalackin.radium.event.impl.render.Render3DEvent;
import io.github.nevalackin.radium.module.Module;
import io.github.nevalackin.radium.module.ModuleCategory;
import io.github.nevalackin.radium.module.ModuleInfo;
import io.github.nevalackin.radium.property.Property;
import io.github.nevalackin.radium.property.impl.DoubleProperty;
import io.github.nevalackin.radium.utils.Wrapper;
import io.github.nevalackin.radium.utils.render.OGLUtils;
import me.zane.basicbus.api.annotations.Listener;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.tileentity.TileEntityEnderChest;
import net.minecraft.util.BlockPos;

import static org.lwjgl.opengl.GL11.*;

@ModuleInfo(label = "Storage ESP", category = ModuleCategory.RENDER)
public final class StorageESP extends Module {

    private final Property<Boolean> outlineProperty = new Property<>("Outline", false);
    private final DoubleProperty alphaProperty = new DoubleProperty("Alpha", 0.5, 0.0, 1.0, 0.1);

    @Listener
    public void onRender3DEvent(Render3DEvent event) {
        for (TileEntity entity : Wrapper.getWorld().loadedTileEntityList) {
            int color = -1;
            if (entity instanceof TileEntityChest)
                color = 0xFFAA00;
            else if (entity instanceof TileEntityEnderChest)
                color = 0x6A00FF;

            if (color != -1) {
                BlockPos pos = entity.getPos();
                glPushMatrix();
                glDisable(GL_DEPTH_TEST);
                OGLUtils.startBlending();
                glDepthMask(false);
                glDisable(GL_TEXTURE_2D);
                glColor4ub((byte) (color >> 16 & 255),
                        (byte) (color >> 8 & 255),
                        (byte) (color & 255),
                        (byte) (alphaProperty.getValue().floatValue() * 255));
                boolean outline = outlineProperty.getValue();
                if (outline) {
                    glLineWidth(1.0F);
                    glEnable(GL_LINE_SMOOTH);
                    glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
                }
                glTranslated(-RenderManager.renderPosX,
                        -RenderManager.renderPosY,
                        -RenderManager.renderPosZ);
                RenderGlobal.func_181561_a(
                        entity.getBlockType().getCollisionBoundingBox(
                                Wrapper.getWorld(),
                                pos,
                                entity.getBlockType().getStateFromMeta(
                                        entity.getBlockMetadata())), outline, true);
                glEnable(GL_DEPTH_TEST);
                OGLUtils.endBlending();
                glDepthMask(true);
                if (outline)
                    glDisable(GL_LINE_SMOOTH);
                glEnable(GL_TEXTURE_2D);
                glPopMatrix();
            }
        }
    }

}
