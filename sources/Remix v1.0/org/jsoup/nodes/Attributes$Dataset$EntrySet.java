package org.jsoup.nodes;

import java.util.*;

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
