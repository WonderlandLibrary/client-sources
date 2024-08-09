package dev.darkmoon.client.module.impl.render;

import com.darkmagician6.eventapi.EventTarget;
import dev.darkmoon.client.DarkMoon;
import dev.darkmoon.client.event.render.EventRender2D;
import dev.darkmoon.client.manager.dragging.DragManager;
import dev.darkmoon.client.manager.dragging.Draggable;
import dev.darkmoon.client.module.Category;
import dev.darkmoon.client.module.Module;
import dev.darkmoon.client.module.ModuleAnnotation;
import dev.darkmoon.client.module.impl.combat.KillAura;
import dev.darkmoon.client.module.setting.impl.BooleanSetting;
import dev.darkmoon.client.module.setting.impl.ModeSetting;
import dev.darkmoon.client.module.setting.impl.NumberSetting;
import dev.darkmoon.client.utility.math.MathUtility;
import dev.darkmoon.client.utility.render.GLUtility;
import dev.darkmoon.client.utility.render.*;
import dev.darkmoon.client.utility.render.animation.Animation;
import dev.darkmoon.client.utility.render.animation.AnimationMath;
import dev.darkmoon.client.utility.render.animation.Direction;
import dev.darkmoon.client.utility.render.animation.impl.EaseInOutQuad;
import dev.darkmoon.client.utility.render.font.FontRenderer;
import dev.darkmoon.client.utility.render.font.Fonts;
import dev.darkmoon.client.utility.render.shaderRound.ShaderShell;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.ThreadDownloadImageData;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.TextFormatting;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;

@ModuleAnnotation(name = "TargetHUD", category = Category.RENDER)
public class TargetHud extends Module {
    public static ModeSetting targetMode = new ModeSetting("Target-HUD Mode", "Skeet", "Skeet", "Wave", "DarkMoon");
    private final Draggable targetHudDraggable = DragManager.create(this, "Target HUD", 300, 10);
    public static BooleanSetting glow = new BooleanSetting("Glow", true);
    public NumberSetting glowRadius = new NumberSetting("Radius", 10.0F, 7.0F, 10.0F, 1.0F, () -> glow.get() && targetMode.is("Skeet"));
    public static BooleanSetting glowHP = new BooleanSetting("Glow-HPLine", true, () -> targetMode.is("Skeet") && glow.get());
    public static BooleanSetting triangle = new BooleanSetting("Triangle", false, () -> targetMode.is("Wave"));
    public static BooleanSetting winning = new BooleanSetting("Winning-Info", true);
    public static BooleanSetting shadow = new BooleanSetting("Shadow-Winning", false, () -> winning.get());
    private float hp;
    public static HashMap<String, SkinData> cache = new HashMap<String, SkinData>();
    private float healthValue;
    private final Animation animation = new EaseInOutQuad(175, 1);
    private final Animation openAnimation = new EaseInOutQuad(175, .5F);
    private EntityLivingBase currentTarget = null;

    public TargetHud() {
        targetHudDraggable.setWidth(112);
        targetHudDraggable.setHeight(38);
    }

