package xyz.mathax.mathaxclient.systems.modules.chat;

import com.mojang.authlib.GameProfile;
import xyz.mathax.mathaxclient.eventbus.EventHandler;
import xyz.mathax.mathaxclient.events.game.GameLeftEvent;
import xyz.mathax.mathaxclient.events.game.OpenScreenEvent;
import xyz.mathax.mathaxclient.events.world.TickEvent;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.Utils;
import xyz.mathax.mathaxclient.utils.text.ChatUtils;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.List;

public class Spam extends Module {
    private int messageI, timer;

    private final SettingGroup generalSettings = settings.createGroup("General");
    private final SettingGroup antiSpamBypassSettings = settings.createGroup("Anti Spam Bypass");

    // General

    private final Setting<List<String>> messagesSetting = generalSettings.add(new StringListSetting.Builder()
            .name("Messages")
            .description("Messages to use for spam. Use %player% for a name of a random player.")
            .defaultValue(List.of("MatHax on top!", "Matejko06 on top!"))
            .build()
    );

    private final Setting<Boolean> ignoreSelfSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Ignore self")
            .description("Skips messages when you're in %player%.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Integer> delaySetting = generalSettings.add(new IntSetting.Builder()
            .name("Delay")
            .description("The delay between specified messages in ticks.")
            .defaultValue(20)
            .min(0)
            .sliderRange(0, 200)
            .build()
    );

    private final Setting<Boolean> disableOnLeaveSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Disable on leave")
            .description("Disables spam when you leave a server.")
            .defaultValue(true)
            .build()
    );


    private final Setting<Boolean> disableOnDisconnectSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Disable on disconnect")
            .description("Disable spam when you are disconnected from a server.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> randomSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Random")
            .description("Select a random message from your spam message list.")
            .defaultValue(false)
            .build()
    );

    // Anti Spam Bypass

    private final Setting<Boolean> randomTextSetting = antiSpamBypassSettings.add(new BoolSetting.Builder()
            .name("Random text")
            .description("Adds random text at the bottom of the text.")
            .defaultValue(false)
            .build()
    );

    private final Setting<Integer> randomTextLengthSetting = antiSpamBypassSettings.add(new IntSetting.Builder()
            .name("Length")
            .description("Text length of anti spam bypass.")
            .defaultValue(16)
            .sliderRange(1, 256)
            .visible(randomTextSetting::get)
            .build()
    );

    public Spam(Category category) {
        super(category, "Spam", "Spams specified messages in chat.");
    }

    @Override
    public void onEnable() {
        timer = delaySetting.get();
        messageI = 0;
    }

    @EventHandler
    private void onScreenOpen(OpenScreenEvent event) {
        if (disableOnDisconnectSetting.get() && event.screen instanceof DisconnectedScreen) {
            forceToggle(false);
        }
    }

    @EventHandler
    private void onGameLeft(GameLeftEvent event) {
        if (disableOnLeaveSetting.get()) {
            forceToggle(false);
        }
    }

    @EventHandler
    private void onTick(TickEvent.Post event) {
        if (messagesSetting.get().isEmpty()) {
            return;
        }

        if (timer <= 0) {
            int i;
            if (randomSetting.get()) {
                i = Utils.random(0, messagesSetting.get().size());
            } else {
                if (messageI >= messagesSetting.get().size()) {
                    messageI = 0;
                }

                i = messageI++;
            }

            String text = messagesSetting.get().get(i);
            if (randomTextSetting.get()) {
                text += " " + RandomStringUtils.randomAlphabetic(randomTextLengthSetting.get());
            }

            if (text.contains("%player%")) {
                GameProfile randomPlayer;
                do {
                    randomPlayer = Utils.getRandomGameProfile();
                } while (randomPlayer == mc.getSession().getProfile());

                if (randomPlayer != null) {
                    text = text.replace("%player%", randomPlayer.getName());
                }
            }

            ChatUtils.sendMessageAsPlayer(text);

            timer = delaySetting.get();
        } else {
            timer--;
        }
    }
}