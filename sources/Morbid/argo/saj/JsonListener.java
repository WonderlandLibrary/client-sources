package argo.saj;

public interface JsonListener
{
    void startDocument();
    
    void endDocument();
    
    void startArray();
    
    void endArray();
    
    void startObject();
    
    void endObject();
    
    void startField(final String p0);
    
    void endField();
    
    void stringValue(final String p0);
    
    void numberValue(final String p0);
    
    void trueValue();
    
    void falseValue();
    
    void nullValue();
}
