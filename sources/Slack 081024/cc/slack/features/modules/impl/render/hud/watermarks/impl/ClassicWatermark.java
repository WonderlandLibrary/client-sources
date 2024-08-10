package cc.slack.features.modules.impl.render.hud.watermarks.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.impl.render.Interface;
import cc.slack.features.modules.impl.render.hud.watermarks.IWatermarks;
import cc.slack.start.Slack;
import cc.slack.utils.font.Fonts;
import cc.slack.utils.render.ColorUtil;

public class ClassicWatermark implements IWatermarks {
    @Override
    public void onRender(RenderEvent event) {
        renderClassic(ColorUtil.getColor(Slack.getInstance().getModuleManager().getInstance(Interface.class).theme.getValue(), 0.15).getRGB());
    }

    @Override
    public void onUpdate(UpdateEvent event) {

    }

    private void renderClassic(int themeColor) {
        switch (Slack.getInstance().getModuleManager().getInstance(Interface.class).watermarkFont.getValue()) {
            case "Apple":
                Fonts.apple24.drawStringWithShadow("S", 3.4, 4, themeColor);
                Fonts.apple24.drawStringWithShadow("lack", 11, 4, -1);
                break;
            case "Poppins":
                Fonts.poppins24.drawStringWithShadow("S", 3.8, 3.8, themeColor);
                Fonts.poppins24.drawStringWithShadow("lack", 11, 4, -1);
                break;
            case "Roboto":
                Fonts.roboto24.drawStringWithShadow("S", 3.8, 3.8, themeColor);
                Fonts.roboto24.drawStringWithShadow("lack", 11, 4, -1);
                break;
        }
    }

    @Override
    public String toString() {
        return "Classic";
    }
}
