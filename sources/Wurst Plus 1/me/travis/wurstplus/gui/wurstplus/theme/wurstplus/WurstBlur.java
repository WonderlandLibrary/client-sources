package me.travis.wurstplus.gui.wurstplus.theme.wurstplus;

import me.travis.wurstplus.gui.wurstplus.*;
import me.travis.wurstplus.gui.rgui.component.container.use.Frame;
import me.travis.wurstplus.gui.rgui.render.AbstractComponentUI;
import me.travis.wurstplus.gui.rgui.render.font.FontRenderer;

public class WurstBlur<T extends Frame> extends AbstractComponentUI<Frame> {

    @Override
    public void renderComponent(Frame component, FontRenderer fontRenderer) {
        RenderHelper.blurScreen();
    }

}
