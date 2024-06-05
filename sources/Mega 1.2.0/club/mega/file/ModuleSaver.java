package club.mega.file;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;
import club.mega.module.Module;
import club.mega.module.setting.Setting;
import club.mega.module.setting.impl.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public final class ModuleSaver implements MinecraftInterface {

    public static void save() {
        final File file = new File(MC.mcDataDir, Mega.INSTANCE.getName() + "/Modules.json");

        try {
            if (file.exists())
                file.delete();
            if (new File(MC.mcDataDir, Mega.INSTANCE.getName() + "/").mkdir() || !file.exists())
                file.createNewFile();

            final JsonObject mainObject = new JsonObject();
            for (final Module module : Mega.INSTANCE.getModuleManager().getModules())
            {
                final JsonObject moduleObject = new JsonObject();
                final JsonObject settingObject = new JsonObject();

                for (final Setting setting : module.getSettings())
                {
                    if (setting instanceof TextSetting)
                        settingObject.addProperty(setting.getName(), ((TextSetting) setting).getRawText());
                    if (setting instanceof ListSetting)
                        settingObject.addProperty(setting.getName(), ((ListSetting) setting).getCurrent());
                    if (setting instanceof BooleanSetting)
                        settingObject.addProperty(setting.getName(), ((BooleanSetting) setting).get());
                    if (setting instanceof NumberSetting)
                        settingObject.addProperty(setting.getName(), ((NumberSetting) setting).getAsDouble());
                }
                moduleObject.addProperty("Toggled", module.isToggled());
                moduleObject.addProperty("Key", module.getKey());
                moduleObject.add("Setting", settingObject);
                mainObject.add(module.getName(), moduleObject);
            }
            final Gson gson = new GsonBuilder().setPrettyPrinting().create();

            final FileWriter fileWriter = new FileWriter(file.getAbsoluteFile());
            gson.toJson(mainObject, fileWriter);
            fileWriter.close();
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

    public static void load() {
        final File file = new File(MC.mcDataDir, Mega.INSTANCE.getName() + "/Modules.json");

        try {
            if (!file.exists())
                return;

            FileReader reader = new FileReader(file.getAbsoluteFile());
            JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
            reader.close();

            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Module module = Mega.INSTANCE.getModuleManager().getModule(entry.getKey());
                final JsonObject jsonModule = (JsonObject) entry.getValue();
                if (module.isToggled() != jsonModule.get("Toggled").getAsBoolean()) {
                    module.setToggled(jsonModule.get("Toggled").getAsBoolean());
                }
                module.setKey(jsonModule.get("Key").getAsInt());
                for(Map.Entry<String, JsonElement> setting : jsonModule.get("Setting").getAsJsonObject().entrySet()) {
                    Setting s = module.getSetting(setting.getKey());
                    if (s != null) {
                        if (s instanceof TextSetting)
                            ((TextSetting) s).setText(setting.getValue().getAsString());
                        if (s instanceof ListSetting)
                            ((ListSetting) s).setCurrent(setting.getValue().getAsString());
                        if (s instanceof BooleanSetting)
                            ((BooleanSetting) s).set(setting.getValue().getAsBoolean());
                        if (s instanceof NumberSetting)
                            ((NumberSetting) s).setCurrent(setting.getValue().getAsDouble());
                    }
                }
            }
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
    }

}