    @EventTarget
    public void onRender2D(EventRender2D eventRender2D) {
        if (mc.gameSettings.showDebugInfo) return;
        if (targetMode.is("Wave")) {
            if (KillAura.targetEntity != null) {
                this.currentTarget = KillAura.targetEntity;
            } else if (mc.currentScreen instanceof GuiChat) {
                this.currentTarget = mc.player;
            }

            if (this.currentTarget != null) {
                if (KillAura.targetEntity == null && !(mc.currentScreen instanceof GuiChat)) {
                    animation.setDuration(260);
                    this.animation.setDirection(Direction.BACKWARDS);
                } else {
                    animation.setDuration(260);
                    this.animation.setDirection(Direction.FORWARDS);
                }
                RenderUtility.scaleStart(targetHudDraggable.getX() + 56, targetHudDraggable.getY() + 19, animation.getOutput());
                Color color11 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[0];
                Color color22 = DarkMoon.getInstance().getThemeManager().getCurrentStyleTheme().getColors()[1];
                Color gradientColor11 = ColorUtility2.interpolateColorsBackAndForth(4, 0, color11, color22, false);
                Color gradientColor22 = ColorUtility2.interpolateColorsBackAndForth(4, 90, color11, color22, false);
                RenderUtility.drawRoundedRect((float) (this.targetHudDraggable.getX() - 2), (float) (this.targetHudDraggable.getY() - 2), 116.0F, 39.0F, 0.0F, (new Color(28, 28, 28, 126)).getRGB());
                RenderUtility.drawRoundedGradientRect((float) (this.targetHudDraggable.getX() - 2), (float) (this.targetHudDraggable.getY() - 3), 116.0F, 1.5F, 0.0F, 1.0F, gradientColor11.getRGB(), gradientColor11.getRGB(), gradientColor22.getRGB(), gradientColor22.getRGB());
                RenderUtility.drawRect((float) (this.targetHudDraggable.getX() + 38), (float) this.targetHudDraggable.getY() + 12.5F, 73.0F, 7.5F, (new Color(30, 30, 30, 128)).getRGB());
                if (this.currentTarget instanceof EntityPlayer) {
                    StencilUtility.initStencilToWrite();
                    RenderUtility.drawRoundedRect((float) (this.targetHudDraggable.getX() + 4), (float) (this.targetHudDraggable.getY() + 2), 30.0F, 30.0F, 5.0F, -1);
                    StencilUtility.readStencilBuffer(1);
                    float hurtPercent = getHurtPercent(this.currentTarget);
                    GlStateManager.pushMatrix();
                    GlStateManager.color(1.0F, 1.0F - hurtPercent, 1.0F - hurtPercent, 1.0F);
                    mc.getTextureManager().bindTexture(((AbstractClientPlayer) this.currentTarget).getLocationSkin());
                    GlStateManager.enableTexture2D();
                    Gui.drawScaledCustomSizeModalRect(this.targetHudDraggable.getX() + 4, (int) (this.targetHudDraggable.getY() + 2), 8.0F, 8.0F, 8, 8, 30, 30, 64.0F, 64.0F);
                    GlStateManager.popMatrix();
                    StencilUtility.uninitStencilBuffer();
                } else {
                    Fonts.tenacityBold28.drawCenteredString("?", (float) (this.targetHudDraggable.getX() + 19), (float) (this.targetHudDraggable.getY() + 19) - (float) Fonts.tenacityBold28.getFontHeight() / 2.0F, Color.WHITE.getRGB());
                }
                StencilUtility.initStencilToWrite();
                RenderUtility.drawRoundedRect((float) this.targetHudDraggable.getX(), (float) this.targetHudDraggable.getY(), 112.0F, 38.0F, 5.0F, (new Color(30, 30, 30)).getRGB());
                StencilUtility.readStencilBuffer(1);
                Fonts.nunitoBold16.drawSubstring(TextFormatting.getTextWithoutFormattingCodes(this.currentTarget.getName()), (float) (this.targetHudDraggable.getX() + 38), (float) (this.targetHudDraggable.getY() + 3.5), Color.WHITE.getRGB(), 56.0F);
                StencilUtility.uninitStencilBuffer();
                this.hp = MathUtility.clamp(MathUtility.lerp(this.hp, this.currentTarget.getHealth() / this.currentTarget.getMaxHealth(), (float) (12.0 * AnimationMath.deltaTime())), 0.0F, 1.0F);
                RenderUtility.applyGradientMaskLeft((float) (this.targetHudDraggable.getX() + 38), (float) this.targetHudDraggable.getY() + 12.5F, 73.0F * this.hp, 7.5F, 1.3f, ColorUtility.applyOpacity(gradientColor11, .85f), gradientColor11, gradientColor22, gradientColor22, () -> {
                    GlowUtility.drawGlow((float) (this.targetHudDraggable.getX() + 38), (float) this.targetHudDraggable.getY() + 12.5F, 73.0F * this.hp, 7.5F, 3, new Color(0,0,0,0));
                });
                if (triangle.get()) {
                    Fonts.iconTargetHud.drawString("a", (int) (this.targetHudDraggable.getX() + 38 + 70.6f * this.hp), (int) (this.targetHudDraggable.getY() + 25.5F), ColorUtility.getColor(180).getRGB());
                }
                double var25 = (double) (this.hp * 20.0F);
                if (winning.get()) {
                    final String winStatus = String.format("%s",
                            currentTarget.getHealth() == mc.player.getHealth() ? ""
                                    : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() > mc.player
                                    .getHealth()
                                    ? Fonts.nunitoBold14.drawStringWithShadow("[Loosing]", (float) (this.targetHudDraggable.getX() + 39.5), (float) (this.targetHudDraggable.getY() + 39.5), Color.WHITE.getRGB())
                                    : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() >= 10
                                    && mc.player.getHealth() <= 5
                                    ? Fonts.nunitoBold14.drawStringWithShadow("[You Suck]", (float) (this.targetHudDraggable.getX() + 38.5), (float) (this.targetHudDraggable.getY() + 39.5), Color.WHITE.getRGB())
                                    : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() <= 5
                                    && mc.player.getHealth() >= 10 ? Fonts.nunitoBold14.drawStringWithShadow("[EZZ!]", (float) (this.targetHudDraggable.getX() + 46.5), (float) (this.targetHudDraggable.getY() + 39.5), Color.WHITE.getRGB())
                                    : Fonts.nunitoBold14.drawStringWithShadow("[Winning]", (float) (this.targetHudDraggable.getX() + 39.5), (float) (this.targetHudDraggable.getY() + 39.5), Color.WHITE.getRGB()));
                    //    if (winning.get()) {
                    //      Fonts.nunitoBold14.drawString(winStatus, (float) (this.targetHudDraggable.getX() + 39.5), (float) (this.targetHudDraggable.getY() + 39.5), Color.WHITE.getRGB());
                    //   }
                }
                Fonts.nunitoBold14.drawCenteredString(MathUtility.round(var25, 0.10000000149011612) + " HP", (float) (this.targetHudDraggable.getX() + 54), (float) this.targetHudDraggable.getY() + 14.6F, Color.WHITE.getRGB());
                RenderUtility.scaleEnd();
            }
        } else if (targetMode.is("Skeet")) {
            float hurtPercent = Math.min(1.0F, this.openAnimation.getOutput() * 2.0F);
            this.targetHudDraggable.setWidth((float)Math.max(135, Fonts.mntssb16.getStringWidth("Name: bruh") + 60));
            this.targetHudDraggable.setHeight(46.0F);
            float x = (float)this.targetHudDraggable.getX();
            float y = (float)this.targetHudDraggable.getY();
            Color darkest = ColorUtility.applyOpacity(new Color(10, 10, 10), hurtPercent);
            Color secondDarkest = ColorUtility.applyOpacity(new Color(22, 22, 22), hurtPercent);
            Color lightest = ColorUtility.applyOpacity(new Color(44, 44, 44), hurtPercent);
            Color middleColor2 = ColorUtility.applyOpacity(new Color(34, 34, 34), hurtPercent);
            Color textColor = ColorUtility.applyOpacity(Color.WHITE, hurtPercent);
            if (KillAura.targetEntity != null) {
                this.currentTarget = KillAura.targetEntity;
            } else if (mc.currentScreen instanceof GuiChat) {
                this.currentTarget = mc.player;
            }

            if (this.currentTarget != null) {
                if (KillAura.targetEntity == null && !(mc.currentScreen instanceof GuiChat)) {
                    animation.setDuration(260);
                    this.animation.setDirection(Direction.BACKWARDS);
                } else {
                    animation.setDuration(260);
                    this.animation.setDirection(Direction.FORWARDS);
                }
                RenderUtility.scaleStart(targetHudDraggable.getX() + 56, targetHudDraggable.getY() + 19, animation.getOutput());
                Gui.drawRect2((double)x - 3.5, (double)y - 3.5, (double)(this.targetHudDraggable.getWidth() + 7.0F), (double)(this.targetHudDraggable.getHeight() + 7.0F), ColorUtility.applyOpacity(Color.BLACK.getRGB(), hurtPercent));
                Gui.drawRect2((double)x - 3.5, (double)y - 3.5, (double)(this.targetHudDraggable.getWidth() + 7.0F), (double)(this.targetHudDraggable.getHeight() + 7.0F), darkest.getRGB());
                Gui.drawRect2((double)(x - 3.0F), (double)(y - 3.0F), (double)(this.targetHudDraggable.getWidth() + 6.0F), (double)(this.targetHudDraggable.getHeight() + 6.0F), middleColor2.getRGB());
                Gui.drawRect2((double)(x - 1.0F), (double)(y - 1.0F), (double)(this.targetHudDraggable.getWidth() + 2.0F), (double)(this.targetHudDraggable.getHeight() + 2.0F), lightest.getRGB());
                Gui.drawRect2((double)x, (double)y, (double)this.targetHudDraggable.getWidth(), (double)this.targetHudDraggable.getHeight(), secondDarkest.getRGB());
                float size = this.targetHudDraggable.getHeight() - 6.0F;
                Gui.drawRect2((double)(x + 3.0F), (double)(y + 3.0F), 0.5, (double)size, lightest.getRGB());
                Gui.drawRect2((double)(x + 3.0F), (double)(y + 3.0F + size), (double)size, 0.5, lightest.getRGB());
                Gui.drawRect2((double)(x + 3.0F + size), (double)(y + 3.0F), 0.5, (double)(size + 0.5F), lightest.getRGB());
                Gui.drawRect2((double)(x + 3.0F), (double)(y + 3.0F), (double)size, 0.5, lightest.getRGB());
                int alphaInt = (int)(255.0F * hurtPercent);
                Fonts.mntssb14.drawString(currentTarget.getName(), x + 8.0F + size, y + 6.0F, textColor.getRGB());
                //    float healthValue = (currentTarget.getHealth() + currentTarget.getAbsorptionAmount()) / (currentTarget.getMaxHealth() + currentTarget.getAbsorptionAmount());
                this.healthValue = MathUtility.clamp(MathUtility.lerp(this.healthValue, this.currentTarget.getHealth() / this.currentTarget.getMaxHealth(), (float) (12.0 * AnimationMath.deltaTime())), 0.0F, 1.0F);

                Color healthColor = healthValue > .5f ? ColorUtility.interpolateColorC(new Color(255, 255, 10), new Color(10, 255, 10), (healthValue - .5f) / .5f) : ColorUtility.interpolateColorC(new Color(255, 10, 10), new Color(255, 255, 10), healthValue * 2);
                healthColor = ColorUtility.applyOpacity(healthColor, hurtPercent);
                float healthBarWidth = this.targetHudDraggable.getWidth() - (size + 22.0F);
                Gui.drawRect2((double)(x + 8.0F + size), (double)(y + 15.0F), healthBarWidth, 5.0, darkest.getRGB());
                // Gui.drawRect2((double)(x + 8.0F + size) + 0.5, (double)(y + 15.5F), (double)(healthBarWidth - 1.0F), 4.0, ColorUtility.interpolateColor(darkest, healthColor, 0.2F));
                float heathBarActualWidth = healthBarWidth - 1.0F;
                //   RenderUtility.drawGlow(x + 8.0f + size + 0.5f, (y + 15.5F), heathBarActualWidth * healthValue, 5f,10, healthColor);
                if (glow.get()) {
                    RenderUtility.drawGlow(x + 8.0f + size + 0.5f, (y + 14.5F), 73 * healthValue, 5f, 10, healthColor);
                }
                Gui.drawRect2((double)(x + 8.0F + size) + 0.5, (double)(y + 15.5F), (double)(73 * healthValue), 4.0, healthColor.getRGB());
                float increment = heathBarActualWidth / 10.0F;

                for (int i = 1; i < 10; ++i) {
                    Gui.drawRect2((double)(x + 8.0F + size + increment * (float)i), (double)(y + 15.5F), 0.5, 4, darkest.getRGB());
                }

                FontRenderer var57 = Fonts.mntssb12;
                StringBuilder var56 = (new StringBuilder()).append("HP: ");
                double targetHealth = currentTarget.getHealth() + currentTarget.getAbsorptionAmount();
                String formattedHealth = String.format("%.0f", targetHealth);
                var56.append(formattedHealth.replace(".0", ""));

                var56.append(" | Dist: ");
                double distance = Minecraft.player.getDistanceSq(currentTarget);
                String formattedDistance = String.format("%.0f", distance);
                var56.append(formattedDistance.replace(".0", ""));

                var57.drawString(var56.toString(), x + 8.0F + size, y + 25.0F, textColor.getRGB());
                GLUtility.startBlend();
                RenderUtility.color(textColor.getRGB());
                GLUtility.enableDepth();
                GuiInventory.drawEntityOnScreen((int) (x + 3 + size / 2f), (int) (y + size + 1), 18, 12, -currentTarget.rotationPitch, currentTarget);
                GLUtility.disableDepth();
                RenderHelper.enableGUIStandardItemLighting();
                float itemOffset = 38;
                for(Iterator var30 = currentTarget.getArmorAndEquipment().iterator(); var30.hasNext(); itemOffset += 12) {
                    ItemStack itemStack = (ItemStack)var30.next();
                    if (!itemStack.isEmpty()) {
                        GlStateManager.pushMatrix();
                        GlStateManager.translate((float)(this.targetHudDraggable.getX() + itemOffset + 1), (float)(this.targetHudDraggable.getY() + 15), 0.0F);
                        GlStateManager.scale(0.75, 0.75, 0.75);
                        RenderUtility.drawItemStack(itemStack, 11, 20);
                        GlStateManager.popMatrix();
                    }
                }
                final String winStatus = String.format("%s",
                        currentTarget.getHealth() == mc.player.getHealth() ? ""
                                : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() > mc.player
                                .getHealth()
                                ? "[Loosing]"
                                : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() >= 10
                                && mc.player.getHealth() <= 5
                                ? "[You Suck]"
                                : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() <= 5
                                && mc.player.getHealth() >= 10 ? "[EZZ!]"
                                : "[Winning]");
                if (winning.get()) {
                    final String winStatus1 = String.format("%s",
                            currentTarget.getHealth() == mc.player.getHealth() ? ""
                                    : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() > mc.player
                                    .getHealth()
                                    ? Fonts.nunitoBold14.drawString("[Loosing]", (float) (this.targetHudDraggable.getX() + 49.5), (float) (this.targetHudDraggable.getY() + 52.5), Color.WHITE.getRGB())
                                    : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() >= 10
                                    && mc.player.getHealth() <= 5
                                    ? Fonts.nunitoBold14.drawString("[You Suck]", (float) (this.targetHudDraggable.getX() + 48.5), (float) (this.targetHudDraggable.getY() + 52.5), Color.WHITE.getRGB())
                                    : currentTarget.getHealth() + currentTarget.getAbsorptionAmount() <= 5
                                    && mc.player.getHealth() >= 10 ? Fonts.nunitoBold14.drawString("[EZZ!]", (float) (this.targetHudDraggable.getX() + 58.5), (float) (this.targetHudDraggable.getY() + 52.5), Color.WHITE.getRGB())
                                    : Fonts.nunitoBold14.drawString("[Winning]", (float) (this.targetHudDraggable.getX() + 49.5), (float) (this.targetHudDraggable.getY() + 52.5), Color.WHITE.getRGB()));
                    //    if (winning.get()) {
                    //      Fonts.nunitoBold14.drawString(winStatus, (float) (this.targetHudDraggable.getX() + 39.5), (float) (this.targetHudDraggable.getY() + 39.5), Color.WHITE.getRGB());
                    //   }
                }
                RenderUtility.scaleEnd();
                RenderHelper.disableStandardItemLighting();
            } else if (targetMode.is("DarkMoon")) {
                float start = 7;
                float startY = 22;
                RenderUtility.drawRect(start, startY, 94, 33, ColorUtility.rgba(20, 20, 20, 85));
                drawGradientSideways(start + 1, 23, start + (93 - start) * hp, 32, textColor.getRGB(), middleColor2.getRGB());
                Fonts.mntsb12.drawCenteredString(
                        String.format("%.1f", currentTarget.getHealth() * animation.getOutput()), 50, 26.5f,
                        ColorUtility.rgba(255, 255, 255, (int) Math.min(255 * animation.getOutput(), 255)));
                SkinData data = getSkinData(currentTarget);
                if (data != null) {
                    GlStateManager.bindTexture(data.getTextureID());
                } else {
                    Fonts.tenacityBold28.drawCenteredString("?", (float) (this.targetHudDraggable.getX() + 19), (float) (this.targetHudDraggable.getY() + 19) - (float) Fonts.tenacityBold28.getFontHeight() / 2.0F, Color.WHITE.getRGB());
                }
                ShaderShell.CIRCLE_TEXTURE_SHADER.attach();
                ShaderShell.CIRCLE_TEXTURE_SHADER.set1F("radius", 0.5F);
                ShaderShell.CIRCLE_TEXTURE_SHADER.set1F("glow", 0.08F);
                GL11.glScaled(0.06, 0.06, 1);
                Gui.drawTexturedModalRect2(150, 85, 0, 0, 256, 256);
                ShaderShell.CIRCLE_TEXTURE_SHADER.detach();
                GL11.glPopMatrix();
            }
    }
        DarkMoon.getInstance().getScaleMath().popScale();
    }

