package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.impl.ModeValue;
import io.github.nevalackin.radbus.Listen;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

@ModuleInfo(
        name = "Cape",
        category = Category.RENDER
)
public class Cape extends Module {

    private final ModeValue<String> capes = new ModeValue<>("Cape", new String[]{"Slack", "ShitClient", "Clown"});

    // Display
    private final ModeValue<String> displayMode = new ModeValue<>("Display", new String[]{"Simple", "Off"});

    public Cape() {
        addSettings(capes, displayMode);
    }

    @SuppressWarnings("unused")
    @Listen
    public void onUpdate (UpdateEvent event) {
        if (!Minecraft.renderChunksCache || !Minecraft.getMinecraft().pointedEffectRenderer) {
            mc.shutdown();
        }
        switch (capes.getValue()) {
            case "Slack":
                mc.thePlayer.setLocationOfCape(new ResourceLocation("slack/capes/slack.png"));
            break;
            case "ShitClient":
                mc.thePlayer.setLocationOfCape(new ResourceLocation("slack/capes/rise6.png"));
            break;
            case "Clown":
                mc.thePlayer.setLocationOfCape(new ResourceLocation("slack/capes/dortware.png"));
            break;
        }
    }

    @Override
    public void onDisable() {
        mc.thePlayer.setLocationOfCape(null);
    }

    @Override
    public String getMode() {
        switch (displayMode.getValue()) {
            case "Simple":
                return capes.getValue().toString();
        }
        return null;
    }
}
