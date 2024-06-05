package zamorozka.ui;

import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import zamorozka.main.indexer;
import zamorozka.module.Module;
import zamorozka.module.ModuleManager;

import java.io.*;

public class FileManager {
    public static File Zamorozka;
    public static Minecraft mc = Minecraft.getMinecraft();

    public FileManager() {
        Zamorozka = new File((mc).mcDataDir + File.separator + "Zamorozka");
        if (!Zamorozka.exists()) {
            Zamorozka.mkdirs();
        }

        File file1 = new File(Zamorozka.getAbsolutePath(), "cfgs");

        if (!file1.exists()) {
            file1.mkdirs();
        }
        loadKeybinds();
        loadModules();
        loadFriends();

    }

    public static void saveKeybinds() {
        try {
            File file = new File(Zamorozka.getAbsolutePath(), "bind.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Module mod : ModuleManager.getModules()) {
                int getKey = mod.getKey();
                if (getKey <= 114) {
                    out.write("key-" + mod.getName().toLowerCase().replace(" ", "") + ":" + Keyboard.getKeyName(getKey));
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception e) {
            mc.player.sendChatMessage("Failed to save keybind!");
        }
    }


    public void loadKeybinds() {
        try {
            File file = new File(Zamorozka.getAbsolutePath(), "bind.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.toLowerCase().trim();
                String[] s = curLine.split(":");
                String hack = s[0];
                int id = Keyboard.getKeyIndex(s[1].toUpperCase());
                for (Module mod : ModuleManager.getModules()) {
                    if (hack.equalsIgnoreCase("key-" + mod.getName().toLowerCase().replace(" ", ""))) {
                        mod.setKey(id);
                    }
                }
            }
            br.close();
        } catch (Exception err) {
            err.printStackTrace();
            saveKeybinds();
            err.printStackTrace();

        }
    }

    public static void saveModules() {
        try {
            File file = new File(Zamorozka.getAbsolutePath(), "module.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Module mod : ModuleManager.getModules()) {
                if (mod.getState()) {
                    out.write(mod.getName().toLowerCase().replace(" ", ""));
                    out.write("\r\n");
                }
            }
            out.close();
        } catch (Exception e) {
        }
    }

    public void loadModules() {
        try {
            File file = new File(Zamorozka.getAbsolutePath(), "module.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.toLowerCase().trim();
                String name = curLine.split(":")[0];
                for (Module mod : ModuleManager.getModules()) {
                    if (mod.getName().toLowerCase().replace(" ", "").equals(name)) {
                        mod.setState(true);
                    }
                }
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    public static void saveFriends() {
        try {
            File file = new File(Zamorozka.getAbsolutePath(), "friend.txt");
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            for (Friend friend : FriendManager.friendsList) {
                out.write(friend.getName().toLowerCase().replace(" ", "") + ":" + friend.getAlias());
                out.write("\r\n");
            }

            out.close();
        } catch (Exception e) {
            mc.player.sendChatMessage("Failed to save friend!");
        }
    }

    public static File getConfigFile(String name) {
        File file = new File(Zamorozka, String.format("%s.txt", new Object[]{name}));
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return file;
    }

    public void loadFriends() {
        try {
            File file = new File(Zamorozka.getAbsolutePath(), "friend.txt");
            FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = br.readLine()) != null) {
                String curLine = line.trim();
                String name = curLine.split(":")[0];
                String alias = curLine.split(":")[1];
                indexer.getFriends().addFriend(name, alias);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}