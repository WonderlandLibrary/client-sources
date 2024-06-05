package de.dietrichpaul.clientbase.config.list;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.config.ConfigType;
import de.dietrichpaul.clientbase.config.ext.json.JsonArrayConfig;

public class FriendConfig extends JsonArrayConfig {

    public FriendConfig() {
        super("friend", ConfigType.PRE);
    }

    @Override
    protected void read(JsonArray element) {
        for (JsonElement jsonElement : element) {
            ClientBase.INSTANCE.getFriendList().add(jsonElement.getAsString());
        }
    }

    @Override
    protected void write(JsonArray element) {
        for (String friend : ClientBase.INSTANCE.getFriendList().getFriends()) {
            element.add(friend);
        }
    }
}
