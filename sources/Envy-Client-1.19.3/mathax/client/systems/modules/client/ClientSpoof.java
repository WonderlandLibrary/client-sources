package mathax.client.systems.modules.client;

import mathax.client.settings.*;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.render.color.SettingColor;
import net.minecraft.item.Items;

public class ClientSpoof extends Module {
    private final SettingGroup sgVersion = settings.createGroup("Version");
    private final SettingGroup sgWatermark = settings.createGroup("Watermark");
    private final SettingGroup sgChatFeedback = settings.createGroup("Chat Feedback");
    private final SettingGroup sgWindow = settings.createGroup("Window");

    // Version

    public final Setting<Boolean> version = sgVersion.add(new BoolSetting.Builder()
        .name("enabled")
        .description("Changes the client version.")
        .defaultValue(false)
        .build()
    );

    public final Setting<String> versionText = sgVersion.add(new StringSetting.Builder()
        .name("text")
        .description("The text to replace the version with.")
        .defaultValue("0.4.7")
        .build()
    );

    // Watermark

    public final Setting<Boolean> watermark = sgWatermark.add(new BoolSetting.Builder()
        .name("enabled")
        .description("Changes the watermark client name.")
        .defaultValue(false)
        .build()
    );

    public final Setting<String> watermarkText = sgWatermark.add(new StringSetting.Builder()
        .name("text")
        .description("The text to replace the watermark with.")
        .defaultValue("Meteor Client")
        .build()
    );

    public final Setting<Boolean> watermarkMeteorIcon = sgWatermark.add(new BoolSetting.Builder()
        .name("meteor-icon")
        .description("Changes the watermark icon to Meteor.")
        .defaultValue(true)
        .build()
    );

    // Chat Feedback

    public final Setting<Boolean> chatFeedback = sgChatFeedback.add(new BoolSetting.Builder()
        .name("enabled")
        .description("Changes the chat feedback client name.")
        .defaultValue(false)
        .build()
    );

    public final Setting<String> chatFeedbackText = sgChatFeedback.add(new StringSetting.Builder()
        .name("text")
        .description("The text to replace chat feedback with.")
        .defaultValue("Meteor")
        .build()
    );

    public final Setting<Boolean> chatFeedbackChangeTextColor = sgChatFeedback.add(new BoolSetting.Builder()
        .name("change-color")
        .description("Changes the chat feedback client name color.")
        .defaultValue(true)
        .build()
    );

    public final Setting<SettingColor> chatFeedbackTextColor = sgChatFeedback.add(new ColorSetting.Builder()
        .name("color")
        .description("The text color of the chat feedback.")
        .defaultValue(new SettingColor(145, 61, 226))
        .build()
    );

    public final Setting<Boolean> chatFeedbackMeteorIcon = sgChatFeedback.add(new BoolSetting.Builder()
        .name("meteor-icon")
        .description("Changes the chat feedback icon to Meteor.")
        .defaultValue(true)
        .build()
    );

    public ClientSpoof() {
        super(Categories.Client, Items.COMMAND_BLOCK, "client-spoof", "Allows you to change the name of the client.", true);
    }

    @Override
    public boolean onActivate() {
        return false;
    }

    @Override
    public void onDeactivate() {
        // Icon & Window Title
    }

    public boolean changeVersion() {
        return isActive() && version.get();
    }

    public boolean changeWatermark() {
        return isActive() && watermark.get();
    }

    public boolean changeWatermarkIcon() {
        return isActive() && watermark.get() && watermarkMeteorIcon.get();
    }

    public boolean changeChatFeedback() {
        return isActive() && chatFeedback.get();
    }

    public boolean changeChatFeedbackColor() {
        return isActive() && chatFeedback.get() && chatFeedbackChangeTextColor.get();
    }

    public boolean changeChatFeedbackIcon() {
        return isActive() && chatFeedback.get() && chatFeedbackMeteorIcon.get();
    }
}
