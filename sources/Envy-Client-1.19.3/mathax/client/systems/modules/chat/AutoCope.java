package mathax.client.systems.modules.chat;

import mathax.client.eventbus.EventHandler;
import mathax.client.events.world.TickEvent;
import mathax.client.settings.BoolSetting;
import mathax.client.settings.Setting;
import mathax.client.settings.SettingGroup;
import mathax.client.settings.StringListSetting;
import mathax.client.systems.modules.Categories;
import mathax.client.systems.modules.Module;
import mathax.client.utils.Utils;
import net.minecraft.item.Items;

import java.util.List;

public class AutoCope extends Module {
    private boolean sent;

    private int messageI;

    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    private final Setting<List<String>> messagesSetting = generalSettings.add(new StringListSetting.Builder()
        .name("messages")
        .description("Messages to send when you die")
        .defaultValue(
            "Haxxer",
            "Fucking Lunar Hacker",
            "I'm using a trackpad",
            "I Accidently Spilled Water on my CPU",
            "Sorry I'm on Mobile",
            "Lag",
            "I have 300 Ping",
            "Stop Cheating LMAO",
            "I'm using a Controller",
            "WTF Bhop",
            "AntiKB!!!!???",
            "I'm using a Trackball",
            "I'm using a Pen to aim",
            "My Mouse stopped working",
            "I'm using a Gamepad",
            "I'm using a Joystick",
            "My Keyboard is broken",
            "Fucking Lag",
            "This Server Sucks",
            "Go hack on 2B2T hacker LMAO"
        )
        .build()
    );

    private final Setting<Boolean> ignoreSelfSetting = generalSettings.add(new BoolSetting.Builder()
        .name("Ignore self")
        .description("Skips messages when you're in '${random_player}'.")
        .defaultValue(true)
        .build()
    );

    private final Setting<Boolean> randomSetting = generalSettings.add(new BoolSetting.Builder()
        .name("random")
        .description("Select a random message from your spam message list.")
        .defaultValue(false)
        .build()
    );

    @EventHandler
    public void onTick(TickEvent.Post event) {
        if (messagesSetting.get().isEmpty()) {
            return;
        }

        if (!mc.player.isDead() && sent) {
            sent = false;
        }

        if (mc.player.isDead() && !sent) {
            int i;
            if (randomSetting.get()) {
                i = Utils.random(0, messagesSetting.get().size());
            } else {
                if (messageI >= messagesSetting.get().size()) {
                    messageI = 0;
                }

                i = messageI++;
            }

            mc.player.networkHandler.sendChatMessage(messagesSetting.get().get(i));

            sent = true;
        }
    }

    public AutoCope() {
        super(Categories.Chat, Items.PAPER, "auto-cope", "Automatically sends a message when you die.");
    }
}
