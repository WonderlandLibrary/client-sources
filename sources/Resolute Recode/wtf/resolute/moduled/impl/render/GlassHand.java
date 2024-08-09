package wtf.resolute.moduled.impl.render;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import wtf.resolute.evented.EventDisplay;
import wtf.resolute.moduled.Categories;
import wtf.resolute.moduled.Module;
import wtf.resolute.moduled.ModuleAnontion;
import wtf.resolute.utiled.render.CustomFramebuffer;
import wtf.resolute.utiled.render.ColorUtils;
import wtf.resolute.utiled.render.KawaseBlur;
import wtf.resolute.utiled.shader.impl.Outline;
import net.minecraft.client.settings.PointOfView;
import org.lwjgl.opengl.GL11;

@ModuleAnontion(name = "Glass Hand", type = Categories.Render,server = "")
public class GlassHand extends Module {

    public CustomFramebuffer hands = new CustomFramebuffer(false).setLinear();
    public CustomFramebuffer mask = new CustomFramebuffer(false).setLinear();

    @Subscribe
    public void onRender(EventDisplay e) {
        if (e.getType() != EventDisplay.Type.HIGH) {
            return;
        }

        if (mc.gameSettings.getPointOfView() == PointOfView.FIRST_PERSON) {
            KawaseBlur.blur.updateBlur(3, 4);
            GlStateManager.pushMatrix();
            GlStateManager.enableBlend();
            GlStateManager.enableAlphaTest();
            ColorUtils.setColor(ColorUtils.getColor(0));
            KawaseBlur.blur.render(() -> {
                hands.draw();
            });

            Outline.registerRenderCall(() -> {
                hands.draw();
            });


            GlStateManager.disableAlphaTest();
            GlStateManager.popMatrix();
        }
    }

    public static void setSaturation(float saturation) {
        float[] saturationMatrix = {0.3086f * (1.0f - saturation) + saturation, 0.6094f * (1.0f - saturation), 0.0820f * (1.0f - saturation), 0, 0, 0.3086f * (1.0f - saturation), 0.6094f * (1.0f - saturation) + saturation, 0.0820f * (1.0f - saturation), 0, 0, 0.3086f * (1.0f - saturation), 0.6094f * (1.0f - saturation), 0.0820f * (1.0f - saturation) + saturation, 0, 0, 0, 0, 0, 1, 0};
        GL11.glLoadMatrixf(saturationMatrix);
    }
}
