package org.jsoup.nodes;

import org.jsoup.helper.*;
import org.jsoup.*;
import java.io.*;
import java.util.*;

public class Attributes implements Iterable<Attribute>, Cloneable
{
    protected static final String dataPrefix = "data-";
    private LinkedHashMap<String, Attribute> attributes;
    
    public Attributes() {
        this.attributes = null;
    }
    
    public String get(final String key) {
        Validate.notEmpty(key);
        if (this.attributes == null) {
            return "";
        }
        final Attribute attr = this.attributes.get(key);
        return (attr != null) ? attr.getValue() : "";
    }
    
    public String getIgnoreCase(final String key) {
        Validate.notEmpty(key);
        if (this.attributes == null) {
            return "";
        }
        final Attribute attr = this.attributes.get(key);
        if (attr != null) {
            return attr.getValue();
        }
        for (final String attrKey : this.attributes.keySet()) {
            if (attrKey.equalsIgnoreCase(key)) {
                return this.attributes.get(attrKey).getValue();
            }
        }
        return "";
    }
    
    public void put(final String key, final String value) {
        final Attribute attr = new Attribute(key, value);
        this.put(attr);
    }
    
    public void put(final String key, final boolean value) {
        if (value) {
            this.put(new BooleanAttribute(key));
        }
        else {
            this.remove(key);
        }
    }
    
    public void put(final Attribute attribute) {
        Validate.notNull(attribute);
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap<String, Attribute>(2);
        }
        this.attributes.put(attribute.getKey(), attribute);
    }
    
    public void remove(final String key) {
        Validate.notEmpty(key);
        if (this.attributes == null) {
            return;
        }
        this.attributes.remove(key);
    }
    
    public void removeIgnoreCase(final String key) {
        Validate.notEmpty(key);
        if (this.attributes == null) {
            return;
        }
        final Iterator<String> it = this.attributes.keySet().iterator();
        while (it.hasNext()) {
            final String attrKey = it.next();
            if (attrKey.equalsIgnoreCase(key)) {
                it.remove();
            }
        }
    }
    
    public boolean hasKey(final String key) {
        return this.attributes != null && this.attributes.containsKey(key);
    }
    
    public boolean hasKeyIgnoreCase(final String key) {
        if (this.attributes == null) {
            return false;
        }
        for (final String attrKey : this.attributes.keySet()) {
            if (attrKey.equalsIgnoreCase(key)) {
                return true;
            }
        }
        return false;
    }
    
    public int size() {
        if (this.attributes == null) {
            return 0;
        }
        return this.attributes.size();
    }
    
    public void addAll(final Attributes incoming) {
        if (incoming.size() == 0) {
            return;
        }
        if (this.attributes == null) {
            this.attributes = new LinkedHashMap<String, Attribute>(incoming.size());
        }
        this.attributes.putAll((Map<?, ?>)incoming.attributes);
    }
    
    public Iterator<Attribute> iterator() {
        if (this.attributes == null || this.attributes.isEmpty()) {
            return Collections.emptyList().iterator();
        }
        return this.attributes.values().iterator();
    }
    
    public List<Attribute> asList() {
        if (this.attributes == null) {
            return Collections.emptyList();
        }
        final List<Attribute> list = new ArrayList<Attribute>(this.attributes.size());
        for (final Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
            list.add(entry.getValue());
        }
        return Collections.unmodifiableList((List<? extends Attribute>)list);
    }
    
    public Map<String, String> dataset() {
        return new Dataset();
    }
    
    public String html() {
        final StringBuilder accum = new StringBuilder();
        try {
            this.html(accum, new Document("").outputSettings());
        }
        catch (IOException e) {
            throw new SerializationException(e);
        }
        return accum.toString();
    }
    
    void html(final Appendable accum, final Document.OutputSettings out) throws IOException {
        if (this.attributes == null) {
            return;
        }
        for (final Map.Entry<String, Attribute> entry : this.attributes.entrySet()) {
            final Attribute attribute = entry.getValue();
            accum.append(" ");
            attribute.html(accum, out);
        }
    }
    
    @Override
    public String toString() {
        return this.html();
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Attributes)) {
            return false;
        }
        final Attributes that = (Attributes)o;
        if (this.attributes != null) {
            if (!this.attributes.equals(that.attributes)) {
                return false;
            }
        }
        else if (that.attributes != null) {
            return false;
        }
        return true;
        b = false;
        return b;
    }
    
    @Override
    public int hashCode() {
        return (this.attributes != null) ? this.attributes.hashCode() : 0;
    }
    
    public Attributes clone() {
        if (this.attributes == null) {
            return new Attributes();
        }
        Attributes clone;
        try {
            clone = (Attributes)super.clone();
        }
        catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        clone.attributes = new LinkedHashMap<String, Attribute>(this.attributes.size());
        for (final Attribute attribute : this) {
            clone.attributes.put(attribute.getKey(), attribute.clone());
        }
        return clone;
    }
    
    private static String dataKey(final String key) {
        return "data-" + key;
    }
    
    private class Dataset extends AbstractMap<String, String>
    {
        private Dataset() {
            if (Attributes.this.attributes == null) {
                Attributes.this.attributes = (LinkedHashMap<String, Attribute>)new LinkedHashMap(2);
            }
        }
        
        @Override
        public Set<Map.Entry<String, String>> entrySet() {
            return new EntrySet();
        }
        
        @Override
        public String put(final String key, final String value) {
            final String dataKey = dataKey(key);
            final String oldValue = Attributes.this.hasKey(dataKey) ? Attributes.this.attributes.get(dataKey).getValue() : null;
            final Attribute attr = new Attribute(dataKey, value);
            Attributes.this.attributes.put(dataKey, attr);
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
                this.attrIter = Attributes.this.attributes.values().iterator();
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
                Attributes.this.attributes.remove(this.attr.getKey());
            }
        }
    }
}
