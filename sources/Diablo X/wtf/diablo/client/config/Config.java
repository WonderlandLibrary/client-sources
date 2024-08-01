package wtf.diablo.client.config;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import wtf.diablo.client.core.api.ClientInformation;
import wtf.diablo.client.core.impl.Diablo;
import wtf.diablo.client.gui.draggable.AbstractDraggableElement;
import wtf.diablo.client.module.api.management.IModule;
import wtf.diablo.client.setting.api.AbstractSetting;

public final class Config {
    private final String name;

    public Config(final String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public JsonObject constructJSON() {
        final ClientInformation clientInformation = Diablo.getInstance().getClientInformation();

        final JsonObject json = new JsonObject();
        json.addProperty("name", this.name);
        json.addProperty("client_name", clientInformation.getDisplayName());

        final JsonArray modules = new JsonArray();

        for(final IModule module : Diablo.getInstance().getModuleRepository().getModules()) {
            final JsonObject moduleJson = new JsonObject();
            moduleJson.addProperty("name", module.getName());
            moduleJson.addProperty("key", module.getKey());
            moduleJson.addProperty("enabled", module.isEnabled());

            final JsonArray settings = new JsonArray();

            for(final AbstractSetting<?> setting : module.getSettingList()) {
                final JsonObject settingJson = new JsonObject();
                settingJson.addProperty("name", setting.getName());
                settingJson.addProperty("value", setting.getValue().toString());

                settings.add(settingJson);
            }

            moduleJson.add("settings", settings);

            modules.add(moduleJson);
        }

        final JsonArray elements = new JsonArray();

        for (final AbstractDraggableElement element : Diablo.getInstance().getDraggableElementHandler().getDraggableElements()) {
            final JsonObject elementJson = new JsonObject();
            elementJson.addProperty("name", element.getName());
            elementJson.addProperty("x", element.getX());
            elementJson.addProperty("y", element.getY());


            elements.add(elementJson);
        }

        json.add("modules", modules);
        json.add("elements", elements);

        return json;
    }

    public void parseJSON(final JsonObject json) {
        if (json.has("client_name")) {
            final String clientName = json.get("client_name").getAsString();

            final ClientInformation clientInformation = Diablo.getInstance().getClientInformation();

            clientInformation.setDisplayName(clientName);
        }

        final JsonArray modules = json.get("modules").getAsJsonArray();

        for(final JsonElement moduleElement : modules) {
            try {
                final JsonObject moduleJson = moduleElement.getAsJsonObject();
                final String name = moduleJson.get("name").getAsString();
                final int key = moduleJson.get("key").getAsInt();
                final boolean enabled = moduleJson.get("enabled").getAsBoolean();

                final IModule module = Diablo.getInstance().getModuleRepository().getModuleByName(name);

                if(module == null)
                    continue;

                try {
                    module.toggle(enabled);
                } catch (final Exception e) {
                    e.printStackTrace();
                }
                module.setKey(key);

                final JsonArray settings = moduleJson.get("settings").getAsJsonArray();

                for (final JsonElement settingElement : settings) {
                    final JsonObject settingJson = settingElement.getAsJsonObject();

                    final String settingName = settingJson.get("name").getAsString();
                    final String settingValue = settingJson.get("value").getAsString();

                    final AbstractSetting setting = module.getSettingByName(settingName);
                    setting.setValue(setting.parse(settingValue));
                }
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }

        final JsonArray elements = json.get("elements").getAsJsonArray();

        for(final JsonElement element : elements) {
            try {
                final JsonObject elementJson = element.getAsJsonObject();
                final String name = elementJson.get("name").getAsString();
                final int x = elementJson.get("x").getAsInt();
                final int y = elementJson.get("y").getAsInt();

                final AbstractDraggableElement draggableElement = Diablo.getInstance().getDraggableElementHandler().getDraggableElementByName(name);
                draggableElement.setPos(x, y);
            } catch (final Exception e) {
                e.printStackTrace();
            }
        }
    }
}
