package club.bluezenith.command.commands;

import club.bluezenith.BlueZenith;
import club.bluezenith.command.Command;
import club.bluezenith.util.client.ClientUtils;

public class IgnoreErrorsCommand extends Command {

    public IgnoreErrorsCommand() {
        super("IgnoreErrors", "Disables notifications about errors in modules.", ".ignoreerrors");
    }

    @Override
    public void execute(String[] args) {
        boolean ignore = BlueZenith.getBlueZenith().ignoreModuleErrors;
        ignore = !ignore;
        BlueZenith.getBlueZenith().ignoreModuleErrors = ignore;
        ClientUtils.fancyMessage((ignore ? "Disabled" : "Enabled") + " error notifications.");
    }
}
