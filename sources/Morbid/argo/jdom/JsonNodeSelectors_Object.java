package argo.jdom;

import java.util.*;

final class JsonNodeSelectors_Object extends LeafFunctor
{
    public boolean func_74640_a(final JsonNode par1JsonNode) {
        return JsonNodeType.OBJECT == par1JsonNode.getType();
    }
    
    @Override
    public String shortForm() {
        return "A short form object";
    }
    
    public Map func_74639_b(final JsonNode par1JsonNode) {
        return par1JsonNode.getFields();
    }
    
    @Override
    public String toString() {
        return "an object";
    }
    
    public Object typeSafeApplyTo(final Object par1Obj) {
        return this.func_74639_b((JsonNode)par1Obj);
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.func_74640_a((JsonNode)par1Obj);
    }
}
