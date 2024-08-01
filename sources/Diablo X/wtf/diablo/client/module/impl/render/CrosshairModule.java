package wtf.diablo.client.module.impl.render;

import best.azura.eventbus.handler.EventHandler;
import best.azura.eventbus.handler.Listener;
import wtf.diablo.client.event.impl.client.renderering.OverlayEvent;
import wtf.diablo.client.module.api.data.AbstractModule;
import wtf.diablo.client.module.api.data.ModuleCategoryEnum;
import wtf.diablo.client.module.api.data.ModuleMetaData;
import wtf.diablo.client.setting.impl.ColorSetting;
import wtf.diablo.client.setting.impl.NumberSetting;

import java.awt.*;

@ModuleMetaData(name = "Crosshair", description = "Renders a crosshair in the middle of your screen.", category = ModuleCategoryEnum.RENDER)
public final class CrosshairModule extends AbstractModule {
    //private final BooleanSetting dynamic = new BooleanSetting("Dynamic", true); TODO: Add dynamic crosshair
    private final NumberSetting<Integer> gap = new NumberSetting<>("Gap", 2, 0, 10,1);
    private final NumberSetting<Integer> width = new NumberSetting<>("Width", 1, 0, 10,1);
    private final NumberSetting<Integer> height = new NumberSetting<>("Height", 1, 0, 10,1);
    private final NumberSetting<Integer> outlineThickness = new NumberSetting<>("Outline Thickness", 1, 0, 10,1);
    private final ColorSetting color = new ColorSetting("Color", Color.WHITE);
    private final ColorSetting outlineColor = new ColorSetting("Outline Color", Color.BLACK);

    public CrosshairModule() {
        this.registerSettings(gap, width, height, outlineThickness, color, outlineColor);
    }

    @EventHandler
    private final Listener<OverlayEvent> overlayEventListener = event -> {
        if (mc.gameSettings.thirdPersonView == 0) {
            int x = event.getScaledResolution().getScaledWidth() / 2;
            int y = event.getScaledResolution().getScaledHeight() / 2;
            int gap = this.gap.getValue();
            int width = this.width.getValue();
            int height = this.height.getValue();
            int outlineThickness = this.outlineThickness.getValue();
            Color color = this.color.getValue();
            Color outlineColor = this.outlineColor.getValue();

        }
    };

}
