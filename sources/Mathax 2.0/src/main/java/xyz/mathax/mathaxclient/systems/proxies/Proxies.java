package xyz.mathax.mathaxclient.systems.proxies;

import xyz.mathax.mathaxclient.MatHax;
import xyz.mathax.mathaxclient.systems.System;
import xyz.mathax.mathaxclient.systems.Systems;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class Proxies extends System<Proxies> implements Iterable<Proxy> {
    public static final File FOLDER = new File(MatHax.VERSION_FOLDER, "Proxies");

    public static final Pattern PATTERN = Pattern.compile("^(?:([\\w\\s]+)=)?((?:0*(?:\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5])(?:\\.(?!:)|)){4}):(?!0)(\\d{1,4}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])(?i:@(socks[45]))?$", Pattern.MULTILINE);

    private final List<Proxy> proxies = new ArrayList<>();

    public Proxies() {
        super("Proxies", null);
    }

    public static Proxies get() {
        return Systems.get(Proxies.class);
    }

    public boolean add(Proxy proxy) {
        for (Proxy proxy1 : proxies) {
            if (proxy1.typeSetting.get() == proxy.typeSetting.get() && proxy1.addressSetting.get().equals(proxy.addressSetting.get()) && proxy1.portSetting.get().equals(proxy.portSetting.get())) {
                return false;
            }
        }

        if (proxies.isEmpty()) {
            proxy.enabledSetting.set(true);
        }

        proxies.add(proxy);
        save();

        return true;
    }

    public void remove(Proxy proxy) {
        if (proxies.remove(proxy)) {
            save();
        }
    }

    public Proxy getEnabled() {
        for (Proxy proxy : proxies) {
            if (proxy.enabledSetting.get()) {
                return proxy;
            }
        }

        return null;
    }

    public void setEnabled(Proxy proxy, boolean enabled) {
        for (Proxy proxy1 : proxies) {
            proxy1.enabledSetting.set(false);
        }

        proxy.enabledSetting.set(enabled);
        save();
    }

    public boolean isEmpty() {
        return proxies.isEmpty();
    }

    @NotNull
    @Override
    public Iterator<Proxy> iterator() {
        return proxies.iterator();
    }

    @Override
    public void save(File folder) {
        if (folder != null) {
            folder = new File(folder, "Proxies");
        } else {
            folder = FOLDER;
        }

        folder.mkdirs();

        for (Proxy proxy : proxies) {
            proxy.save(folder);
        }
    }

    @Override
    public void load(File folder) {
        if (folder != null) {
            folder = new File(folder, "Proxies");
        } else {
            folder = FOLDER;
        }

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (!file.getName().endsWith(".json")) {
                    return;
                }

                Proxy proxy = new Proxy();
                proxy.load(folder);
                proxies.add(proxy);
            }
        }
    }
}
