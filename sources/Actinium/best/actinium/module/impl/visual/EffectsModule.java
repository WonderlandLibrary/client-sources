package best.actinium.module.impl.visual;

import best.actinium.event.impl.render.BloomEvent;
import best.actinium.event.impl.render.BlurEvent;
import best.actinium.module.Module;
import best.actinium.module.ModuleCategory;
import best.actinium.module.api.data.ModuleInfo;
import best.actinium.property.impl.BooleanProperty;
import best.actinium.property.impl.NumberProperty;
import best.actinium.util.shaders.FrameBufferUtil;
import best.actinium.util.shaders.KawaseBloom;
import best.actinium.util.shaders.KawaseBlur;
import best.actinium.Actinium;
import net.minecraft.client.shader.Framebuffer;

@ModuleInfo(
        name = "Effects",
        description = "Adds post processing effects to visual modules.",
        category = ModuleCategory.VISUAL
)
public class EffectsModule extends Module {

    public BooleanProperty blur = new BooleanProperty("Blur",this,false);
    public NumberProperty blurIterations = new NumberProperty("Blur Iterations", this, 2, 1, 8, 1)
            .setHidden(() -> !blur.isEnabled());
    public NumberProperty blurOffset = new NumberProperty("Blur Offset", this, 1, 1, 10, 1)
            .setHidden(() -> !blur.isEnabled());

    public BooleanProperty bloom = new BooleanProperty("Bloom",this,false);
    public NumberProperty bloomIterations = new NumberProperty("Bloom Iterations", this, 2, 1, 8, 1)
            .setHidden(() -> !bloom.isEnabled());
    public NumberProperty bloomOffset = new NumberProperty("Bloom Offset", this, 1, 1, 10, 1)
            .setHidden(() -> !bloom.isEnabled());

    private Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);

    public void blurScreen() {
        if (blur.isEnabled()) {
            stencilFramebuffer = FrameBufferUtil.createFrameBuffer(stencilFramebuffer);

            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);
            Actinium.INSTANCE.getEventManager().publish(new BlurEvent());
            stencilFramebuffer.unbindFramebuffer();

            KawaseBlur.renderBlur(stencilFramebuffer.framebufferTexture, blurIterations.getValue().intValue(), blurOffset.getValue().intValue());
        }

        if (bloom.isEnabled()) {
            stencilFramebuffer = FrameBufferUtil.createFrameBuffer(stencilFramebuffer);
            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);

            Actinium.INSTANCE.getEventManager().publish(new BloomEvent());

            stencilFramebuffer.unbindFramebuffer();

            KawaseBloom.renderBlur(stencilFramebuffer.framebufferTexture, bloomIterations.getValue().intValue(), bloomOffset.getValue().intValue());
        }
    }

}