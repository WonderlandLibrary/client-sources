package zamorozka.ui;

import org.lwjgl.input.Keyboard;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;

public class CmdBind extends Command {

    public CmdBind() {
        super("bind");
    }

    @Override
    public void runCommand(String s, String[] args) {
        try {
            if (args[0].equalsIgnoreCase("add")) {
                for (Module mod : ModuleManager.getModules()) {
                    if (mod.getName().replace(" ", "").equalsIgnoreCase(args[1])) {
                        if (Keyboard.getKeyIndex(args[2].toUpperCase()) == 0) {
                            ChatUtils.printChatprefix("Invalid key.");
                            return;
                        }
                        mod.setKey(Keyboard.getKeyIndex(args[2].toUpperCase()));
                        ChatUtils.printChatprefix(mod.getName() + " bound to: " + Keyboard.getKeyName(mod.getKey()));
                        FileManager.saveKeybinds();
                        break;
                    }
                }
            }

            if (args[0].equalsIgnoreCase("del")) {
                for (Module mod : ModuleManager.getModules()) {
                    if (mod.getKey() == Keyboard.getKeyIndex(args[1].toUpperCase())) {
                        mod.setKey(256);
                        ChatUtils.printChatprefix("Unbound: " + args[1].toUpperCase());
                        FileManager.saveKeybinds();
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            ChatUtils.printChatprefix("Usage: " + getSyntax());
        }
    }

    @Override
    public String getDescription() {
        return "Binds a key to a hack";
    }

    @Override
    public String getSyntax() {
        return "bind add <hack> <key>, bind del <key>";
    }
}