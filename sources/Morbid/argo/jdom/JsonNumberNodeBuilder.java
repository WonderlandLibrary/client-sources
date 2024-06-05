package argo.jdom;

final class JsonNumberNodeBuilder implements JsonNodeBuilder
{
    private final JsonNode builtNode;
    
    JsonNumberNodeBuilder(final String par1Str) {
        this.builtNode = JsonNodeFactories.aJsonNumber(par1Str);
    }
    
    @Override
    public JsonNode buildNode() {
        return this.builtNode;
    }
}
