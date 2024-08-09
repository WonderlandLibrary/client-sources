package xyz.mathax.mathaxclient.utils.misc;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;

import java.lang.reflect.Type;
import java.time.Instant;
import java.util.Date;

public class JsonDateDeserializer implements JsonDeserializer<Date> {
    @Override
    public Date deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        try {
            return Date.from(Instant.parse(jsonElement.getAsString()));
        }
        catch (Exception ignored) {
            return null;
        }
    }
}