/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client;

import java.util.ArrayList;
import me.thekirkayt.client.command.CommandManager;
import me.thekirkayt.client.friend.FriendManager;
import me.thekirkayt.client.gui.account.AccountScreen;
import me.thekirkayt.client.gui.click.ClickGui;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.module.modules.render.ESP;
import me.thekirkayt.client.notification.NotificationManager;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.client.threads.Killswitch;
import me.thekirkayt.client.threads.NewestVersion;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.MCStencil;
import net.minecraft.entity.player.EntityPlayer;

public final class Client {
    public static String clientName = "\u00a79\u00a7lK\u00a77\u00a7lirka \u00a79\u00a7lb\u00a77\u00a7l11\u00a79\u00a7l";
    public static final double VERSION = 2.4;
    public static AccountScreen accountScreen;
    private static final NotificationManager notManager;
    private static NewestVersion newestVersion;
    private static boolean latestVersion;
    private static boolean killswitch;
    private static ClickGui clickGui;
    public static ArrayList<EntityPlayer> isabotspawn;
    public static ArrayList<EntityPlayer> notabot;
    public static int timeongroundbot;
    public static ArrayList<String> timeonground;
    public static boolean secretfeatureson;
    public static Client clientInstance;

    static {
        notManager = new NotificationManager();
    }

    public static void start() {
        ESP.checkSetupFBO();
        ClientUtils.loadClientFont();
        ModuleManager.start();
        CommandManager.start();
        OptionManager.start();
        FriendManager.start();
        ClickGui.start();
        MCStencil.checkSetupFBO();
        new Killswitch().start();
        newestVersion = new NewestVersion();
        notManager.setup();
    }

    public static ClickGui getClickGui() {
        return clickGui;
    }

    public static void setClickGui(ClickGui clickGui) {
    }

    public static NotificationManager getNotificationManager() {
        return notManager;
    }

    public static boolean isKillswitched() {
        return killswitch;
    }

    public static boolean isLatestVersion() {
        return latestVersion;
    }

    public static void setLatestVersion(boolean latestVersion) {
        Client.latestVersion = latestVersion;
    }

    public static Client getInstance() {
        if (clientInstance == null) {
            clientInstance = new Client();
        }
        return clientInstance;
    }

    public static void setKillswitch(boolean killswitch) {
    }

    public static boolean isBot(EntityPlayer p) {
        return !notabot.contains(p) || p.ticksExisted < 140 || Client.getTimeOnGround(p) < (double)timeongroundbot;
    }

    public static double getTimeOnGround(EntityPlayer p) {
        String id = "" + p.getEntityId();
        for (String s : timeonground) {
            if (!s.contains(id)) continue;
            String[] split = s.split(":");
            double value = Double.parseDouble(split[1]);
            return value;
        }
        return 0.0;
    }

    public static void TimeOnGroundAdd(EntityPlayer p, double amount) {
        boolean set = false;
        String id = "" + p.getEntityId();
        for (String string : timeonground) {
        }
        for (String s : timeonground) {
            if (!s.contains(id)) continue;
            String[] split = s.split(":");
            double setto = Double.parseDouble(split[1]) + amount;
            timeonground.remove(s);
            timeonground.add(String.valueOf(String.valueOf(String.valueOf(id))) + ":" + setto);
            set = true;
            return;
        }
        if (!set) {
            timeonground.add(String.valueOf(String.valueOf(String.valueOf(id))) + ":" + amount);
        }
    }
}

