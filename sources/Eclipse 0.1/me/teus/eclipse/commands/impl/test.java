package me.teus.eclipse.commands.impl;

import me.teus.eclipse.Client;
import me.teus.eclipse.commands.Command;

public class test extends Command {
    public test() {
        super("Test", "Testio", "test", "test");
    }

    @Override
    public void onCommand(String[] args, String command) {
        Client.getInstance().addChatMessage("Worked");
    }
}
