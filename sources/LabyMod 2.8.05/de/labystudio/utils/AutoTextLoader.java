package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EnumPlayerModelParts;
import org.apache.commons.io.IOUtils;
import org.lwjgl.input.Keyboard;

public class AutoTextLoader
{
    public static HashMap<String, String> autoText = new HashMap();
    public static boolean enabled = true;
    public static boolean listening = false;
    public static int key = -1;
    public static boolean ctrl = false;
    public static boolean shift = false;
    public static boolean alt = false;
    static boolean repeat = false;

    public static void load()
    {
        if (autoText.isEmpty())
        {
            autoText.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream("LabyMod/friend_autotext.json")));
            }
            catch (FileNotFoundException var3)
            {
                ;
            }
            catch (IOException var4)
            {
                ;
            }

            try
            {
                autoText = (HashMap)Utils.ConvertJsonToObject.getFromJSON(s, HashMap.class);
            }
            catch (Exception exception)
            {
                (new File("LabyMod/friend_autotext.json")).delete();
                exception.printStackTrace();
            }

            if (autoText == null)
            {
                autoText = new HashMap();
            }
        }
    }

    public static void create()
    {
        if (!(new File("LabyMod/friend_autotext.json")).exists())
        {
            try
            {
                if (!(new File("LabyMod/friend_autotext.json")).getParentFile().exists())
                {
                    (new File("LabyMod/friend_autotext.json")).getParentFile().mkdirs();
                }

                (new File("LabyMod/friend_autotext.json")).createNewFile();
            }
            catch (IOException var1)
            {
                ;
            }
        }
    }

    public static void save()
    {
        FriendsLoader.create();
        String s = Utils.ConvertJsonToObject.toJSON(autoText);

        try
        {
            PrintWriter printwriter = new PrintWriter(new FileOutputStream("LabyMod/friend_autotext.json"));
            printwriter.print(s);
            printwriter.flush();
            printwriter.close();
        }
        catch (FileNotFoundException var2)
        {
            ;
        }
    }

    public static void handleKeyboardInput(int key)
    {
        if (listening)
        {
            if (key != 29 && key != 42 && key != 56)
            {
                key = key;
            }

            if (Keyboard.isKeyDown(29))
            {
                ctrl = true;
            }

            if (Keyboard.isKeyDown(42))
            {
                shift = true;
            }

            if (Keyboard.isKeyDown(56))
            {
                alt = true;
            }
        }
        else if (key == -1)
        {
            repeat = false;
        }
        else
        {
            if (LabyMod.getInstance().mc.currentScreen == null && ConfigManager.settings.autoText && enabled && !repeat && Allowed.chat())
            {
                for (String s : autoText.keySet())
                {
                    try
                    {
                        if (Keyboard.isKeyDown(getNormalKey(s)) && (!isShift(s) || Keyboard.isKeyDown(42)) && (!isCtrl(s) || Keyboard.isKeyDown(29)) && (!isAlt(s) || Keyboard.isKeyDown(56)))
                        {
                            repeat = true;
                            String s1 = (String)autoText.get(s);

                            if (s1.contains("%togglelayer%"))
                            {
                                try
                                {
                                    int i = Integer.parseInt(s1.split("%togglelayer%")[1]);
                                    Minecraft.getMinecraft().gameSettings.switchModelPartEnabled(EnumPlayerModelParts.values()[i]);
                                }
                                catch (Exception exception)
                                {
                                    exception.printStackTrace();
                                }
                            }
                            else
                            {
                                LabyMod.getInstance().sendChatMessage(s1);
                            }
                        }
                    }
                    catch (Exception exception1)
                    {
                        exception1.printStackTrace();
                    }
                }
            }
        }
    }

    public static boolean isShift(String hotKeyCode)
    {
        return hotKeyCode.contains("#SHIFT");
    }

    public static boolean isAlt(String hotKeyCode)
    {
        return hotKeyCode.contains("#ALT");
    }

    public static boolean isCtrl(String hotKeyCode)
    {
        return hotKeyCode.contains("#CTRL");
    }

    public static int getNormalKey(String hotKeyCode)
    {
        try
        {
            return Integer.parseInt(hotKeyCode.replace("#SHIFT", "").replace("#ALT", "").replace("#CTRL", "").replace(";", ""));
        }
        catch (Exception var2)
        {
            return -1;
        }
    }
}
