package argo.jdom;

import java.util.*;

public abstract class JsonNode
{
    public abstract JsonNodeType getType();
    
    public abstract String getText();
    
    public abstract Map getFields();
    
    public abstract List getElements();
    
    public final Boolean getBooleanValue(final Object... par1ArrayOfObj) {
        return (Boolean)this.wrapExceptionsFor(JsonNodeSelectors.func_98315_c(par1ArrayOfObj), this, par1ArrayOfObj);
    }
    
    public final String getStringValue(final Object... par1ArrayOfObj) {
        return (String)this.wrapExceptionsFor(JsonNodeSelectors.func_74682_a(par1ArrayOfObj), this, par1ArrayOfObj);
    }
    
    public final String getNumberValue(final Object... par1ArrayOfObj) {
        return (String)this.wrapExceptionsFor(JsonNodeSelectors.func_98316_b(par1ArrayOfObj), this, par1ArrayOfObj);
    }
    
    public final boolean isArrayNode(final Object... par1ArrayOfObj) {
        return JsonNodeSelectors.func_74683_b(par1ArrayOfObj).matches(this);
    }
    
    public final List getArrayNode(final Object... par1ArrayOfObj) {
        return (List)this.wrapExceptionsFor(JsonNodeSelectors.func_74683_b(par1ArrayOfObj), this, par1ArrayOfObj);
    }
    
    private Object wrapExceptionsFor(final JsonNodeSelector par1JsonNodeSelector, final JsonNode par2JsonNode, final Object[] par3ArrayOfObj) throws JsonNodeDoesNotMatchPathElementsException {
        try {
            return par1JsonNodeSelector.getValue(par2JsonNode);
        }
        catch (JsonNodeDoesNotMatchChainedJsonNodeSelectorException var5) {
            throw JsonNodeDoesNotMatchPathElementsException.jsonNodeDoesNotMatchPathElementsException(var5, par3ArrayOfObj, JsonNodeFactories.aJsonArray(par2JsonNode));
        }
    }
}
