package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name = "China Hat", type = Category.Render)
public class ChinaHat extends Function {

    @Subscribe
    private void onRender(WorldEvent e) {
        if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) return;
        float radius = 0.6f;

        GlStateManager.pushMatrix();

        RenderSystem.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
        Vector3d interpolated = MathUtil.interpolate(mc.player.getPositionVec(), new Vector3d(mc.player.lastTickPosX, mc.player.lastTickPosY, mc.player.lastTickPosZ), e.getPartialTicks());
        interpolated.y -= 0.05f;
        RenderSystem.translated(interpolated.x, interpolated.y + mc.player.getHeight(), interpolated.z);
        final double yaw = mc.getRenderManager().info.getYaw();

        GL11.glRotatef((float) -yaw, 0f, 1f, 0f);

        float height = mc.player.container.getSlot(5).getStack().isEmpty() ? 0.02F : 0.11F;

        RenderSystem.translated(-interpolated.x, -(interpolated.y + mc.player.getHeight()) + height, -interpolated.z);

        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.disableTexture();
        RenderSystem.disableCull();
        RenderSystem.blendFunc(770, 771);
        RenderSystem.shadeModel(7425);
        RenderSystem.lineWidth(3);

        int steps = 40;
        double angleStep = 2 * Math.PI / steps;

        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        buffer.begin(GL11.GL_TRIANGLE_FAN, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(interpolated.x, interpolated.y + mc.player.getHeight() + 0.3, interpolated.z).color(ColorUtils.setAlpha(HUD.getColor(0, 1), 128)).endVertex();
        for (int i = 0; i <= steps; i++) {

            float x = (float) (interpolated.x + Math.sin(i * angleStep) * radius);
            float z = (float) (interpolated.z + -Math.cos(i * angleStep) *  radius);

            buffer.pos(x, interpolated.y + mc.player.getHeight(), z).color(ColorUtils.setAlpha(HUD.getColor(i, 1), 128)).endVertex();
        }
        tessellator.draw();
        buffer.begin(GL11.GL_LINE_LOOP, DefaultVertexFormats.POSITION_COLOR);
        for (int i = 0; i <= steps; i++) {

            float x = (float) (interpolated.x + Math.sin(i * angleStep) * radius);
            float z = (float) (interpolated.z + -Math.cos(i * angleStep) *  radius);

            buffer.pos(x, interpolated.y + mc.player.getHeight(), z).color(ColorUtils.setAlpha(HUD.getColor(i, 1), 255)).endVertex();
        }
        tessellator.draw();
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_DONT_CARE);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        RenderSystem.enableTexture();
        RenderSystem.disableBlend();
        RenderSystem.enableCull();
        RenderSystem.depthMask(true);
        RenderSystem.shadeModel(7424);
        GlStateManager.popMatrix();
    }
}