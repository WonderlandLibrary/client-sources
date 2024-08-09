package dev.excellent.impl.client.config;

import com.google.gson.JsonObject;
import dev.excellent.Excellent;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.impl.render.ClickGui;
import dev.excellent.impl.client.theme.Themes;
import dev.excellent.impl.util.file.AbstractFile;
import dev.excellent.impl.util.file.FileType;
import dev.excellent.impl.util.math.Mathf;
import dev.excellent.impl.value.Value;
import dev.excellent.impl.value.impl.*;
import i.gishreloaded.protection.annotation.Native;
import org.joml.Vector2d;

import java.awt.*;
import java.io.*;
import java.util.Arrays;
import java.util.Optional;


public class ConfigFile extends AbstractFile {

    private boolean loadKeyCodes;

    public ConfigFile(final File file) {
        super(file, FileType.CONFIG);
    }

    @Native
    @Override
    public boolean read() {
        if (!this.getFile().exists()) {
            return false;
        }

        try {
            final FileReader fileReader = new FileReader(getFile());
            final BufferedReader bufferedReader = new BufferedReader(fileReader);
            final JsonObject jsonObject = GSON.fromJson(bufferedReader, JsonObject.class);

            bufferedReader.close();
            fileReader.close();

            if (getReadJsonObject(jsonObject, loadKeyCodes)) return false;
        } catch (final IOException ignored) {
            return false;
        }

        return true;
    }

    public static boolean getReadJsonObject(JsonObject jsonObject, boolean loadKeyCodes) {
        if (jsonObject == null) {
            return true;
        }

        if (jsonObject.has("theme")) {
            Optional<Themes> theme = Arrays.stream(Themes.values())
                    .filter(x -> x.name().equalsIgnoreCase(jsonObject.get("theme").getAsString()))
                    .findFirst();

            theme.ifPresent(configTheme -> Excellent.getInst().getThemeManager().setTheme(configTheme));
        }

        for (final Module module : Excellent.getInst().getModuleManager().getAll()) {
            if (!jsonObject.has(module.getDisplayName())) {
                continue;
            }

            module.setEnabled(false, false);

            final JsonObject moduleJsonObject = jsonObject.getAsJsonObject(module.getDisplayName());
            int index = 0;
            for (final Value<?> value : module.getAllValues()) {
                index++;

                if (!moduleJsonObject.has(value.getName() + "-" + index)) {
                    continue;
                }

                final JsonObject valueJsonObject = moduleJsonObject.getAsJsonObject(value.getName() + "-" + index);

                try {
                    if (value instanceof final ModeValue enumValue) {
                        if (valueJsonObject.has("value")) {
                            enumValue.setDefault(valueJsonObject.get("value").getAsString());
                        }
                    } else if (value instanceof final BooleanValue booleanValue) {
                        if (valueJsonObject.has("value")) {
                            booleanValue.setValue(valueJsonObject.get("value").getAsBoolean());
                        }
                    } else if (value instanceof final KeyValue keyValue) {
                        if (valueJsonObject.has("value")) {
                            keyValue.setValue(valueJsonObject.get("value").getAsInt());
                        }
                    } else if (value instanceof final StringValue stringValue) {

                        if (valueJsonObject.has("value")) {

                            String load = valueJsonObject.get("value").getAsString();
                            load = load.replace("<percentsign>", "%");

                            stringValue.setValue(load);
                        }
                    } else if (value instanceof final NumberValue numberValue) {

                        if (valueJsonObject.has("value")) {
                            numberValue.setValue(valueJsonObject.get("value").getAsDouble());
                            numberValue.setValue(Mathf.clamp(numberValue.getMin().floatValue(), numberValue.getMax().floatValue(), numberValue.getValue().floatValue()));
                        }
                    } else if (value instanceof final BoundsNumberValue boundsNumberValue) {

                        if (valueJsonObject.has("first")) {
                            boundsNumberValue.setValue(valueJsonObject.get("first").getAsDouble());
                            boundsNumberValue.setValue(Mathf.clamp(boundsNumberValue.getMin().floatValue(), boundsNumberValue.getMax().floatValue(), boundsNumberValue.getValue().floatValue()));
                        }

                        if (valueJsonObject.has("second")) {
                            boundsNumberValue.setSecondValue(valueJsonObject.get("second").getAsDouble());
                            boundsNumberValue.setSecondValue(Mathf.clamp(boundsNumberValue.getMin().floatValue(), boundsNumberValue.getMax().floatValue(), boundsNumberValue.getSecondValue().floatValue()));
                        }
                    } else if (value instanceof final ColorValue colorValue) {

                        int red = 0, green = 0, blue = 0, alpha = 0;

                        if (valueJsonObject.has("red")) {
                            red = valueJsonObject.get("red").getAsInt();
                        }
                        if (valueJsonObject.has("green")) {
                            green = valueJsonObject.get("green").getAsInt();
                        }
                        if (valueJsonObject.has("blue")) {
                            blue = valueJsonObject.get("blue").getAsInt();
                        }
                        if (valueJsonObject.has("alpha")) {
                            alpha = valueJsonObject.get("alpha").getAsInt();
                        }

                        colorValue.setValue(new Color(red, green, blue, alpha));
                    } else if (value instanceof final DragValue positionValue) {

                        double positionX = 0, positionY = 0, scaleX = 0, scaleY = 0;

                        if (valueJsonObject.has("positionX")) {
                            positionX = valueJsonObject.get("positionX").getAsDouble();
                        }

                        if (valueJsonObject.has("positionY")) {
                            positionY = valueJsonObject.get("positionY").getAsDouble();
                        }

                        if (valueJsonObject.has("scaleX")) {
                            scaleX = valueJsonObject.get("scaleX").getAsDouble();
                        }

                        if (valueJsonObject.has("scaleY")) {
                            scaleY = valueJsonObject.get("scaleY").getAsDouble();
                        }

                        positionValue.setTargetPosition(new Vector2d(positionX, positionY));
                        positionValue.setPosition(new Vector2d(positionX, positionY));
                        positionValue.setSize(new Vector2d(scaleX, scaleY));
                    } else if (value instanceof final ListValue<?> enumValue) {

                        for (Object mode : enumValue.getModes()) {
                            if (mode.toString().equals(valueJsonObject.get("value").getAsString())) {
                                enumValue.setValueAsObject(mode);
                            }
                        }
                    } else if (value instanceof final MultiBooleanValue multiBooleanValue) {
                        int i = 0;
                        for (BooleanValue s : multiBooleanValue.getValues()) {
                            if (valueJsonObject.has("value" + "-" + i)) {
                                s.setValue(valueJsonObject.get("value" + "-" + i).getAsBoolean());
                            }
                            i++;
                        }
                    }
                } catch (final Exception ignored) {
                }
            }

            try {
                if (moduleJsonObject.has("state")) {
                    final boolean state = moduleJsonObject.get("state").getAsBoolean();
                    module.setEnabled(state, false);
                }
            } catch (Exception ignored) {
            }

            if (!loadKeyCodes) {
                continue;
            }

            if (moduleJsonObject.has("keyCode")) {
                final int keyCode = moduleJsonObject.get("keyCode").getAsInt();
                module.setKeyCode(keyCode);
            }
        }
        return false;
    }

