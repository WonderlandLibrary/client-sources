package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render3DPosedLastEvent;
import dev.excellent.api.interfaces.client.IRenderAccess;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.render.color.ColorUtil;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EnderPearlEntity;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.mojang.blaze3d.systems.RenderSystem;
import org.lwjgl.opengl.GL11;

@ModuleInfo(name = "Pearl Predict", description = "Отображает траекторию падения эндер жемчуга", category = Category.RENDER)
public class PearlPredict extends Module implements IRenderAccess {
    private final Listener<Render3DPosedLastEvent> onRender3D = event -> {
        RenderSystem.pushMatrix();
        RenderSystem.translated(-mc.getRenderManager().renderPosX(), -mc.getRenderManager().renderPosY(), -mc.getRenderManager().renderPosZ());
        RenderSystem.enableBlend();
        RenderSystem.disableTexture();
        RenderSystem.disableDepthTest();
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        RenderSystem.lineWidth(2);
        BUFFER.begin(1, DefaultVertexFormats.POSITION_COLOR);

        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof EnderPearlEntity pearl) {
                renderLine(pearl);
            }
        }
        TESSELLATOR.draw();
        RenderSystem.enableDepthTest();
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        RenderSystem.popMatrix();
    };

    private void renderLine(EnderPearlEntity pearl) {

        Vector3d pearlPosition = pearl.getPositionVec().add(0, 0, 0);
        Vector3d pearlMotion = pearl.getMotion();
        Vector3d lastPosition;

        for (int i = 0; i <= 300; i++) {
            lastPosition = pearlPosition;
            pearlPosition = pearlPosition.add(pearlMotion);
            pearlMotion = updatePearlMotion(pearl, pearlMotion);

            if (shouldEntityHit(pearlPosition.add(0, 1, 0), lastPosition.add(0, 1, 0)) || pearlPosition.y <= 0) {
                break;
            }

            int color = getTheme().getClientColor(i * 10);

            float red, green, blue;

            red = ColorUtil.redf(color);
            green = ColorUtil.greenf(color);
            blue = ColorUtil.bluef(color);

            BUFFER.pos((float) lastPosition.x, (float) lastPosition.y, (float) lastPosition.z)
                    .color(red, green, blue, 1)
                    .endVertex();
            BUFFER.pos((float) pearlPosition.x, (float) pearlPosition.y, (float) pearlPosition.z)
                    .color(red, green, blue, 1)
                    .endVertex();
        }
    }

    private Vector3d updatePearlMotion(EnderPearlEntity pearl, Vector3d originalPearlMotion) {
        Vector3d pearlMotion = originalPearlMotion;
        if (pearl.isInWater()) {
            pearlMotion = pearlMotion.scale(0.8f);
        } else {
            pearlMotion = pearlMotion.scale(0.99f);
        }

        if (!pearl.hasNoGravity())
            pearlMotion.y -= pearl.getGravityVelocity();

        return pearlMotion;
    }

    private boolean shouldEntityHit(Vector3d pearlPosition, Vector3d lastPosition) {
        final RayTraceContext rayTraceContext = new RayTraceContext(
                lastPosition,
                pearlPosition,
                RayTraceContext.BlockMode.COLLIDER,
                RayTraceContext.FluidMode.NONE,
                mc.player
        );
        final BlockRayTraceResult blockHitResult = mc.world.rayTraceBlocks(rayTraceContext);

        return blockHitResult.getType() == RayTraceResult.Type.BLOCK;
    }

}
