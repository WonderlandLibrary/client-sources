package net.silentclient.client.event.impl;

import net.silentclient.client.event.EventCancelable;

public class RunCommandEvent extends EventCancelable {
    private final String command;

    public RunCommandEvent(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }
}
