package argo.jdom;

import java.util.*;

final class JsonObject extends JsonRootNode
{
    private final Map fields;
    
    JsonObject(final Map par1Map) {
        this.fields = new HashMap(par1Map);
    }
    
    @Override
    public Map getFields() {
        return new HashMap(this.fields);
    }
    
    @Override
    public JsonNodeType getType() {
        return JsonNodeType.OBJECT;
    }
    
    @Override
    public String getText() {
        throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
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
            final JsonObject var2 = (JsonObject)par1Obj;
            return this.fields.equals(var2.fields);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return this.fields.hashCode();
    }
    
    @Override
    public String toString() {
        return "JsonObject fields:[" + this.fields + "]";
    }
}
