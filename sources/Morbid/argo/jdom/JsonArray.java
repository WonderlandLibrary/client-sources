package argo.jdom;

import java.util.*;

final class JsonArray extends JsonRootNode
{
    private final List elements;
    
    JsonArray(final Iterable par1Iterable) {
        this.elements = asList(par1Iterable);
    }
    
    @Override
    public JsonNodeType getType() {
        return JsonNodeType.ARRAY;
    }
    
    @Override
    public List getElements() {
        return new ArrayList(this.elements);
    }
    
    @Override
    public String getText() {
        throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
    }
    
    @Override
    public Map getFields() {
        throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (par1Obj != null && this.getClass() == par1Obj.getClass()) {
            final JsonArray var2 = (JsonArray)par1Obj;
            return this.elements.equals(var2.elements);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.elements.hashCode();
    }
    
    @Override
    public String toString() {
        return "JsonArray elements:[" + this.elements + "]";
    }
    
    private static List asList(final Iterable par0Iterable) {
        return new JsonArray_NodeList(par0Iterable);
    }
}
