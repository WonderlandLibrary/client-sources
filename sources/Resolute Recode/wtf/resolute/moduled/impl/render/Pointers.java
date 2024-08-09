package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.util.ResourceLocation;
import wtf.resolute.manage.friends.FriendStorage;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.moduled.impl.render.HUD.HUD;
import wtf.resolute.moduled.settings.impl.ModeSetting;
import wtf.resolute.utiled.math.MathUtil;
import wtf.resolute.utiled.player.MoveUtils;
import wtf.resolute.utiled.player.PlayerUtils;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.DisplayUtils;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

import java.awt.*;

@ModuleAnontion(name = "Arrows", type = Categories.Render,server = "")
public class Pointers extends Module {

    public float animationStep;
    public static ModeSetting arrowsmode = new ModeSetting("Mode", "Модернезированый", "Модернезированый", "Спрощеный");


    public Pointers() {
        addSettings(arrowsmode);
    }

    private float lastYaw;
    private float lastPitch;

    private float animatedYaw;
    private float animatedPitch;

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.PRE) {
            return;
        }

        animatedYaw = MathUtil.fast(animatedYaw, (mc.player.moveStrafing) * 10,
                5);
        animatedPitch = MathUtil.fast(animatedPitch,
                (mc.player.moveForward) * 10, 5);

        float size = 70;

        if (mc.currentScreen instanceof InventoryScreen) {
            size += 80;
        }

        if (MoveUtils.isMoving()) {
            size += 10;
        }
        animationStep = MathUtil.fast(animationStep, size, 6);
        if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            for (AbstractClientPlayerEntity player : mc.world.getPlayers()) {
                if (!PlayerUtils.isNameValid(player.getNameClear()) || mc.player == player)
                    continue;

                double x = player.lastTickPosX + (player.getPosX() - player.lastTickPosX) * mc.getRenderPartialTicks()
                        - mc.getRenderManager().info.getProjectedView().getX();
                double z = player.lastTickPosZ + (player.getPosZ() - player.lastTickPosZ) * mc.getRenderPartialTicks()
                        - mc.getRenderManager().info.getProjectedView().getZ();

                double cos = MathHelper.cos((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
                double sin = MathHelper.sin((float) (mc.getRenderManager().info.getYaw() * (Math.PI * 2 / 360)));
                double rotY = -(z * cos - x * sin);
                double rotX = -(x * cos + z * sin);

                float angle = (float) (Math.atan2(rotY, rotX) * 180 / Math.PI);

                double x2 = animationStep * MathHelper.cos((float) Math.toRadians(angle)) + window.getScaledWidth() / 2f;
                double y2 = animationStep * MathHelper.sin((float) Math.toRadians(angle)) + window.getScaledHeight() / 2f;

                x2 += animatedYaw;
                y2 += animatedPitch;

                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                GlStateManager.translated(x2, y2, 0);
                GlStateManager.rotatef(angle, 0, 0, 1);

                int color = FriendStorage.isFriend(player.getGameProfile().getName()) ? FriendStorage.getColor() : ColorUtils.getColor(1);

                drawTriangle(-4, -1F, 4F, 7F, new Color(0, 0, 0, 32));
                drawTriangle(-3F, 0F, 3F, 5F, new Color(color));

                GlStateManager.enableBlend();
                GlStateManager.popMatrix();
            }
        }
        lastYaw = mc.player.rotationYaw;
        lastPitch = mc.player.rotationPitch;
    }

    public static void drawTriangle(float x, float y, float width, float height, Color color) {
        GL11.glPushMatrix();

        enableSmoothLine(1);
        GL11.glRotatef(180 + 180, 0F, 0F, 1.0F);

        final ResourceLocation logo2 = new ResourceLocation("resolute/images/triangle2.png");
        final ResourceLocation logo = new ResourceLocation("resolute/images/triangle.png");
        switch (arrowsmode.get()) {
            case "Модернезированый" -> {
                DisplayUtils.drawImage(logo, x, y, width + 15, height + 12, HUD.getColor(0, 1));
            }
            case "Спрощеный" -> {
                DisplayUtils.drawImage(logo2, x, y, width + 15, height + 12, HUD.getColor(0, 1));
            }
        }
        disableSmoothLine();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glRotatef(-180 - 90, 0F, 0F, 1.0F);
        GL11.glPopMatrix();
    }

    private static void enableSmoothLine(float width) {
        GL11.glDisable(GL11.GL_ALPHA_TEST);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glDepthMask(false);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
        GL11.glLineWidth(width);
    }

    private static void disableSmoothLine() {
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glDepthMask(true);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glHint(GL11.GL_LINE_SMOOTH_HINT, GL11.GL_NICEST);
    }

}
