package net.silentclient.client.emotes;

import java.util.ArrayList;
import java.util.List;

public class EmoteData {
    public List<String> emoteUsers = new ArrayList<>();
    public String emoteName;

    public EmoteData(String emoteName) {
        this.emoteName = emoteName;
    }
}
