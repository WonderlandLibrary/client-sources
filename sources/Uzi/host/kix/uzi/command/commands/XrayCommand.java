package host.kix.uzi.command.commands;

import host.kix.uzi.command.Command;
import host.kix.uzi.module.modules.render.Xray;
import host.kix.uzi.utilities.minecraft.Logger;
import net.minecraft.block.Block;

/**
 * Created by Kix on 5/30/2017.
 * Made for the eclipse project.
 */
public class XrayCommand extends Command {

    public XrayCommand() {
        super("Xray", "Allows the user to add selective blocks to the XrayCommand.", "x-ray", "xra", "ray");
    }

    @Override
    public void dispatch(String message) {
        String[] arguments = message.split(" ");
        Integer block = Integer.parseInt(arguments[1]);
        if (!Xray.getBlocks().contains(block)) {
            Xray.getBlocks().add(block);
            Logger.logToChat(Block.getBlockById(block).getLocalizedName() + " has been added to your Xray.");
        } else {
            Logger.logToChat("That block is already in your Xray, however we have removed it for you.");
        }
    }
}
