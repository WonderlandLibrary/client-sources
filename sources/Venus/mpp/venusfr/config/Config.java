/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.config;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.settings.Setting;
import mpp.venusfr.functions.settings.impl.BindSetting;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.ColorSetting;
import mpp.venusfr.functions.settings.impl.ModeListSetting;
import mpp.venusfr.functions.settings.impl.ModeSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.functions.settings.impl.StringSetting;
import mpp.venusfr.ui.styles.Style;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.venusfr;
import net.minecraft.client.Minecraft;

public class Config
implements IMinecraft {
    private final File file;
    private final String name;

    public Config(String string) {
        this.name = string;
        this.file = new File(new File(Minecraft.getInstance().gameDir, "\\venusfr\\configs"), string + ".cfg");
    }

    public void loadConfig(JsonObject jsonObject) {
        if (jsonObject == null) {
            return;
        }
        if (jsonObject.has("functions")) {
            this.loadFunctionSettings(jsonObject.getAsJsonObject("functions"));
        }
        if (jsonObject.has("styles")) {
            this.loadStyleSettings(jsonObject.getAsJsonObject("styles"));
        }
    }

    private void loadStyleSettings(JsonObject jsonObject) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            boolean bl;
            String string = entry.getKey();
            JsonObject jsonObject2 = entry.getValue().getAsJsonObject();
            Style style = this.findStyleByName(string);
            if (style == null || !jsonObject2.has("selected") || !(bl = jsonObject2.get("selected").getAsBoolean())) continue;
            venusfr.getInstance().getStyleManager().setCurrentStyle(style);
        }
    }

    private Style findStyleByName(String string) {
        for (Style style : venusfr.getInstance().getStyleManager().getStyleList()) {
            if (!style.getStyleName().equalsIgnoreCase(string)) continue;
            return style;
        }
        return null;
    }

    private void loadFunctionSettings(JsonObject jsonObject) {
        venusfr.getInstance().getFunctionRegistry().getFunctions().forEach(arg_0 -> this.lambda$loadFunctionSettings$3(jsonObject, arg_0));
    }

    private void loadIndividualSetting(JsonObject jsonObject, Setting<?> setting) {
        JsonElement jsonElement = jsonObject.get(setting.getName());
        if (jsonElement == null || jsonElement.isJsonNull()) {
            return;
        }
        if (setting instanceof SliderSetting) {
            ((SliderSetting)setting).set(Float.valueOf(jsonElement.getAsFloat()));
        }
        if (setting instanceof BooleanSetting) {
            ((BooleanSetting)setting).set(jsonElement.getAsBoolean());
        }
        if (setting instanceof ColorSetting) {
            ((ColorSetting)setting).set(jsonElement.getAsInt());
        }
        if (setting instanceof ModeSetting) {
            ((ModeSetting)setting).set(jsonElement.getAsString());
        }
        if (setting instanceof BindSetting) {
            ((BindSetting)setting).set(jsonElement.getAsInt());
        }
        if (setting instanceof StringSetting) {
            ((StringSetting)setting).set(jsonElement.getAsString());
        }
        if (setting instanceof ModeListSetting) {
            this.loadModeListSetting((ModeListSetting)setting, jsonObject);
        }
    }

    private void loadModeListSetting(ModeListSetting modeListSetting, JsonObject jsonObject) {
        JsonObject jsonObject2 = jsonObject.getAsJsonObject(modeListSetting.getName());
        ((List)modeListSetting.get()).forEach(arg_0 -> Config.lambda$loadModeListSetting$4(jsonObject2, arg_0));
    }

    private void loadSettingFromJson(JsonObject jsonObject, String string, Consumer<JsonElement> consumer) {
        JsonElement jsonElement = jsonObject.get(string);
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            consumer.accept(jsonElement);
        }
    }

    public JsonElement saveConfig() {
        JsonObject jsonObject = new JsonObject();
        JsonObject jsonObject2 = new JsonObject();
        this.saveFunctionSettings(jsonObject);
        this.saveStyleSettings(jsonObject2);
        JsonObject jsonObject3 = new JsonObject();
        jsonObject3.add("functions", jsonObject);
        jsonObject3.add("styles", jsonObject2);
        return jsonObject3;
    }

    private void saveFunctionSettings(JsonObject jsonObject) {
        venusfr.getInstance().getFunctionRegistry().getFunctions().forEach(arg_0 -> this.lambda$saveFunctionSettings$6(jsonObject, arg_0));
    }

    private void saveIndividualSetting(JsonObject jsonObject, Setting<?> setting) {
        if (setting instanceof BooleanSetting) {
            jsonObject.addProperty(setting.getName(), (Boolean)((BooleanSetting)setting).get());
        }
        if (setting instanceof SliderSetting) {
            jsonObject.addProperty(setting.getName(), (Number)((SliderSetting)setting).get());
        }
        if (setting instanceof ModeSetting) {
            jsonObject.addProperty(setting.getName(), (String)((ModeSetting)setting).get());
        }
        if (setting instanceof ColorSetting) {
            jsonObject.addProperty(setting.getName(), (Number)((ColorSetting)setting).get());
        }
        if (setting instanceof BindSetting) {
            jsonObject.addProperty(setting.getName(), (Number)((BindSetting)setting).get());
        }
        if (setting instanceof StringSetting) {
            jsonObject.addProperty(setting.getName(), (String)((StringSetting)setting).get());
        }
        if (setting instanceof ModeListSetting) {
            this.saveModeListSetting(jsonObject, (ModeListSetting)setting);
        }
    }

    private void saveModeListSetting(JsonObject jsonObject, ModeListSetting modeListSetting) {
        JsonObject jsonObject2 = new JsonObject();
        ((List)modeListSetting.get()).forEach(arg_0 -> Config.lambda$saveModeListSetting$7(jsonObject2, arg_0));
        jsonObject.add(modeListSetting.getName(), jsonObject2);
    }

    private void saveStyleSettings(JsonObject jsonObject) {
        for (Style style : venusfr.getInstance().getStyleManager().getStyleList()) {
            JsonObject jsonObject2 = new JsonObject();
            jsonObject2.addProperty("selected", venusfr.getInstance().getStyleManager().getCurrentStyle() == style);
            jsonObject.add(style.getStyleName(), jsonObject2);
        }
    }

    public File getFile() {
        return this.file;
    }

    public String getName() {
        return this.name;
    }

    private static void lambda$saveModeListSetting$7(JsonObject jsonObject, BooleanSetting booleanSetting) {
        jsonObject.addProperty(booleanSetting.getName(), (Boolean)booleanSetting.get());
    }

    private void lambda$saveFunctionSettings$6(JsonObject jsonObject, Function function) {
        JsonObject jsonObject2 = new JsonObject();
        jsonObject2.addProperty("bind", function.getBind());
        jsonObject2.addProperty("state", function.isState());
        function.getSettings().forEach(arg_0 -> this.lambda$saveFunctionSettings$5(jsonObject2, arg_0));
        jsonObject.add(function.getName().toLowerCase(), jsonObject2);
    }

    private void lambda$saveFunctionSettings$5(JsonObject jsonObject, Setting setting) {
        this.saveIndividualSetting(jsonObject, setting);
    }

    private static void lambda$loadModeListSetting$4(JsonObject jsonObject, BooleanSetting booleanSetting) {
        JsonElement jsonElement = jsonObject.get(booleanSetting.getName());
        if (jsonElement != null && !jsonElement.isJsonNull()) {
            booleanSetting.set(jsonElement.getAsBoolean());
        }
    }

    private void lambda$loadFunctionSettings$3(JsonObject jsonObject, Function function) {
        JsonObject jsonObject2 = jsonObject.getAsJsonObject(function.getName().toLowerCase());
        if (jsonObject2 == null) {
            return;
        }
        function.setState(false, false);
        this.loadSettingFromJson(jsonObject2, "bind", arg_0 -> Config.lambda$loadFunctionSettings$0(function, arg_0));
        this.loadSettingFromJson(jsonObject2, "state", arg_0 -> Config.lambda$loadFunctionSettings$1(function, arg_0));
        function.getSettings().forEach(arg_0 -> this.lambda$loadFunctionSettings$2(jsonObject2, arg_0));
    }

    private void lambda$loadFunctionSettings$2(JsonObject jsonObject, Setting setting) {
        this.loadIndividualSetting(jsonObject, setting);
    }

    private static void lambda$loadFunctionSettings$1(Function function, JsonElement jsonElement) {
        function.setState(jsonElement.getAsBoolean(), false);
    }

    private static void lambda$loadFunctionSettings$0(Function function, JsonElement jsonElement) {
        function.setBind(jsonElement.getAsInt());
    }
}

