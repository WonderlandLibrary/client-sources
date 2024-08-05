package fr.dog.command.impl;

import fr.dog.command.Command;
import fr.dog.util.player.ChatUtil;

public class HelpCommand extends Command {

    private static final String[] COMMANDS = {
    "Bind [Bind KEY] ", "Config [Config List/Load]", "cs (Cs looking visual)", "setkey [Api key]", "Queue/Q [Bw1/Bw2/Bw3/Bw4/Sw_si/Sw_sn]", "Theme [Theme list/Load]", "Toggle/T [Toggle Module]", "ocfg/OnlineConfig [Ocfg Config Name]","ign [Copy your ign]","cn/customname (Example : cn Killaura SkillAura) [reset/list]"};

    public HelpCommand() {
        super("help", "h");
    }

    @Override
    public void execute(String[] args, String message) {
        StringBuilder helpMessage = new StringBuilder();
        helpMessage.append("Available commands:\n");
        for (String command : COMMANDS) {
            helpMessage.append("§f[Dog Client]§8 > §7").append(command).append("\n");
        }
        ChatUtil.display(helpMessage.toString());
    }
}
