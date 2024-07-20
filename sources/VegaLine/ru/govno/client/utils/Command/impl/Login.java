/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.security.SecureRandom;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.Session;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class Login
extends Command {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private static final String alphabet = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM1234567890";
    private static final SecureRandom secureRandom = new SecureRandom();
    static boolean run = false;
    static String nn = null;
    static String nn2 = null;

    public Login() {
        super("Login", new String[]{"login", "l", "connect", "con"});
    }

    public static String randomString(int strLength) {
        StringBuilder stringBuilder = new StringBuilder(strLength);
        for (int i = 0; i < strLength; ++i) {
            stringBuilder.append(alphabet.charAt(secureRandom.nextInt(alphabet.length())));
        }
        return stringBuilder.toString();
    }

    public static void doRct() {
        if (run && Minecraft.player != null && Login.mc.world != null && Minecraft.player.connection != null && mc.getSession() != null) {
            if (nn != null) {
                Login.mc.session = new Session(nn, "", "", "mojang");
            }
            for (int i = 0; i < 60; ++i) {
                System.out.println(nn);
            }
            if (!mc.isSingleplayer() || nn2 != null) {
                if (nn2 != null) {
                    GuiDisconnected.lastServer = nn2;
                }
                boolean flag = mc.isIntegratedServerRunning();
                boolean flag1 = mc.isConnectedToRealms();
                Login.mc.world.sendQuittingDisconnectingPacket();
                if (flag) {
                    mc.displayGuiScreen(new GuiMainMenu());
                } else if (flag1) {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(new GuiMainMenu());
                } else {
                    mc.displayGuiScreen(new GuiMainMenu());
                }
                if (nn2 != null) {
                    GuiDisconnected.does = true;
                }
            }
            run = false;
        }
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[0].equalsIgnoreCase("login") || args[0].equalsIgnoreCase("l")) {
                if (!mc.isSingleplayer() && args[1] != null && !args[1].isEmpty()) {
                    String nickname = Login.mc.session.getUsername();
                    if (args[1].equalsIgnoreCase("random") || args[1].equalsIgnoreCase("r")) {
                        nickname = Client.randomNickname();
                    } else if (args[1].length() <= 16 && args[1].length() >= 3) {
                        nickname = args[1];
                    }
                    nn = nickname;
                    nn2 = Login.mc.getCurrentServerData().serverIP;
                    run = true;
                    Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77\u0432\u044b \u0437\u0430\u0445\u043e\u0434\u0438\u0442\u0435 \u043f\u043e\u0434 \u043d\u0438\u043a\u043e\u043c \u00a7b" + nickname, false);
                    return;
                }
                String warn = args[1] == null || args[1].isEmpty() ? "\u0443\u043a\u0430\u0436\u0438\u0442\u0435 \u043d\u0438\u043a." : (args[1].length() > 16 ? "\u0432\u0430\u0448 \u043d\u0438\u043a \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u0434\u043b\u0438\u043d\u043d\u044b\u0439." : (args[1].length() < 3 ? "\u0432\u0430\u0448 \u043d\u0438\u043a \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u043a\u043e\u0440\u043e\u0442\u043a\u0438\u0439." : (mc.isSingleplayer() ? "\u0432\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u043c\u0435\u043d\u044f\u0442\u044c \u043d\u0438\u043a \u0432 \u043e\u0434\u0438\u043d\u043e\u0447\u043d\u043e\u0439 \u0438\u0433\u0440\u0435." : "\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.")));
                Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77" + warn, false);
            } else if (args[0].equalsIgnoreCase("connect") || args[0].equalsIgnoreCase("con")) {
                if (!args[1].isEmpty()) {
                    nn2 = args[1];
                    nn = null;
                    String nickname = Login.mc.session.getUsername();
                    if (args.length == 3 && !args[2].isEmpty()) {
                        if (!mc.isSingleplayer()) {
                            nickname = args[2].equalsIgnoreCase("random") ? Client.randomNickname() : args[2];
                            nn = nickname;
                            run = true;
                            return;
                        }
                        String warn = args[2].length() > 16 ? "\u0432\u0430\u0448 \u043d\u0438\u043a \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u0434\u043b\u0438\u043d\u043d\u044b\u0439." : (args[2].length() < 3 ? "\u0432\u0430\u0448 \u043d\u0438\u043a \u0441\u043b\u0438\u0448\u043a\u043e\u043c \u043a\u043e\u0440\u043e\u0442\u043a\u0438\u0439." : (mc.isSingleplayer() ? "\u0432\u044b \u043d\u0435 \u043c\u043e\u0436\u0435\u0442\u0435 \u043c\u0435\u043d\u044f\u0442\u044c \u043d\u0438\u043a \u0432 \u043e\u0434\u0438\u043d\u043e\u0447\u043d\u043e\u0439 \u0438\u0433\u0440\u0435." : "\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e."));
                        Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77" + warn, false);
                        return;
                    }
                    run = true;
                    Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77\u0437\u0430\u0445\u043e\u0436\u0443 \u043d\u0430 ip " + args[1], false);
                    return;
                }
                Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
                Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77login: login/l [\u00a7lNAME / random\u00a7r\u00a77]", false);
                Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77connect: connect/con [\u00a7lIP / IP + NAME\u00a7r\u00a77]", false);
            }
        } catch (Exception formatException) {
            Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77login: login/l [\u00a7lNAME / random\u00a7r\u00a77]", false);
            Client.msg("\u00a77\u00a7lLogin:\u00a7r \u00a77connect: connect/con [\u00a7lIP / IP + NAME\u00a7r\u00a77]", false);
            formatException.printStackTrace();
        }
    }
}

