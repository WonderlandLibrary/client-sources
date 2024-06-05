package club.mega.util;

import club.mega.Mega;
import club.mega.interfaces.MinecraftInterface;

import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.Setting;
import club.mega.module.setting.impl.*;
import com.google.gson.*;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

public final class ConfigUtil implements MinecraftInterface {

    public static void save(final String name) {
        final JsonObject main = new JsonObject();
        for (final Module m : Mega.INSTANCE.getModuleManager().getModules())
        {
            if (m.getCategory() == Category.VISUAL || m.getCategory() == Category.HUD)
                continue;

            JsonObject module = new JsonObject();
            JsonObject setting = new JsonObject();
            for (final Setting s : m.getSettings())
            {
                if (!s.isConfigurable())
                    continue;

                if (s instanceof ListSetting)
                    setting.addProperty(s.getName(), ((ListSetting) s).getCurrent());
                if (s instanceof BooleanSetting)
                    setting.addProperty(s.getName(), ((BooleanSetting) s).get());
                if (s instanceof NumberSetting)
                    setting.addProperty(s.getName(), ((NumberSetting) s).getAsDouble());
            }
            module.addProperty("Toggled", m.isToggled());
            module.add("Setting", setting);
            main.add(m.getName(), module);
        }
        final Gson gson = new GsonBuilder().setPrettyPrinting().create();
        try {
            final File file = new File(MC.mcDataDir, "Mega/Configs");
            if (file.mkdir())
                file.createNewFile();
            final String path = new File(MC.mcDataDir, "Mega/Configs").getAbsolutePath();
            final FileWriter fileWriter = new FileWriter(path + "/" + name + ".json");
            gson.toJson(main, fileWriter);
            fileWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load(final String name) {
        try {
            final String path = new File(MC.mcDataDir, "Mega/Configs").getAbsolutePath();
            final FileReader reader = new FileReader(path + "/" + name + ".json");
            final JsonObject object = new JsonParser().parse(reader).getAsJsonObject();
            reader.close();
            for (Map.Entry<String, JsonElement> entry : object.entrySet()) {
                Module module = Mega.INSTANCE.getModuleManager().getModule(entry.getKey());
                final JsonObject jsonModule = (JsonObject) entry.getValue();
                if (module.isToggled() != jsonModule.get("Toggled").getAsBoolean()) {
                    module.setToggled(jsonModule.get("Toggled").getAsBoolean());
                }
                for(Map.Entry<String, JsonElement> setting : jsonModule.get("Setting").getAsJsonObject().entrySet()) {
                    Setting s = module.getSetting(setting.getKey());
                    if (s != null) {
                        if (s instanceof ListSetting)
                            ((ListSetting) s).setCurrent(setting.getValue().getAsString());
                        if (s instanceof BooleanSetting)
                            ((BooleanSetting) s).set(setting.getValue().getAsBoolean());
                        if (s instanceof NumberSetting)
                            ((NumberSetting) s).setCurrent(setting.getValue().getAsDouble());

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
