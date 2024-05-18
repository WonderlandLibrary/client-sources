package argo.jdom;

import java.util.*;

final class JsonConstants extends JsonNode
{
    static final JsonConstants NULL;
    static final JsonConstants TRUE;
    static final JsonConstants FALSE;
    private final JsonNodeType jsonNodeType;
    
    static {
        NULL = new JsonConstants(JsonNodeType.NULL);
        TRUE = new JsonConstants(JsonNodeType.TRUE);
        FALSE = new JsonConstants(JsonNodeType.FALSE);
    }
    
    private JsonConstants(final JsonNodeType par1JsonNodeType) {
        this.jsonNodeType = par1JsonNodeType;
    }
    
    @Override
    public JsonNodeType getType() {
        return this.jsonNodeType;
    }
    
    @Override
    public String getText() {
        throw new IllegalStateException("Attempt to get text on a JsonNode without text.");
    }
    
    @Override
    public Map getFields() {
        throw new IllegalStateException("Attempt to get fields on a JsonNode without fields.");
    }
    
    @Override
    public List getElements() {
        throw new IllegalStateException("Attempt to get elements on a JsonNode without elements.");
    }
}
