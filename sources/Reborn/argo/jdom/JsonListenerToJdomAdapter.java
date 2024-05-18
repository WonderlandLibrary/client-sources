package argo.jdom;

import argo.saj.*;
import java.util.*;

final class JsonListenerToJdomAdapter implements JsonListener
{
    private final Stack stack;
    private JsonNodeBuilder root;
    
    JsonListenerToJdomAdapter() {
        this.stack = new Stack();
    }
    
    JsonRootNode getDocument() {
        return (JsonRootNode)this.root.buildNode();
    }
    
    @Override
    public void startDocument() {
    }
    
    @Override
    public void endDocument() {
    }
    
    @Override
    public void startArray() {
        final JsonArrayNodeBuilder var1 = JsonNodeBuilders.anArrayBuilder();
        this.addRootNode(var1);
        this.stack.push(new JsonListenerToJdomAdapter_Array(this, var1));
    }
    
    @Override
    public void endArray() {
        this.stack.pop();
    }
    
    @Override
    public void startObject() {
        final JsonObjectNodeBuilder var1 = JsonNodeBuilders.anObjectBuilder();
        this.addRootNode(var1);
        this.stack.push(new JsonListenerToJdomAdapter_Object(this, var1));
    }
    
    @Override
    public void endObject() {
        this.stack.pop();
    }
    
    @Override
    public void startField(final String par1Str) {
        final JsonFieldBuilder var2 = JsonFieldBuilder.aJsonFieldBuilder().withKey(JsonNodeBuilders.func_74710_b(par1Str));
        this.stack.peek().addField(var2);
        this.stack.push(new JsonListenerToJdomAdapter_Field(this, var2));
    }
    
    @Override
    public void endField() {
        this.stack.pop();
    }
    
    @Override
    public void numberValue(final String par1Str) {
        this.addValue(JsonNodeBuilders.func_74712_a(par1Str));
    }
    
    @Override
    public void trueValue() {
        this.addValue(JsonNodeBuilders.func_74713_b());
    }
    
    @Override
    public void stringValue(final String par1Str) {
        this.addValue(JsonNodeBuilders.func_74710_b(par1Str));
    }
    
    @Override
    public void falseValue() {
        this.addValue(JsonNodeBuilders.func_74709_c());
    }
    
    @Override
    public void nullValue() {
        this.addValue(JsonNodeBuilders.func_74714_a());
    }
    
    private void addRootNode(final JsonNodeBuilder par1JsonNodeBuilder) {
        if (this.root == null) {
            this.root = par1JsonNodeBuilder;
        }
        else {
            this.addValue(par1JsonNodeBuilder);
        }
    }
    
    private void addValue(final JsonNodeBuilder par1JsonNodeBuilder) {
        this.stack.peek().addNode(par1JsonNodeBuilder);
    }
}
