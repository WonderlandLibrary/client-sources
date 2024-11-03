package dev.stephen.nexus.commands.impl;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.commands.Command;
import dev.stephen.nexus.module.Module;
import org.lwjgl.glfw.GLFW;

public class BindCommand extends Command {

    public BindCommand() {
        super("bind", new String[]{"<module>", "<key>"});
    }

    @Override
    public void execute(String[] commands) {
        if (commands.length == 1) {
            sendMessage("Please specify a module");
            return;
        }
        String moduleName = commands[0];
        Module module = Client.INSTANCE.getModuleManager().getModuleByName(moduleName);

        if (module == null) {
            sendMessage("Unknown module");
            return;
        }

        if (commands.length == 2) {
            sendMessage("Please enter a key");
            return;
        }

        if (commands[1].length() == 1) {
            int chartokey = charToKey(commands[1].charAt(0));
            module.setKey(chartokey);
            sendMessage("Set " + moduleName + "'s key to " + commands[1].charAt(0));
        } else {
            if (commands[1].equals("rshift")) {
                module.setKey(344);
                sendMessage("Set " + moduleName + "'s key to " + "rshift");
            } else if (commands[1].equals("lshift")) {
                module.setKey(340);
                sendMessage("Set " + moduleName + "'s key to " + "lshift");
            }else {
                module.setKey(0);
                sendMessage("Set " + moduleName + "'s key to " + "NONE");
            }
        }
    }

    private int charToKey(char character) {
        return switch (character) {
            case 'a' -> GLFW.GLFW_KEY_A;
            case 'b' -> GLFW.GLFW_KEY_B;
            case 'c' -> GLFW.GLFW_KEY_C;
            case 'd' -> GLFW.GLFW_KEY_D;
            case 'e' -> GLFW.GLFW_KEY_E;
            case 'f' -> GLFW.GLFW_KEY_F;
            case 'g' -> GLFW.GLFW_KEY_G;
            case 'h' -> GLFW.GLFW_KEY_H;
            case 'i' -> GLFW.GLFW_KEY_I;
            case 'j' -> GLFW.GLFW_KEY_J;
            case 'k' -> GLFW.GLFW_KEY_K;
            case 'l' -> GLFW.GLFW_KEY_L;
            case 'm' -> GLFW.GLFW_KEY_M;
            case 'n' -> GLFW.GLFW_KEY_N;
            case 'o' -> GLFW.GLFW_KEY_O;
            case 'p' -> GLFW.GLFW_KEY_P;
            case 'q' -> GLFW.GLFW_KEY_Q;
            case 'r' -> GLFW.GLFW_KEY_R;
            case 's' -> GLFW.GLFW_KEY_S;
            case 't' -> GLFW.GLFW_KEY_T;
            case 'u' -> GLFW.GLFW_KEY_U;
            case 'v' -> GLFW.GLFW_KEY_V;
            case 'w' -> GLFW.GLFW_KEY_W;
            case 'x' -> GLFW.GLFW_KEY_X;
            case 'y' -> GLFW.GLFW_KEY_Y;
            case 'z' -> GLFW.GLFW_KEY_Z;
            default -> 0;
        };
    }
}