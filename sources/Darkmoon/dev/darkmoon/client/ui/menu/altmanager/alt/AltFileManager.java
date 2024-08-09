package dev.darkmoon.client.ui.menu.altmanager.alt;

import net.minecraft.client.Minecraft;

import java.io.*;
import java.nio.file.Files;

public class AltFileManager {
    private static final File altsFile = new File(System.getenv("SystemDrive") + "\\DarkMoon\\alts.dm");

    public void init() throws IOException {
        if (!altsFile.exists()) {
            altsFile.createNewFile();
        } else {
            readAlts();
        }
    }

    private void readAlts() {
        try {
            FileInputStream fileInputStream = new FileInputStream(altsFile.getAbsolutePath());
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(fileInputStream)));
            String line;
            while ((line = reader.readLine()) != null) {
                String curLine = line.trim();
                String username = curLine.split(";")[0];
                String password = curLine.split(";")[1];
                String date = curLine.split(";")[2];
                AltManager.registry.add(new Alt(username, password, date));
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void saveAll() {
        try {
            StringBuilder builder = new StringBuilder();
            AltManager.registry.forEach(alt -> builder.append(alt.getUsername()).append(";").append(alt.getPassword()).append(";").append(alt.getDate()).append("\n"));
            Files.write(altsFile.toPath(), builder.toString().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
