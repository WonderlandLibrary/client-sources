package com.alan.clients.component.impl.render;

import com.alan.clients.Client;
import com.alan.clients.component.Component;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.font.Fonts;
import com.alan.clients.font.Weight;
import com.alan.clients.ui.theme.Themes;
import com.alan.clients.util.animation.Animation;
import com.alan.clients.util.animation.Easing;
import com.alan.clients.util.math.MathUtil;
import com.alan.clients.util.render.ColorUtil;
import com.alan.clients.util.render.RenderUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.MathHelper;

import java.awt.*;

import static com.alan.clients.layer.Layers.*;

public class PercentageComponent extends Component {

    private static final float WIDTH = 150;
    private static final float HEIGHT = 10;

    private static final Animation animation = new Animation(Easing.LINEAR, 50);
    private static final Animation scaleAnimation = new Animation(Easing.EASE_OUT_EXPO, 900);
    public static boolean active;
    private static boolean finished = true;
    private static float percent;

    @EventLink(EventBusPriorities.LOWEST)
    public final Listener<PreUpdateEvent> onPreUpdate = event -> active = false;

    @EventLink
    public final Listener<Render2DEvent> onRender2D = event -> {
        if (finished) {
            return;
        }

        final ScaledResolution sr = event.getScaledResolution();

        final float x = (sr.getScaledWidth() * 0.5F) - (WIDTH * 0.5F);
        final float y = (sr.getScaledHeight() * 0.5F) + 15;

        scaleAnimation.run(!active ? 1.1 : 1);
        scaleAnimation.setDuration(900);
        scaleAnimation.setEasing(Easing.EASE_OUT_EXPO);

        double scale = scaleAnimation.getValue();
        double opacity = 1 - 10 * Math.abs(1 - scaleAnimation.getValue());

        animation.run(percent);

        final Themes theme = Client.INSTANCE.getThemeManager().getTheme();

        getLayer(REGULAR, 1).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + WIDTH * 0.5F) * (1 - scale), (y + HEIGHT * 0.5F) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(x, y, WIDTH, HEIGHT, theme.getRound(), ColorUtil.withAlpha(theme.getBackgroundShade(), (int) (theme.getBackgroundShade().getAlpha() * opacity)));
            RenderUtil.drawRoundedGradientRect(x, y, WIDTH * animation.getValue(), HEIGHT, theme.getRound(), ColorUtil.withAlpha(theme.getFirstColor(), (int) (255 * opacity)), ColorUtil.withAlpha(theme.getSecondColor(), (int) (255 * opacity)), false);

            String text = MathUtil.round(animation.getValue() * 100, 1) + "%";
            Fonts.MAIN.get(16, Weight.REGULAR).drawWithShadow(text, x + WIDTH - Fonts.MAIN.get(16, Weight.REGULAR).width(text) - 2, y + 3, ColorUtil.withAlpha(Color.WHITE, (int) (255 * opacity)).getRGB());

            GlStateManager.popMatrix();
        });

        getLayer(BLOOM).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + WIDTH * 0.5F) * (1 - scale), (y + HEIGHT * 0.5F) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(x + 0.5F, y + 0.5F, WIDTH - 1, HEIGHT - 1, theme.getRound() + 1, ColorUtil.withAlpha(getTheme().getDropShadow(), (int) (getTheme().getDropShadow().getAlpha() * opacity)));

            GlStateManager.popMatrix();
        });

        getLayer(BLUR).add(() -> {
            GlStateManager.pushMatrix();
            GlStateManager.translate((x + WIDTH * 0.5F) * (1 - scale), (y + HEIGHT * 0.5F) * (1 - scale), 0);
            GlStateManager.scale(scale, scale, 0);

            RenderUtil.roundedRectangle(x, y, WIDTH, HEIGHT, theme.getRound(), ColorUtil.withAlpha(Color.BLACK, (int) (255 * opacity)));

            GlStateManager.popMatrix();
        });

        if (!active && scaleAnimation.isFinished()) {
            animation.setValue(0);
            finished = true;
        }
    };

    public static void setActive(float percent) {
        percent = MathHelper.clamp_float(percent, 0.0F, 1.0F);
        PercentageComponent.percent = percent;
        finished = false;
        active = true;
    }
}