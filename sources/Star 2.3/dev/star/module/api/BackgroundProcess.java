package dev.star.module.api;

import dev.star.Client;
import dev.star.config.DragManager;
import dev.star.event.ListenerAdapter;
import dev.star.event.impl.game.GameCloseEvent;
import dev.star.event.impl.game.KeyPressEvent;
import dev.star.event.impl.game.TickEvent;
import dev.star.event.impl.game.WorldEvent;
import dev.star.event.impl.player.ChatReceivedEvent;
import dev.star.event.impl.render.Render2DEvent;
import dev.star.event.impl.render.ShaderEvent;
import dev.star.module.Module;
import dev.star.module.impl.movement.Scaffold;
import dev.star.module.impl.display.Statistics;
import dev.star.utils.Utils;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.util.StringUtils;

import java.util.Arrays;

public class BackgroundProcess extends ListenerAdapter implements Utils {

    private final Scaffold scaffold = (Scaffold) Client.INSTANCE.getModuleCollection().get(Scaffold.class);

    @Override
    public void onKeyPressEvent(KeyPressEvent event) {

        // We should probably have a static arraylist of all the modules instead of creating a new on in getModules()
        for (Module module : Client.INSTANCE.getModuleCollection().getModules()) {
            if (module.getKeybind().getCode() == event.getKey()) {
                module.toggle();
            }
        }
    }

    @Override
    public void onGameCloseEvent(GameCloseEvent event) {
        Client.INSTANCE.getConfigManager().saveDefaultConfig();
        DragManager.saveDragData();
    }

    @Override
    public void onChatReceivedEvent(ChatReceivedEvent event) {
        if (mc.thePlayer == null) return;
        String message = event.message.getUnformattedText(), strippedMessage = StringUtils.stripControlCodes(message);
        String messageStr = event.message.toString();
        if (!strippedMessage.contains(":") && Arrays.stream(Statistics.KILL_TRIGGERS).anyMatch(strippedMessage.replace(mc.thePlayer.getName(), "*")::contains)) {
            Statistics.killCount++;
        } else if (messageStr.contains("ClickEvent{action=RUN_COMMAND, value='/play ") || messageStr.contains("Want to play again?")) {
            Statistics.gamesPlayed++;
            if (messageStr.contains("You died!")) {
                Statistics.deathCount++;
            }
        }
    }

    @Override
    public void onTickEvent(TickEvent event) {
        if (Statistics.endTime == -1 && ((!mc.isSingleplayer() && mc.getCurrentServerData() == null) || mc.currentScreen instanceof GuiMainMenu || mc.currentScreen instanceof GuiMultiplayer || mc.currentScreen instanceof GuiDisconnected)) {
            Statistics.endTime = System.currentTimeMillis();
        } else if (Statistics.endTime != -1 && (mc.isSingleplayer() || mc.getCurrentServerData() != null)) {
            Statistics.reset();
        }
    }

    @Override
    public void onShaderEvent(ShaderEvent event) {
        if (mc.thePlayer != null) {
            scaffold.renderCounterBlur();
        }
    }

    @Override
    public void onRender2DEvent(Render2DEvent event) {
        if (mc.thePlayer != null) {
            scaffold.renderCounter();
        }
    }

    @Override
    public void onWorldEvent(WorldEvent event) {
        if (event instanceof WorldEvent.Load) {
        }
    }

}
