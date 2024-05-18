package argo.jdom;

import java.util.*;

public final class JsonObjectNodeBuilder implements JsonNodeBuilder
{
    private final List fieldBuilders;
    
    public JsonObjectNodeBuilder() {
        this.fieldBuilders = new LinkedList();
    }
    
    public JsonObjectNodeBuilder withFieldBuilder(final JsonFieldBuilder par1JsonFieldBuilder) {
        this.fieldBuilders.add(par1JsonFieldBuilder);
        return this;
    }
    
    public JsonRootNode func_74606_a() {
        return JsonNodeFactories.aJsonObject(new JsonObjectNodeBuilder_List(this));
    }
    
    @Override
    public JsonNode buildNode() {
        return this.func_74606_a();
    }
    
    static List func_74607_a(final JsonObjectNodeBuilder par0JsonObjectNodeBuilder) {
        return par0JsonObjectNodeBuilder.fieldBuilders;
    }
}
