package argo.jdom;

import argo.format.*;

public final class JsonNodeDoesNotMatchPathElementsException extends JsonNodeDoesNotMatchJsonNodeSelectorException
{
    private static final JsonFormatter JSON_FORMATTER;
    
    static {
        JSON_FORMATTER = new CompactJsonFormatter();
    }
    
    static JsonNodeDoesNotMatchPathElementsException jsonNodeDoesNotMatchPathElementsException(final JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, final Object[] par1ArrayOfObj, final JsonRootNode par2JsonRootNode) {
        return new JsonNodeDoesNotMatchPathElementsException(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, par1ArrayOfObj, par2JsonRootNode);
    }
    
    private JsonNodeDoesNotMatchPathElementsException(final JsonNodeDoesNotMatchChainedJsonNodeSelectorException par1JsonNodeDoesNotMatchChainedJsonNodeSelectorException, final Object[] par2ArrayOfObj, final JsonRootNode par3JsonRootNode) {
        super(formatMessage(par1JsonNodeDoesNotMatchChainedJsonNodeSelectorException, par2ArrayOfObj, par3JsonRootNode));
    }
    
    private static String formatMessage(final JsonNodeDoesNotMatchChainedJsonNodeSelectorException par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException, final Object[] par1ArrayOfObj, final JsonRootNode par2JsonRootNode) {
        return "Failed to find " + par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failedNode.toString() + " at [" + JsonNodeDoesNotMatchChainedJsonNodeSelectorException.getShortFormFailPath(par0JsonNodeDoesNotMatchChainedJsonNodeSelectorException.failPath) + "] while resolving [" + commaSeparate(par1ArrayOfObj) + "] in " + JsonNodeDoesNotMatchPathElementsException.JSON_FORMATTER.format(par2JsonRootNode) + ".";
    }
    
    private static String commaSeparate(final Object[] par0ArrayOfObj) {
        final StringBuilder var1 = new StringBuilder();
        boolean var2 = true;
        for (final Object var5 : par0ArrayOfObj) {
            if (!var2) {
                var1.append(".");
            }
            var2 = false;
            if (var5 instanceof String) {
                var1.append("\"").append(var5).append("\"");
            }
            else {
                var1.append(var5);
            }
        }
        return var1.toString();
    }
}
