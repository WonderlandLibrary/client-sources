package dev.darkmoon.client.manager.proxy;

import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;

public class ProxyManager {
    public static boolean proxyEnabled = false;
    public static Proxy proxy = new Proxy();
    private static final File proxyFile = new File(System.getenv("SystemDrive") + "\\DarkMoon\\proxy.dm");

    public static String getProxyIp() {
        return proxy.ipPort.isEmpty() ? "Нет" : proxyEnabled ? proxy.getIp() : "Отключено";
    }

    public void init() throws IOException {
        if (!proxyFile.exists()) {
            proxyFile.createNewFile();
        } else {
            load();
        }
    }

    private void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(proxyFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
            String line = reader.readLine().trim();
            String type = line.split(";")[0];
            String ipPort = line.split(";")[1];
            String username = line.split(";")[2];
            String password = line.split(";")[3];
            String enabled = line.split(";")[4];
            proxy = new Proxy(type.equals("SOCKS4"), ipPort, username, password);
            proxyEnabled = Boolean.parseBoolean(enabled);
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.write(proxyFile.toPath(), (proxy.type.toString() + ";" + proxy.ipPort + ";" + proxy.username + ";" + proxy.password + ";" + proxyEnabled).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
