/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import mpp.venusfr.events.EventDisplay;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.impl.render.HUD;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.animations.Animation;
import mpp.venusfr.utils.animations.Direction;
import mpp.venusfr.utils.animations.impl.DecelerateAnimation;
import mpp.venusfr.utils.math.Vector4i;
import mpp.venusfr.utils.projections.ProjectionUtil;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.DisplayUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;

@FunctionRegister(name="TargetESP", type=Category.Visual)
public class TargetESP
extends Function {
    private final KillAura killAura;
    public ModeSetting mod = new ModeSetting("\u041c\u043e\u0434", "Client", "Client", "\u041f\u0440\u0438\u0437\u0440\u0430\u043a\u0438", "Nightly", "Client2", "Client3", "Client4");
    public SliderSetting speed = new SliderSetting("\u0421\u043a\u043e\u0440\u043e\u0441\u0442\u044c", 3.0f, 0.7f, 9.0f, 1.0f);
    public SliderSetting size = new SliderSetting("\u0420\u0430\u0437\u043c\u0435\u0440", 30.0f, 5.0f, 140.0f, 1.0f);
    public SliderSetting bright = new SliderSetting("\u042f\u0440\u043a\u043e\u0441\u0442\u044c", 255.0f, 1.0f, 255.0f, 1.0f);
    private final Animation alpha = new DecelerateAnimation(600, 255.0);
    public static final long detime = System.currentTimeMillis();

    public TargetESP(KillAura killAura) {
        this.killAura = killAura;
        this.addSettings(this.mod, this.speed, this.size, this.bright);
    }

    @Subscribe
    private void onDisplay(EventDisplay eventDisplay) {
        Vector2f vector2f;
        float f;
        double d;
        double d2;
        if (this.mod.is("Client")) {
            boolean bl = this.killAura.isState();
            if (eventDisplay.getType() != EventDisplay.Type.PRE) {
                return;
            }
            this.alpha.setDirection(!bl || this.killAura.getTarget() == null ? Direction.BACKWARDS : Direction.FORWARDS);
            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double d3 = this.killAura.getTarget().lastTickPosX + (this.killAura.getTarget().getPosX() - this.killAura.getTarget().lastTickPosX) * (double)eventDisplay.getPartialTicks();
                d2 = this.killAura.getTarget().lastTickPosY + (this.killAura.getTarget().getPosY() - this.killAura.getTarget().lastTickPosY) * (double)eventDisplay.getPartialTicks();
                d = this.killAura.getTarget().lastTickPosZ + (this.killAura.getTarget().getPosZ() - this.killAura.getTarget().lastTickPosZ) * (double)eventDisplay.getPartialTicks();
                Vector2f vector2f2 = ProjectionUtil.project(d3, d2 + 1.0, d);
                int n = ColorUtils.setAlpha(ColorUtils.getColor(1), (int)this.alpha.getOutput());
                int n2 = ColorUtils.setAlpha(ColorUtils.getColor(90), (int)this.alpha.getOutput());
                if (vector2f2 != null) {
                    GL11.glPushMatrix();
                    GL11.glTranslatef(vector2f2.x, vector2f2.y, 0.0f);
                    GL11.glRotatef((float)(Math.sin((float)(System.currentTimeMillis() - detime) / 1000.0f) * 360.0), 0.0f, 0.0f, 1.0f);
                    GL11.glTranslatef(-vector2f2.x, -vector2f2.y, 0.0f);
                    DisplayUtils.drawImage(new ResourceLocation("venusfr/images/target.png"), vector2f2.x - 50.0f, vector2f2.y - 50.0f, 100, 100, n, n, n2, n2);
                    GL11.glPopMatrix();
                }
            }
        }
        if (this.mod.is("Client2")) {
            if (eventDisplay.getType() != EventDisplay.Type.PRE) {
                return;
            }
            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double d4 = Math.sin((double)System.currentTimeMillis() / 1000.0);
                f = 70.0f;
                Vector3d vector3d = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks());
                vector2f = ProjectionUtil.project(vector3d.x, vector3d.y + (double)(this.killAura.getTarget().getHeight() / 2.0f), vector3d.z);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(vector2f.x, vector2f.y, 0.0f);
                GlStateManager.rotatef((float)d4 * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.translatef(-vector2f.x, -vector2f.y, 0.0f);
                DisplayUtils.drawImage(new ResourceLocation("venusfr/images/target2.png"), vector2f.x - f / 2.0f, vector2f.y - f / 2.0f, f, f, new Vector4i(ColorUtils.rgb(255, 255, 255), ColorUtils.setAlpha(HUD.getColor(90, 1.0f), 220), ColorUtils.setAlpha(HUD.getColor(180, 1.0f), 220), ColorUtils.setAlpha(HUD.getColor(270, 1.0f), 220)));
                GlStateManager.popMatrix();
            }
        }
        if (this.mod.is("Client3")) {
            if (eventDisplay.getType() != EventDisplay.Type.PRE) {
                return;
            }
            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double d5 = Math.sin((double)System.currentTimeMillis() / 1000.0);
                f = 70.0f;
                Vector3d vector3d = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks());
                vector2f = ProjectionUtil.project(vector3d.x, vector3d.y + (double)(this.killAura.getTarget().getHeight() / 2.0f), vector3d.z);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(vector2f.x, vector2f.y, 0.0f);
                GlStateManager.rotatef((float)d5 * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.translatef(-vector2f.x, -vector2f.y, 0.0f);
                DisplayUtils.drawImage(new ResourceLocation("venusfr/images/target3.png"), vector2f.x - f / 2.0f, vector2f.y - f / 2.0f, f, f, new Vector4i(ColorUtils.rgb(255, 255, 255), ColorUtils.setAlpha(HUD.getColor(90, 1.0f), 220), ColorUtils.setAlpha(HUD.getColor(180, 1.0f), 220), ColorUtils.setAlpha(HUD.getColor(270, 1.0f), 220)));
                GlStateManager.popMatrix();
            }
        }
        if (this.mod.is("Client4")) {
            if (eventDisplay.getType() != EventDisplay.Type.PRE) {
                return;
            }
            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                double d6 = Math.sin((double)System.currentTimeMillis() / 1000.0);
                f = 70.0f;
                Vector3d vector3d = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks());
                vector2f = ProjectionUtil.project(vector3d.x, vector3d.y + (double)(this.killAura.getTarget().getHeight() / 2.0f), vector3d.z);
                GlStateManager.pushMatrix();
                GlStateManager.translatef(vector2f.x, vector2f.y, 0.0f);
                GlStateManager.rotatef((float)d6 * 360.0f, 0.0f, 0.0f, 1.0f);
                GlStateManager.translatef(-vector2f.x, -vector2f.y, 0.0f);
                DisplayUtils.drawImage(new ResourceLocation("venusfr/images/target4.png"), vector2f.x - f / 2.0f, vector2f.y - f / 2.0f, f, f, new Vector4i(ColorUtils.rgb(255, 255, 255), ColorUtils.setAlpha(HUD.getColor(90, 1.0f), 220), ColorUtils.setAlpha(HUD.getColor(180, 1.0f), 220), ColorUtils.setAlpha(HUD.getColor(270, 1.0f), 220)));
                GlStateManager.popMatrix();
            }
        }
        if (this.mod.is("Nightly")) {
            double d7;
            double d8;
            double d9;
            float f2;
            float f3;
            int n;
            ResourceLocation resourceLocation;
            Vector3d[] vector3dArray;
            Vector3d[] vector3dArray2;
            Vector3d[] vector3dArray3;
            Vector3d vector3d;
            Vector3d vector3d2;
            Vector3d vector3d3;
            int n3;
            float f4;
            if (eventDisplay.getType() != EventDisplay.Type.PRE) {
                return;
            }
            if (this.killAura.isState() && this.killAura.getTarget() != null) {
                float f5 = ((Float)this.speed.get()).floatValue();
                float f6 = ((Float)this.size.get()).floatValue();
                int n4 = ((Float)this.bright.get()).intValue();
                d2 = f5;
                d = (double)System.currentTimeMillis() / (500.0 / d2);
                double d10 = Math.sin(d);
                double d11 = Math.cos(d);
                f4 = f6;
                n3 = n4;
                vector3d3 = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks()).add(0.0, this.killAura.getTarget().getHeight(), 0.0);
                vector3d2 = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks()).add(0.0, this.killAura.getTarget().getHeight() / 2.0f, 0.0);
                vector3d = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks());
                vector3dArray3 = new Vector3d[]{vector3d2.add(0.0, 0.5, 0.0)};
                vector3dArray2 = new Vector3d[]{vector3d2.add(0.0, 0.5, 0.0)};
                vector3dArray = new Vector3d[]{vector3d2.add(0.0, 0.5, 0.0)};
                resourceLocation = new ResourceLocation("venusfr/images/firefly.png");
                for (n = 0; n < 40; ++n) {
                    int n5;
                    int n6;
                    Vector2f vector2f3;
                    Vector3d vector3d4;
                    int n7;
                    f3 = n3 - n * 5;
                    if (f3 < 0.0f) {
                        f3 = 0.0f;
                    }
                    f2 = f4 * (1.0f - (float)n * 0.02f);
                    d9 = d - (double)n * 0.1;
                    d8 = Math.sin(d9);
                    d7 = Math.cos(d9);
                    double d12 = Math.sin(d9 + 0.5);
                    double d13 = Math.cos(d9 + 0.5);
                    double d14 = Math.sin(d9 + 1.0);
                    double d15 = Math.cos(d9 + 1.0);
                    float f7 = (float)n * 7.2f;
                    for (n7 = 0; n7 < vector3dArray3.length; ++n7) {
                        vector3d4 = vector3dArray3[n7].add(0.0, 0.0, 0.0);
                        vector2f3 = ProjectionUtil.project(vector3d4.x + d13 * 0.5, vector3d4.y + d7 * 0.35, vector3d4.z + d12 * 0.5);
                        GlStateManager.pushMatrix();
                        GlStateManager.translatef(vector2f3.x, vector2f3.y, 0.0f);
                        GlStateManager.rotatef((float)(d8 * 360.0 + (double)(n7 * 180) + (double)f7), 0.0f, 0.0f, 1.0f);
                        GlStateManager.translatef(-vector2f3.x, -vector2f3.y, 0.0f);
                        n6 = ColorUtils.setAlpha(ColorUtils.getColor(0), 165);
                        n5 = ColorUtils.setAlpha(ColorUtils.getColor(90), 165);
                        DisplayUtils.drawImage(resourceLocation, vector2f3.x - f2 / 2.0f, vector2f3.y - f2 / 2.0f, f2, f2, n6, n6, n5, n5);
                        GlStateManager.popMatrix();
                    }
                    for (n7 = 0; n7 < vector3dArray2.length; ++n7) {
                        vector3d4 = vector3dArray2[n7].add(0.0, 0.0, 0.0);
                        vector2f3 = ProjectionUtil.project(vector3d4.x - d15 * 0.5, vector3d4.y - d7 * 0.35, vector3d4.z - d14 * 0.5);
                        GlStateManager.pushMatrix();
                        GlStateManager.translatef(vector2f3.x, vector2f3.y, 0.0f);
                        GlStateManager.rotatef((float)(-d8 * 360.0 + (double)(n7 * 180) + (double)f7), 0.0f, 0.0f, 1.0f);
                        GlStateManager.translatef(-vector2f3.x, -vector2f3.y, 0.0f);
                        n6 = ColorUtils.setAlpha(ColorUtils.getColor(90), 165);
                        n5 = ColorUtils.setAlpha(ColorUtils.getColor(180), 165);
                        DisplayUtils.drawImage(resourceLocation, vector2f3.x - f2 / 2.0f, vector2f3.y - f2 / 2.0f, f2, f2, n6, n6, n5, n5);
                        GlStateManager.popMatrix();
                    }
                    for (n7 = 0; n7 < vector3dArray.length; ++n7) {
                        vector3d4 = vector3dArray[n7].add(0.0, 0.0, 0.0);
                        vector2f3 = ProjectionUtil.project(vector3d4.x - d7 * 0.5, vector3d4.y + d7 * 0.35, vector3d4.z - d8 * 0.5);
                        GlStateManager.pushMatrix();
                        GlStateManager.translatef(vector2f3.x, vector2f3.y, 0.0f);
                        GlStateManager.rotatef((float)(-d8 * 360.0 + (double)(n7 * 180) + (double)f7), 0.0f, 0.0f, 1.0f);
                        GlStateManager.translatef(-vector2f3.x, -vector2f3.y, 0.0f);
                        n6 = ColorUtils.setAlpha(ColorUtils.getColor(180), 165);
                        n5 = ColorUtils.setAlpha(ColorUtils.getColor(270), 165);
                        DisplayUtils.drawImage(resourceLocation, vector2f3.x - f2 / 2.0f, vector2f3.y - f2 / 2.0f, f2, f2, n6, n6, n5, n5);
                        GlStateManager.popMatrix();
                    }
                }
            }
            if (this.mod.is("\u041f\u0440\u0438\u0437\u0440\u0430\u043a\u0438")) {
                if (eventDisplay.getType() != EventDisplay.Type.PRE) {
                    return;
                }
                if (this.killAura.isState() && this.killAura.getTarget() != null) {
                    float f8 = ((Float)this.speed.get()).floatValue();
                    float f9 = ((Float)this.size.get()).floatValue();
                    int n8 = ((Float)this.bright.get()).intValue();
                    d2 = f8;
                    d = (double)System.currentTimeMillis() / (500.0 / d2);
                    double d16 = Math.sin(d);
                    double d17 = Math.cos(d);
                    f4 = f9;
                    n3 = n8;
                    vector3d3 = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks()).add(0.0, this.killAura.getTarget().getHeight(), 0.0);
                    vector3d2 = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks()).add(0.0, this.killAura.getTarget().getHeight() / 2.0f, 0.0);
                    vector3d = this.killAura.getTarget().getPositon(eventDisplay.getPartialTicks());
                    vector3dArray3 = new Vector3d[]{vector3d2.add(0.0, 0.5, 0.0)};
                    vector3dArray2 = new Vector3d[]{vector3d.add(0.0, 0.5, 0.0)};
                    vector3dArray = new Vector3d[]{vector3d3.add(0.0, 0.5, 0.0)};
                    resourceLocation = new ResourceLocation("venusfr/images/hud/glow.png");
                    for (n = 0; n < 40; ++n) {
                        Vector2f vector2f4;
                        Vector3d vector3d5;
                        int n9;
                        f3 = n3 - n * 5;
                        if (f3 < 0.0f) {
                            f3 = 0.0f;
                        }
                        f2 = f4 * (1.0f - (float)n * 0.02f);
                        d9 = d - (double)n * 0.1;
                        d8 = Math.sin(d9);
                        d7 = Math.cos(d9);
                        float f10 = (float)n * 7.2f;
                        for (n9 = 0; n9 < vector3dArray3.length; ++n9) {
                            vector3d5 = vector3dArray3[n9].add(0.0, -0.39, 0.0);
                            vector2f4 = ProjectionUtil.project(vector3d5.x + d7 * 0.5, vector3d5.y, vector3d5.z + d8 * 0.5);
                            GlStateManager.pushMatrix();
                            GlStateManager.translatef(vector2f4.x, vector2f4.y, 0.0f);
                            GlStateManager.rotatef((float)(d8 * 360.0 + (double)(n9 * 180) + (double)f10), 0.0f, 0.0f, 1.0f);
                            GlStateManager.translatef(-vector2f4.x, -vector2f4.y, 0.0f);
                            DisplayUtils.drawImage(resourceLocation, vector2f4.x - f2 / 2.0f, vector2f4.y - f2 / 2.0f, f2, f2, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(90, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(180, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(270, 1.0f), (int)f3)));
                            GlStateManager.popMatrix();
                        }
                        for (n9 = 0; n9 < vector3dArray2.length; ++n9) {
                            vector3d5 = vector3dArray2[n9].add(0.0, 1.08, 0.0);
                            vector2f4 = ProjectionUtil.project(vector3d5.x - d7 * 0.5, vector3d5.y, vector3d5.z - d8 * 0.5);
                            GlStateManager.pushMatrix();
                            GlStateManager.translatef(vector2f4.x, vector2f4.y, 0.0f);
                            GlStateManager.rotatef((float)(-d8 * 360.0 + (double)(n9 * 180) + (double)f10), 0.0f, 0.0f, 1.0f);
                            GlStateManager.translatef(-vector2f4.x, -vector2f4.y, 0.0f);
                            DisplayUtils.drawImage(resourceLocation, vector2f4.x - f2 / 2.0f, vector2f4.y - f2 / 2.0f, f2, f2, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(90, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(180, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(270, 1.0f), (int)f3)));
                            GlStateManager.popMatrix();
                        }
                        for (n9 = 0; n9 < vector3dArray.length; ++n9) {
                            vector3d5 = vector3dArray[n9].add(0.0, -2.16, 0.0);
                            vector2f4 = ProjectionUtil.project(vector3d5.x - d7 * 0.5, vector3d5.y, vector3d5.z - d8 * 0.5);
                            GlStateManager.pushMatrix();
                            GlStateManager.translatef(vector2f4.x, vector2f4.y, 0.0f);
                            GlStateManager.rotatef((float)(-d8 * 360.0 + (double)(n9 * 180) + (double)f10), 0.0f, 0.0f, 1.0f);
                            GlStateManager.translatef(-vector2f4.x, -vector2f4.y, 0.0f);
                            DisplayUtils.drawImage(resourceLocation, vector2f4.x - f2 / 2.0f, vector2f4.y - f2 / 2.0f, f2, f2, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(90, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(180, 1.0f), (int)f3), ColorUtils.setAlpha(HUD.getColor(270, 1.0f), (int)f3)));
                            GlStateManager.popMatrix();
                        }
                    }
                }
            }
        }
    }
}

