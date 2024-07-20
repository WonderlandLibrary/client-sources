/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.util;

import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.configuration.ConfigurationProvider;
import com.viaversion.viaversion.compatibility.YamlCompat;
import com.viaversion.viaversion.compatibility.unsafe.Yaml1Compat;
import com.viaversion.viaversion.compatibility.unsafe.Yaml2Compat;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.gson.GsonComponentSerializer;
import com.viaversion.viaversion.libs.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import com.viaversion.viaversion.util.CommentStore;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public abstract class Config
implements ConfigurationProvider {
    private static final YamlCompat YAMP_COMPAT = YamlCompat.isVersion1() ? new Yaml1Compat() : new Yaml2Compat();
    private static final ThreadLocal<Yaml> YAML = ThreadLocal.withInitial(() -> {
        DumperOptions options = new DumperOptions();
        options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        options.setPrettyFlow(false);
        options.setIndent(2);
        return new Yaml(YAMP_COMPAT.createSafeConstructor(), YAMP_COMPAT.createRepresenter(options), options);
    });
    private final CommentStore commentStore = new CommentStore('.', 2);
    private final File configFile;
    private Map<String, Object> config;

    protected Config(File configFile) {
        this.configFile = configFile;
    }

    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }

    public Map<String, Object> loadConfig(File location) {
        return this.loadConfig(location, this.getDefaultConfigURL());
    }

    public synchronized Map<String, Object> loadConfig(File location, URL jarConfigFile) {
        List<String> comments;
        List<String> unsupported = this.getUnsupportedOptions();
        try {
            this.commentStore.storeComments(jarConfigFile.openStream());
            for (String option : unsupported) {
                comments = this.commentStore.header(option);
                if (comments == null) continue;
                comments.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        Map<String, Object> config = null;
        if (location.exists()) {
            try {
                FileInputStream input = new FileInputStream(location);
                comments = null;
                try {
                    config = (Map)YAML.get().load(input);
                } catch (Throwable throwable) {
                    comments = throwable;
                    throw throwable;
                } finally {
                    if (input != null) {
                        if (comments != null) {
                            try {
                                input.close();
                            } catch (Throwable throwable) {
                                ((Throwable)((Object)comments)).addSuppressed(throwable);
                            }
                        } else {
                            input.close();
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if (config == null) {
            config = new HashMap<String, Object>();
        }
        Map<String, Object> defaults = config;
        try (InputStream stream = jarConfigFile.openStream();){
            defaults = (Map)YAML.get().load(stream);
            for (String string : unsupported) {
                defaults.remove(string);
            }
            for (Map.Entry entry : config.entrySet()) {
                if (!defaults.containsKey(entry.getKey()) || unsupported.contains(entry.getKey())) continue;
                defaults.put((String)entry.getKey(), entry.getValue());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.handleConfig(defaults);
        this.saveConfig(location, defaults);
        return defaults;
    }

    protected abstract void handleConfig(Map<String, Object> var1);

    public synchronized void saveConfig(File location, Map<String, Object> config) {
        try {
            this.commentStore.writeComments(YAML.get().dump(config), location);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public abstract List<String> getUnsupportedOptions();

    @Override
    public void set(String path, Object value) {
        this.config.put(path, value);
    }

    @Override
    public void saveConfig() {
        this.configFile.getParentFile().mkdirs();
        this.saveConfig(this.configFile, this.config);
    }

    public void saveConfig(File file) {
        this.saveConfig(file, this.config);
    }

    @Override
    public void reloadConfig() {
        this.configFile.getParentFile().mkdirs();
        this.config = new ConcurrentSkipListMap<String, Object>(this.loadConfig(this.configFile));
    }

    @Override
    public Map<String, Object> getValues() {
        return this.config;
    }

    public <T> @Nullable T get(String key, Class<T> clazz, T def) {
        Object o = this.config.get(key);
        if (o != null) {
            return (T)o;
        }
        return def;
    }

    public boolean getBoolean(String key, boolean def) {
        Object o = this.config.get(key);
        if (o != null) {
            return (Boolean)o;
        }
        return def;
    }

    public @Nullable String getString(String key, @Nullable String def) {
        Object o = this.config.get(key);
        if (o != null) {
            return (String)o;
        }
        return def;
    }

    public int getInt(String key, int def) {
        Object o = this.config.get(key);
        if (o != null) {
            if (o instanceof Number) {
                return ((Number)o).intValue();
            }
            return def;
        }
        return def;
    }

    public double getDouble(String key, double def) {
        Object o = this.config.get(key);
        if (o != null) {
            if (o instanceof Number) {
                return ((Number)o).doubleValue();
            }
            return def;
        }
        return def;
    }

    public List<Integer> getIntegerList(String key) {
        Object o = this.config.get(key);
        return o != null ? (List)o : new ArrayList();
    }

    public List<String> getStringList(String key) {
        Object o = this.config.get(key);
        return o != null ? (List)o : new ArrayList();
    }

    public <T> List<T> getListSafe(String key, Class<T> type2, String invalidValueMessage) {
        Object o = this.config.get(key);
        if (o instanceof List) {
            List list = (List)o;
            ArrayList<T> filteredValues = new ArrayList<T>();
            for (Object o1 : list) {
                if (type2.isInstance(o1)) {
                    filteredValues.add(type2.cast(o1));
                    continue;
                }
                if (invalidValueMessage == null) continue;
                Via.getPlatform().getLogger().warning(String.format(invalidValueMessage, o1));
            }
            return filteredValues;
        }
        return new ArrayList();
    }

    public @Nullable JsonElement getSerializedComponent(String key) {
        Object o = this.config.get(key);
        if (o != null && !((String)o).isEmpty()) {
            return GsonComponentSerializer.gson().serializeToTree(LegacyComponentSerializer.legacySection().deserialize((String)o));
        }
        return null;
    }
}

