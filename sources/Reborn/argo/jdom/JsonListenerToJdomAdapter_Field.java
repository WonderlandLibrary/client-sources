package argo.jdom;

class JsonListenerToJdomAdapter_Field implements JsonListenerToJdomAdapter_NodeContainer
{
    final JsonFieldBuilder fieldBuilder;
    final JsonListenerToJdomAdapter listenerToJdomAdapter;
    
    JsonListenerToJdomAdapter_Field(final JsonListenerToJdomAdapter par1JsonListenerToJdomAdapter, final JsonFieldBuilder par2JsonFieldBuilder) {
        this.listenerToJdomAdapter = par1JsonListenerToJdomAdapter;
        this.fieldBuilder = par2JsonFieldBuilder;
    }
    
    @Override
    public void addNode(final JsonNodeBuilder par1JsonNodeBuilder) {
        this.fieldBuilder.withValue(par1JsonNodeBuilder);
    }
    
    @Override
    public void addField(final JsonFieldBuilder par1JsonFieldBuilder) {
        throw new RuntimeException("Coding failure in Argo:  Attempt to add a field to a field.");
    }
}
