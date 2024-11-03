package dev.stephen.nexus.commands.impl;

import dev.stephen.nexus.Client;
import dev.stephen.nexus.commands.Command;
import dev.stephen.nexus.module.Module;
import org.lwjgl.glfw.GLFW;

public class BindListCommand extends Command {
    public BindListCommand() {
        super("bindlist");
    }

    @Override
    public void execute(String[] commands) {
        sendMessage("Modules: ");
        for (Module module : Client.INSTANCE.getModuleManager().getModules()) {
            if (module.getKey() != 0) {
                sendMessage(module.getName() + ": " + keyToChar(module.getKey()));
            }
        }
    }

    private String keyToChar(int key) {
        return switch (key) {
            case GLFW.GLFW_KEY_A -> "a";
            case GLFW.GLFW_KEY_B -> "b";
            case GLFW.GLFW_KEY_C -> "c";
            case GLFW.GLFW_KEY_D -> "d";
            case GLFW.GLFW_KEY_E -> "e";
            case GLFW.GLFW_KEY_F -> "f";
            case GLFW.GLFW_KEY_G -> "g";
            case GLFW.GLFW_KEY_H -> "h";
            case GLFW.GLFW_KEY_I -> "i";
            case GLFW.GLFW_KEY_J -> "j";
            case GLFW.GLFW_KEY_K -> "k";
            case GLFW.GLFW_KEY_L -> "l";
            case GLFW.GLFW_KEY_M -> "m";
            case GLFW.GLFW_KEY_N -> "n";
            case GLFW.GLFW_KEY_O -> "o";
            case GLFW.GLFW_KEY_P -> "p";
            case GLFW.GLFW_KEY_Q -> "q";
            case GLFW.GLFW_KEY_R -> "r";
            case GLFW.GLFW_KEY_S -> "s";
            case GLFW.GLFW_KEY_T -> "t";
            case GLFW.GLFW_KEY_U -> "u";
            case GLFW.GLFW_KEY_V -> "v";
            case GLFW.GLFW_KEY_W -> "w";
            case GLFW.GLFW_KEY_X -> "x";
            case GLFW.GLFW_KEY_Y -> "y";
            case GLFW.GLFW_KEY_Z -> "z";
            case GLFW.GLFW_KEY_RIGHT_SHIFT -> "rshift";
            case GLFW.GLFW_KEY_LEFT_SHIFT -> "lshift";
            default -> "";
        };
    }

}
