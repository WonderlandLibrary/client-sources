package dev.excellent.impl.util.shader.impl;

import dev.excellent.api.interfaces.client.IAccess;
import dev.excellent.api.interfaces.shader.IShader;
import dev.excellent.impl.util.render.color.ColorUtil;
import dev.excellent.impl.util.shader.ShaderLink;

public class MainMenuShader implements IAccess {

    private final ShaderLink mainmenu = ShaderLink.create("mainmenu");

    public void draw(final float mouseX, final float mouseY, final long initTime) {
        IShader.ROUNDED.draw((float) 0, (float) 0, (float) scaled().x, (float) scaled().y, 0, ColorUtil.getColor(20, 20, 20, 255));
        mainmenu.init();
        mainmenu.setUniformf("red", (getTheme().getClientColor().getRed() / 255F) * 0.5F);
        mainmenu.setUniformf("green", (getTheme().getClientColor().getGreen() / 255F) * 0.5F);
        mainmenu.setUniformf("blue", (getTheme().getClientColor().getBlue() / 255F) * 0.5F);
        mainmenu.setUniformf("iTime", (System.currentTimeMillis() - initTime) / 1000F);
        mainmenu.setUniformf("iMouse", (float) (mouseX / scaled().x), (float) (1F - mouseY / scaled().y));
        mainmenu.setUniformf("iResolution", (float) (scaled().x * mc.getMainWindow().getScaleFactor()), (float) (scaled().y * mc.getMainWindow().getScaleFactor()));
        ShaderLink.drawScaledQuads();
        mainmenu.unload();
    }

}
