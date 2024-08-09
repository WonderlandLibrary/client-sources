/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data;

import com.google.common.collect.ObjectArrays;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectMap;
import com.viaversion.viaversion.libs.fastutil.ints.Int2ObjectOpenHashMap;
import com.viaversion.viaversion.libs.gson.reflect.TypeToken;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.data.MappingData;
import com.viaversion.viaversion.util.GsonUtil;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;

public class BlockIdData {
    public static final String[] PREVIOUS = new String[0];
    public static Map<String, String[]> blockIdMapping;
    public static Map<String, String[]> fallbackReverseMapping;
    public static Int2ObjectMap<String> numberIdToString;

    public static void init() {
        Object object;
        Object object2;
        Closeable closeable;
        InputStream inputStream = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockIds1.12to1.13.json");
        try {
            closeable = new InputStreamReader(inputStream);
            object2 = null;
            try {
                object = (Map)GsonUtil.getGson().fromJson((Reader)closeable, new TypeToken<Map<String, String[]>>(){}.getType());
                blockIdMapping = new HashMap<String, String[]>((Map<String, String[]>)object);
                fallbackReverseMapping = new HashMap<String, String[]>();
                for (Map.Entry entry : blockIdMapping.entrySet()) {
                    for (String string : (String[])entry.getValue()) {
                        String[] stringArray = fallbackReverseMapping.get(string);
                        if (stringArray == null) {
                            stringArray = PREVIOUS;
                        }
                        fallbackReverseMapping.put(string, ObjectArrays.concat(stringArray, entry.getKey()));
                    }
                }
            } catch (Throwable throwable) {
                object2 = throwable;
                throw throwable;
            } finally {
                if (closeable != null) {
                    if (object2 != null) {
                        try {
                            ((InputStreamReader)closeable).close();
                        } catch (Throwable throwable) {
                            ((Throwable)object2).addSuppressed(throwable);
                        }
                    } else {
                        ((InputStreamReader)closeable).close();
                    }
                }
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        closeable = MappingData.class.getClassLoader().getResourceAsStream("assets/viaversion/data/blockNumberToString1.12.json");
        try {
            object2 = new InputStreamReader((InputStream)closeable);
            object = null;
            try {
                Map map = (Map)GsonUtil.getGson().fromJson((Reader)object2, new TypeToken<Map<Integer, String>>(){}.getType());
                numberIdToString = new Int2ObjectOpenHashMap<String>(map);
            } catch (Throwable throwable) {
                object = throwable;
                throw throwable;
            } finally {
                if (object2 != null) {
                    if (object != null) {
                        try {
                            ((InputStreamReader)object2).close();
                        } catch (Throwable throwable) {
                            ((Throwable)object).addSuppressed(throwable);
                        }
                    } else {
                        ((InputStreamReader)object2).close();
                    }
                }
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }
}

