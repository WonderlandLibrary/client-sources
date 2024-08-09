package fun.ellant.functions.impl.hud;

import com.google.common.eventbus.Subscribe;
import com.jhlabs.image.Gradient;
import fun.ellant.Ellant;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.ui.styles.Style;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.text.GradientUtil;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.RayTraceResult.Type;

import java.awt.*;

@FunctionRegister(name = "Crosshair", type = Category.RENDER, desc = "Разный прицел")
public class Crosshair extends Function {

    private final ModeSetting mode = new ModeSetting("Вид", "Круг", "Круг", "Орбиз", "Класический");
    private final SliderSetting radius = new SliderSetting("Радиус", 3f, 3f, 6f, 0.1f).setVisible(() -> mode.is("Круг"));;

    private final BooleanSetting staticCrosshair = new BooleanSetting("Статический", false);
    /*private float lastYaw;
    private float lastPitch;

    private float animatedYaw;
    private float animatedPitch;

    private float animation;
    private float animationSize;

    private final int outlineColor = Color.BLACK.getRGB();
    private final int entityColor = Color.RED.getRGB();*/
    private float lastYaw;
    private float lastPitch;
    private float animatedYaw;
    private float animatedPitch;
    private float animation;
    private float animationSize;
    private final int outlineColor;
    private final int entityColor;

    public Crosshair() {
        //addSettings(mode, staticCrosshair);
        this.outlineColor = Color.BLACK.getRGB();
        this.entityColor = Color.RED.getRGB();
        this.addSettings(new Setting[]{this.mode, this.staticCrosshair, this.radius});
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player == null || mc.world == null || e.getType() != EventDisplay.Type.POST) {
            return;
        }

        float x = mc.getMainWindow().getScaledWidth() / 2f;
        float y = mc.getMainWindow().getScaledHeight() / 2f;

        float padding = 5.0F;
        float cooldown;
        float length;
        switch (mode.getIndex()) {
            case 0 -> {
                //this.addSettings(new Setting[]{this.radius});
                cooldown = 5.0F;
                int color = ColorUtils.interpolate(HUD.getColor(1), HUD.getColor(1), 1.0F - this.animation);
                if (!(Boolean) this.staticCrosshair.get()) {
                    x += this.animatedYaw;
                    y += this.animatedPitch;
                }

                this.animationSize = MathUtil.fast(this.animationSize, (1.0F - mc.player.getCooledAttackStrength(1.0F)) * 260.0F, 10.0F);
                length = 3.0F + ((Boolean) this.staticCrosshair.get() ? 0.0F : this.animationSize);
                if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                    DisplayUtils.drawCircle2(x, y, 0.0F, 360.0F, radius.get(), 3.0F, false, ColorUtils.getColor(90));
                    DisplayUtils.drawCircle(x, y, 0.0F, this.animationSize, radius.get(), 3.0F, false, ColorUtils.rgb(23, 21, 21));
                }
            }
            case 1 -> {
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
            case 2 -> {
                if (mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) return;

                cooldown = 1 - mc.player.getCooledAttackStrength(0);

                float thickness = 1;
                /*float */length = 3;
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