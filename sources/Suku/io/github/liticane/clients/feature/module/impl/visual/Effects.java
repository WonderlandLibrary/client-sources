package io.github.liticane.clients.feature.module.impl.visual;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.feature.event.impl.render.ShaderEvent;
import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.impl.BooleanProperty;
import io.github.liticane.clients.feature.property.impl.NumberProperty;
import io.github.liticane.clients.feature.property.impl.StringProperty;
import io.github.liticane.clients.util.render.shaders.impl.KawaseBloom;
import io.github.liticane.clients.util.render.shaders.impl.KawaseBlur;
import io.github.liticane.clients.util.render.shaders.FrameBufferUtil;
import net.minecraft.client.shader.Framebuffer;

@Module.Info(name = "Effects", category = Module.Category.VISUAL)
public class Effects extends Module{
    //todo finish this
    public BooleanProperty blur = new BooleanProperty("Blur",this,false);
    public NumberProperty iteration = new NumberProperty("Blur Iteration", this, 2, 1, 8, 1);
    public NumberProperty offset = new NumberProperty("Blur offset", this, 1, 0, 10, 1);
    public BooleanProperty bloom = new BooleanProperty("Bloom",this,false);
    public NumberProperty shadowiteration = new NumberProperty("Bloom Iteration", this, 2, 1, 8, 1);
    public NumberProperty shadowoffset = new NumberProperty("Bloom offset", this, 1, 0, 10, 1);
    public BooleanProperty watermark = new BooleanProperty("Watermark",this,false);
    public BooleanProperty arraylist = new BooleanProperty("Arraylist",this,false);
    public StringProperty arraylistcolor = new StringProperty("Arraylist Shadow Color",this,"Black","Black","Client");

    private Framebuffer stencilFramebuffer = new Framebuffer(1, 1, false);

    public void blurScreen() {
        setSuffix("Shadow & blur");
        if (!isToggled()) return;
        if (blur.isToggled()) {
            stencilFramebuffer = FrameBufferUtil.createFrameBuffer(stencilFramebuffer);

            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);
            Client.INSTANCE.getEventManager().handle(new ShaderEvent(false));
            //stuffToBlur(false);
            stencilFramebuffer.unbindFramebuffer();

            KawaseBlur.renderBlur(stencilFramebuffer.framebufferTexture, (int)iteration.getValue(), (int)offset.getValue());

        }


        if (bloom.isToggled()) {
            stencilFramebuffer = FrameBufferUtil.createFrameBuffer(stencilFramebuffer);
            stencilFramebuffer.framebufferClear();
            stencilFramebuffer.bindFramebuffer(false);

            Client.INSTANCE.getEventManager().handle(new ShaderEvent(true));
            //stuffToBlur(true);

            stencilFramebuffer.unbindFramebuffer();

            KawaseBloom.renderBlur(stencilFramebuffer.framebufferTexture, (int)shadowiteration.getValue(), (int)shadowoffset.getValue());

        }
    }
}
