package ru.FecuritySQ.module.общее;

import net.minecraft.client.MainWindow;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.settings.KeyBinding;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.event.Event;
import ru.FecuritySQ.event.imp.EventUpdate;
import ru.FecuritySQ.module.Module;

import java.lang.reflect.Field;

public class InventoryWalk  extends Module {
    public InventoryWalk () {
        super(Category.Общее, GLFW.GLFW_KEY_0);
    }

    @Override
    public void event(Event e) {
        if(e instanceof EventUpdate && isEnabled()){
            if (!(this.mc.currentScreen instanceof ChatScreen)) {
                try {
                    KeyBinding[] binds = this.mc.gameSettings.keyBindings;
                    KeyBinding[] var2 = binds;
                    int var3 = binds.length;

                    for(int var4 = 0; var4 < var3; ++var4) {
                        KeyBinding key = var2[var4];
                        Field field = KeyBinding.class.getDeclaredFields()[8];
                        field.setAccessible(true);
                        Field str_field = KeyBinding.class.getDeclaredFields()[4];
                        str_field.setAccessible(true);
                        String display = (String)str_field.get(key);
                        if (display.contains("forward")) {
                            field.set(key, isDown(87));
                        } else if (display.contains("back")) {
                            field.set(key, isDown(83));
                        }

                        if (display.contains("right")) {
                            field.set(key, isDown(68));
                        }

                        if (display.contains("left")) {
                            field.set(key, isDown(65));
                        } else if (display.contains("jump")) {
                            field.set(key, isDown(32));
                        }


                        if (display.contains("sprint")) {
                            field.set(key, isDown(341));
                        }
                    }
                } catch (Exception var9) {
                    var9.printStackTrace();
                }

            }
        }
        }
    public boolean isDown(int key) {
        MainWindow window = mc.getMainWindow();
        return GLFW.glfwGetKey(window.getHandle(), key) == 1;
    }
}
