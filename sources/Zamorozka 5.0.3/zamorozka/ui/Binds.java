package zamorozka.ui;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Keyboard;
import zamorozka.main.Zamorozka;

public class Binds {
    private static final boolean[] keyStates = new boolean[256];
    public static Multimap<Integer, String> binds = ArrayListMultimap.create();

    public static void makeBinds() {
        for (Integer key : binds.keySet()) {
            if (checkKey(key.intValue())) {
                for (String s : binds.get(key)) {
                    Zamorozka.player().sendChatMessage(s);
                    try {
                        Thread.sleep(15L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }


    }


    private static int getKey(String key) {
        return Keyboard.getKeyIndex(key);
    }


    private static boolean checkKey(int i) {
        if ((Minecraft.getMinecraft()).currentScreen != null) {
            return false;
        }

        if (Keyboard.isKeyDown(i) != keyStates[i]) {
            keyStates[i] = !keyStates[i];
            return !keyStates[i];
        }
        return false;
    }


    public static void addBind(String key, String msg) {
        binds.put(Integer.valueOf(getKey(key.toUpperCase())), msg);
        ChatUtils.printChatprefix("&aMessage \"" + msg + "\" binded on key " + key.toUpperCase() + ".");
    }


    public static void addBindSave(String key, String msg) {
        binds.put(Integer.valueOf(getKey(key.toUpperCase())), msg);
    }


    public static void delBind(String key) {
        if (!binds.containsKey(Integer.valueOf(getKey(key.toUpperCase())))) {
            ChatUtils.printChatprefix("&cThis key not contains messages.");

            return;
        }
        binds.removeAll(Integer.valueOf(getKey(key.toUpperCase())));
        ChatUtils.printChatprefix("&aDeleted messages from key " + key.toUpperCase() + ".");
    }
}
