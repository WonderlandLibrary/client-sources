package me.travis.wurstplus.gui.rgui.render.theme;

import me.travis.wurstplus.gui.rgui.component.Component;
import me.travis.wurstplus.gui.rgui.render.ComponentUI;
import me.travis.wurstplus.gui.rgui.render.font.FontRenderer;

/**
 * Created by 086 on 25/06/2017.
 */
public interface Theme {
    public ComponentUI getUIForComponent(Component component);
    public FontRenderer getFontRenderer();
}
