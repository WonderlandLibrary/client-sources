package dev.excellent.client.module.impl.render;

import dev.excellent.api.event.impl.render.Render2DEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.animation.Animation;
import dev.excellent.impl.util.animation.Easing;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.render.RectUtil;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;
import net.mojang.blaze3d.matrix.MatrixStack;

@ModuleInfo(name = "Crosshair", description = "Отображает кастомный прицел.", category = Category.RENDER)
public class Crosshair extends Module {
    public static Singleton<Crosshair> singleton = Singleton.create(() -> Module.link(Crosshair.class));
    private final ModeValue mode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Кастомный"),
                    new SubMode("Кругляшок")
            ).setDefault("Кругляшок");

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }

    private final BooleanValue animate = new BooleanValue("Анимация удара", this, true);
    private final BooleanValue dot = new BooleanValue("Точка", this, true, () -> !mode.is("Кастомный"));
    private final BooleanValue tCrosshair = new BooleanValue("T-образный", this, false, () -> !mode.is("Кастомный"));
    private final NumberValue width = new NumberValue("Ширина", this, 5, 0, 10, 1, () -> !mode.is("Кастомный"));
    private final NumberValue height = new NumberValue("Высота", this, 5, 0, 10, 1, () -> !mode.is("Кастомный"));
    private final NumberValue gap = new NumberValue("Точка", this, 3, 0, 10, 1, () -> !mode.is("Кастомный"));
    private final NumberValue thickness = new NumberValue("Толщина", this, 1, 1, 10, 1, () -> !mode.is("Кастомный"));
    public Animation animation = new Animation(Easing.LINEAR, 50);

    private final Listener<Render2DEvent> onRender2D = event -> {
        if (!mc.gameSettings.getPointOfView().equals(PointOfView.FIRST_PERSON)) return;
        MatrixStack matrix = event.getMatrix();
        double centerX = scaled().x / 2F;
        double centerY = scaled().y / 2F;

        double cwidth = this.width.getValue().doubleValue();
        double cheight = this.height.getValue().doubleValue();

        int color = -1;

        float thickness = this.thickness.getValue().floatValue();
        float swingProgress = mc.player.swingProgress;
        float sin = MathHelper.sin(swingProgress * swingProgress * (float) Math.PI);

        if (animate.getValue())
            animation.run(sin);

        if (mode.is("Кастомный")) {
            float gap = (float) (this.gap.getValue().floatValue() + (animate.getValue() ? (animation.getValue() * 4F) : 0));
            if (dot.getValue()) {
                RectUtil.drawRect(matrix, (float) (centerX - 0.5), (float) (centerY - 0.5), (float) (centerX - 0.5 + 1), (float) (centerY - 0.5 + 1), color);
            }
            // up
            if (!tCrosshair.getValue())
                RectUtil.drawRect(matrix, (float) (centerX - (thickness / 2F)), (float) (centerY - gap - cheight), (float) (centerX - (thickness / 2F) + thickness), (float) (centerY - gap - cheight + cheight), color);
            // down
            RectUtil.drawRect(matrix, (float) (centerX - (thickness / 2F)), (float) (centerY + gap), (float) (centerX - (thickness / 2F) + thickness), (float) (centerY + gap + cheight), color);
            // left
            RectUtil.drawRect(matrix, (float) (centerX - gap - cwidth), (float) (centerY - (thickness / 2F)), (float) (centerX - gap - cwidth + cwidth), (float) (centerY - (thickness / 2F) + thickness), color);
            // right
            RectUtil.drawRect(matrix, (float) (centerX + gap), (float) (centerY - (thickness / 2F)), (float) (centerX + gap + cwidth), (float) (centerY - (thickness / 2F) + thickness), color);
        }
        if (mode.is("Кругляшок")) {
            RectUtil.drawDuadsCircle(event.getMatrix(), (float) centerX, (float) centerY, 5 + animation.getValue(), 359, 3, ColorUtil.getColor(30, 30, 30, 0.4F * 255), false);
            RectUtil.drawDuadsCircleClientColored(event.getMatrix(), (float) centerX, (float) centerY, 5 + animation.getValue(), 359 - (359 * animation.getValue()), 3, true, 0.4F);
        }


    };

}
