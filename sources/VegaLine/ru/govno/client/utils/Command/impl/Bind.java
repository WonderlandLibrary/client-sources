/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import java.util.List;
import java.util.stream.Collectors;
import org.lwjgl.input.Keyboard;
import ru.govno.client.Client;
import ru.govno.client.module.Module;
import ru.govno.client.utils.Command.Command;

public class Bind
extends Command {
    public Bind() {
        super("Bind", new String[]{"bind", "b", "bnd", "unbind", "unb"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            for (Module f : Client.moduleManager.getModuleList()) {
                if (args[1].equalsIgnoreCase("get") && f.getName().equalsIgnoreCase(args[2])) {
                    Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77[\u00a77\u00a7l" + f.getName() + "\u00a7r\u00a77] key: " + Keyboard.getKeyName(f.getBind()) + ".", false);
                    continue;
                }
                if (args[1].equalsIgnoreCase("see") || args[1].equalsIgnoreCase("list")) {
                    if (!args[0].equalsIgnoreCase("bind") && !args[0].equalsIgnoreCase("bnd") && !args[0].equalsIgnoreCase("b") || f.getBind() == 0) continue;
                    Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77[\u00a77\u00a7l" + f.getName() + "\u00a7r\u00a77] key: " + Keyboard.getKeyName(f.getBind()) + ".", false);
                    continue;
                }
                if (args[0].equalsIgnoreCase("bind") || args[0].equalsIgnoreCase("bnd") || args[0].equalsIgnoreCase("b")) {
                    if (!f.getName().equalsIgnoreCase(args[1])) continue;
                    if (args[2].equalsIgnoreCase("null") || args[2].equalsIgnoreCase("none") || args[2].equalsIgnoreCase("0")) {
                        if (f.getBind() == 0) {
                            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041c\u043e\u0434\u0443\u043b\u044c [\u00a77\u00a7l" + f.getName() + "\u00a7r\u00a77] \u0443\u0436\u0435 \u0440\u0430\u0437\u0431\u0438\u043d\u0436\u0435\u043d.", false);
                        } else {
                            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041c\u043e\u0434\u0443\u043b\u044c [\u00a77\u00a7l" + f.getName() + "\u00a7r\u00a77] \u0431\u044b\u043b \u0440\u0430\u0437\u0431\u0438\u043d\u0436\u0435\u043d.", false);
                            f.setBind(0);
                        }
                    } else if (f.getBind() == Keyboard.getKeyIndex(args[2].toUpperCase())) {
                        Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + f.getName() + "\u00a7r\u00a77] \u0443\u0436\u0435 \u0437\u0430\u0431\u0438\u043d\u0436\u0435\u043d \u043d\u0430 [\u00a7l" + args[2].toLowerCase() + "\u00a7r\u00a77].", false);
                    } else {
                        Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041c\u043e\u0434\u0443\u043b\u044c [\u00a7l" + f.getName() + "\u00a7r\u00a77] \u0431\u044b\u043b \u0437\u0430\u0431\u0438\u043d\u0436\u0435\u043d \u043d\u0430 [\u00a7l" + args[2].toLowerCase() + "\u00a7r\u00a77].", false);
                        f.setBind(Keyboard.getKeyIndex(args[2].toUpperCase()));
                    }
                    return;
                }
                if (!args[0].equalsIgnoreCase("unbind") && !args[0].equalsIgnoreCase("unb")) continue;
                if (args[1].equalsIgnoreCase("all")) {
                    List<Module> toUnbind = Client.moduleManager.getModuleList().stream().filter(m -> m.getBind() != 0).collect(Collectors.toList());
                    if (toUnbind.isEmpty()) {
                        Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u0412\u0441\u0435 \u043c\u043e\u0434\u0443\u043b\u0438 \u0443\u0436\u0435 \u0431\u044b\u043b\u0438 \u0440\u0430\u0437\u0431\u0438\u043d\u0436\u0435\u043d\u044b.", false);
                    } else {
                        Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u0412\u0441\u0435 \u043c\u043e\u0434\u0443\u043b\u0438 (" + toUnbind.size() + "\u0448\u0442) \u0431\u044b\u043b\u0438 \u0440\u0430\u0437\u0431\u0438\u043d\u0436\u0435\u043d\u044b.", false);
                        toUnbind.forEach(m -> m.setBind(0));
                    }
                    return;
                }
                if (!f.getName().equalsIgnoreCase(args[1])) continue;
                if (f.getBind() == 0) {
                    Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041c\u043e\u0434\u0443\u043b\u044c [\u00a77\u00a7l" + f.getName() + "\u00a7r\u00a77] \u0443\u0436\u0435 \u0440\u0430\u0437\u0431\u0438\u043d\u0436\u0435\u043d.", false);
                } else {
                    Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041c\u043e\u0434\u0443\u043b\u044c [\u00a77\u00a7l" + f.getName() + "\u00a7r\u00a77] \u0431\u044b\u043b \u0440\u0430\u0437\u0431\u0438\u043d\u0436\u0435\u043d.", false);
                    f.setBind(0);
                }
                return;
            }
        } catch (Exception formatException) {
            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77bind: bind/bnd/b [\u00a7lname\u00a7r\u00a77] [\u00a7lkey\u00a7r\u00a77]", false);
            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77unbind: unbind/unbnd [\u00a7lname\u00a7r\u00a77 | \u00a7lall\u00a7r\u00a77]", false);
            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77show binds: list/see", false);
            Client.msg("\u00a7b\u00a7lBinds:\u00a7r \u00a77get bind: get [\u00a7lname\u00a7r\u00a77]", false);
        }
    }
}

