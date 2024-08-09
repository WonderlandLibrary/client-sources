package xyz.mathax.mathaxclient.systems.modules.misc;

import xyz.mathax.mathaxclient.settings.*;
import xyz.mathax.mathaxclient.systems.enemies.Enemies;
import xyz.mathax.mathaxclient.systems.enemies.Enemy;
import xyz.mathax.mathaxclient.systems.friends.Friend;
import xyz.mathax.mathaxclient.systems.friends.Friends;
import xyz.mathax.mathaxclient.systems.modules.Category;
import xyz.mathax.mathaxclient.systems.modules.Module;
import xyz.mathax.mathaxclient.utils.render.color.Color;
import xyz.mathax.mathaxclient.utils.render.color.SettingColor;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TextColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.GameMode;

public class BetterTab extends Module {
    private final SettingGroup generalSettings = settings.createGroup("General");

    // General

    public final Setting<Integer> tabSizeSetting = generalSettings.add(new IntSetting.Builder()
            .name("Tablist size")
            .description("How many players to display in the tablist.")
            .defaultValue(100)
            .min(1)
            .sliderRange(1, 1000)
            .build()
    );

    private final Setting<Boolean> selfSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Highlight self")
            .description("Highlights yourself in the tablist.")
            .defaultValue(true)
            .build()
    );

    private final Setting<SettingColor> selfColorSetting = generalSettings.add(new ColorSetting.Builder()
            .name("Self color")
            .description("The color to highlight your name with.")
            .defaultValue(new SettingColor(0, 165, 255))
            .visible(selfSetting::get)
            .build()
    );

    private final Setting<Boolean> friendsSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Highlight friends")
            .description("Highlights friends in the tablist.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> enemiesSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Highlight enemies")
            .description("Highlights enemies in the tablist.")
            .defaultValue(true)
            .build()
    );

    public final Setting<Boolean> accurateLatencySetting = generalSettings.add(new BoolSetting.Builder()
            .name("Accurate latency")
            .description("Shows latency as a number in the tablist.")
            .defaultValue(true)
            .build()
    );

    private final Setting<Boolean> gamemodeSetting = generalSettings.add(new BoolSetting.Builder()
            .name("Gamemode")
            .description("Display gamemode next to the nick.")
            .defaultValue(false)
            .build()
    );


    public BetterTab(Category category) {
        super(category, "Better Tab", "Various improvements to the tab list.");
    }

    public Text getPlayerName(PlayerListEntry playerListEntry) {
        Text name = playerListEntry.getDisplayName();
        if (name == null) {
            name = Text.literal(playerListEntry.getProfile().getName());
        }

        Color color = null;
        if (playerListEntry.getProfile().getId().toString().equals(mc.player.getGameProfile().getId().toString()) && selfSetting.get()) {
            color = selfColorSetting.get();
        } else if (friendsSetting.get() && Friends.get().contains(playerListEntry)) {
            Friend friend = Friends.get().get(playerListEntry);
            if (friend != null) {
                color = Friends.get().colorSetting.get();
            }
        } else if (enemiesSetting.get() && Enemies.get().contains(playerListEntry)) {
            Enemy enemy = Enemies.get().get(playerListEntry);
            if (enemy != null) {
                color = Enemies.get().colorSetting.get();
            }
        }

        if (color != null) {
            String nameString = name.getString();
            for (Formatting format : Formatting.values()) {
                if (format.isColor()) {
                    nameString = nameString.replace(format.toString(), "");
                }
            }

            name = Text.literal(nameString).setStyle(name.getStyle().withColor(new TextColor(color.getPacked())));
        }

        if (gamemodeSetting.get()) {
            GameMode gm = playerListEntry.getGameMode();
            String gmText = "?";
            if (gm != null) {
                gmText = switch (gm) {
                    case SPECTATOR -> "Sp";
                    case SURVIVAL -> "S";
                    case CREATIVE -> "C";
                    case ADVENTURE -> "A";
                };
            }

            MutableText text = Text.literal("");
            text.append(name);
            text.append(" [" + gmText + "]");
            name = text;
        }

        return name;
    }

}
