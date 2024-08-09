package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import src.Wiksi.Wiksi;
import src.Wiksi.events.EventDisplay;
import src.Wiksi.events.EventDisplay.Type;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.Setting;
import src.Wiksi.functions.settings.impl.BooleanSetting;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.ui.styles.Style;
import src.Wiksi.utils.animations.AnimationMath;
import src.Wiksi.utils.math.MathUtil;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import java.awt.Color;
import net.minecraft.client.MainWindow;
import net.minecraft.client.settings.PointOfView;
import net.minecraft.util.math.MathHelper;

@FunctionRegister(name = "Crosshair", type = Category.Render)
public class Crosshair extends Function {
    private float circleAnimation = 0.0F;
    private final ModeSetting mode = new ModeSetting("Вид", "Орбиз", "Орбиз", "Класический", "Новый");
    private final SliderSetting witdh = (new SliderSetting("Толщина", 3.0F, 0.7F, 5.0F, 0.1f)).setVisible(() -> {
        return !mode.is("Орбиз") && !mode.is("Класический");
    });
    private final BooleanSetting staticCrosshair = (new BooleanSetting("Статический", false)).setVisible(() -> {
        return !mode.is("Новый");
    });
    private float lastYaw;
    private float lastPitch;
    private float animatedYaw;
    private float animatedPitch;
    private float animation;
    private float animationSize;
    private final int outlineColor;
    private final int entityColor;

    public Crosshair() {
        this.outlineColor = Color.BLACK.getRGB();
        this.entityColor = Color.RED.getRGB();
        this.addSettings(new Setting[]{this.mode, this.staticCrosshair, this.witdh});
    }

    @Subscribe
    public void onDisplay(EventDisplay e) {
        if (mc.player != null && mc.world != null && e.getType() == Type.POST) {
            float x;
            float y;
            float cooldown;
            float radius;
            switch (this.mode.getIndex()) {
                case 0:
                    x = (float)mc.getMainWindow().getScaledWidth() / 2.0F;
                    y = (float)mc.getMainWindow().getScaledHeight() / 2.0F;
                    cooldown = 5.0F;
                    this.animatedYaw = MathUtil.fast(this.animatedYaw, (this.lastYaw - mc.player.rotationYaw + mc.player.moveStrafing) * cooldown, 5.0F);
                    this.animatedPitch = MathUtil.fast(this.animatedPitch, (this.lastPitch - mc.player.rotationPitch + mc.player.moveForward) * cooldown, 5.0F);
                    this.animation = MathUtil.fast(this.animation, mc.objectMouseOver.getType() == net.minecraft.util.math.RayTraceResult.Type.ENTITY ? 1.0F : 0.0F, 5.0F);
                    int color = ColorUtils.interpolate(HUD.getColor(1), HUD.getColor(1), 1.0F - this.animation);
                    if (!(Boolean)this.staticCrosshair.get()) {
                        x += this.animatedYaw;
                        y += this.animatedPitch;
                    }

                    this.animationSize = MathUtil.fast(this.animationSize, (1.0F - mc.player.getCooledAttackStrength(1.0F)) * 3.0F, 10.0F);
                    radius = 3.0F + ((Boolean)this.staticCrosshair.get() ? 0.0F : this.animationSize);
                    if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
                        DisplayUtils.drawShadowCircle(x, y, radius * 2.0F, ColorUtils.setAlpha(color, 64));
                        DisplayUtils.drawCircle(x, y, radius, color);
                    }

                    this.lastYaw = mc.player.rotationYaw;
                    this.lastPitch = mc.player.rotationPitch;
                    break;
                case 1:
                    x = (float)mc.getMainWindow().getScaledWidth() / 2.0F;
                    y = (float)mc.getMainWindow().getScaledHeight() / 2.0F;
                    if (mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) {
                        return;
                    }

                    cooldown = 1.0F - mc.player.getCooledAttackStrength(0.0F);
                    float thickness = 1.0F;
                    radius = 3.0F;
                    float gap = 2.0F + 8.0F * cooldown;
                    color = mc.pointedEntity != null ? this.entityColor : -1;
                    this.drawOutlined(x - thickness / 2.0F, y - gap - radius, thickness, radius, color);
                    this.drawOutlined(x - thickness / 2.0F, y + gap, thickness, radius, color);
                    this.drawOutlined(x - gap - radius, y - thickness / 2.0F, radius, thickness, color);
                    this.drawOutlined(x + gap, y - thickness / 2.0F, radius, thickness, color);
                    break;
                case 2:
                    this.handleCrosshairRender();
            }
        }
    }

    private void handleCrosshairRender() {
        float tolschina = (Float)this.witdh.get();
        if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            MainWindow mainWindow = mc.getMainWindow();
            float x = (float)mainWindow.scaledWidth() / 2.0F;
            float y = (float)mainWindow.scaledHeight() / 2.0F;
            float calculateCooldown = mc.player.getCooledAttackStrength(1.0F);
            float endRadius = MathHelper.clamp(calculateCooldown * 360.0F, 0.0F, 360.0F);
            this.circleAnimation = AnimationMath.lerp(this.circleAnimation, -endRadius, 4.0F);
            Style style = new Style("style", new Color(ColorUtils.rgb(0, 0, 0)), new Color(ColorUtils.rgb(0, 0, 0)));
            Style mainStyle = new Style("mainStyle", new Color(ColorUtils.getColor(0)), new Color(ColorUtils.getColor(0)));
            DisplayUtils.drawCircle1(x, y, 0.0F, 360.0F, 3.5F, tolschina, false, style);
            DisplayUtils.drawCircle1(x, y, 0.0F, this.circleAnimation, 3.5F, tolschina, false, mainStyle);
        }
    }

    private void drawOutlined(float x, float y, float w, float h, int hex) {
        DisplayUtils.drawRectW((double)x - 0.5, (double)y - 0.5, (double)(w + 1.0F), (double)(h + 1.0F), this.outlineColor);
        DisplayUtils.drawRectW((double)x, (double)y, (double)w, (double)h, hex);
    }
}
