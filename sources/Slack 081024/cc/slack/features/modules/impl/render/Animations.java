// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import cc.slack.features.modules.api.settings.impl.NumberValue;

@ModuleInfo(
        name = "Animations",
        category = Category.RENDER
)
public class Animations extends Module {

    public final ModeValue<String> blockStyle = new ModeValue<>("Block Animation", new String[]{"1.7", "1.8", "Astolfo", "Exhibition", "Lucky", "Slack", "Spin"});

    public final NumberValue<Double> animationSpeedValue = new NumberValue<>("Animation Speed", 1.0D, 0.1D, 3.0D, 0.1D);
    public final NumberValue <Float> spinSpeed = new NumberValue<>("Spin Speed", 0.5F, 0.1F, 2.0F, 0.1F);
    public  final NumberValue<Double> xValue = new NumberValue<>("X", 0.0D, -1.0D, 1.0D, 0.05D);
    public  final NumberValue<Double> yValue = new NumberValue<>("Y", 0.0D, -1.0D, 1.0D, 0.05D);
    public  final NumberValue<Double> zValue = new NumberValue<>("Z", 0.0D, -1.0D, 1.0D, 0.05D);

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});


    public Animations() {
        addSettings(blockStyle, animationSpeedValue, spinSpeed, xValue, yValue, zValue, displayMode);
    }


    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return blockStyle.getValue().toString();
        }
        return null;
    }

}
