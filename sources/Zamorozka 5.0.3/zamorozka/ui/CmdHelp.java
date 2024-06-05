package zamorozka.ui;

public class CmdHelp extends Command {
    public CmdHelp() {
        super("help");
    }

    @Override
    public void runCommand(String s, String[] args) {
        for (Command cmd : CommandManager.commands) {
            if (cmd != this) {
                ChatUtils.printChatprefix(cmd.getSyntax().replace("<", "<\247a").replace(">", "\247f>") + " - " + cmd.getDescription());

            }
        }
    }

    @Override
    public String getDescription() {
        return "��������� �������";
    }

    @Override
    public String getSyntax() {
        return "help";
    }
}