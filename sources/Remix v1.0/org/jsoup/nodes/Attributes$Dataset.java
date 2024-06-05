package org.jsoup.nodes;

import java.util.*;

private class Dataset extends AbstractMap<String, String>
{
    private Dataset() {
        if (Attributes.access$100(Attributes.this) == null) {
            Attributes.access$102(Attributes.this, new LinkedHashMap(2));
        }
    }
    
    @Override
    public Set<Map.Entry<String, String>> entrySet() {
        return new EntrySet();
    }
    
    @Override
    public String put(final String key, final String value) {
        final String dataKey = Attributes.access$300(key);
        final String oldValue = Attributes.this.hasKey(dataKey) ? Attributes.access$100(Attributes.this).get(dataKey).getValue() : null;
        final Attribute attr = new Attribute(dataKey, value);
        Attributes.access$100(Attributes.this).put(dataKey, attr);
        return oldValue;
    }
    
    private class EntrySet extends AbstractSet<Map.Entry<String, String>>
    {
        @Override
        public Iterator<Map.Entry<String, String>> iterator() {
            return new DatasetIterator();
        }
        
        @Override
        public int size() {
            int count = 0;
            final Iterator iter = new DatasetIterator();
            while (iter.hasNext()) {
                ++count;
            }
            return count;
        }
    }
    
    private class DatasetIterator implements Iterator<Map.Entry<String, String>>
    {
        private Iterator<Attribute> attrIter;
        private Attribute attr;
        
        private DatasetIterator() {
            this.attrIter = Attributes.access$100(Attributes.this).values().iterator();
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
            Attributes.access$100(Attributes.this).remove(this.attr.getKey());
        }
    }
}
