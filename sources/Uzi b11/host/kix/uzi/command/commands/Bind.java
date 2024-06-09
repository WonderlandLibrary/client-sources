package host.kix.uzi.command.commands;

import host.kix.uzi.Uzi;
import host.kix.uzi.command.Command;
import host.kix.uzi.module.Module;
import host.kix.uzi.utilities.minecraft.Logger;
import org.lwjgl.input.Keyboard;

/**
 * Created by myche on 4/13/2017.
 */
public class Bind extends Command {

    public Bind() {
        super("Bind", "Allows the user to bind certain modules to certain keystrokes!", "b", "keybind", "macro");
    }

    @Override
    public void dispatch(String message) {
        String[] args = message.split(" ");
        Module module = Uzi.getInstance().getModuleManager().find(args[1]);
        int macro = Keyboard.getKeyIndex(args[2].toUpperCase());
        if(module != null){
            module.setBind(macro);
        }else{
            Logger.logToChat("Module is null!");
        }
    }
}
