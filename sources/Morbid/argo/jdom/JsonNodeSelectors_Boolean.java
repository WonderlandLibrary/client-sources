package argo.jdom;

final class JsonNodeSelectors_Boolean extends LeafFunctor
{
    public boolean func_98312_a(final JsonNode par1JsonNode) {
        return JsonNodeType.TRUE == par1JsonNode.getType() || JsonNodeType.FALSE == par1JsonNode.getType();
    }
    
    @Override
    public String shortForm() {
        return "A short form boolean";
    }
    
    public Boolean func_98311_b(final JsonNode par1JsonNode) {
        return JsonNodeType.TRUE == par1JsonNode.getType();
    }
    
    @Override
    public String toString() {
        return "a true or false";
    }
    
    public Object typeSafeApplyTo(final Object par1Obj) {
        return this.func_98311_b((JsonNode)par1Obj);
    }
    
    @Override
    public boolean matchesNode(final Object par1Obj) {
        return this.func_98312_a((JsonNode)par1Obj);
    }
}
