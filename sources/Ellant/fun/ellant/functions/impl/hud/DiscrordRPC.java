package fun.ellant.functions.impl.hud;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import com.google.common.eventbus.Subscribe;
import fun.ellant.events.EventDisplay;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.settings.impl.StringSetting;

@FunctionRegister(name = "Discord Activity", type = Category.RENDER, desc = "Показывает активность в дискорде")
public class DiscrordRPC extends Function {

    public static StringSetting name = new StringSetting("Имя в дискорде", "Nakson_Play", "Отображаемое имя ");
    @Override
    public boolean onEnable() {
        super.onEnable();
        startRPC();
        return false;
    }

    @Override
    public void onDisable() {
        super.onDisable();
        stopRPC();
    }

    @Subscribe
    public void onEvent(EventDisplay event) {
        // Обработка события при необходимости
    }

    private static final DiscordRPC discordRPC = DiscordRPC.INSTANCE;
    private static final String discordID = "1239666098845122621";
    private static final DiscordRichPresence discordRichPresence = new DiscordRichPresence();

    public static void stopRPC() {
        discordRPC.Discord_Shutdown();
    }

    public static void startRPC() {
        DiscordEventHandlers eventHandlers = new DiscordEventHandlers();
        discordRPC.Discord_Initialize(discordID, eventHandlers, true, null);
        discordRichPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordRichPresence.largeImageKey = "logo";
        discordRichPresence.largeImageText = "dsc.gg/ellantclient";

        new Thread(() -> {
            while (true) {
                try {
                    discordRichPresence.details = "Роль -> Создатель";
                    discordRichPresence.state = "Версия -> Альфа";
                    discordRPC.Discord_UpdatePresence(discordRichPresence);
                    Thread.sleep(9999);
                } catch (InterruptedException ignored) {
                }
            }
        }).start();
    }
}