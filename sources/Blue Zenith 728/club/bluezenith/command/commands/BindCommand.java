package club.bluezenith.command.commands;

import club.bluezenith.command.Command;
import club.bluezenith.module.Module;
import club.bluezenith.module.modules.render.ClickGUI;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
import java.util.Locale;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static org.lwjgl.input.Keyboard.getKeyName;

@SuppressWarnings("unused")
public class BindCommand extends Command {
    public BindCommand() {
        super("Bind", "Bind a module.",".bind module key | .bind key module", "b", "binds");
    }
    @Override
    public void execute(String[] args){

        if(args[0].equalsIgnoreCase("binds")) {
            chat("List of all set binds:");
            getBlueZenith().getModuleManager().getModules().forEach(mod -> {
                if(mod.keyBind > 11){
                    chat(mod.getName() + ": " + EnumChatFormatting.WHITE + getKeyName(mod.keyBind).toLowerCase());
                }
            });
            return;
        }
        if(args.length == 2) {
            switch(args[1].toLowerCase()) {
                case "list":
                    chat("List of all set binds:");
                    getBlueZenith().getModuleManager().getModules().stream().sorted(Comparator.comparing(mod -> getKeyName(mod.keyBind)))
                            .filter(mod -> mod.keyBind > 0)
                            .forEach(mod -> chat(mod.getName() + ": " + getKeyName(mod.keyBind).toLowerCase()));
                break;

                case "reset":
                    getBlueZenith().getModuleManager().getModules().forEach(mod -> {
                       if(mod instanceof ClickGUI) return;
                       mod.keyBind = 0;
                    });
                    getBlueZenith().getNotificationPublisher().postInfo("Binds", "Reset binds for all modules", 2000);
                break;
            }
            return;
        }

        if(args.length > 2){
            Module m = getBlueZenith().getModuleManager().getModule(args[1]);
            if(m == null){
                Module m1 = getBlueZenith().getModuleManager().getModule(args[2]);
                checkKey(args[1], args[2]);
                if(m1 == null) {
                    checkKey(args[2], args[1]);
                } else {
                    int key = Keyboard.getKeyIndex(args[1].toUpperCase());
                    if(key == 0) {
                        getBlueZenith().getNotificationPublisher().postError("Binds", "Invalid key specified: §7" + args[1], 2000);
                        //chat("Invalid key specified: " + args[1]);
                        bind(0, m1);
                        return;
                    }
                    bind(key, m1);
                }
                return;
            }
            int k = Keyboard.getKeyIndex(args[2].toUpperCase(Locale.ROOT));
            bind(k, m);
        }else {
            chat("Syntax: .bind <module> <key>");
        }
    }
    private void checkKey(String key, String modName) {
        if(Keyboard.getKeyIndex(key.toUpperCase()) == 0) {
            getBlueZenith().getNotificationPublisher().postError("Binds", "Couldn't find module §7" + modName, 2000);
        }
    }

    private void bind(int key, Module mod) {
        mod.keyBind = key;
        getBlueZenith().getNotificationPublisher().postInfo("Binds", "Bound " + mod.getName() + " to " + getKeyName(key), 2000);
    }
}
