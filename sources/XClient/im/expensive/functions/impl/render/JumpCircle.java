//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package im.expensive.functions.impl.render;

import com.google.common.eventbus.Subscribe;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import org.lwjgl.opengl.GL11;
import ru.hogoshi.Animation;
import ru.hogoshi.util.Easings;
import im.expensive.events.JumpEvent;
import im.expensive.events.WorldEvent;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.settings.Setting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.render.ColorUtils;

@FunctionRegister(
        name = "JumpCircle",
        type = Category.RENDER
)
public class JumpCircle extends Function {
    private final CopyOnWriteArrayList<Circle> circles = new CopyOnWriteArrayList();
    private ResourceLocation circle;
    public SliderSetting jumpCircleSize = new SliderSetting("Speed", 1.0F, 1.0F, 4.0F, 0.5F);

    public JumpCircle() {
        this.addSettings(new Setting[]{new ModeSetting("Режим рендера", "Alpha", new String[]{"Alpha"}), this.jumpCircleSize});
    }

    @Subscribe
    private void onJump(JumpEvent e) {
        this.circles.add(new Circle(mc.player.getPositionVec().add(0.0, 0.05, 0.0)));
    }

    @Subscribe
    private void onRender(WorldEvent e) {
        BufferBuilder buffer = Tessellator.getInstance().getBuffer();
        Tessellator tessellator = Tessellator.getInstance();
        GL11.glPushMatrix();
        GL11.glShadeModel(7425);
        GL11.glBlendFunc(770, 771);
        GL11.glDepthMask(false);
        GL11.glEnable(3042);
        GL11.glDisable(3008);
        GL11.glDisable(2884);
        GL11.glTranslated(-mc.getRenderManager().info.getProjectedView().x, -mc.getRenderManager().info.getProjectedView().y, -mc.getRenderManager().info.getProjectedView().z);
        ModeSetting modeSetting = (ModeSetting)this.getSettings().get(0);
        String mode = (String)modeSetting.get();
        float jumpCircleSize = (Float)this.jumpCircleSize.get();
        switch (mode) {
            case "Alpha" -> this.circle = new ResourceLocation("expensive/images/circle.png");
        }

        Iterator var7 = this.circles.iterator();

        while(var7.hasNext()) {
            Circle c = (Circle)var7.next();
            mc.getTextureManager().bindTexture(this.circle);
            if (System.currentTimeMillis() - c.time > 10000L) {
                this.circles.remove(c);
            }

            if (!c.isBack) {
                c.animation.animate((double)jumpCircleSize, 4.6, Easings.EXPO_OUT);
                c.isBack = true;
            }

            c.animation.update();
            float rad = (float)c.animation.getValue();
            Vector3d vector3d = c.vector3d;
            vector3d = vector3d.add((double)(-rad / 2.0F), 0.0, (double)(-rad / 2.0F));
            buffer.begin(6, DefaultVertexFormats.POSITION_COLOR_TEX);
            int alpha = (int)(255.0F * MathHelper.clamp(1.0F - rad, 0.0F, 1.0F) * (1.0F - (float)(System.currentTimeMillis() - c.time) / 9000.0F));
            buffer.pos(vector3d.x, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(30), alpha)).tex(0.0F, 0.0F).endVertex();
            buffer.pos(vector3d.x + (double)rad, vector3d.y, vector3d.z).color(ColorUtils.setAlpha(ColorUtils.getColor(50), alpha)).tex(1.0F, 0.0F).endVertex();
            buffer.pos(vector3d.x + (double)rad, vector3d.y, vector3d.z + (double)rad).color(ColorUtils.setAlpha(ColorUtils.getColor(70), alpha)).tex(1.0F, 1.0F).endVertex();
            buffer.pos(vector3d.x, vector3d.y, vector3d.z + (double)rad).color(ColorUtils.setAlpha(ColorUtils.getColor(100), alpha)).tex(0.0F, 1.0F).endVertex();
            tessellator.draw();
        }

        GL11.glDisable(3042);
        GL11.glShadeModel(7424);
        GL11.glDepthMask(true);
        GL11.glEnable(3008);
        GL11.glEnable(2884);
        GL11.glPopMatrix();
    }

    private static class Circle {
        private final Vector3d vector3d;
        private final long time;
        private final Animation animation = new Animation();
        private boolean isBack;

        public Circle(Vector3d vector3d) {
            this.vector3d = vector3d;
            this.time = System.currentTimeMillis();
            this.animation.animate(1.0, 0.5, Easings.BACK_OUT);
        }
    }
}