    @Override
    public boolean write() {
        try {
            if (!this.getFile().exists()) {
                if (this.getFile().createNewFile()) {
                    System.out.println("Файл конфига успешно создана.");
                } else {
                    System.out.println("Произошла ошибка при создании файла с конфигом.");
                }
            }

            final JsonObject jsonObject = getWriteJsonObject();
            final FileWriter fileWriter = new FileWriter(getFile());
            final BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

            GSON.toJson(jsonObject, bufferedWriter);

            bufferedWriter.flush();
            bufferedWriter.close();
            fileWriter.flush();
            fileWriter.close();
        } catch (IOException ignored) {
            return false;
        }

        return true;
    }

    public static JsonObject getWriteJsonObject() {
        final JsonObject jsonObject = new JsonObject();

        final JsonObject infoJsonObject = new JsonObject();
        infoJsonObject.addProperty("version", Excellent.getInst().getInfo().getVersion());
        infoJsonObject.addProperty("commit", Excellent.getInst().getInfo().getGitCommit());
        infoJsonObject.addProperty("build", Excellent.getInst().getInfo().getBuild());
        jsonObject.add(Excellent.getInst().getInfo().getName(), infoJsonObject);

        for (final Module module : Excellent.getInst().getModuleManager().getAll()) {
            final JsonObject moduleJsonObject = new JsonObject();

            if (!(module instanceof ClickGui)) {
                moduleJsonObject.addProperty("state", module.isEnabled());
            }

            moduleJsonObject.addProperty("keyCode", module.getKeyCode());

            int index = 0;
            for (final Value<?> value : module.getAllValues()) {
                index++;

                final JsonObject valueJsonObject = new JsonObject();

                if (value instanceof final ModeValue enumValue) {
                    valueJsonObject.addProperty("value", enumValue.getValue().getName());
                } else if (value instanceof final BooleanValue booleanValue) {
                    valueJsonObject.addProperty("value", booleanValue.getValue());
                } else if (value instanceof final KeyValue keyValue) {
                    valueJsonObject.addProperty("value", keyValue.getValue());
                } else if (value instanceof final NumberValue numberValue) {
                    valueJsonObject.addProperty("value", numberValue.getValue().doubleValue());
                } else if (value instanceof final StringValue stringValue) {

                    String save = stringValue.getValue();
                    save = save.replace("%", "<percentsign>");

                    valueJsonObject.addProperty("value", save);
                } else if (value instanceof final BoundsNumberValue boundsNumberValue) {
                    valueJsonObject.addProperty("first", boundsNumberValue.getValue().doubleValue());
                    valueJsonObject.addProperty("second", boundsNumberValue.getSecondValue().doubleValue());
                } else if (value instanceof final ColorValue colorValue) {

                    valueJsonObject.addProperty("red", colorValue.getValue().getRed());
                    valueJsonObject.addProperty("green", colorValue.getValue().getGreen());
                    valueJsonObject.addProperty("blue", colorValue.getValue().getBlue());
                    valueJsonObject.addProperty("alpha", colorValue.getValue().getAlpha());
                } else if (value instanceof final DragValue positionValue) {
                    valueJsonObject.addProperty("positionX", positionValue.position.x);
                    valueJsonObject.addProperty("positionY", positionValue.position.y);

                    valueJsonObject.addProperty("scaleX", positionValue.size.x);
                    valueJsonObject.addProperty("scaleY", positionValue.size.y);
                } else if (value instanceof final ListValue<?> enumValue) {
                    valueJsonObject.addProperty("value", enumValue.getValue().toString());
                } else if (value instanceof final MultiBooleanValue multiBooleanValue) {
                    int i = 0;
                    for (BooleanValue s : multiBooleanValue.getValues()) {
                        valueJsonObject.addProperty("value" + "-" + i, s.getValue());
                        i++;
                    }
                }

                moduleJsonObject.add(value.getName() + "-" + index, valueJsonObject);
            }

            jsonObject.add(module.getDisplayName(), moduleJsonObject);
        }
        jsonObject.addProperty("theme", Excellent.getInst().getThemeManager().getTheme().name());
        return jsonObject;
    }

    public void allowKeyCodeLoading() {
        this.loadKeyCodes = true;
    }
}