package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render3DEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.modules.render.hud.HUD;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.ColorValue;
import club.bluezenith.module.value.types.ModeValue;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import org.lwjgl.opengl.GL11;

import java.awt.*;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.module.value.builders.AbstractBuilder.createMode;

public class Tracers extends Module {
    private static final int TARGET_COLOR = new Color(225, 44, 44).getRGB();

    private final BooleanValue targetOnly = new BooleanValue("Targets only", false).setIndex(1);

    private final ModeValue colorMode = createMode("Color mode")
            .range("HUD, White, Custom")
            .index(2)
            .build();

    //whether the color should be overridden if the entity is a target
    private final BooleanValue colorOverride = new BooleanValue("Override for targets", true).setIndex(4);

    private final ColorValue customColor = new ColorValue("Color").setIndex(3).showIf(() -> colorMode.is("Custom"));


    public Tracers() {
        super("Tracers", ModuleCategory.RENDER);
    }

    @Listener
    public void onRender3D(Render3DEvent event) {
        final RenderManager renderManager = mc.getRenderManager();
        final float ticks = mc.timer.renderPartialTicks;

        mc.theWorld.playerEntities.forEach(player -> {
            if(player == mc.thePlayer) return;
            boolean isTarget = getBlueZenith().getTargetManager().isTarget(player.getGameProfile().getName());
            if(targetOnly.get() && !isTarget) return;
            if(player.isInvisible() || !player.isEntityAlive()) return;

            int color = -1;

            if(colorMode.is("HUD"))
                color = HUD.module.getColor(1);
            else if(colorMode.is("Custom"))
                color = customColor.getRGB();

            if(colorOverride.get() && isTarget)
                color = TARGET_COLOR;

            final float x = (float) ((player.prevPosX + (player.posX - player.prevPosX) * ticks) - renderManager.viewerPosX),
                        y = (float) ((player.prevPosY + (player.posY - player.prevPosY) * ticks) - renderManager.viewerPosY),
                        z = (float) ((player.prevPosZ + (player.posZ - player.prevPosZ) * ticks) - renderManager.viewerPosZ);

            GlStateManager.pushMatrix();
            GlStateManager.loadIdentity();
            GlStateManager.disableDepth();
            final boolean wasBobbingEnabled = mc.gameSettings.viewBobbing;
            mc.gameSettings.viewBobbing = false; //disable bobbing so lines arent shaky when moving
            mc.entityRenderer.setupCameraTransform(ticks, 0);
            mc.gameSettings.viewBobbing = wasBobbingEnabled; //re enable if needed as we already setup the camera
            RenderUtil.start2D(GL11.GL_LINE_STRIP);
            RenderUtil.glColor(color);
            GL11.glVertex3d(0, mc.thePlayer.getEyeHeight(), 0);
            GL11.glVertex3d(x, y, z);
            RenderUtil.end2D();
            GlStateManager.enableDepth();
            GlStateManager.popMatrix();
        });
    }

}

