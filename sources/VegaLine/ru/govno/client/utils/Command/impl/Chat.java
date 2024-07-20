/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import net.minecraft.client.Minecraft;
import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class Chat
extends Command {
    public static List<String> blackListMassages = new CopyOnWriteArrayList<String>();

    public Chat() {
        super("Chat", new String[]{"ignore"});
    }

    Minecraft mc() {
        return Minecraft.getMinecraft();
    }

    public static boolean stringIsContainsBadMassage(String text) {
        if (blackListMassages.size() != 0) {
            boolean suspicious = false;
            for (String blms : blackListMassages) {
                if (!text.contains(blms)) continue;
                suspicious = !text.contains("Chat:");
            }
            if (suspicious) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void onCommand(String[] args) {
        try {
            if (args[1].equalsIgnoreCase("add") || args[1].equalsIgnoreCase("new")) {
                if (args[2] == null) {
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u043a\u043e\u043d\u0442\u0430\u0439\u043d \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u044f", false);
                } else {
                    boolean ujeEst = false;
                    if (blackListMassages.size() > 0) {
                        for (String blm : blackListMassages) {
                            if (!blm.equalsIgnoreCase(args[2])) continue;
                            ujeEst = true;
                        }
                    }
                    if (ujeEst) {
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u043a\u043e\u043d\u0442\u0430\u0439\u043d \u0443\u0436\u0435 \u0435\u0441\u0442\u044c \u0432 \u0441\u043f\u0438\u0441\u043a\u0435", false);
                    } else {
                        blackListMassages.add(args[2]);
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0434\u043e\u0431\u0430\u0432\u043b\u0435\u043d \u043d\u043e\u0432\u044b\u0439 \u043a\u043e\u043d\u0442\u0430\u0439\u043d \u0441 \u0441\u043e\u043e\u0431\u0449\u0435\u043d\u0438\u0435:", false);
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77" + args[2], false);
                    }
                }
            }
            if (args[1].equalsIgnoreCase("remove") || args[1].equalsIgnoreCase("del")) {
                if (args[2] == null) {
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0432\u0432\u0435\u0434\u0438\u0442\u0435 \u043a\u043e\u043d\u0442\u0430\u0439\u043d \u0441\u043f\u0438\u0441\u043a\u0430", false);
                } else if (blackListMassages.size() == 0) {
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0438\u0433\u043d\u043e\u0440\u043e\u0432 \u043f\u0443\u0441\u0442", false);
                } else {
                    String name = "unnamed blm";
                    for (String blm : blackListMassages) {
                        if (!blm.equalsIgnoreCase(args[2])) continue;
                        name = blm;
                    }
                    if (name.equalsIgnoreCase("unnamed blm")) {
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u044d\u0442\u043e\u0433\u043e \u043ao\u043d\u0442\u0430\u0439\u043d\u0430 \u043d\u0435\u0442 \u0432 \u0441\u043f\u0438\u0441\u043a\u0435", false);
                    } else {
                        blackListMassages.remove(name);
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u043a\u043e\u043d\u0442\u0430\u0439\u043d \u0431\u044b\u043b \u0443\u0434\u0430\u043b\u0451\u043d ->", false);
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77" + args[2], false);
                    }
                }
            }
            if (args[1].equalsIgnoreCase("list") || args[1].equalsIgnoreCase("see")) {
                if (blackListMassages.size() == 0) {
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0438\u0433\u043d\u043e\u0440\u043e\u0432 \u043f\u0443\u0441\u0442", false);
                } else {
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0438\u0433\u043d\u043e\u0440\u043e\u0432:", false);
                    int number = 0;
                    for (String blm : blackListMassages) {
                        Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u2116" + ++number + ": " + blm, false);
                    }
                }
            }
            if (args[1].equalsIgnoreCase("clear") || args[1].equalsIgnoreCase("ci")) {
                if (blackListMassages.size() == 0) {
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0438\u0433\u043d\u043e\u0440\u043e\u0432 \u043f\u0443\u0441\u0442", false);
                } else {
                    blackListMassages.clear();
                    Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u0441\u043f\u0438\u0441\u043e\u043a \u0438\u0433\u043d\u043e\u0440\u043e\u0432 o\u0447\u0438\u0449\u0435\u043d", false);
                }
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77use: ignore", false);
            Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77add: add/new [\u00a7lSTRING\u00a7r\u00a77]", false);
            Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77remove: remove/del [\u00a7lSTRING\u00a7r\u00a77]", false);
            Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77list: list/see", false);
            Client.msg("\u00a7e\u00a7lChat:\u00a7r \u00a77clear: clear/ci", false);
        }
    }
}

