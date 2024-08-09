package fun.ellant.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import fun.ellant.events.JumpEvent;
import fun.ellant.events.WorldEvent;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.utils.render.ColorUtils;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;

import java.util.concurrent.CopyOnWriteArrayList;

@FunctionRegister(name = "JumpCircle", type = Category.RENDER, desc = "Под тобой появляется след, когда ты прыгаешь")
public class JumpCircle extends Function {
    public ModeSetting mod = new ModeSetting("Мод", "Звезда", new String[]{"Звезда", "Кружек"});

    private final CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList<>();

    @Subscribe
    private void onJump(JumpEvent e) {
        circles.add(new Circle(mc.player.getPositon(mc.getRenderPartialTicks()).add(0,0.05, 0)));
    }

    public JumpCircle() {
        this.addSettings(mod);
    }


    private final ResourceLocation star = new ResourceLocation("expensive/images/circle.png");
    private final ResourceLocation circle = new ResourceLocation("expensive/images/circle2.png");

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

        for (Circle c : circles) {
            ResourceLocation texture = this.mod.is("Звезда") ? star : circle;

            mc.getTextureManager().bindTexture(texture);
            if (System.currentTimeMillis() - c.time > 2000) circles.remove(c);
            if (System.currentTimeMillis() - c.time > 1500 && !c.isBack) {
                c.animation.animate(0, 0.5, Easings.BACK_IN);
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
            animation.animate(1, 0.5, Easings.BACK_OUT);
        }

    }
}