package wtf.shiyeno.modules.impl.render;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.shader.Framebuffer;
import wtf.shiyeno.events.Event;
import wtf.shiyeno.events.impl.player.EventModelRender;
import wtf.shiyeno.events.impl.render.EventRender;
import wtf.shiyeno.modules.Function;
import wtf.shiyeno.modules.FunctionAnnotation;
import wtf.shiyeno.modules.Type;
import wtf.shiyeno.util.render.BloomHelper;
import wtf.shiyeno.util.render.OutlineUtils;
import wtf.shiyeno.util.render.ShaderUtil;

@FunctionAnnotation(
        name = "GlowESP",
        type = Type.Render
)
public class GlowESP extends Function {
    public static Framebuffer framebuffer = new Framebuffer(1, 1, true, false);

    public GlowESP() {
    }

    public void onEvent(Event event) {
        if (event instanceof EventModelRender e) {
            framebuffer.bindFramebuffer(false);
            e.render();
            framebuffer.unbindFramebuffer();
            mc.getFramebuffer().bindFramebuffer(true);
        }

        if (event instanceof EventRender e) {
            if (e.isRender2D()) {
                GlStateManager.enableBlend();
                OutlineUtils.registerRenderCall(() -> {
                    framebuffer.bindFramebufferTexture();
                    ShaderUtil.drawQuads();
                });
                BloomHelper.registerRenderCallHand(() -> {
                    framebuffer.bindFramebufferTexture();
                    ShaderUtil.drawQuads();
                });
                OutlineUtils.draw(1, -1);
                BloomHelper.drawC(10, 1.0F, true, -1, 2.0F);
                OutlineUtils.setupBuffer(framebuffer);
                mc.getFramebuffer().bindFramebuffer(true);
            }
        }
    }
}