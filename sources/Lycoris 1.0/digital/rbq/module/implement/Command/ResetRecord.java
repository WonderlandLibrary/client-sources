package digital.rbq.module.implement.Command;

import digital.rbq.Lycoris;
import digital.rbq.module.Command;
import digital.rbq.utility.ChatUtils;


@Command.Info(name = "resetrecord", syntax = { "" }, help = "Reset All your Game records.(DiscordRPC)")
public class ResetRecord extends Command {
    @Override
    public void execute(String[] args) throws Error {
        Lycoris.INSTANCE.getModuleManager().killAuraMod.killed = 0;
        Lycoris.INSTANCE.getModuleManager().autoGGMod.win = 0;
        ChatUtils.sendMessageToPlayer("Your Game Record has been resat.");
    }
}
