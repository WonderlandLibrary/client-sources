/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import java.util.concurrent.CopyOnWriteArrayList;
import mpp.venusfr.events.JumpEvent;
import mpp.venusfr.events.WorldEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

@FunctionRegister(name="JumpCircle", type=Category.Visual)
public class JumpCircle
extends Function {
    private final ModeSetting mode = new ModeSetting("\u0412\u0438\u0434", "Circle 1", "Circle 1", "Circle 2", "Circle 3", "Circle 4", "Circle 5");
    private final SliderSetting value = new SliderSetting("\u0420\u0430\u0437\u043c\u0435\u0440 \u043a\u0440\u0443\u0433\u0430", 1.0f, 1.0f, 4.0f, 0.1f);
    private final CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList();

    @Subscribe
    private void onJump(JumpEvent jumpEvent) {
        this.circles.add(new Circle(this, JumpCircle.mc.player.getPositon(mc.getRenderPartialTicks()).add(0.0, 0.05, 0.0)));
    }

    public JumpCircle() {
        this.addSettings(this.mode, this.value);
    }

    @Subscribe
    private void onRender(WorldEvent worldEvent) {
        GlStateManager.pushMatrix();
        GlStateManager.shadeModel(7425);
        GlStateManager.blendFunc(770, 771);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableCull();
        GlStateManager.translated(-JumpCircle.mc.getRenderManager().info.getProjectedView().x, -JumpCircle.mc.getRenderManager().info.getProjectedView().y, -JumpCircle.mc.getRenderManager().info.getProjectedView().z);
        ResourceLocation resourceLocation = new ResourceLocation("venusfr/images/circle1.png");
        switch (this.mode.getIndex()) {
            case 0: {
                for (Circle circle : this.circles) {
                    mc.getTextureManager().bindTexture(resourceLocation);
                    if (System.currentTimeMillis() - circle.time > 2000L) {
                        this.circles.remove(circle);
                    }
                    if (System.currentTimeMillis() - circle.time > 1500L && !circle.isBack) {
                        circle.animation.animate(0.0, 0.5, Easings.BACK_IN);
                        circle.isBack = true;
                    }
                    circle.animation.update();
                    float f = (float)circle.animation.getValue() * ((Float)this.value.get()).floatValue();
                    Vector3d vector3d = circle.vector3d;
                    vector3d = vector3d.add(-f / 2.0f, 0.0, -f / 2.0f);
                    buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
                    int n = (int)(255.0f * MathHelper.clamp(f, 0.0f, 1.0f));
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), n)).tex(0.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), n)).tex(1.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(15), n)).tex(1.0f, 1.0f).endVertex();
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(20), n)).tex(0.0f, 1.0f).endVertex();
                    tessellator.draw();
                }
                break;
            }
            case 1: {
                ResourceLocation resourceLocation2 = new ResourceLocation("venusfr/images/circle2.png");
                for (Circle circle : this.circles) {
                    mc.getTextureManager().bindTexture(resourceLocation2);
                    if (System.currentTimeMillis() - circle.time > 2000L) {
                        this.circles.remove(circle);
                    }
                    if (System.currentTimeMillis() - circle.time > 1500L && !circle.isBack) {
                        circle.animation.animate(0.0, 0.5, Easings.BACK_IN);
                        circle.isBack = true;
                    }
                    circle.animation.update();
                    float f = (float)circle.animation.getValue() * ((Float)this.value.get()).floatValue();
                    Vector3d vector3d = circle.vector3d;
                    vector3d = vector3d.add(-f / 2.0f, 0.0, -f / 2.0f);
                    buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
                    int n = (int)(255.0f * MathHelper.clamp(f, 0.0f, 1.0f));
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), n)).tex(0.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), n)).tex(1.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(15), n)).tex(1.0f, 1.0f).endVertex();
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(20), n)).tex(0.0f, 1.0f).endVertex();
                    tessellator.draw();
                }
                break;
            }
            case 2: {
                ResourceLocation resourceLocation3 = new ResourceLocation("venusfr/images/circle3.png");
                for (Circle circle : this.circles) {
                    mc.getTextureManager().bindTexture(resourceLocation3);
                    if (System.currentTimeMillis() - circle.time > 2000L) {
                        this.circles.remove(circle);
                    }
                    if (System.currentTimeMillis() - circle.time > 1500L && !circle.isBack) {
                        circle.animation.animate(0.0, 0.5, Easings.BACK_IN);
                        circle.isBack = true;
                    }
                    circle.animation.update();
                    float f = (float)circle.animation.getValue() * ((Float)this.value.get()).floatValue();
                    Vector3d vector3d = circle.vector3d;
                    vector3d = vector3d.add(-f / 2.0f, 0.0, -f / 2.0f);
                    buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
                    int n = (int)(255.0f * MathHelper.clamp(f, 0.0f, 1.0f));
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), n)).tex(0.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), n)).tex(1.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(15), n)).tex(1.0f, 1.0f).endVertex();
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(20), n)).tex(0.0f, 1.0f).endVertex();
                    tessellator.draw();
                }
                break;
            }
            case 3: {
                ResourceLocation resourceLocation4 = new ResourceLocation("venusfr/images/circle4.png");
                for (Circle circle : this.circles) {
                    mc.getTextureManager().bindTexture(resourceLocation4);
                    if (System.currentTimeMillis() - circle.time > 2000L) {
                        this.circles.remove(circle);
                    }
                    if (System.currentTimeMillis() - circle.time > 1500L && !circle.isBack) {
                        circle.animation.animate(0.0, 0.5, Easings.BACK_IN);
                        circle.isBack = true;
                    }
                    circle.animation.update();
                    float f = (float)circle.animation.getValue() * ((Float)this.value.get()).floatValue();
                    Vector3d vector3d = circle.vector3d;
                    vector3d = vector3d.add(-f / 2.0f, 0.0, -f / 2.0f);
                    buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
                    int n = (int)(255.0f * MathHelper.clamp(f, 0.0f, 1.0f));
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), n)).tex(0.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), n)).tex(1.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(15), n)).tex(1.0f, 1.0f).endVertex();
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(20), n)).tex(0.0f, 1.0f).endVertex();
                    tessellator.draw();
                }
                break;
            }
            case 4: {
                ResourceLocation resourceLocation5 = new ResourceLocation("venusfr/images/circle5.png");
                for (Circle circle : this.circles) {
                    mc.getTextureManager().bindTexture(resourceLocation5);
                    if (System.currentTimeMillis() - circle.time > 2000L) {
                        this.circles.remove(circle);
                    }
                    if (System.currentTimeMillis() - circle.time > 1500L && !circle.isBack) {
                        circle.animation.animate(0.0, 0.5, Easings.BACK_IN);
                        circle.isBack = true;
                    }
                    circle.animation.update();
                    float f = (float)circle.animation.getValue() * ((Float)this.value.get()).floatValue();
                    Vector3d vector3d = circle.vector3d;
                    vector3d = vector3d.add(-f / 2.0f, 0.0, -f / 2.0f);
                    buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
                    int n = (int)(255.0f * MathHelper.clamp(f, 0.0f, 1.0f));
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), n)).tex(0.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), n)).tex(1.0f, 0.0f).endVertex();
                    buffer.pos(vector3d.x + (double)f, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(15), n)).tex(1.0f, 1.0f).endVertex();
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z + (double)f).color(ColorUtils.setAlpha(ColorUtils.getColor(20), n)).tex(0.0f, 1.0f).endVertex();
                    tessellator.draw();
                }
                break;
            }
        }
        GlStateManager.disableBlend();
        GlStateManager.shadeModel(7424);
        GlStateManager.depthMask(true);
        GlStateManager.enableAlphaTest();
        GlStateManager.enableCull();
        GlStateManager.popMatrix();
    }

    private class Circle {
        private final Vector3d vector3d;
        private final long time;
        private final Animation animation;
        private boolean isBack;
        final JumpCircle this$0;

        public Circle(JumpCircle jumpCircle, Vector3d vector3d) {
            this.this$0 = jumpCircle;
            this.animation = new Animation();
            this.vector3d = vector3d;
            this.time = System.currentTimeMillis();
            this.animation.animate(1.0, 0.5, Easings.BACK_OUT);
        }
    }
}

