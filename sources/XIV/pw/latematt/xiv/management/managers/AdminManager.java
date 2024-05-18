package pw.latematt.xiv.management.managers;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.StringUtils;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.management.ListManager;
import pw.latematt.xiv.utils.ChatLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Rederpz
 */
public class AdminManager extends ListManager<String> {
    public AdminManager() {
        super(new ArrayList<>());
    }

    @Override
    public void setup() {
        XIV.getInstance().getLogger().info(String.format("Starting to setup %s.", getClass().getSimpleName()));

        new XIVFile("admins", "json") {
            @Override
            public void load() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                List<String> admins = gson.fromJson(reader, new TypeToken<List<String>>() {
                }.getType());
                for (String mcname : admins)
                    XIV.getInstance().getAdminManager().getContents().add(mcname);
            }

            @Override
            public void save() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Files.write(gson.toJson(XIV.getInstance().getAdminManager().getContents()).getBytes("UTF-8"), file);
            }
        };

        Command.newCommand()
                .cmd("admin")
                .description("Manages a player's friend status so the client doesn't target him.")
                .aliases("enemy")
                .arguments("<action>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String action = arguments[1];
                        switch (action.toLowerCase()) {
                            case "add":
                            case "a":
                                if (arguments.length >= 3) {
                                    String mcname = arguments[2];
                                    XIV.getInstance().getAdminManager().getContents().add(mcname);
                                    XIV.getInstance().getFileManager().saveFile("admins");
                                    ChatLogger.print(String.format("Admin \"\2475%s\247r\" added.", mcname));
                                } else {
                                    ChatLogger.print("Invalid arguments, valid: admin add <mcname>");
                                }
                                break;
                            case "del":
                            case "d":
                            case "remove":
                            case "r":
                                if (arguments.length >= 3) {
                                    String mcname = arguments[2];
                                    XIV.getInstance().getAdminManager().getContents().remove(mcname);
                                    XIV.getInstance().getFileManager().saveFile("admins");
                                    ChatLogger.print(String.format("Admin \"%s\" removed.", mcname));
                                } else {
                                    ChatLogger.print("Invalid arguments, valid: admin del <mcname>");
                                }
                                break;
                            default:
                                ChatLogger.print("Invalid action, valid: add, del");
                                break;
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: admin <action>");
                    }
                }).build();
        XIV.getInstance().getLogger().info(String.format("Successfully setup %s.", getClass().getSimpleName()));
    }

    public boolean isAdmin(String mcname) {
        return contents.contains(StringUtils.stripControlCodes(mcname));
    }
}
