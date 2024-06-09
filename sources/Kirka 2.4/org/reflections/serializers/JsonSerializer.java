/*
 * Decompiled with CFR 0.143.
 */
package org.reflections.serializers;

import com.google.common.base.Supplier;
import com.google.common.collect.Multimap;
import com.google.common.collect.Multimaps;
import com.google.common.collect.SetMultimap;
import com.google.common.collect.Sets;
import com.google.common.io.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.reflections.Reflections;
import org.reflections.serializers.Serializer;
import org.reflections.util.Utils;

public class JsonSerializer
implements Serializer {
    private Gson gson;

    public Reflections read(InputStream inputStream) {
        return (Reflections)this.getGson().fromJson((Reader)new InputStreamReader(inputStream), Reflections.class);
    }

    public File save(Reflections reflections, String filename) {
        try {
            File file = Utils.prepareFile(filename);
            Files.write((CharSequence)this.toString(reflections), (File)file, (Charset)Charset.defaultCharset());
            return file;
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public String toString(Reflections reflections) {
        return this.getGson().toJson((Object)reflections);
    }

    private Gson getGson() {
        if (this.gson == null) {
            this.gson = new GsonBuilder().registerTypeAdapter(Multimap.class, (Object)new com.google.gson.JsonSerializer<Multimap>(){

                public JsonElement serialize(Multimap multimap, Type type, JsonSerializationContext jsonSerializationContext) {
                    return jsonSerializationContext.serialize((Object)multimap.asMap());
                }
            }).registerTypeAdapter(Multimap.class, (Object)new JsonDeserializer<Multimap>(){

                public Multimap deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
                    SetMultimap map = Multimaps.newSetMultimap(new HashMap(), (Supplier)new Supplier<Set<String>>(){

                        public Set<String> get() {
                            return Sets.newHashSet();
                        }
                    });
                    for (Map.Entry entry : ((JsonObject)jsonElement).entrySet()) {
                        for (JsonElement element : (JsonArray)entry.getValue()) {
                            map.get(entry.getKey()).add(element.getAsString());
                        }
                    }
                    return map;
                }

            }).setPrettyPrinting().create();
        }
        return this.gson;
    }

}

