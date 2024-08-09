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
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;

public abstract class Config
implements ConfigurationProvider {
    private static final YamlCompat YAMP_COMPAT = YamlCompat.isVersion1() ? new Yaml1Compat() : new Yaml2Compat();
    private static final ThreadLocal<Yaml> YAML = ThreadLocal.withInitial(Config::lambda$static$0);
    private final CommentStore commentStore = new CommentStore('.', 2);
    private final File configFile;
    private Map<String, Object> config;

    protected Config(File file) {
        this.configFile = file;
    }

    public URL getDefaultConfigURL() {
        return this.getClass().getClassLoader().getResource("assets/viaversion/config.yml");
    }

    public Map<String, Object> loadConfig(File file) {
        return this.loadConfig(file, this.getDefaultConfigURL());
    }

    /*
     * WARNING - void declaration
     */
    public synchronized Map<String, Object> loadConfig(File file, URL uRL) {
        void var5_12;
        List<String> list;
        List<String> list2 = this.getUnsupportedOptions();
        try {
            this.commentStore.storeComments(uRL.openStream());
            for (String object2 : list2) {
                list = this.commentStore.header(object2);
                if (list == null) continue;
                list.clear();
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        Object object3 = null;
        if (file.exists()) {
            try {
                FileInputStream iOException = new FileInputStream(file);
                list = null;
                try {
                    object3 = (Map)YAML.get().load(iOException);
                } catch (Throwable throwable) {
                    list = throwable;
                    throw throwable;
                } finally {
                    if (iOException != null) {
                        if (list != null) {
                            try {
                                iOException.close();
                            } catch (Throwable throwable) {
                                ((Throwable)((Object)list)).addSuppressed(throwable);
                            }
                        } else {
                            iOException.close();
                        }
                    }
                }
            } catch (IOException iOException) {
                iOException.printStackTrace();
            }
        }
        if (object3 == null) {
            object3 = new HashMap();
        }
        Iterator<String> iterator2 = object3;
        try {
            list = uRL.openStream();
            Throwable throwable = null;
            try {
                Map map = (Map)YAML.get().load((InputStream)((Object)list));
                for (String string : list2) {
                    map.remove(string);
                }
                for (Map.Entry entry : object3.entrySet()) {
                    if (!map.containsKey(entry.getKey()) || list2.contains(entry.getKey())) continue;
                    map.put(entry.getKey(), entry.getValue());
                }
            } catch (Throwable throwable2) {
                throwable = throwable2;
                throw throwable2;
            } finally {
                if (list != null) {
                    if (throwable != null) {
                        try {
                            ((InputStream)((Object)list)).close();
                        } catch (Throwable throwable3) {
                            throwable.addSuppressed(throwable3);
                        }
                    } else {
                        ((InputStream)((Object)list)).close();
                    }
                }
            }
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
        this.handleConfig((Map<String, Object>)var5_12);
        this.saveConfig(file, (Map<String, Object>)var5_12);
        return var5_12;
    }

    protected abstract void handleConfig(Map<String, Object> var1);

    public synchronized void saveConfig(File file, Map<String, Object> map) {
        try {
            this.commentStore.writeComments(YAML.get().dump(map), file);
        } catch (IOException iOException) {
            iOException.printStackTrace();
        }
    }

    public abstract List<String> getUnsupportedOptions();

    @Override
    public void set(String string, Object object) {
        this.config.put(string, object);
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

    public <T> @Nullable T get(String string, Class<T> clazz, T t) {
        Object object = this.config.get(string);
        if (object != null) {
            return (T)object;
        }
        return t;
    }

    public boolean getBoolean(String string, boolean bl) {
        Object object = this.config.get(string);
        if (object != null) {
            return (Boolean)object;
        }
        return bl;
    }

    public @Nullable String getString(String string, @Nullable String string2) {
        Object object = this.config.get(string);
        if (object != null) {
            return (String)object;
        }
        return string2;
    }

    public int getInt(String string, int n) {
        Object object = this.config.get(string);
        if (object != null) {
            if (object instanceof Number) {
                return ((Number)object).intValue();
            }
            return n;
        }
        return n;
    }

    public double getDouble(String string, double d) {
        Object object = this.config.get(string);
        if (object != null) {
            if (object instanceof Number) {
                return ((Number)object).doubleValue();
            }
            return d;
        }
        return d;
    }

    public List<Integer> getIntegerList(String string) {
        Object object = this.config.get(string);
        return object != null ? (List)object : new ArrayList();
    }

    public List<String> getStringList(String string) {
        Object object = this.config.get(string);
        return object != null ? (List)object : new ArrayList();
    }

    public <T> List<T> getListSafe(String string, Class<T> clazz, String string2) {
        Object object = this.config.get(string);
        if (object instanceof List) {
            List list = (List)object;
            ArrayList<T> arrayList = new ArrayList<T>();
            for (Object e : list) {
                if (clazz.isInstance(e)) {
                    arrayList.add(clazz.cast(e));
                    continue;
                }
                if (string2 == null) continue;
                Via.getPlatform().getLogger().warning(String.format(string2, e));
            }
            return arrayList;
        }
        return new ArrayList();
    }

    public @Nullable JsonElement getSerializedComponent(String string) {
        Object object = this.config.get(string);
        if (object != null && !((String)object).isEmpty()) {
            return GsonComponentSerializer.gson().serializeToTree(LegacyComponentSerializer.legacySection().deserialize((String)object));
        }
        return null;
    }

    private static Yaml lambda$static$0() {
        DumperOptions dumperOptions = new DumperOptions();
        dumperOptions.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);
        dumperOptions.setPrettyFlow(true);
        dumperOptions.setIndent(2);
        return new Yaml(YAMP_COMPAT.createSafeConstructor(), YAMP_COMPAT.createRepresenter(dumperOptions), dumperOptions);
    }
}

