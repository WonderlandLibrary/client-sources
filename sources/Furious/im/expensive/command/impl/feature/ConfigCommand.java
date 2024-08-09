package im.expensive.command.impl.feature;

import im.expensive.command.*;
import im.expensive.command.impl.CommandException;
import im.expensive.config.Config;
import im.expensive.config.ConfigStorage;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import net.minecraft.util.text.TextFormatting;

import java.io.File;
import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ConfigCommand implements Command, CommandWithAdvice, MultiNamedCommand {

    final ConfigStorage configStorage;
    final Prefix prefix;
    final Logger logger;


    @Override
    public void execute(Parameters parameters) {
        String commandType = parameters.asString(0).orElse("");

        switch (commandType) {
            case "load" -> loadConfig(parameters);
            case "save" -> saveConfig(parameters);
            case "list" -> configList();
            case "dir" -> getDirectory();
            default ->
                    throw new CommandException(TextFormatting.RED + "Укажите тип команды:" + TextFormatting.GRAY + " load, save, list, dir");
        }
    }

    // ... (
    @Override
    public String name() {
        return "config";
    }

    @Override
    public String description() {
        return "Позволяет взаимодействовать с конфигами в чите";
    }

    @Override
    public List<String> adviceMessage() {
        String commandPrefix = prefix.get();

        return List.of(commandPrefix + name() + " load <config> - Загрузить конфиг",
                commandPrefix + name() + " save <config> - Сохранить конфиг",
                commandPrefix + name() + " list - Получить список конфигов",
                commandPrefix + name() + " dir - Открыть папку с конфигами",
                "Пример: " + TextFormatting.RED + commandPrefix + "cfg save myConfig",
                "Пример: " + TextFormatting.RED + commandPrefix + "cfg load myConfig"

        );
    }

    @Override
    public List<String> aliases() {
        return List.of("cfg");
    }

    private void loadConfig(Parameters parameters) {
        String configName = parameters.asString(1)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите название конфига!"));

        if (new File(configStorage.CONFIG_DIR, configName + ".cfg").exists()) {
            configStorage.loadConfiguration(configName);
            logger.log(TextFormatting.GREEN + "Конфигурация " + TextFormatting.RED + configName + TextFormatting.GREEN + " загружена!");

        } else {
            logger.log(TextFormatting.RED + "Конфигурация " + TextFormatting.GRAY + configName + TextFormatting.RED + " не найдена!");
        }
    }

    private void saveConfig(Parameters parameters) {
        String configName = parameters.asString(1)
                .orElseThrow(() -> new CommandException(TextFormatting.RED + "Укажите название конфига!"));

        configStorage.saveConfiguration(configName);
        logger.log(TextFormatting.GREEN + "Конфигурация " + TextFormatting.RED + configName + TextFormatting.GREEN + " сохранена!");

    }

    private void configList() {
        if (configStorage.isEmpty()) {
            logger.log(TextFormatting.RED + "Список конфигураций пустой");
            return;
        }
        logger.log(TextFormatting.GRAY + "Список конфигов:");

        for (Config config : configStorage.getConfigs()) {
            logger.log(TextFormatting.GRAY + config.getName());
        }
    }

    private void getDirectory() {
        try {
            Runtime.getRuntime().exec("explorer " + configStorage.CONFIG_DIR.getAbsolutePath());
        } catch (IOException e) {
            logger.log(TextFormatting.RED + "Папка с конфигурациями не найдена!" + e.getMessage());
        }
    }
}
