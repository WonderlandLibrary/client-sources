package dev.excellent.client.command.impl;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.impl.client.config.ConfigFile;
import dev.excellent.impl.client.config.ConfigManager;
import dev.excellent.impl.util.chat.ChatUtil;
import lombok.SneakyThrows;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.util.text.event.HoverEvent;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;
import java.util.concurrent.CompletableFuture;

public final class ConfigCommand extends Command {

    public ConfigCommand() {
        super("", "config", "cfg");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            ChatUtil.addText(TextFormatting.RED + """
                                                
                    Использование:
                    .cfg (save/create) <name>
                    .cfg (dir/folder)
                    .cfg load <name>
                    .cfg list""");
            return;
        }

        final ConfigManager configManager = Excellent.getInst().getConfigManager();
        final String command = args[1].toLowerCase();

        switch (args.length) {
            case 3 -> {
                final String name = args[2];
                switch (command) {
                    case "code" -> {
                        String code = performDecryption(name);
                        final JsonObject jsonObject = new Gson().fromJson(code, JsonObject.class);
                        if (!ConfigFile.getReadJsonObject(jsonObject, true)) {
                            ChatUtil.addText(TextFormatting.RED + "Конфиг из кода успешно загружен");
                        } else {
                            ChatUtil.addText(TextFormatting.RED + "Некорректный код");
                        }
                    }
                    case "load" -> {
                        configManager.update();
                        final ConfigFile config = configManager.get(name, true);
                        if (config != null) {
                            CompletableFuture.runAsync(() -> {
                                if (config.read()) {
                                    configManager.set();
                                    ChatUtil.addText(String.format(TextFormatting.RED + "Конфиг %s загружен", name));
                                } else {
                                    ChatUtil.addText(TextFormatting.RED + "Конфиг не найден");

                                }
                            });
                        } else {
                            ChatUtil.addText(TextFormatting.RED + "Конфиг не найден");
                        }
                    }
                    case "save", "create" -> {
                        CompletableFuture.runAsync(() -> {
                            configManager.set(name);
                            configManager.set();

                            ChatUtil.addText(TextFormatting.RED + "Конфиг сохранён");
                        });
                    }
                    default -> ChatUtil.addText(TextFormatting.RED + """
                                                        
                            Использование:
                            .cfg (save/create) <name>
                            .cfg (dir/folder)
                            .cfg load <name>
                            .cfg list""");
                }
            }
            case 2 -> {
                switch (command) {
                    case "code" -> {
                        String cfg = ConfigFile.getWriteJsonObject().toString();
                        String code = performEncryption(cfg);
                        mc.keyboardListener.setClipboardString(code);
                        ChatUtil.addText(TextFormatting.RED + "Код скопирован в буфер обмена");
                    }
                    case "list" -> {
                        configManager.set();
                        ChatUtil.addText(TextFormatting.RED + "\nВыберите конфиг для подгрузки");
                        configManager.update();
                        configManager.forEach(configFile -> {
                            final String configName = configFile.getFile().getName().replace("." + Excellent.getInst().getInfo().getNamespace(), "");
                            final String configCommand = ".config load " + configName;
                            final String color = String.valueOf(TextFormatting.AQUA);

                            final StringTextComponent chatText = new StringTextComponent(color + "> " + configName);
                            final StringTextComponent hoverText = new StringTextComponent(TextFormatting.RED + "Конфиг: " + configName);

                            chatText.setStyle(Style.EMPTY.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, configCommand)).setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, hoverText)));

                            ChatUtil.addText(chatText);
                        });
                    }
                    case "folder", "dir" -> {
                        try {
                            Util.getOSType().openFile(ConfigManager.CONFIG_DIRECTORY);
                            configManager.set();
                            ChatUtil.addText(TextFormatting.RED + "Папка с конфигами открыта");
                        } catch (IllegalArgumentException exception) {
                            ChatUtil.addText(TextFormatting.RED + "Директория с конфигами не найдена");
                        }
                    }
                    default -> {
                        configManager.set();
                        ChatUtil.addText(TextFormatting.RED + """
                                                            
                                Использование:
                                .cfg (save/create) <name>
                                .cfg (dir/folder)
                                .cfg load <name>
                                .cfg list""");
                    }
                }
            }
            default -> ChatUtil.addText(TextFormatting.RED + """
                                                
                    Использование:
                    .cfg (save/create) <name>
                    .cfg (dir/folder)
                    .cfg load <name>
                    .cfg list""");
        }
    }

    private final String ALGORITHM = "AES";
    private final String TRANSFORMATION = "AES/ECB/PKCS5Padding";
    private final String SECRET_KEY = "excellent1234567";

    @SneakyThrows
    private String performEncryption(String str) {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec("excellent1234567".getBytes(StandardCharsets.UTF_8), ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
        return Base64.getEncoder().encodeToString(cipher.doFinal(str.getBytes()));
    }

    @SneakyThrows
    private String performDecryption(String str) {
        Cipher cipher = Cipher.getInstance(TRANSFORMATION);
        SecretKeySpec secretKeySpec = new SecretKeySpec("excellent1234567".getBytes(StandardCharsets.UTF_8), ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
        return new String(cipher.doFinal(Base64.getDecoder().decode(str)));
    }
}
