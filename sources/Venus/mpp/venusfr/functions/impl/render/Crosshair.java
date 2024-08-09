/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.awt.Color;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.utils.math.MathUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import net.minecraft.client.settings.PointOfView;

@FunctionRegister(name="Crosshair", type=Category.Visual)
public class Crosshair
extends Function {
    private final ModeSetting mode = new ModeSetting("\u0412\u0438\u0434", "\u041a\u0440\u0443\u0433", "\u041a\u0440\u0443\u0433", "\u041a\u043b\u0430\u0441\u0438\u0447\u0435\u0441\u043a\u0438\u0439");
    private final BooleanSetting staticCrosshair = new BooleanSetting("\u0421\u0442\u0430\u0442\u0438\u0447\u0435\u0441\u043a\u0438\u0439", false);
    private float lastYaw;
    private float lastPitch;
    private float animatedYaw;
    private float animatedPitch;
    private float animation;
    private float animationSize;
    private final int outlineColor = Color.BLACK.getRGB();
    private final int entityColor = Color.RED.getRGB();

    public Crosshair() {
        this.addSettings(this.mode, this.staticCrosshair);
    }

    @Subscribe
    public void onDisplay(EventDisplay eventDisplay) {
        if (Crosshair.mc.player != null && Crosshair.mc.world != null && eventDisplay.getType() == EventDisplay.Type.POST) {
            float f = (float)mc.getMainWindow().getScaledWidth() / 2.0f;
            float f2 = (float)mc.getMainWindow().getScaledHeight() / 2.0f;
            float f3 = 5.0f;
            switch (this.mode.getIndex()) {
                case 0: {
                    float f4 = 5.0f;
                    int n = ColorUtils.interpolate(HUD.getColor(1), HUD.getColor(1), 1.0f - this.animation);
                    if (!((Boolean)this.staticCrosshair.get()).booleanValue()) {
                        f += this.animatedYaw;
                        f2 += this.animatedPitch;
                    }
                    this.animationSize = MathUtil.fast(this.animationSize, (1.0f - Crosshair.mc.player.getCooledAttackStrength(1.0f)) * 260.0f, 10.0f);
                    float f5 = 3.0f + ((Boolean)this.staticCrosshair.get() != false ? 0.0f : this.animationSize);
                    if (Crosshair.mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) break;
                    DisplayUtils.drawCircle1(f, f2, 0.0f, 360.0f, 3.5f, 3.0f, false, ColorUtils.getColor(90));
                    DisplayUtils.drawCircle1(f, f2, 0.0f, this.animationSize, 3.5f, 3.0f, false, ColorUtils.rgb(23, 21, 21));
                    break;
                }
                case 1: {
                    if (Crosshair.mc.gameSettings.getPointOfView() != PointOfView.FIRST_PERSON) {
                        return;
                    }
                    float f6 = 1.0f - Crosshair.mc.player.getCooledAttackStrength(1.0f);
                    float f7 = 1.0f;
                    float f8 = 3.0f;
                    float f9 = 2.0f + 8.0f * f6;
                    int n = Crosshair.mc.pointedEntity != null ? this.entityColor : -1;
                    this.drawOutlined(f - f7 / 2.0f, f2 - f9 - f8, f7, f8, ColorUtils.getColor(90));
                    this.drawOutlined(f - f7 / 2.0f, f2 + f9, f7, f8, ColorUtils.getColor(90));
                    this.drawOutlined(f - f9 - f8, f2 - f7 / 2.0f, f8, f7, n);
                    this.drawOutlined(f + f9, f2 - f7 / 2.0f, f8, f7, n);
                }
            }
        }
    }

    private void drawOutlined(float f, float f2, float f3, float f4, int n) {
        DisplayUtils.drawRectW((double)f - 0.5, (double)f2 - 0.5, f3 + 1.0f, f4 + 1.0f, this.outlineColor);
        DisplayUtils.drawRectW(f, f2, f3, f4, n);
    }
}

