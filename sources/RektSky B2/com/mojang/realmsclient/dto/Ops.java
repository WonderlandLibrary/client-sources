package com.mojang.realmsclient.dto;

import com.google.gson.*;
import java.util.*;

public class Ops
{
    public Set<String> ops;
    
    public Ops() {
        this.ops = new HashSet<String>();
    }
    
    public static Ops parse(final String json) {
        final Ops ops = new Ops();
        final JsonParser parser = new JsonParser();
        try {
            final JsonElement jsonElement = parser.parse(json);
            final JsonObject jsonObject = jsonElement.getAsJsonObject();
            final JsonElement opsArray = jsonObject.get("ops");
            if (opsArray.isJsonArray()) {
                for (final JsonElement jsonElement2 : opsArray.getAsJsonArray()) {
                    ops.ops.add(jsonElement2.getAsString());
                }
            }
        }
        catch (Exception ex) {}
        return ops;
    }
}
