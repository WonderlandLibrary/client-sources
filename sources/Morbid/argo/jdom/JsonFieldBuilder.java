package argo.jdom;

final class JsonFieldBuilder
{
    private JsonNodeBuilder key;
    private JsonNodeBuilder valueBuilder;
    
    static JsonFieldBuilder aJsonFieldBuilder() {
        return new JsonFieldBuilder();
    }
    
    JsonFieldBuilder withKey(final JsonNodeBuilder par1JsonNodeBuilder) {
        this.key = par1JsonNodeBuilder;
        return this;
    }
    
    JsonFieldBuilder withValue(final JsonNodeBuilder par1JsonNodeBuilder) {
        this.valueBuilder = par1JsonNodeBuilder;
        return this;
    }
    
    JsonStringNode func_74724_b() {
        return (JsonStringNode)this.key.buildNode();
    }
    
    JsonNode buildValue() {
        return this.valueBuilder.buildNode();
    }
}
