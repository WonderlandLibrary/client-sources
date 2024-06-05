package club.mega.module.impl.visual;

import static org.lwjgl.opengl.GL11.*;

import club.mega.event.impl.EventRender3D;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.BooleanSetting;
import club.mega.module.setting.impl.ListSetting;
import club.mega.module.setting.impl.NumberSetting;
import club.mega.util.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import rip.hippo.lwjeb.annotation.Handler;

import javax.vecmath.Vector3d;
import javax.vecmath.Vector4d;
import java.awt.*;
import java.util.Arrays;
import java.util.List;

@Module.ModuleInfo(name = "ESP", description = "See players through walls", category = Category.VISUAL)
public class ESP extends Module {

    public final ListSetting mode = new ListSetting("Mode", this, new String[]{"Shader", "Real2D"});
    public final NumberSetting with = new NumberSetting("With", this, 0, 2, 0.3, 0.1, () -> mode.is("real2D"));
    private final BooleanSetting filled = new BooleanSetting("Filled", this, false, () -> mode.is("real2D"));
    private final BooleanSetting outline = new BooleanSetting("Outline", this, true, () -> mode.is("real2D"));
    public final NumberSetting outlineSize = new NumberSetting("Size", this, 0.1, 2, 0.5, 0.1, outline::get);

    @Handler
    public final void onRender(final EventRender3D eventRender3D) {
        if (!mode.is("Real2D"))
            return;

        glPushMatrix();
        final ScaledResolution resolution = new ScaledResolution(MC);
        final double resizable = resolution.getScaleFactor() / 2F;

        glScaled(resizable, resizable, resizable);

        for (final Entity e : MC.theWorld.loadedEntityList) {
            if (shouldExecute(e) && RenderUtil.isInViewFrustrum(e)) {

                final float partialTicks = eventRender3D.partialTicks;
                final double xAxis = RenderUtil.executeInterpolate(e.posX, e.lastTickPosX, partialTicks);
                final double yAxis = RenderUtil.executeInterpolate(e.posY, e.lastTickPosY, partialTicks);
                final double zAxis = RenderUtil.executeInterpolate(e.posZ, e.lastTickPosZ, partialTicks);
                double sneakFix = 0.2;

                if (e.isSneaking()) sneakFix = -0.3;

                final double width = with.getAsDouble(), height = e.height + sneakFix;
                final AxisAlignedBB alignedBB = new AxisAlignedBB(xAxis - width, yAxis, zAxis - width, xAxis + width, yAxis + height, zAxis + width);
                final List<Vector3d> vectors = Arrays.asList(new Vector3d(alignedBB.minX, alignedBB.minY, alignedBB.minZ), new Vector3d(alignedBB.minX, alignedBB.maxY, alignedBB.minZ), new Vector3d(alignedBB.maxX, alignedBB.minY, alignedBB.minZ), new Vector3d(alignedBB.maxX, alignedBB.maxY, alignedBB.minZ), new Vector3d(alignedBB.minX, alignedBB.minY, alignedBB.maxZ), new Vector3d(alignedBB.minX, alignedBB.maxY, alignedBB.maxZ), new Vector3d(alignedBB.maxX, alignedBB.minY, alignedBB.maxZ), new Vector3d(alignedBB.maxX, alignedBB.maxY, alignedBB.maxZ));

                MC.entityRenderer.setupCameraTransform(partialTicks, 0);

                Vector4d position = null;
                for (Vector3d vector : vectors) {
                    vector = RenderUtil.executeProjection2D(resolution, vector.x - MC.getRenderManager().viewerPosX, vector.y - MC.getRenderManager().viewerPosY, vector.z - MC.getRenderManager().viewerPosZ);
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

                final Color color = Color.WHITE;
                final Color outlineColor = Color.BLACK;
                final double size = outlineSize.getAsDouble();

                MC.entityRenderer.setupOverlayRendering();

                if (position != null) {
                    final double x = position.x, y = position.y, renderWidth = position.z - position.x, renderHeight = position.w - position.y;

                    if (filled.get())
                    RenderUtil.drawRect(x, y, renderWidth, renderHeight, new Color(1,1,1, 140));

                    if (outline.get()) {
                        RenderUtil.drawOutlinedRect(x, y, renderWidth, 1, size, color, outlineColor);
                        RenderUtil.drawOutlinedRect(x, y + renderHeight, renderWidth, 0.5, size, color, outlineColor);
                        RenderUtil.drawOutlinedRect(x, y, 1, renderHeight, size, color, outlineColor);
                        RenderUtil.drawOutlinedRect(x + renderWidth, y, 1, renderHeight, size, color, outlineColor);
                    }

                    RenderUtil.drawRect(x, y, renderWidth, 1, color);
                    RenderUtil.drawRect(x, y + renderHeight, renderWidth + 1, 0.5, color);
                    RenderUtil.drawRect(x, y, 1, renderHeight, color);
                    RenderUtil.drawRect(x + renderWidth, y, 1, renderHeight, color);
                }
            }
        }
        glEnable(GL_BLEND);
        MC.entityRenderer.setupOverlayRendering();
        glPopMatrix();
    }

    private boolean shouldExecute(final Entity e) {
        return (e != MC.thePlayer || MC.gameSettings.thirdPersonView > 0) && e instanceof EntityPlayer;
    }

}
