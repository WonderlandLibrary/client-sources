package pw.latematt.xiv.command.commands;

import pw.latematt.xiv.command.CommandHandler;
import pw.latematt.xiv.utils.ChatLogger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

/**
 * @author Rederpz
 */
public class History implements CommandHandler {
    @Override
    public void onCommandRan(String message) {
        String[] arguments = message.split(" ");

        if (arguments.length >= 2) {
            String uuid = "";
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + arguments[1]);
                BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    uuid = line.split("\"")[3];
                }

                br.close();

                url = new URL("https://api.mojang.com/user/profiles/" + uuid + "/names");
                br = new BufferedReader(new InputStreamReader(url.openStream()));

                String originalName;
                String oldNames = "";

                while ((line = br.readLine()) != null) {
                    originalName = line.split("\"")[3];

                    for (int i = 0; i < line.split("\"").length; i++) {
                        if ((i != line.split("\"").length - 1) && (line.split("\"")[(i + 1)].equals(","))) {
                            if (oldNames.equals("")) {
                                oldNames = line.split("\"")[i];
                            } else {
                                oldNames = oldNames + ", " + line.split("\"")[i];
                            }
                        }
                    }

                    if (oldNames.equals(""))
                        ChatLogger.print(arguments[1] + " hasn't changed their name");
                    else
                        ChatLogger.print(arguments[1] + "'s name history: " + originalName + ", " + oldNames + ".");
                }
            } catch (IOException e) {
                ChatLogger.print("Unable to retrieve UUID of player.");
            }
        } else {
            ChatLogger.print("Invalid arguments, valid: history <name>");
        }
    }
}
