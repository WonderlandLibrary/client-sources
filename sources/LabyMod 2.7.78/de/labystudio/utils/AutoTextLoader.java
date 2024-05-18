package de.labystudio.utils;

import de.labystudio.labymod.ConfigManager;
import de.labystudio.labymod.LabyMod;
import de.labystudio.labymod.Source;
import de.labystudio.labymod.Timings;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.HashMap;
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
        Timings.start("Load AutoText Config");

        if (autoText.isEmpty())
        {
            autoText.clear();
            String s = "";
            create();

            try
            {
                s = IOUtils.toString((InputStream)(new FileInputStream(Source.file_autoText)));
            }
            catch (FileNotFoundException var2)
            {
                ;
            }
            catch (IOException var3)
            {
                ;
            }

            autoText = (HashMap)Utils.ConvertJsonToObject.getFromJSON(s, HashMap.class);

            if (autoText == null)
            {
                autoText = new HashMap();
            }

            Timings.stop("Load AutoText Config");
        }
    }

    public static void create()
    {
        if (!(new File(Source.file_autoText)).exists())
        {
            try
            {
                if (!(new File(Source.file_autoText)).getParentFile().exists())
                {
                    (new File(Source.file_autoText)).getParentFile().mkdirs();
                }

                (new File(Source.file_autoText)).createNewFile();
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
            PrintWriter printwriter = new PrintWriter(new FileOutputStream(Source.file_autoText));
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
            if (LabyMod.getInstance().mc.currentScreen == null && ConfigManager.settings.autoText && enabled && !repeat)
            {
                for (String s : autoText.keySet())
                {
                    try
                    {
                        if (Keyboard.isKeyDown(getNormalKey(s)) && (!isShift(s) || Keyboard.isKeyDown(42)) && (!isCtrl(s) || Keyboard.isKeyDown(29)) && (!isAlt(s) || Keyboard.isKeyDown(56)))
                        {
                            repeat = true;
                            LabyMod.getInstance().sendChatMessage((String)autoText.get(s));
                            return;
                        }
                    }
                    catch (Exception var4)
                    {
                        ;
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
            return Integer.parseInt(hotKeyCode.replace("#SHIFT", "").replace("#ALT", "").replace("#CTRL", ""));
        }
        catch (Exception var2)
        {
            return -1;
        }
    }
}
