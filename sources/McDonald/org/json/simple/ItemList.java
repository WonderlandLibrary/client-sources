// 
// Decompiled by Procyon v0.5.36
// 

package org.json.simple;

import java.util.Collection;
import java.util.StringTokenizer;
import java.util.ArrayList;
import java.util.List;

public class ItemList
{
    private String sp;
    List items;
    
    public ItemList() {
        this.sp = ",";
        this.items = new ArrayList();
    }
    
    public ItemList(final String s) {
        this.sp = ",";
        this.items = new ArrayList();
        this.split(s, this.sp, this.items);
    }
    
    public ItemList(final String s, final String sp) {
        this.sp = ",";
        this.items = new ArrayList();
        this.split(this.sp = s, sp, this.items);
    }
    
    public ItemList(final String s, final String sp, final boolean isMultiToken) {
        this.sp = ",";
        this.split(s, sp, this.items = new ArrayList(), isMultiToken);
    }
    
    public List getItems() {
        return this.items;
    }
    
    public String[] getArray() {
        return (String[])this.items.toArray();
    }
    
    public void split(final String s, final String sp, final List append, final boolean isMultiToken) {
        if (s == null || sp == null) {
            return;
        }
        if (isMultiToken) {
            final StringTokenizer tokens = new StringTokenizer(s, sp);
            while (tokens.hasMoreTokens()) {
                append.add(tokens.nextToken().trim());
            }
        }
        else {
            this.split(s, sp, append);
        }
    }
    
    public void split(final String s, final String sp, final List append) {
        if (s == null || sp == null) {
            return;
        }
        int pos = 0;
        int prevPos = 0;
        do {
            prevPos = pos;
            pos = s.indexOf(sp, pos);
            if (pos == -1) {
                break;
            }
            append.add(s.substring(prevPos, pos).trim());
            pos += sp.length();
        } while (pos != -1);
        append.add(s.substring(prevPos).trim());
    }
    
    public void setSP(final String sp) {
        this.sp = sp;
    }
    
    public void add(final int i, final String item) {
        if (item == null) {
            return;
        }
        this.items.add(i, item.trim());
    }
    
    public void add(final String item) {
        if (item == null) {
            return;
        }
        this.items.add(item.trim());
    }
    
    public void addAll(final ItemList list) {
        this.items.addAll(list.items);
    }
    
    public void addAll(final String s) {
        this.split(s, this.sp, this.items);
    }
    
    public void addAll(final String s, final String sp) {
        this.split(s, sp, this.items);
    }
    
    public void addAll(final String s, final String sp, final boolean isMultiToken) {
        this.split(s, sp, this.items, isMultiToken);
    }
    
    public String get(final int i) {
        return this.items.get(i);
    }
    
    public int size() {
        return this.items.size();
    }
    
    public String toString() {
        return this.toString(this.sp);
    }
    
    public String toString(final String sp) {
        final StringBuffer sb = new StringBuffer();
        for (int i = 0; i < this.items.size(); ++i) {
            if (i == 0) {
                sb.append(this.items.get(i));
            }
            else {
                sb.append(sp);
                sb.append(this.items.get(i));
            }
        }
        return sb.toString();
    }
    
    public void clear() {
        this.items.clear();
    }
    
    public void reset() {
        this.sp = ",";
        this.items.clear();
    }
}
