/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Command.impl;

import ru.govno.client.Client;
import ru.govno.client.utils.Command.Command;

public class GetCommands
extends Command {
    public GetCommands() {
        super("GetCommands", new String[]{"gc", "getcom", "getcommands"});
    }

    @Override
    public void onCommand(String[] args) {
        try {
            Client.moduleManager.getModule("STabMonitor").toggleSilent(true);
        } catch (Exception formatException) {
            Client.msg("\u00a75\u00a7lGetCommands:\u00a7r \u00a77\u041a\u043e\u043c\u043c\u0430\u043d\u0434\u0430 \u043d\u0430\u043f\u0438\u0441\u0430\u043d\u0430 \u043d\u0435\u0432\u0435\u0440\u043d\u043e.", false);
            Client.msg("\u00a75\u00a7lGetCommands:\u00a7r \u00a77use: getcommands/getcom/gc", false);
            formatException.printStackTrace();
        }
    }
}

