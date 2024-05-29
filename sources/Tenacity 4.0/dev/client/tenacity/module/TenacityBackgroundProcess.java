package dev.client.tenacity.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.client.tenacity.Tenacity;
import dev.client.tenacity.config.DragManager;
import dev.client.tenacity.module.impl.render.SessionStats;
import dev.client.tenacity.ui.mainmenu.TenacityMainMenu;
import dev.event.EventListener;
import dev.event.impl.game.KeyPressEvent;
import dev.event.impl.game.GameCloseEvent;
import dev.event.impl.game.TickEvent;
import dev.event.impl.player.ChatReceivedEvent;
import dev.utils.Utils;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Arrays;

public class TenacityBackgroundProcess implements Utils {

    private final EventListener<KeyPressEvent> onKeyPress = e ->
            Tenacity.INSTANCE.getModuleCollection().getModules().stream()
                    .filter(m -> m.getKeybind().getCode() == e.getKey()).forEach(Module::toggle);

    private final File dragData = new File(Tenacity.DIRECTORY, "Drag.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().excludeFieldsWithoutExposeAnnotation().setPrettyPrinting().create();

    private final EventListener<GameCloseEvent> onGameClose = e -> {
        //Tenacity.INSTANCE.getConfigManager().saveDefaultConfig();
        DragManager.saveDragData();
    };

    private final EventListener<ChatReceivedEvent> onChatReceived = e -> {
        if (mc.thePlayer == null) return;
        String message = StringUtils.stripControlCodes(e.message.getUnformattedText());
        if (!message.contains(":") && Arrays.stream(SessionStats.KILL_TRIGGERS).anyMatch(message.replace(mc.thePlayer.getName(), "*")::contains)) {
            SessionStats.killCount++;
        } else if (e.message.toString().contains("ClickEvent{action=RUN_COMMAND, value='/play ")) {
            SessionStats.gamesPlayed++;
        }
    };

    private final EventListener<TickEvent> onTick = e -> {
        if (SessionStats.endTime == -1 && ((!mc.isSingleplayer() && mc.getCurrentServerData() == null) || mc.currentScreen instanceof TenacityMainMenu || mc.currentScreen instanceof GuiMultiplayer || mc.currentScreen instanceof GuiDisconnected)) {
            SessionStats.endTime = System.currentTimeMillis();
        } else if (SessionStats.endTime != -1 && (mc.isSingleplayer() || mc.getCurrentServerData() != null)) {
            SessionStats.reset();
        }
    };

}
