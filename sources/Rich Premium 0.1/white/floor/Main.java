package white.floor;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import csgogui.CSGOGui;
import io.netty.channel.AdaptiveRecvByteBufAllocator;
import net.minecraft.client.renderer.entity.RenderPlayer;
import org.lwjgl.opengl.Display;
import clickgui.ClickGuiScreen;
import clickgui.setting.SettingsManager;
import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import net.minecraft.client.Minecraft;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import white.floor.changelogs.ChangeLogMngr;
import white.floor.event.EventManager;
import white.floor.event.EventTarget;
import white.floor.event.event.EventKey;
import white.floor.features.Feature;
import white.floor.features.FeatureDirector;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.render.cosmetic.CosmeticRender;
import white.floor.helpers.render.cosmetic.impl.DragonWing;

public class Main {

    public static Main instance = new Main();
    public static String name = "Rich Premium", version = "0.1";
    public static EventManager eventManager;
    public static FeatureDirector featureDirector;
    public static SettingsManager settingsManager;
    public static ClickGuiScreen clickGui1;
    public static CSGOGui csgoGui;
    public static ChangeLogMngr changeLogMngr;

    public static void startClient() {


        // Managers.
        (changeLogMngr = new ChangeLogMngr()).setChangeLogs();
        settingsManager = new SettingsManager();
        eventManager = new EventManager();
        featureDirector = new FeatureDirector();
        csgoGui = new CSGOGui();
        clickGui1 = new ClickGuiScreen();

        // Cosmetic.
        new DragonWing();
        for (RenderPlayer render : Minecraft.getMinecraft().getRenderManager().getSkinMap().values()) {
            render.addLayer(new CosmeticRender(render));
        }

        // Minecraft name.
        Display.setTitle(name);

        EventManager.register(instance);
    }

    public static void startDiscordRPC(String line) {
        DiscordRPC lib = DiscordRPC.INSTANCE;
        String applicationId = "898567657987649568";
        DiscordEventHandlers handlers = new DiscordEventHandlers();
        handlers.ready = (user) -> System.out.println("Ready!");
        lib.Discord_Initialize(applicationId, handlers, true, "");
        DiscordRichPresence presence = new DiscordRichPresence();
        presence.startTimestamp = System.currentTimeMillis() / 1000; // epoch second
        presence.details = name + " " + version;
        presence.largeImageKey = "rc";
        presence.largeImageText = line;
        lib.Discord_UpdatePresence(presence);
        // in a worker thread
        new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                lib.Discord_RunCallbacks();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException ignored) {}
            }
        }, "RPC-Callback-Handler").start();
    }

    public static void stopClient() {
        EventManager.unregister(instance);
    }

    public static ChangeLogMngr getChangeLogMngr() {
        return  instance.changeLogMngr;
    }

    public static void msg(String s, boolean prefix) {
        s = (prefix ? TextFormatting.GRAY +"[" + TextFormatting.RED  + "RC" + TextFormatting.GRAY +"]" + ": " : "") + s;
        Minecraft.getMinecraft().player.addChatMessage(new TextComponentTranslation(s.replace("&", "??")));
    }

    @EventTarget
    public void onKey(EventKey event) {
        featureDirector.getFeatures().stream().filter(module -> module.getKey() == event.getKey()).forEach(module -> module.toggle());}

    public static Color getClientColor(float yStep, float yStepFull, int speed) {

        Color color = Color.white;
        String mode = Main.settingsManager.getSettingByName("List Color").getValString();

        switch (mode) {
            case "Astolfo": {
                color = DrawHelper.astolfoColors45(yStep, yStepFull, 0.5f, speed);
                break;
            }
            case "Blood": {
                color = DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (yStep * 2.55) / 60);
                break;
            }
            case "Pinky": {
                color = DrawHelper.TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (yStep * 2.55) / 60);
                break;
            }
            case "Toxic": {
                color = DrawHelper.TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (yStep * 2.55) / 60);
                break;
            }
        }
        return color;
    }

    public static Color getClientColor() {

        Color color = Color.white;
        String mode = Main.settingsManager.getSettingByName("List Color").getValString();

        switch (mode) {
            case "Astolfo": {
                color = DrawHelper.astolfoColors45(0, 0, 0.5f, 3);
                break;
            }
            case "Blood": {
                color = DrawHelper.TwoColoreffect(new Color(255, 50, 50), new Color(79, 9, 9), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (1 * 2.55) / 60);
                break;
            }
            case "Pinky": {
                color = DrawHelper.TwoColoreffect(new Color(255, 183, 255), new Color(165, 95, 165), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (1 * 2.55) / 60);
                break;
            }
            case "Toxic": {
                color = DrawHelper.TwoColoreffect(new Color(90, 200, 79), new Color(30, 120, 20), Math.abs(System.currentTimeMillis() / 10L) / 100.0 + 6.0F * (1 * 2.55) / 60);
                break;
            }
        }
        return color;
    }
}