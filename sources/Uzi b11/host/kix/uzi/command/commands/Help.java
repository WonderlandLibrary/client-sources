package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.utilities.minecraft.Logger;
import net.minecraft.client.Minecraft;

import org.apache.commons.lang3.text.WordUtils;

/**
 * Created by myche on 4/11/2017.
 */
public class Help extends Command {

    public Help() {
        super("Help", "Allows the user to grab all of the useable commands but hides secret ones ^.^", "help", "commands");
    }

    @Override
    public void dispatch(String message) {
        Uzi.getInstance().getCommandManager().getContents().forEach(command ->{
            Logger.logToChat(WordUtils.capitalizeFully(command.getLabel()) + " \2478- \2477" + command.getDescription());
        });
    }
}
