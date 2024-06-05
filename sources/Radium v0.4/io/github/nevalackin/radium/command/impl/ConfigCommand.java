package io.github.nevalackin.radium.command.impl;

import io.github.nevalackin.radium.RadiumClient;
import io.github.nevalackin.radium.command.Command;
import io.github.nevalackin.radium.command.CommandExecutionException;
import io.github.nevalackin.radium.config.Config;
import io.github.nevalackin.radium.notification.Notification;
import io.github.nevalackin.radium.notification.NotificationType;
import io.github.nevalackin.radium.utils.Wrapper;

public final class ConfigCommand extends Command {
    @Override
    public String[] getAliases() {
        return new String[]{"config", "c", "preset"};
    }

    @Override
    public void execute(String[] arguments) throws CommandExecutionException {
        if (arguments.length >= 2) {
            String upperCaseFunction = arguments[1].toUpperCase();

            if (arguments.length == 3) {
                switch (upperCaseFunction) {
                    case "LOAD":
                        if (RadiumClient.getInstance().getConfigHandler().loadConfig(arguments[2]))
                            success("loaded", arguments[2]);
                        else
                            fail("load", arguments[2]);
                        return;
                    case "SAVE":
                        if (RadiumClient.getInstance().getConfigHandler().saveConfig(arguments[2]))
                            success("saved", arguments[2]);
                        else
                            fail("save", arguments[2]);
                        return;
                    case "DELETE":
                        if (RadiumClient.getInstance().getConfigHandler().deleteConfig(arguments[2]))
                            success("deleted", arguments[2]);
                        else
                            fail("delete", arguments[2]);
                        return;
                }
            } else if (arguments.length == 2 && upperCaseFunction.equalsIgnoreCase("LIST")) {
                Wrapper.addChatMessage("Available Configs:");
                for (Config config : RadiumClient.getInstance().getConfigHandler().getElements())
                    Wrapper.addChatMessage(config.getName());
                return;
            }
        }

        throw new CommandExecutionException(getUsage());
    }

    private void success(String type, String configName) {
        RadiumClient.getInstance().getNotificationManager().add(new Notification(
                String.format("Successfully %s config: '%s'", type, configName), NotificationType.SUCCESS));
    }

    private void fail(String type, String configName) {
        RadiumClient.getInstance().getNotificationManager().add(new Notification(
                String.format("Failed to %s config: '%s'", type, configName), NotificationType.ERROR));
    }

    @Override
    public String getUsage() {
        return "config/c/preset <load/save/delete/list> <(optional)config>";
    }
}
