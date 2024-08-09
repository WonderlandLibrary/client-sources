package mathax.client.systems.config;

import mathax.client.renderer.text.Fonts;
import mathax.client.settings.*;
import mathax.client.systems.Systems;
import mathax.client.systems.System;
import mathax.client.utils.render.color.RainbowColors;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.nbt.NbtString;

import java.util.ArrayList;
import java.util.List;

public class Config extends System<Config> {
    public final Settings settings = new Settings();

    private final SettingGroup sgVisual = settings.createGroup("Visual");
    private final SettingGroup sgChat = settings.createGroup("Chat");
    private final SettingGroup sgToasts = settings.createGroup("Toasts");
    private final SettingGroup sgMusic = settings.createGroup("Music");
    private final SettingGroup sgMisc = settings.createGroup("Misc");

    // Visual

    public final Setting<Boolean> customFont = sgVisual.add(new BoolSetting.Builder()
        .name("custom-font")
        .description("Use a custom font.")
        .defaultValue(true)
        .build()
    );

    public final Setting<String> font = sgVisual.add(new ProvidedStringSetting.Builder()
        .name("font")
        .description("Custom font to use (picked from .minecraft/MatHax/Fonts folder).")
        .visible(customFont::get)
        .supplier(Fonts::getAvailableFonts)
        .defaultValue(Fonts.DEFAULT_FONT)
        .onChanged(s -> Fonts.load())
        .build()
    );

    public final Setting<Double> rainbowSpeed = sgVisual.add(new DoubleSetting.Builder()
        .name("rainbow-speed")
        .description("The global rainbow speed.")
        .defaultValue(0.5)
        .range(0, 100)
        .sliderRange(0, 100)
        .onChanged(value -> RainbowColors.GLOBAL.setSpeed(value / 100))
        .onModuleActivated(setting -> setting.set(RainbowColors.GLOBAL.getSpeed() * 100))
        .build()
    );

    // Chat

    public final Setting<String> prefix = sgChat.add(new StringSetting.Builder()
        .name("prefix")
        .description("The command prefix.")
        .defaultValue(".")
        .build()
    );

    public final Setting<Boolean> prefixOpensConsole = sgChat.add(new BoolSetting.Builder()
        .name("open-chat-on-prefix")
        .description("Open chat when command prefix is pressed. Works like pressing '/' in vanilla.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> chatFeedback = sgChat.add(new BoolSetting.Builder()
        .name("chat-feedback")
        .description("Sends chat feedback when MatHax performs certain actions.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Boolean> deleteChatFeedback = sgChat.add(new BoolSetting.Builder()
        .name("delete-chat-feedback")
        .description("Delete previous matching chat feedback to keep chat clear.")
        .visible(chatFeedback::get)
        .defaultValue(true)
        .build()
    );

    // Toasts

    public final Setting<Boolean> toastFeedback = sgToasts.add(new BoolSetting.Builder()
        .name("toast-feedback")
        .description("Sends a toast feedback when MatHax performs certain actions.")
        .defaultValue(true)
        .build()
    );

    public final Setting<Integer> toastDuration = sgToasts.add(new IntSetting.Builder()
        .name("duration")
        .description("Determines how long the toast will stay visible in milliseconds")
        .defaultValue(3000)
        .min(1)
        .sliderRange(1, 6000)
        .build()
    );

    public final Setting<Boolean> toastSound = sgToasts.add(new BoolSetting.Builder()
        .name("sound")
        .description("Plays a sound when a toast appears.")
        .defaultValue(true)
        .build()
    );

    // Music

    public final Setting<Integer> musicVolume = sgMusic.add(new IntSetting.Builder()
        .name("volume")
        .description("Determines the volume of the currently played music.")
        .defaultValue(100)
        .min(1)
        .sliderRange(1, 250)
        .build()
    );

    // Misc

    public final Setting<Integer> rotationHoldTicks = sgMisc.add(new IntSetting.Builder()
        .name("rotation-hold")
        .description("Hold long to hold server side rotation when not sending any packets.")
        .defaultValue(4)
        .build()
    );

    public final Setting<Boolean> useTeamColor = sgMisc.add(new BoolSetting.Builder()
        .name("use-team-color")
        .description("Uses player's team color for rendering things like esp and tracers.")
        .defaultValue(true)
        .build()
    );

    public List<String> dontShowAgainPrompts = new ArrayList<>();

    public Config() {
        super("Config");
    }

    public static Config get() {
        return Systems.get(Config.class);
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.put("settings", settings.toTag());
        tag.put("dontShowAgainPrompts", listToTag(dontShowAgainPrompts));

        return tag;
    }

    @Override
    public Config fromTag(NbtCompound tag) {
        if (tag.contains("settings")) settings.fromTag(tag.getCompound("settings"));
        if (tag.contains("don'tShowAgainPrompts")) dontShowAgainPrompts = listFromTag(tag, "dontShowAgainPrompts");

        return this;
    }

    private NbtList listToTag(List<String> list) {
        NbtList nbt = new NbtList();
        for (String item : list) nbt.add(NbtString.of(item));
        return nbt;
    }

    private List<String> listFromTag(NbtCompound tag, String key) {
        List<String> list = new ArrayList<>();
        for (NbtElement item : tag.getList(key, 8)) list.add(item.asString());
        return list;
    }
}
