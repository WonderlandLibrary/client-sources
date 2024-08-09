package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import im.expensive.events.EventDisplay;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.utils.math.MathUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.RayTraceResult.Type;

import java.awt.*;

@FunctionRegister(name = "Crosshair", type = Category.Render)
public class Crosshair extends Function {

    private final ModeSetting mode = new ModeSetting("Вид", "Орбиз", "Орбиз", "Класический");

    private final BooleanSetting staticCrosshair = new BooleanSetting("Статический", false);
    private float lastYaw;
    private float lastPitch;

    private float animatedYaw;
    private float animatedPitch;

    private float animation;
    private float animationSize;

    private final int outlineColor = Color.BLACK.getRGB();
    private final int entityColor = Color.RED.getRGB();

    public Crosshair() {
        addSettings(mode, staticCrosshair);
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.POST) {
            return;
        }

        float x = mc.getMainWindow().getScaledWidth() / 2f;
        float y = mc.getMainWindow().getScaledHeight() / 2f;

        switch (mode.getIndex()) {
            case 0 -> {
                float size = 5;

                animatedYaw = MathUtil.fast(animatedYaw,
                        ((lastYaw - mc.player.rotationYaw) + mc.player.moveStrafing) * size,
                        5);
                animatedPitch = MathUtil.fast(animatedPitch,
                        ((lastPitch - mc.player.rotationPitch) + mc.player.moveForward) * size, 5);
                animation = MathUtil.fast(animation, mc.objectMouseOver.getType() == Type.ENTITY ? 1 : 0, 5);

                int color = ColorUtils.interpolate(HUD.getColor(1), HUD.getColor(1), 1 - animation);

                if (!staticCrosshair.get()) {
                    x += animatedYaw;
                    y += animatedPitch;
                }

                animationSize = MathUtil.fast(animationSize, (1 - mc.player.getCooledAttackStrength(1)) * 3, 10);

                float radius = 3 + (staticCrosshair.get() ? 0 : animationSize);
                if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                    DisplayUtils.drawShadowCircle(x, y, radius * 2, ColorUtils.setAlpha(color, 64));
                    DisplayUtils.drawCircle(x, y, radius, color);
                }
                lastYaw = mc.player.rotationYaw;
                lastPitch = mc.player.rotationPitch;
            }

            case 1 -> {
                if (mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) return;

                float cooldown = 1 - mc.player.getCooledAttackStrength(0);

                float thickness = 1;
                float length = 3;
                float gap = 2 + 8 * cooldown;

                int color = mc.pointedEntity != null ? entityColor : -1;

                drawOutlined(x - thickness / 2, y - gap - length, thickness, length, color);
                drawOutlined(x - thickness / 2, y + gap, thickness, length, color);

                drawOutlined(x - gap - length, y - thickness / 2, length, thickness, color);
                drawOutlined(x + gap, y - thickness / 2, length, thickness, color);
            }
        }
    }

    private void drawOutlined(
            final float x,
            final float y,
            final float w,
            final float h,
            final int hex
    ) {
        DisplayUtils.drawRectW(x - 0.5, y - 0.5, w + 1, h + 1, outlineColor); // бля че за хуйня поч его хуярит салат что наделал
        DisplayUtils.drawRectW(x, y, w, h, hex);
    }
}
