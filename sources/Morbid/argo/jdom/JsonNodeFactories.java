package argo.jdom;

import java.util.*;

public final class JsonNodeFactories
{
    public static JsonNode aJsonNull() {
        return JsonConstants.NULL;
    }
    
    public static JsonNode aJsonTrue() {
        return JsonConstants.TRUE;
    }
    
    public static JsonNode aJsonFalse() {
        return JsonConstants.FALSE;
    }
    
    public static JsonStringNode aJsonString(final String par0Str) {
        return new JsonStringNode(par0Str);
    }
    
    public static JsonNode aJsonNumber(final String par0Str) {
        return new JsonNumberNode(par0Str);
    }
    
    public static JsonRootNode aJsonArray(final Iterable par0Iterable) {
        return new JsonArray(par0Iterable);
    }
    
    public static JsonRootNode aJsonArray(final JsonNode... par0ArrayOfJsonNode) {
        return aJsonArray(Arrays.asList(par0ArrayOfJsonNode));
    }
    
    public static JsonRootNode aJsonObject(final Map par0Map) {
        return new JsonObject(par0Map);
    }
}
