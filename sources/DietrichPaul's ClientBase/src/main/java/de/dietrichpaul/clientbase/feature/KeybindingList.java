/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature;

import de.dietrichpaul.clientbase.ClientBase;
import de.dietrichpaul.clientbase.event.KeyListener;
import de.dietrichpaul.clientbase.feature.hack.Hack;
import de.dietrichpaul.clientbase.util.minecraft.ChatUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/*
TODO Mouse bindings
 */
public class KeybindingList implements KeyListener {
    private final Map<InputUtil.Key, List<String>> bindings = new LinkedHashMap<>();

    public KeybindingList() {
        ClientBase.INSTANCE.getEventDispatcher().subscribe(KeyListener.class, this);
    }

    @Override
    public void onKey(int key, int scan, int action, int modifiers) {
        if (action != GLFW.GLFW_PRESS || MinecraftClient.getInstance().currentScreen != null) {
            return;
        }
        List<String> messages = getBindings(InputUtil.fromKeyCode(key, scan));
        if (messages != null) {
            for (String message : messages) {
                Hack hack = ClientBase.INSTANCE.getHackList().getHack(message);
                if (hack != null) {
                    hack.toggle();
                    continue;
                }

                ChatUtil.sendChatMessageToServer(message);
            }
        }
    }

    public void bindWithoutSaving(InputUtil.Key key, String message) {
        bindings.computeIfAbsent(key, k -> new LinkedList<>()).add(message);
    }

    public void bind(InputUtil.Key key, String message) {
        bindWithoutSaving(key, message);
        ClientBase.INSTANCE.getConfigList().bind.save();
    }

    public List<String> getBindings(InputUtil.Key key) {
        return bindings.get(key);
    }

    public void unbind(InputUtil.Key key) {
        bindings.remove(key);
        ClientBase.INSTANCE.getConfigList().bind.save();
    }

    public Map<InputUtil.Key, List<String>> getBindings() {
        return bindings;
    }
}
