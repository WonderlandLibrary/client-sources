/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package org.apache.logging.log4j.core.config.plugins.processor;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URL;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.logging.log4j.core.config.plugins.processor.PluginEntry;

public class PluginCache {
    private final Map<String, Map<String, PluginEntry>> categories = new LinkedHashMap<String, Map<String, PluginEntry>>();

    public Map<String, Map<String, PluginEntry>> getAllCategories() {
        return this.categories;
    }

    public Map<String, PluginEntry> getCategory(String string) {
        String string2 = string.toLowerCase();
        if (!this.categories.containsKey(string2)) {
            this.categories.put(string2, new LinkedHashMap());
        }
        return this.categories.get(string2);
    }

    public void writeCache(OutputStream outputStream) throws IOException {
        try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(outputStream));){
            dataOutputStream.writeInt(this.categories.size());
            for (Map.Entry<String, Map<String, PluginEntry>> entry : this.categories.entrySet()) {
                dataOutputStream.writeUTF(entry.getKey());
                Map<String, PluginEntry> map = entry.getValue();
                dataOutputStream.writeInt(map.size());
                for (Map.Entry<String, PluginEntry> entry2 : map.entrySet()) {
                    PluginEntry pluginEntry = entry2.getValue();
                    dataOutputStream.writeUTF(pluginEntry.getKey());
                    dataOutputStream.writeUTF(pluginEntry.getClassName());
                    dataOutputStream.writeUTF(pluginEntry.getName());
                    dataOutputStream.writeBoolean(pluginEntry.isPrintable());
                    dataOutputStream.writeBoolean(pluginEntry.isDefer());
                }
            }
        }
    }

    public void loadCacheFiles(Enumeration<URL> enumeration) throws IOException {
        this.categories.clear();
        while (enumeration.hasMoreElements()) {
            URL uRL = enumeration.nextElement();
            DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(uRL.openStream()));
            Throwable throwable = null;
            try {
                int n = dataInputStream.readInt();
                for (int i = 0; i < n; ++i) {
                    String string = dataInputStream.readUTF();
                    Map<String, PluginEntry> map = this.getCategory(string);
                    int n2 = dataInputStream.readInt();
                    for (int j = 0; j < n2; ++j) {
                        PluginEntry pluginEntry = new PluginEntry();
                        pluginEntry.setKey(dataInputStream.readUTF());
                        pluginEntry.setClassName(dataInputStream.readUTF());
                        pluginEntry.setName(dataInputStream.readUTF());
                        pluginEntry.setPrintable(dataInputStream.readBoolean());
                        pluginEntry.setDefer(dataInputStream.readBoolean());
                        pluginEntry.setCategory(string);
                        if (map.containsKey(pluginEntry.getKey())) continue;
                        map.put(pluginEntry.getKey(), pluginEntry);
                    }
                }
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (dataInputStream == null) continue;
                if (throwable != null) {
                    try {
                        dataInputStream.close();
                    } catch (Throwable throwable3) {
                        throwable.addSuppressed(throwable3);
                    }
                    continue;
                }
                dataInputStream.close();
            }
        }
    }

    public int size() {
        return this.categories.size();
    }
}

