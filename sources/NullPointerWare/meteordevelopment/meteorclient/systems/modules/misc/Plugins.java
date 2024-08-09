/*
 * This file is part of the Meteor Client distribution (https://github.com/MeteorDevelopment/meteor-client).
 * Copyright (c) Meteor Development.
 */

package meteordevelopment.meteorclient.systems.modules.misc;

import com.mojang.brigadier.suggestion.Suggestion;
import meteordevelopment.meteorclient.events.packets.CommandSuggestEvent;
import meteordevelopment.meteorclient.settings.SettingGroup;
import meteordevelopment.meteorclient.systems.modules.Categories;
import meteordevelopment.meteorclient.systems.modules.Module;
import meteordevelopment.meteorclient.utils.player.ChatUtils;
import meteordevelopment.orbit.EventHandler;
import net.minecraft.network.packet.c2s.play.RequestCommandCompletionsC2SPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public final class Plugins extends Module {
    private final SettingGroup sgGeneral = settings.getDefaultGroup();

    public Plugins() {
        super(Categories.Misc, "Plugins", "Checks what plugins the server has :)");
    }

    @Override
    public void onActivate() {
        super.onActivate();
        if (mc.player != null) {
            sendNoEvent(new RequestCommandCompletionsC2SPacket(new Random().nextInt(200), "/"));
        }
    }

    @EventHandler
    private void onCMDSuggest(final CommandSuggestEvent e) {
        CommandSuggestionsS2CPacket packet = e.getPacket();
        List<String> plugins = new ArrayList<>();
        List<Suggestion> cmdList = packet.getSuggestions().getList();

        for (Suggestion s : cmdList) {
            String[] cmd = s.getText().split(":");

            if (cmd.length > 1) {
                String pluginName = cmd[0].replace("/", "");

                if (!plugins.contains(pluginName)) {
                    plugins.add(pluginName);
                }
            }
        }

        if (!plugins.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (String s : plugins) {
                if (s.equalsIgnoreCase("itemeditor")) {
                    sb.append("§c").append(s).append(", §a");
                } else if (s.equalsIgnoreCase("bettershulkerboxes")) {
                    sb.append("§c").append(s).append(", §a");
                } else if (s.equalsIgnoreCase("vulcan") || s.equalsIgnoreCase("ncp") || s.equalsIgnoreCase("spartan") || s.equalsIgnoreCase("matrix")) {
                    sb.append("§b").append(s).append(", §a");
                } else sb.append(s).append(", ");
            }
            ChatUtils.addMessage(Text.literal("&7Plugins: &a" + sb));
        } else {
            ChatUtils.addMessage(Text.literal("&cNo plugins found!"));
        }


        toggle();
    }


}
