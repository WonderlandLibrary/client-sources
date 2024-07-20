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

    public Map<String, PluginEntry> getCategory(String category) {
        String key = category.toLowerCase();
        if (!this.categories.containsKey(key)) {
            this.categories.put(key, new LinkedHashMap());
        }
        return this.categories.get(key);
    }

    public void writeCache(OutputStream os) throws IOException {
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(os));){
            out.writeInt(this.categories.size());
            for (Map.Entry<String, Map<String, PluginEntry>> category : this.categories.entrySet()) {
                out.writeUTF(category.getKey());
                Map<String, PluginEntry> m = category.getValue();
                out.writeInt(m.size());
                for (Map.Entry<String, PluginEntry> entry : m.entrySet()) {
                    PluginEntry plugin = entry.getValue();
                    out.writeUTF(plugin.getKey());
                    out.writeUTF(plugin.getClassName());
                    out.writeUTF(plugin.getName());
                    out.writeBoolean(plugin.isPrintable());
                    out.writeBoolean(plugin.isDefer());
                }
            }
        }
    }

    public void loadCacheFiles(Enumeration<URL> resources) throws IOException {
        this.categories.clear();
        while (resources.hasMoreElements()) {
            URL url = resources.nextElement();
            DataInputStream in = new DataInputStream(new BufferedInputStream(url.openStream()));
            Throwable throwable = null;
            try {
                int count = in.readInt();
                for (int i = 0; i < count; ++i) {
                    String category = in.readUTF();
                    Map<String, PluginEntry> m = this.getCategory(category);
                    int entries = in.readInt();
                    for (int j = 0; j < entries; ++j) {
                        PluginEntry entry = new PluginEntry();
                        entry.setKey(in.readUTF());
                        entry.setClassName(in.readUTF());
                        entry.setName(in.readUTF());
                        entry.setPrintable(in.readBoolean());
                        entry.setDefer(in.readBoolean());
                        entry.setCategory(category);
                        if (m.containsKey(entry.getKey())) continue;
                        m.put(entry.getKey(), entry);
                    }
                }
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (in == null) continue;
                if (throwable != null) {
                    try {
                        in.close();
                    } catch (Throwable x2) {
                        throwable.addSuppressed(x2);
                    }
                    continue;
                }
                in.close();
            }
        }
    }

    public int size() {
        return this.categories.size();
    }
}

