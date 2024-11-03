
package dev.star.module.impl.display.targethud;

import dev.star.module.impl.display.HUDMod;
import dev.star.module.impl.render.Streamer;
import dev.star.utils.animations.ContinualAnimation;
import dev.star.utils.font.FontUtil;
import dev.star.utils.render.ColorUtil;
import dev.star.utils.render.RoundedUtil;
import dev.star.utils.render.StencilUtil;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import java.awt.*;

public class EchoTargetHUD extends TargetHUD {

    public EchoTargetHUD() {
        super("Echo");
    }

    private final ContinualAnimation animatedHealthBar = new ContinualAnimation();

    @Override
    public void render(float x, float y, float alpha, EntityLivingBase target) {
        setWidth(Math.max(145, BoldFont24.getStringWidth(target.getName()) + 40));
        setHeight(50);

        Color firstColor = ColorUtil.applyOpacity(HUDMod.getClientColors().getFirst(), alpha);
        Color secondColor = ColorUtil.applyOpacity(HUDMod.getClientColors().getSecond(), alpha);

        Color color = ColorUtil.applyOpacity(Color.BLACK, 0.3F);

        int textColor = ColorUtil.applyOpacity(-1, alpha);
        //LITICANE BREAKING CODE INSAE BIPAS 2023 NO RAT$$$$$
        // StencilUtil.initStencilToWrite();
        //  RenderUtil.renderRoundedRect(x, y, getWidth(), getHeight(), 4, Color.WHITE.getRGB());
        // StencilUtil.readStencilBuffer(0);

        Gui.drawRect2(x, y, getWidth(), getHeight(), color.getRGB());
        Gui.drawRect2(x, y, getWidth(), 15, color.getRGB());

        FontUtil.Font20.drawStringWithShadow(
                "Target",
                x + getWidth() / 2.0F - Font20.getStringWidth("Target") / 1.2F,
                y + Font20.getMiddleOfBox(15),
                textColor
        );

        FontUtil.BoldFont20.drawStringWithShadow(
                "Info",
                x + getWidth() / 2.0F + BoldFont20.getStringWidth("Info") / 2.2F,
                y + BoldFont20.getMiddleOfBox(15),
                textColor
        );

        if (target instanceof AbstractClientPlayer) {
            renderPlayer2D(x, y + 15, 35, 35, (AbstractClientPlayer) target);
        } else {
            Gui.drawRect2(x, y + 15, 35, 35, ColorUtil.applyOpacity(Color.WHITE.getRGB(), 0.5F));

            FontUtil.BoldFont32.drawCenteredStringWithShadow(
                    "?",
                    x + 20.5F - BoldFont32.getStringWidth("?") / 2.0F,
                    y + 32.5F - BoldFont32.getHeight() / 2.0F,
                    textColor
            );
        }

        BoldFont24.drawStringWithShadow(
                Streamer.enabled ? Streamer.customName.getString() : target.getName(),
                x + 40.0F,
                y + 20.5F,
                textColor
        );


        float healthPercent = MathHelper.clamp_float((target.getHealth() + target.getAbsorptionAmount()) / (target.getMaxHealth() + target.getAbsorptionAmount()), 0, 1);

        float realHealthWidth = getWidth() - 45;
        float realHealthHeight = 5;
        animatedHealthBar.animate(realHealthWidth * healthPercent, 50);

        float healthWidth = animatedHealthBar.getOutput();

        RoundedUtil.drawRound(
                x + 40,
                y + getHeight() - 10,
                realHealthWidth,
                realHealthHeight,
                1.0F,
                color
        );

        RoundedUtil.drawGradientHorizontal(
                x + 40,
                y + getHeight() - 10,
                healthWidth,
                realHealthHeight,
                1.0F,
                firstColor,
                secondColor
        );

        StencilUtil.uninitStencilBuffer();
    }


    @Override
    public void renderEffects(float x, float y, float alpha) {
        RoundedUtil.drawRound(
                x + 0.75F,
                y + 0.75F,
                getWidth() - 1.5F,
                getHeight() - 1.5F,
                4,
                Color.BLACK
        );
    }
}
