package argo.jdom;

import java.util.*;

public final class JsonStringNode extends JsonNode implements Comparable
{
    private final String value;
    
    JsonStringNode(final String par1Str) {
        if (par1Str == null) {
            throw new NullPointerException("Attempt to construct a JsonString with a null value.");
        }
        this.value = par1Str;
    }
    
    @Override
    public JsonNodeType getType() {
        return JsonNodeType.STRING;
    }
    
    @Override
    public String getText() {
        return this.value;
    }
    
    @Override
    public Map getFields() {
        throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
    }
    
    @Override
    public List getElements() {
        throw new IllegalStateException("Attempt to get elements on a JsonNode without elements.");
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        if (this == par1Obj) {
            return true;
        }
        if (par1Obj != null && this.getClass() == par1Obj.getClass()) {
            final JsonStringNode var2 = (JsonStringNode)par1Obj;
            return this.value.equals(var2.value);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.value.hashCode();
    }
    
    @Override
    public String toString() {
        return "JsonStringNode value:[" + this.value + "]";
    }
    
    public int func_74626_a(final JsonStringNode par1JsonStringNode) {
        return this.value.compareTo(par1JsonStringNode.value);
    }
    
    @Override
    public int compareTo(final Object par1Obj) {
        return this.func_74626_a((JsonStringNode)par1Obj);
    }
}
