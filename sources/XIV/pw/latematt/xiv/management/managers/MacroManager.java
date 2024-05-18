package pw.latematt.xiv.management.managers;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.macro.Macro;
import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.utils.ChatLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author Matthew
 */
public class MacroManager extends ListManager<Macro> {
    public MacroManager() {
        super(new ArrayList<>());
    }

    @Override
    public void setup() {
        XIV.getInstance().getLogger().info(String.format("Starting to setup %s.", getClass().getSimpleName()));

        new XIVFile("macroconfig", "json") {
            @Override
            public void load() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<MacroConfig> macroConfig = gson.fromJson(reader, new TypeToken<List<MacroConfig>>() {
                }.getType());
                macroConfig.forEach(config -> getContents().add(new Macro(Keyboard.getKeyIndex(config.getKeybind()) > 0 ? Keyboard.getKeyIndex(config.getKeybind()) : Mouse.getButtonIndex(config.getKeybind()) + 256, config.getCommand())));
            }

            @Override
            public void save() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                List<MacroConfig> macroConfig = new ArrayList<>();
                getContents().forEach(macro -> macroConfig.add(new MacroConfig(macro.getKeybind() >= 256 ? Mouse.getButtonName(macro.getKeybind() - 256) : Keyboard.getKeyName(macro.getKeybind()), macro.getCommand())));
                Files.write(gson.toJson(macroConfig).getBytes("UTF-8"), file);
            }
        };

        Command.newCommand()
                .cmd("macro")
                .description("Manage macros.")
                .arguments("<action>")
                .aliases("mac")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String action = arguments[1];
                        switch (action.toLowerCase()) {
                            case "add":
                            case "a":
                                if (arguments.length >= 4) {
                                    String command = message.substring(String.format("%s %s %s ", arguments[0], arguments[1], arguments[2]).length(), message.length());

                                    String newBindName = arguments[2].toUpperCase();
                                    int newBind = Mouse.getButtonIndex(newBindName);

                                    if (newBind > -1) {
                                        Macro macro = new Macro(newBind + 256, command);
                                        getContents().add(macro);
                                        XIV.getInstance().getFileManager().saveFile("macroconfig");
                                        ChatLogger.print(String.format("Macro \"%s\" added.", Mouse.getButtonName(newBind)));
                                    } else {
                                        newBind = Keyboard.getKeyIndex(newBindName);

                                        if(newBind == Keyboard.KEY_NONE) {
                                            ChatLogger.print("Invalid or unrecognized keybind.");
                                        }else {
                                            Macro macro = new Macro(newBind, command);
                                            getContents().add(macro);
                                            XIV.getInstance().getFileManager().saveFile("macroconfig");
                                            ChatLogger.print(String.format("Macro \"%s\" added.", Keyboard.getKeyName(newBind)));
                                        }
                                    }
                                } else {
                                    ChatLogger.print("Invalid arguments, valid: macro add <keybind> <command>");
                                }
                                break;
                            case "remove":
                            case "r":
                            case "del":
                            case "d":
                                if (arguments.length >= 3) {
                                    int keybind = Keyboard.getKeyIndex(arguments[2].toUpperCase());

                                    if (keybind > 0) {
                                        Macro macro = getMacro(keybind);
                                        if (Objects.nonNull(macro)) {
                                            getContents().remove(macro);
                                            XIV.getInstance().getFileManager().saveFile("macroconfig");
                                            ChatLogger.print(String.format("Macro \"%s\" removed.", Keyboard.getKeyName(macro.getKeybind())));
                                        } else {
                                            ChatLogger.print(String.format("Macro \"%s\" not found.", Keyboard.getKeyName(keybind)));
                                        }
                                    } else {
                                        int mousebutton = Mouse.getButtonIndex(arguments[2].toUpperCase());

                                        Macro macro = getMacro(mousebutton + 256);
                                        if (Objects.nonNull(macro)) {
                                            getContents().remove(macro);
                                            XIV.getInstance().getFileManager().saveFile("macroconfig");
                                            ChatLogger.print(String.format("Macro \"%s\" removed.", Mouse.getButtonName(macro.getKeybind() - 256)));
                                        } else {
                                            ChatLogger.print(String.format("Macro \"%s\" not found.", Mouse.getButtonName(mousebutton)));
                                        }
                                    }
                                } else {
                                    ChatLogger.print("Invalid arguments, valid: macro del <keybind>");
                                }
                                break;
                            default:
                                ChatLogger.print("Invalid action, valid: add, remove");
                                break;
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: macro <action>");
                    }
                }).build();
        XIV.getInstance().getLogger().info(String.format("Successfully setup %s.", getClass().getSimpleName()));
    }

    public Macro getMacro(int keybind) {
        for (Macro macro : getContents()) {
            if (macro.getKeybind() == keybind) {
                return macro;
            }
        }
        return null;
    }

    public Macro getMacro(String command) {
        for (Macro macro : getContents()) {
            if (macro.getCommand().equals(command)) {
                return macro;
            }
        }
        return null;
    }

    public final class MacroConfig {
        private final String keybind;
        private final String command;

        public MacroConfig(String keybind, String command) {
            this.keybind = keybind;
            this.command = command;
        }

        public String getKeybind() {
            return keybind;
        }

        public String getCommand() {
            return command;
        }
    }

}
