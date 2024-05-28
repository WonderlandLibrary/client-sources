package arsenic.module.impl.visual;

import arsenic.event.bus.Listener;
import arsenic.event.bus.annotations.EventLink;
import arsenic.event.impl.EventRender2D;
import arsenic.main.Nexus;
import arsenic.module.Module;
import arsenic.module.ModuleCategory;
import arsenic.module.ModuleInfo;
import arsenic.module.impl.combat.Aura;
import arsenic.module.property.PropertyInfo;
import arsenic.module.property.impl.EnumProperty;
import arsenic.module.property.impl.doubleproperty.DoubleProperty;
import arsenic.module.property.impl.doubleproperty.DoubleValue;
import arsenic.utils.font.FontRendererExtension;
import arsenic.utils.java.ColorUtils;
import arsenic.utils.minecraft.PlayerUtils;
import arsenic.utils.render.DrawUtils;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;
import java.text.DecimalFormat;

@ModuleInfo(name = "TargetHud", category = ModuleCategory.Visual)
public class TargetHud extends Module {
    public final EnumProperty<targethudMode> targethudmode = new EnumProperty<>("Mode:", targethudMode.Arsenic);
    @PropertyInfo(reliesOn = "Mode:", value = "Arsenic")
    public final DoubleProperty opacity = new DoubleProperty("opacity", new DoubleValue(0, 255, 100, 1));
    public final DoubleProperty x = new DoubleProperty("X", new DoubleValue(0, 1000, 1, 1));
    public final DoubleProperty y = new DoubleProperty("Y", new DoubleValue(0, 1000, 30, 1));
    String display;
    String health;
    String name;
    DecimalFormat decimalFormat = new DecimalFormat("0.0");
    @EventLink
    public final Listener<EventRender2D> onRender2D = event -> {
        FontRendererExtension<?> fr = Nexus.getNexus().getClickGuiScreen().getFontRenderer();
        if (fr == null) {
            return;
        }
        EntityPlayer target = PlayerUtils.getClosestPlayerWithin(Nexus.getNexus().getModuleManager().getModuleByClass(Aura.class).rangerot.getValue().getInput());
        if (target == null) {
            return;
        }
        final ScaledResolution scaledResolution = event.getSr();
        final int width = scaledResolution.getScaledWidth(), height = scaledResolution.getScaledHeight();

        if (targethudmode.getValue() == targethudMode.Arsenic) {
            DrawUtils.drawRect((float) x.getValue().getInput(), (float) y.getValue().getInput(), fr.getWidth("Name:" + target.getName()) + 6 + (float) x.getValue().getInput(), (float) y.getValue().getInput() + 21, new Color(0, 0, 0, (int) opacity.getValue().getInput()).getRGB());
            DrawUtils.drawRect((float) x.getValue().getInput(), (float) y.getValue().getInput(), fr.getWidth("Name:" + target.getName()) + 6 + (float) x.getValue().getInput(), (float) y.getValue().getInput() + 1, ColorUtils.getThemeRainbowColor(4, 0));
            fr.drawStringWithShadow("Name: " + EnumChatFormatting.WHITE + target.getName(), (float) x.getValue().getInput() + 1, (float) y.getValue().getInput() + 2, ColorUtils.getThemeRainbowColor(4, 0));
            fr.drawStringWithShadow("HP: " + EnumChatFormatting.WHITE + Math.floor(target.getHealth()), (float) x.getValue().getInput() + 1, (float) y.getValue().getInput() + 2 + fr.getHeight("N"), ColorUtils.getThemeRainbowColor(4, 0));
        }
        if (targethudmode.getValue() == targethudMode.RavenB4) {
            Color startColor = Color.red;
            Color endColor = Color.green;
            float healthPercentage = target.getHealth() / 20.0f;
            if (target.getHealth() > 15) {
                Color slider = new Color(105, 193, 3, 255);
                health = String.valueOf(EnumChatFormatting.GREEN + decimalFormat.format(target.getHealth()));
            } else if (target.getHealth() > 10 && target.getHealth() < 15) {
                Color slider = new Color(255, 216, 0, 255);
                health = String.valueOf(EnumChatFormatting.YELLOW + decimalFormat.format(target.getHealth()));
            } else if (target.getHealth() > 5 && target.getHealth() < 10) {
                Color slider = new Color(255, 104, 63, 255);
                health = String.valueOf(EnumChatFormatting.RED + decimalFormat.format(target.getHealth()));
            } else if (target.getHealth() > 0 && target.getHealth() < 5) {
                Color slider = new Color(141, 38, 9, 255);
                health = String.valueOf(EnumChatFormatting.RED + decimalFormat.format(target.getHealth()));
            }
            if (target.getHealth() > mc.thePlayer.getHealth() && target.getHealth() != mc.thePlayer.getHealth()) {
                display = target.getName() + " " + health + EnumChatFormatting.RED + " L ";
            }
            if (target.getHealth() <= mc.thePlayer.getHealth()) {
                display = target.getName() + " " + health + EnumChatFormatting.GREEN + " W ";
            }
            DrawUtils.drawBorderedRoundedRect((float) x.getValue().getInput() - 5, (float) y.getValue().getInput() - 5, fr.getWidth(display) + (float) x.getValue().getInput() + 5, (float) y.getValue().getInput() + 21 + 5, 15, 2, Color.white.getRGB(), new Color(0, 0, 0, 150).getRGB());
            fr.drawStringWithShadow(display, (float) x.getValue().getInput() + 1, (float) y.getValue().getInput() + 2, Color.white.getRGB());
            DrawUtils.drawRoundedRect((int) ((float) x.getValue().getInput() - 1), (int) ((float) y.getValue().getInput() + 14), (int) (fr.getWidth(display) + (float) x.getValue().getInput() + 1), (int) ((float) y.getValue().getInput() + 20), 6, new Color(0, 0, 0, 150).getRGB());
            int rectWidth = (int) (fr.getWidth(display));
            int minRectWidth = 20; // Minimum width for the rectangle
            int maxWidth = rectWidth; // Maximum width for the rectangle (initially set to the calculated width)
            float healthPercentage2 = (float) target.getHealth() / target.getMaxHealth(); // Calculate the health percentage
            int newRectWidth = (int) (minRectWidth + (maxWidth - minRectWidth) * healthPercentage2);
            DrawUtils.drawRoundedRect(
                    (int) ((float) x.getValue().getInput() - 1),
                    (int) ((float) y.getValue().getInput() + 14),
                    (int) ((float) x.getValue().getInput() + newRectWidth + 1),
                    (int) ((float) y.getValue().getInput() + 20),
                    6,
                    Color.white.getRGB()
            );
        }
        if (targethudmode.getValue() == targethudMode.Neon) {
            DrawUtils.drawRoundedRect((float) x.getValue().getInput() - 4, (float) y.getValue().getInput() - 4, 121 + (float) x.getValue().getInput(), ((float) y.getValue().getInput() + 36), 12, new Color(0, 0, 0, 150).getRGB());
            fr.drawStringWithShadow(target.getName(), ((float) x.getValue().getInput() + 36), ((float) y.getValue().getInput() + 1), Color.white.getRGB());
            fr.drawStringWithShadow("Health: " + decimalFormat.format(target.getHealth()), ((float) x.getValue().getInput() + 36), ((float) y.getValue().getInput() + 2 + fr.getHeight(String.valueOf(target.getHealth()))), Color.white.getRGB());
            DrawUtils.drawRoundedRect(
                    (int) ((float) x.getValue().getInput() + 36),
                    (int) ((float) y.getValue().getInput() + 22),
                    (int) ((float) x.getValue().getInput() + 110),
                    (int) ((float) y.getValue().getInput() + 30),
                    12,
                    new Color(0, 0, 0, 150).getRGB()
            );

            int health = (int) target.getHealth();
            while (health > 0) {
                int rectWidth = (int) ((float) x.getValue().getInput() + 36);
                int rectHeight = (int) ((float) y.getValue().getInput() + 22);
                int rectX2 = (int) ((float) x.getValue().getInput() + 110 - (20 - health) * 3.7);
                int rectY2 = (int) ((float) y.getValue().getInput() + 30);
                if (target.getHealth() > 15) {
                    DrawUtils.drawRoundedRect(rectWidth, rectHeight, rectX2, rectY2, 5, new Color(105, 193, 3, 255).getRGB());
                } else if (target.getHealth() > 10 && target.getHealth() <= 15) {
                    DrawUtils.drawRoundedRect(rectWidth, rectHeight, rectX2, rectY2, 5, new Color(255, 216, 0, 255).getRGB());
                } else if (target.getHealth() > 5 && target.getHealth() <= 10) {
                    DrawUtils.drawRoundedRect(rectWidth, rectHeight, rectX2, rectY2, 5, new Color(255, 104, 63, 255).getRGB());
                } else if (target.getHealth() > 0 && target.getHealth() <= 5) {
                    DrawUtils.drawRoundedRect(rectWidth, rectHeight, rectX2, rectY2, 5, new Color(141, 38, 9, 255).getRGB());
                }
                health--;
            }
            if (target.hurtTime == 0) {
                GlStateManager.color(1F, 1F, 1F, 1.0F);
            }
            if (target.hurtTime == 10) {
                GlStateManager.color(1F, 0.9F, 0.9F, 1.0F);
            }
            if (target.hurtTime == 9) {
                GlStateManager.color(1F, 0.9F, 0.9F, 1.0F);
            }
            if (target.hurtTime == 8) {
                GlStateManager.color(1F, 0.8F, 0.8F, 1.0F);
            }
            if (target.hurtTime == 7) {
                GlStateManager.color(1F, 0.7F, 0.7F, 1.0F);
            }
            if (target.hurtTime == 6) {
                GlStateManager.color(1F, 0.6F, 0.6F, 1.0F);
            }
            if (target.hurtTime == 5) {
                GlStateManager.color(1F, 0.5F, 0.5F, 1.0F);
            }
            if (target.hurtTime == 4) {
                GlStateManager.color(1F, 0.6F, 0.6F, 1.0F);
            }
            if (target.hurtTime == 3) {
                GlStateManager.color(1F, 0.7F, 0.7F, 1.0F);
            }
            if (target.hurtTime == 2) {
                GlStateManager.color(1F, 0.8F, 0.8F, 1.0F);
            }
            if (target.hurtTime == 1) {
                GlStateManager.color(1F, 0.9F, 0.9F, 1.0F);
            }
            GlStateManager.enableAlpha();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            mc.getTextureManager().bindTexture(((AbstractClientPlayer) target).getLocationSkin());
            Gui.drawScaledCustomSizeModalRect((int) ((float) x.getValue().getInput()), (int) ((float) y.getValue().getInput()), 8, 8, 8, 8, 32, 32, 64, 64);
            GlStateManager.disableBlend();
        }
        if (targethudmode.getValue() == targethudMode.Nexus) {

        }
    };

    public enum targethudMode {
        Arsenic,
        RavenB4,
        Neon,
        Nexus
    }
}
