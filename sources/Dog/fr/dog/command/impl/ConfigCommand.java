package fr.dog.command.impl;

import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.util.player.ChatUtil;
import lombok.SneakyThrows;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;

import java.awt.*;
import java.io.File;
import java.util.Objects;

public class ConfigCommand extends Command {

    private final File configFolder = new File(mc.mcDataDir, "/dog/configs");

    public ConfigCommand() {
        super("config", "c");
    }

    @Override
    @SneakyThrows
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");

        if (words.length < 2 || words.length > 3) {
            ChatUtil.display("Invalid arguments! Usage: .config <save|load|list> [name]");
            return;
        }

        String action = words[1].toLowerCase();
        String config = words.length == 3 ? words[2] : "default";

        switch (action) {
            case "list" -> listConfigs();
            case "save" -> Dog.getInstance().getConfigManager().saveConfig(config);
            case "load" -> Dog.getInstance().getConfigManager().loadConfig(config);
            case "folder" -> Desktop.getDesktop().open(new File(mc.mcDataDir, "/dog/configs"));
        }
    }

    private void listConfigs() {
        ChatUtil.display("Listing of all the configs:");

        File[] configFiles = Objects.requireNonNull(configFolder.listFiles());
        if (configFiles.length > 0) {
            for (File file : configFiles) {
                String configName = file.getName().replace(".json", "");
                ChatComponentText themeComponent = new ChatComponentText("§f[" + Dog.getInstance().getThemeManager().getCurrentTheme().chatFormatting + "Dog Client§f]§8 > §7".toLowerCase());
                    ChatComponentText configComponent = new ChatComponentText(configName);
                    configComponent.setChatStyle(new ChatStyle()

                        .setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ".config load " + configName))
                        .setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("Click to load " + configName))));
                Minecraft.getMinecraft().thePlayer.addChatMessage(configComponent);
            }
        } else {
            ChatUtil.display("No configs found.");
        }
    }
}
