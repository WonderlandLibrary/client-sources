package white.floor.features.impl.display;

import clickgui.setting.Setting;
import com.mojang.realmsclient.gui.ChatFormatting;
import net.minecraft.client.gui.Gui;
import org.lwjgl.input.Keyboard;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.Event2D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

import java.awt.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;

public class Watermark extends Feature {
    public Watermark() {
        super("Watermark", "Client logo.", Keyboard.KEY_H, Category.DISPLAY);
        ArrayList<String> watermark = new ArrayList<>();
        watermark.add("Nursultan");
        watermark.add("Novoline");
        watermark.add("FDP");
        watermark.add("Onetap");
        watermark.add("OnetapV2");
        watermark.add("Neverlose");
        watermark.add("Akrien");
        Main.instance.settingsManager.rSetting(new Setting("WaterMark Mode", this, "Nursultan", watermark));
        Main.settingsManager.rSetting(new Setting("ClientName", this, true));
    }

    @EventTarget
    public void ebatkopat(Event2D render) {

        String watermark = Main.settingsManager.getSettingByName("WaterMark Mode").getValString();
        String name = mc.player.getName();

        this.setModuleName("Watermark §7[" + watermark + "]");

        if (watermark.equalsIgnoreCase("Novoline")) {
            String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
            int offset = Fonts.sfui16.getStringWidth(Main.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + time + ChatFormatting.GRAY + ")");
            Fonts.sfui18.drawStringWithShadow(Main.name + ChatFormatting.GRAY + " (" + ChatFormatting.WHITE + time + ChatFormatting.GRAY + ")", 3, 4, Main.getClientColor(0, 0, 3).getRGB());
        }

        if (watermark.equalsIgnoreCase("FDP")) {
                String time = (new SimpleDateFormat("HH:mm")).format(Calendar.getInstance().getTime());
                String text = "liquidbounce.net |  " + name + " | " + time;
                Gui.drawRect(5, 5, Fonts.roboto_16.getStringWidth(text) + 9, 21, new Color(37, 37, 37, 255).getRGB());
                Gui.drawRect(6, 6, Fonts.roboto_16.getStringWidth(text) + 8, 8.5f, Main.getClientColor(0, 0, 3).getRGB());
                Fonts.roboto_16.drawStringWithShadow(text, 7, 13, -1);
        }
        if (watermark.equalsIgnoreCase("Nursultan")) {
            String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                String text = "Rich Lite | " + name + " | " + server + " | " + mc.getDebugFPS() + " fps" + " | " + Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "ms" + " | " + time;
                Gui.drawRect(5, 6, Fonts.sfui16.getStringWidth(text) + 9, 19, new Color(31, 31, 31, 255).getRGB());
                Gui.drawRect(5, 6.5f, Fonts.sfui16.getStringWidth(text) + 9, 7f, new Color(20, 20, 20, 100).getRGB());

                Fonts.sfui16.drawStringWithShadow(text, 7, 10.5, -1);
        }

        if (watermark.equalsIgnoreCase("Onetap")) {
            String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
            String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
            String ping = mc.getConnection().getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "";
                DrawHelper.drawGradientRect(5, 185, 95, 198, new Color(22, 22, 22, 205).getRGB(), new Color(21, 21, 21, 0).getRGB());
                for(float l = 0; l < 90; l += 1f) {
                    Gui.drawRect(l + 5, 184, l + 6, 185, DrawHelper.astolfoColors5(l - l + 15, l, 0.5f, 10f));
                }
                Fonts.roboto_13.drawStringWithShadow("onetap.com", 31, 190, -1);
                Fonts.roboto_13.drawStringWithShadow("user", 9, 200, -1);
                Fonts.roboto_13.drawStringWithShadow("[" + mc.player.getName() + "]", 93 - Fonts.roboto_13.getStringWidth("[" + mc.player.getName() + "]"), 200, -1);
                Fonts.roboto_13.drawStringWithShadow("server", 9, 208, -1);
                Fonts.roboto_13.drawStringWithShadow("[" + server + "]", 93 - Fonts.roboto_13.getStringWidth("[" + server + "]"), 208, -1);
                Fonts.roboto_13.drawStringWithShadow("time", 9, 216, -1);
                Fonts.roboto_13.drawStringWithShadow("[" + time + "]", 93 - Fonts.roboto_13.getStringWidth("[" + time + "]"), 216, -1);
                Fonts.roboto_13.drawStringWithShadow("latency", 9, 224, -1);
                Fonts.roboto_13.drawStringWithShadow("[" + ping + "]", 93 - Fonts.roboto_13.getStringWidth("[" + ping + "]"), 224, -1);
        }
        if (watermark.equalsIgnoreCase("OnetapV2")) {
            String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
                String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());
                String text = "Rich Lite | " + name + " | " + server + " | " + mc.getDebugFPS() + " fps" + " | " + Objects.requireNonNull(mc.getConnection()).getPlayerInfo(mc.player.getUniqueID()).getResponseTime() + "ms" + " | " + time;
                Gui.drawRect(5, 6, Fonts.neverlose500_15.getStringWidth(text) + 9, 18, new Color(31, 31, 31, 255).getRGB());
                for(float l = 0; l < Fonts.neverlose500_15.getStringWidth(text) + 4; l += 1f) {
                    Gui.drawRect(5 + l, 5, l + 6, 6, DrawHelper.astolfoColors5(l - l + 15, l, 0.5f, 3f));
                }
                Gui.drawRect(5, 6f, Fonts.neverlose500_15.getStringWidth(text) + 9, 6.5f, new Color(20, 20, 20, 100).getRGB());
                Fonts.neverlose500_15.drawStringWithShadow(text, 7, 10.5, -1);
        }
        if (watermark.equalsIgnoreCase("Neverlose")) {

            String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
            String text = name + " | " + mc.getDebugFPS() + " fps" + " | " + server;
            String scam = "xydov | " + name + " | " + mc.getDebugFPS() + " fps" + " | " + server;
            DrawHelper.drawSmoothRect(5, 6, Fonts.neverlose500_15.getStringWidth(scam) + 12, 18, new Color(31, 31, 31, 255).getRGB());
            Fonts.neverlose500_16.drawStringWithShadow("xydov", 6.5, 10, new Color(25,125,255).getRGB());
            Fonts.neverlose500_16.drawStringWithShadow("xydov", 7, 10.5, -1);
            Fonts.neverlose500_15.drawStringWithShadow("| " + text, 4 + Fonts.neverlose500_15.getStringWidth("xydov | "), 10.5, -1);
        }

        if (watermark.equalsIgnoreCase("Akrien")) {
            DrawHelper.drawNewRect(5, 6, Fonts.sfui14.getStringWidth(Main.name) + 7, 15, new Color(25,125,255).getRGB());
            DrawHelper.drawNewRect(7, 6, Fonts.sfui14.getStringWidth(Main.name) + 10, 15, new Color(31, 31, 31).getRGB());
            Fonts.sfui14.drawStringWithShadow(Main.name, 8, 9, -1);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}
