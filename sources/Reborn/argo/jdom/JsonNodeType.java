package argo.jdom;

public enum JsonNodeType
{
    OBJECT("OBJECT", 0), 
    ARRAY("ARRAY", 1), 
    STRING("STRING", 2), 
    NUMBER("NUMBER", 3), 
    TRUE("TRUE", 4), 
    FALSE("FALSE", 5), 
    NULL("NULL", 6);
    
    private JsonNodeType(final String s, final int n) {
    }
}
