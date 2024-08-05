package fr.dog.command.impl;

import fr.dog.command.Command;
import fr.dog.config.ConfigKeybind;
import fr.dog.util.player.ChatUtil;
import org.lwjglx.input.Keyboard;

public class KeyConfigCommand extends Command {

    public KeyConfigCommand() {
        super("keyconfig", "kc");
    }
    public ConfigKeybind configKeybind = new ConfigKeybind();
    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");

        if (words.length < 2 || words.length > 4) {
            ChatUtil.display("Invalid arguments! Usage: .keyconfig <save|load|list|add|remove>");
            return;
        }

        String action = words[1].toLowerCase();

        switch (action) {
            case "list" -> listConfigs();
            case "save" -> configKeybind.saveKeyConfig();
            case "load" -> configKeybind.loadKeyConfig();
            case "add" -> configKeybind.addKeyConfig(Keyboard.getKeyIndex(words[2].toUpperCase()), words[3].toLowerCase());
            case "remove" -> configKeybind.removeKeyConfig(Keyboard.getKeyIndex(words[2].toUpperCase()));
        }
    }

    public void listConfigs(){
        ChatUtil.display("Configs bound to keys - ");

        configKeybind.loadedKeys.forEach((k, v)->{
            ChatUtil.display(Keyboard.getKeyName(k) + " : " + v);
        });
    }

}
