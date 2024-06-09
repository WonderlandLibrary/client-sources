package me.travis.wurstplus.gui.wurstplus.theme.wurstplus;

import java.awt.Font;
import me.travis.wurstplus.gui.wurstplus.wurstplusGUI;
import me.travis.wurstplus.gui.wurstplus.theme.staticui.RadarUI;
import me.travis.wurstplus.gui.wurstplus.theme.staticui.TabGuiUI;
import me.travis.wurstplus.gui.font.CFontRenderer;
import me.travis.wurstplus.gui.rgui.component.container.use.Frame;
import me.travis.wurstplus.gui.rgui.component.use.Button;
import me.travis.wurstplus.gui.rgui.render.AbstractComponentUI;
import me.travis.wurstplus.gui.rgui.render.font.FontRenderer;
import me.travis.wurstplus.gui.rgui.render.theme.AbstractTheme;
import me.travis.wurstplus.gui.wurstplus.theme.wurstplus.WurstBlur;

/**
 * Created by 086 on 26/06/2017.
 */
public class wurstplusTheme extends AbstractTheme {

    FontRenderer fontRenderer;
    CFontRenderer CFontRenderer;

    public wurstplusTheme() {
        installUI(new WurstBlur());
        installUI(new RootButtonUI<Button>());
        installUI(new GUIUI());
        installUI(new RootGroupboxUI());
        installUI(new wurstplusFrameUI<Frame>());
        installUI(new RootScrollpaneUI());
        installUI(new RootInputFieldUI());
        installUI(new RootLabelUI());
        installUI(new RootChatUI());
        installUI(new RootCheckButtonUI());
        installUI(new wurstplusActiveModulesUI());
        installUI(new wurstplusSettingsPanelUI());
        installUI(new RootSliderUI());
        installUI(new wurstplusEnumbuttonUI());
        installUI(new RootColorizedCheckButtonUI());
        installUI(new wurstplusUnboundSliderUI());

        installUI(new RadarUI());
        installUI(new TabGuiUI());

        fontRenderer=wurstplusGUI.fontRenderer;
    }

    @Override
    public FontRenderer getFontRenderer() {
        return fontRenderer;
    }

    public class GUIUI extends AbstractComponentUI<wurstplusGUI> {
    }
}
