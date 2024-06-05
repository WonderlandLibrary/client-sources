package argo.jdom;

class JsonListenerToJdomAdapter_Object implements JsonListenerToJdomAdapter_NodeContainer
{
    final JsonObjectNodeBuilder nodeBuilder;
    final JsonListenerToJdomAdapter listenerToJdomAdapter;
    
    JsonListenerToJdomAdapter_Object(final JsonListenerToJdomAdapter par1JsonListenerToJdomAdapter, final JsonObjectNodeBuilder par2JsonObjectNodeBuilder) {
        this.listenerToJdomAdapter = par1JsonListenerToJdomAdapter;
        this.nodeBuilder = par2JsonObjectNodeBuilder;
    }
    
    @Override
    public void addNode(final JsonNodeBuilder par1JsonNodeBuilder) {
        throw new RuntimeException("Coding failure in Argo:  Attempt to add a node to an object.");
    }
    
    @Override
    public void addField(final JsonFieldBuilder par1JsonFieldBuilder) {
        this.nodeBuilder.withFieldBuilder(par1JsonFieldBuilder);
    }
}
