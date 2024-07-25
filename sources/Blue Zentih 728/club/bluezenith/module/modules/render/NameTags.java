package club.bluezenith.module.modules.render;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.Render2DEvent;
import club.bluezenith.events.impl.RenderNameTagEvent;
import club.bluezenith.events.impl.UpdatePlayerEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.IntegerValue;
import club.bluezenith.util.math.MathUtil;
import club.bluezenith.util.math.Range;
import club.bluezenith.util.render.ColorUtil;
import club.bluezenith.util.render.RenderUtil;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;
import java.util.List;

import static club.bluezenith.module.value.builders.AbstractBuilder.createBoolean;
import static club.bluezenith.module.value.builders.AbstractBuilder.createInteger;
import static club.bluezenith.util.render.ColorUtil.setOpacity;

public class NameTags extends Module {
    private final IntegerValue backgroundAlpha = createInteger("Background alpha")
            .index(1)
            .range(Range.of(0, 150))
            .increment(1)
            .build();

    private final BooleanValue showHealth = createBoolean("Show health").index(2).build();

    private final BooleanValue shortenName = createBoolean("Shorter names").index(3).build();

    private final Frustum frustum = new Frustum();

    public NameTags() {
        super("NameTags", ModuleCategory.RENDER, "NameTags");
    }

    private final IntBuffer viewportBuffer = GLAllocation.createDirectIntBuffer(16);
    private final FloatBuffer modelViewBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer projectionBuffer = GLAllocation.createDirectFloatBuffer(16);
    private final FloatBuffer windowPositionBuffer = GLAllocation.createDirectFloatBuffer(4);

    private final List<EntityLivingBase> players = new ArrayList<>();

    @Listener
    public void onRenderNameTag(RenderNameTagEvent event) {
        if(players.contains(event.getEntity()))
            event.cancel();
    }

    @Listener
    public void onRender2D(Render2DEvent event) {
        if(players.isEmpty()) return;

        final FontRenderer font = mc.fontRendererObj;

        for (EntityLivingBase entityLivingBase : players) {
            mc.entityRenderer.setupCameraTransform(event.partialTicks, 0);

            final double[] dCoords = RenderUtil.projectBoundingBox(
                    entityLivingBase,
                    entityLivingBase.getEntityBoundingBox(),
                    event.resolution.getScaleFactor(),
                    event.partialTicks,
                    modelViewBuffer,
                    projectionBuffer,
                    viewportBuffer,
                    windowPositionBuffer
            );

            final float x1 = (float) dCoords[0],
                    y1 = (float) dCoords[1] - 7,
                    x2 = (float) dCoords[2],
                    y2 = (float) dCoords[1];

            mc.entityRenderer.setupOverlayRendering();

            final float scale = 0.5F;
            final float divisor = 2F / scale;

            String name = entityLivingBase.getDisplayName().getUnformattedText();

            if(shortenName.get() && entityLivingBase instanceof EntityPlayer) {
                name = ((EntityPlayer) entityLivingBase).getGameProfile().getName();
                name += ColorUtil.getFirstColor(name);
            }

            if(showHealth.get())
                name += EnumChatFormatting.RED +  " " + MathUtil.round(entityLivingBase.getHealth() + entityLivingBase.getAbsorptionAmount(), 1) + " HP";

            final float fontWidth = (font.getStringWidthF(name) - 2) / divisor;
            final float fontX = x1 + (x2 - x1)/2F - fontWidth;
            final float fontY = y1 + (y2 - y1)/2F - font.FONT_HEIGHT/2F;

            RenderUtil.hollowRect(fontX - 2, fontY - 2, 2 + fontX + fontWidth*2, fontY + 6, 1, setOpacity(Color.BLACK, backgroundAlpha.get() + 20).getRGB());
            RenderUtil.rect(fontX - 2, fontY - 2, 2 + fontX + fontWidth*2, fontY + 6, setOpacity(Color.BLACK, backgroundAlpha.get()));

          /*  if(true) health bar
                RenderUtil.rect(fontX - 2, y1 + font.FONT_HEIGHT/2F, 2 + fontX + fontWidth * 2, y1 + font.FONT_HEIGHT/2F + 0.5F, -1);*/

            RenderUtil.drawScaledFont(font, name, fontX, fontY, -1, true, scale);
        }
    }

    @Listener
    public void onUpdate(UpdatePlayerEvent event) {
        if(event.isPre()) return;

        players.clear();

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if(entity == mc.thePlayer
                    || !(entity instanceof EntityPlayer)
                    || entity.isInvisible()
                    || !entity.isEntityAlive()
            ) continue;

            final Entity renderViewEntity = mc.getRenderViewEntity();
            frustum.setPosition(renderViewEntity.posX, renderViewEntity.posY, renderViewEntity.posZ);

            if(!frustum.isBoundingBoxInFrustum(entity.getEntityBoundingBox())) continue;

            players.add((EntityLivingBase) entity);
        }
    }
}
