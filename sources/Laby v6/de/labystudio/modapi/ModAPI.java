package de.labystudio.modapi;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.utils.DrawUtils;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.client.gui.GuiScreen;

public class ModAPI
{
    public static int registeredEvents = 0;

    public static Event callEvent(Event event)
    {
        try
        {
            for (Entry<Listener, List<Method>> entry : event.getListenerMethods().entrySet())
            {
                for (Object method : (List)entry.getValue())
                {
                    try
                    {
                        ((Method) method).invoke(entry.getKey(), new Object[] {event});
                    }
                    catch (Exception exception)
                    {
                        exception.printStackTrace();
                    }
                }
            }

            return event;
        }
        catch (Exception exception1)
        {
            exception1.printStackTrace();
            return null;
        }
    }

    public static boolean enabled()
    {
        return registeredEvents != 0 && ConfigManager.settings.api;
    }

    public static void registerListener(Listener listener)
    {
        try
        {
            for (Method method : listener.getClass().getDeclaredMethods())
            {
                method.setAccessible(true);

                if (method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes()[0].getSuperclass() == Event.class)
                {
                    Class <? extends Event > oclass = (Class<? extends Event>) method.getParameterTypes()[0];
                    Map<Listener, List<Method>> map = (Map)oclass.getDeclaredField("listenerMethods").get((Object)null);

                    if (!map.containsKey(listener))
                    {
                        map.put(listener, new ArrayList());
                    }

                    List<Method> list = (List)map.get(listener);
                    list.add(method);
                }
            }

            System.out.println("[LabyMod] New listener " + listener.getClass().getSimpleName());
            ++registeredEvents;
        }
        catch (Exception exception)
        {
            exception.printStackTrace();
        }
    }

    public static void addSettingsButton(String name, GuiScreen screen)
    {
        HashMap<String, GuiScreen> hashmap = new HashMap();
        hashmap.putAll(ModManager.getSettings());
        hashmap.put(name, screen);
        ModManager.setSettings(hashmap);
    }

    public static LabyMod getLabyMod()
    {
        return LabyMod.getInstance();
    }

    public static DrawUtils getDrawUtils()
    {
        return LabyMod.getInstance().draw;
    }

    public static GuiScreen getLastScreen()
    {
        return ModManager.getLastScreen();
    }
}
