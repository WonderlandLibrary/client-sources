package dev.star.module.impl.display.targethud;

import dev.star.module.impl.display.HUDMod;
import dev.star.utils.animations.ContinualAnimation;
import dev.star.utils.font.FontUtil;
import dev.star.utils.render.*;
import dev.star.utils.tuples.Pair;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class StarTargetHUD extends TargetHUD {

    private EntityLivingBase target;


    private final ContinualAnimation animation = new ContinualAnimation();


    public StarTargetHUD() {
        super("Star");
    }

    @Override
    public void render(float x, float y, float alpha, EntityLivingBase target) {
        setWidth(Math.max(150, FontUtil.BoldFont26.getStringWidth(target.getName()) + 45));
        setHeight(42);

        this.target = target;

        Color background = new Color(0, 0, 0, (int) (25 * alpha));
        Color text = new Color(255, 255, 255, (int) alpha * 255);

        RoundedUtil.drawRound(x, y, getWidth(), getHeight(), 4, background);

        if (target instanceof AbstractClientPlayer) {
            StencilUtil.initStencilToWrite();
            RenderUtil.renderRoundedRect(x + 3, y + 3, 36, 36, 15, -1);
            StencilUtil.readStencilBuffer(1);
            RenderUtil.color(-1, alpha);
            renderPlayer2D(x + 3, y + 3, 36, 36, (AbstractClientPlayer) target);
            StencilUtil.uninitStencilBuffer();
            GlStateManager.disableBlend();
        } else {
            FontUtil.BoldFont32.drawCenteredStringWithShadow("?", x + 20, y + 17 - FontUtil.BoldFont32.getHeight() / 2f, text.getRGB());
        }

        FontUtil.Font26.drawStringWithShadow(target.getName(), x + 43.5F, y + 4, text.getRGB());
        FontUtil.BoldFont24.drawStringWithShadow(target.getHealth() >= mc.thePlayer.getHealth() ? EnumChatFormatting.RED + "Losing" : EnumChatFormatting.GREEN + "Winning", x + 44F, y + 18, text.getRGB());

        float percentage = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0, 1);

        float healthWidth = getWidth() - 48;
        animation.animate(healthWidth * percentage, 20);

        float animationOutput = animation.getOutput();


        int i = 0;

        int index = i * 20;
        Pair<Color, Color> colors = HUDMod.getClientColors();

        Color textcolor = ColorUtil.interpolateColorsBackAndForth(5, index, colors.getFirst(), colors.getSecond(), false);
        RoundedUtil.drawRoundOutline(x, y, getWidth(), getHeight(), 5,1,new Color(0,0,0,0),textcolor);
        RoundedUtil.drawRound(x + 44, (y + getHeight() - 8), 98, 3, 1.5f, background);
        RoundedUtil.drawGradientHorizontal(x + 44, (y + getHeight() - 8), animationOutput, 3, 1.5f, HUDMod.getClientColors().getFirst(), HUDMod.getClientColors().getSecond());
    }


    @Override
    public void renderEffects(float x, float y, float alpha) {
        float percentage = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0, 1);
        float healthWidth = getWidth() - 48;

        animation.animate(healthWidth * percentage, 20);
        int i = 0;

        int index = i * 20;
        Pair<Color, Color> colors = HUDMod.getClientColors();

        Color textcolor = ColorUtil.interpolateColorsBackAndForth(5, index, colors.getFirst(), colors.getSecond(), false);
        RoundedUtil.drawRound(x, y, getWidth(), getHeight(), 5, textcolor);
    }
}