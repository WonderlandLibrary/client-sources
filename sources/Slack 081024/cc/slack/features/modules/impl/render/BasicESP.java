// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.BooleanValue;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.player.AttackUtil;
import cc.slack.utils.render.ColorUtil;
import cc.slack.utils.render.RenderUtil;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.entity.Entity;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.util.Timer;
import net.minecraft.util.AxisAlignedBB;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;


@ModuleInfo(
        name = "BasicESP",
        category = Category.RENDER
)
public class  BasicESP extends Module {

    private final BooleanValue itemESP = new BooleanValue("Item ESP", true);
    private final NumberValue<Float> lineWidth = new NumberValue<>("Line Width", 1f, 1f, 5f, 0.1f);
    private final BooleanValue rotateYaw = new BooleanValue("Yaw Rotate", false);

    // Color
    public final ModeValue<String> colormodes = new ModeValue<>("Color", new String[] { "Client Theme", "Rainbow", "Custom" });
    private final NumberValue<Integer> redValue = new NumberValue<>("Red", 0, 0, 255, 1);
    private final NumberValue<Integer> greenValue = new NumberValue<>("Green", 255, 0, 255, 1);
    private final NumberValue<Integer> blueValue = new NumberValue<>("Blue", 255, 0, 255, 1);
    private final NumberValue<Integer> alphaValue = new NumberValue<>("Alpha", 100, 0, 255, 1);


    public BasicESP() {
        super();
        addSettings(itemESP,lineWidth, rotateYaw, colormodes, redValue, greenValue, blueValue, alphaValue);
    }

    @Listen
    public void onRender(RenderEvent event) {
        if (event.getState() != RenderEvent.State.RENDER_3D) return;
        Color ct = ColorUtil.getColor();
        final RenderManager renderManager = mc.getRenderManager();
        final Timer timer = mc.timer;

        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        RenderUtil.enableGlCap(GL_BLEND);
        RenderUtil.disableGlCap(GL_TEXTURE_2D, GL_DEPTH_TEST);
        glDepthMask(false);

        double renderPosX = renderManager.renderPosX;
        double renderPosY = renderManager.renderPosY;
        double renderPosZ = renderManager.renderPosZ;

        glLineWidth(lineWidth.getValue());
        RenderUtil.enableGlCap(GL_LINE_SMOOTH);

        for (Entity entity : mc.theWorld.loadedEntityList) {
            if (entity.getEntityId() == mc.thePlayer.getEntityId()) continue;
            if (entity instanceof EntityItem && !itemESP.getValue()) continue;
            if (!(entity instanceof EntityLivingBase) && !(entity instanceof EntityItem)) continue;
            if (entity instanceof EntityLivingBase && !AttackUtil.isTarget(entity)) continue;

            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * timer.renderPartialTicks - renderPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * timer.renderPartialTicks - renderPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * timer.renderPartialTicks - renderPosZ;

            AxisAlignedBB entityBox = entity.getEntityBoundingBox();
            AxisAlignedBB axisAlignedBB = new AxisAlignedBB(
                    entityBox.minX - entity.posX - 0.05D,
                    entityBox.minY - entity.posY,
                    entityBox.minZ - entity.posZ - 0.05D,
                    entityBox.maxX - entity.posX + 0.05D,
                    entityBox.maxY - entity.posY + 0.15D,
                    entityBox.maxZ - entity.posZ + 0.05D
            );

            if (entity.hurtResistantTime > 1) {
                glColor(255, 10, 10, 95);
            } else if (colormodes.getValue().equals("Client Theme")) {
                glColor(ct.getRed(), ct.getGreen(), ct.getBlue(), ct.getAlpha());
            } else if (colormodes.getValue().equals("Custom")) {
                glColor(redValue.getValue(), greenValue.getValue(), blueValue.getValue(), alphaValue.getValue());
            } else {
                Color rainbowColor = ColorUtil.rainbow(-100, 1.0f, 0.47f);
                glColor(rainbowColor.getRed(), rainbowColor.getGreen(), rainbowColor.getBlue(), rainbowColor.getAlpha());
            }

            glPushMatrix();
            glTranslated(x, y, z);
            if (rotateYaw.getValue()) {
                glRotated(-entity.rotationYaw, 0, 1, 0);
            }
            RenderUtil.drawSelectionBoundingBox(axisAlignedBB);
            glPopMatrix();
        }

        GlStateManager.resetColor();
        glDepthMask(true);
        RenderUtil.resetCaps();
    }

    public static void glColor(final int red, final int green, final int blue, final int alpha) {
        GlStateManager.color(red / 255F, green / 255F, blue / 255F, alpha / 255F);
    }

}
