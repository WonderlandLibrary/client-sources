package wtf.shiyeno.modules.impl.render;

import net.minecraft.client.MainWindow;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.managment.Managment;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.modules.settings.imp.SliderSetting;
import wtf.shiyeno.ui.midnight.Style;
import wtf.shiyeno.util.animations.Animation;
import wtf.shiyeno.util.math.MathUtil;
import wtf.shiyeno.util.render.animation.AnimationMath;

import static wtf.shiyeno.util.render.ColorUtil.*;
import static wtf.shiyeno.util.render.RenderUtil.Render2D.*;

@FunctionAnnotation(name = "Crosshair", type = Type.Render)
public class Crosshair extends Function {
    private float circleAnimation = 0.0F;

    @Override
    public void onEvent(Event event) {
        if (event instanceof EventRender e) {
            handleCrosshairRender();
        }
    }

    private void handleCrosshairRender() {
        if (mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) {
            return;
        }

        final MainWindow mainWindow = mc.getMainWindow();

        final float x = (float) mainWindow.scaledWidth() / 2.0F;
        final float y = (float) mainWindow.scaledHeight() / 2.0F;

        final float calculateCooldown = mc.player.getCooledAttackStrength(1.0F);
        final float endRadius = MathHelper.clamp(calculateCooldown * 360, 0, 360);

        this.circleAnimation = AnimationMath.lerp(this.circleAnimation, -endRadius, 4);

        final int mainColor = rgba(30, 30, 30, 255);
        Style style = Managment.STYLE_MANAGER.getCurrentStyle();

        drawCircle(x, y, 0, 360, 3.5f, 3, false, mainColor);
        drawCircle(x, y, 0, circleAnimation, 3.5f, 3, false, style);
    }
}