package dev.star.module.impl.display.targethud;

import dev.star.module.impl.display.HUDMod;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.render.RenderUtil;
import dev.star.utils.time.TimerUtil;
import dev.star.utils.tuples.Pair;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;

import java.awt.*;

public class RavenTargetHUD extends TargetHUD {
    public RavenTargetHUD() {
        super("Raven");
    }
    private double lastHealth;
    private float lastHealthBar;
    private TimerUtil healthBarTimer = null;


    private int[] gradientColorWheel(Pair<Color, Color> colors) {
        Color firstColor = ColorUtil.interpolateColorsBackAndForth(15, 0, colors.getFirst(), colors.getSecond(), false);
        Color secondColor = ColorUtil.interpolateColorsBackAndForth(15, 90, colors.getFirst(), colors.getSecond(), false);
        Color thirdColor = ColorUtil.interpolateColorsBackAndForth(15, 180, colors.getFirst(), colors.getSecond(), false);
        Color fourthColor = ColorUtil.interpolateColorsBackAndForth(15, 270, colors.getFirst(), colors.getSecond(), false);

        return new int[]{firstColor.getRGB(), secondColor.getRGB(), thirdColor.getRGB(), fourthColor.getRGB()};
    }

    @Override
    public void render(float x, float y, float alpha, EntityLivingBase target) {
        String playerInfo = target.getDisplayName().getFormattedText();

        double health1 = target.getHealth() / target.getMaxHealth();
        if (health1 != lastHealth) {
            (healthBarTimer = new TimerUtil()).start();
        }

        lastHealth = health1;
        playerInfo += " " + RenderUtil.getHealthStr(target);

        String string = playerInfo + " " + ((health1 <= RenderUtil.getCompleteHealth(mc.thePlayer) / mc.thePlayer.getMaxHealth()) ? "§aW" : "§cL");

        final int n2 = 8;
        final int n3 = (int) (mc.fontRendererObj.getStringWidth(string) + n2);
        final int n4 = (int) x;
        final int n5 = (int) y;
        final int n6 = n4 - n2;
        final int n7 = n5 - n2;
        final int n8 = n4 + n3;
        final int n9 = n5 + (mc.fontRendererObj.FONT_HEIGHT + 5) - 6 + n2;
        final int n10 = 210;
        if (n10 > 0) {
            final int n11 = (n10 > 110) ? 110 : n10;
            final int n12 = (n10 > 210) ? 210 : n10;
            final int[] array =  gradientColorWheel(HUDMod.getClientColors());;
            RenderUtil.drawRoundedGradientOutlinedRectangle((float) n6, (float) n7, (float) n8, (float) (n9 + 13), 10.0f, RenderUtil.merge(Color.black.getRGB(), n11), RenderUtil.merge(array[0], n10), RenderUtil.merge(array[1], n10)); // outline
            final int n13 = n6 + 6;
            final int n14 = n8 - 6;
            final int n15 = n9;
            RenderUtil.drawRoundedRectangle((float) n13, (float) n15, (float) n14, (float) (n15 + 5), 4.0f, RenderUtil.merge(Color.black.getRGB(), n11)); // background
            int k = RenderUtil.merge(array[0], n12);
            int n16 = RenderUtil.merge(array[1], n12);
            float healthBar = (float) (int) (n14 + (n13 - n14) * (1.0 - ((health1 < 0.05) ? 0.05 : health1)));
            if (healthBar - n13 < 3) { // if goes below, the rounded health bar glitches out
                healthBar = n13 + 3;
            }
            if (healthBar != lastHealthBar && lastHealthBar - n13 >= 3 && healthBarTimer != null) {
                float diff = lastHealthBar - healthBar;
                if (diff > 0) {
                    lastHealthBar = lastHealthBar - healthBarTimer.getValueFloat(0, diff, 1);
                } else {
                    lastHealthBar = healthBarTimer.getValueFloat(lastHealthBar, healthBar, 1);
                }
            } else {
                lastHealthBar = healthBar;
            }


            RenderUtil.drawRoundedGradientRect((float) n13, (float) n15, lastHealthBar, (float) (n15 + 5), 4.0f, k, k, k, n16); // health bar
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            mc.fontRendererObj.drawString(string, (float) n4, (float) n5, (new Color(220, 220, 220, 255).getRGB() & 0xFFFFFF) | RenderUtil.clamp(n10 + 15) << 24, true);
            GlStateManager.disableBlend();
            GlStateManager.popMatrix();
        }
    }


    @Override
    public void renderEffects(float x, float y, float alpha) {
        Gui.drawRect(x, y, x + getWidth(), y + 39.5F, ColorUtil.applyOpacity(Color.BLACK.getRGB(), alpha));
    }
}
