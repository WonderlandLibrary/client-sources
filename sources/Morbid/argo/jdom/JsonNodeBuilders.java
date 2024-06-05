package argo.jdom;

public final class JsonNodeBuilders
{
    public static JsonNodeBuilder func_74714_a() {
        return new JsonNodeBuilders_Null();
    }
    
    public static JsonNodeBuilder func_74713_b() {
        return new JsonNodeBuilders_True();
    }
    
    public static JsonNodeBuilder func_74709_c() {
        return new JsonNodeBuilders_False();
    }
    
    public static JsonNodeBuilder func_74712_a(final String par0Str) {
        return new JsonNumberNodeBuilder(par0Str);
    }
    
    public static JsonStringNodeBuilder func_74710_b(final String par0Str) {
        return new JsonStringNodeBuilder(par0Str);
    }
    
    public static JsonObjectNodeBuilder anObjectBuilder() {
        return new JsonObjectNodeBuilder();
    }
    
    public static JsonArrayNodeBuilder anArrayBuilder() {
        return new JsonArrayNodeBuilder();
    }
}
