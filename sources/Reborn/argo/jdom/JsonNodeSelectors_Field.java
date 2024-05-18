package argo.jdom;

import java.util.*;

final class JsonNodeSelectors_Field extends LeafFunctor
{
    final JsonStringNode theFieldName;
    
    JsonNodeSelectors_Field(final JsonStringNode par1JsonStringNode) {
        this.theFieldName = par1JsonStringNode;
    }
    
    public boolean func_74641_a(final Map par1Map) {
        return par1Map.containsKey(this.theFieldName);
    }
    
    @Override
    public String shortForm() {
        return "\"" + this.theFieldName.getText() + "\"";
    }
    
    public JsonNode typeSafeApplyTo(final Map par1Map) {
        return par1Map.get(this.theFieldName);
    }
    
    @Override
    public String toString() {
        return "a field called [\"" + this.theFieldName.getText() + "\"]";
    }
    
    public Object typeSafeApplyTo(final Object par1Obj) {
        return this.typeSafeApplyTo((Map)par1Obj);
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.func_74641_a((Map)par1Obj);
    }
}
