/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render;

import mpp.venusfr.utils.CustomFramebuffer;
import mpp.venusfr.utils.render.Stencil;
import mpp.venusfr.utils.shader.ShaderUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.shader.Framebuffer;

public class KawaseBlur {
    public static KawaseBlur blur = new KawaseBlur();
    public final CustomFramebuffer BLURRED;
    public final CustomFramebuffer ADDITIONAL;
    CustomFramebuffer blurTarget = new CustomFramebuffer(false).setLinear();

    public KawaseBlur() {
        this.BLURRED = new CustomFramebuffer(false).setLinear();
        this.ADDITIONAL = new CustomFramebuffer(false).setLinear();
    }

    public void render(Runnable runnable) {
        Stencil.initStencilToWrite();
        runnable.run();
        Stencil.readStencilBuffer(1);
        this.BLURRED.draw();
        Stencil.uninitStencilBuffer();
    }

    public void updateBlur(float f, int n) {
        int n2;
        int n3;
        Minecraft minecraft = Minecraft.getInstance();
        Framebuffer framebuffer = minecraft.getFramebuffer();
        this.ADDITIONAL.setup();
        framebuffer.bindFramebufferTexture();
        ShaderUtil.kawaseDown.attach();
        ShaderUtil.kawaseDown.setUniform("offset", f);
        ShaderUtil.kawaseDown.setUniformf("resolution", 1.0f / (float)minecraft.getMainWindow().getWidth(), 1.0f / (float)minecraft.getMainWindow().getHeight());
        CustomFramebuffer.drawTexture();
        CustomFramebuffer[] customFramebufferArray = new CustomFramebuffer[]{this.ADDITIONAL, this.BLURRED};
        for (n3 = 1; n3 < n; ++n3) {
            n2 = n3 % 2;
            customFramebufferArray[n2].setup();
            customFramebufferArray[(n2 + 1) % 2].draw();
        }
        ShaderUtil.kawaseUp.attach();
        ShaderUtil.kawaseUp.setUniform("offset", f);
        ShaderUtil.kawaseUp.setUniformf("resolution", 1.0f / (float)minecraft.getMainWindow().getWidth(), 1.0f / (float)minecraft.getMainWindow().getHeight());
        for (n3 = 0; n3 < n; ++n3) {
            n2 = n3 % 2;
            customFramebufferArray[(n2 + 1) % 2].setup();
            customFramebufferArray[n2].draw();
        }
        ShaderUtil.kawaseUp.detach();
        framebuffer.bindFramebuffer(true);
    }
}

