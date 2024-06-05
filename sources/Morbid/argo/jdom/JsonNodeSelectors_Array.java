package argo.jdom;

import java.util.*;

final class JsonNodeSelectors_Array extends LeafFunctor
{
    public boolean matchesNode_(final JsonNode par1JsonNode) {
        return JsonNodeType.ARRAY == par1JsonNode.getType();
    }
    
    @Override
    public String shortForm() {
        return "A short form array";
    }
    
    public List typeSafeApplyTo(final JsonNode par1JsonNode) {
        return par1JsonNode.getElements();
    }
    
    @Override
    public String toString() {
        return "an array";
    }
    
    public Object typeSafeApplyTo(final Object par1Obj) {
        return this.typeSafeApplyTo((JsonNode)par1Obj);
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.matchesNode_((JsonNode)par1Obj);
    }
}
