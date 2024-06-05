package com.google.gson;

import com.google.gson.stream.*;
import java.io.*;
import com.google.gson.internal.*;

public final class JsonParser
{
    @Deprecated
    public JsonParser() {
    }
    
    public static JsonElement parseString(final String json) throws JsonSyntaxException {
        return parseReader(new StringReader(json));
    }
    
    public static JsonElement parseReader(final Reader reader) throws JsonIOException, JsonSyntaxException {
        try {
            final JsonReader jsonReader = new JsonReader(reader);
            final JsonElement element = parseReader(jsonReader);
            if (!element.isJsonNull() && jsonReader.peek() != JsonToken.END_DOCUMENT) {
                throw new JsonSyntaxException("Did not consume the entire document.");
            }
            return element;
        }
        catch (MalformedJsonException e) {
            throw new JsonSyntaxException(e);
        }
        catch (IOException e2) {
            throw new JsonIOException(e2);
        }
        catch (NumberFormatException e3) {
            throw new JsonSyntaxException(e3);
        }
    }
    
    public static JsonElement parseReader(final JsonReader reader) throws JsonIOException, JsonSyntaxException {
        final boolean lenient = reader.isLenient();
        reader.setLenient(true);
        try {
            return Streams.parse(reader);
        }
        catch (StackOverflowError e) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e);
        }
        catch (OutOfMemoryError e2) {
            throw new JsonParseException("Failed parsing JSON source: " + reader + " to Json", e2);
        }
        finally {
            reader.setLenient(lenient);
        }
    }
    
    @Deprecated
    public JsonElement parse(final String json) throws JsonSyntaxException {
        return parseString(json);
    }
    
    @Deprecated
    public JsonElement parse(final Reader json) throws JsonIOException, JsonSyntaxException {
        return parseReader(json);
    }
    
    @Deprecated
    public JsonElement parse(final JsonReader json) throws JsonIOException, JsonSyntaxException {
        return parseReader(json);
    }
}
