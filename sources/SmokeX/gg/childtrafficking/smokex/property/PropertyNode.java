// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.property;

import java.util.List;

public final class PropertyNode
{
    private String nodeName;
    private final List<Property> propertyList;
    
    public PropertyNode(final String nodeName, final List<Property> propertyList) {
        this.nodeName = "";
        this.nodeName = nodeName;
        this.propertyList = propertyList;
    }
    
    public PropertyNode(final List<Property> propertyList) {
        this.nodeName = "";
        this.propertyList = propertyList;
    }
    
    public String getNodeName() {
        return this.nodeName;
    }
    
    public List<Property> getPropertyList() {
        return this.propertyList;
    }
}
