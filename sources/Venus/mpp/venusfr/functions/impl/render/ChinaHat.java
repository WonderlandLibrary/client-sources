/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="China Hat", type=Category.Visual)
public class ChinaHat
extends Function {
    @Subscribe
    private void onRender(WorldEvent worldEvent) {
        float f;
        float f2;
        int n;
        if (ChinaHat.mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            return;
        }
        float f3 = 0.6f;
        GlStateManager.pushMatrix();
        RenderSystem.translated(-ChinaHat.mc.getRenderManager().info.getProjectedView().x, -ChinaHat.mc.getRenderManager().info.getProjectedView().y, -ChinaHat.mc.getRenderManager().info.getProjectedView().z);
        Vector3d vector3d = MathUtil.interpolate(ChinaHat.mc.player.getPositionVec(), new Vector3d(ChinaHat.mc.player.lastTickPosX, ChinaHat.mc.player.lastTickPosY, ChinaHat.mc.player.lastTickPosZ), worldEvent.getPartialTicks());
        vector3d.y -= (double)0.05f;
        RenderSystem.translated(vector3d.x, vector3d.y + (double)ChinaHat.mc.player.getHeight(), vector3d.z);
        double d = ChinaHat.mc.getRenderManager().info.getPitch();
        double d2 = ChinaHat.mc.getRenderManager().info.getYaw();
        GL11.glRotatef((float)(-d2), 0.0f, 1.0f, 0.0f);
        RenderSystem.translated(-vector3d.x, -(vector3d.y + (double)ChinaHat.mc.player.getHeight()), -vector3d.z);
        RenderSystem.enableBlend();
        RenderSystem.depthMask(false);
        RenderSystem.disableTexture();
        RenderSystem.disableCull();
        RenderSystem.blendFunc(770, 771);
        RenderSystem.shadeModel(7425);
        RenderSystem.lineWidth(3.0f);
        GL11.glEnable(2848);
        GL11.glHint(3154, 4354);
        buffer.begin(6, DefaultVertexFormats.POSITION_COLOR);
        buffer.pos(vector3d.x, vector3d.y + (double)ChinaHat.mc.player.getHeight() + 0.3, vector3d.z).color(ColorUtils.setAlpha(HUD.getColor(0, 1.0f), 128)).endVertex();
        for (n = 0; n <= 360; ++n) {
            f2 = (float)(vector3d.x + (double)(MathHelper.sin((float)Math.toRadians(n)) * f3));
            f = (float)(vector3d.z + (double)(-MathHelper.cos((float)Math.toRadians(n)) * f3));
            buffer.pos(f2, vector3d.y + (double)ChinaHat.mc.player.getHeight(), f).color(ColorUtils.setAlpha(HUD.getColor(n, 1.0f), 128)).endVertex();
        }
        tessellator.draw();
        buffer.begin(2, DefaultVertexFormats.POSITION_COLOR);
        for (n = 0; n <= 360; ++n) {
            f2 = (float)(vector3d.x + (double)(MathHelper.sin((float)Math.toRadians(n)) * f3));
            f = (float)(vector3d.z + (double)(-MathHelper.cos((float)Math.toRadians(n)) * f3));
            buffer.pos(f2, vector3d.y + (double)ChinaHat.mc.player.getHeight(), f).color(ColorUtils.setAlpha(HUD.getColor(n, 1.0f), 255)).endVertex();
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