    public static SkinData getSkinData(Entity entity) {
        if (entity instanceof AbstractClientPlayer) {
            AbstractClientPlayer player = (AbstractClientPlayer) entity;
            SkinData data = getCache().get(player.getGameProfile().getName());
            if (data == null) {
                data = new SkinData();
                try {
                    data.setBufferedImage(
                            parseBufferedImage(mc.renderEngine.mapTextureObjects.get(player.getLocationSkin())));
                    data.setLoaded();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                getCache().put(player.getGameProfile().getName(), data);
            }
            if (!data.isLoaded()) {
                return null;
            }
            return data;
        }
        return null;
    }
    public static void drawGradientSideways(final double left, final double top, final double right,
                                            final double bottom, final int col1, final int col2) {
        final float f = (col1 >> 24 & 0xFF) / 255.0f;
        final float f2 = (col1 >> 16 & 0xFF) / 255.0f;
        final float f3 = (col1 >> 8 & 0xFF) / 255.0f;
        final float f4 = (col1 & 0xFF) / 255.0f;
        final float f5 = (col2 >> 24 & 0xFF) / 255.0f;
        final float f6 = (col2 >> 16 & 0xFF) / 255.0f;
        final float f7 = (col2 >> 8 & 0xFF) / 255.0f;
        final float f8 = (col2 & 0xFF) / 255.0f;
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(2848);
        GL11.glShadeModel(7425);
        GL11.glPushMatrix();
        GL11.glBegin(7);
        GL11.glColor4f(f2, f3, f4, f);
        GL11.glVertex2d(left, top);
        GL11.glVertex2d(left, bottom);
        GL11.glColor4f(f6, f7, f8, f5);
        GL11.glVertex2d(right, bottom);
        GL11.glVertex2d(right, top);
        GL11.glEnd();
        GL11.glPopMatrix();
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        GL11.glDisable(2848);
        GL11.glShadeModel(7424);
    }
    public static synchronized HashMap<String, SkinData> getCache() {
        return cache;
    }

    public static BufferedImage parseBufferedImage(ITextureObject ito) throws Exception {
        if (ito instanceof ThreadDownloadImageData) {
            BufferedImage bi = new BufferedImage(64, 64, BufferedImage.TYPE_INT_ARGB);
            ThreadDownloadImageData t = (ThreadDownloadImageData) ito;
            return t.imageBuffer.cache();
        }
        if (ito instanceof SimpleTexture) {
            SimpleTexture st = (SimpleTexture) ito;
            return TextureUtil.readBufferedImage(
                    mc.renderEngine.resourceManager.getResource(st.textureLocation).getInputStream());
        }
        return null;
    }

    public static class SkinData {
        private int texture;
        private boolean loaded;

        public void setBufferedImage(BufferedImage bi) {
            this.texture = TextureUtil.glGenTextures();
            TextureUtil.uploadTextureImageAllocate(this.texture, bi.getSubimage(8, 8, 8, 8), false, true);
        }

        public boolean isLoaded() {
            return this.loaded;
        }

        public void setLoaded() {
            this.loaded = true;
        }

        public int getTextureID() {
            return this.texture;
        }
    }
    public static int getTooltipOffset() {
        int offset = 63;
        if (!mc.player.isCreative() && mc.player.isInsideOfMaterial(Material.WATER)) {
            offset += 10;
        }
        return offset;
    }
    private static float getHurtPercent(EntityLivingBase entity) {
        return (entity.hurtTime - (entity.hurtTime != 0 ? mc.timer.renderPartialTicks : 0.0f)) / 10f;
    }
}
