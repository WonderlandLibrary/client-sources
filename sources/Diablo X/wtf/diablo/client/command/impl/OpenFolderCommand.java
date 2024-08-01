package wtf.diablo.client.command.impl;

import wtf.diablo.client.command.api.AbstractCommand;
import wtf.diablo.client.command.api.data.Command;
import wtf.diablo.client.core.impl.Diablo;

@Command(
        name = "openfolder",
        description = "Opens the client folder",
        usage = "openfolder",
        aliases = {"of", "folder", "open"}
)
public final class OpenFolderCommand extends AbstractCommand {
    @Override
    public void execute(final String[] args) {
        try {
            Runtime.getRuntime().exec("explorer.exe " + Diablo.getInstance().getMainDirectory().getAbsolutePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
