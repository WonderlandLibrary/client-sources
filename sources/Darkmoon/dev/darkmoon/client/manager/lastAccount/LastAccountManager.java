package dev.darkmoon.client.manager.lastAccount;

import dev.darkmoon.client.utility.Utility;
import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.io.*;
import java.nio.file.Files;

public class LastAccountManager implements Utility {
    public static final File lastAccountFile = new File(System.getenv("SystemDrive") + "\\DarkMoon\\lastAccount.dm");

    public void init() throws IOException {
        if (!lastAccountFile.exists()) {
            lastAccountFile.createNewFile();
        } else {
            load();
        }
    }

    private void load() {
        try {
            FileInputStream fileInputStream = new FileInputStream(lastAccountFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
            String line = reader.readLine();
            mc.session = new Session(line.trim(), "", "", "mojang");
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void save() {
        try {
            Files.write(lastAccountFile.toPath(), (mc.session.getUsername()).getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
