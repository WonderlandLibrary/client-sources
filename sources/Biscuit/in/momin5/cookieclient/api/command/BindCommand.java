package in.momin5.cookieclient.api.command;

import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.module.ModuleManager;
import in.momin5.cookieclient.api.util.utils.player.MessageUtil;
import me.yagel15637.venture.command.AbstractCommand;
import me.yagel15637.venture.exceptions.VentureException;
import org.lwjgl.input.Keyboard;

public class BindCommand extends AbstractCommand {

    public BindCommand(){
        super("binds a key to the module","bind/b <module name> <key>","bind","b","key");
    }


    @Override
    public void execute(String[] args) throws VentureException {

        String main = args[0];
        String value = args[1].toUpperCase();

        for (Module module : ModuleManager.getModules()) {
            if (module.getName().equalsIgnoreCase(main)) {
                if (value.equalsIgnoreCase("none")) {
                    module.setBind(Keyboard.KEY_NONE);
                    MessageUtil.sendClientPrefixMessage(module.getName() + " binded to " + value);
                } else if (value.length() == 1) {
                    int key = Keyboard.getKeyIndex(value);
                    module.setBind(key);
                    MessageUtil.sendClientPrefixMessage(module.getName() + " binded to " + value);
                } else if (value.length() > 1) {
                    MessageUtil.sendClientPrefixMessage(this.getUsage());
                }
            }
        }
    }
}
