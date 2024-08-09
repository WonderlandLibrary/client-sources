package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import fun.ellant.events.WorldEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.hud.HUD;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name = "China Hat", type = Category.RENDER, desc = "Надевает на вас шляпу китайца")
public class ChinaHat extends Function {
    final ModeSetting correctionType = new ModeSetting("Мод", "Шляпа", new String[]{"Шляпа", "Нимб"});
    private ModeSetting type;

    public ChinaHat() {
        this.addSettings(new Setting[]{this.correctionType});
    }

    @Subscribe
    private void onRender(WorldEvent e) {
        float radius;
        Vector3d interpolated;
        double pitch;
        double yaw;
        int i;
        float x;
        float z;
        if (this.correctionType.is("Шляпа")) {
            if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                return;
            }

            radius = 0.6F;
            GlStateManager.pushMatrix();
            RenderSystem.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
            interpolated = MathUtil.interpolate(mc.player.getPositionVec(), new Vector3d(mc.player.lastTickPosX, mc.player.lastTickPosY, mc.player.lastTickPosZ), e.getPartialTicks());
            interpolated.y -= 0.05000000074505806D;
            RenderSystem.translated(interpolated.x, interpolated.y + (double)mc.player.getHeight(), interpolated.z);
            pitch = (double)mc.getRenderManager().info.getPitch();
            yaw = (double)mc.getRenderManager().info.getYaw();
            GL11.glRotatef((float)(-yaw), 0.0F, 1.0F, 0.0F);
            RenderSystem.translated(-interpolated.x, -(interpolated.y + (double)mc.player.getHeight()), -interpolated.z);
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.disableTexture();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.shadeModel(7425);
            RenderSystem.lineWidth(3.0F);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(interpolated.x, interpolated.y + (double)mc.player.getHeight() + 0.3D, interpolated.z).color(ColorUtils.setAlpha(HUD.getColor(0, 1.0F), 128)).endVertex();

            for(i = 0; i <= 360; ++i) {
                x = (float)(interpolated.x + (double)(MathHelper.sin((float)Math.toRadians((double)i)) * radius));
                z = (float)(interpolated.z + (double)(-MathHelper.cos((float)Math.toRadians((double)i)) * radius));
                buffer.pos((double)x, interpolated.y + (double)mc.player.getHeight(), (double)z).color(ColorUtils.setAlpha(HUD.getColor(i, 1.0F), 128)).endVertex();
            }

            tessellator.draw();
            buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);

            for(i = 0; i <= 360; ++i) {
                x = (float)(interpolated.x + (double)(MathHelper.sin((float)Math.toRadians((double)i)) * radius));
                z = (float)(interpolated.z + (double)(-MathHelper.cos((float)Math.toRadians((double)i)) * radius));
                buffer.pos((double)x, interpolated.y + (double)mc.player.getHeight(), (double)z).color(ColorUtils.setAlpha(HUD.getColor(i, 1.0F), 255)).endVertex();
            }

            tessellator.draw();
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
            RenderSystem.shadeModel(7424);
            GlStateManager.popMatrix();
        }

        if (this.correctionType.is("Нимб")) {
            if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                return;
            }

            radius = 0.5F;
            GlStateManager.pushMatrix();
            RenderSystem.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
            interpolated = MathUtil.interpolate(mc.player.getPositionVec(), new Vector3d(mc.player.lastTickPosX, mc.player.lastTickPosY, mc.player.lastTickPosZ), e.getPartialTicks());
            interpolated.y += 0.07000000029802322D;
            RenderSystem.translated(interpolated.x, interpolated.y + (double)mc.player.getHeight(), interpolated.z);
            pitch = (double)mc.getRenderManager().info.getPitch();
            yaw = (double)mc.getRenderManager().info.getYaw();
            GL11.glRotatef((float)(-yaw), 0.0F, 1.0F, 0.0F);
            RenderSystem.translated(-interpolated.x, -(interpolated.y + (double)mc.player.getHeight()), -interpolated.z);
            RenderSystem.enableBlend();
            RenderSystem.depthMask(false);
            RenderSystem.disableTexture();
            RenderSystem.disableCull();
            RenderSystem.blendFunc(770, 771);
            RenderSystem.shadeModel(7425);
            RenderSystem.lineWidth(3.0F);
            GL11.glEnable(2848);
            GL11.glHint(3154, 4354);
            buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
            buffer.pos(interpolated.x, interpolated.y, interpolated.z).color(ColorUtils.setAlpha(HUD.getColor(0, 1.0F), 60)).endVertex();

            for(i = 0; i <= 360; ++i) {
                x = (float)(interpolated.x + (double)(MathHelper.sin((float)Math.toRadians((double)i)) * radius));
                z = (float)(interpolated.z + (double)(-MathHelper.cos((float)Math.toRadians((double)i)) * radius));
            }

            tessellator.draw();
            buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);

            for(i = 0; i <= 360; ++i) {
                x = (float)(interpolated.x + (double)(MathHelper.sin((float)Math.toRadians((double)i)) * radius));
                z = (float)(interpolated.z + (double)(-MathHelper.cos((float)Math.toRadians((double)i)) * radius));
                buffer.pos((double)x, interpolated.y + (double)mc.player.getHeight(), (double)z).color(ColorUtils.setAlpha(HUD.getColor(i, 1.0F), 255)).endVertex();
            }

            tessellator.draw();
            GL11.glHint(3154, 4352);
            GL11.glDisable(2848);
            RenderSystem.enableTexture();
            RenderSystem.disableBlend();
            RenderSystem.enableCull();
            RenderSystem.depthMask(true);
            RenderSystem.shadeModel(7424);
            GlStateManager.popMatrix();
        }

    }
}