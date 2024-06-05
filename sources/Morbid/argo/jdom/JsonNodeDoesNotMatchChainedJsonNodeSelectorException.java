package argo.jdom;

import java.util.*;

public final class JsonNodeDoesNotMatchChainedJsonNodeSelectorException extends JsonNodeDoesNotMatchJsonNodeSelectorException
{
    final Functor failedNode;
    final List failPath;
    
    static JsonNodeDoesNotMatchJsonNodeSelectorException func_74701_a(final Functor par0Functor) {
        return new JsonNodeDoesNotMatchChainedJsonNodeSelectorException(par0Functor, new LinkedList());
    }
    
    static JsonNodeDoesNotMatchJsonNodeSelectorException func_74699_a(final JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, final JsonNodeSelector par1JsonNodeSelector) {
        final LinkedList var2 = new LinkedList(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failPath);
        var2.add(par1JsonNodeSelector);
        return new JsonNodeDoesNotMatchChainedJsonNodeSelectorException(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failedNode, var2);
    }
    
    static JsonNodeDoesNotMatchJsonNodeSelectorException func_74698_b(final JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, final JsonNodeSelector par1JsonNodeSelector) {
        final LinkedList var2 = new LinkedList();
        var2.add(par1JsonNodeSelector);
        return new JsonNodeDoesNotMatchChainedJsonNodeSelectorException(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failedNode, var2);
    }
    
    private JsonNodeDoesNotMatchChainedJsonNodeSelectorException(final Functor par1Functor, final List par2List) {
        super("Failed to match any JSON node at [" + getShortFormFailPath(par2List) + "]");
        this.failedNode = par1Functor;
        this.failPath = par2List;
    }
    
    static String getShortFormFailPath(final List par0List) {
        final StringBuilder var1 = new StringBuilder();
        for (int var2 = par0List.size() - 1; var2 >= 0; --var2) {
            var1.append(par0List.get(var2).shortForm());
            if (var2 != 0) {
                var1.append(".");
            }
        }
        return var1.toString();
    }
    
    @Override
    public String toString() {
        return "JsonNodeDoesNotMatchJsonNodeSelectorException{failedNode=" + this.failedNode + ", failPath=" + this.failPath + '}';
    }
}
