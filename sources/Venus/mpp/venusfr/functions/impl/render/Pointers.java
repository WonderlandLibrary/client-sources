/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import java.awt.Color;
import mpp.venusfr.command.friends.FriendStorage;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.player.MoveUtils;
import mpp.venusfr.utils.player.PlayerUtils;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import net.minecraft.client.entity.player.AbstractClientPlayerEntity;
import net.minecraft.client.gui.screen.inventory.InventoryScreen;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="Pointers", type=Category.Visual)
public class Pointers
extends Function {
    public float animationStep;
    private float lastYaw;
    private float lastPitch;
    private float animatedYaw;
    private float animatedPitch;

    @Subscribe
    public void onDisplay(EventDisplay eventDisplay) {
        if (Pointers.mc.player == null || Pointers.mc.world == null || eventDisplay.getType() != EventDisplay.Type.PRE) {
            return;
        }
        this.animatedYaw = MathUtil.fast(this.animatedYaw, Pointers.mc.player.moveStrafing * 10.0f, 5.0f);
        this.animatedPitch = MathUtil.fast(this.animatedPitch, Pointers.mc.player.moveForward * 10.0f, 5.0f);
        float f = 70.0f;
        if (Pointers.mc.currentScreen instanceof InventoryScreen) {
            f += 80.0f;
        }
        if (MoveUtils.isMoving()) {
            f += 10.0f;
        }
        this.animationStep = MathUtil.fast(this.animationStep, f, 6.0f);
        if (Pointers.mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            for (AbstractClientPlayerEntity abstractClientPlayerEntity : Pointers.mc.world.getPlayers()) {
                if (!PlayerUtils.isNameValid(abstractClientPlayerEntity.getNameClear()) || Pointers.mc.player == abstractClientPlayerEntity) continue;
                double d = abstractClientPlayerEntity.lastTickPosX + (abstractClientPlayerEntity.getPosX() - abstractClientPlayerEntity.lastTickPosX) * (double)mc.getRenderPartialTicks() - Pointers.mc.getRenderManager().info.getProjectedView().getX();
                double d2 = abstractClientPlayerEntity.lastTickPosZ + (abstractClientPlayerEntity.getPosZ() - abstractClientPlayerEntity.lastTickPosZ) * (double)mc.getRenderPartialTicks() - Pointers.mc.getRenderManager().info.getProjectedView().getZ();
                double d3 = MathHelper.cos((float)((double)Pointers.mc.getRenderManager().info.getYaw() * (Math.PI / 180)));
                double d4 = MathHelper.sin((float)((double)Pointers.mc.getRenderManager().info.getYaw() * (Math.PI / 180)));
                double d5 = -(d2 * d3 - d * d4);
                double d6 = -(d * d3 + d2 * d4);
                float f2 = (float)(Math.atan2(d5, d6) * 180.0 / Math.PI);
                double d7 = this.animationStep * MathHelper.cos((float)Math.toRadians(f2)) + (float)window.getScaledWidth() / 2.0f;
                double d8 = this.animationStep * MathHelper.sin((float)Math.toRadians(f2)) + (float)window.getScaledHeight() / 2.0f;
                GlStateManager.pushMatrix();
                GlStateManager.disableBlend();
                GlStateManager.translated(d7 += (double)this.animatedYaw, d8 += (double)this.animatedPitch, 0.0);
                GlStateManager.rotatef(f2, 0.0f, 0.0f, 1.0f);
                int n = FriendStorage.isFriend(abstractClientPlayerEntity.getGameProfile().getName()) ? FriendStorage.getColor() : ColorUtils.getColor(1);
                DisplayUtils.drawShadowCircle(1.0f, 0.0f, 14.0f, ColorUtils.setAlpha(n, 255));
                Pointers.drawTriangle(-4.0f, -1.0f, 4.0f, 7.0f, new Color(0, 0, 0, 32));
                Pointers.drawTriangle(-3.0f, 0.0f, 3.0f, 5.0f, new Color(n));
                GlStateManager.enableBlend();
                GlStateManager.popMatrix();
            }
        }
        this.lastYaw = Pointers.mc.player.rotationYaw;
        this.lastPitch = Pointers.mc.player.rotationPitch;
    }

    public static void drawTriangle(float f, float f2, float f3, float f4, Color color) {
        DisplayUtils.drawImage(new ResourceLocation("venusfr/images/triangle.png"), -8.0f, -9.0f, 18.0f, 18.0f, ColorUtils.getColor(1));
        GL11.glPushMatrix();
        GL11.glPopMatrix();
    }
}

