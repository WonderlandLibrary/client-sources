package com.ibm.icu.impl;

import java.nio.*;
import com.ibm.icu.util.*;
import java.util.*;

class ICUResourceBundleImpl extends ICUResourceBundle
{
    protected int resource;
    
    protected ICUResourceBundleImpl(final ICUResourceBundleImpl container, final String key, final int resource) {
        super(container, key);
        this.resource = resource;
    }
    
    ICUResourceBundleImpl(final WholeBundle wholeBundle) {
        super(wholeBundle);
        this.resource = wholeBundle.reader.getRootResource();
    }
    
    public int getResource() {
        return this.resource;
    }
    
    protected final ICUResourceBundle createBundleObject(final String _key, final int _resource, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
        switch (ICUResourceBundleReader.RES_GET_TYPE(_resource)) {
            case 0:
            case 6: {
                return new ResourceString(this, _key, _resource);
            }
            case 1: {
                return new ResourceBinary(this, _key, _resource);
            }
            case 3: {
                return ICUResourceBundle.getAliasedResource(this, null, 0, _key, _resource, aliasesVisited, requested);
            }
            case 7: {
                return new ResourceInt(this, _key, _resource);
            }
            case 14: {
                return new ResourceIntVector(this, _key, _resource);
            }
            case 8:
            case 9: {
                return new ResourceArray(this, _key, _resource);
            }
            case 2:
            case 4:
            case 5: {
                return new ResourceTable(this, _key, _resource);
            }
            default: {
                throw new IllegalStateException("The resource type is unknown");
            }
        }
    }
    
    private static final class ResourceBinary extends ICUResourceBundleImpl
    {
        @Override
        public int getType() {
            return 1;
        }
        
        @Override
        public ByteBuffer getBinary() {
            return this.wholeBundle.reader.getBinary(this.resource);
        }
        
        @Override
        public byte[] getBinary(final byte[] ba) {
            return this.wholeBundle.reader.getBinary(this.resource, ba);
        }
        
        ResourceBinary(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
        }
    }
    
    private static final class ResourceInt extends ICUResourceBundleImpl
    {
        @Override
        public int getType() {
            return 7;
        }
        
        @Override
        public int getInt() {
            return ICUResourceBundleReader.RES_GET_INT(this.resource);
        }
        
        @Override
        public int getUInt() {
            return ICUResourceBundleReader.RES_GET_UINT(this.resource);
        }
        
        ResourceInt(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
        }
    }
    
    private static final class ResourceString extends ICUResourceBundleImpl
    {
        private String value;
        
        @Override
        public int getType() {
            return 0;
        }
        
        @Override
        public String getString() {
            if (this.value != null) {
                return this.value;
            }
            return this.wholeBundle.reader.getString(this.resource);
        }
        
        ResourceString(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
            final String s = this.wholeBundle.reader.getString(resource);
            if (s.length() < 12 || CacheValue.futureInstancesWillBeStrong()) {
                this.value = s;
            }
        }
    }
    
    private static final class ResourceIntVector extends ICUResourceBundleImpl
    {
        @Override
        public int getType() {
            return 14;
        }
        
        @Override
        public int[] getIntVector() {
            return this.wholeBundle.reader.getIntVector(this.resource);
        }
        
        ResourceIntVector(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
        }
    }
    
    abstract static class ResourceContainer extends ICUResourceBundleImpl
    {
        protected ICUResourceBundleReader.Container value;
        
        @Override
        public int getSize() {
            return this.value.getSize();
        }
        
        @Override
        public String getString(final int index) {
            final int res = this.value.getContainerResource(this.wholeBundle.reader, index);
            if (res == -1) {
                throw new IndexOutOfBoundsException();
            }
            final String s = this.wholeBundle.reader.getString(res);
            if (s != null) {
                return s;
            }
            return super.getString(index);
        }
        
        protected int getContainerResource(final int index) {
            return this.value.getContainerResource(this.wholeBundle.reader, index);
        }
        
        protected UResourceBundle createBundleObject(final int index, final String resKey, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
            final int item = this.getContainerResource(index);
            if (item == -1) {
                throw new IndexOutOfBoundsException();
            }
            return this.createBundleObject(resKey, item, aliasesVisited, requested);
        }
        
