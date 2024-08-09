package src.Wiksi.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import src.Wiksi.events.JumpEvent;
import src.Wiksi.events.WorldEvent;
import src.Wiksi.functions.api.Category;
import src.Wiksi.functions.api.Function;
import src.Wiksi.functions.api.FunctionRegister;
import src.Wiksi.functions.settings.impl.ModeSetting;
import src.Wiksi.functions.settings.impl.SliderSetting;
import src.Wiksi.utils.render.ColorUtils;
import src.Wiksi.utils.render.DisplayUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.concurrent.CopyOnWriteArrayList;

@FunctionRegister(name = "JumpCircle", type = Category.Render)
public class JumpCircle extends Function {

    private final CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList<>();
    private final ModeSetting type = new ModeSetting("Тип", "Мод1", "Мод1", "Мод2","star");
    public SliderSetting radius = new SliderSetting("Радиус", 1, 0.01f, 3, 0.01f);
    public SliderSetting satur = new SliderSetting("Прозрачность", 1, 0.5f, 3, 0.01f);
 //   public SliderSetting life = new SliderSetting("Время жизни", 2000.0f, 500.0f, 10000.0f, 500.0f);

    {
        addSettings(type, radius, satur);
    }

    @Subscribe
    private void onJump(JumpEvent e) {
        circles.add(new Circle(mc.player.getPositon(mc.getRenderPartialTicks()).add(0, 0.05, 0)));
    }

    @Subscribe
    private void onRender(WorldEvent e) {
        if (type.is("Мод1")) {
            final ResourceLocation circle = new ResourceLocation("Wiksi/images/circle.png");

            GlStateManager.pushMatrix();
            GlStateManager.shadeModel(7425);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.disableAlphaTest();
            GlStateManager.disableCull();
            GlStateManager.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
            // render
            for (Circle c : circles) {
                mc.getTextureManager().bindTexture(circle);
                if (System.currentTimeMillis() - c.time > 1500) circles.remove(c);
                if (System.currentTimeMillis() - c.time > 1000 && !c.isBack) {
                    c.animation.animate(0, 0.5, Easings.BACK_IN);
                    c.isBack = true;
                }
                c.animation.update();
                float rad = (float) c.animation.getValue() + radius.get();
                Vector3d vector3d = c.vector3d;
                vector3d = vector3d.add(-rad / 2f, 0, -rad / 2f);
                buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);


                int alpha = (int) (255 * satur.get());
                buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), alpha)).tex(0, 0).endVertex();
                buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), alpha)).tex(1, 0).endVertex();
                buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(15), alpha)).tex(1, 1).endVertex();
                buffer.pos(vector3d.x, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(20), alpha)).tex(0, 1).endVertex();
                tessellator.draw();
            }
        }
            if (type.is("star")) {
                final ResourceLocation circle = new ResourceLocation("Wiksi/images/starcircle.png");

                GlStateManager.pushMatrix();
                GlStateManager.shadeModel(7425);
                GlStateManager.blendFunc(770, 771);
                GlStateManager.depthMask(false);
                GlStateManager.enableBlend();
                GlStateManager.disableAlphaTest();
                GlStateManager.disableCull();
                GlStateManager.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
                // render
                for (Circle c : circles) {
                    mc.getTextureManager().bindTexture(circle);
                    if (System.currentTimeMillis() - c.time > 1500) circles.remove(c);
                    if (System.currentTimeMillis() - c.time > 1000 && !c.isBack) {
                        c.animation.animate(0, 0.5, Easings.BACK_IN);
                        c.isBack = true;
                    }
                    c.animation.update();
                    float rad = (float) c.animation.getValue() + radius.get();
                    Vector3d vector3d = c.vector3d;
                    vector3d = vector3d.add(-rad / 2f, 0, -rad / 2f);
                    buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);


                    int alpha =(int) (255 * satur.get());
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), alpha)).tex(0, 0).endVertex();
                    buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), alpha)).tex(1, 0).endVertex();
                    buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(15), alpha)).tex(1, 1).endVertex();
                    buffer.pos(vector3d.x, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(20), alpha)).tex(0, 1).endVertex();
                    tessellator.draw();
                }

        }else if (type.is("Мод2")) {
            final ResourceLocation circle = new ResourceLocation("Wiksi/images/circle2.png");
            GlStateManager.pushMatrix();
            GlStateManager.shadeModel(7425);
            GlStateManager.blendFunc(770, 771);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.disableAlphaTest();
            GlStateManager.disableCull();
            GlStateManager.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
            // render
            for (Circle c : circles) {
                mc.getTextureManager().bindTexture(circle);
                if (System.currentTimeMillis() - c.time > 1500) circles.remove(c);
                if (System.currentTimeMillis() - c.time > 1000 && !c.isBack) {
                    c.animation.animate(0, 0.5, Easings.BACK_IN);
                    c.isBack = true;
                }
                c.animation.update();
                float rad = (float) c.animation.getValue() + radius.get();
                Vector3d vector3d = c.vector3d;
                vector3d = vector3d.add(-rad / 2f, 0, -rad / 2f);
                buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);


                int alpha =(int) (255 * satur.get());


                buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), alpha)).tex(0, 0).endVertex();
                buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), alpha)).tex(1, 0).endVertex();
                buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(15), alpha)).tex(1, 1).endVertex();
                buffer.pos(vector3d.x, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(20), alpha)).tex(0, 1).endVertex();
                tessellator.draw();
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
        private final Animation animation = new Animation();
        private boolean isBack;

        public Circle(Vector3d vector3d) {
            this.vector3d = vector3d;
            time = System.currentTimeMillis();
            animation.animate(1.6, 0.5, Easings.BACK_OUT);
        }
    }
}
