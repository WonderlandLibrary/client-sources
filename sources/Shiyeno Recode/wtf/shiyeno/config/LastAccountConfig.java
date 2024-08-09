package wtf.shiyeno.config;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Session;

import java.io.*;
import java.nio.file.Files;

public class LastAccountConfig {

    public static final File file = new File(Minecraft.getInstance().gameDir, "\\shiyeno\\lastAccount.shiyeno");

    public void init() throws Exception {
        if (!file.exists()) {
            System.out.println("Файл с последним аккаунтом не был найден! Создаю пустой файл..");
            file.createNewFile();
        } else {
            readAlts();
        }
    }

    public void updateFile() {
        try {
            Files.write(file.toPath(), Minecraft.getInstance().session.getUsername().getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void readAlts() {
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(new DataInputStream(new FileInputStream(file.getAbsolutePath()))));
            String line;
            while ((line = reader.readLine()) != null) {
                Minecraft.getInstance().session = new Session(line, "", "", "mojang");
            }
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}