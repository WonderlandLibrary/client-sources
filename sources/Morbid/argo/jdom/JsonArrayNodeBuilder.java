package argo.jdom;

import java.util.*;

public final class JsonArrayNodeBuilder implements JsonNodeBuilder
{
    private final List elementBuilders;
    
    public JsonArrayNodeBuilder() {
        this.elementBuilders = new LinkedList();
    }
    
    public JsonArrayNodeBuilder withElement(final JsonNodeBuilder par1JsonNodeBuilder) {
        this.elementBuilders.add(par1JsonNodeBuilder);
        return this;
    }
    
    public JsonRootNode build() {
        final LinkedList var1 = new LinkedList();
        for (final JsonNodeBuilder var3 : this.elementBuilders) {
            var1.add(var3.buildNode());
        }
        return JsonNodeFactories.aJsonArray(var1);
    }
    
    @Override
    public JsonNode buildNode() {
        return this.build();
    }
}
