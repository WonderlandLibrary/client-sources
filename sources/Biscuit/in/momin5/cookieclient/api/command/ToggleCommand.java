package in.momin5.cookieclient.api.command;

import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.util.utils.player.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;
import me.yagel15637.venture.exceptions.VentureException;

public class ToggleCommand extends AbstractCommand {
    public ToggleCommand(){
        super("toggles a module","toggle <module name>","on","off","toggle");
    }


    @Override
    public void execute(String[] args) throws VentureException {
        String main = args[0];

        Module module = ModuleManager.getModule(main);

        if (module == null) {
            MessageUtil.sendClientPrefixMessage(this.getUsage());
            return;
        }

        module.toggle();

        if (module.isEnabled()) {
            MessageUtil.sendClientPrefixMessage(module.getName() + " set to: ENABLED!");
        } else {
            MessageUtil.sendClientPrefixMessage(module.getName() + " set to: DISABLED!");
        }
    }
}
