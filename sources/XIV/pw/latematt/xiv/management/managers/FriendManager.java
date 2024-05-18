package pw.latematt.xiv.management.managers;

import com.google.common.io.Files;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.minecraft.util.StringUtils;
import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.management.MapManager;
import pw.latematt.xiv.utils.ChatLogger;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.regex.Matcher;

/**
 * @author Matthew
 */
public class FriendManager extends MapManager<String, String> {
    public FriendManager() {
        super(new HashMap<>());
    }

    @Override
    public void setup() {
        XIV.getInstance().getLogger().info(String.format("Starting to setup %s.", getClass().getSimpleName()));

        new XIVFile("friends", "json") {
            @Override
            public void load() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                BufferedReader reader = new BufferedReader(new FileReader(file));
                HashMap<String, String> friends = gson.fromJson(reader, new TypeToken<HashMap<String, String>>() {
                }.getType());
                for (String mcname : friends.keySet()) {
                    String alias = friends.get(mcname);
                    XIV.getInstance().getFriendManager().add(mcname, alias);
                }
            }

            @Override
            public void save() throws IOException {
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                Files.write(gson.toJson(XIV.getInstance().getFriendManager().getContents()).getBytes("UTF-8"), file);
            }
        };

        Command.newCommand()
                .cmd("friend")
                .description("Manages a player's friend status so the client doesn't target him.")
                .aliases("fr")
                .arguments("<action>")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String action = arguments[1];
                        switch (action.toLowerCase()) {
                            case "add":
                            case "a":
                                if (arguments.length == 3) {
                                    String mcname = arguments[2];
                                    XIV.getInstance().getFriendManager().add(mcname, mcname);
                                    XIV.getInstance().getFileManager().saveFile("friends");
                                    ChatLogger.print(String.format("Friend \"\247g%s\247r\" added.", mcname));
                                } else if (arguments.length >= 4) {
                                    String mcname = arguments[2];
                                    String alias = arguments[3];
                                    XIV.getInstance().getFriendManager().add(mcname, alias);
                                    XIV.getInstance().getFileManager().saveFile("friends");
                                    ChatLogger.print(String.format("Friend \"\247g%s\247r\" added.", alias));
                                } else {
                                    ChatLogger.print("Invalid arguments, valid: friend add <mcname> [alias]");
                                }
                                break;
                            case "r":
                            case "remove":
                            case "del":
                            case "d":
                                if (arguments.length >= 3) {
                                    String mcname = arguments[2];
                                    XIV.getInstance().getFriendManager().remove(mcname);
                                    XIV.getInstance().getFileManager().saveFile("friends");
                                    ChatLogger.print(String.format("Friend \"%s\" removed.", mcname));
                                } else {
                                    ChatLogger.print("Invalid arguments, valid: friend del <mcname>");
                                }
                                break;
                            default:
                                ChatLogger.print("Invalid action, valid: add, del");
                                break;
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: friend <action>");
                    }
                }).build();
        XIV.getInstance().getLogger().info(String.format("Successfully setup %s.", getClass().getSimpleName()));
    }

    public void add(String mcname, String alias) {
        contents.put(mcname, alias);
    }

    public void remove(String mcname) {
        contents.remove(mcname);
    }

    public String replace(String string, boolean colored) {
        for (String mcname : contents.keySet()) {
            String alias = contents.get(mcname);
            if (colored)
                alias = String.format("\247g%s\247r", alias);
            string = string.replaceAll("(?i)" + Matcher.quoteReplacement(mcname), Matcher.quoteReplacement(alias));
        }
        return string;
    }

    public boolean isFriend(String mcname) {
        return contents.containsKey(StringUtils.stripControlCodes(mcname));
    }
}
