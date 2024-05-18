package argo.jdom;

class JsonListenerToJdomAdapter_Array implements JsonListenerToJdomAdapter_NodeContainer
{
    final JsonArrayNodeBuilder nodeBuilder;
    final JsonListenerToJdomAdapter listenerToJdomAdapter;
    
    JsonListenerToJdomAdapter_Array(final JsonListenerToJdomAdapter par1JsonListenerToJdomAdapter, final JsonArrayNodeBuilder par2JsonArrayNodeBuilder) {
        this.listenerToJdomAdapter = par1JsonListenerToJdomAdapter;
        this.nodeBuilder = par2JsonArrayNodeBuilder;
    }
    
    @Override
    public void addNode(final JsonNodeBuilder par1JsonNodeBuilder) {
        this.nodeBuilder.withElement(par1JsonNodeBuilder);
    }
    
    @Override
    public void addField(final JsonFieldBuilder par1JsonFieldBuilder) {
        throw new RuntimeException("Coding failure in Argo:  Attempt to add a field to an array.");
    }
}
