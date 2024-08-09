package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.events.JumpEvent;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.concurrent.CopyOnWriteArrayList;

@FunctionRegister(name = "JumpCircle", type = Category.Render)
public class JumpCircle extends Function {
    private final SliderSetting timeLife = new SliderSetting("Время жизни", 1f, 1f,5f,0.1f);
    private final SliderSetting radius = new SliderSetting("Радиус", 1f, 1f,5f,0.1f);
    private final CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList<>();

    public JumpCircle() {
        addSettings(timeLife, radius);
    }
    @Subscribe
    private void onJump(JumpEvent e) {
        circles.add(new Circle(mc.player.getPositon(mc.getRenderPartialTicks()).add(0,0.05, 0)));
    }

    private final ResourceLocation circle = new ResourceLocation("expensive/images/circle.png");

    @Subscribe
    private void onRender(WorldEvent e) {

        GlStateManager.pushMatrix();
        GlStateManager.shadeModel(7425);
        GlStateManager.blendFunc(770,771);
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.disableAlphaTest();
        GlStateManager.disableCull();
        GlStateManager.translated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y,-mc.getRenderManager().info.getProjectedView().z);

        // render
        {



            for (Circle c : circles) {
                mc.getTextureManager().bindTexture(circle);
                if (System.currentTimeMillis() - c.time > timeLife.get() * 2000) circles.remove(c);
                if (System.currentTimeMillis() - c.time > timeLife.get() * 1500 && !c.isBack) {
                    c.animation.animate(radius.get() * 0, radius.get() * 0.1f, Easings.BACK_IN);
                    c.isBack = true;
                }

                c.animation.update();
                float rad = (float) c.animation.getValue();

                Vector3d vector3d = c.vector3d;

                vector3d = vector3d.add(-rad / 2f, 0 ,-rad / 2f);

                buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
                int alpha = (int) (255 * MathHelper.clamp(rad, 0, 1));
                buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(5), alpha)).tex(0,0).endVertex();
                buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(10), alpha)).tex(1,0).endVertex();
                buffer.pos(vector3d.x + rad, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(15), alpha)).tex(1,1).endVertex();
                buffer.pos(vector3d.x, vector3d.y, vector3d.z + rad).color(ColorUtils.setAlpha(ColorUtils.getColor(20), alpha)).tex(0,1).endVertex();
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
            animation.animate(radius.get(), radius.get() * 0.5f, Easings.BACK_OUT);
        }

    }

}
