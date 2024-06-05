// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.config;

import gg.childtrafficking.smokex.property.Property;
import java.util.List;
import java.util.Map;
import java.io.File;
import gg.childtrafficking.smokex.gui.font.CFontRenderer;
import java.awt.Font;
import java.util.Iterator;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.visuals.HUDModule;
import com.google.gson.JsonElement;
import gg.childtrafficking.smokex.module.Module;
import gg.childtrafficking.smokex.SmokeXClient;
import com.google.gson.JsonObject;

public class Config
{
    private final String name;
    
    public Config(final String name) {
        this.name = name;
    }
    
    public JsonObject serialize() {
        final JsonObject jsonObject = new JsonObject();
        for (final Module module : SmokeXClient.getInstance().getModuleManager().getModules()) {
            final JsonObject moduleObject = new JsonObject();
            moduleObject.addProperty("Keybind", (Number)module.getKey());
            moduleObject.addProperty("Toggled", Boolean.valueOf(module.isToggled()));
            moduleObject.addProperty("Hidden", Boolean.valueOf(module.isHidden()));
            final JsonObject propertyObject = new JsonObject();
            module.getProperties().forEach(property -> propertyObject.addProperty(property.getIdentifier(), property.getValueAsString()));
            moduleObject.add("Properties", (JsonElement)propertyObject);
            jsonObject.add(module.getName(), (JsonElement)moduleObject);
        }
        jsonObject.addProperty("watermark", ModuleManager.getInstance(HUDModule.class).watermark);
        jsonObject.addProperty("font", SmokeXClient.getInstance().font);
        return jsonObject;
    }
    
    public boolean deserialize(final JsonElement jsonElement) {
        try {
            final List<Module> antiStackOverflow = SmokeXClient.getInstance().getModuleManager().getModules();
            if (jsonElement.getAsJsonObject().get("font") != null) {
                final String font = jsonElement.getAsJsonObject().get("font").getAsString();
                if (font.equalsIgnoreCase("default")) {
                    try {
                        SmokeXClient.getInstance().fontRenderer = new CFontRenderer(Font.createFont(0, this.getClass().getResourceAsStream("/default.ttf")).deriveFont(20.0f));
                    }
                    catch (final Exception e) {
                        net.minecraft.src.Config.log(e.toString());
                    }
                }
                else {
                    try {
                        SmokeXClient.getInstance().fontRenderer = new CFontRenderer(Font.createFont(0, new File(SmokeXClient.getInstance().getFontDirectory(), font)).deriveFont(20.0f));
                    }
                    catch (final Exception e) {
                        net.minecraft.src.Config.log(e.toString());
                    }
                }
            }
            for (final Map.Entry<String, JsonElement> entry : jsonElement.getAsJsonObject().entrySet()) {
                for (final Module module : antiStackOverflow) {
                    if (module instanceof HUDModule) {
                        ((HUDModule)module).watermark = jsonElement.getAsJsonObject().get("watermark").getAsString();
                    }
                    if (entry.getKey().equalsIgnoreCase(module.getName())) {
                        final JsonObject jsonModule = (JsonObject)entry.getValue();
                        if (module.isToggled() != jsonModule.get("Toggled").getAsBoolean()) {
                            module.setToggled(jsonModule.get("Toggled").getAsBoolean());
                        }
                        module.setKey(jsonModule.get("Keybind").getAsInt());
                        module.setHidden(jsonModule.get("Hidden").getAsBoolean());
                        for (final Map.Entry<String, JsonElement> Entry : jsonModule.get("Properties").getAsJsonObject().entrySet()) {
                            final Property<?> property = module.getProperty(Entry.getKey());
                            if (property != null) {
                                property.setValueFromString(Entry.getValue().getAsString());
                            }
                        }
                    }
                }
            }
            return true;
        }
        catch (final Exception e2) {
            e2.printStackTrace();
            return false;
        }
    }
    
    public String getName() {
        return this.name;
    }
}