        ResourceContainer(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
        }
        
        ResourceContainer(final WholeBundle wholeBundle) {
            super(wholeBundle);
        }
    }
    
    static class ResourceArray extends ResourceContainer
    {
        @Override
        public int getType() {
            return 8;
        }
        
        @Override
        protected String[] handleGetStringArray() {
            final ICUResourceBundleReader reader = this.wholeBundle.reader;
            final int length = this.value.getSize();
            final String[] strings = new String[length];
            for (int i = 0; i < length; ++i) {
                final String s = reader.getString(this.value.getContainerResource(reader, i));
                if (s == null) {
                    throw new UResourceTypeMismatchException("");
                }
                strings[i] = s;
            }
            return strings;
        }
        
        @Override
        public String[] getStringArray() {
            return this.handleGetStringArray();
        }
        
        @Override
        protected UResourceBundle handleGet(final String indexStr, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
            final int i = Integer.parseInt(indexStr);
            return this.createBundleObject(i, indexStr, aliasesVisited, requested);
        }
        
        @Override
        protected UResourceBundle handleGet(final int index, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
            return this.createBundleObject(index, Integer.toString(index), aliasesVisited, requested);
        }
        
        ResourceArray(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
            this.value = this.wholeBundle.reader.getArray(resource);
        }
    }
    
    static class ResourceTable extends ResourceContainer
    {
        @Override
        public int getType() {
            return 2;
        }
        
        protected String getKey(final int index) {
            return ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, index);
        }
        
        @Override
        protected Set<String> handleKeySet() {
            final ICUResourceBundleReader reader = this.wholeBundle.reader;
            final TreeSet<String> keySet = new TreeSet<String>();
            final ICUResourceBundleReader.Table table = (ICUResourceBundleReader.Table)this.value;
            for (int i = 0; i < table.getSize(); ++i) {
                keySet.add(table.getKey(reader, i));
            }
            return keySet;
        }
        
        @Override
        protected UResourceBundle handleGet(final String resKey, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
            final int i = ((ICUResourceBundleReader.Table)this.value).findTableItem(this.wholeBundle.reader, resKey);
            if (i < 0) {
                return null;
            }
            return this.createBundleObject(resKey, this.getContainerResource(i), aliasesVisited, requested);
        }
        
        @Override
        protected UResourceBundle handleGet(final int index, final HashMap<String, String> aliasesVisited, final UResourceBundle requested) {
            final String itemKey = ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, index);
            if (itemKey == null) {
                throw new IndexOutOfBoundsException();
            }
            return this.createBundleObject(itemKey, this.getContainerResource(index), aliasesVisited, requested);
        }
        
        @Override
        protected Object handleGetObject(final String key) {
            final ICUResourceBundleReader reader = this.wholeBundle.reader;
            final int index = ((ICUResourceBundleReader.Table)this.value).findTableItem(reader, key);
            if (index >= 0) {
                final int res = this.value.getContainerResource(reader, index);
                String s = reader.getString(res);
                if (s != null) {
                    return s;
                }
                final ICUResourceBundleReader.Container array = reader.getArray(res);
                if (array != null) {
                    final int length = array.getSize();
                    final String[] strings = new String[length];
                    for (int j = 0; j != length; ++j) {
                        s = reader.getString(array.getContainerResource(reader, j));
                        if (s == null) {
                            return super.handleGetObject(key);
                        }
                        strings[j] = s;
                    }
                    return strings;
                }
            }
            return super.handleGetObject(key);
        }
        
        String findString(final String key) {
            final ICUResourceBundleReader reader = this.wholeBundle.reader;
            final int index = ((ICUResourceBundleReader.Table)this.value).findTableItem(reader, key);
            if (index < 0) {
                return null;
            }
            return reader.getString(this.value.getContainerResource(reader, index));
        }
        
        ResourceTable(final ICUResourceBundleImpl container, final String key, final int resource) {
            super(container, key, resource);
            this.value = this.wholeBundle.reader.getTable(resource);
        }
        
        ResourceTable(final WholeBundle wholeBundle, final int rootRes) {
            super(wholeBundle);
            this.value = wholeBundle.reader.getTable(rootRes);
        }
    }
}
