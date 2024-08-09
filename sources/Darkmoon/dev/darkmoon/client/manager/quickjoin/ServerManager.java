package dev.darkmoon.client.manager.quickjoin;

import lombok.Getter;
import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class ServerManager {
    @Getter
    public List<ServerInstance> servers = new ArrayList<>();
    private static final File quickjoinFile = new File(System.getenv("SystemDrive") +  "\\DarkMoon\\quickjoin.dm");

    public void init() throws IOException {
        if (!quickjoinFile.exists()) {
            quickjoinFile.createNewFile();
        } else {
            readServers();
        }
        addDefault();
    }

    public void addDefault() {
        if (!getServers().isEmpty()) return;
        addServer(new ServerInstance("ReallyWorld", "mc.reallyworld.ru"));
        addServer(new ServerInstance("SunRise", "play.sunmc.ru"));
        addServer(new ServerInstance("PlayMine", "mc.playmine.org"));
    }

    public void addServer(ServerInstance server) {
        servers.add(server);
        updateFile();
    }

    public void updateFile() {
        try {
            StringBuilder builder = new StringBuilder();
            servers.forEach(server -> builder.append(server.getName()).append(":").append(server.getIp()).append("\n"));
            Files.write(quickjoinFile.toPath(), builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readServers() {
        try {
            FileInputStream fileInputStream = new FileInputStream(quickjoinFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
            String line;
            while ((line = reader.readLine()) != null) {
                String curLine = line.trim();
                String command = curLine.split(":")[0];
                String key = curLine.split(":")[1];
                servers.add(new ServerInstance(command, key));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}