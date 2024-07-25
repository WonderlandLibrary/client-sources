package club.bluezenith.command.commands;

import club.bluezenith.command.Command;
import club.bluezenith.module.modules.misc.AntiSpam;

public class AntiSpamCommand extends Command {

    public AntiSpamCommand() {
        super("antispam", "Manage blacklisted messages.", ".antispam | add | list | remove | refresh | ignoreplayer | showignored | unignore", "as");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 1) {
            chat("Invalid usage! Syntax: " + syntax);
            return;
        }
        if(args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "list":
                    if(AntiSpam.blacklistedMessages.isEmpty()) {
                        chat("Message list is empty!");
                        return;
                    }
                    chat("[index] All blacklisted messages:");
                    int i = 0;
                    for(String a : AntiSpam.blacklistedMessages) {
                        chat(String.format("[%s] \"%s\"", i++, a));
                    }
                    break;

                case "refresh":
                    AntiSpam.init();
                    chat("Refreshed blacklisted messages!");
                    break;

                case "clear":
                    AntiSpam.blacklistedMessages.clear();
                    chat("Removed all blacklisted messages");
                    break;

                case "showignored":
                    if(AntiSpam.ignoredPlayers.isEmpty()) {
                        chat("No ignored players!");
                        return;
                    }
                break;

                case "clearignored":
                    AntiSpam.ignoredPlayers.clear();
                    chat("Cleared ignored player list!");
                break;
            }
        }

        if(args.length < 3) return;
        switch (args[1].toLowerCase()) {
            case "remove":
                try {
                    final int index = Integer.parseInt(args[2]);
                    if(index >= AntiSpam.blacklistedMessages.size()) throw new NumberFormatException();
                    AntiSpam.blacklistedMessages.remove(index);
                    chat("Removed message #" + index);
                } catch (NumberFormatException exception) {
                    chat("Invalid index provided!");
                }
                break;

            case "add":
                final StringBuilder builder = new StringBuilder();
                int index = 0;
                for(String arg : args) {
                    if(++index <= 2) continue;
                    builder.append(arg).append(" ");
                }
                chat("Added message: " + builder);
                AntiSpam.blacklistedMessages.add(builder.toString().trim());
                break;

            case "ignoreplayer":
                chat("Ignoring player " + args[2]);
                AntiSpam.ignoredPlayers.add(args[2]);
            break;

            case "unignore":
                chat("Unignoring player " + args[2]);
                AntiSpam.ignoredPlayers.remove(args[2]);
            break;
        }
    }
}
