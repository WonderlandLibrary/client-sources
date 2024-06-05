package me.dinozoid.strife.command.implementations;

import joptsimple.internal.Strings;
import me.dinozoid.strife.StrifeClient;
import me.dinozoid.strife.command.Command;
import me.dinozoid.strife.command.CommandInfo;
import me.dinozoid.strife.command.argument.Argument;
import me.dinozoid.strife.command.argument.implementations.MultiChoiceArgument;
import me.dinozoid.strife.font.CustomFont;
import me.dinozoid.strife.util.player.PlayerUtil;
import me.dinozoid.strife.util.system.FolderUtil;
import org.apache.commons.io.FileUtils;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

@CommandInfo(name = "Font", aliases = "Fonts", description = "Handle fonts.")
public class FontCommand extends Command {

    private boolean found = false;

    @Override
    public boolean execute(String[] args, String label) {
        if(label.equalsIgnoreCase("fonts")) args[0] = "list";
        switch (args[0].toLowerCase()) {
            case "add":
                List<File> files = FolderUtil.chooseFiles("Add a font", "", true, JFileChooser.FILES_ONLY, "Font files (.ttf)", "ttf");
                files.forEach(file -> {
                    try {
                        new File(StrifeClient.DIRECTORY + "/fonts").mkdirs();
                        FileUtils.copyFile(file, new File(StrifeClient.DIRECTORY + "/fonts/" + file.getName()));
                        StrifeClient.INSTANCE.fontRepository().add(new CustomFont(file.getName().substring(0, file.getName().length() - 3), file.getPath()));
                        PlayerUtil.sendMessageWithPrefix("&7Font successfully added!");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                break;
            case "remove":
                try {
                    found = false;
                    Files.walk(Paths.get(StrifeClient.DIRECTORY + "/fonts")).forEach(file -> {
                        if(file.getFileName().toString().startsWith(args[1])) {
                            try {
                                Files.delete(file);
                                found = true;
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if(found) PlayerUtil.sendMessageWithPrefix("&7Font successfully deleted!");
                else PlayerUtil.sendMessageWithPrefix("&7Font not found.");
                break;
            case "load":
                CustomFont font = StrifeClient.INSTANCE.fontRepository().fonts().stream().filter(customFont -> customFont.name().equalsIgnoreCase(args[1])).findFirst().orElse(null);
                if(font == StrifeClient.INSTANCE.fontRepository().currentFont()) PlayerUtil.sendMessageWithPrefix("&7This font already has been loaded!");
                else if(font != null) {
                    StrifeClient.INSTANCE.fontRepository().load(StrifeClient.INSTANCE.fontRepository().fontBy(args[1]));
                    PlayerUtil.sendMessageWithPrefix("&c" + font.name() + " &7has been loaded.");
                } else {
                    PlayerUtil.sendMessageWithPrefix("&7Font does not exist!");
                }
                break;
            case "list":
                PlayerUtil.sendMessage(" ");
                PlayerUtil.sendMessage("&7All available fonts.");
                StringBuilder stringBuilder = new StringBuilder();
                List<CustomFont> fonts = StrifeClient.INSTANCE.fontRepository().fonts();
                if (!fonts.isEmpty()) {
                    for (CustomFont customFont : fonts)
                        stringBuilder.append("&c" + customFont.name()).append(StrifeClient.INSTANCE.fontRepository().currentFont() == customFont ? " (Loaded)" : "").append("&7, ");
                    PlayerUtil.sendMessage(stringBuilder.substring(0, stringBuilder.length() - 2));
                } else PlayerUtil.sendMessage("&7No available fonts.");
                PlayerUtil.sendMessage(" ");
                break;
        }
        return true;
    }

    @Override
    public List<Argument> arguments(String[] args) {
        return Arrays.asList(new MultiChoiceArgument(String.class, "Operation", "Add", "Remove", "Load", "List", "Folder"), new Argument(String.class, "Name", () -> !(args[0].equalsIgnoreCase("add") || args[0].equalsIgnoreCase("list") || args[0].equalsIgnoreCase("folder"))));
    }
}
