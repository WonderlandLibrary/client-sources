package de.dietrichpaul.clientbase.config.list;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.config.ext.json.JsonObjectConfig;
import net.minecraft.client.util.InputUtil;

public class BindConfig extends JsonObjectConfig {

    public BindConfig() {
        super("bind", ConfigType.PRE);
    }

    @Override
    protected void read(JsonObject element) {
        element.asMap().forEach((s, jsonElement) -> {
            InputUtil.Key key = InputUtil.fromTranslationKey(s);
            for (JsonElement feature : jsonElement.getAsJsonArray()) {
                ClientBase.INSTANCE.getKeybindingList().bindWithoutSaving(key, feature.getAsString());
            }
        });
    }

    @Override
    protected void write(JsonObject element) {
        ClientBase.INSTANCE.getKeybindingList().getBindings().forEach((key, strings) -> {
            JsonArray array = new JsonArray();
            strings.forEach(array::add);
            element.add(key.getTranslationKey(), array);
        });
    }
}
