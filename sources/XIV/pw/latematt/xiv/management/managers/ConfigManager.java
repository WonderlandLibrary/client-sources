package pw.latematt.xiv.management.managers;

import pw.latematt.xiv.XIV;
import pw.latematt.xiv.command.Command;
import pw.latematt.xiv.file.XIVFile;
import pw.latematt.xiv.utils.ChatLogger;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author Matthew
 */
public class ConfigManager {
    private final File configDir = new File(XIVFile.XIV_DIRECTORY + File.separator + "configs");

    public void setup() {
        XIV.getInstance().getLogger().info(String.format("Starting to setup %s.", getClass().getSimpleName()));

        if ((!configDir.isDirectory() || !configDir.exists()) && configDir.mkdir()) {
            XIV.getInstance().getLogger().info("Successfully created config directory at \"" + XIVFile.XIV_DIRECTORY.getAbsolutePath() + "\".");
        } else if (!configDir.isDirectory() || !configDir.exists()) {
            XIV.getInstance().getLogger().info("Failed to create config directory.");
        }
        File autoExecuteFile = new File(configDir + File.separator + "autoexec.cfg");
        if (autoExecuteFile.exists()) {
            try {
                parseConfig(autoExecuteFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Command.newCommand()
                .cmd("execute")
                .description("Execute commands in a config file placed in your XIV folder.")
                .arguments("<filename> <output>")
                .aliases("exec")
                .handler(message -> {
                    String[] arguments = message.split(" ");
                    if (arguments.length >= 2) {
                        String filename = arguments[1];
                        boolean output = true;
                        if (arguments.length >= 3)
                            output = Boolean.parseBoolean(arguments[2]);

                        File[] files = configDir.listFiles((dir, name) -> name.equalsIgnoreCase(filename + ".cfg"));
                        if (files != null && files.length > 0) {
                            for (File config : files) {
                                try {
                                    if (!output)
                                        ChatLogger.setEnabled(false);

                                    parseConfig(config);
                                    ChatLogger.print(String.format("Executed config file \"%s\"", config.getName()));
                                    if (!output)
                                        ChatLogger.setEnabled(true);

                                } catch (IOException e) {
                                    if (output)
                                        ChatLogger.print("Failed to parse config, a stacktrace has been printed.");
                                    else
                                        ChatLogger.setEnabled(true);
                                    e.printStackTrace();
                                }
                            }
                        } else {
                            ChatLogger.print(String.format("No config found by name \"%s\".", filename));
                        }
                    } else {
                        ChatLogger.print("Invalid arguments, valid: execute <filename>");
                    }
                }).build();

        XIV.getInstance().getLogger().info(String.format("Successfully setup %s.", getClass().getSimpleName()));
    }

    public void parseConfig(File config) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(config));
        String line;
        while ((line = reader.readLine()) != null) {
            if (line.length() > 0 && !line.startsWith("//")) {
                XIV.getInstance().getCommandManager().parseCommand(XIV.getInstance().getCommandManager().getPrefix() + line);
            }
        }
        reader.close();
    }
}
