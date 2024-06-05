package digital.rbq.module.implement.Command;

import digital.rbq.Lycoris;
import digital.rbq.module.Command;

/**
 * Created by John on 2017/05/02.
 */
@Command.Info(name = "resetgui", syntax = { "" }, help = "Reset the position of all windows")
public class ResetGuiCmd extends Command {
    @Override
    public void execute(String[] args) throws Error {
        Lycoris.INSTANCE.getClickGUI().rePositionWindows();
    }
}
