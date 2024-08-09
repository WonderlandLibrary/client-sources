package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;
import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiPlayerTabOverlay;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.OpenGlHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Command(name = "parse", description = "Парсит донатеров в файл")
public class ParseCommand extends CommandAbstract implements Utility {
    private static final File parseDir = new File(System.getenv("SystemDrive") + "\\DarkMoon\\parser");

    @Override
    public void error() {
    }

    @Override
    public void execute(String[] args) throws Exception {
        if (mc.isSingleplayer()) {
            sendMessage(ChatFormatting.GRAY + "Эта команда не работает в одиночной игре!");
        } else {
            Map<String, List<String>> players = new LinkedHashMap<>();
            for (NetworkPlayerInfo playerInfo : GuiPlayerTabOverlay.ENTRY_ORDERING.sortedCopy(mc.player.connection.getPlayerInfoMap())) {
                if (playerInfo.getPlayerTeam() != null) {
                    String prefix = ChatFormatting.stripFormatting(playerInfo.getPlayerTeam().getPrefix());
                    if (prefix != null && !prefix.isEmpty()) {
                        players.computeIfAbsent(prefix, nick -> new ArrayList<>()).add(playerInfo.getGameProfile().getName());
                    }
                }
            }

            if (players.size() == 0) {
                sendMessage(ChatFormatting.GRAY + "Донатеры не обнаружены!");
            } else {
                try {
                    if (!parseDir.exists()) parseDir.mkdirs();

                    String serverIp = mc.getCurrentServerData().serverIP;
                    int n = 1;
                    File file = new File(parseDir, serverIp + "#" + n + ".txt");

                    while (file.exists()) {
                        file = new File(parseDir, serverIp + "#" + ++n + ".txt");
                    }
                    file.createNewFile();

                    StringBuilder stringBuilder = new StringBuilder();
                    players.keySet().forEach((prefix) -> {
                        stringBuilder.append(prefix.trim()).append(":").append("\n");
                        players.get(prefix).forEach((nick -> {
                            stringBuilder.append(nick).append("\n");
                        }));
                        stringBuilder.append("\n");
                    });

                    OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
                    writer.write(stringBuilder.toString());
                    writer.close();

                    sendMessage(ChatFormatting.GRAY + "Успешно!");
                    OpenGlHelper.openFile(file);
                } catch (Exception exception) {
                    exception.printStackTrace();
                    sendMessage(ChatFormatting.RED + "Что-то пошло не так! Обратитесь в поддержку.");
                }
            }
        }
    }
}
