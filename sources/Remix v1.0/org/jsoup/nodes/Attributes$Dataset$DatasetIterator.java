package org.jsoup.nodes;

import java.util.*;

private class DatasetIterator implements Iterator<Map.Entry<String, String>>
{
    private Iterator<Attribute> attrIter;
    private Attribute attr;
    
    private DatasetIterator() {
        this.attrIter = Attributes.access$100(Dataset.this.this$0).values().iterator();
    }
    
    public boolean hasNext() {
        while (this.attrIter.hasNext()) {
            this.attr = this.attrIter.next();
            if (this.attr.isDataAttribute()) {
                return true;
            }
        }
        return false;
    }
    
    public Map.Entry<String, String> next() {
        return new Attribute(this.attr.getKey().substring("data-".length()), this.attr.getValue());
    }
    
    public void remove() {
        Attributes.access$100(Dataset.this.this$0).remove(this.attr.getKey());
    }
}
