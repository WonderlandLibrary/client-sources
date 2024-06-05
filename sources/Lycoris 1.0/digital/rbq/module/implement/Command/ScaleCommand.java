package digital.rbq.module.implement.Command;

import digital.rbq.module.Command;
import digital.rbq.utility.PlayerUtils;

@Command.Info(name = "scale", syntax = { "" }, help = "Scale your gui.")
public class ScaleCommand extends Command {
    @Override
    public void execute(String[] args) throws Error {
        try {
            mc.gameSettings.guiScale = Float.parseFloat(args[0]);
        } catch (Exception e) {
            PlayerUtils.tellPlayer("Wrong GUI Scale Value!");
        }
    }
}
