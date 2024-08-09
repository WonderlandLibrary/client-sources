package dev.excellent.impl.util.shader.impl;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.impl.util.shader.ShaderLink;
import net.mojang.blaze3d.platform.GlStateManager;
import net.mojang.blaze3d.systems.RenderSystem;

public class ClickGuiShader implements IAccess {

    private final ShaderLink clickgui1 = ShaderLink.create("clickgui1");
    private final ShaderLink clickgui2 = ShaderLink.create("clickgui2");

    public void drawShader1(final float mouseX, final float mouseY, final long initTime, float alphaPC) {
        clickgui1.init();
        clickgui1.setUniformf("red", getTheme().getClientColor().getRed() / 255F);
        clickgui1.setUniformf("green", getTheme().getClientColor().getGreen() / 255F);
        clickgui1.setUniformf("blue", getTheme().getClientColor().getBlue() / 255F);
        clickgui1.setUniformf("alpha", alphaPC);
        clickgui1.setUniformf("iTime", (System.currentTimeMillis() - initTime) / 1000F);
        clickgui1.setUniformf("iMouse", (float) (mouseX / scaled().x), (float) (1F - mouseY / scaled().y));
        clickgui1.setUniformf("iResolution", (float) (scaled().x * mc.getMainWindow().getScaleFactor()), (float) (scaled().y * mc.getMainWindow().getScaleFactor()));
        GlStateManager.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ShaderLink.drawScaledQuads();
        GlStateManager.disableBlend();
        clickgui1.unload();
    }

    public void drawShader2(final float mouseX, final float mouseY, final long initTime, float alphaPC) {
        clickgui2.init();
        clickgui2.setUniformf("red", getTheme().getClientColor().getRed() / 255F);
        clickgui2.setUniformf("green", getTheme().getClientColor().getGreen() / 255F);
        clickgui2.setUniformf("blue", getTheme().getClientColor().getBlue() / 255F);
        clickgui2.setUniformf("alpha", alphaPC);
        clickgui2.setUniformf("iTime", (System.currentTimeMillis() - initTime) / 1000F);
        clickgui2.setUniformf("iMouse", (float) (mouseX / scaled().x), (float) (1F - mouseY / scaled().y));
        clickgui2.setUniformf("iResolution", (float) (scaled().x * mc.getMainWindow().getScaleFactor()), (float) (scaled().y * mc.getMainWindow().getScaleFactor()));
        GlStateManager.enableBlend();
        RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
        ShaderLink.drawScaledQuads();
        GlStateManager.disableBlend();
        clickgui2.unload();
    }

}
