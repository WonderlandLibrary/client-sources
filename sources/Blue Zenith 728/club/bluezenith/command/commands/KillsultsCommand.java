package club.bluezenith.command.commands;

import club.bluezenith.command.Command;
import club.bluezenith.module.modules.fun.KillSults;

import java.util.List;

public class KillsultsCommand extends Command {

    public KillsultsCommand() {
        super("Killsults", "Manage your killsults.", ".killsults | add | list | remove | refresh", "ks");
    }

    @Override
    public void execute(String[] args) {
        if(args.length == 1) {
            chat("Invalid usage! Syntax: " + syntax);
            return;
        }
        final List<String> the = KillSults.insults;
        if(args.length == 2) {
            switch (args[1].toLowerCase()) {
                case "list":
                    if(the.isEmpty()) {
                        chat("Killsults are empty!");
                        return;
                    }
                    chat("[index] All killsults:");
                    int i = 0;
                    for(String a : the) {
                        chat(String.format("[%s] \"%s\"", i++, a));
                    }
                    break;

                case "refresh":
                    new KillSults().deserialize();
                    chat("Refreshed killsults!");
                    break;

                case "clear":
                    the.clear();
                    chat("Removed all killsults");
                    break;
            }
        }

        if(args.length < 3) return;
        switch (args[1].toLowerCase()) {
            case "remove":
                try {
                    final int index = Integer.parseInt(args[2]);
                    if(index >= the.size()) throw new NumberFormatException();
                    the.remove(index);
                    chat("Removed killsult #" + index);
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
                chat("Added killsult: " + builder);
                the.add(builder.toString().trim());
                break;
        }
    }
}
