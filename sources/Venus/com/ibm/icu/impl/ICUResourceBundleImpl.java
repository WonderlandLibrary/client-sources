/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.ibm.icu.impl;

import com.ibm.icu.impl.CacheValue;
import com.ibm.icu.impl.ICUResourceBundle;
import com.ibm.icu.impl.ICUResourceBundleReader;
import com.ibm.icu.util.UResourceBundle;
import com.ibm.icu.util.UResourceTypeMismatchException;
import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

class ICUResourceBundleImpl
extends ICUResourceBundle {
    protected int resource;

    protected ICUResourceBundleImpl(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
        super(iCUResourceBundleImpl, string);
        this.resource = n;
    }

    ICUResourceBundleImpl(ICUResourceBundle.WholeBundle wholeBundle) {
        super(wholeBundle);
        this.resource = wholeBundle.reader.getRootResource();
    }

    public int getResource() {
        return this.resource;
    }

    protected final ICUResourceBundle createBundleObject(String string, int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
        switch (ICUResourceBundleReader.RES_GET_TYPE(n)) {
            case 0: 
            case 6: {
                return new ResourceString(this, string, n);
            }
            case 1: {
                return new ResourceBinary(this, string, n);
            }
            case 3: {
                return ICUResourceBundleImpl.getAliasedResource(this, null, 0, string, n, hashMap, uResourceBundle);
            }
            case 7: {
                return new ResourceInt(this, string, n);
            }
            case 14: {
                return new ResourceIntVector(this, string, n);
            }
            case 8: 
            case 9: {
                return new ResourceArray(this, string, n);
            }
            case 2: 
            case 4: 
            case 5: {
                return new ResourceTable(this, string, n);
            }
        }
        throw new IllegalStateException("The resource type is unknown");
    }

    static class ResourceTable
    extends ResourceContainer {
        @Override
        public int getType() {
            return 1;
        }

        protected String getKey(int n) {
            return ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, n);
        }

        @Override
        protected Set<String> handleKeySet() {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            TreeSet<String> treeSet = new TreeSet<String>();
            ICUResourceBundleReader.Table table = (ICUResourceBundleReader.Table)this.value;
            for (int i = 0; i < table.getSize(); ++i) {
                treeSet.add(table.getKey(iCUResourceBundleReader, i));
            }
            return treeSet;
        }

        @Override
        protected UResourceBundle handleGet(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            int n = ((ICUResourceBundleReader.Table)this.value).findTableItem(this.wholeBundle.reader, string);
            if (n < 0) {
                return null;
            }
            return this.createBundleObject(string, this.getContainerResource(n), hashMap, uResourceBundle);
        }

        @Override
        protected UResourceBundle handleGet(int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            String string = ((ICUResourceBundleReader.Table)this.value).getKey(this.wholeBundle.reader, n);
            if (string == null) {
                throw new IndexOutOfBoundsException();
            }
            return this.createBundleObject(string, this.getContainerResource(n), hashMap, uResourceBundle);
        }

        @Override
        protected Object handleGetObject(String string) {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            int n = ((ICUResourceBundleReader.Table)this.value).findTableItem(iCUResourceBundleReader, string);
            if (n >= 0) {
                int n2 = this.value.getContainerResource(iCUResourceBundleReader, n);
                String string2 = iCUResourceBundleReader.getString(n2);
                if (string2 != null) {
                    return string2;
                }
                ICUResourceBundleReader.Array array = iCUResourceBundleReader.getArray(n2);
                if (array != null) {
                    int n3 = array.getSize();
                    String[] stringArray = new String[n3];
                    int n4 = 0;
                    while (true) {
                        if (n4 == n3) {
                            return stringArray;
                        }
                        string2 = iCUResourceBundleReader.getString(array.getContainerResource(iCUResourceBundleReader, n4));
                        if (string2 == null) break;
                        stringArray[n4] = string2;
                        ++n4;
                    }
                }
            }
            return super.handleGetObject(string);
        }

        String findString(String string) {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            int n = ((ICUResourceBundleReader.Table)this.value).findTableItem(iCUResourceBundleReader, string);
            if (n < 0) {
                return null;
            }
            return iCUResourceBundleReader.getString(this.value.getContainerResource(iCUResourceBundleReader, n));
        }

        ResourceTable(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
            this.value = this.wholeBundle.reader.getTable(n);
        }

        ResourceTable(ICUResourceBundle.WholeBundle wholeBundle, int n) {
            super(wholeBundle);
            this.value = wholeBundle.reader.getTable(n);
        }
    }

    static class ResourceArray
    extends ResourceContainer {
        @Override
        public int getType() {
            return 1;
        }

        @Override
        protected String[] handleGetStringArray() {
            ICUResourceBundleReader iCUResourceBundleReader = this.wholeBundle.reader;
            int n = this.value.getSize();
            String[] stringArray = new String[n];
            for (int i = 0; i < n; ++i) {
                String string = iCUResourceBundleReader.getString(this.value.getContainerResource(iCUResourceBundleReader, i));
                if (string == null) {
                    throw new UResourceTypeMismatchException("");
                }
                stringArray[i] = string;
            }
            return stringArray;
        }

        @Override
        public String[] getStringArray() {
            return this.handleGetStringArray();
        }

        @Override
        protected UResourceBundle handleGet(String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            int n = Integer.parseInt(string);
            return this.createBundleObject(n, string, hashMap, uResourceBundle);
        }

        @Override
        protected UResourceBundle handleGet(int n, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            return this.createBundleObject(n, Integer.toString(n), hashMap, uResourceBundle);
        }

        ResourceArray(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
            this.value = this.wholeBundle.reader.getArray(n);
        }
    }

    static abstract class ResourceContainer
    extends ICUResourceBundleImpl {
        protected ICUResourceBundleReader.Container value;

        @Override
        public int getSize() {
            return this.value.getSize();
        }

        @Override
        public String getString(int n) {
            int n2 = this.value.getContainerResource(this.wholeBundle.reader, n);
            if (n2 == -1) {
                throw new IndexOutOfBoundsException();
            }
            String string = this.wholeBundle.reader.getString(n2);
            if (string != null) {
                return string;
            }
            return super.getString(n);
        }

        protected int getContainerResource(int n) {
            return this.value.getContainerResource(this.wholeBundle.reader, n);
        }

        protected UResourceBundle createBundleObject(int n, String string, HashMap<String, String> hashMap, UResourceBundle uResourceBundle) {
            int n2 = this.getContainerResource(n);
            if (n2 == -1) {
                throw new IndexOutOfBoundsException();
            }
            return this.createBundleObject(string, n2, hashMap, uResourceBundle);
        }

        ResourceContainer(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }

        ResourceContainer(ICUResourceBundle.WholeBundle wholeBundle) {
            super(wholeBundle);
        }
    }

    private static final class ResourceIntVector
    extends ICUResourceBundleImpl {
        @Override
        public int getType() {
            return 1;
        }

        @Override
        public int[] getIntVector() {
            return this.wholeBundle.reader.getIntVector(this.resource);
        }

        ResourceIntVector(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }
    }

    private static final class ResourceString
    extends ICUResourceBundleImpl {
        private String value;

        @Override
        public int getType() {
            return 1;
        }

        @Override
        public String getString() {
            if (this.value != null) {
                return this.value;
            }
            return this.wholeBundle.reader.getString(this.resource);
        }

        ResourceString(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
            String string2 = this.wholeBundle.reader.getString(n);
            if (string2.length() < 12 || CacheValue.futureInstancesWillBeStrong()) {
                this.value = string2;
            }
        }
    }

    private static final class ResourceInt
    extends ICUResourceBundleImpl {
        @Override
        public int getType() {
            return 0;
        }

        @Override
        public int getInt() {
            return ICUResourceBundleReader.RES_GET_INT(this.resource);
        }

        @Override
        public int getUInt() {
            return ICUResourceBundleReader.RES_GET_UINT(this.resource);
        }

        ResourceInt(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }
    }

    private static final class ResourceBinary
    extends ICUResourceBundleImpl {
        @Override
        public int getType() {
            return 0;
        }

        @Override
        public ByteBuffer getBinary() {
            return this.wholeBundle.reader.getBinary(this.resource);
        }

        @Override
        public byte[] getBinary(byte[] byArray) {
            return this.wholeBundle.reader.getBinary(this.resource, byArray);
        }

        ResourceBinary(ICUResourceBundleImpl iCUResourceBundleImpl, String string, int n) {
            super(iCUResourceBundleImpl, string, n);
        }
    }
}

