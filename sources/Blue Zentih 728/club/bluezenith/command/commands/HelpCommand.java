package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;

import java.util.Arrays;
@SuppressWarnings("unused")
public final class HelpCommand extends Command {

    public HelpCommand() {
        super("Help", "Figure it out", "help (command name)");
    }

    @Override
    public void execute(String[] args) {
        if(args.length > 1) {
            for(Command c : BlueZenith.getBlueZenith().getCommandManager().commands) {
                if(c.name.equalsIgnoreCase(args[1])) {
                    chat("");
                    chat(c.name);
                    chat(c.description);
                    chat(c.syntax);
                    chat("Aliases: " + Arrays.toString(c.pref));
                    return;
                }
                for(String a : c.pref) {
                    if(a.equalsIgnoreCase(args[1])) {
                        chat("");
                        chat(c.name);
                        chat(c.description);
                        chat(c.syntax);
                        chat("Aliases: " + Arrays.toString(c.pref));
                        return;
                    }
                }
            }
            chat("Couldn't find that command!");
        } else
        BlueZenith.getBlueZenith().getCommandManager().commands.forEach(a -> { if(a.description.contains("Auto-generated")) return; chat(" ");chat(a.name + " : " + a.description); chat(a.syntax); });
    }
}
