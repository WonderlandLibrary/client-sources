package studio.dreamys.command;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.util.BlockPos;
import studio.dreamys.module.mines.CoordsGrabber;

public class Chw extends CommandBase {
    @Override
    public String getCommandName() {
        return "chw";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return null;
    }

    @Override
    public void processCommand(ICommandSender sender, String[] args) {
        if (args[0].equals("set")) {
            CoordsGrabber.coords.put(args[1], new BlockPos(Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4])));
        }

        if (args[0].equals("reset")) {
            CoordsGrabber.coords.clear();
        }

        if (args[0].equals("remove")) {
            CoordsGrabber.coords.keySet().removeIf(string -> string.contains(args[1]));
        }
    }

    @Override
    public boolean canCommandSenderUseCommand(ICommandSender sender) {
        return true;
    }
}
