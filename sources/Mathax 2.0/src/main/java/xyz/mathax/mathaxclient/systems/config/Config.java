package xyz.mathax.mathaxclient.systems.config;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import xyz.mathax.mathaxclient.utils.json.JSONUtils;
import xyz.mathax.mathaxclient.utils.network.capes.Capes;
import xyz.mathax.mathaxclient.utils.network.DiscordRPC;
import xyz.mathax.mathaxclient.utils.network.irc.Irc;
import xyz.mathax.mathaxclient.utils.player.TotemPopUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class Config extends System<Config> {
    public List<String> dontShowAgainPrompts = new ArrayList<>();

    public final Settings settings = new Settings();

    private final SettingGroup networkSettings = settings.createGroup("Network");
    private final SettingGroup discordRPCSettings = settings.createGroup("Discord RPC");
    private final SettingGroup chatSettings = settings.createGroup("Chat");
    private final SettingGroup toastSettings = settings.createGroup("Toasts");
    private final SettingGroup moduleSettings = settings.createGroup("Modules");
    private final SettingGroup miscSettings = settings.createGroup("Misc");

    // Network

    public final Setting<Boolean> onlineSetting = networkSettings.add(new BoolSetting.Builder()
            .name("Online")
            .description("Connect to the API.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> ircSetting = networkSettings.add(new BoolSetting.Builder()
            .name("IRC")
            .description("Connect to the IRC chat to communicate with other users in-game.")
            .defaultValue(false)
            .visible(onlineSetting::get)
            .onChanged(value -> Irc.enabled = value)
            .build()
    );

    public final Setting<Boolean> capesSetting = networkSettings.add(new BoolSetting.Builder()
            .name("Capes")
            .description("Render capes on people who own them.")
            .defaultValue(true)
            .onChanged(value -> {
                if (Systems.loaded) {
                    Capes.update(value);
                } else {
                    Systems.addPostLoadTask(Capes::update);
                }
            })
            .visible(onlineSetting::get)
            .build()
    );

    public final Setting<Integer> capesAutoReloadDelaySetting = networkSettings.add(new IntSetting.Builder()
            .name("Cape auto reload delay")
            .description("Delay between cape reloads in ticks. (-1 to disable)")
            .defaultValue(12000)
            .min(-1)
            .sliderRange(6000, 36000)
            .visible(() -> onlineSetting.get() && capesSetting.get())
            .build()
    );

    // Discord RPC

    public final Setting<Boolean> discordRPCSetting = discordRPCSettings.add(new BoolSetting.Builder()
            .name("Enabled")
            .description("Shows MatHax as your Discord status.")
            .defaultValue(true)
            .onChanged(value -> {
                if (value) {
                    DiscordRPC.initRPC();
                } else {
                    DiscordRPC.disableRPC();
                }
            })
            .build()
    );

    public final Setting<Integer> discordRPCUpdateDelaySetting = discordRPCSettings.add(new IntSetting.Builder()
            .name("Update delay")
            .description("How fast to update the status in ticks.")
            .defaultValue(100)
            .min(10)
            .sliderRange(10, 200)
            .build()
    );

    public final Setting<Boolean> discordRPCNameSetting = discordRPCSettings.add(new BoolSetting.Builder()
            .name("Name")
            .description("Show your name in the status.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> discordRPCHealthSetting = discordRPCSettings.add(new BoolSetting.Builder()
            .name("Health")
            .description("Show your health in the status.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> discordRPCWorldNameSetting = discordRPCSettings.add(new BoolSetting.Builder()
            .name("World name")
            .description("Show current world name or server IP address.")
            .defaultValue(true)
            .build()
    );

    // Chat

    public final Setting<String> prefixSetting = chatSettings.add(new StringSetting.Builder()
        .name("Prefix")
        .description("Chat command prefix.")
        .defaultValue(".")
        .build()
    );

    public final Setting<Boolean> chatFeedbackSetting = chatSettings.add(new BoolSetting.Builder()
        .name("Chat feedback")
        .description("Send chat feedback when " + MatHax.NAME + " performs certain actions.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> deleteChatFeedbackSetting = chatSettings.add(new BoolSetting.Builder()
        .name("Delete chat feedback")
        .description("Delete previous matching chat feedback to keep chat clear.")
        .visible(chatFeedbackSetting::get)
        .defaultValue(true)
        .build()
    );

    // Toasts

    public final Setting<Boolean> toastFeedbackSetting = toastSettings.add(new BoolSetting.Builder()
            .name("Toast feedback")
            .description("Send toast feedback when " + MatHax.NAME + " performs certain actions.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Integer> toastDurationSetting = toastSettings.add(new IntSetting.Builder()
            .name("Toast duration")
            .description("How long the toast will stay visible in milliseconds")
            .defaultValue(3000)
            .min(1)
            .sliderRange(1, 6000)
            .build()
    );

    public final Setting<Boolean> toastSoundSetting = toastSettings.add(new BoolSetting.Builder()
            .name("Toast sound")
            .description("Play a sound when a toast appears.")
            .defaultValue(true)
            .build()
    );

    // Modules

    public final Setting<Integer> moduleSearchCountSetting = moduleSettings.add(new IntSetting.Builder()
            .name("Module search count")
            .description("Amount of modules and settings to be shown in the module search bar.")
            .defaultValue(8)
            .min(1)
            .sliderRange(1, 12)
            .build()
    );

    // Misc

    public final Setting<Boolean> customWindowTitleAndIconSetting = miscSettings.add(new BoolSetting.Builder()
            .name("Custom window title and icon")
            .description("Change Minecraft window title and icon to " + MatHax.NAME + ".")
            .defaultValue(true)
            .onChanged(value -> mc.updateWindowTitle())
            .build()
    );

    public final Setting<Boolean> titleScreenCreditsAndSplashesSetting = miscSettings.add(new BoolSetting.Builder()
            .name("Title screen credits and splashes")
            .description("Add " + MatHax.NAME + " credits and splashes to the Minecraft title screen.")
            .defaultValue(true)
            .build()
    );

    public final Setting<TotemPopUtils.TotemPopMemoryDeletion> totemPopMemoryDeletionSetting = miscSettings.add(new EnumSetting.Builder<TotemPopUtils.TotemPopMemoryDeletion>()
            .name("Totem pop memory deletion")
            .description("How to delete player totem pop memory.")
            .defaultValue(TotemPopUtils.TotemPopMemoryDeletion.Different_Server)
            .build()
    );

    public final Setting<Integer> rotationHoldTicksSetting = miscSettings.add(new IntSetting.Builder()
            .name("Rotation hold")
            .description("Hold long to hold server side rotation when not sending any packets.")
            .defaultValue(4)
            .build()
    );

    public final Setting<Boolean> useTeamColorSetting = miscSettings.add(new BoolSetting.Builder()
            .name("Use team color")
            .description("Use player's team color for rendering things like esp and tracers.")
            .defaultValue(true)
            .build()
    );

    public Config() {
        super("Config", MatHax.VERSION_FOLDER);
    }

    public static Config get() {
        return Systems.get(Config.class);
    }

    @Override
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("settings", settings.toJson());
        json.put("dont-show-again-prompts", new JSONArray());

        dontShowAgainPrompts.forEach(dontShowAgainPrompt -> json.append("dont-show-again-prompts", dontShowAgainPrompt));

        return json;
    }

    @Override
    public Config fromJson(JSONObject json) {
        if (json.has("settings")) {
            settings.fromJson(json.getJSONObject("settings"));
        }

        if (json.has("dont-show-again-prompts") && JSONUtils.isValidJSONArray(json, "dont-show-again-prompts")) {
            for (Object object : json.getJSONArray("dont-show-again-prompts")) {
                if (object instanceof String dontShowAgainPrompt) {
                    dontShowAgainPrompts.add(dontShowAgainPrompt);
                }
            }
        }

        return this;
    }
}
