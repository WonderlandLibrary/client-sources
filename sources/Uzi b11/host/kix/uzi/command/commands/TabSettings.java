package host.kix.uzi.command.commands;

import host.kix.uzi.command.Command;
import host.kix.uzi.ui.tab.TabGui;

/**
 * Created by myche on 4/22/2017.
 */
public class TabSettings extends Command {

    public TabSettings() {
        super("tabgui", "Allows you to change tabgui length and other settings", "tab", "tabui");
    }

    @Override
    public void dispatch(String message) {
        String[] args = message.split(" ");
        if (args[1].equalsIgnoreCase("length")) {
            int length = Integer.parseInt(args[2]);
            TabGui.length.setValue(length);
        }else if(args[1].equalsIgnoreCase("spacing")){
            TabGui.spacing.setValue(Integer.parseInt(args[2]));
        }
    }
}
