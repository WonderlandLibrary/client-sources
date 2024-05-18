package argo.jdom;

import java.util.regex.*;
import java.util.*;

final class JsonNumberNode extends JsonNode
{
    private static final Pattern PATTERN;
    private final String value;
    
    static {
        PATTERN = Pattern.compile("(-?)(0|([1-9]([0-9]*)))(\\.[0-9]+)?((e|E)(\\+|-)?[0-9]+)?");
    }
    
    JsonNumberNode(final String par1Str) {
        if (par1Str == null) {
            throw new NullPointerException("Attempt to construct a JsonNumber with a null value.");
        }
        if (!JsonNumberNode.PATTERN.matcher(par1Str).matches()) {
            throw new IllegalArgumentException("Attempt to construct a JsonNumber with a String [" + par1Str + "] that does not match the JSON number specification.");
        }
        this.value = par1Str;
    }
    
    @Override
    public JsonNodeType getType() {
        return JsonNodeType.NUMBER;
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
            final JsonNumberNode var2 = (JsonNumberNode)par1Obj;
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
        return "JsonNumberNode value:[" + this.value + "]";
    }
}
