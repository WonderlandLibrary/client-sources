package white.floor.features.impl.display;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import org.lwjgl.input.Keyboard;

import clickgui.setting.Setting;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventRender2D;
import white.floor.features.Category;
import white.floor.features.Feature;
import white.floor.font.Fonts;
import white.floor.helpers.DrawHelper;
import white.floor.helpers.notifications.NotificationPublisher;
import white.floor.helpers.notifications.NotificationType;

public class SessionInfo extends Feature {

    public SessionInfo() {
        super("SessionInfo", "Two Scoreboard",Keyboard.KEY_NONE, Category.DISPLAY);
    }

    @EventTarget
    public void ebatkopat(EventRender2D render) {

        String theme = Main.settingsManager.getSettingByName(Main.featureDirector.getModule(ClickGUI.class), "Theme Mode").getValString();

        String server = mc.isSingleplayer() ? "local" : mc.getCurrentServerData().serverIP.toLowerCase();
        String name = "Name: " + mc.player.getName();

       // if (Main.moduleManager.getModule(NameProtect.class).isToggled())
         //   name = "Name: Protected";

            int color = -1;
            int colortext = -1;
            int colortext1 = -1;

            if(theme.equalsIgnoreCase("White")) {
                color = new Color(60, 61, 60).getRGB();
                colortext = new Color(245, 245, 245, 215).getRGB();
                colortext1 = new Color(245, 245, 245, 145).getRGB();
            } else if(theme.equalsIgnoreCase("Dark")) {
                color = -1;
                colortext = new Color(33, 33, 33, 215).getRGB();
                colortext1 = new Color(33, 33, 33, 145).getRGB();
            } else if(theme.equalsIgnoreCase("Blue")) {
                color = -1;
                colortext = new Color(9, 144, 255, 215).getRGB();
                colortext1 = new Color(60, 135, 250, 145).getRGB();
            }

        double prevZ = mc.player.posZ - mc.player.prevPosZ;
            double prevX = mc.player.posX - mc.player.prevPosX;
            double lastDist = Math.sqrt(prevX * prevX + prevZ * prevZ);
            double currSpeed = lastDist * 15.3571428571D;

            float str1 = Fonts.sfui16.getStringWidth(name);
            float str2 = Fonts.sfui16.getStringWidth("IP: " + server);
            String time = (new SimpleDateFormat("HH:mm:ss")).format(Calendar.getInstance().getTime());

            DrawHelper.drawNewRect(5, 50, (str1 > str2 ? str1 : str2) + 45, 103, colortext1);
            DrawHelper.drawNewRect(5, 42, (str1 > str2 ? str1 : str2) + 45, 51, colortext);
            DrawHelper.drawGradientRect(5, 51, (str1 > str2 ? str1 : str2) + 45, 53, new Color(1, 1, 1, 90).getRGB(), new Color(0,0,0,0).getRGB());



            String speed = String.format("%.1f bps", currSpeed);

            Fonts.sfui14.drawString("Session Info", 7, 45, color);
            Fonts.elegant_20.drawString("}", 7.5, 57, color);
            Fonts.sfui15.drawString("Time: " + time, 20, 58, color);
            Fonts.icons_20.drawString("b", 7.5, 69, color);
            Fonts.sfui15.drawString("Move Speed: " + speed, 20, 69, color);
            Fonts.sfui15.drawString(name, 20, 80.5, color);
            Fonts.icons_20.drawString("c", 7.5, 80, color);
            Fonts.sfui15.drawString("IP: " + server, 20, 93, color);
            Fonts.elegant_20.drawString("b", 7.5, 92, color);
            Fonts.elegant_18.drawString("M", (str1 > str2 ? str1 : str2) + 35.5, 44, color);

        }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    public void onDisable() {
        super.onDisable();
    }
}