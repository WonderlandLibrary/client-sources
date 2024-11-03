package net.silentclient.client.emotes.config;

import java.util.ArrayList;

public class EmotesConfigType {
    public ArrayList<EmotesConfigType.Bind> binds;

    public ArrayList<Bind> getBinds() {
        return binds;
    }

    public void addBind(int emoteId, int keyId) {
        int removeIndex = findBindByEmoteId(emoteId);
        if(removeIndex >= 0) {
            binds.remove(removeIndex);
        }
        if(keyId == -1) {
            removeBind(emoteId);
            return;
        }
        EmotesConfigType.Bind bind = new Bind();
        bind.emoteId = emoteId;
        bind.keyId = keyId;

        binds.add(bind);
        EmotesConfig.updateHashMap();
    }

    public void removeBind(int emoteId) {
        int removeIndex = findBindByEmoteId(emoteId);
        if(removeIndex >= 0) {
            binds.remove(removeIndex);
        }
        EmotesConfig.updateHashMap();
    }

    public int findBindByEmoteId(int emoteId) {
        int index = -1;
        for (Bind bind : binds) {
            index++;
            if (bind.emoteId == emoteId) {
                return index;
            }
        }
        return -1; // Если элемент не найден
    }

    public class Bind {
        public int emoteId;
        public int keyId;
    }

    public static EmotesConfigType getDefault() {
        EmotesConfigType config = new EmotesConfigType();
        config.binds = new ArrayList<>();

        return config;
    }
}
