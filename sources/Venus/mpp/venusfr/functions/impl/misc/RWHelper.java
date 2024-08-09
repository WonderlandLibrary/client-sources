/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.misc;

import com.google.common.eventbus.Subscribe;
import java.awt.Image;
import java.awt.SystemTray;
import java.awt.Toolkit;
import java.awt.TrayIcon;
import java.util.UUID;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.utils.math.StopWatch;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.IServerPlayNetHandler;
import net.minecraft.network.play.client.CChatMessagePacket;
import net.minecraft.network.play.server.SOpenWindowPacket;
import net.minecraft.network.play.server.SRespawnPacket;
import net.minecraft.network.play.server.SUpdateBossInfoPacket;
import net.minecraft.util.text.TextFormatting;

@FunctionRegister(name="RWHelper", type=Category.Misc)
public class RWHelper
extends Function {
    boolean joined;
    StopWatch stopWatch = new StopWatch();
    private final ModeListSetting s = new ModeListSetting("\u0424\u0443\u043d\u043a\u0446\u0438\u0438", new BooleanSetting("\u0411\u043b\u043e\u043a\u0438\u0440\u043e\u0432\u0430\u0442\u044c \u0437\u0430\u043f\u0440\u0435\u0449\u0435\u043d\u043d\u044b\u0435 \u0441\u043b\u043e\u0432\u0430", true), new BooleanSetting("\u0417\u0430\u043a\u0440\u044b\u0432\u0430\u0442\u044c \u043c\u0435\u043d\u044e", true), new BooleanSetting("\u0410\u0432\u0442\u043e \u0442\u043e\u0447\u043a\u0430", true), new BooleanSetting("\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u044f", true));
    private UUID uuid;
    int x = -1;
    int z = -1;
    private TrayIcon trayIcon;
    String[] banWords = new String[]{"\u044d\u043a\u0441\u043f\u0430", "\u044d\u043a\u0441\u043f\u0435\u043d\u0441\u0438\u0432", "\u044d\u043a\u0441\u043f\u043e\u0439", "\u043d\u0443\u0440\u0438\u043a\u043e\u043c", "\u0446\u0435\u043b\u043a\u043e\u0439", "\u043d\u0443\u0440\u043b\u0430\u043d", "\u043d\u0443\u0440\u0441\u0443\u043b\u0442\u0430\u043d", "\u0446\u0435\u043b\u0435\u0441\u0442\u0438\u0430\u043b", "\u0446\u0435\u043b\u043a\u0430", "\u043d\u0443\u0440\u0438\u043a", "\u0430\u0442\u0435\u0440\u043d\u043e\u0441", "expa", "celka", "nurik", "venusfr", "celestial", "nursultan", "\u0444\u0430\u043d\u043f\u0435\u0439", "funpay", "fluger", "\u0430\u043a\u0440\u0438\u0435\u043d", "akrien", "\u0444\u0430\u043d\u0442\u0430\u0439\u043c", "ft", "funtime", "\u0431\u0435\u0437\u043c\u0430\u043c\u043d\u044b\u0439", "rich", "\u0440\u0438\u0447", "\u0431\u0435\u0437 \u043c\u0430\u043c\u043d\u044b\u0439", "wild", "\u0432\u0438\u043b\u0434", "excellent", "\u044d\u043a\u0441\u0435\u043b\u043b\u0435\u043d\u0442", "hvh", "\u0445\u0432\u0445", "matix", "impact", "\u043c\u0430\u0442\u0438\u043a\u0441", "\u0438\u043c\u043f\u0430\u043a\u0442", "wurst"};

    public RWHelper() {
        this.addSettings(this.s);
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        IPacket<IServerPlayNetHandler> iPacket;
        IPacket<?> iPacket2;
        if (eventPacket.isSend() && (iPacket2 = eventPacket.getPacket()) instanceof CChatMessagePacket) {
            iPacket = (CChatMessagePacket)iPacket2;
            boolean bl = false;
            for (String string : this.banWords) {
                if (!((CChatMessagePacket)iPacket).getMessage().toLowerCase().contains(string)) continue;
                bl = true;
                break;
            }
            if (bl) {
                this.print("RW Helper |" + TextFormatting.RED + " \u041e\u0431\u043d\u0430\u0440\u0443\u0436\u0435\u043d\u044b \u0437\u0430\u043f\u0440\u0435\u0449\u0435\u043d\u043d\u044b\u0435 \u0441\u043b\u043e\u0432\u0430 \u0432 \u0432\u0430\u0448\u0435\u043c \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0438. \u041e\u0442\u043f\u0440\u0430\u0432\u043a\u0430 \u043e\u0442\u043c\u0435\u043d\u0435\u043d\u0430, \u0447\u0442\u043e\u0431\u044b \u0438\u0437\u0431\u0435\u0436\u0430\u0442\u044c \u0431\u0430\u043d\u0430 \u043d\u0430 ReallyWorld.");
                eventPacket.cancel();
            }
        }
        if (eventPacket.isReceive()) {
            iPacket2 = eventPacket.getPacket();
            if (iPacket2 instanceof SUpdateBossInfoPacket) {
                iPacket = (SUpdateBossInfoPacket)iPacket2;
                if (((Boolean)this.s.getValueByName("\u0410\u0432\u0442\u043e \u0442\u043e\u0447\u043a\u0430").get()).booleanValue()) {
                    this.updateBossInfo((SUpdateBossInfoPacket)iPacket);
                }
            }
            if (((Boolean)this.s.getValueByName("\u0417\u0430\u043a\u0440\u044b\u0432\u0430\u0442\u044c \u043c\u0435\u043d\u044e").get()).booleanValue()) {
                iPacket2 = eventPacket.getPacket();
                if (iPacket2 instanceof SRespawnPacket) {
                    iPacket = (SRespawnPacket)iPacket2;
                    this.joined = true;
                    this.stopWatch.reset();
                }
                if ((iPacket2 = eventPacket.getPacket()) instanceof SOpenWindowPacket && ((SOpenWindowPacket)(iPacket = (SOpenWindowPacket)iPacket2)).getTitle().getString().contains("\u041c\u0435\u043d\u044e") && this.joined && !this.stopWatch.isReached(2000L)) {
                    RWHelper.mc.player.closeScreen();
                    eventPacket.cancel();
                    this.joined = false;
                }
            }
        }
    }

    public void updateBossInfo(SUpdateBossInfoPacket sUpdateBossInfoPacket) {
        if (sUpdateBossInfoPacket.getOperation() == SUpdateBossInfoPacket.Operation.ADD) {
            String string = sUpdateBossInfoPacket.getName().getString().toLowerCase().replaceAll("\\s+", " ");
            if (string.contains("\u0430\u0438\u0440\u0434\u0440\u043e\u043f")) {
                this.parseAirDrop(string);
                this.uuid = sUpdateBossInfoPacket.getUniqueId();
            } else if (string.contains("\u0442\u0430\u043b\u0438\u0441\u043c\u0430\u043d")) {
                this.parseMascot(string);
                this.uuid = sUpdateBossInfoPacket.getUniqueId();
            } else if (string.contains("\u0441\u043a\u0440\u0443\u0434\u0436")) {
                this.parseScrooge(string);
                this.uuid = sUpdateBossInfoPacket.getUniqueId();
            }
        } else if (sUpdateBossInfoPacket.getOperation() == SUpdateBossInfoPacket.Operation.REMOVE && sUpdateBossInfoPacket.getUniqueId().equals(this.uuid)) {
            this.resetCoordinatesAndRemoveWaypoints();
        }
    }

    private void parseAirDrop(String string) {
        this.x = RWHelper.extractCoordinate(string, "x: ");
        this.z = RWHelper.extractCoordinate(string, "z: ");
        if (((Boolean)this.s.getValueByName("\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u0435").get()).booleanValue()) {
            this.windows("RWHelper", "\u041f\u043e\u044f\u0432\u0438\u043b\u0441\u044f \u0430\u0438\u0440\u0434\u0440\u043e\u043f!", true);
        }
        RWHelper.mc.player.sendChatMessage(".gps add \u0410\u0438\u0440\u0414\u0440\u043e\u043f " + this.x + " 100 " + this.z);
    }

    private void parseMascot(String string) {
        String[] stringArray = string.split("\\s+");
        for (int i = 0; i < stringArray.length; ++i) {
            if (!RWHelper.isInteger(stringArray[i]) || i + 1 >= stringArray.length || !RWHelper.isInteger(stringArray[i + 1])) continue;
            this.x = Integer.parseInt(stringArray[i]);
            this.z = Integer.parseInt(stringArray[i + 1]);
            if (((Boolean)this.s.getValueByName("\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u0435").get()).booleanValue()) {
                this.windows("RWHelper", "\u041f\u043e\u044f\u0432\u0438\u043b\u0441\u044f \u0442\u0430\u043b\u0438\u0441\u043c\u0430\u043d!", true);
            }
            RWHelper.mc.player.sendChatMessage(".gps add \u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d " + this.x + " 100 " + this.z);
        }
    }

    private void parseScrooge(String string) {
        int n = string.indexOf("\u041a\u043e\u043e\u0440\u0434\u0438\u043d\u0430\u0442\u044b");
        if (n == -1) {
            return;
        }
        String string2 = string.substring(n + 10).trim();
        String[] stringArray = string2.split("\\s+");
        if (stringArray.length >= 2) {
            this.x = Integer.parseInt(stringArray[0]);
            this.z = Integer.parseInt(stringArray[5]);
            if (((Boolean)this.s.getValueByName("\u0423\u0432\u0435\u0434\u043e\u043c\u043b\u0435\u043d\u0438\u0435").get()).booleanValue()) {
                this.windows("RWHelper", "\u041f\u043e\u044f\u0432\u0438\u043b\u0441\u044f \u0441\u043a\u0440\u0443\u0434\u0436!", true);
            }
            RWHelper.mc.player.sendChatMessage(".gps add \u0421\u043a\u0440\u0443\u0434\u0436 " + this.x + " 100 " + this.z);
        }
    }

    private void resetCoordinatesAndRemoveWaypoints() {
        this.x = 0;
        this.z = 0;
        RWHelper.mc.player.sendChatMessage(".gps remove \u0410\u0438\u0440\u0414\u0440\u043e\u043f");
        RWHelper.mc.player.sendChatMessage(".gps remove \u0422\u0430\u043b\u0438\u0441\u043c\u0430\u043d");
        RWHelper.mc.player.sendChatMessage(".gps remove \u0421\u043a\u0440\u0443\u0434\u0436");
    }

    private static boolean isInteger(String string) {
        try {
            Integer.parseInt(string);
            return true;
        } catch (NumberFormatException numberFormatException) {
            return true;
        }
    }

    private static int extractCoordinate(String string, String string2) {
        int n = string.indexOf(string2);
        if (n != -1) {
            int n2 = n + string2.length();
            int n3 = string.indexOf(" ", n2);
            if (n3 == -1) {
                n3 = string.length();
            }
            String string3 = string.substring(n2, n3);
            return Integer.parseInt(string3.trim());
        }
        return 1;
    }

    private void windows(String string, String string2, boolean bl) {
        this.print(string2);
        if (SystemTray.isSupported()) {
            try {
                if (this.trayIcon == null) {
                    SystemTray systemTray = SystemTray.getSystemTray();
                    Image image = Toolkit.getDefaultToolkit().createImage("");
                    this.trayIcon = new TrayIcon(image, "Baritone");
                    this.trayIcon.setImageAutoSize(false);
                    this.trayIcon.setToolTip(string);
                    systemTray.add(this.trayIcon);
                }
                this.trayIcon.displayMessage(string, string2, bl ? TrayIcon.MessageType.ERROR : TrayIcon.MessageType.INFO);
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }
}

